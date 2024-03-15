/*
 * Clase utilizada para la consulta de cargas de precios
 */
package com.claro.data;


import com.claro.beans.ResponseHomeZone;
import com.claro.beans.cell;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Diego Lopez
 */
public class InventoryModel {

    private DbModelCRM baseCRM;
    private DbModel base;
    JsonParser parser;
    InputStream bi = null;

    
    public InventoryModel() {
        this.baseCRM = new DbModelCRM();
        this.base = new DbModel();
        this.parser = new JsonParser();       
    }

    public String changeStatusPort(String no_interaction, String OldStatePort) throws UnsupportedEncodingException, IOException {
        String respuesta;
        String NewStatusInteraction=this.baseCRM.getStatusInteraction(no_interaction);
        
        if(NewStatusInteraction.equals("N/A")){
            respuesta= jsonReturn("-2","Error orden se encuentra en un estado diferente al mapeado.");
            return respuesta;
        }
        
        String[] changeStatePort = new String[2];
        changeStatePort= this.base.changeStatePortTrx(no_interaction,OldStatePort,NewStatusInteraction);

        respuesta= jsonReturn(changeStatePort[0],changeStatePort[1]);
        System.out.println("respuesta: "+respuesta);
        return respuesta;
        
    }

    
    private String jsonReturn(String code, String description){
		return "{\"exitStatus\": {" +
                    "   \"code\": "+code+"," +
                    "   \"description\": \""+description+"\"" +
                    "}}";
	}
    
    
}
