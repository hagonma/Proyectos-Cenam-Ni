 package Controller;
 
 import Controller.Helper;
 import Model.ConsultasOPEN;
 import Model.ConsultasPisa;
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
 
  
 @Path("SendMailFijo")
 public class SendMailFijoResource
 {
   @Context
   private UriInfo context;
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("idpais") String idpais) throws Exception {
/* 49 */     Select param = new Select();
/* 50 */     if (idpais.equals("502")) {
/* 51 */       ConsultasPisa conpisa = new ConsultasPisa();
/* 52 */       conpisa.VigenciaContrato(id);
/* 53 */       conpisa.EquipoFinanciado(id);
/* 54 */       conpisa.CorreoCliente(id);
/* 55 */       conpisa.FechaPago(id);
     } 
     
/* 58 */     if (idpais.equals("504") && id.equals("CARGA_DIARIA")) {
/* 59 */       ConsultasOPEN conOPEN = new ConsultasOPEN(idpais);
/* 60 */       conOPEN.obtenerInformacion();
     } 
     
/* 63 */     if (idpais.equals("505") && id.equals("CARGA_DIARIA")) {
/* 64 */       ConsultasOPEN conOPEN = new ConsultasOPEN(idpais);
/* 65 */       conOPEN.obtenerInformacion();
     } 
 
     
/* 69 */     if (idpais.equals("507") && id.equals("CARGA_DIARIA")) {
/* 70 */       ConsultasOPEN conOPEN = new ConsultasOPEN(idpais);
/* 71 */       conOPEN.obtenerInformacion();
     } 
 
 
 
     
/* 77 */     String resultMail = param.selectFijo(id, idpais);
/* 78 */     Helper help = new Helper();
/* 79 */     JSONObject job = new JSONObject();
/* 80 */     job.put("Envio", resultMail);
/* 81 */     help.setResponse(200, job);
     
/* 83 */     return help.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\SendMailFijoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */