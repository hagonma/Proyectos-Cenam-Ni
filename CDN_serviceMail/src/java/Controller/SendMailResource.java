 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.ConsultasBSCS;
 import Model.Select;
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
 
 
 
 
 
 
 
 
 
 
 @Path("SendMail")
 public class SendMailResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/* 34 */   public Select parametro = new Select();
/* 35 */   public ConexionOracle con = new ConexionOracle();
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") String pais) throws Exception {
/* 51 */     Helper help = new Helper();
/* 52 */     ConsultasBSCS conBSCS = new ConsultasBSCS();
/* 53 */     Select param = new Select();
     
     try {
/* 56 */       conBSCS.nombrePlan(id);
/* 57 */       conBSCS.correoclienteBSCS(id);
       
/* 59 */       switch (pais) {
         
         case "502":
/* 62 */           conBSCS.montoBSCSGT(id);
           break;
         case "503":
/* 65 */           conBSCS.montoBSCSSV(id);
           break;
         
         case "504":
/* 69 */           conBSCS.montoBSCSHN(id);
           break;
         case "505":
/* 72 */           conBSCS.montoBSCSNI(id);
           break;
       } 
 
       
/* 77 */       String result = param.select(id);
/* 78 */       JSONObject job = new JSONObject();
/* 79 */       job.put("Envio", result);
/* 80 */       help.setResponse(200, job);
     }
/* 82 */     catch (Exception e) {
/* 83 */       JSONObject job = new JSONObject();
/* 84 */       job.put("Error ", e);
/* 85 */       help.setResponse(400, job);
     } 
 
     
/* 89 */     return help.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\SendMailResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */