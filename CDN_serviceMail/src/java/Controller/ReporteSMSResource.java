 package Controller;
 
 import Controller.ConexionOracle;
 import Model.ReporteSMS;
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
 
 
 
 
 
 
 
 
 
 
 @Path("ReporteSMS")
 public class ReporteSMSResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/* 34 */   public ReporteSMS parametro = new ReporteSMS();
/* 35 */   public ConexionOracle con = new ConexionOracle();
 
 
 
   
   public ReporteSMSResource(@Context HttpHeaders headers) throws Exception {
/* 41 */     Helper hp = new Helper();
     
/* 43 */     String token = headers.getRequestHeader("token").get(0);
/* 44 */     String uri = headers.getRequestHeader("Referer").get(0);
/* 45 */     int rol = Integer.parseInt(headers.getRequestHeader("rol").get(0));
/* 46 */     int startIndex = uri.indexOf("N");
/* 47 */     String url = uri.substring(startIndex + 2);
     
/* 49 */     if (!hp.validateToken(token)) {
/* 50 */       throw new UnsupportedOperationException();
     }
/* 52 */     if (!hp.validateURL(url, rol)) {
/* 53 */       throw new UnsupportedOperationException();
     }
   }
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") int pais, @QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin, @QueryParam("keyword") String keyword) throws Exception {
/* 65 */     ReporteSMS param = new ReporteSMS();
/* 66 */     param.select(id, pais, fechaInicio, fechaFin, keyword);
/* 67 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ReporteSMSResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */