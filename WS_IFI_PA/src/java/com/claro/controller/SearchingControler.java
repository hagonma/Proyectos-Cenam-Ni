/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.controller;

import com.claro.data.SearchingModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author Marvin Urias
 */
public class SearchingControler {

    private SearchingModel base;
    private Gson jsonConverter;
    JsonParser parser;

    public SearchingControler() {
        this.parser = new JsonParser();
    }

    public String consultarFactibilidad(String cadena) {
        this.base = new SearchingModel();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();

        double lat = Double.valueOf(root.get("lat").getAsString());
        double lng = Double.valueOf(root.get("lng").getAsString());
        int radio = Integer.valueOf(root.get("radio").getAsString());
        int cantidad = Integer.valueOf(root.get("cantidad").getAsString());
        return jsonConverter.toJson(base.consultarFactibilidad(lat, lng, radio, cantidad));
    }

}
