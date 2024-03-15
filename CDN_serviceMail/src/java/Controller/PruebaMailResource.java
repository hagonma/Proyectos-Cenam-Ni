 package Controller;
 
 import Controller.Helper;
 import Model.Select;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("PruebaMail")
 public class PruebaMailResource
 {
   @Context
   private UriInfo context;
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id) throws Exception {
/* 44 */     Helper help = new Helper();
/* 45 */     Select param = new Select();
 
     
     try {
/* 49 */       String result = param.select(id);
/* 50 */       JSONObject job = new JSONObject();
/* 51 */       job.put("Envio", result);
/* 52 */       help.setResponse(200, job);
     }
/* 54 */     catch (Exception e) {
/* 55 */       JSONObject job = new JSONObject();
/* 56 */       job.put("Error ", e);
/* 57 */       help.setResponse(400, job);
     } 
 
     
/* 61 */     return help.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\PruebaMailResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */