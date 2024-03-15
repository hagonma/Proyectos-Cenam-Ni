 package Controller;
 
 import Controller.Helper;
 import Model.ReporteBitacora;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.HttpHeaders;
 import javax.ws.rs.core.UriInfo;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("ReporteBitacora")
 public class ReporteBitacoraResource
 {
   @Context
   private UriInfo context;
   
   public ReporteBitacoraResource(@Context HttpHeaders headers) throws Exception {
/* 35 */     Helper hp = new Helper();
     
/* 37 */     String token = headers.getRequestHeader("token").get(0);
/* 38 */     String uri = headers.getRequestHeader("Referer").get(0);
/* 39 */     int rol = Integer.parseInt(headers.getRequestHeader("rol").get(0));
/* 40 */     int startIndex = uri.indexOf("N");
/* 41 */     String url = uri.substring(startIndex + 2);
     
/* 43 */     if (!hp.validateToken(token)) {
/* 44 */       throw new UnsupportedOperationException();
     }
/* 46 */     if (!hp.validateURL(url, rol)) {
/* 47 */       throw new UnsupportedOperationException();
     }
   }
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") int pais) throws Exception {
/* 59 */     ReporteBitacora param = new ReporteBitacora();
/* 60 */     param.select(id, pais);
/* 61 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ReporteBitacoraResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */