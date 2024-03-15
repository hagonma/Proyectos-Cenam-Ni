 package Controller;
 
 import Controller.ConexionOracle;
 import Model.Catalogo;
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
 
 
 
 
 
 
 
 
 
 
 
 @Path("Catalogo")
 public class CatalogoResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/*  38 */   public Usuario parametro = new Usuario();
/*  39 */   public ConexionOracle con = new ConexionOracle();
 
 
 
 
 
 
 
 
 
 
 
 
   
   public CatalogoResource(@Context HttpHeaders headers) throws Exception {}
 
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("idCatalogo") int idCatalogo, @QueryParam("pais") int pais, @QueryParam("idDetalle") int idDetalle, @QueryParam("codigo") String codigo) throws Exception {
/*  70 */     Catalogo param = new Catalogo();
/*  71 */     param.select(idCatalogo, pais, idDetalle, codigo);
/*  72 */     return param.hp.getResponse();
   }
 
 
   
   @POST
   @Produces({"application/json"})
   public String postJson(String content) throws JSONException {
/*  80 */     JSONObject params = new JSONObject(content);
/*  81 */     Catalogo parametroObj = new Catalogo();
     try {
/*  83 */       parametroObj.setCODIGO(params.getString("codigo"));
/*  84 */       parametroObj.setID_DETALLE(params.getInt("idDetalle"));
/*  85 */       parametroObj.setDESCRIPCION(params.getString("descripcion"));
/*  86 */       parametroObj.setESTADO(1);
/*  87 */       parametroObj.setID_PAIS(params.getInt("pais"));
/*  88 */       parametroObj.insert();
/*  89 */       return parametroObj.hp.getResponse();
/*  90 */     } catch (Exception ex) {
/*  91 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
 
 
 
   
   @PUT
   @Consumes({"application/json"})
   public String putJson(String content) throws JSONException {
/* 103 */     JSONObject params = new JSONObject(content);
/* 104 */     Catalogo parametroObj = new Catalogo();
     try {
/* 106 */       parametroObj.setID_CATALOGO(params.getInt("idCatalogo"));
/* 107 */       parametroObj.setCODIGO(params.getString("codigo"));
/* 108 */       parametroObj.setID_DETALLE(params.getInt("idDetalle"));
/* 109 */       parametroObj.setDESCRIPCION(params.getString("descripcion"));
/* 110 */       parametroObj.setID_PAIS(params.getInt("pais"));
/* 111 */       parametroObj.update();
/* 112 */       return parametroObj.hp.getResponse();
/* 113 */     } catch (Exception ex) {
/* 114 */       return parametroObj.hp.getResponse();
     } 
   }
 
 
   
   @DELETE
   @Produces({"application/json"})
   public String deleteJson(String content) throws JSONException {
/* 123 */     JSONObject params = new JSONObject(content);
/* 124 */     Catalogo parametroObj = new Catalogo();
     try {
/* 126 */       parametroObj.setID_CATALOGO(params.getInt("idCatalogo"));
/* 127 */       parametroObj.setESTADO(0);
/* 128 */       parametroObj.delete();
/* 129 */       return parametroObj.hp.getResponse();
/* 130 */     } catch (Exception ex) {
/* 131 */       return parametroObj.hp.getResponse();
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\CatalogoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */