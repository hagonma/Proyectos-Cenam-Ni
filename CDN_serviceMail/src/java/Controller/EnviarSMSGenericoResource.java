 package Controller;
 
 import Model.Mensaje;
 import Model.ReporteSMS;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.POST;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("EnviarSMSGenerico")
 public class EnviarSMSGenericoResource
 {
   @Context
   private UriInfo context;
/*  38 */   public String sistema = "R";
/*  39 */   public String askedService = "";
   
   public String getAskedService() {
/*  42 */     return this.askedService;
   }
   
   public void setAskedService(String askedService) {
/*  46 */     this.askedService = askedService;
   }
   
   public String getSistema() {
/*  50 */     return this.sistema;
   }
   
   public void setSistema(String sistema) {
/*  54 */     this.sistema = sistema;
   }
 
 
 
 
 
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson() {
/*  71 */     throw new UnsupportedOperationException();
   }
 
 
 
 
   
   @POST
   @Consumes({"application/json"})
   public String postJson(String content) throws JSONException, Exception {
/*  81 */     JSONObject job = new JSONObject(content);
/*  82 */     String id = job.getString("id");
/*  83 */     Long num = Long.valueOf(job.getLong("numero"));
/*  84 */     int pais = job.getInt("pais");
/*  85 */     String descripcion = job.getString("descripcion");
/*  86 */     Date dNow = new Date();
/*  87 */     SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
/*  88 */     String fecha = ft.format(dNow);
/*  89 */     Mensaje param = new Mensaje();
/*  90 */     ReporteSMS rsms = new ReporteSMS();
/*  91 */     rsms.setOrigen("CLARO");
/*  92 */     rsms.setDestino(num);
/*  93 */     rsms.setStatus("P");
/*  94 */     rsms.setDate_sended(fecha);
/*  95 */     rsms.setSistema(getSistema());
/*  96 */     rsms.setPrioridad(1);
/*  97 */     rsms.setDate_registered(fecha);
/*  98 */     rsms.setQueue(1);
/*  99 */     rsms.setPais_id(pais);
/* 100 */     rsms.setA_demanda(1);
/* 101 */     rsms.setAskedService(getAskedService());
 
     
/* 104 */     if (descripcion.equals("") && !id.equals("")) {
/* 105 */       param.select(id, pais, 0);
/* 106 */       JSONObject jobj = new JSONObject(param.hp.getResponse());
/* 107 */       JSONArray jarr = jobj.getJSONArray("Response");
/* 108 */       JSONObject jobjRow = jarr.getJSONObject(0);
/* 109 */       rsms.setMsg(jobjRow.getString("DESCRIPCION"));
     } 
/* 111 */     if (!descripcion.equals("") && id.equals(""))
     {
/* 113 */       rsms.setMsg(descripcion);
     }
     
/* 116 */     rsms.insert();
     
/* 118 */     return rsms.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\EnviarSMSGenericoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */