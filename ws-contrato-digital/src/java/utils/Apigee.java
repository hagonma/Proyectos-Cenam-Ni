package utils;

import java.io.Serializable;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
import static utils.Variables.POOLTR;

/**
 *
 * @author marlon.rosalio
 */
public class Apigee implements Serializable{
  
  private String protocolo;
  private String host;
  private String port;
  private String service;
  private String user;
  private String pass;
  private String body;
  private String clave;
  private String token;
  
  private Parametros parametros = new Parametros(POOLTR);
  
  private boolean obtenerToken() {
    // OBTENER LOS PARAMETROS DE CONFIGURACION PARA CONSULTAR APIGEE
    this.protocolo = this.parametros.consulta("APIGEE_SCHEME");
    this.host = this.parametros.consulta("APIGEE_HOST");
    this.port = this.parametros.consulta("APIGEE_PORT");
    this.service = this.parametros.consulta("APIGEE_OAUTH_SERVICE");
    this.user = this.parametros.consulta("APIGEE_OAUTH_USER");
    this.pass = this.parametros.consulta("APIGEE_OAUTH_PASS");
    this.body = this.parametros.consulta("APIGEE_OAUTH_BODY");
        
    // Realiza la peticion
    try {
      String credentialsToEncode = this.user + ":" + this.pass;
      this.clave = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes());
      
      OkHttpClient client = new OkHttpClient().newBuilder().build();
      okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
      //RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
      RequestBody body = RequestBody.Companion.create("grant_type=client_credentials", mediaType);
      Request request = new Request.Builder()
                .url(this.protocolo + "://" + this.host + ":" + this.port + this.service)
                .method("POST", body)
                .addHeader("Authorization", "Basic "+this.clave)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
      
      okhttp3.Response response = client.newCall(request).execute();
      String jsonData = response.body().string();
      JSONObject jObject = new JSONObject(jsonData);
      
      if (jObject.has("access_token")) {
          this.token = jObject.getString("access_token");
          return this.parametros.Ejecutar(this.token, "APIGEE_TOKEN");
      }
      else {
          return false;
      }
      
    } catch (Exception ex){
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, ex.getMessage());
      return false;
    }
  }
  
  public String reloadToken() {
    String tokens = "";
    System.out.println("llama reload");
    
    try {
      if (this.obtenerToken()) {
        tokens = this.parametros.consulta("APIGEE_TOKEN");
      }
    } catch(Exception ex){
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
    }
    
    return tokens;
  }
}
