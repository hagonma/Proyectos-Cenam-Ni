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
public class RespContainsPolygon {
    
    private int code;
    private String message;
    private String descriptionPolygon;
    private String stylePolygon;
    private double latitudeRequest;
    private double longitudeRequest;

    public RespContainsPolygon(int code, String message, String descriptionPolygon, String stylePolygon, double latitudeRequest, double longitudeRequest) {
        this.code = code;
        this.message = message;
        this.descriptionPolygon = descriptionPolygon;
        this.stylePolygon = stylePolygon;
        this.latitudeRequest = latitudeRequest;
        this.longitudeRequest = longitudeRequest;
    }
    
    public RespContainsPolygon() {
        this.code = -1;
        this.message = "No respuesta";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescriptionPolygon() {
        return descriptionPolygon;
    }

    public void setDescriptionPolygon(String descriptionPolygon) {
        this.descriptionPolygon = descriptionPolygon;
    }

    public String getStylePolygon() {
        return stylePolygon;
    }

    public void setStylePolygon(String stylePolygon) {
        this.stylePolygon = stylePolygon;
    }

    public double getLatitudeRequest() {
        return latitudeRequest;
    }

    public void setLatitudeRequest(double latitudeRequest) {
        this.latitudeRequest = latitudeRequest;
    }

    public double getLongitudeRequest() {
        return longitudeRequest;
    }

    public void setLongitudeRequest(double longitudeRequest) {
        this.longitudeRequest = longitudeRequest;
    }

    @Override
    public String toString() {
        return "RespContainsPolygon{" + "code=" + code + ", message=" + message + ", descriptionPolygon=" + descriptionPolygon + ", stylePolygon=" + stylePolygon + ", latitudeRequest=" + latitudeRequest + ", longitudeRequest=" + longitudeRequest + '}';
    }
    
    
    
    
    
}
