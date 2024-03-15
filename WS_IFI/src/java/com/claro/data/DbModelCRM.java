/*
 * Informacion general de la base de datos
 */
package com.claro.data;

import com.claro.beans.UserLDAP;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Marvin Urias
 */
public class DbModelCRM {
    
    private static final String DSSPNAME = "jdbc/crm-gt";
    private DataSource dataSource;
    private DbModel base;
    
    public DbModelCRM(){
        //Contructor vacio
        this.base = new DbModel();
    }
    
    private void initialize() {
        //Gestionar la conexion a la base de datos
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            this.dataSource = (javax.sql.DataSource) ctx.lookup(DbModelCRM.DSSPNAME);
        } catch (javax.naming.NamingException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"WS_IFI -- Error al configurar la conexion a base de datos " + DbModelCRM.DSSPNAME + " error: " + e.getMessage());
        }
    }
    
    public Connection getConnection(){
        this.initialize();
        try {
            return this.dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Error al obtener conexion a base de datos " + DbModelCRM.DSSPNAME + " error: " + ex.getMessage());
            Logger.getLogger(DbModelCRM.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String getOrden(String msisdn) {
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String orden = "-1";

        this.initialize();
        conn = getConnection();

        try {
            String query = base.getParam("QUERY_CRM_GET_ORDEN_HZ",Integer.parseInt("502"));
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, msisdn);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                orden = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Error al llamar la funcion PL/SQL." + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Error al cerrar conexion a la base de datos" + e.getMessage());
            }
        }
        return orden;
    }
 
}
