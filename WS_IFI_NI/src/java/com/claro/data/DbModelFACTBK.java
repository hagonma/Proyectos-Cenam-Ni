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
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author hamilton.gonzalez @Dic23
 */
public class DbModelFACTBK {
    
    private static final String DSSPNAME = "IFI-FACTURADOR-NI";
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";
    private DataSource dataSource;
    
    public DbModelFACTBK(){
        //Contructor vacio
    }
    
    private void initialize() {
        //Gestionar la conexion a la base de datos
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            this.dataSource = (javax.sql.DataSource) ctx.lookup(DbModelFACTBK.DSSPNAME);
        } catch (javax.naming.NamingException e) {
            Logger.getLogger(DbModelFACTBK.class.getName()).log(Level.SEVERE, null,"Sistema de precios -- Error al configurar la conexion a base de datos " + DbModelFACTBK.DSSPNAME + " error: " + e.getMessage());
        }
    }
    
    public Connection getConnection(){
        this.initialize();
        try {
            return this.dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DbModelFACTBK.class.getName()).log(Level.SEVERE, null,"Error al obtener conexion a base de datos " + DbModelFACTBK.DSSPNAME + " error: " + ex.getMessage());
            Logger.getLogger(DbModelFACTBK.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Integer AsociarOrdenFACT(String cadena) {
        
        Connection conn;
        CallableStatement cstmt = null;
        ResultSet rs = null;
       
        this.initialize();
        conn = getConnection();
        
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
                      + "SMART.ENI_PR_UBICACION_GEOGRAFICA("
                                               +P_NOINTERACCION+", "
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
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.executeUpdate();
            respuesta = cstmt.getInt(1);
            
            System.out.println("AsociarOrdenFACT - respuesta - " + respuesta );
            
            cstmt.close();

        } catch (SQLException ex) {
            System.out.println("AsociarOrdenFACT - ex.getMessage() = " + ex.getMessage());
            Logger.getLogger(DbModelFACTBK.class.getName()).log(Level.SEVERE, null,"AsociarOrdenFACT -- al ejecutar SP: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                System.out.println("AsociarOrdenFACT - e1.getMessage() = " + e1.getMessage());
                Logger.getLogger(DbModelFACTBK.class.getName()).log(Level.SEVERE, null,"AsociarOrdenFACT -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        /*} finally {
            try {
                rs.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("AsociarOrdenFACT - e.getMessage() = " + e.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null, ERROR_CLOSE_BD + e.getMessage());
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
