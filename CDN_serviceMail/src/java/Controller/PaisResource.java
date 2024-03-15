 package Controller;
 
 import Controller.ConexionOracle;
 import Model.Pais;
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
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("Pais")
 public class PaisResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/* 34 */   public Pais parametro = new Pais();
/* 35 */   public ConexionOracle con = new ConexionOracle();
 
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id) throws Exception {
/* 52 */     Pais param = new Pais();
/* 53 */     param.select(id);
/* 54 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\PaisResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */