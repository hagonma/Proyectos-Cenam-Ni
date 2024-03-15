 package Controller;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Model.ConsultasBSCSCR;
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
 
 
 
 
 
 
 
 
 
 
 
 @Path("SendMailCR")
 public class SendMailCRResource
 {
   @Context
   private UriInfo context;
   public ResultSet rs;
   public JSONObject response;
/* 35 */   public Select parametro = new Select();
/* 36 */   public ConexionOracle con = new ConexionOracle();
 
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("pais") String pais) throws Exception {
/* 53 */     Select param = new Select();
/* 54 */     ConsultasBSCSCR conBSCS = new ConsultasBSCSCR();
/* 55 */     conBSCS.correoclienteBSCS(id);
/* 56 */     conBSCS.montoBSCS(id);
/* 57 */     String result = param.select(id);
/* 58 */     Helper help = new Helper();
/* 59 */     JSONObject job = new JSONObject();
/* 60 */     job.put("Envio", result);
/* 61 */     help.setResponse(200, job);
     
/* 63 */     return help.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\SendMailCRResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */