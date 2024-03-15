/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.controller;

import com.claro.data.DbModel;
import com.claro.data.SearchingModelKml;
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
public class SearchingControlerKml {

    private SearchingModelKml base;
    private Gson jsonConverter;
    JsonParser parser;
    private DbModel bd;

    public SearchingControlerKml() {
        this.parser = new JsonParser();
        this.bd = new DbModel();
    }

    public String consultarFactibilidad(String cadena) throws UnsupportedEncodingException, IOException {
        this.base = new SearchingModelKml();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();

        double lat              = Double.valueOf(root.get("lat").getAsString());
        double lng              = Double.valueOf(root.get("lng").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());
        String codePais         = String.valueOf(root.get("codePais").getAsString());
        String noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
        String user             = String.valueOf(root.get("user").getAsString());
        String cliente          = String.valueOf(root.get("cliente").getAsString());
        double tipoServicio     = Double.valueOf(bd.getServiceCode(root.get("tipoServicio").getAsString()));
        String dir              = String.valueOf(root.get("dir").getAsString());
        String optionConsult    = String.valueOf(root.get("optionConsult").getAsString());
        try{
        return jsonConverter.toJson(base.consultarFactibilidad(lat, lng, pais, codePais, noInteraccion, user, cadena, cliente, tipoServicio, dir, optionConsult));
        }catch(Exception ex){
            return "{\"mensaje\":\""+ex+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[]}";
        }
    }
    
    public String consultarFactibilidadAPI(String cadena) throws UnsupportedEncodingException, IOException {
        this.base = new SearchingModelKml();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();

        double lat              = Double.valueOf(root.get("lat").getAsString());
        double lng              = Double.valueOf(root.get("lng").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());
        String codePais         = String.valueOf(root.get("codePais").getAsString());
        String noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
        String key              = String.valueOf(root.get("key").getAsString());
        String dir              = String.valueOf(root.get("dir").getAsString());
        String optionConsult    = String.valueOf(root.get("optionConsult").getAsString());
        try{
        return jsonConverter.toJson(base.consultarFactibilidadAPI(lat, lng, pais, codePais, noInteraccion, key, cadena, dir, optionConsult));
        }catch(Exception ex){
            return "{\"mensaje\":\""+ex+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[]}";
        }
    }
    
    public String consultarFactibilidadCobertFija(String cadena) throws UnsupportedEncodingException, IOException {
        this.base = new SearchingModelKml();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();

        double lat              = Double.valueOf(root.get("lat").getAsString());
        double lng              = Double.valueOf(root.get("lng").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());
        String codePais         = String.valueOf(root.get("codePais").getAsString());
        String noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
        String user             = String.valueOf(root.get("user").getAsString());
        String cliente          = String.valueOf(root.get("cliente").getAsString());
        double tipoServicio     = Double.valueOf(bd.getServiceCode(root.get("tipoServicio").getAsString()));
        String dir              = String.valueOf(root.get("dir").getAsString());
        String optionConsult    = String.valueOf(root.get("optionConsult").getAsString());
        try{
        return jsonConverter.toJson(base.consultarFactibilidadCobertFija(lat, lng, pais, codePais, noInteraccion, user, cadena, cliente, tipoServicio, dir,optionConsult));
        }catch(Exception ex){
            return "{\"mensaje\":\""+ex+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[]}";
        }
    }

}
