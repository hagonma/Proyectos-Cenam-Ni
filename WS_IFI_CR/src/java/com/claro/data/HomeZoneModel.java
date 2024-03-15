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
public class HomeZoneModel {

    private DbModelCRM baseCRM;
    private DbModel base;
    JsonParser parser;
    InputStream bi = null;

    
    public HomeZoneModel() {
        this.baseCRM = new DbModelCRM();
        this.base = new DbModel();
        this.parser = new JsonParser();       
    }

    public String consultarCeldas(Long msisdn, ArrayList<cell> c3G, ArrayList<cell> c4G) throws UnsupportedEncodingException, IOException {
        ResponseHomeZone respuesta;
        String orden=this.baseCRM.getOrden(msisdn.toString());
        double distanciaMax=Double.parseDouble(base.getDistanciaMaxHz(Integer.parseInt("506")));
        String responseValidateCellHz="{\"code\":\"-1\"}";
        
        if ((orden.equals("-1"))){
            responseValidateCellHz=this.base.getValidateCellsHZ(orden, distanciaMax, c3G, c4G,1, msisdn);
        }else{
            responseValidateCellHz=this.base.getValidateCellsHZ(orden, distanciaMax, c3G, c4G,2, msisdn);
        }
		
		

		JsonParser parser= new JsonParser();
		JsonObject root = parser.parse(responseValidateCellHz).getAsJsonObject();

		String code    = String.valueOf(root.get("code").getAsString());
		if (!code.equals("0")){
			respuesta= new ResponseHomeZone(-7,"Error no se encuentran datos en el sistema IFI",msisdn);
			return "{" + "\"exitStatus\":"+respuesta.getExitStatus()+ '}';
		}
		
		String TokenId    = String.valueOf(root.get("TokenId").getAsString());
		String lat_long    = String.valueOf(root.get("lat_long").getAsString());

		JsonArray celdas = (JsonArray) root.get("celdas");

		Iterator<JsonElement> iterator = celdas.iterator();

		 while (iterator.hasNext()) {
		   JsonObject celda = parser.parse(iterator.next().toString()).getAsJsonObject();
		   String cellid    = String.valueOf(celda.get("cellid").getAsString());
		   boolean install  = Boolean.valueOf(celda.get("install").getAsString());

		   for (cell c: c3G){
			   if(c.getCgi().equals(cellid)){
				   c.setInstall(install);
			   }
		   }

		   for (cell c: c4G){
			   if(c.getCgi().equals(cellid)){
				   c.setInstall(install);
			   }
		   }
		 }

		respuesta= new ResponseHomeZone(0,"Successful Transaction",msisdn,orden,TokenId,lat_long,c3G, c4G);
		return respuesta.toString();
        
    }
    
    public String cambioCliente(Long msisdn, String token, String tipo_cambio) throws UnsupportedEncodingException, IOException {
        String responseExecCambioCliente="{\"code\":\"-1\"}";
        try{
        String orden=this.baseCRM.getOrden(msisdn.toString());
        responseExecCambioCliente="{\"code\":\"-1\"}";
        
        responseExecCambioCliente=this.base.getExecCambioCliente(msisdn, token, tipo_cambio, orden, "506");
	
        return responseExecCambioCliente;
        }catch(Exception ex){
            responseExecCambioCliente= jsonReturnCambio("-4","Error al ejecutar cambio del cliente en el sistema IFI");
            return responseExecCambioCliente;
        }
    }
    
    private String jsonReturnCambio(String code, String description){
		return "{\"exitStatus\": {\n" +
                    "   \"code\": "+code+",\n" +
                    "   \"description\": \""+description+"\"\n" +
                    "}}";
	}
}
