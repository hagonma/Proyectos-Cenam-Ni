/*
 * Informacion general de la base de datos
 */
package com.claro.data;

import com.claro.beans.UserLDAP;
import com.claro.beans.cell;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author hamilton.gonzalez @Dic23
 */
public class DbModelFACT {
    
    public Connection conn;
    public PreparedStatement ps;
    public ResultSet rs;
    public CallableStatement cstmt;
    
     private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@172.24.35.134:1521:SMARTEST";
    private static final String DB_IP = "172.24.35.134";
    private static final String DB_PORT = "1521";
    private static final String DB_NAME = "SMARTEST";
    private static final String DB_USERNAME = "SMART";
    private static final String DB_PASSWORD = "ywttwyyustwz";
        
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";

    public Connection Conectar() throws Exception {
        
        
        File jar = new File("//home//cobertura-ifi//library//oracle//ojdbc6.jar");
        //File jar = new File("C:\\tmp\\cobertura-ifi\\library\\oracleojdbc6.jar");
        
        //DriverManager.registerDriver((Driver)new OracleDriver());
            //if (this.ip == "") {
            //String url = new String("jdbc:oracle:thin:@" + this.ip + ":" + this.port + "/" + this.bd);
            try {
                
                URL[] cp = new URL[1];
                cp[0] = jar.toURI().toURL();
                URLClassLoader ora9loader = new URLClassLoader(cp, ClassLoader.getSystemClassLoader());
                Class<?> drvClass = ora9loader.loadClass(this.DB_DRIVER);
                Driver ora9driver = (Driver)drvClass.newInstance();
                Properties props = new Properties();
                
                 props.setProperty("user", this.DB_USERNAME);
                 props.setProperty("password", this.DB_PASSWORD);
                 this.conn = ora9driver.connect("jdbc:oracle:thin:@" + this.DB_IP + ":" + this.DB_PORT + "/" + this.DB_NAME, props);

                //DriverManager.getConnection(url, this.usr, this.pass);
                //this.conexion = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                
                  if(this.conn != null){
			   System.out.println("Conectado correctamente.");
			}else{
			   System.out.println("No se ha podido conectar.");
			}

                  
                     return this.conn;   
                        
                 }catch(Exception e){
			System.out.println("Error al tratar de abrir la base de Datos" + e.getLocalizedMessage());
                        return null;
		}
        
      
    }

    public Connection CerrarConsulta() throws SQLException {
        try {
            this.ps.close();
            this.rs.close();
            this.conn.close();
            this.conn = null;
            return this.conn;
        } catch (Exception exc) {
            return null;
        } 
    }

    public ResultSet Consultar(String SQL) throws SQLException {
        this.ps = null;
        this.rs = null;

        try {
            this.ps = this.conn.prepareStatement(SQL);
            this.rs = this.ps.executeQuery();

            return this.rs;
        } catch (Exception exc) {
            System.out.println("Error al tratar de ejecutar el query: " + exc);
         return null;
        } 
    }
    
    public Integer AsociarOrdenFACT(String cadena) {   
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        System.out.println("AsociarOrdenFACT - Inicio proceso ");
        
        //String P_USER_CREATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_create").getAsString()));
        
        String P_LATITUD  	 = String.valueOf(root.get("Latitud").getAsString()); 
        String P_LONGITUD   	 = String.valueOf(root.get("Longitud").getAsString());
        String P_NOINTERACCION   =String.valueOf(root.get("NoInteraccion").getAsString());
        String P_TOKEN          =String.valueOf(root.get("Token").getAsString());
        
        //Connection conn;
        //CallableStatement pstmt = null;
        //ResultSet rs = null;
        String query;

        System.out.println("AsociarOrdenFACT - P_NOINTERACCION - " + P_NOINTERACCION );
        
        Integer respuesta = -1; //{\"estado\":\"-1\",\"msg\":\"Error al crear orden.\"}";

        /**
        VNI_CONTRATO, -- Id del contrato al que se le actualizara las georreferencias (este no es necesario, si se manda el id de solicitud, entonces se mandaría en valor NULL)
        VNI_LATITUD, -- Latitud de la georreferencia
        VNI_LONGITUD, -- Longitud de la georreferencia
        VNI_SOLICITUD, -- Id de la solicitud de venta, daño o traslado a la cual se mandará a actualizar el contrato a la que pertenece su posición georreferencial
        VVI_TOKEN, -- Token enviado por plataforma externa
        VVI_PROGRAM, -- Nombre de la plataforma que está mandando a registrar la coordenada
        VVI_OBSERVACION, -- Observación por la gestión del registro
        **/

        try {
            //Obtener todas las cargas propuestas
             query = "{ CALL \n" 
                      + "SMART.ENI_PR_UBICACION_GEOGRAFICA(NULL,"
                                               //+P_NOINTERACCION+", "
                                               +P_LATITUD+", "
                                               +P_LONGITUD+", "
                                               +P_NOINTERACCION+", '"
                                               +P_TOKEN+"', "
                                               +"'Consulta de Cobertura', "
                                               +"'Envío de GeoLocalización al Facturador',"
                      + "?,?) }\n";
            
            System.out.println("AsociarOrdenFACT - query - " + query );
            
            cstmt = conn.prepareCall(query);
            cstmt.setInt(1, 0);
            cstmt.setString(2, "");
            //cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.LONGVARCHAR);
            cstmt.executeUpdate();
            respuesta = cstmt.getInt(1);
            
            System.out.println("AsociarOrdenFACT - respuesta - " + respuesta );
            
            cstmt.close();

        } catch (SQLException ex) {
            System.out.println("AsociarOrdenFACT - ex.getMessage() = " + ex.getMessage());
            Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,"AsociarOrdenFACT -- al ejecutar SP: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                System.out.println("AsociarOrdenFACT - e1.getMessage() = " + e1.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,"AsociarOrdenFACT -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        /*} finally {
            try {
                rs.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("AsociarOrdenFACT - e.getMessage() = " + e.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null, ERROR_CLOSE_BD + e.getMessage());
            }*/
        }

        return respuesta;
    }
    

}
