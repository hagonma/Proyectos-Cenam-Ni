/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import com.claro.logic.LdapLogic;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.claro.beans.UserBean;
import com.claro.beans.UserLDAP;
import com.claro.data.DbModel;
import com.claro.data.DbModelFACT;
import com.claro.data.DecryptCryptoJs;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Marvin Urias
 */
@Path("check")
public class CheckResource {

    @Context
    private UriInfo context;
    

    public CheckResource() {
        //Constructor vacio
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("logIn")
    public String getJson(@QueryParam("user") String user, @QueryParam("pass") String pass) {
        LdapLogic ldap = new LdapLogic();
        //return String.valueOf(ldap.validate(user, pass));
        return "";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Path("/{id}")
    @Path("HelloPost")
    public Response HelloPost(String cadena) {
        System.out.println(cadena);
        return Response.status(200).entity(cadena).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("logIn")
    public String validateUser(String cadena) {
        Gson parser = new Gson();
        UserBean dataUser = parser.fromJson(cadena, UserBean.class);
        LdapLogic ldap = new LdapLogic();
        String resultado = "{\"code\":0,\"msg\":\"invalido\"}";
        String respuesta = ldap.validate(dataUser.getUser(), dataUser.getPass(), dataUser.getCountry());
        if ("1".equals(respuesta)) {
            String user= DecryptCryptoJs.encrypt(dataUser.getUser());
            DbModel dbManager = new DbModel();
            String Token = dbManager.getTokenUserSession(dataUser.getUser(), "505");
            if (Token.equals("-2")){
                resultado = "{\"code\":\"-2\",\"msg\":\"Alerta. El usuario posee una sesión abierta.\"}";
            } else if (Token.equals("-3")){
                resultado = "{\"code\":\"-3\",\"msg\":\"Error el usuario no existe.\"}";
            } else if (Token.equals("-1")){
                resultado = "{\"code\":\"-1\",\"msg\":\"Error general por favor comunicarse con el administrador.\"}";
            } else{
                resultado = "{\"code\":\"1\",\"msg\":\"valido\",\"token\":\""+Token+"\",\"user\":\""+user+"\"}";
            }
        }else{
            resultado = "{\"code\":\""+respuesta+"\",\"msg\":\""+ldap.getMessage(respuesta)+"\"}";
        }
        return resultado;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createValidateUser")
    public String createValidateUser(String cadena) {
        Gson parser = new Gson();
        UserBean dataUser = parser.fromJson(cadena, UserBean.class);
        DbModel dbManager = new DbModel();
        UserLDAP user = dbManager.getValidateUserLDAP(dataUser.getUser(),dataUser.getCountry());
        String resultado = "{\"code\":0,\"msg\":\""+user.getResponse()+"\"}";
        if(user.getResponse().equalsIgnoreCase("Successful Registration")){
            resultado = "{\"code\":1,\"response\":\""+user.getResponse()+"\",\"nombre\":\""+user.getNombre_completo()+"\",\"mail\":\""+user.getMail()+"\"}";
        }
        return resultado;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("roles")
    public String getRoles() {
        DbModel dbManager = new DbModel();
        String roles = dbManager.getRoles();
        return roles;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pages")
    public String getPages() {
        DbModel dbManager = new DbModel();
        String roles = dbManager.getPages();
        return roles;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users/{userId}")
    public String getUsers(@PathParam("userId") String user, String cadena) {
    try{        
        //System.out.println("cadena:"+cadena);
        DbModel dbManager = new DbModel();
        String usuarios = dbManager.getUsers(user, cadena);
        //System.out.println("usuarios:"+usuarios);
        return usuarios;
    } catch(Exception ex){
        return "{\"ID\":\"\", \"USER\":\"\",\"NAME\":\"\",\"EMAIL\":\"\",\"ID_ROLE\":\"\",\"ROLE_NAME\":\"\",\"ID_STATE\":\"\",\"NAME_STATE\":\"\",\"ID_COUNTRY\":\"\",\"NAME_COUNTRY\":\"\",\"CREATION_DATE\":\"\",\"USER_C\":\"\",\"LAST_CHANGE_DATE\":\"\",\"USER_U\":\"\"}";
    }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create-user")
    public String createUser(String cadena) {
        DbModel dbManager = new DbModel();
        String usuario = dbManager.regUser(cadena);
        return usuario;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update-user")
    public String updateUser(String cadena) {
        DbModel dbManager = new DbModel();
        String usuario = dbManager.updateUser(cadena);
        return usuario;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete-user")
    public String deleteUser(String cadena) {
        DbModel dbManager = new DbModel();
        String usuario = dbManager.deleteUser(cadena);
        return usuario;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("validateKey")
    public String validateKey(String cadena) {
        //System.out.println("cadena:"+cadena);
        DbModel dbManager = new DbModel();
        String validate = dbManager.getValidateKey(cadena);
        return validate;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("validateAccessPage")
    public String validateAccessPage(String cadena) {
        try{
        //System.out.println("cadena:"+cadena);
        DbModel dbManager = new DbModel();
        String validate = dbManager.getValidateAccess(cadena);
        return validate;
        } catch(Exception ex){
            return "{\"code\":-1,\"msg\":\"Acceso denegado\"}";
        }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("roles/{userId}")
    public String getRoles(@PathParam("userId") String user, String cadena) {
    try{
        //System.out.println("cadena:"+cadena);
        DbModel dbManager = new DbModel();
        String roles = dbManager.getRoles(user, cadena);
        //System.out.println("usuarios:"+usuarios);
        return roles;
    } catch(Exception e){
        return "{\"ID_ROLE\":\"\",\"ROLE_CODE\":\"\",\"ROLE_NAME\":\"\",\"ID_STATE\":\"\",\"NAME_STATE\":\"\",\"CREATION_DATE\":\"\",\"USER_C\":\"\",\"LAST_CHANGE_DATE\":\"\",\"USER_U\":\"\"}";
    }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("create-rol")
    public String createRol(String cadena) {
        DbModel dbManager = new DbModel();
        String usuario = dbManager.regRol(cadena);
        return usuario;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update-rol")
    public String updateRol(String cadena) {
        DbModel dbManager = new DbModel();
        String usuario = dbManager.updateRol(cadena);
        return usuario;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete-rol")
    public String deleteRol(String cadena) {
        DbModel dbManager = new DbModel();
        String usuario = dbManager.deleteRol(cadena);
        return usuario;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("rol_web_pages/{userId}")
    public String getRolWebPages(@PathParam("userId") String user, String cadena) {
        //System.out.println("cadena:"+cadena);
        DbModel dbManager = new DbModel();
        String roles = dbManager.getRolWebPages(user, cadena);
        //System.out.println("usuarios:"+usuarios);
        return roles;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("rol_web_pages_permit/{userId}/{roleId}")
    public String accessRolWebPagespermit(@PathParam("userId") String user,@PathParam("roleId") String role, String cadena) {
        //System.out.println("cadena:"+cadena);
        DbModel dbManager = new DbModel();
        String roles = dbManager.accessRolWebPagesPermit(user,role, cadena);
        //System.out.println("roles:"+roles);
        return roles;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("confirmCRM")
    public String confirmCRM(String cadena) {
        DbModel dbManager = new DbModel();
        String confirmCRM = dbManager.confirmCRM(cadena);
        return confirmCRM;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("isUserLogged/{userId}/{token}")
    public String isUserLogged(@PathParam("userId") String user,@PathParam("token") String token) {
        try{
        DbModel dbManager = new DbModel();
        String isUserLogged = dbManager.isUserLogged(user, token, "505");
        return isUserLogged;
        } catch(Exception e){
            return "false";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("CloseSessionUserLogged/{userId}/{token}")
    public String CloseSessionUserLogged (@PathParam("userId") String user,@PathParam("token") String token) {
        try{
        DbModel dbManager = new DbModel();
        String CloseSessionUserLogged  = dbManager.CloseSessionUserLogged (user, token, "505");
        return CloseSessionUserLogged ;
        } catch(Exception e){
            return "false";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("urlAPIMap")
    public String urlAPIMap() {
        DbModel dbManager = new DbModel();
        String url = DecryptCryptoJs.encrypt(dbManager.getParam("API_URL_GOOGLE_MAPS",505));
        return "\""+url+"\""; 
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("keyAPIMap")
    public String keyAPIMap() {
        DbModel dbManager = new DbModel();
        //System.out.println(dbManager.getParam("API_KEY_GOOGLE_MAPS",505));
        String key = DecryptCryptoJs.encrypt(dbManager.getParam("API_KEY_GOOGLE_MAPS",505));
        return "\""+key+"\"";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("options_consult_web")
    public String getOptionsConsultWeb() {
        DbModel dbManager = new DbModel();
        String options = dbManager.getOptionsConsult("WEB");
        return options;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("options_consult_crm")
    public String getOptionsConsultCrm() {
        DbModel dbManager = new DbModel();
        String options = dbManager.getOptionsConsult("CRM");
        return options;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("kml_manchas_cobertura")
    public String getKmlManchas() {
        DbModel dbManager = new DbModel();
        String KmlLayers = dbManager.getKmlManchas();
        return KmlLayers;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("options_consult_web_cobert_fija")
    public String getOptionsConsultCobertFija() {
        DbModel dbManager = new DbModel();
            String options = dbManager.getOptionsConsult("COBERTURA_FIJA");
        return options;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("reportToken/{userId}")
    public String reportToken(@PathParam("userId") String user, String cadena) {
        try{
            //System.out.println("cadena:"+cadena);
            DbModel dbManager = new DbModel();
            String report = dbManager.getReportToken(user, cadena);
            //System.out.println("usuarios:"+usuarios);
            return report;
        } catch(Exception e){
            return "{\"REG_DATE\":\"\",\"COORDENADA\":\"\",\"TECNOLOGIA\":\"\",\"PROCEDE_VENTA\":\"\",\"MOTIVO\":\"\",\"SERVICE_TYPE_NAME\":\"\",\"LUGAR_O_DEPARTAMENTO\":\"\",\"USER_NAME\":\"\",\"USER\":\"\",\"POLIGONO\":\"\",\"NUMERO_VENDIDO\":\"\"}";
        }
    }
    
    /*
        hamilton.gonzalez@claro.com.ni - Dic23 
    */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("asociar-orden-fact")
    public String asociarOrdenFACT(String cadena) throws Exception {
        String asociarIFI = "{\"estado\":\"1\",\"msg\":\"SOLICITUD NO VALIDA.\"}";
        //try {
            //****************************************
            //Crear la orden en el facturador
            DbModelFACT AOFACT = new DbModelFACT();
            AOFACT.Conectar();
            Integer asociarFACT = AOFACT.AsociarOrdenFACT(cadena);
           //****************************************            
            
            System.out.println("Resultado Asociar Orden FACT .. " + asociarFACT);
            
            if(asociarFACT==0)
                asociarIFI = "{\"estado\":\"0\",\"msg\":\"La orden fue asociada correctamente.\"}";
            
        /*} catch (Exception e) {
            System.out.println("Error al asociar orden .. " + e.getMessage());
        }finally{
            System.out.println("Error al asociar orden .. ");
        }*/
        
        
        return asociarIFI;
    }
    
    /*
        hamilton.gonzalez@claro.com.ni - Dic23 
    */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("asociar-orden-ifi")
    public String asociarOrdenIFI(String cadena) throws Exception {
        String asociarIFI = "{\"estado\":\"1\",\"msg\":\"Error al asociar la orden.\"}";
        //try {
            //****************************************
            //Crear la orden en el facturador
          
                DbModel dbManager = new DbModel();
                asociarIFI = dbManager.AsociarOrdenIFI(cadena);
                System.out.println("Insertando en consulta IFI .. " + asociarIFI);
       
            
        /*} catch (Exception e) {
            System.out.println("Error al asociar orden .. " + e.getMessage());
        }finally{
            System.out.println("Error al asociar orden .. ");
        }*/
        
        return asociarIFI;
    }
    
    /*
        hamilton.gonzalez@claro.com.ni - Dic23 
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("obtener-token/{pais}/{tecno}")
    public String obtenerToken (@PathParam("pais") String pais,@PathParam("tecno") String tecno) {
    //public String obtenerToken(String pais, String tecno) {
        DbModel dbManager = new DbModel();
        //DbModelFACT dbManager = new DbModelFACTBK();
        String token = dbManager.ObtenerToken(pais, tecno);
        return token;
    }
}
