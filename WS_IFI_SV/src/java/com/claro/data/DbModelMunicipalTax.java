/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author hamilton.gonzalez
 */
public class DbModelMunicipalTax {
    
    
    
    private static final String ERROR_CALL_FUNCTION = "Error al llamar la funcion PL/SQL. ";
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";
    
    
    
    //
    public String getParametro(){
        
        System.out.println("DbModelMunicipalTax -- getParametro - Inicio.. ");  
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;     
        
        DbModel db = new DbModel();        
        conn = db.getConnection();
        
        String URL=null;
        try {
            String query = "SELECT VALOR FROM TBL_IFI_PARAMETROS WHERE NOMBRE = 'URL_API_CORP_MUNI_WEBMAP'";
            pstmt = conn.prepareStatement(query);
  
            rs = pstmt.executeQuery();
            if(rs.next()){
                URL = rs.getString("VALOR");
            }
            System.out.println("DbModelMunicipalTax -- getParametro - URL => "+URL);    
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
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
        System.out.println("DbModelMunicipalTax -- getParametro - Fin.. ");  
        return URL;
    }

    
    
    //
    public String getDepartamento(Integer codPais)
    {
        System.out.println("DbModelMunicipalTax -- getDepartamento - Inicio.. ");    
        
        String result="";
        String output=null;
        BufferedReader br = null;
        
        String URL=this.getParametro();
        try {
            
            System.out.println("DbModelMunicipalTax -- getDepartamento - codPais => "+codPais);    

            URL url = new URL(URL+"/departamento?codPais="+codPais);
            Charset utf8 = Charset.forName("UTF-8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("ContentType", "application/json");
            conn.setRequestProperty("Accept-Charset", utf8.toString());
            
            String contentEncoding = conn.getContentEncoding();
            
            System.out.println("DbModelMunicipalTax -- getDepartamento - contentEncoding => "+contentEncoding);

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()) , "UTF-8"));

            while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                    result=result+output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }

        System.out.println("DbModelMunicipalTax -- getDepartamento - Fin.. ");
        return result;
    }//getDepartamento
   
    
    
    
    
    //
    public String getMunicipio(Integer codDepto)
    {
            
        System.out.println("DbModelMunicipalTax -- getMunicipio - Inicio.. ");
    
        String result="";
        String output=null;
        BufferedReader br = null;
        
        String URL=this.getParametro();
        try {
            
            System.out.println("DbModelMunicipalTax -- getMunicipio - codDepto => "+codDepto);    

            URL url = new URL(URL+"/municipio?codDepto="+codDepto);
            Charset utf8 = Charset.forName("UTF-8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("ContentType", "application/json");
            conn.setRequestProperty("Accept-Charset", utf8.toString());
            
            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream()) , "UTF-8"));

            while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                    result=result+output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }
        
        System.out.println("DbModelMunicipalTax -- getMunicipio - Fin.. ");
        return result;
    }//getMunicipio
    
    
    
    
    
    //
    public String getPermisosMunicipales(Integer codMunic) {
        //return "Not supported yet!";
        
        System.out.println("DbModelMunicipalTax -- getPermisosMunicipales - Inicio.. ");
    
        String result="";
        String output=null;
        BufferedReader br = null;
        
        String URL=this.getParametro();
        try {
            
            System.out.println("DbModelMunicipalTax -- getPermisosMunicipales - codMunic => "+codMunic);    

            URL url = new URL(URL+"/PermiMunicipales?codMunicipio="+codMunic);
            Charset utf8 = Charset.forName("UTF-8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("ContentType", "application/json");
            conn.setRequestProperty("Accept-Charset", utf8.toString());

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
               (conn.getInputStream()) , "UTF-8"));

            while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                    result=result+output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }
        
        System.out.println("DbModelMunicipalTax -- getPermisosMunicipales - Fin.. ");
        return result;
    }

    
}
