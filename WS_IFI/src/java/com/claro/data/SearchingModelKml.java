/*
 * Clase utilizada para la consulta de cargas de precios
 */
package com.claro.data;

import com.claro.services.ApplicationConfig;

import com.jcraft.jsch.Session;

import com.claro.beans.RespContainsPolygon;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;

import com.vividsolutions.jts.geom.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Lopez
 */
public class SearchingModelKml {

    private DbModel base;
    JsonParser parser;
    JSch sftp = new JSch();
    Session session = null;
    Channel channel;
    ChannelSftp sftpChannel = null;
    InputStream bi = null;
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";
    private static final int COUNTRY_ID = 502;
    Gson gson = new Gson();//Para mostrar objetos en consola
    
    public SearchingModelKml() {
        this.base = new DbModel();
        this.parser = new JsonParser();       
    }

    public String consultarFactibilidad(double lat, double lng, String pais, String codePais, String noInteraccion, String user, String request, String cliente, double tipoServicio, String dir, String optionConsult) throws UnsupportedEncodingException, IOException, JSchException, SftpException  {
        
        ////System.out.println("DECRYPTED = " + DecryptCryptoJs.decrypt(user));
        double radio=Double.parseDouble(base.getDistanciaMax(Integer.parseInt(codePais)));
        String medida=base.getMedidaDistanciaMax(Integer.parseInt(codePais));
        String msgNoProcedeVenta=base.getMsgNoProcedeVenta(Integer.parseInt(codePais));
        
        double estado_consulta=Double.parseDouble(base.getEstadoNoProcedeVenta(Integer.parseInt(codePais)));
        
        SearchingModelKml l = new SearchingModelKml();
        RespContainsPolygon rcp = new RespContainsPolygon();
        String respuesta = jsonMessage(msgNoProcedeVenta);
        String fileSelect="";
        
        int cantFiles=ApplicationConfig.Kmls.size();
        int indexFileSelect=0;
        String respLog="";
              
        do
        {
            try{
                rcp = new RespContainsPolygon();
                File file = ApplicationConfig.Kmls.get(indexFileSelect);
                indexFileSelect+=1; 
                fileSelect=file.toString();
                String LOGIC_READ_KMLS=base.getParam("LOGIC_READ_KMLS",COUNTRY_ID);
                String[] KMLS_READ_SPLIT=LOGIC_READ_KMLS.split("\\|");
                String  KML_READ=KMLS_READ_SPLIT[Integer.parseInt(optionConsult)];
                String[] KMLS=KML_READ.split("\\,");
                Boolean valid_kml=false;

                for(int i=0;i<KMLS.length;i++){
                    if (fileSelect.contains(KMLS[i])){
                        valid_kml=true;
                        break;
                    }
                }
                
                if (!valid_kml){
                    continue;
                }else{
                    System.out.println("file archivo a inspeccionar: "+file.toString());
                    rcp=l.parseKml(file, lat, lng, radio, rcp, medida);
                }
             } catch (Exception ex) {
                    respuesta = jsonMessage(ex.toString());
                    indexFileSelect=cantFiles;
                }
            System.out.println("RCP: "+rcp.toString());

            respuesta=l.validarSitio(rcp,codePais,fileSelect,0,noInteraccion);
            System.out.println("respuesta: "+respuesta);
            JsonObject rootVal = parser.parse(respuesta).getAsJsonObject();
            estado_consulta=Double.valueOf(rootVal.get("estado").getAsString());

            respLog = l.regLogConsulta(DecryptCryptoJs.decrypt(user), pais, noInteraccion, request, respuesta, codePais, cliente, tipoServicio, dir, lat, lng, estado_consulta, rcp.getDescriptionPolygon(),fileSelect);

            if(estado_consulta==1){
                break;
            }
        }
        while(cantFiles>indexFileSelect);

        JsonObject root = parser.parse(respLog).getAsJsonObject();
        String estado_respLog=root.get("estado").getAsString();
        if(!estado_respLog.equals("0")){
            respuesta = jsonMessage("Error en registro de log: "+root.get("msg").getAsString());
        }else{
            respuesta=respuesta.replace("[*TOKEN*]",root.get("token").getAsString());
            respuesta=respuesta.replace("[*VEL_MAX_WTTX*]",root.get("vel_max_wttx").getAsString());
            respuesta=respuesta.replace("[*UNIT_VEL_MAX_WTTX*]",root.get("unit_vel_max_wttx").getAsString());
        }
        return respuesta;
    }
    
    public String consultarFactibilidadAPI(double lat, double lng, String pais, String codePais, String noInteraccion, String key, String request, String dir, String optionConsult) throws UnsupportedEncodingException, IOException  {
        
        ////System.out.println("DECRYPTED = " + DecryptCryptoJs.decrypt(user));
        double radio=Double.parseDouble(base.getDistanciaMax(Integer.parseInt(codePais)));
        String medida=base.getMedidaDistanciaMax(Integer.parseInt(codePais));
        String msgNoProcedeVenta=base.getMsgNoProcedeVenta(Integer.parseInt(codePais));
        
        double estado_consulta=Double.parseDouble(base.getEstadoNoProcedeVenta(Integer.parseInt(codePais)));
        
        SearchingModelKml l = new SearchingModelKml();
        RespContainsPolygon rcp = new RespContainsPolygon();
        String respuesta = jsonMessage(msgNoProcedeVenta);
        String fileSelect="";
        
        int cantFiles=ApplicationConfig.Kmls.size();
        int indexFileSelect=0;
        String respLog="";
        
        do
        {
            try{
                rcp = new RespContainsPolygon();
                File file = ApplicationConfig.Kmls.get(indexFileSelect);
                indexFileSelect+=1;
                fileSelect=file.toString();
                String LOGIC_READ_KMLS=base.getParam("LOGIC_READ_KMLS",COUNTRY_ID);
                String[] KMLS_READ_SPLIT=LOGIC_READ_KMLS.split("\\|");
                String  KML_READ=KMLS_READ_SPLIT[Integer.parseInt(optionConsult)];
                String[] KMLS=KML_READ.split("\\,");
                Boolean valid_kml=false;

                for(int i=0;i<KMLS.length;i++){
                    if (fileSelect.contains(KMLS[i])){
                        valid_kml=true;
                        break;
                    }
                }
                
                if (!valid_kml){
                    continue;
                }else{
                    System.out.println("file archivo a inspeccionar: "+file.toString());
                    rcp=l.parseKml(file, lat, lng, radio, rcp, medida);
                }
             } catch (Exception ex) {
                    respuesta = jsonMessage(ex.toString());
                    indexFileSelect=cantFiles;
                }
            

            respuesta=l.validarSitio(rcp,codePais,fileSelect,1,noInteraccion);
            JsonObject rootVal = parser.parse(respuesta).getAsJsonObject();
            estado_consulta=Double.valueOf(rootVal.get("estado").getAsString());

            respLog = l.regLogConsultaAPI(key, pais, noInteraccion, request, respuesta, codePais, dir, lat, lng, estado_consulta, rcp.getDescriptionPolygon(),fileSelect);

            if(estado_consulta==1){
                break;
            }
        }
        while(cantFiles>indexFileSelect);
        
        JsonObject root = parser.parse(respLog).getAsJsonObject();
        String estado_respLog=root.get("estado").getAsString();
        if(!estado_respLog.equals("0")){
            respuesta = jsonMessage("Error en registro de log: "+root.get("msg").getAsString());
        }else{
            respuesta=respuesta.replace("[*TOKEN*]",root.get("token").getAsString());
            respuesta=respuesta.replace("[*VEL_MAX_WTTX*]",root.get("vel_max_wttx").getAsString());
            respuesta=respuesta.replace("[*UNIT_VEL_MAX_WTTX*]",root.get("unit_vel_max_wttx").getAsString());
        }
        return respuesta;
    }
    
    //Hamilton G. - Abril 2022
    public String consultarFactibilidadAPI2(double lat, double lng, String pais, String codePais, String noInteraccion, String key, String request, String dir, String optionConsult) throws UnsupportedEncodingException, IOException  {
        
        System.out.println("consultarFactibilidadAPI2 - inicio ");
        
        String tecno="";
         
        ////System.out.println("DECRYPTED = " + DecryptCryptoJs.decrypt(user));
        double radio=Double.parseDouble(base.getDistanciaMax(Integer.parseInt(codePais)));
        String medida=base.getMedidaDistanciaMax(Integer.parseInt(codePais));
        String msgNoProcedeVenta=base.getMsgNoProcedeVenta(Integer.parseInt(codePais));
        
        double estado_consulta=Double.parseDouble(base.getEstadoNoProcedeVenta(Integer.parseInt(codePais)));
        
        SearchingModelKml l = new SearchingModelKml();
        RespContainsPolygon rcp = new RespContainsPolygon();
        String respuesta = jsonMessage2(msgNoProcedeVenta, tecno);
        String fileSelect="";
        
        System.out.println("consultarFactibilidadAPI2 - respuesta 1 => " + respuesta);
        
        int cantFiles=ApplicationConfig.Kmls.size();
        int indexFileSelect=0;
        String respLog="";
        
        File file = null;
        do
        {
            try{
                rcp = new RespContainsPolygon();
                file = ApplicationConfig.Kmls.get(indexFileSelect);
                
                System.out.println("consultarFactibilidadAPI2 - file.getName() => " + file.getName());
                
                indexFileSelect+=1;
                fileSelect=file.toString();
                String LOGIC_READ_KMLS=base.getParam("LOGIC_READ_KMLS2",COUNTRY_ID);
                
                System.out.println("consultarFactibilidadAPI2 - LOGIC_READ_KMLS => " + LOGIC_READ_KMLS);
                
                String[] KMLS_READ_SPLIT=LOGIC_READ_KMLS.split("\\|");        
                String  KML_READ=KMLS_READ_SPLIT[Integer.parseInt(optionConsult)];
                
                System.out.println("consultarFactibilidadAPI2 - KML_READ => " + KML_READ);
                 
                String[] KMLS=KML_READ.split("\\,");
                
                System.out.println("consultarFactibilidadAPI2 - KMLS => " + KMLS);
                
                Boolean valid_kml=false;
                
                System.out.println("consultarFactibilidadAPI2 - fileSelect => " + fileSelect);
                
                System.out.println("consultarFactibilidadAPI2 - tecno => " + tecno);

                for(int i=0;i<KMLS.length;i++){
                    System.out.println("consultarFactibilidadAPI2 - KMLS["+i+"]=> "+KMLS[i]);
                    System.out.println("consultarFactibilidadAPI2 - fileSelect.contains(KMLS["+i+"])=> "+fileSelect.contains(KMLS[i]));
                    System.out.println("consultarFactibilidadAPI2 - getBasename(file.getName())=> "+getBasename(file.getName()));
                    //if (fileSelect.contains(KMLS[i])){
                    if(getBasename(file.getName()).equals(KMLS[i])){
                        valid_kml=true;
                        break;
                    }
                }
                
                System.out.println("consultarFactibilidadAPI2 - valid_kml: "+valid_kml);
                
                if (!valid_kml){
                    continue;
                }else{
                    System.out.println("consultarFactibilidadAPI2 - archivo a inspeccionar: "+file.toString());
                    rcp=l.parseKml(file, lat, lng, radio, rcp, medida);
                }
             } catch (Exception ex) {
                    respuesta = jsonMessage2(ex.toString(), tecno);
                    indexFileSelect=cantFiles;
                }
            
            //Hamilton G. - Abril 2022
            tecno=getBasename(file.getName()); 

            respuesta=l.validarSitio2(rcp,codePais,fileSelect,1,noInteraccion);
            
            System.out.println("consultarFactibilidadAPI2 - respuesta 2 => " + respuesta);
            
            JsonObject rootVal = parser.parse(respuesta).getAsJsonObject();
            estado_consulta=Double.valueOf(rootVal.get("estado").getAsString());

            respLog = l.regLogConsultaAPI(key, pais, noInteraccion, request, respuesta, codePais, dir, 
                    lat, lng, estado_consulta, rcp.getDescriptionPolygon(),tecno);

            if(estado_consulta==1){
                break;
            }
        }
        while(cantFiles>indexFileSelect);
        
        JsonObject root = parser.parse(respLog).getAsJsonObject();
        String estado_respLog=root.get("estado").getAsString();
        if(!estado_respLog.equals("0")){
            respuesta = jsonMessage2("Error en registro de log: "+root.get("msg").getAsString(), tecno);
        }else{
            respuesta=respuesta.replace("[*TOKEN*]",root.get("token").getAsString());
            respuesta=respuesta.replace("[*VEL_MAX_WTTX*]",root.get("vel_max_wttx").getAsString());
            respuesta=respuesta.replace("[*UNIT_VEL_MAX_WTTX*]",root.get("unit_vel_max_wttx").getAsString());
            respuesta=respuesta.replace("[*TECNO*]",tecno);
        }
        
        System.out.println("consultarFactibilidadAPI2 - respuesta 3 => " + respuesta);
        return respuesta;
    }
    
    public String consultarFactibilidadCobertFija(double lat, double lng, String pais, String codePais, String noInteraccion, String user, String request, String cliente, double tipoServicio, String dir, String optionConsult) throws UnsupportedEncodingException, IOException, JSchException, SftpException  {
        
        ////System.out.println("DECRYPTED = " + DecryptCryptoJs.decrypt(user));
        double radio=Double.parseDouble(base.getDistanciaMax(Integer.parseInt(codePais)));
        String medida=base.getMedidaDistanciaMax(Integer.parseInt(codePais));
        String msgNoProcedeVenta=base.getMsgNoProcedeVenta(Integer.parseInt(codePais));
        
        double estado_consulta=Double.parseDouble(base.getEstadoNoProcedeVenta(Integer.parseInt(codePais)));
        
        SearchingModelKml l = new SearchingModelKml();
        RespContainsPolygon rcp = new RespContainsPolygon();
        RespContainsPolygon rcp2 = new RespContainsPolygon();
        String respuesta = jsonMessage(msgNoProcedeVenta);
        String fileSelect="";
        
        int cantFiles=ApplicationConfig.Kmls.size();
        int indexFileSelect=0;
        String respLog="";
        
        do
        {
            File file = ApplicationConfig.Kmls.get(indexFileSelect);
            try{
                rcp = new RespContainsPolygon();
                indexFileSelect+=1;
                fileSelect=file.toString();
                System.out.println(fileSelect);
                String LOGIC_READ_KMLS=base.getParam("LOGIC_READ_KMLS_RED_FIJA",COUNTRY_ID);
                System.out.println(LOGIC_READ_KMLS);
                String[] KMLS_READ_SPLIT=LOGIC_READ_KMLS.split("\\|");
                String  KML_READ=KMLS_READ_SPLIT[Integer.parseInt(optionConsult)];
                System.out.println(KML_READ);
                String[] KMLS=KML_READ.split("\\,");
                Boolean valid_kml=false;

                for(int i=0;i<KMLS.length;i++){
                    if (fileSelect.contains(KMLS[i])){
                        valid_kml=true;
                        break;
                    }
                }
                
                if (!valid_kml){
                    continue;
                }else{
                    System.out.println("file archivo a inspeccionar: "+file.toString());
                    rcp=l.parseKml(file, lat, lng, radio, rcp, medida);
                }
             } catch (Exception ex) {
                respuesta = jsonMessage(ex.toString());
                indexFileSelect=cantFiles;
            }
            
            
            System.out.println(gson.toJson(rcp));
            
            if(rcp.getCode()==0){
                respuesta=base.getGponResp("RESPONSE_COBERT_FIJA_SUCCESS", Integer.parseInt(codePais));
                respuesta=respuesta.replace("[*NOM_CELL*]",rcp.getDescriptionPolygon());
                String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codePais));
                respuesta=respuesta.replace("[*TECNOLOGIA*]",file.getName().replace(EXT_FILE, "").trim());
                rcp2=l.parseKml(file, rcp.getDescriptionPolygon(), rcp2);
                
                 if(rcp2.getCode()==0){
                     respuesta=respuesta.replace("[*LAT*]", String.valueOf(rcp2.getLatitudeRequest()));
                     respuesta=respuesta.replace("[*LON*]", String.valueOf(rcp2.getLongitudeRequest()));
                 }else{
                     respuesta=respuesta.replace("[*LAT*]", String.valueOf(0));
                     respuesta=respuesta.replace("[*LON*]", String.valueOf(0));
                 }
            }else{
                respuesta=base.getGponResp("RESPONSE_COBERT_FIJA_ERROR", Integer.parseInt(codePais));
            }

            System.out.println("respuesta: "+respuesta);
            JsonObject rootVal = parser.parse(respuesta).getAsJsonObject();
            estado_consulta=Double.valueOf(rootVal.get("estado").getAsString());

            respLog = l.regLogConsulta(DecryptCryptoJs.decrypt(user), pais, noInteraccion, request, respuesta, codePais, cliente, tipoServicio, dir, lat, lng, estado_consulta, rcp.getDescriptionPolygon(),fileSelect);
            
            if(estado_consulta==1){
                break;
            }
        }
        while(cantFiles>indexFileSelect);
        
        JsonObject root = parser.parse(respLog).getAsJsonObject();
        String estado_respLog=root.get("estado").getAsString();
        if(!estado_respLog.equals("0")){
            respuesta = jsonMessage("Error en registro de log: "+root.get("msg").getAsString());
        }else{
            respuesta=respuesta.replace("[*TOKEN*]",root.get("token").getAsString());
        }
        return respuesta;
    }
    
    //Hamilton G. - Abril 2022
    public String consultarFactibilidadCobertFija2(double lat, double lng, String pais, String codePais, String noInteraccion, String user, String request, String cliente, double tipoServicio, String dir, String optionConsult) throws UnsupportedEncodingException, IOException, JSchException, SftpException  {
        
        System.out.println("consultarFactibilidadCobertFija2 - Model - Inicio ");
        
        String tecno="";
                
        double radio=Double.parseDouble(base.getDistanciaMax(Integer.parseInt(codePais)));
        String medida=base.getMedidaDistanciaMax(Integer.parseInt(codePais));
        String msgNoProcedeVenta=base.getMsgNoProcedeVenta(Integer.parseInt(codePais));
        
        double estado_consulta=Double.parseDouble(base.getEstadoNoProcedeVenta(Integer.parseInt(codePais)));
        
        SearchingModelKml l = new SearchingModelKml();
        RespContainsPolygon rcp = new RespContainsPolygon();
        RespContainsPolygon rcp2 = new RespContainsPolygon();
        String respuesta = jsonMessage2(msgNoProcedeVenta,tecno);
        String fileSelect="";
        
        int cantFiles=ApplicationConfig.Kmls.size();        
        System.out.println("consultarFactibilidadCobertFija2 - Model - cantFiles => "+cantFiles);
        
        int indexFileSelect=0;
        String respLog="";
        
        System.out.println("consultarFactibilidadCobertFija2 - Model - respuesta => " + respuesta);
        
        String EXT_FILE=null;
        
        File file = null;
        do
        {
            
            System.out.println("consultarFactibilidadCobertFija2 - Model - do ");            
            
            file = ApplicationConfig.Kmls.get(indexFileSelect);
            indexFileSelect+=1;
           // System.out.println("consultarFactibilidadCobertFija2 - Model - file => " + file);            
            
            try{
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - try ");
                
                rcp = new RespContainsPolygon();
                //indexFileSelect+=1;
                //fileSelect=file.toString();     
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - file.getCanonicalPath => " + file.getCanonicalPath());
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - file.getAbsolutePath => " + file.getAbsolutePath());
                          
                System.out.println("consultarFactibilidadCobertFija2 - Model - file.getParent => " + file.getParent());
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - file.getName => " + file.getName());
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - fileSelect => " + file.toString());
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - getBasename(file.getName()) => " + getBasename(file.getName()));
                
                String LOGIC_READ_KMLS=base.getParam("LOGIC_READ_KMLS_RED_FIJA",COUNTRY_ID);
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - LOGIC_READ_KMLS => " + LOGIC_READ_KMLS);
                        
                String[] KMLS_READ_SPLIT=LOGIC_READ_KMLS.split("\\|");
                String  KML_READ=KMLS_READ_SPLIT[Integer.parseInt(optionConsult)];
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - KML_READ => " + KML_READ);
                        
                String[] KMLS=KML_READ.split("\\,");
                Boolean valid_kml=false;
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - KMLS=> "+KMLS);

                for(int i=0;i<KMLS.length;i++){
                    System.out.println("consultarFactibilidadCobertFija2 - Model - KMLS["+i+"]=> "+KMLS[i]);
                    System.out.println("consultarFactibilidadCobertFija2 - Model - fileSelect.contains(KMLS["+i+"])=> "+fileSelect.contains(KMLS[i]));
                    //if (fileSelect.contains(KMLS[i])){
                    if(getBasename(file.getName()).equals(KMLS[i])){
                        valid_kml=true;
                        break;
                    }
                }
                
                System.out.println("consultarFactibilidadCobertFija2 - Model - valid_kml=> "+valid_kml);
                
                if (!valid_kml){
                    System.out.println("consultarFactibilidadCobertFija2 - Model - !valid_kml=> "+!valid_kml);
                    continue;
                }else{
                    System.out.println("consultarFactibilidadCobertFija2 - Model - archivo a inspeccionar: "+file.toString());
                    rcp=l.parseKml(file, lat, lng, radio, rcp, medida);
                }
             } catch (Exception ex) {
                respuesta = jsonMessage2(ex.toString(),tecno);
                indexFileSelect=cantFiles;
            }
            
            tecno=getBasename(file.getName()); //file.getName().replace(EXT_FILE, "").trim();
            
            if(rcp.getCode()==0){
                respuesta=base.getGponResp("RESPONSE_COBERT_FIJA_SUCCESS2", Integer.parseInt(codePais));
                respuesta=respuesta.replace("[*NOM_CELL*]",rcp.getDescriptionPolygon());
                EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codePais));
                //file.getName().replace(EXT_FILE, "").trim()
                respuesta=respuesta.replace("[*TECNOLOGIA*]",tecno);
                rcp2=l.parseKml(file, rcp.getDescriptionPolygon(), rcp2);
                
                 if(rcp2.getCode()==0){
                     respuesta=respuesta.replace("[*LAT*]", String.valueOf(rcp2.getLatitudeRequest()));
                     respuesta=respuesta.replace("[*LON*]", String.valueOf(rcp2.getLongitudeRequest()));
                 }else{
                     respuesta=respuesta.replace("[*LAT*]", String.valueOf(0));
                     respuesta=respuesta.replace("[*LON*]", String.valueOf(0));
                 }
                 
            }else{
                respuesta=base.getGponResp("RESPONSE_COBERT_FIJA_ERROR2", Integer.parseInt(codePais));
                System.out.println("consultarFactibilidadCobertFija2 - Model - respuesta 1=> "+respuesta);               
            }

            System.out.println("consultarFactibilidadCobertFija2 - Model - respuesta 2=> "+respuesta);
            JsonObject rootVal = parser.parse(respuesta).getAsJsonObject();
            estado_consulta=Double.valueOf(rootVal.get("estado").getAsString());

            respLog = l.regLogConsulta(DecryptCryptoJs.decrypt(user), pais, noInteraccion, request, 
                    respuesta, codePais, cliente, tipoServicio, dir, lat, lng, estado_consulta, 
                    rcp.getDescriptionPolygon(),tecno);
            
            if(estado_consulta==1){
                break;
            }
        }
        while(cantFiles>indexFileSelect);
        
        JsonObject root = parser.parse(respLog).getAsJsonObject();
        String estado_respLog=root.get("estado").getAsString();
        if(!estado_respLog.equals("0")){
            respuesta = jsonMessage2("Error en registro de log: "+root.get("msg").getAsString(), tecno);
        }else{
            respuesta=respuesta.replace("[*TOKEN*]",root.get("token").getAsString());
            System.out.println("consultarFactibilidadCobertFija2 - Model - respuesta 3=> "+respuesta);
            respuesta=respuesta.replace("[*TECNO*]",tecno);
            System.out.println("consultarFactibilidadCobertFija2 - Model - respuesta 4=> "+respuesta);
        }
        
        System.out.println("consultarFactibilidadCobertFija2 - Model - tecno=> "+tecno);
        System.out.println("consultarFactibilidadCobertFija2 - Model - respuesta 5=> "+respuesta);
        return respuesta;
    }
    
    private RespContainsPolygon parseKml(File kml_file, double lat, double lon, double radio, RespContainsPolygon rcp, String medida) {
        try {
           // BufferedInputStream is = new BufferedInputStream(kml_file);
            Kml kml = Kml.unmarshal(kml_file);
            Feature feature = kml.getFeature();
            rcp=parseFeature(feature, lat, lon, radio, rcp, medida,kml_file.toString());
            return rcp;
        }catch(Exception ex){
            System.out.println("Error parseKml: "+ex.toString());
            ex.printStackTrace();
            return rcp;
        }
    }
    
    private RespContainsPolygon parseFeature(Feature feature, double lat, double lon, double radio, RespContainsPolygon rcp, String medida, String kml_file) {
        if(feature != null) {
            if(feature instanceof Document) {
                Document document = (Document) feature;
                 for (int j=0; j< document.getFeature().size(); j++){
                    Folder folder = (Folder) document.getFeature().get(j);
                    String nameFolder="";
                    if (kml_file.toString().contains("GPON")){
                        nameFolder=folder.getName().trim()+"/";
                    }
                    for (int i = 0; i < folder.getFeature().size(); i++) {
                        if(folder.getFeature().get(i) instanceof Placemark) {
                            Placemark placemark = (Placemark) folder.getFeature().get(i);
                            Geometry geometry = placemark.getGeometry();
                            if(geometry instanceof Polygon) {
                                rcp = parseGeometry(geometry, nameFolder+quitCDATA(placemark.getName()) , placemark.getStyleUrl(), lat, lon, radio, rcp, medida);
                                if(rcp.getCode()==0){
                                    break;
                                }
                            }else if (geometry instanceof MultiGeometry){
                                MultiGeometry multiGeometry = (MultiGeometry) geometry;
                                for (int x = 0; x < multiGeometry.getGeometry().size(); x++) {
                                    Geometry geometryArray = multiGeometry.getGeometry().get(x);
                                    rcp=parseGeometry(geometryArray, nameFolder+quitCDATA(placemark.getName()), placemark.getStyleUrl(), lat, lon, radio, rcp, medida);
                                    if(rcp.getCode()==0){
                                        break;
                                    }
                                }
                            }
                        }
                        if(rcp.getCode()==0){
                            break;
                        }
                    }
                }
            }else if(feature instanceof Folder) {
                Folder folder = (Folder) feature;
                String nameFolder="";
                if (kml_file.toString().contains("GPON")){
                    nameFolder=folder.getName().trim()+"/";
                }
                for (int i = 0; i < folder.getFeature().size(); i++) {
                        if(folder.getFeature().get(i) instanceof Placemark) {
                            Placemark placemark = (Placemark) folder.getFeature().get(i);
                            Geometry geometry = placemark.getGeometry();
                            if(geometry instanceof Polygon) {
                                rcp = parseGeometry(geometry, nameFolder+quitCDATA(placemark.getName()), placemark.getStyleUrl(), lat, lon, radio, rcp, medida);
                                if(rcp.getCode()==0){
                                    break;
                                }
                            }else if (geometry instanceof MultiGeometry){
                                MultiGeometry multiGeometry = (MultiGeometry) geometry;
                                for (int j = 0; j < multiGeometry.getGeometry().size(); j++) {
                                    Geometry geometryArray = multiGeometry.getGeometry().get(j);
                                    rcp=parseGeometry(geometryArray, nameFolder+quitCDATA(placemark.getName()), placemark.getStyleUrl(), lat, lon, radio, rcp, medida);
                                    if(rcp.getCode()==0){
                                        break;
                                    }
                                }
                            }
                        }
                        if(rcp.getCode()==0){
                            break;
                        }
                    }
            }
        }
        return rcp;
    }
    
    private RespContainsPolygon parseGeometry(Geometry geometry, String descPoligono, String stylePoligono, double lat, double lon, double radio, RespContainsPolygon rcp, String medida) {
        if(geometry != null) {
            if(geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if(outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if(linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if(coordinates != null) {
                            for(Coordinate coordinate : coordinates) {
                                double distancia= calcularDistancia(lat, lon, coordinate.getLatitude(), coordinate.getLongitude(),medida);
                                if(distancia<=radio){
                                    rcp=esdentroPoligono(coordinates, descPoligono, stylePoligono, lat, lon, rcp);
                                    break;
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return rcp;
    }
    
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2, String unit){
        
        double distancia;
        double radio;
        
        //Radio de la tierra según WGS84
        radio = 6378.137;
        
        double deg2radMultiplier;
        deg2radMultiplier = (Math.PI / 180);
        
        lat1=lat1*deg2radMultiplier;
        lon1=lon1*deg2radMultiplier;
        
        lat2=lat2*deg2radMultiplier;
        lon2=lon2*deg2radMultiplier;
        
        double dlongitud=lon2-lon1;
        distancia=Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(dlongitud))*radio;
        
        if (unit.equals("M")){
            distancia=distancia*1000;
        }
        
        return distancia;
    }
    
    private RespContainsPolygon esdentroPoligono(List<Coordinate> coordinates, String descPoligono, String stylePoligono, double lat, double lon, RespContainsPolygon rcp){

        int i=0;
        
        GeometryFactory gf = new GeometryFactory();
        
        int numPoints = coordinates.size();
        com.vividsolutions.jts.geom.Coordinate[] points = new com.vividsolutions.jts.geom.Coordinate[numPoints];
        
        for(Coordinate coordinate : coordinates) {
            points[i] = new com.vividsolutions.jts.geom.Coordinate(coordinate.getLongitude(), coordinate.getLatitude());
            i++;
        }
        
        com.vividsolutions.jts.geom.LinearRing jtsRing = gf.createLinearRing(points);
        com.vividsolutions.jts.geom.Polygon poly = gf.createPolygon(jtsRing, null);
        
        com.vividsolutions.jts.geom.Coordinate coord = new com.vividsolutions.jts.geom.Coordinate(lon, lat);
        com.vividsolutions.jts.geom.Point pt = gf.createPoint(coord);
        if (poly.contains(pt)) {
            //System.out.println("Poligono: "+ descPoligono + " estilo: " + stylePoligono +" coordenadas(lat,lon): ("+lat+","+lon+")");
            rcp.setCode(0);
            rcp.setMessage("Success");
            rcp.setDescriptionPolygon(descPoligono);
            rcp.setStylePolygon(stylePoligono);
            rcp.setLatitudeRequest(lat);
            rcp.setLongitudeRequest(lon);
        }
        return rcp;   
    }
    
    public String validarSitio(RespContainsPolygon rcp, String pais, String VAR_TECNO, Integer IS_CRM, String noInteraccion) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "Sin cobertura";

        conn = this.base.getConnection();
 
        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_GET_VALID_SITE_SEC (?,?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, rcp.getDescriptionPolygon());
            pstmt.setString(2, pais);
            pstmt.setString(3, VAR_TECNO);
            pstmt.setDouble(4, IS_CRM);
            pstmt.setString(5, noInteraccion);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"VALIDACION IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"VALIDACION IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    //Hamilton G. - Abril 2022
    public String validarSitio2(RespContainsPolygon rcp, String pais, String VAR_TECNO, Integer IS_CRM, String noInteraccion) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "Sin cobertura";

        conn = this.base.getConnection();
 
        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_GET_VALID_SITE_SEC2 (?,?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, rcp.getDescriptionPolygon());
            pstmt.setString(2, pais);
            pstmt.setString(3, VAR_TECNO);
            pstmt.setDouble(4, IS_CRM);
            pstmt.setString(5, noInteraccion);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"VALIDACION IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"VALIDACION IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String regLogConsulta(String P_USER, String P_PAIS, String P_NO_INTERACTION, String P_REQUEST, String P_RESPONSE, String P_COUNTRY_ID, String P_CLIENTE, double P_TIPO_SERVICIO, String P_DIR,
                                    double P_LAT, double P_LNG, double P_STATE_ID, String P_DESC_POLIGONO, String P_TECNOLOGIA) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error en registro de LOG \"}";

        conn = this.base.getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_REG_LOG (?,?,?,?,?,?,?,?,?,?,?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_PAIS);
            pstmt.setString(3, P_NO_INTERACTION);
            pstmt.setString(4, P_REQUEST);
            pstmt.setString(5, P_RESPONSE);
            pstmt.setString(6, P_COUNTRY_ID);
            pstmt.setString(7, P_CLIENTE);
            pstmt.setDouble(8, P_TIPO_SERVICIO);
            pstmt.setString(9, P_DIR);
            pstmt.setDouble(10, P_LAT);
            pstmt.setDouble(11, P_LNG);
            pstmt.setDouble(12, P_STATE_ID);
            pstmt.setString(13, P_DESC_POLIGONO);
            pstmt.setString(14, P_TECNOLOGIA);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"REGISTRAR LOG IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"REGISTRAR LOG IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String regLogConsultaAPI(String P_KEY, String P_PAIS, String P_NO_INTERACTION, String P_REQUEST, String P_RESPONSE, String P_COUNTRY_ID, String P_DIR,
                                    double P_LAT, double P_LNG, double P_STATE_ID, String P_DESC_POLIGONO, String P_TECNOLOGIA) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "-1";

        conn = this.base.getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_REG_LOG_API (?,?,?,?,?,?,?,?,?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_KEY);
            pstmt.setString(2, P_PAIS);
            pstmt.setString(3, P_NO_INTERACTION);
            pstmt.setString(4, P_REQUEST);
            pstmt.setString(5, P_RESPONSE);
            pstmt.setString(6, P_COUNTRY_ID);
            pstmt.setString(7, P_DIR);
            pstmt.setDouble(8, P_LAT);
            pstmt.setDouble(9, P_LNG);
            pstmt.setDouble(10, P_STATE_ID);
            pstmt.setString(11, P_DESC_POLIGONO);
            pstmt.setString(12, P_TECNOLOGIA);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"REGISTRAR LOG IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(SearchingModelKml.class.getName()).log(Level.SEVERE, null,"REGISTRAR LOG IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String jsonMessage(String msg){
        return "{\"mensaje\":\""+msg+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[]}";
    }
    
    //Hamilton G. - Abril 2022
    public String jsonMessage2(String msg, String tecno){
        return "{\"mensaje\":\""+msg+"\",\"token\":\"\",\"fec\":\"\", \"celdas\":[],\"tecnology\":\""+tecno+"\"}";
    }
    
    public String quitCDATA(String etiqueta){
        etiqueta = (etiqueta==null)?"":etiqueta;
        return etiqueta.replace("!\\[CDATA\\[", "").replace("\\]\\]>", "");
    }

    private RespContainsPolygon parseKml(File kml_file, String poligono, RespContainsPolygon rcp2) {
        try {
           // BufferedInputStream is = new BufferedInputStream(kml_file);
            Kml kml = Kml.unmarshal(kml_file);
            Feature feature = kml.getFeature();
            rcp2=parseFeature(feature, poligono, rcp2);
            return rcp2;
        }catch(Exception ex){
            System.out.println("Error parseKml: "+ex.toString());
            return rcp2;
        }
    }
    
    private RespContainsPolygon parseFeature(Feature feature, String poligono, RespContainsPolygon rcp2) {
        if(feature != null) {
            if(feature instanceof Document) {
                Document document = (Document) feature;
                 for (int j=0; j< document.getFeature().size(); j++){
                    Folder folder = (Folder) document.getFeature().get(j);

                    for (int i = 0; i < folder.getFeature().size(); i++) {
                        if(folder.getFeature().get(i) instanceof Placemark) {
                            Placemark placemark = (Placemark) folder.getFeature().get(i);
                            Geometry geometry = placemark.getGeometry();
                            if(geometry instanceof Polygon) {
                                rcp2 = parseGeometry(geometry, quitCDATA(placemark.getName()) , placemark.getStyleUrl(), poligono, rcp2);
                            }else if (geometry instanceof MultiGeometry){
                                MultiGeometry multiGeometry = (MultiGeometry) geometry;
                                for (int x = 0; x < multiGeometry.getGeometry().size(); x++) {
                                    Geometry geometryArray = multiGeometry.getGeometry().get(x);
                                    rcp2=parseGeometry(geometryArray, quitCDATA(placemark.getName()), placemark.getStyleUrl(), poligono, rcp2);
                                }
                            }else if (geometry instanceof Point){
                                rcp2 = parseGeometry(geometry, quitCDATA(placemark.getName()) , placemark.getStyleUrl(), poligono, rcp2);
                                if(rcp2.getCode()==0){
                                    break;
                                }
                            }
                        }
                    }
                }
            }else if(feature instanceof Folder) {
                Folder folder = (Folder) feature;
                for (int i = 0; i < folder.getFeature().size(); i++) {
                        if(folder.getFeature().get(i) instanceof Placemark) {
                            Placemark placemark = (Placemark) folder.getFeature().get(i);
                            Geometry geometry = placemark.getGeometry();
                            if(geometry instanceof Polygon) {
                                rcp2 = parseGeometry(geometry, quitCDATA(placemark.getName()), placemark.getStyleUrl(), poligono, rcp2);
                            }else if (geometry instanceof MultiGeometry){
                                MultiGeometry multiGeometry = (MultiGeometry) geometry;
                                for (int j = 0; j < multiGeometry.getGeometry().size(); j++) {
                                    Geometry geometryArray = multiGeometry.getGeometry().get(j);
                                    rcp2=parseGeometry(geometryArray, quitCDATA(placemark.getName()), placemark.getStyleUrl(), poligono, rcp2);
                                }
                            }else if (geometry instanceof Point){
                                rcp2 = parseGeometry(geometry, quitCDATA(placemark.getName()) , placemark.getStyleUrl(), poligono, rcp2);
                                if(rcp2.getCode()==0){
                                    break;
                                }
                            }
                        }
                    }
            }
        }
        return rcp2;
    }
    
    private RespContainsPolygon parseGeometry(Geometry geometry, String descPoligono, String stylePoligono, String poligono, RespContainsPolygon rcp2) {
        if(geometry != null) {
            if(geometry instanceof Point) {
                Point point = (Point) geometry;
                List<Coordinate> coordinates = point.getCoordinates();
                if(coordinates != null) {
                    for(Coordinate coordinate : coordinates) {
                        if(descPoligono.trim().equals(poligono.trim())){
                            rcp2.setCode(0);
                            rcp2.setMessage("Success");
                            rcp2.setDescriptionPolygon(descPoligono);
                            rcp2.setStylePolygon(stylePoligono);
                            rcp2.setLatitudeRequest(coordinate.getLatitude());
                            rcp2.setLongitudeRequest(coordinate.getLongitude());
                            break;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        return rcp2;
    }
    
    //Hamilton G. - Abril 2022
    public static String getBasename(String fileName) {
        
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }        
      
    }
}
