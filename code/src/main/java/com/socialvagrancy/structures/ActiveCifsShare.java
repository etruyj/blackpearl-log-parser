//===================================================================
// ActiveCifsShare.java
//      Description:
//          This model holds the information for CIFS shares with 
//          active connections.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures;

public class ActiveCifsShare {
    private String share;
    private String pid;
    private String machine;
    private String connectedAt;
    private String encryption;
    private String signing;

    //===========================================
    // Getters
    //===========================================
    public String getShare() { return share; }
    public String getPid() { return pid; }
    public String getMachine() { return machine; }
    public String getConnectedAt() { return connectedAt; }
    public String getEncryption() { return encryption; }
    public String getSigning() { return signing; }

    //===========================================
    // Setters
    //===========================================
    public void setShare(String share) { this.share = share; }
    public void setPid(String pid) { this.pid = pid; }
    public void setMachine(String machine) { this.machine = machine; }
    public void setConnectedAt(String connectedAt) { this.connectedAt = connectedAt; }
    public void setEncryption(String encryption) { this.encryption = encryption; }
    public void setSigning(String signing) { this.signing = signing; }
}
