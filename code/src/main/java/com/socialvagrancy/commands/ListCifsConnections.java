//===================================================================
// ListCifsConnections.java
//      Description:
//          This function provides a list of active CIFS connections
//          and what shares they're connected to.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.commands;

import com.socialvagrancy.blackpearl.logs.structures.ActiveCifsShare;
import com.socialvagrancy.blackpearl.logs.structures.CifsConnection;
import com.socialvagrancy.blackpearl.logs.structures.outputs.CifsConnectionSummary;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetCifsConnections;
import com.socialvagrancy.blackpearl.logs.utils.importers.GetActiveShares;
import com.socialvagrancy.blackpearl.logs.utils.linkers.MapActiveCifsShareToPid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCifsConnections {
    public static ArrayList<CifsConnectionSummary> active(String dir_path) {
        List<CifsConnection> conn_list = GetCifsConnections.fromFile(dir_path);
        ArrayList<ActiveCifsShare> share_list = GetActiveShares.fromFile(dir_path);
        ArrayList<CifsConnectionSummary> summary_list = new ArrayList<CifsConnectionSummary>();
        CifsConnectionSummary summary = null;
        ActiveCifsShare temp_share = null;

        // Remove the hidden windows share IPC$ from the list
        // in case it used the same PID as another share.
        for(int i=0; i<share_list.size(); i++) {
            if(share_list.get(i).getShare().equals("IPC$")) {
                share_list.remove(i);
                i--; // drop back one as the current element was dropped.
            }
        }

        Map<String, ActiveCifsShare> share_map = MapActiveCifsShareToPid.createMap(share_list);

        for(CifsConnection conn : conn_list) {

            if(share_map.get(conn.getPid()) != null) {
                summary = new CifsConnectionSummary();
                
                temp_share = share_map.get(conn.getPid());

                summary.setShareName(temp_share.getShare());
                summary.setConnectedHost(temp_share.getMachine());
                summary.setUser(conn.getUser());
                summary.setGroup(conn.getGroup());
                summary.setConnectedAt(temp_share.getConnectedAt());
                summary.setProtocol(conn.getProtocol());
                summary.setEncryption(conn.getEncryption());
                summary.setSigning(conn.getSigning());

                summary_list.add(summary);
            } else {
                System.err.println("Unable to find share with PID: " + conn.getPid());
            }
        }

        return summary_list;
    }
}
