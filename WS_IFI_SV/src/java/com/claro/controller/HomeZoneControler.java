/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.controller;

import com.claro.beans.cell;
import com.claro.data.HomeZoneModel;
import com.claro.data.SearchingModelKml;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marvin Urias
 */
public class HomeZoneControler {

    private HomeZoneModel base;
    private Gson jsonConverter;
    JsonParser parser;

    public HomeZoneControler() {
        this.parser = new JsonParser();
    }

    public String consultarCeldas(String cadena) throws UnsupportedEncodingException, IOException {
        this.base = new HomeZoneModel();
        this.jsonConverter = new Gson();
        JsonObject root;
        Long msisdn;
        JsonArray cells3G;
        JsonArray cellsLTE;
        ArrayList<cell> c3G;
        ArrayList<cell> c4G;
        
        try{
            root = parser.parse(cadena).getAsJsonObject();
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-1", ex.toString());
        }
        
        try{
            msisdn           = Long.valueOf(root.get("msisdn").getAsString());
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-2", "Error falta atributo msisdn"); 
        }
        
        try{
            cells3G = (JsonArray) root.get("cells3G");
            c3G= new ArrayList<cell>();
        
            Iterator<JsonElement> iterator = cells3G.iterator();
            int pos3G=0;
             while (iterator.hasNext()) {
               JsonElement item =iterator.next();
               JsonObject jsonObj = item.getAsJsonObject();
               c3G.add(new cell(++pos3G, jsonObj.get("cgi").getAsString().replace("\"", ""),jsonObj.get("cell").getAsString().replace("\"", "")));
             }
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-3", "Error falta atributo cells3G"); 
        }
         
        try{
            cellsLTE = (JsonArray) root.get("cellsLTE");
            c4G= new ArrayList<cell>();
        
            Iterator<JsonElement> iterator2 = cellsLTE.iterator();
            int pos4G=0;
             while (iterator2.hasNext()) {
               JsonElement item =iterator2.next();
               JsonObject jsonObj = item.getAsJsonObject();
               c4G.add(new cell(++pos4G, jsonObj.get("cgi").getAsString().replace("\"", ""),jsonObj.get("cell").getAsString().replace("\"", "")));
             }
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-4", "Error falta atributo cellsLTE");
        }
        
        try{
            return base.consultarCeldas(msisdn, c3G, c4G);
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-5", ex.toString());
        }
    }
	
	private String jsonReturnCambio(String code, String description){
		return "{\"exitStatus\": {\n" +
                    "   \"code\": "+code+",\n" +
                    "   \"description\": \""+description+"\"\n" +
                    "}}";
	}
        
    public String cambioCliente(String cadena) throws UnsupportedEncodingException, IOException {
        this.base = new HomeZoneModel();
        this.jsonConverter = new Gson();
        JsonObject root;
        Long msisdn;
        String token;
        String tipo_cambio;
        
        try{
            root = parser.parse(cadena).getAsJsonObject();
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-1", ex.toString());
        }
        
        try{
            msisdn = Long.valueOf(root.get("msisdn").getAsString());
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-2", "Error falta atributo msisdn"); 
        }
        
        try{
            token = root.get("token").getAsString();
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-2", "Error falta atributo token"); 
        }
        
        try{
            tipo_cambio = root.get("tipo_cambio").getAsString();
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-2", "Error falta atributo tipo de cambio"); 
        }
           
        try{
            return base.cambioCliente(msisdn, token, tipo_cambio);
        }catch(Exception ex){
            Logger.getLogger(HomeZoneControler.class.getName()).log(Level.SEVERE, null,ex);
            return jsonReturnCambio("-3", ex.toString());
        }
    }
}