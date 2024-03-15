 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.Acceso;
 import java.sql.ResultSet;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.DELETE;
 import javax.ws.rs.GET;
 import javax.ws.rs.POST;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.HttpHeaders;
 import javax.ws.rs.core.UriInfo;
 import org.json.JSONException;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 @Path("Acceso")
 public class AccesoResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/*  37 */   public Acceso parametro = new Acceso();
/*  38 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public AccesoResource(@Context HttpHeaders headers) throws Exception {
/*  44 */     Helper hp = new Helper();
     
/*  46 */     String token = headers.getRequestHeader("token").get(0);
/*  47 */     String uri = headers.getRequestHeader("Referer").get(0);
/*  48 */     int rol = Integer.parseInt(headers.getRequestHeader("rol").get(0));
/*  49 */     int startIndex = uri.indexOf("N");
/*  50 */     String url = uri.substring(startIndex + 2);
 
 
     
/*  54 */     if (!hp.validateToken(token)) {
/*  55 */       throw new UnsupportedOperationException();
     }
/*  57 */     if (!hp.validateURL(url, rol)) {
/*  58 */       throw new UnsupportedOperationException();
     }
   }
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("rol") int rol, @QueryParam("pantalla") int pantalla) throws Exception {
/*  71 */     Acceso param = new Acceso();
/*  72 */     param.select(id, rol, pantalla);
/*  73 */     return param.hp.getResponse();
   }
 
 
   
   @POST
   @Produces({"application/json"})
   public String postJson(String content) throws JSONException {
/*  81 */     JSONObject params = new JSONObject(content);
/*  82 */     Acceso parametroObj = new Acceso();
     try {
/*  84 */       parametroObj.setPantalla(params.getInt("pantalla"));
/*  85 */       parametroObj.setRol(params.getInt("rol"));
/*  86 */       parametroObj.setEstado(1);
/*  87 */       parametroObj.insert();
/*  88 */       return parametroObj.hp.getResponse();
/*  89 */     } catch (Exception ex) {
/*  90 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
 
 
 
   
   @PUT
   @Consumes({"application/json"})
   public String putJson(String content) throws JSONException {
/* 102 */     JSONObject params = new JSONObject(content);
/* 103 */     Acceso parametroObj = new Acceso();
     try {
/* 105 */       parametroObj.setId(params.getInt("id"));
/* 106 */       parametroObj.setPantalla(params.getInt("pantalla"));
/* 107 */       parametroObj.setRol(params.getInt("rol"));
/* 108 */       parametroObj.update();
/* 109 */       return parametroObj.hp.getResponse();
/* 110 */     } catch (Exception ex) {
/* 111 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
   
   @DELETE
   @Produces({"application/json"})
   public String deleteJson(String content) throws JSONException {
/* 120 */     JSONObject params = new JSONObject(content);
/* 121 */     Acceso parametroObj = new Acceso();
     try {
/* 123 */       parametroObj.setId(params.getInt("id"));
/* 124 */       parametroObj.setEstado(0);
/* 125 */       parametroObj.delete();
/* 126 */       return parametroObj.hp.getResponse();
/* 127 */     } catch (Exception ex) {
/* 128 */       return parametroObj.hp.getResponse();
     } 
   }
 }


