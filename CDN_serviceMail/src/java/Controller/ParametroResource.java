 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.Parametro;
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
 
 
 
 
 
 
 
 
 
 
 @Path("Parametro")
 public class ParametroResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/*  37 */   public Parametro parametro = new Parametro();
/*  38 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public ParametroResource(@Context HttpHeaders headers) throws Exception {
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
/*  69 */     Parametro param = new Parametro();
/*  70 */     param.select(id, pais);
/*  71 */     return param.hp.getResponse();
   }
 
 
   
   @POST
   @Produces({"application/json"})
   public String postJson(String content) throws JSONException {
/*  79 */     JSONObject params = new JSONObject(content);
/*  80 */     Parametro parametroObj = new Parametro();
     try {
/*  82 */       parametroObj.setNombre(params.getString("nombre"));
/*  83 */       parametroObj.setDescripcion(params.getString("descripcion"));
/*  84 */       parametroObj.setValor(params.getString("valor"));
/*  85 */       parametroObj.setPais(params.getInt("pais"));
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
/* 103 */     Parametro parametroObj = new Parametro();
     try {
/* 105 */       parametroObj.setId(params.getInt("id"));
/* 106 */       parametroObj.setNombre(params.getString("nombre"));
/* 107 */       parametroObj.setDescripcion(params.getString("descripcion"));
/* 108 */       parametroObj.setValor(params.getString("valor"));
/* 109 */       parametroObj.setPais(params.getInt("pais"));
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
/* 123 */     Parametro parametroObj = new Parametro();
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


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ParametroResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */