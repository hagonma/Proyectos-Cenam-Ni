 package Controller;
 
 import Controller.ConexionOracle;
 import Model.TipoMensaje;
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
 
 
 
 
 
 
 
 
 
 
 
 @Path("TipoMensaje")
 public class TipoMensajeResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/* 33 */   public TipoMensaje parametro = new TipoMensaje();
/* 34 */   public ConexionOracle con = new ConexionOracle();
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id) throws Exception {
/* 50 */     TipoMensaje param = new TipoMensaje();
/* 51 */     param.select(id);
/* 52 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\TipoMensajeResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */