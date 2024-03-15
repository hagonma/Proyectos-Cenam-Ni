/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.beans;

/**
 *
 * @author Diego Lopez
 */
public class cell {
    private int posix;
    private String cgi;
    private String cellid;
    private boolean install;

    public cell(int posix, String cgi, String cellid) {
        this.posix = posix;
        this.cgi=cgi;
        this.cellid = cellid;
        this.install = false;
    }

    public int getPosix() {
        return posix;
    }

    public void setPosix(int posix) {
        this.posix = posix;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public boolean isInstall() {
        return install;
    }

    public void setInstall(boolean install) {
        this.install = install;
    }

    public String getCgi() {
        return cgi;
    }

    public void setCgi(String cgi) {
        this.cgi = cgi;
    }

       
    @Override
    public String toString() {
        return "{" + "\"posix\":" + posix + ", \"cgi\":\"" + cgi +"\", \"cell\":\"" + cellid + "\", \"install\":" + install + '}';
    }
    
      
}
