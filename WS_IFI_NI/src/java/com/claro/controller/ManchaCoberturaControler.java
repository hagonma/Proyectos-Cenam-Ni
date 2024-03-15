/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.controller;

import com.claro.data.DbModel;
import com.claro.data.ManchaCoberturaModel;
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
public class ManchaCoberturaControler {

    private ManchaCoberturaModel base;
    private Gson jsonConverter;
    JsonParser parser;
    private DbModel bd; 

    public ManchaCoberturaControler() {
        this.parser = new JsonParser();
        this.bd = new DbModel();
    }

    public String crearKmlsManchasCobertura(String cadena) throws UnsupportedEncodingException, IOException {
       
        this.base = new ManchaCoberturaModel();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();

        String key             = String.valueOf(root.get("key").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());

        try{
            return base.crearKmlsManchaDeCobertura(key, pais);
        }catch(Exception ex){
            return "{\"code\":\"-1\",\"mensaje\":\""+ex.toString()+"\", \"KMLS\":[]}";
        }
        
    }
}
