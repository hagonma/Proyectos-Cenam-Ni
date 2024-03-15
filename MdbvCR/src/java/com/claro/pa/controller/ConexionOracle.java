/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.controller;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

public class ConexionOracle {

    //DECLARACION DE VARIABLES NECESARIAS
    public static Connection conexion;
    public static PreparedStatement ps;
    public static ResultSet rs;
    public static CallableStatement cstmt;
    public static String ip   = "";
    public static String port = "";
    public static String bd   = "";
    public static String usr  = "";
    public static String pass = "";
    public static String error = "";

    //METODO PARA CONECTAR
    public static Connection Conectar() throws Exception {

        //LEER PARAMETROS DE CONFIGURACION DE CONEXION
        
        // Oracle 9 connection
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String url; 
                /*ip = "10.218.41.115";
                port = "3871";
                bd = "DMISC";
                usr = "CDN"; 
                pass = "DKfa826Sxi"; */
                /*ip = "oracleprd05-scan";
                port = "3871";
                bd = "CDNRG";
                usr = "CDNRG";
                pass = "C1@r0cdn#";*/
                /*ip = "oracledes02-scan";
                port = "3871";
                bd = "DMISC";
                usr = "MACTPRE";
                pass = "asdowe25ds";*/
                ip = "172.18.125.181";
                port = "3871";
                bd = "CDNRG";
                usr = "CDNRG";
                pass = "C1@r0cdn#";
                
            //String driver, url, ip = "oracledes02-scan", port = "3871", bd = "DMISC", usr = "CDN", pass = "DKfa826Sxi";
            url = new String("jdbc:oracle:thin:@" + ip + ":" + port + "/" + bd);
            try {
                conexion = DriverManager.getConnection(url, usr, pass);
                //System.out.println("Conexion a Base de Datos " + ip + " Ok");
                return conexion;
            } catch (Exception exc) {
                System.out.println("Error al tratar de abrir la base de Datos" + bd + " : " + exc);
               // System.out.println("Error al tratar de abrir la base de Datos" + ip + " : " + exc);
                return null;
            }
        }
    

    //METODO PARA CERRAR LAS CONEXIONES Y OBJETOS DE QUERYS
   public void CerrarConsulta() throws SQLException {
        try {
            //cerrando conexiones	    		
            ps.close();
            rs.close();
            conexion.close();
            conexion = null;
        //    if (conexion == null) {
          //      System.out.println("La conexion de consulta fue cerrada");
            //}
        } catch (Exception exc) {
         //   System.out.println("Error al tratar de cerrar las conexiones: " + exc);
        }
    }

    //METODO PARA EJECUTAR UN QUERY
    public ResultSet Consultar(String SQL) {
        //variables para consulta
        ps = null;
        rs = null;
        //realizar la consulta
        try {
            //System.out.println(SQL);
            ps = conexion.prepareStatement(SQL);
            rs = ps.executeQuery();
            //devolver el resultset
            return rs;
        } catch (SQLException ex) {
            System.err.println("Error en "+ConexionOracle.class.getName()+" "+ex.getMessage());
            Logger.getLogger(ConexionOracle.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

}
