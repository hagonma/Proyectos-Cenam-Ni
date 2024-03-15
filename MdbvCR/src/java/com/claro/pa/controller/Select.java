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

/**
 *
 * @author AVP GUATEMALA
 */
public class Select {

    int id;
    public ResultSet rs;
    public PreparedStatement ps;
    public JSONArray responseArray;
    public Helper hp = new Helper();
    public ConexionOracle con = new ConexionOracle();
    public Parametro par = new Parametro();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String selectFijo(String id, String pais) throws SQLException, Exception {
        JSONObject jsonObj = new JSONObject();
        cuerpoCorreo body = new cuerpoCorreo();
        responseArray = new JSONArray();
        String response = "";
        if(!id.equals("CARGA_DIARIA")){
            try {
                con.Conectar();
                //String query = "SELECT A.ID CLIENTE, A.FECHA_PAGO PAGO,  B.CODIGO AS IDPAIS, B.ID AS IDPAIS_SMPP, NVL(TRIM(B.MARCACION),0) MARCACION, NVL(TRIM(B.NUM_CC),0) NUM_CC, NVL(TRIM(B.FACTURA),0) FACTURA, NVL(TRIM(B.LINK),0) LINK, TO_CHAR(A.FECHA_CONTRATACION, 'DD/MM/YYYY') AS FECHA_CONTRATACION2,  A.* FROM CDN_MAIL A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.CODIGO";
                String query = "SELECT A.ID AS CLIENTE, NVL(EQUIPO,'0') AS EQUIPO, NVL(CORREO_CLIENTE,'0') AS CORREO_CLIENTE, \n" +
                                "        NVL(A.FECHA_PAGO,'0')  AS PAGO,  \n" +
                                "        NVL(B.CODIGO,'0') AS IDPAIS, \n" +
                                "        NVL(B.ID,'0') AS IDPAIS_SMPP, \n" +
                                "        NVL(TRIM(B.MARCACION),0) MARCACION, \n" +
                                "        NVL(TRIM(B.NUM_CC),0) NUM_CC, \n" +
                                "        NVL(TRIM(B.FACTURA),0) FACTURA, NVL(TRIM(B.LINK),0) LINK, \n" +
                                "        TO_CHAR(A.FECHA_CONTRATACION, 'DD/MM/YYYY') AS FECHA_CONTRATACION,  \n" +
                                "        NVL(A.VIGENCIA_CONTRATO,'0') AS VIGENCIA_CONTRATO ,\n" +
                                "    NVL(SERVICIO,'0') AS SERVICIO,\n" +
                                "    NVL(CUOTA,'0') AS CUOTA,\n" +
                                "    NVL(CLARO_VIDEO,'0') AS CLARO_VIDEO,\n" +
                                "    NVL(INTERNET,'0') AS INTERNET,\n" +
                                "    NVL(LLAMADA,'0') AS LLAMADA ,\n" +
                                "    NVL(TELEVISION,'0') AS TELEVISION,\n" +
                                "    NVL(FOX,'0') AS FOX,\n" +
                                "    NVL(HBO,'0') AS HBO,\n" +
                                "	NVL(NUM_CC,'0') AS NUM_CC,\n" +
                                "	NVL(LINK,'0') AS LINK,\n" +
                                "	NVL(FACTURA,'0') AS FACTURA,\n" +
                                "	NVL(MARCACION,'0') AS MARCACION,\n" +
                                "	NVL(ILIMITADAS_A_CLARO,'0') AS ILIMITADAS_A_CLARO,\n" +
                                "	NVL(TIPO_PLAN_TV,'0') AS TIPO_PLAN_TV,\n" +
                                "	NVL(VELOCIDAD,'0') AS VELOCIDAD,\n" +
                                "	NVL(NUMERO_REFERENCIA,'0') AS NUMERO_REFERENCIA,\n" +
                                "	NVL(PAIS_ID,'0') AS PAIS_ID\n" +
                                "FROM CDN_MAIL A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.CODIGO";
                
                if (id != null) {
                    query += " WHERE B.CODIGO = "+pais+" AND A.ID = " + id ;
                }
                rs = con.Consultar(query);
                //System.out.println(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columna = rsmd.getColumnCount();
                while (rs.next()) {
                    JSONObject rows = new JSONObject();
                    for (int i = 1; i <= columna; i++) {
                        String columnaBD = rsmd.getColumnName(i);
                        rows.put(columnaBD, rs.getString(columnaBD));
                    }
                    responseArray.put(rows);
                    response = body.cuerpoFijoRenovadoPro(rows);
                }
                hp.setArrayResponse(200, responseArray);
                //con.CerrarConsulta();
            } catch (SQLException ex) {
                response = ex.toString();
                hp.setArrayResponse(400, responseArray);
                con.CerrarConsulta();
            }
        }else{
            response = "Primera Carga "+pais+", no se accede a mandar correo.";
        }
        return response;
    }
    
    public String getTMCODE(String id) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        try {
            con.Conectar();
            String query = "SELECT TMCODE FROM CDN_MAIL WHERE ID = "+id;
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("TMCODE");
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
    
    public String getLlamadas(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("LLAMADAS_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getMensajes(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("MENSAJES_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getRedes(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("REDES_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getInternet(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("INTERNET_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getSinFronteras(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("SINFRONTERAS_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getEquipo(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("EQUIPO_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            System.out.println(query);
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getNombrePlan(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("NOMBRE_PLAN", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
    
    public String getClaroMusica(String codigo, String pais) throws SQLException, Exception {
        String dato = "";
        String equipos = "";
        String idLlamada = par.getParametro("CLAROMUSICA_MAIL", pais);
        try {
            con.Conectar();
            String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE="+idLlamada+" AND CODIGO = '"+codigo+"' AND ID_PAIS ="+pais+"";
            ResultSet resultado = con.Consultar(query);

            while (resultado.next()) {
                dato = resultado.getString("DESCRIPCION");
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
