package entidades.contrato;

import com.google.gson.JsonObject;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
import utils.Apigee;
import utils.BaseDatos;
import utils.Parametros;
import static utils.Variables.POOLTR;

/**
 *
 * @author marlon.rosalio
 */
public class EnvioSms implements Serializable {
  private String protocolo;
  private String host;
  private String port;
  private String service;
  private String token;
  private String sms;
  private String source;
  private String app;
  private String link;
  private String cliente;
  private String creado;
  private Parametros parametros = new Parametros(POOLTR);
  private String pais;
  
  @Getter
  @Setter
  private String customer;
  @Getter
  @Setter
  private String number;
  @Getter
  @Setter
  private String usuario;
  @Getter
  @Setter
  private RespContrato vrespcontrato;
 
  
  public RespContrato sendSms() {
    BaseDatos db = new BaseDatos(POOLTR);
    EnvioLink lk = new EnvioLink();
    UUID uuid = UUID.randomUUID();
    String tokenCustomer = uuid.toString();
    
    vrespcontrato = new RespContrato();

    // OBTENER LOS PARAMETROS DE CONFIGURACION PARA CONSULTAR APIGEE
    this.protocolo = this.parametros.consulta("APIGEE_SCHEME");
    this.host = this.parametros.consulta("APIGEE_HOST");
    this.port = this.parametros.consulta("APIGEE_PORT");
    this.service = this.parametros.consulta("APIGEE_SMS_SERVICE");
    this.token = this.parametros.consulta("APIGEE_TOKEN");
    this.sms = this.parametros.consulta("SMS_CONTENT");
    this.source = this.parametros.consulta("SMS_SOURCE");
    this.app = this.parametros.consulta("SMS_APP");
    this.pais = this.parametros.consulta("SMS_PREFIJO_PAIS");
    this.link = lk.getLink(tokenCustomer);
    this.cliente = db.consulta("SELECT NOMBRE RESULTADO FROM CD_DOC_CUSTOMER WHERE ID_CUSTOMER = "+this.customer);
    this.creado = db.consulta("SELECT TO_CHAR(DATE_CREATE, 'DD/MM/YYYY') RESULTADO FROM CD_DOC_CASO WHERE ID_CUSTOMER = "+this.customer);
    
    if (tokenCustomer.isEmpty()) {
      vrespcontrato.error("Error al obtener token", "1");
      return vrespcontrato;
    }
    
    if (this.link.isEmpty()) {
      vrespcontrato.error("Error al obtener link", "1");
      return vrespcontrato;
    } else {
            
      JsonObject json = new JsonObject();
      json.addProperty("App", this.app);
      json.addProperty("Source", this.source);
      json.addProperty("Msisdn", this.pais + this.number);
      json.addProperty("Message", this.sms.replace("?", this.link));

      try {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        //RequestBody body = RequestBody.create(mediaType, json.toString());
        RequestBody body = RequestBody.Companion.create(json.toString(), mediaType);
        Request request = new Request.Builder()
                .url(this.protocolo + "://" + this.host /*+ ":" + this.port*/ + this.service)
                .method("POST", body)
                .addHeader("Authorization", "Bearer "+this.token)
                .addHeader("Content-Type", "application/json")
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        int code = response.code();
        String jsonData = response.body().string();
        JSONObject jObject = new JSONObject(jsonData);

        if (code == 401) {
          Apigee getToken = new Apigee();
          this.token = getToken.reloadToken();
          sendSms();
          return vrespcontrato;
        }

        if (code != 200) {
          vrespcontrato.error(jsonData, Integer.toString(code));
          return vrespcontrato;
        }

        // Peticion exitosa
        if (jObject.has("responseMessage")) {
          String sended = jObject.getString("responseMessage");
          if (sended.equals("S")) {
            db.Ejecutar("UPDATE CD_DOC_CASO SET TOKEN = '"+tokenCustomer+"', USER_UPDATE = '"+this.usuario+"', DATE_UPDATE = SYSDATE, STATUS = 6 WHERE ID_CUSTOMER ="+this.customer);
            
            vrespcontrato.exito(this.customer, "Mensaje enviado satisfactoriamente");
            return vrespcontrato;
          } else {
            vrespcontrato.error(jsonData, Integer.toString(code));
            return vrespcontrato;
          }
        }
        else {
          vrespcontrato.error(jsonData, Integer.toString(code));
          return vrespcontrato;
        }

      } catch(Exception ex){
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
        vrespcontrato.error("Error al enviar sms", ex.getMessage());
        return vrespcontrato;
      }
    }
  }
}
