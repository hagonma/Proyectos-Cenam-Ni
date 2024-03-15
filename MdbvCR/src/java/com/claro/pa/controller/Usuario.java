package com.claro.pa.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
//import org.omg.PortableInterceptor.PolicyFactory;

/**
 *
 * @author Claro
 */
public class Usuario {

    int id, rol, pais, estado;
    String nombre, username, password;
    public ResultSet rs;
    public PreparedStatement ps;
    public JSONArray responseArray;
    public Helper hp = new Helper();
    public ConexionOracle con = new ConexionOracle();

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Jsoup.clean(nombre, Whitelist.basic());
        this.nombre = this.nombre.replaceAll("<[^>]*>", "");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Jsoup.clean(username, Whitelist.basic());
        this.username = this.username.replaceAll("<[^>]*>", "");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Jsoup.clean(password, Whitelist.basic());
        this.password = this.password.replaceAll("<[^>]*>", "");
    }

    public ResultSet select(String id, int pais, int rol, String nombre) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        try {
            con.Conectar();
            String query = "SELECT A.ID,A.NOMBRE,A.USERNAME,A.PAIS_ID,A.ROL_ID,B.NOMBRE AS PAIS,C.NOMBRE AS ROL FROM CDN_USUARIO A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.ID INNER JOIN CDN_ROL C ON A.ROL_ID = C.ID WHERE A.ESTADO=1";
            if (id != null) {
                query += " AND A.ID = " + id;
            }
            if (pais != 0) {
                query += " AND A.PAIS_ID = " + pais;
            }
            if (rol != 0) {
                query += " AND A.ROL_ID = " + rol;
            }
            if (nombre != null) {
                query += " AND A.NOMBRE = " + id;
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
            query = "INSERT INTO CDN_USUARIO(NOMBRE,USERNAME,PAIS_ID,ROL_ID,ESTADO) VALUES (?,?,?,?,?)";
            ps = (PreparedStatement) con.conexion.prepareStatement(query);
            ps.setString(1, this.getNombre());
            ps.setString(2, this.getUsername());
            ps.setInt(3, this.getPais());
            ps.setInt(4, this.getRol());
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
            query = "UPDATE CDN_USUARIO SET NOMBRE=?, USERNAME=?, PAIS_ID=?, ROL_ID=? WHERE ID = ?";
            ps = (PreparedStatement) con.conexion.prepareStatement(query);
            ps.setString(1, this.getNombre());
            ps.setString(2, this.getUsername());
            ps.setInt(3, this.getPais());
            ps.setInt(4, this.getRol());
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
            query = "UPDATE CDN_USUARIO SET ESTADO =?  WHERE ID = ?";
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

    public ResultSet getUser(String nombre, int pais) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        try {
            con.Conectar();
            String query = "SELECT * FROM CDN_USUARIO WHERE ESTADO = 1 AND USERNAME = '" + nombre + "'";
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

    public ResultSet validateURL(String url, int rol) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        try {
            con.Conectar();
            String query = "SELECT * FROM CDN_ACCESO A JOIN CDN_PANTALLA B on A.PANTALLA_ID = B.ID WHERE A.ROL_ID =" + rol + " AND B.URL = '" + url + "' AND A.ESTADO =1";

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
    public String getToken(int idUser) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        String token = null;
        
        try {
            con.Conectar();
            String query = "SELECT ID,USERNAME,TOKEN FROM CDN_USUARIO WHERE ESTADO=1 AND FECHA_HORA_TOKEN > SYSDATE";
            if (idUser != 0) {
                query += " AND ID = " + idUser;
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
                token=rs.getString("TOKEN");
            }
            hp.setArrayResponse(200, responseArray);
            con.CerrarConsulta();
        } catch (SQLException ex) {
            hp.setArrayResponse(400, responseArray);
            con.CerrarConsulta();
        }
        return token;
    }    
    
    public ResultSet setToken(int idUser) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        responseArray = new JSONArray();
        try {
            con.Conectar();
            String query = "UPDATE CDN_USUARIO set TOKEN = ORA_HASH(SYSDATE),FECHA_HORA_TOKEN =(SYSDATE+1) WHERE ID = "+idUser+"";
            rs = con.Consultar(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columna = rsmd.getColumnCount();
            JSONObject rows = new JSONObject();
            responseArray.put(rows);
            hp.setArrayResponse(200, responseArray);
            con.CerrarConsulta();
        } catch (SQLException ex) {
            hp.setArrayResponse(400, responseArray);
            con.CerrarConsulta();
        }
        return rs;
    }  
    

}
