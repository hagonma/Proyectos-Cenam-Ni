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
    
     private static final String DB_DRIVER = 
		           "oracle.jdbc.driver.OracleDriver";
        private static final String DB_URL = 
                    "jdbc:oracle:thin:@172.24.35.133:1525:SMARTHN";
            private static final String DB_IP = "172.24.35.133";
    private static final String DB_PORT = "1525";
    private static final String DB_NAME = "SMARTHN";
    private static final String DB_USERNAME = "PORT_COBERTURA";
    private static final String DB_PASSWORD = "Ene$%2024";
       
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";

    public Connection Conectar() throws Exception {
        
        //DriverManager.registerDriver((Driver)new OracleDriver());
         File jar = new File("//home//cobertura-ifi//library//oracle//ojdbc6.jar");

        //if (this.ip == "") {
            //String url = new String("jdbc:oracle:thin:@" + this.ip + ":" + this.port + "/" + this.bd);
            try {
                //DriverManager.getConnection(url, this.usr, this.pass);
                /*this.conexion = DriverManager.
			getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);*/
                
                URL[] cp = new URL[1];
                cp[0] = jar.toURI().toURL();
                URLClassLoader ora9loader = new URLClassLoader(cp, ClassLoader.getSystemClassLoader());
                Class<?> drvClass = ora9loader.loadClass("oracle.jdbc.driver.OracleDriver");
                Driver ora9driver = (Driver)drvClass.newInstance();
                Properties props = new Properties();
                
                 props.setProperty("user", this.DB_USERNAME);
                 props.setProperty("password", this.DB_PASSWORD);
                 this.conexion = ora9driver.connect("jdbc:oracle:thin:@" + this.DB_IP + ":" + this.DB_PORT + "/" + this.DB_NAME, props);
                
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
        
        //String P_USER_CREATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_create").getAsString()));
        
        String P_CONTRATO  	 = String.valueOf(root.get("Contrato").getAsString());
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
        V_LATITUD: Valor que almacena la Latitud del contrato.
        V_LONGITUD: Valor que almacena la Longitud del contrato.
        V_CONTRATO: Numero de contrato correspondiente a la ubicación geográfica proporcionada
        V_SOLICITUD: Numero de solicitud generada en el facturador
        V_TOKEN:
        V_RESULTADO: Variable que devuelve el resultado de la ejecución del procedimiento (EXITO, SOLICITUD NO VALIDA, NO_DATA_FOUND, OTHERS)
        **/

        try {
            //Obtener todas las cargas propuestas
            /*query = "DECLARE\n" +
                    "V_RESULTADO VARCHAR2 (100) := '';\n" +
                    "BEGIN\n" +
                    "SMART.SER_UBICACION_GEOGRAFIC("+P_LATITUD+", "
                                                    +P_LONGITUD+", "
                                                    +P_NOINTERACCION+", "
                                                    +P_NOINTERACCION+", '"
                                                    +P_TOKEN+"', V_RESULTADO);\n" +
                    "END;";*/
            
            query = "{ ? = " +
                      "call SMART.F_SER_UBICACION_GEOGRAFIC("+P_LATITUD+", "+P_LONGITUD+", "+P_NOINTERACCION+","+P_CONTRATO+", '"+P_TOKEN+"')" +
                    " }\n";
             
            System.out.println("AsociarOrdenFACT - query - " + query );
            
            this.cstmt = this.conexion.prepareCall(query);
            //this.cstmt.setInt(1, 0);
            //this.cstmt.setString(1, "");
            this.cstmt.registerOutParameter(1, Types.INTEGER);
            this.cstmt.executeUpdate();
            respuesta = Integer.valueOf(this.cstmt.getString(1));
            
            System.out.println("AsociarOrdenFACT - respuesta - " + respuesta );
            
            this.cstmt.close();

        } catch (SQLException ex) {
            System.out.println("AsociarOrdenFACT - ex.getMessage() = " + ex.getMessage());
            Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,"CREAR ORDEN FACT -- error consulta base: " + ex.getMessage());
            try {
                conexion.close();
            } catch (SQLException e1) {
                System.out.println("AsociarOrdenFACT - e1.getMessage() = " + e1.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,"CREAR ORDEN FACT -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }/*finally{
            try {
                rs.close();
                this.cstmt.close();
                conexion.close();
            } catch (SQLException e) {
                System.out.println("AsociarOrdenFACT - e.getMessage() = " + e.getMessage());
                Logger.getLogger(DbModelFACT.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }*/

        return respuesta;
    }
    
}

