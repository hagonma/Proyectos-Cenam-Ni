package com.claro.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import org.codehaus.jettison.json.JSONObject;
//import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/*
        hamilton.gonzalez@claro.com.ni - Dic23 
    */

public class RestServiceFACT
{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient(); 

    public Integer RestServiceFACT(String cadena) throws JSONException {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        System.out.println("RestServiceFACT - Inicio proceso ");
        
        String P_CLIENTE       = String.valueOf(root.get("cliente").getAsString()); 
        String P_COBERTURA      = String.valueOf(root.get("resultCobertura").getAsString());
        String P_DIRECCION  	= String.valueOf(root.get("dir").getAsString()); 
        String P_LATITUD  	 = String.valueOf(root.get("latitud").getAsString()); 
        String P_LONGITUD   	 = String.valueOf(root.get("longitud").getAsString());
        String P_MENSAJE  	 = String.valueOf(root.get("Mensaje").getAsString());   
        String P_NOINTERACCION   =String.valueOf(root.get("noInteraccion").getAsString()).toUpperCase();
        String P_TECNOLOGIA   	= String.valueOf(root.get("tecnologia").getAsString());
        String P_TIPOINTERACCION =String.valueOf(root.get("tipoInteraccion").getAsString());
        String P_TOKEN          =String.valueOf(root.get("token").getAsString());
        String P_USER    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("usuario").getAsString()));
        
        JSONObject cadena2 = new JSONObject();
        cadena2.put("cliente",  P_CLIENTE);
        cadena2.put("resultCobertura",  P_COBERTURA);
        cadena2.put("dir",  P_DIRECCION);
        cadena2.put("latitud",  P_LATITUD);
        cadena2.put("longitud",  P_LONGITUD);
        cadena2.put("Mensaje",  P_MENSAJE);
        cadena2.put("noInteraccion",  P_NOINTERACCION);
        cadena2.put("tecnologia",  P_TECNOLOGIA);
        cadena2.put("token",  P_TOKEN);
        cadena2.put("usuario",  P_USER);

        /*String cadena2 = "{"
                            + "\"cliente\": ""\""" + P_CLIENTE + """\",
                            + "\"resultCobertura\": \"1\","
                            + "\"dir\": \"UNIVERSIDAD ZAMORANO\", "
                            + "\"latitud\": \"14.009895\","
                            + "\"longitud\": \"-87.011206\","
                            + "\"Mensaje\": \"Sin cobertura\",  "
                            + "\"noInteraccion\": \"1319160\","
                            + "\"Tecnologia\": \"GPON\","
                            + "\"Token\": \""+P_TOKEN+""\","
                            + "\"Usuario\": \""+P_USUARIO+"" }"; */
        
        int resp = -1;
        RestServiceFACT example = new RestServiceFACT();
        
        System.out.println("RestServiceFACT - cadena " +cadena2.toString());
       
        try
        {
            //String response = example.post("http://172.20.5.145/zntapp_PISA_ETADIRECTWeb/mtto-geolocalizacion-pruebas", cadena);
            String response = example.post("http://172.20.5.139/zntapp_PISA_ETADIRECTWeb/Mtto_geolocalizacion", cadena2.toString());
              
            System.out.println("RestServiceFACT - response " +response);
            JSONObject json = new JSONObject(response); 
            resp = json.getInt( "codigo" );
            System.out.println("RestServiceFACT - response code " +resp);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
         
        System.out.println("RestServiceFACT - Fin proceso ");

        return resp;
    }
    
    String post(String url, String json) throws IOException {
        //RequestBody body = RequestBody.create(JSON, json);
        RequestBody body = RequestBody.Companion.create(json.toString(), JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

