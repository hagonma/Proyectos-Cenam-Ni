 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.Pantalla;
 import java.sql.ResultSet;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.HttpHeaders;
 import javax.ws.rs.core.UriInfo;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 @Path("Pantalla")
 public class PantallaResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/* 34 */   public Pantalla parametro = new Pantalla();
/* 35 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public PantallaResource(@Context HttpHeaders headers) throws Exception {
/* 41 */     String token = headers.getRequestHeader("token").get(0);
/* 42 */     Helper hp = new Helper();
/* 43 */     if (!hp.validateToken(token)) {
/* 44 */       throw new UnsupportedOperationException();
     }
   }
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id) throws Exception {
/* 57 */     Pantalla param = new Pantalla();
/* 58 */     param.select(id);
/* 59 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\PantallaResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */