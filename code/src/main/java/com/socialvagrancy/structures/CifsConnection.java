//===================================================================
// CifsConnections.java
//      Description:
//          This class models hosts connected to a CIFS share.
//
// Created by Sean Snyder
//===================================================================

package com.socialvagrancy.blackpearl.logs.structures;

public class CifsConnection {
    private String pid;
    private String user;
    private String group;
    private String ip;
    private String ipv4Details;
    private String protocol;
    private String encryption;
    private String signing;

    //===========================================
    // Getters
    //===========================================
    public String getPid() { return pid; }
    public String getUser() { return user; }
    public String getGroup() { return group; }
    public String getIp() { return ip; }
    public String getIpv4Details() { return ipv4Details; }
    public String getProtocol() { return protocol; }
    public String getEncryption() { return encryption; }
    public String getSigning() { return signing; }

    //===========================================
    // Setters
    //===========================================
    public void setPid(String id) { this.pid = id; }
    public void setUser(String user) { this.user = user; }
    public void setGroup(String group) { this.group = group; }
    public void setIp(String ip) { this.ip = ip; }
    public void setIpv4Details(String ipv4Details) { this.ipv4Details = ipv4Details; }
    public void setProtocol(String protocol) { this.protocol = protocol; }
    public void setEncryption(String encryption) { this.encryption = encryption; }
    public void setSigning(String signing) { this.signing = signing; }
}
