 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.Usuario;
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
 
 
 
 
 
 
 
 
 
 
 @Path("Usuario")
 public class UsuarioResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/*  37 */   public Usuario parametro = new Usuario();
/*  38 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public UsuarioResource(@Context HttpHeaders headers) throws Exception {
/*  44 */     Helper hp = new Helper();
     
/*  46 */     String token = headers.getRequestHeader("token").get(0);
/*  47 */     String uri = headers.getRequestHeader("Referer").get(0);
/*  48 */     int rol = Integer.parseInt(headers.getRequestHeader("rol").get(0));
/*  49 */     int startIndex = uri.indexOf("N");
/*  50 */     String url = uri.substring(startIndex + 2);
     
/*  52 */     if (!hp.validateToken(token)) {
/*  53 */       throw new UnsupportedOperationException();
     }
/*  55 */     if (!hp.validateURL(url, rol)) {
/*  56 */       throw new UnsupportedOperationException();
     }
   }
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") int pais, @QueryParam("rol") int rol, @QueryParam("nombre") String nombre) throws Exception {
/*  69 */     Usuario param = new Usuario();
/*  70 */     param.select(id, pais, rol, nombre);
/*  71 */     return param.hp.getResponse();
   }
 
 
   
   @POST
   @Produces({"application/json"})
   public String postJson(String content) throws JSONException {
/*  79 */     JSONObject params = new JSONObject(content);
/*  80 */     Usuario parametroObj = new Usuario();
     try {
/*  82 */       parametroObj.setNombre(params.getString("nombre"));
/*  83 */       parametroObj.setUsername(params.getString("username"));
/*  84 */       parametroObj.setPais(params.getInt("pais"));
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
/* 103 */     Usuario parametroObj = new Usuario();
     try {
/* 105 */       parametroObj.setId(params.getInt("id"));
/* 106 */       parametroObj.setNombre(params.getString("nombre"));
/* 107 */       parametroObj.setUsername(params.getString("username"));
/* 108 */       parametroObj.setPais(params.getInt("pais"));
/* 109 */       parametroObj.setRol(params.getInt("rol"));
/* 110 */       parametroObj.update();
/* 111 */       return parametroObj.hp.getResponse();
/* 112 */     } catch (Exception ex) {
/* 113 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
   
   @DELETE
   @Produces({"application/json"})
   public String deleteJson(String content) throws JSONException {
/* 122 */     JSONObject params = new JSONObject(content);
/* 123 */     Usuario parametroObj = new Usuario();
     try {
/* 125 */       parametroObj.setId(params.getInt("id"));
/* 126 */       parametroObj.setEstado(0);
/* 127 */       parametroObj.delete();
/* 128 */       return parametroObj.hp.getResponse();
/* 129 */     } catch (Exception ex) {
/* 130 */       return parametroObj.hp.getResponse();
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\UsuarioResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */