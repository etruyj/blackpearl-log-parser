//======================================================================
// CifsConnectionSummary.java
//      Description:
//          This class holds the information summarizing information on
//          active cifs connections.
//
// Created by Sean Snyder
//======================================================================

package com.socialvagrancy.blackpearl.logs.structures.outputs;

import com.google.gson.annotations.SerializedName;

public class CifsConnectionSummary {
    @SerializedName("share_name")
    private String shareName;
    @SerializedName("connected_host")
    private String connectedHost;
    private String user;
    private String group;
    @SerializedName("connected_at")
    private String connectedAt;
    private String protocol;
    private String encryption;
    private String signing;

    //===========================================
    // Getters
    //===========================================
    public String getShareName() { return shareName; }
    public String getConnectedHost() { return connectedHost; }
    public String getUser() { return user; }
    public String getGroup() { return group; }
    public String getConnectedAt() { return connectedAt; }
    public String getProtocol() { return protocol; }
    public String getEncryption() { return encryption; }
    public String getSigning() { return signing; }

    //===========================================
    // Setters
    //===========================================
    public void setShareName(String shareName) { this.shareName = shareName; }
    public void setConnectedHost(String connectedHost) { this.connectedHost = connectedHost; }
    public void setUser(String user) { this.user = user; }
    public void setGroup(String group) { this.group = group; }
    public void setConnectedAt(String connectedAt) { this.connectedAt = connectedAt; }
    public void setProtocol(String protocol) { this.protocol = protocol; }
    public void setEncryption(String encryption) { this.encryption = encryption; }
    public void setSigning(String signing) { this.signing = signing; }
}

