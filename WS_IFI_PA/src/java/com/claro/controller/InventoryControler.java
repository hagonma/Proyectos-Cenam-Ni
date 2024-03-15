/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.controller;

import com.claro.data.InventoryModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Marvin Urias
 */
public class InventoryControler {

    private InventoryModel base;
    private Gson jsonConverter;
    JsonParser parser;

    public InventoryControler() {
        this.parser = new JsonParser();
    }

    public String changeStatusPort(String cadena) throws UnsupportedEncodingException, IOException {
        try{
        this.base = new InventoryModel();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();

        String noInteraccion  = String.valueOf(root.get("noInteraccion").getAsString());
        String OldStatePort   = String.valueOf(root.get("oldStatePort").getAsString());        
        
        return base.changeStatusPort(noInteraccion, OldStatePort.toUpperCase());
        }catch(Exception ex){
            return jsonReturn("-1","Error general en metodo. Ex:"+ex.toString());
        }
    }
    
    private String jsonReturn(String code, String description){
		return "{\"exitStatus\": {" +
                    "   \"code\": "+code+"," +
                    "   \"description\": \""+description+"\"" +
                    "}}";
	}


}
