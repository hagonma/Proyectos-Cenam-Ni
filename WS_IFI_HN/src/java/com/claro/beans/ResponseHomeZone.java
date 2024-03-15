/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.beans;

import java.util.ArrayList;

/**
 *
 * @author Diego Lopez
 */

class exitStatus{
    protected int code;
    protected String description;

    public exitStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" + "\"code\":" + code + ", \"description\":\"" + description + "\"}";
    }
    
    
}

public class ResponseHomeZone {
    private exitStatus exitStatus;
    private Long msisdn;
    private String iteractionId;
    private String TokenId;
    private String lat_long;
    private ArrayList<cell> cells3G;
    private ArrayList<cell> cellsLTE;

    public ResponseHomeZone(int code, String description, Long msisdn, String iteractionId, String TokenId, String lat_long, ArrayList<cell> cells3G, ArrayList<cell> cellsLTE) {
        this.exitStatus = new exitStatus(code, description);
        this.msisdn = msisdn;
        this.iteractionId = iteractionId;
        this.TokenId = TokenId;
        this.lat_long = lat_long;
        this.cells3G = cells3G;
        this.cellsLTE = cellsLTE;
    }
    
    public ResponseHomeZone(int code, String description, Long msisdn) {
        this.exitStatus = new exitStatus(code, description);
        this.msisdn = msisdn;
        this.iteractionId = "";
        this.TokenId = "";
        this.lat_long = "";
        this.cells3G = null;
        this.cellsLTE = null;
    }

    public String getExitStatus() {
        return exitStatus.toString();
    }
    
    
    

    @Override
    public String toString() {
        return "{" + "\"exitStatus\":" + exitStatus + ", \"msisdn\":" + msisdn + ", \"iteractionId\":\"" + iteractionId + "\", \"TokenId\":\"" + TokenId + "\", \"lat_long\":\"" + lat_long + "\", \"cells3G\":" + cells3G.toString() + ", \"cellsLTE\":" + cellsLTE.toString() + '}';
    }
    
    
}
