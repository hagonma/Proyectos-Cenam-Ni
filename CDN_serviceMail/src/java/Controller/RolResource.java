 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.Rol;
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
 
 
 
 
 
 
 
 
 
 
 @Path("Rol")
 public class RolResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/*  37 */   public Rol parametro = new Rol();
/*  38 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public RolResource(@Context HttpHeaders headers) throws Exception {
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
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") int pais) throws Exception {
/*  69 */     Rol param = new Rol();
/*  70 */     param.select(id, pais);
/*  71 */     return param.hp.getResponse();
   }
 
 
   
   @POST
   @Produces({"application/json"})
   public String postJson(String content) throws JSONException {
/*  79 */     JSONObject params = new JSONObject(content);
/*  80 */     Rol parametroObj = new Rol();
     try {
/*  82 */       parametroObj.setNombre(params.getString("nombre"));
/*  83 */       parametroObj.setPais(params.getInt("pais"));
/*  84 */       parametroObj.setEstado(1);
/*  85 */       parametroObj.insert();
/*  86 */       return parametroObj.hp.getResponse();
/*  87 */     } catch (Exception ex) {
/*  88 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
 
 
 
   
   @PUT
   @Consumes({"application/json"})
   public String putJson(String content) throws JSONException {
/* 100 */     JSONObject params = new JSONObject(content);
/* 101 */     Rol parametroObj = new Rol();
     try {
/* 103 */       parametroObj.setId(params.getInt("id"));
/* 104 */       parametroObj.setNombre(params.getString("nombre"));
/* 105 */       parametroObj.setPais(params.getInt("pais"));
/* 106 */       parametroObj.update();
/* 107 */       return parametroObj.hp.getResponse();
/* 108 */     } catch (Exception ex) {
/* 109 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
   
   @DELETE
   @Produces({"application/json"})
   public String deleteJson(String content) throws JSONException {
/* 118 */     JSONObject params = new JSONObject(content);
/* 119 */     Rol parametroObj = new Rol();
     try {
/* 121 */       parametroObj.setId(params.getInt("id"));
/* 122 */       parametroObj.setEstado(0);
/* 123 */       parametroObj.delete();
/* 124 */       return parametroObj.hp.getResponse();
/* 125 */     } catch (Exception ex) {
/* 126 */       return parametroObj.hp.getResponse();
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\RolResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */