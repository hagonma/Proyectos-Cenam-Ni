 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.Mensaje;
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
 
 
 
 
 
 
 
 
 
 
 
 @Path("Mensaje")
 public class MensajeResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/*  38 */   public Mensaje parametro = new Mensaje();
/*  39 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public MensajeResource(@Context HttpHeaders headers) throws Exception {
/*  45 */     Helper hp = new Helper();
/*  46 */     String token = headers.getRequestHeader("token").get(0);
     
/*  48 */     if (!hp.validateToken(token)) {
/*  49 */       throw new UnsupportedOperationException();
     }
   }
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") int pais, @QueryParam("tipoMensaje") int tipoMensaje) throws Exception {
/*  62 */     Mensaje param = new Mensaje();
/*  63 */     param.select(id, pais, tipoMensaje);
/*  64 */     return param.hp.getResponse();
   }
 
 
   
   @POST
   @Produces({"application/json"})
   public String postJson(String content) throws JSONException {
/*  72 */     JSONObject params = new JSONObject(content);
/*  73 */     Mensaje parametroObj = new Mensaje();
     try {
/*  75 */       parametroObj.setDescripcion(params.getString("nombre"));
/*  76 */       parametroObj.setPais(params.getInt("pais"));
/*  77 */       parametroObj.setTipo_mensaje(params.getInt("tipoMensaje"));
/*  78 */       parametroObj.setEstado(1);
/*  79 */       parametroObj.insert();
/*  80 */       return parametroObj.hp.getResponse();
/*  81 */     } catch (Exception ex) {
/*  82 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
 
 
 
   
   @PUT
   @Consumes({"application/json"})
   public String putJson(String content) throws JSONException {
/*  94 */     JSONObject params = new JSONObject(content);
/*  95 */     Mensaje parametroObj = new Mensaje();
     try {
/*  97 */       parametroObj.setId(params.getInt("id"));
/*  98 */       parametroObj.setDescripcion(params.getString("nombre"));
/*  99 */       parametroObj.setTipo_mensaje(params.getInt("tipoMensaje"));
/* 100 */       parametroObj.setPais(params.getInt("pais"));
/* 101 */       parametroObj.update();
/* 102 */       return parametroObj.hp.getResponse();
/* 103 */     } catch (Exception ex) {
/* 104 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
   
   @DELETE
   @Produces({"application/json"})
   public String deleteJson(String content) throws JSONException {
/* 113 */     JSONObject params = new JSONObject(content);
/* 114 */     Mensaje parametroObj = new Mensaje();
     try {
/* 116 */       parametroObj.setId(params.getInt("id"));
/* 117 */       parametroObj.setEstado(0);
/* 118 */       parametroObj.delete();
/* 119 */       return parametroObj.hp.getResponse();
/* 120 */     } catch (Exception ex) {
/* 121 */       return parametroObj.hp.getResponse();
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\MensajeResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */