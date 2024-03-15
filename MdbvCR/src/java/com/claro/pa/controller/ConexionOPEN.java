/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pablo.cabrera
 */
public class ConexionOPEN {
   
    //DECLARACION DE VARIABLES NECESARIAS
    public static Connection conexion;
    public static Statement ps;
    public static ResultSet rs;
    public static CallableStatement cstmt;
    public static String ip   = "";
    public static String port = "";
    public static String bd   = "";
    public static String usr  = "";
    public static String pass = "";

    //METODO PARA CONECTAR
    public static Connection Conectar(String pais) throws Exception {

        //LEER PARAMETROS DE CONFIGURACION DE CONEXION
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String driver,url; 
        if(ip==""){
            if(pais.equals("506")){
    
                /*ip = "172.24.4.226";
                port = "1533";
                bd = "SMARTPNM";
                usr = "CDN_PA";
                pass = "N3w_C1aHN$1920";*/
                ip = "172.17.241.217";
                port = "3871";
                bd = "BSCSCRI";
                usr = "TTMAFAUTO";
                pass = "A876D277011712CED";
              
            } 
        }
        
        //String driver, url, ip = "oracledes02-scan", port = "3871", bd = "DMISC", usr = "CDN", pass = "DKfa826Sxi";
        url = new String("jdbc:oracle:thin:@" + ip + ":" + port + "/" + bd);
        try {
            conexion = DriverManager.getConnection(url, usr, pass);
            System.out.println("Conexion a Base de Datos " + ip + " Ok");
            return conexion;
        } catch (Exception exc) {
            //System.out.println("Error al tratar de abrir la base de Datos" + bd + " : " + exc);
            System.out.println("Error al tratar de abrir la base de Datos " + ip + " : " + exc);
            return null;
        }
    }

    //METODO PARA CERRAR LAS CONEXIONES Y OBJETOS DE QUERYS
    public void CerrarConsulta() throws SQLException {
        try {
            //cerrando conexiones	    		
            ps.close();
            rs.close();
            if (ps == null && rs == null) {
                System.out.println("La conexion de consulta fue cerrada");
            }
        } catch (Exception exc) {
            System.out.println("Error al tratar de cerrar las conexiones: " + exc);
        }
    }
    
    public void CerrarConexion() throws SQLException {
        try {
            //cerrando conexiones	    		
            conexion.close();
            conexion = null;
            if (conexion == null) {
                System.out.println("La conexion de consulta fue cerrada");
            }
        } catch (Exception exc) {
            System.out.println("Error al tratar de cerrar las conexiones: " + exc);
        }
    }    

    //METODO PARA EJECUTAR UN QUERY
    public ResultSet Consultar(String SQL) throws SQLException {
        //variables para consulta
        ps = null;
        rs = null;
        //realizar la consulta
        try {
            ps = conexion.prepareStatement(SQL);
            rs = ps.executeQuery(SQL);
            //devolver el resultset
            return rs;
        } catch (Exception exc) {
            System.out.println("Error al tratar de ejecutar el query: " + exc);
            return null;
        }
    }
    
        //METODO PARA EJECUTAR UN QUERY
    public ResultSet ConsultarDesglose(String SQL) throws SQLException {
        //variables para consulta
        ps = null;
        rs = null;
        //realizar la consulta
        ps = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = ps.executeQuery(SQL);
        //devolver el resultset
        return rs;
    }

}
