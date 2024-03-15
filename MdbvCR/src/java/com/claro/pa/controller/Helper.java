/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Claro
 */
public class Helper {

    JSONObject objJson = new JSONObject();
    String json = "";
    
    public JSONObject request(String urld,String method,String data) throws MalformedURLException, ProtocolException, IOException, JSONException{
            URL url = new URL(urld);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json");//
            if(method.equals("POST")){
                String input = data;//"{ \"retrieveBillHistoryList\": {    \"AccountID\": \"62172869\",    \"UserProfileID\": \"FACTURACION.ELECTRONICA@CLARO.COM.GT\",    \"BillsQuantity\": \"12\",    \"Type\":\"N\"  }}";
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();
            }
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                	+ conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null ) {
                if(!output.equals("null"))
                    json += output;
            }
            conn.disconnect();
            return new JSONObject(json);
    }

    public InputStream getStreamFromUrl(String urlParam) throws MalformedURLException, IOException {

        URL url = new URL(urlParam);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("TIV-TLG-PRX-03", 4713));
        URLConnection uc;
        InputStream inputStream = null;
        try {
            uc = (URLConnection) url.openConnection(proxy);
            //uc = (URLConnection)url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2");
            uc.connect();
            inputStream = uc.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public void writeFile(JSONArray arr) throws JSONException, IOException {
        try {
            File file = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "..//..//..//web//file//LIMITE_CONSUMO_SUBSC.CSV");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < arr.length(); i++) {
                String line = arr.getJSONObject(i).getString("TELEFONO") + ";" + arr.getJSONObject(i).getString("LIMITE_CONSUMO") + ";" + arr.getJSONObject(i).getString("SEGMENTACION");
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setResponse(int code, JSONObject response) throws JSONException {
        switch (code) {
            case 100:
                objJson.put("Codigo", 100);
                objJson.put("Response", response);
                objJson.put("Description", "Continue");
                break;
            case 200:
                objJson.put("Codigo", 200);
                objJson.put("Response", response);
                objJson.put("Description", "OK");
                break;
            default:
                objJson.put("Codigo", 400);
                objJson.put("Response", response);
                objJson.put("Description", "Bad Request");
                break;
            case 401:
                objJson.put("Codigo", 401);
                objJson.put("Response", response);
                objJson.put("Description", "Unauthorized");
                break;
        }
    }

    public void setArrayResponse(int code, JSONArray response) throws JSONException {
        switch (code) {
            case 100:
                objJson.put("Codigo", 100);
                objJson.put("Response", response);
                objJson.put("Description", "Continue");
                break;
            case 200:
                objJson.put("Codigo", 200);
                objJson.put("Response", response);
                objJson.put("Description", "OK");
                break;
            default:
                objJson.put("Codigo", 400);
                objJson.put("Response", response);
                objJson.put("Description", "Bad Request");
                break;
            case 401:
                objJson.put("Codigo", 401);
                objJson.put("Response", response);
                objJson.put("Description", "Unauthorized");
                break;
        }
    }

    public String getResponse() {
        return objJson.toString();
    }

    public boolean validateToken(String token) throws Exception {
        String tokenF = "";
        String idUser = "";
        Usuario user = new Usuario();
        StringTokenizer st = new StringTokenizer(token, "-");
        while (st.hasMoreTokens()) {
            tokenF = st.nextToken();
            idUser = st.nextToken();
        }
        String tokenBack = user.getToken(Integer.parseInt(idUser));
        
        if (tokenF.equals(tokenBack)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateURL(String url, int rol) throws Exception {
        Usuario user = new Usuario();
        user.validateURL(url, rol);
        JSONObject job = new JSONObject(user.hp.getResponse());
        JSONArray job2 = job.getJSONArray("Response");
        JSONObject s = job2.getJSONObject(0);

        if (s.getString("URL").equals(url)) {
            return true;
        } else {
            return false;
        }
    }

    public void downloadFromInputStream(InputStream in, String filename)
            throws MalformedURLException, IOException {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
    
    public boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
}
