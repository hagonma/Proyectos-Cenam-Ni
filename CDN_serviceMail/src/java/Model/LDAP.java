 package Model;
 
 import Model.Parametro;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 public class LDAP
 {
   String dominio;
   String dns;
   
   public String getDominio(int pais) throws Exception {
/* 22 */     Parametro param = new Parametro();
/* 23 */     param.getByName("DOMINIO_LDAP", pais);
/* 24 */     JSONObject jobj = new JSONObject(param.hp.getResponse());
/* 25 */     JSONArray jarr = jobj.getJSONArray("Response");
/* 26 */     JSONObject jobjRow = jarr.getJSONObject(0);
/* 27 */     return jobjRow.getString("VALOR");
   }
   
   public void setDominio(String dominio) {
/* 31 */     this.dominio = dominio;
   }
   
   public String getDns(int pais) throws Exception {
/* 35 */     Parametro param = new Parametro();
/* 36 */     param.getByName("DNS_LDAP", pais);
/* 37 */     JSONObject jobj = new JSONObject(param.hp.getResponse());
/* 38 */     JSONArray jarr = jobj.getJSONArray("Response");
/* 39 */     JSONObject jobjRow = jarr.getJSONObject(0);
/* 40 */     return jobjRow.getString("VALOR");
   }
   
   public void setDns(String dns) {
/* 44 */     this.dns = dns;
   }
 }


