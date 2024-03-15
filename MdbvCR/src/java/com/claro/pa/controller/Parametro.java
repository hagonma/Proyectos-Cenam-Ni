/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author Claro
 */
public class Parametro {

    String nombre, descripcion, valor;
    int id, pais, estado;
    public ResultSet rs;
    public PreparedStatement ps;
    public JSONArray responseArray;
    public Helper hp = new Helper();
    public ConexionOracle con = new ConexionOracle();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Jsoup.clean(nombre, Whitelist.basic());
        this.nombre = this.nombre.replaceAll("<[^>]*>", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Jsoup.clean(descripcion, Whitelist.basic());
        this.descripcion = this.descripcion.replaceAll("<[^>]*>", "");
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = Jsoup.clean(valor, Whitelist.basic());
        this.valor = this.valor.replaceAll("<[^>]*>", "");
    }

    public int getPais() {
        return pais;
    }

    public void setPais(int pais) {
        this.pais = pais;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ResultSet select(String id, int pais) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        try {
            con.Conectar();
            String query = "SELECT A.ID,A.NOMBRE,A.DESCRIPCION,A.VALOR,A.PAIS_ID,A.ESTADO,B.NOMBRE AS PAIS FROM CDN_PARAMETRO A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.ID WHERE A.ESTADO=1";
            if (id != null) {
                query += " AND A.ID = " + id;
            }
            if (pais != 0) {
                query += " AND A.PAIS_ID = " + pais;
            }
            rs = con.Consultar(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columna = rsmd.getColumnCount();

            while (rs.next()) {
                JSONObject rows = new JSONObject();
                for (int i = 1; i <= columna; i++) {
                    String columnaBD = rsmd.getColumnName(i);
                    rows.put(columnaBD, rs.getString(columnaBD));
                }
                responseArray.put(rows);
            }
            hp.setArrayResponse(200, responseArray);
            con.CerrarConsulta();
        } catch (SQLException ex) {
            hp.setArrayResponse(400, responseArray);
            con.CerrarConsulta();
        }
        return rs;
    }

    public int insert() throws Exception, SQLException {
        String query;
        int retorno = 0;
        JSONObject jsonObj = new JSONObject();
        try {
            con.Conectar();
            query = "INSERT INTO CDN_PARAMETRO(NOMBRE,DESCRIPCION,VALOR,PAIS_ID,ESTADO) VALUES (?,?,?,?,?)";
            ps = (PreparedStatement) con.conexion.prepareStatement(query);
            ps.setString(1, this.getNombre());
            ps.setString(2, this.getDescripcion());
            ps.setString(3, this.getValor());
            ps.setInt(4, this.getPais());
            ps.setInt(5, this.getEstado());

            int executar = ps.executeUpdate();
            retorno = executar;
            con.conexion.close();
            jsonObj.put("TransactSQL", true);
            hp.setResponse(200, jsonObj);
        } catch (SQLException ex) {
            con.conexion.close();
            jsonObj.put("ErrorSQL", ex.getMessage());
            hp.setResponse(400, jsonObj);
            retorno = 0;
        }
        return retorno;
    }

    public int update() throws Exception, SQLException {
        String query;
        int retorno = 0;
        JSONObject jsonObj = new JSONObject();
        try {
            con.Conectar();
            query = "UPDATE CDN_PARAMETRO SET NOMBRE=?, DESCRIPCION=?, VALOR=?, PAIS_ID=? WHERE ID = ?";
            ps = (PreparedStatement) con.conexion.prepareStatement(query);
            ps.setString(1, this.getNombre());
            ps.setString(2, this.getDescripcion());
            ps.setString(3, this.getValor());
            ps.setInt(4, this.getPais());
            ps.setInt(5, this.getId());
            int executar = ps.executeUpdate();
            retorno = executar;
            con.conexion.close();
            jsonObj.put("TransactSQL", true);
            hp.setResponse(200, jsonObj);
        } catch (SQLException ex) {
            con.conexion.close();
            jsonObj.put("ErrorSQL", ex.getMessage());
            hp.setResponse(400, jsonObj);
            retorno = 0;
        }
        return retorno;
    }

    public int delete() throws Exception {
        String query;
        int retorno = 0;
        JSONObject jsonObj = new JSONObject();
        try {
            con.Conectar();
            query = "UPDATE CDN_PARAMETRO SET ESTADO =?  WHERE ID = ?";
            ps = (PreparedStatement) con.conexion.prepareStatement(query);
            ps.setInt(1, this.getEstado());
            ps.setInt(2, this.getId());
            int executar = ps.executeUpdate();
            retorno = executar;
            con.conexion.close();
            jsonObj.put("TransactSQL", true);
            hp.setResponse(200, jsonObj);
        } catch (SQLException ex) {
            con.conexion.close();
            jsonObj.put("ErrorSQL", ex.getMessage());
            hp.setResponse(400, jsonObj);
            retorno = 0;
        }
        return retorno;
    }

    public ResultSet getByName(String nombre, int pais) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        try {
            con.Conectar();
            String query = "SELECT * FROM CDN_PARAMETRO WHERE ESTADO = 1 AND NOMBRE = '" + nombre + "'";
            if (pais != 0) {
                query += " AND PAIS_ID = " + pais;
            }
            rs = con.Consultar(query); 
            ResultSetMetaData rsmd = rs.getMetaData();
            int columna = rsmd.getColumnCount();

            while (rs.next()) {
                JSONObject rows = new JSONObject();
                for (int i = 1; i <= columna; i++) {
                    String columnaBD = rsmd.getColumnName(i);
                    rows.put(columnaBD, rs.getString(columnaBD));
                }
                responseArray.put(rows); 
            }
            hp.setArrayResponse(200, responseArray);
            con.CerrarConsulta();
        } catch (SQLException ex) {
            hp.setArrayResponse(400, responseArray);
            con.CerrarConsulta();
        } 
        return rs;
    }
    
    public String getParametro(String nombre, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        try {
            con.Conectar();
            String query = "SELECT VALOR FROM CDN_PARAMETRO WHERE NOMBRE = '"+nombre+"' AND PAIS_ID = '"+pais+"'";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("VALOR");
            }
            if (dato.isEmpty()) {
                equipos = "0";
            } else {
                equipos = dato;
            }
            con.CerrarConsulta();
            return equipos;
        } catch (SQLException e) {
            con.CerrarConsulta();
            return null;
        }
    }

}
