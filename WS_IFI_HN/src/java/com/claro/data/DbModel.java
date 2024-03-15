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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Marvin Urias
 */
public class DbModel {
    
    private static final String DSSPNAME = "jdbc/consulta-mapa-HN";
    private static final String ETIQUETA_VACIO = "<vacio>";
    private static final String QUERY_GET_PARAMETRO = "SELECT VALOR FROM TBL_IFI_PARAMETROS "
                                                        + "WHERE NOMBRE = ? "
                                                        + "AND PAIS_ID = ?";
    private static final String ERROR_CALL_FUNCTION = "Error al llamar la funcion PL/SQL. ";
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";
    private DataSource dataSource;
    private static final String QUERY_GET_ID_SERVICE = "SELECT SERVICE_TYPE_ID VALOR FROM TB_IFI_SERVICE_TYPE "
                                                        + "WHERE SERVICE_TYPE_CODE = ?";
    private static final String QUERY_GET_GPON_RESP = "SELECT REPLACE(VALOR,'[*FECHA_CONSULT*]',TO_CHAR(SYSDATE,'DD/MM/YYYY HH24:MI:SS')) VALOR FROM TBL_IFI_PARAMETROS "
                                                        + "WHERE NOMBRE = ? "
                                                        + "AND PAIS_ID = ?";
    
    public DbModel(){
        //Contructor vacio
    }
    
    private void initialize() {
        //Gestionar la conexion a la base de datos
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            this.dataSource = (javax.sql.DataSource) ctx.lookup(DbModel.DSSPNAME);
        } catch (javax.naming.NamingException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Sistema de precios -- Error al configurar la conexion a base de datos " + DbModel.DSSPNAME + " error: " + e.getMessage());
        }
    }
    
    public Connection getConnection(){
        this.initialize();
        try {
            return this.dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"Error al obtener conexion a base de datos " + DbModel.DSSPNAME + " error: " + ex.getMessage());
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /*
        Recuperar dominio de LDAP segun pais
     */
    public String getDominioLdap(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dominio = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "DOMINIO_LDAP");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dominio = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return dominio;
    }

    /*
        Recuperar DNS LDAP segun pais
     */
    public String getDnsLdap(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dominio = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "DNS_LDAP");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            rs.next();
            dominio = rs.getString("VALOR");
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return dominio;
    }

    public boolean validarUsuario(String usuario) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean valido = false;

        this.initialize();
        
        conn = getConnection();

        try {
            String query = "SELECT TB.\"USER\" FROM TB_IFI_USERS TB "
                    + "WHERE TB.\"USER\" = ? AND TB.STATE_ID = 2";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usuario);
            rs = pstmt.executeQuery();
            if(rs.next()){
                valido = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return valido;
    }
    
    public String getRoleUser(String usuario) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Role = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT R.ROLE_CODE FROM TB_IFI_USERS U INNER JOIN TB_IFI_ROLES R ON U.ROLE_ID=R.ROLE_ID "
                    + "WHERE U.\"USER\"= ? ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usuario.trim());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Role = rs.getString("ROLE_CODE");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return Role;
    }
    
    public UserLDAP getValidateUserLDAP(String usuario, int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserLDAP user = new UserLDAP();

        this.initialize();
        conn = getConnection();

        try {
            String query = "select RESPONSE, TRIM(INITCAP(FIRST_NAME))||' '||TRIM(INITCAP(LAST_NAME)) NOMBRE_COMPLETO, MAIL "
                    + "from table(gu_ldap_search(?,?)) ";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pais);
            pstmt.setString(2, usuario.trim());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setResponse(rs.getString("RESPONSE"));
                user.setNombre_completo(rs.getString("NOMBRE_COMPLETO"));
                user.setMail(rs.getString("MAIL"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return user;
    }
    
    public String getRoles() {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String Roles = "[{ \"roleId\": \"0\", \"roleName\": \"Seleccione un rol\" }";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT R.ROLE_ID, R.ROLE_NAME FROM TB_IFI_ROLES R WHERE STATE_ID=2 ORDER BY R.ROLE_ID ";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Roles = Roles+",{ \"roleId\": \""+rs.getString("ROLE_ID")+"\", \"roleName\": \""+rs.getString("ROLE_NAME")+"\" }";
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        System.out.println("Roles:"+Roles);
        return Roles+"]";
    }
    
    public String getPages() {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String webPages = "[";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT P.WEB_PAGE_ID, P.WEB_PAGE_NAME FROM TB_IFI_WEB_PAGES P WHERE P.STATE_ID=2 ORDER BY P.WEB_PAGE_ID ";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                webPages = webPages+"{ \"pageId\": \""+rs.getString("WEB_PAGE_ID")+"\", \"pageName\": \""+rs.getString("WEB_PAGE_NAME")+"\" },";
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        //System.out.println("Roles:"+Roles);
        int l=webPages.length();
        return webPages.substring(0, l-1)+"]";
    }
    
    public String getUsers(String pUser, String cadena) {
        String user=DecryptCryptoJs.decrypt(pUser);
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String FIELD_FILTER=String.valueOf(root.get("search").getAsJsonObject().get("value").getAsString());
        double START_REG=Double.valueOf(root.get("start").getAsString())+1;
        double LARGO=Double.valueOf(root.get("length").getAsString());
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String users = "{\"ID\":\"\", \"USER\":\"\",\"NAME\":\"\",\"EMAIL\":\"\",\"ID_ROLE\":\"\",\"ROLE_NAME\":\"\",\"ID_STATE\":\"\",\"NAME_STATE\":\"\",\"ID_COUNTRY\":\"\",\"NAME_COUNTRY\":\"\",\"CREATION_DATE\":\"\",\"USER_C\":\"\",\"LAST_CHANGE_DATE\":\"\",\"USER_U\":\"\"}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_USERS(?,?,?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, FIELD_FILTER);
            pstmt.setDouble(3, START_REG);
            pstmt.setDouble(4, LARGO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                users = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return users;
    }
    
    
    public String getDistanciaMax(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String distancia = "2";

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "DISTANCIA_MAX");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                distancia = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return distancia;
    }
    
    public String getDistanciaMaxHz(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String distancia = "2";

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "DISTANCIA_MAX_HZ");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                distancia = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return distancia;
    }
    
    public String getMedidaDistanciaMax(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String medida = "K";

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "MEDIDA_DIST_MAX");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                medida = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return medida;
    }
    
    public String getPathKml(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String path = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "PATH_KML");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                path = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return path;
    }
    
    public String getParam(String KEY, int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String path = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, KEY);
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                path = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return path;
    }
    
    public String getEstadoNoProcedeVenta(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String estado = "22";

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "ESTADO_NO_PROCEDE_VENTA");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                estado = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return estado;
    }
    
    public String getMsgNoProcedeVenta(int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String msg = "No procede venta";

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_PARAMETRO;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "MSG_NO_PROCEDE_VENTA");
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                msg = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return msg;
    }
    
    public String regUser(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_USER_NEW       =  String.valueOf(root.get("user_new").getAsString());
        String P_USER_CREATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_create").getAsString()));
        String P_PAIS           =  String.valueOf(root.get("country").getAsString());
        double P_ROL            =  Double.parseDouble(String.valueOf(root.get("role").getAsString()));
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al crear usuario.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_REG_USER (?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER_NEW);
            pstmt.setString(2, P_USER_CREATE);
            pstmt.setString(3, P_PAIS);
            pstmt.setDouble(4, P_ROL);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CREAR USUARIO IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CREAR USUARIO IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String updateUser(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_USER           =  String.valueOf(root.get("user").getAsString());
        String P_USER_UPDATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_update").getAsString()));
        double P_ROL            =  Double.parseDouble(String.valueOf(root.get("role").getAsString()));
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al actualizar usuario.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_UPDATE_USER (?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_USER_UPDATE);
            pstmt.setDouble(3, P_ROL);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"ACTUALIZAR USUARIO IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"ACTUALIZAR USUARIO IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String deleteUser(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_USER           =  String.valueOf(root.get("user").getAsString());
        String P_USER_UPDATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_update").getAsString()));
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al dar de baja al usuario.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_DELETE_USER (?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_USER_UPDATE);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"BAJA USUARIO IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"BAJA USUARIO IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String getValidateKey( String cadena) {
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_KEY=String.valueOf(root.get("key").getAsString());

        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String validate ="{\"code\":-1,\"msg\":\"Clave invalida\"}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_VALIDATE_KEY(?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_KEY);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                validate = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return validate;
    }
    
    public String getValidateAccess( String cadena) {
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_USUARIO=DecryptCryptoJs.decrypt(String.valueOf(root.get("user").getAsString()));  
        String P_PAGE=String.valueOf(root.get("page").getAsString());
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String validate ="{\"code\":-1,\"msg\":\"Acceso denegado\"}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_VALIDATE_ACCESS(?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USUARIO);
            pstmt.setString(2, P_PAGE);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                validate = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return validate;
    }

    public String getRoles(String pUser, String cadena) {
        String user=DecryptCryptoJs.decrypt(pUser);
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String FIELD_FILTER=String.valueOf(root.get("search").getAsJsonObject().get("value").getAsString());
        double START_REG=Double.valueOf(root.get("start").getAsString())+1;
        double LARGO=Double.valueOf(root.get("length").getAsString());
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String roles = "{\"ID_ROLE\":\"\",\"ROLE_CODE\":\"\",\"ROLE_NAME\":\"\",\"ID_STATE\":\"\",\"NAME_STATE\":\"\",\"CREATION_DATE\":\"\",\"USER_C\":\"\",\"LAST_CHANGE_DATE\":\"\",\"USER_U\":\"\"}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_ROLES(?,?,?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, FIELD_FILTER);
            pstmt.setDouble(3, START_REG);
            pstmt.setDouble(4, LARGO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                roles = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return roles;
    }
    
    public String updateRol(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        double P_ROL_ID         =  Double.parseDouble(String.valueOf(root.get("role").getAsString()));
        String P_USER_UPDATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_update").getAsString()));
        String P_ROL_CODE       =  String.valueOf(root.get("code").getAsString());
        String P_ROL_NAME       =  String.valueOf(root.get("name").getAsString());
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al actualizar rol.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_UPDATE_ROL (?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, P_ROL_ID);
            pstmt.setString(2, P_USER_UPDATE);
            pstmt.setString(3, P_ROL_CODE);
            pstmt.setString(4, P_ROL_NAME);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"ACTUALIZAR ROL IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"ACTUALIZAR ROL IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String deleteRol(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        double P_ROL_ID         =  Double.parseDouble(String.valueOf(root.get("role").getAsString()));
        String P_USER_UPDATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_update").getAsString()));
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al dar de baja al rol.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_DELETE_ROL (?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, P_ROL_ID);
            pstmt.setString(2, P_USER_UPDATE);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"BAJA ROL IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"BAJA ROL IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String regRol(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_USER_CREATE    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("user_create").getAsString()));
        String P_ROLE_CODE      =  String.valueOf(root.get("code").getAsString());
        String P_ROLE_NAME      =  String.valueOf(root.get("name").getAsString());
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al crear rol.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_REG_ROL (?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER_CREATE);
            pstmt.setString(2, P_ROLE_CODE);
            pstmt.setString(3, P_ROLE_NAME);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CREAR ROL IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CREAR ROL IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String getRolWebPages(String pUser, String cadena) {
        String user=DecryptCryptoJs.decrypt(pUser);
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        double rol=Double.valueOf(root.get("rol").getAsString());
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String rolWebPages = "{\"roles\":[]}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_PAGES_ROL(?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setDouble(2, rol);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rolWebPages = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return rolWebPages;
    }
    
    public String accessRolWebPagesPermit(String pUser, String pRole, String cadena) {
        String user=DecryptCryptoJs.decrypt(pUser);
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        JsonArray roles = (JsonArray) root.get("roles");
        
        Iterator<JsonElement> iterator = roles.iterator();
        String pagesConcat="";
         while (iterator.hasNext()) {
           pagesConcat+=iterator.next().toString().replace("\"", "")+",";
         }
         
         int l=pagesConcat.length();
         String pages="";
         
         if(l>0){
             pages=pagesConcat.substring(0, l-1);
         }
         
         String accessRolWebPages = "{\"estado\":\"-1\",\"msg\":\"Error al conceder accesos al rol\"}";
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_ACCES_PAGES_ROL(?,?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, pRole);
            pstmt.setString(3, pages);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                accessRolWebPages = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        
        return accessRolWebPages;
    }
    
    public String confirmCRM(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String P_TOKEN=String.valueOf(root.get("token").getAsString());
        String P_COUNTRY_CODE=String.valueOf(root.get("country").getAsString());
        String P_KEY=String.valueOf(root.get("key").getAsString());
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al confirmar.\"}";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_CONFIRM_CRM (?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_TOKEN);
            pstmt.setString(2, P_COUNTRY_CODE);
            pstmt.setString(3, P_KEY);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CONFIRMAR CRM ROL IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CONFIRMAR CRM ROL IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String getValidateCellsHZ(String VAR_INTERACTION, double distanciaMax, ArrayList<cell> c3G, ArrayList<cell> c4G, double VERIFY_TYPE, Long msisdn) {
        
        String cells="";
         for (cell c: c3G){
           cells+=c.getCgi()+",";
         }
         
         for (cell c: c4G){
           cells+=c.getCgi()+",";
         }
         
         int l=cells.length();
         
         if(l>0){
             cells=cells.substring(0, l-1);
         }
         
        String validateCellsHZ = "{\"code\":\"-1\"}";
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_VALID_CELL_HZ(?,?,?,?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, VAR_INTERACTION);
            pstmt.setDouble(2, distanciaMax);
            pstmt.setString(3, cells);
            pstmt.setDouble(4, VERIFY_TYPE);
            pstmt.setDouble(5, msisdn);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                validateCellsHZ = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        
        return validateCellsHZ;
    }
    
    public String regLogApp(String P_DESC, String P_LOG_APP_TYPE_CODE , String P_COUNTRY_ID) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "-1";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_REG_LOG_APP (?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_DESC);
            pstmt.setString(2, P_LOG_APP_TYPE_CODE);
            pstmt.setString(3, P_COUNTRY_ID);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "REGISTRAR LOG APP IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "REGISTRAR LOG APP IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String getTokenUserSession(String P_USER, String P_COUNTRY_ID) {

        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "-1";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_USER_SESSION (?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_COUNTRY_ID);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "OBTENER TOKEN DE SESION IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "OBTENER TOKEN DE SESION IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String isUserLogged(String P_USERD, String P_TOKEN, String P_COUNTRY_ID) {
        
        String P_USER=DecryptCryptoJs.decrypt(P_USERD);
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "false";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_isUserLogged (?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_TOKEN);
            pstmt.setString(3, P_COUNTRY_ID);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "isUserLogged IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "isUserLogged IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String CloseSessionUserLogged(String P_USERD, String P_TOKEN, String P_COUNTRY_ID) {
        
        String P_USER=DecryptCryptoJs.decrypt(P_USERD);
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "false";

        conn = getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_CloseSessionUserLogged (?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_TOKEN);
            pstmt.setString(3, P_COUNTRY_ID);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "CloseSessionUserLogged IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, "CloseSessionUserLogged IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String getServiceCode(String CODE) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String path = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_ID_SERVICE;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, CODE);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                path = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return path;
    }
    
    public String getGponResp(String KEY, int pais) {
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String path = ETIQUETA_VACIO;

        this.initialize();
        conn = getConnection();

        try {
            String query = QUERY_GET_GPON_RESP;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, KEY);
            pstmt.setInt(2, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                path = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return path;
    }
    
    public String getlastUpdatedFiles( String countryId) {
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String val ="{\"kmlFiles\":[]}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_LAST_UPLOADED_FILES(?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, countryId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                val = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return val;
    }
    
    public String getOptionsConsult(String sitio) {
         Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String val ="{\"kmlFiles\":[]}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_OPTIONS_CONSULT(?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, sitio);
            pstmt.setString(2, "504");

            rs = pstmt.executeQuery();
            if (rs.next()) {
                val = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return val;
    }
    
    public String getExecCambioCliente(Long msisdn, String token, String tipo_cambio, String orden, String pais) {
            
        String execCambioCliente = "{\"code\":\"-1\"}";
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_EXEC_CHANGE_CLI(?,?,?,?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, msisdn);
            pstmt.setString(2, token);
            pstmt.setString(3, tipo_cambio);
            pstmt.setString(4, orden);
            pstmt.setString(5, pais);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                execCambioCliente = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        
        return execCambioCliente;
    }
      
    public String getKmlManchas() {
         Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String val ="{\"kmlLayers\":[]}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_KML_LAYERS(?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "504");

            rs = pstmt.executeQuery();
            if (rs.next()) {
                val = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return val;
    }
    
    public String getReportToken(String pUser, String cadena) {
        String user=DecryptCryptoJs.decrypt(pUser);
        
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        String FIELD_FILTER=String.valueOf(root.get("search").getAsJsonObject().get("value").getAsString());
        double START_REG=Double.valueOf(root.get("start").getAsString())+1;
        double LARGO=Double.valueOf(root.get("length").getAsString());
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String report = "{\"REG_DATE\":\"\",\"COORDENADA\":\"\",\"TECNOLOGIA\":\"\",\"PROCEDE_VENTA\":\"\",\"MOTIVO\":\"\",\"SERVICE_TYPE_NAME\":\"\",\"LUGAR_O_DEPARTAMENTO\":\"\",\"USER_NAME\":\"\",\"USER\":\"\",\"POLIGONO\":\"\",\"NUMERO_VENDIDO\":\"\"}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT FN_IFI_GET_REPORT_BY_TOKEN(?,?,?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, FIELD_FILTER);
            pstmt.setDouble(3, START_REG);
            pstmt.setDouble(4, LARGO);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                report = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return report;
    }
    
    public String AsociarOrdenIFI(String cadena) {
        JsonParser parser= new JsonParser();
        JsonObject root = parser.parse(cadena).getAsJsonObject();
        
        Connection conexion;
        this.initialize();
        conexion = getConnection();
        
        String P_CLIENTE       = String.valueOf(root.get("Cliente").getAsString()); 
        String P_COBERTURA      = String.valueOf(root.get("Cobertura").getAsString());
        String P_DIRECCION  	= String.valueOf(root.get("Direccion").getAsString()); 
        String P_LATITUD  	 = String.valueOf(root.get("Latitud").getAsString()); 
        String P_LONGITUD   	 = String.valueOf(root.get("Longitud").getAsString());
        String P_MENSAJE  	 = String.valueOf(root.get("Mensaje").getAsString());   
        String P_NOINTERACCION   =String.valueOf(root.get("NoInteraccion").getAsString());
        String P_TECNOLOGIA   	= String.valueOf(root.get("Tecnologia").getAsString());
        String P_TIPOINTERACCION =String.valueOf(root.get("TipoInteraccion").getAsString());
        String P_TOKEN          =String.valueOf(root.get("Token").getAsString());
        String P_USER    =  DecryptCryptoJs.decrypt(String.valueOf(root.get("Usuario").getAsString()));
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "{\"estado\":\"-1\",\"msg\":\"Error al asociar la orden.\"}";

        //****************************************
        //Crear la orden en el facturador
        //DbModelFACT dbManager = new DbModelFACT();
        //String orden = dbManager.AsociarOrdenIFI(cadena);
        //****************************************

        /*if(orden!=null)
        {*/
            try {
                //Obtener todas las cargas propuestas
                query = "SELECT FN_IFI_GEOLOCALIZACION_FACT (?,?,?,?,?,?,?,?,?,?,?) RESP FROM DUAL";
                pstmt = conexion.prepareStatement(query);
                pstmt.setString(1, P_CLIENTE);
                pstmt.setString(2, P_COBERTURA);
                pstmt.setString(3, P_DIRECCION);
                pstmt.setString(4, P_LATITUD);
                pstmt.setString(5, P_LONGITUD);
                pstmt.setString(6, P_MENSAJE);
                pstmt.setString(7, P_NOINTERACCION);
                pstmt.setString(8, P_TECNOLOGIA);
                pstmt.setString(9, P_TIPOINTERACCION);
                pstmt.setString(10, P_TOKEN);
                pstmt.setString(11, P_USER);                

                rs = pstmt.executeQuery();

                //Agregar los resultados a listado de cargas
                if (rs.next()) {
                    respuesta = rs.getString("RESP");
                }

            } catch (SQLException ex) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CREAR USUARIO IFI -- error consulta base: " + ex.getMessage());
                try {
                    conexion.close();
                } catch (SQLException e1) {
                    Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,"CREAR USUARIO IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
                }
            }finally{
                try {
                    rs.close();
                    pstmt.close();
                    conexion.close();
                } catch (SQLException e) {
                    Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
                }
            }
        //} 
        return respuesta;
    }
    
    public String ObtenerToken(String pais, String tecnologia) {
        
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String token = "{\"token\":\"Error al crear el token\"}";

        this.initialize();
        conn = getConnection();

        try {
            String query = "SELECT GET_IFI_GEOFACT_TOKEN(?,?) VALOR FROM DUAL ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, pais);
            pstmt.setString(2, tecnologia);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                token = rs.getString("VALOR");
            }
        } catch (SQLException e) {
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CALL_FUNCTION + e.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }
        return token;
    }
}
