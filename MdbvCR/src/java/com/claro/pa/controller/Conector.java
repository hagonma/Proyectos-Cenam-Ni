/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.controller;

/**
 *
 * @author AVP PROYECTOS
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Conector {

    String IP, USER, PASS, PORT, SERVICE_NAME;
    Connection c1;

    public Conector(String ip, String user, String password, String port, String service_name) {
        this.IP = ip;
        this.USER = user;
        this.PASS = password;
        this.PORT = port;
        this.SERVICE_NAME = service_name;
    }

    public void conectar() {

        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
            c1 = DriverManager.getConnection("jdbc:oracle:thin:@" + IP + ":" + PORT + "/" + SERVICE_NAME, USER, PASS);

            System.out.println("Conectado");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void desconectar() {

        try {
            this.c1.commit();
            this.c1.close();
            System.out.println("Desconectado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void Transacciones(String SQL) {

        try {
            this.conectar();
            Statement st = c1.createStatement();
            st.executeUpdate(SQL);
            System.out.println("Transaccion exitosa");
            this.desconectar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void rollBCK() {

        try {
            this.c1.rollback();
        } catch (Exception ex) {

        }
    }

    public String[][] consultas(String SQL) {
        String[][] datos = null;
        int filas = 0, columnas = 0, i = 0;

        try {

            Statement st = c1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(SQL);
            ResultSetMetaData rsmd = rs.getMetaData();
            columnas = rsmd.getColumnCount();
            rs.last();
            filas = rs.getRow();
            rs.beforeFirst();

            datos = new String[filas][columnas];

            while (rs.next()) {

                for (int j = 0; j < columnas; j++) {
                    datos[i][j] = rs.getString(j + 1);
                }

                i++;

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.desconectar();
        }

        return datos;
    }

}
