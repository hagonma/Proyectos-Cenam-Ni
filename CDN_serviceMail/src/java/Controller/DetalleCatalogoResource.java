 package Controller;
 
 import Controller.ConexionOracle;
 import Model.DetalleCatalogo;
 import java.sql.ResultSet;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("DetalleCatalogo")
 public class DetalleCatalogoResource
 {
   @Context
   private UriInfo context;
/* 32 */   public ConexionOracle con = new ConexionOracle();
   public ResultSet rs;
   public JSONObject response;
/* 35 */   public DetalleCatalogo parametro = new DetalleCatalogo();
 
 
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id) throws Exception {
/* 53 */     DetalleCatalogo param = new DetalleCatalogo();
/* 54 */     param.select(id);
/* 55 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\DetalleCatalogoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */