//===================================================================
// ListShares.java
//      Description:
//          This command pulls a list of all share information in the
//          BlackPearl and displays it in a list.
//
//      Information Provided:
//          - Share Name
//          - Share Type
//          - Mount Point
//          - Volume Name
//          - Volume Used Size
//          - Pool Name
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.outputs.ShareSummary;
import com.socialvagrancy.blackpearl.logs.structures.rest.Pools;
import com.socialvagrancy.blackpearl.logs.structures.rest.data.Pool;
import com.socialvagrancy.blackpearl.logs.structures.rest.Shares;
import com.socialvagrancy.blackpearl.logs.structures.rest.Volumes;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapVolumeToID;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapPoolIDtoPool;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetPoolData;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetShareData;
import com.socialvagrancy.blackpearl.logs.utils.importers.rest.GetVolumeData;

import java.util.ArrayList;
import java.util.Map;

public class ListShares {
    public static ArrayList<ShareSummary> all(String path) {
        // Read data from log files.
        Shares share_info = GetShareData.fromRest(path);
        Volumes vol_info = GetVolumeData.fromRest(path);
        Pools pool_info = GetPoolData.fromRest(path);

        // Map data to ids to allow them to be linked.
        Map<String, Volumes.Volume> vol_map = MapVolumeToID.createMap(vol_info);
        Map<String, Pool> pool_map = MapPoolIDtoPool.createMapForNAS(pool_info);

        ArrayList<ShareSummary> share_list = new ArrayList<ShareSummary>();
        ShareSummary summary = null;
        Volumes.Volume temp_vol = null;
        Pool temp_pool = null;

        for(Shares.Share share : share_info.getData()) {
            summary = new ShareSummary();

            summary.setShareName(share.getName());
            summary.setShareType(share.getType());
            summary.setShareStatus(share.getStatus());
            summary.setShareReadOnly(share.getReadOnly());
            summary.setPath(share.getPath());
            
            if(vol_map.get(share.getVolumeId()) != null) {
                temp_vol = vol_map.get(share.getVolumeId());

                summary.setVolumeName(temp_vol.getZfsName());
                summary.setVolumeUsed(temp_vol.getUsedString());
                summary.setVolumeReadOnly(temp_vol.getReadOnly());

            } else {
                summary.setVolumeName("NO VOLUME");
            }

            if(temp_vol != null && pool_map.get(temp_vol.getPoolId()) != null) {
                temp_pool = pool_map.get(temp_vol.getPoolId());

                summary.setPoolName(temp_pool.getName());
                summary.setPoolTotal(temp_pool.getTotalAvailableCapacityString());

            } else {
                summary.setPoolName("NO POOL");
            }

            share_list.add(summary);
        }

        return share_list;
    }
}
