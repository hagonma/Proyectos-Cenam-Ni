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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        //Hamilton G. - Enero 2023
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(String.valueOf(root.get("dir").getAsString()));
        String dir = m.replaceAll("");
        
        double lat              = Double.valueOf(root.get("lat").getAsString());
        double lng              = Double.valueOf(root.get("lng").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());
        String codePais         = String.valueOf(root.get("codePais").getAsString());
        String noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
        String user             = String.valueOf(root.get("user").getAsString());
        String cliente          = String.valueOf(root.get("cliente").getAsString());
        double tipoServicio     = Double.valueOf(bd.getServiceCode(root.get("tipoServicio").getAsString()));
        //String dir              = String.valueOf(root.get("dir").getAsString());
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
        
        //Hamilton G. - Enero 2023
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(String.valueOf(root.get("dir").getAsString()));
        String dir = m.replaceAll("");
        
        double lat              = Double.valueOf(root.get("lat").getAsString());
        double lng              = Double.valueOf(root.get("lng").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());
        String codePais         = String.valueOf(root.get("codePais").getAsString());
        String noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
        String key              = String.valueOf(root.get("key").getAsString());
        //String dir              = String.valueOf(root.get("dir").getAsString());
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
        
        //Hamilton G. - Enero 2023
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(String.valueOf(root.get("dir").getAsString()));
        String dir = m.replaceAll("");
        
        double lat              = Double.valueOf(root.get("lat").getAsString());
        double lng              = Double.valueOf(root.get("lng").getAsString());
        String pais             = String.valueOf(root.get("pais").getAsString());
        String codePais         = String.valueOf(root.get("codePais").getAsString());
        String noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
        String user             = String.valueOf(root.get("user").getAsString());
        String cliente          = String.valueOf(root.get("cliente").getAsString());
        double tipoServicio     = Double.valueOf(bd.getServiceCode(root.get("tipoServicio").getAsString()));
        //String dir              = String.valueOf(root.get("dir").getAsString());
        String optionConsult    = String.valueOf(root.get("optionConsult").getAsString());
        try{
        return jsonConverter.toJson(base.consultarFactibilidadCobertFija(lat, lng, pais, codePais, noInteraccion, user, cadena, cliente, tipoServicio, dir,optionConsult));
        }catch(Exception ex){
            return "{\"mensaje\":\""+ex+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[]}";
        }
    }
    
    //Hamilton G. - Abril 2022
    public String consultarFactibilidadAPITodas(String cadena) throws UnsupportedEncodingException, IOException {
        
        System.out.println("consultarFactibilidadAPITodas - inicio ");
         
        this.base = new SearchingModelKml();
        this.jsonConverter = new Gson();

        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        System.out.println("consultarFactibilidadAPITodas - cadena => " + cadena);
        
        double lat = 0.0;            
        double lng  = 0.0;            
        String pais  = "";          
        String codePais = "";       
        String noInteraccion = "";  
        String user ="";           
        String cliente ="";       
        double tipoServicio=0.0;    
        String dir ="";             
        String optionConsult ="";   
        String key =""; 
        
        String tipoServ = "";
        
        String LOGIC_READ_KMLS = "";
        String[] KMLS;
        
        //Hamilton G. - Enero 2023
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(String.valueOf(root.get("dir").getAsString()));
        dir = m.replaceAll("");

        if((root.get("tipoServicio").getAsString()).equals("MOVIL"))  
        {    
            System.out.println("consultarFactibilidadAPITodas - MOVIL.."); 

            lat              = Double.valueOf(root.get("lat").getAsString());
            lng              = Double.valueOf(root.get("lng").getAsString());
            pais             = String.valueOf(root.get("pais").getAsString());
            codePais         = String.valueOf(root.get("codePais").getAsString());
            noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
            key              = String.valueOf(root.get("key").getAsString());
            //dir              = String.valueOf(root.get("dir").getAsString());
            optionConsult    = String.valueOf(root.get("optionConsult").getAsString());  

            System.out.println("consultarFactibilidadAPITodas - key => " + key); 
            System.out.println("consultarFactibilidadAPITodas - optionConsult => " + optionConsult); 
            
            tipoServ = "MOVIL";
            
            // REALIZAR CONSULTA DE NUMEROS de opcion y recorrer opcciones
            LOGIC_READ_KMLS=bd.getParam("LOGIC_READ_KMLS2",Integer.parseInt(codePais)); 
            KMLS=LOGIC_READ_KMLS.split("\\|");
        }
        else
        {            
            System.out.println("consultarFactibilidadAPITodas - FIJA.."); 
                
            lat              = Double.valueOf(root.get("lat").getAsString());
            lng              = Double.valueOf(root.get("lng").getAsString());
            pais             = String.valueOf(root.get("pais").getAsString());
            codePais         = String.valueOf(root.get("codePais").getAsString());
            noInteraccion    = String.valueOf(root.get("noInteraccion").getAsString());
            user             = String.valueOf(root.get("user").getAsString());
            cliente          = String.valueOf(root.get("cliente").getAsString());
            tipoServicio     = Double.valueOf(bd.getServiceCode(root.get("tipoServicio").getAsString()));
            //dir              = String.valueOf(root.get("dir").getAsString());
            optionConsult    = String.valueOf(root.get("optionConsult").getAsString());

            tipoServ = "COBERTFIJA";            
            
            // REALIZAR CONSULTA DE NUMEROS de opcion y recorrer opcciones
            LOGIC_READ_KMLS=bd.getParam("LOGIC_READ_KMLS_RED_FIJA",Integer.parseInt(codePais));       
            KMLS=LOGIC_READ_KMLS.split("\\|");
        }
        
        System.out.println("consultarFactibilidadAPITodas - tipoServ => " + tipoServ); 
        
        try{
            String  parahacerlista="{\"coberturas\": [";        
            
            System.out.println("consultarFactibilidadAPITodas - LOGIC_READ_KMLS=> " + LOGIC_READ_KMLS);
            
            System.out.println("consultarFactibilidadAPITodas - KMLS=> " + KMLS);
            
            //String key="123ABC**";
            int c=0;
            for(int i=0;i<KMLS.length;i++){
                System.out.println("\n\n\n"
                + "**************************************"
                + "**************************************");
                System.out.println("consultarFactibilidadAPITodas - i=> " + i);          
                if(i!=c)
                parahacerlista = parahacerlista.concat(",");//
                //if(i==0||i==1||i==2) //+","
                if(tipoServ.equals("MOVIL"))
                parahacerlista = parahacerlista.concat(base.consultarFactibilidadAPI2(lat, lng, pais, codePais, noInteraccion, key, cadena, dir, /*KMLS[i]*/Integer.toString(i) ));                
                //if(i==3||i==4||i==5)
                if(tipoServ.equals("COBERTFIJA"))
                parahacerlista = parahacerlista.concat(base.consultarFactibilidadCobertFija2(lat, lng, pais, codePais, noInteraccion, user, cadena, cliente, tipoServicio, dir, Integer.toString(i) ));
                c=c+2;              
            }
            
            //parahacerlista = base.consultarFactibilidadAPI2(lat, lng, pais, codePais, noInteraccion, key, cadena, dir, "" );
            parahacerlista = parahacerlista.concat("]}");
               
            System.out.println("consultarFactibilidadAPITodas - parahacerlista => " + parahacerlista);
            
            System.out.println("consultarFactibilidadAPITodas - fin ");
            
            return jsonConverter.toJson(parahacerlista);               
               
        }catch(Exception ex){
            return "{\"mensaje\":\""+ex+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[],\"tecnology\":\"\"}";
        }
    }

}
