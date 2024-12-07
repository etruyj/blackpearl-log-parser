//===================================================================
// ShareSummary.java
//      Description:
//          This model holds share information for output.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import com.socialvagrancy.utils.storage.UnitConverter;

public class ShareSummary {
    private String shareName;
    private String shareType;
    private String shareStatus;
    private boolean shareReadOnly; 
    private String path;
    private String volumeName;
    private String volumeUsed;
    private boolean volumeReadOnly;
    private String poolName;
    private String poolTotal;

    //===========================================
    // Getters
    //===========================================
    public String getShareName() { return shareName; }
    public String getShareType() { return shareType; }
    public String getShareStatus() { return shareStatus; }
    public boolean getShareReadOnly() { return shareReadOnly; }
    public String getPath() { return path; }
    public String getVolumeName() { return volumeName; }
    public String getVolumeUsed() { return volumeUsed; }
    public boolean getVolumeReadOnly() { return volumeReadOnly; }
    public String getPoolName() { return poolName; }
    public String getPoolTotal() { return poolTotal; }

    //===========================================
    // Setters
    //===========================================
    public void setShareName(String name) { this.shareName = name; }
    public void setShareType(String type) { this.shareType = type; }
    public void setShareStatus(String status) { this.shareStatus = status; }
    public void setShareReadOnly(boolean read_only) { this.shareReadOnly = read_only; }
    public void setPath(String path) { this.path = path; }
    public void setVolumeName(String name) { this.volumeName = name; }
    public void setVolumeUsed(String size) {
        this.volumeUsed = UnitConverter.bytesToHumanReadable(size);
    }
    public void setVolumeReadOnly(boolean read_only) { this.volumeReadOnly = read_only; }
    public void setPoolName(String name) { this.poolName = name; }
    public void setPoolTotal(String size) { 
        this.poolTotal = UnitConverter.bytesToHumanReadable(size);
    }
}
