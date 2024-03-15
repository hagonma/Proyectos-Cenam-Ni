package com.claro.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.driver.OracleDriver;

/*
        hamilton.gonzalez@claro.com.ni - Dic23 
    */

public class DbModelFACT
{
    public Connection conexion;
    public PreparedStatement ps;
    public ResultSet rs;
    public CallableStatement cstmt;
    
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@192.168.3.103:1525:GAIASV";
    private static final String DB_IP = "192.168.3.103";
    private static final String DB_PORT = "1525";
    private static final String DB_NAME = "GAIASV";
    private static final String DB_USERNAME = "LOCUCION";
    private static final String DB_PASSWORD = "LOCATARIO";
        
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";

    public Connection Conectar() throws Exception {
        
        
        File jar = new File("//home//cobertura-ifi//library//oracle//ojdbc6.jar");
        
        //DriverManager.registerDriver((Driver)new OracleDriver());
            //if (this.ip == "") {
            //String url = new String("jdbc:oracle:thin:@" + this.ip + ":" + this.port + "/" + this.bd);
            try {
                
                URL[] cp = new URL[1];
                cp[0] = jar.toURI().toURL();
                URLClassLoader ora9loader = new URLClassLoader(cp, ClassLoader.getSystemClassLoader());
                Class<?> drvClass = ora9loader.loadClass("oracle.jdbc.driver.OracleDriver");
                Driver ora9driver = (Driver)drvClass.newInstance();
                Properties props = new Properties();
                
                 props.setProperty("user", this.DB_USERNAME);
                 props.setProperty("password", this.DB_PASSWORD);
                 this.conexion = ora9driver.connect("jdbc:oracle:thin:@" + this.DB_IP + ":" + this.DB_PORT + "/" + this.DB_NAME, props);

                //DriverManager.getConnection(url, this.usr, this.pass);
                //this.conexion = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                
                if(this.conexion != null){
			   System.out.println("Conectado correctamente.");
			}else{
			   System.out.println("No se ha podido conectar.");
			}

                return this.conexion;
            } catch (Exception e) {
                System.out.println(ERROR_CLOSE_BD  + e.getMessage());
                return null;
            } 
        //}
    }

    public Connection CerrarConsulta() throws SQLException {
        try {
            this.ps.close();
            this.rs.close();
            this.conexion.close();
            this.conexion = null;
            return this.conexion;
        } catch (Exception exc) {
            return null;
        } 
    }

    public ResultSet Consultar(String SQL) throws SQLException {
        this.ps = null;
        this.rs = null;

        try {
            this.ps = this.conexion.prepareStatement(SQL);
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
        
        String P_CLIENTE       = String.valueOf(root.get("Cliente").getAsString()); 

        String P_TIPOINTERACCION =String.valueOf(root.get("TipoInteraccion").getAsString().equals("Orden")?'I':'A');
         
        //String P_USER_CREATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_create").getAsString()));
        
        Integer P_NOINTERACCION   =root.get("NoInteraccion").getAsInt();
        
        System.out.println("AsociarOrdenFACT - P_NOINTERACCION - " + P_NOINTERACCION );
        
        Double P_LATITUD  	 = Double.parseDouble(root.get("Latitud").getAsString()); 
        Double P_LONGITUD   	 = Double.parseDouble(root.get("Longitud").getAsString());
        
        System.out.println("AsociarOrdenFACT - P_LATITUD - " + P_LATITUD );
        
        String P_TOKEN          =String.valueOf(root.get("Token").getAsString());
        
        //Connection conn;
        //CallableStatement pstmt = null;
        //ResultSet rs = null;
        String query;

        System.out.println("AsociarOrdenFACT - P_TOKEN - " + P_TOKEN );
        
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
            //SELECT Ops$gaia_cc.fn_location_gaia(11741743, 3281811, 2, 1, SYSDATE, 12.4564565, -85.504850, 1, 'I', null) RESP FROM dual
             //query = "SELECT Ops$gaia_cc.fn_location_gaia(?, 3281811, 2, 1, SYSDATE, ?, ?, 1, 'I', null) RESP FROM dual";
              query = "SELECT Ops$gaia_cc.fn_location_gaia("+
                       P_NOINTERACCION+","+ P_CLIENTE+", 2, 1, SYSDATE, "+
                       P_LATITUD+","+ P_LONGITUD+",'"+ P_TOKEN +"', '"+ P_TIPOINTERACCION+"', null) RESP FROM dual";
            
            System.out.println("AsociarOrdenFACT - query - " + query );
            
            this.ps = this.conexion.prepareStatement(query);
            //this.ps.setInt(1, P_NOINTERACCION);
            //this.ps.setDouble(2, P_LATITUD);
            //this.ps.setDouble(3, P_LONGITUD);

            this.rs = this.ps.executeQuery();
            if (this.rs.next()) {
                respuesta = rs.getInt("RESP");
            }
            
            System.out.println("AsociarOrdenFACT - respuesta - " + respuesta );
            
            this.ps.close();

        } catch (SQLException ex) {
            System.out.println("AsociarOrdenFACT - ex.getMessage() = " + ex.getMessage());
            Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,"AsociarOrdenFACT -- al ejecutar SP: " + ex.getMessage());
            try {
                this.conexion.close();
            } catch (SQLException e1) {
                System.out.println("AsociarOrdenFACT - e1.getMessage() = " + e1.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,"AsociarOrdenFACT -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        /*}finally{
            try {
                this.rs.close();
                this.ps.close();
                conexion.close();
            } catch (SQLException e) {
                System.out.println("AsociarOrdenFACT - e.getMessage() = " + e.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }*/
        }

        return respuesta;
    }
    
}

