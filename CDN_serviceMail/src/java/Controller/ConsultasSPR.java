 package Controller;
 
 import Controller.Helper;
 import java.io.IOException;
 import java.net.MalformedURLException;
 import java.net.ProtocolException;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 class ConsultasSPR
 {
   int encontrarNegocio(String numero, String pais) throws ProtocolException, IOException, MalformedURLException, JSONException {
     JSONObject respuesta;
/* 24 */     if (pais.equals("502")) {
/* 25 */       respuesta = (new Helper()).request("http://172.16.204.215:10001/spr/rest/resource/v1/cache/profile/pisa-" + numero + "/default/default/show", "GET", "");
     } else {
/* 27 */       respuesta = (new Helper()).request("http://172.16.204.215:10001/spr/rest/resource/v1/cache/profile/" + pais + "" + numero + "/fixed-line/default/show", "GET", "");
     } 
     
/* 30 */     JSONObject job = respuesta.getJSONObject("exitStatus");
/* 31 */     String ackResponse = job.getString("description");
/* 32 */     String ackValidate = "Invalid MSISDN", ackSuccess = "Successful Transaction", ackAcceptable = "User or requested service in Black List";
/* 33 */     int retorno = 0;
     
/* 35 */     if (ackValidate.equals(ackResponse) && pais.equals("502")) {
/* 36 */       respuesta = (new Helper()).request("http://172.16.204.215:10001/spr/rest/resource/v1/cache/profile/" + pais + "" + numero + "/fixed-line/default/show", "GET", "");
/* 37 */       job = respuesta.getJSONObject("exitStatus");
/* 38 */       ackResponse = job.getString("description");
     } 
     
/* 41 */     if (ackValidate.equals(ackResponse) || ackAcceptable.equals(ackResponse)) {
/* 42 */       JSONArray jarr = respuesta.getJSONArray("paramList");
/* 43 */       JSONObject accType = jarr.getJSONObject(0);
/* 44 */       if (accType.get("value").equals("OPEN_NI")) {
/* 45 */         retorno = 2;
       } else {
/* 47 */         retorno = 1;
       } 
     } 
/* 50 */     return retorno;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ConsultasSPR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */