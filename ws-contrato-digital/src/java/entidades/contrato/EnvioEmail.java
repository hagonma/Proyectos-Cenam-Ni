package entidades.contrato;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import utils.BaseDatos;
import utils.Parametros;
import static utils.Variables.POOLTR;
import utils.Xml;

/**
 *
 * @author marlon.rosalio
 */
public class EnvioEmail implements Serializable {
  private String protocolo;
  private String host;
  private String port;
  private String service;
  private String remitente;
  private String asunto;
  private String mensaje;
  private String link;
  private String cliente;
  private String creado;
  private Parametros parametros = new Parametros(POOLTR);
  
  @Getter
  @Setter
  private String customer;
  @Getter
  @Setter
  private String email;
  @Getter
  @Setter
  private String usuario;
  @Getter
  @Setter
  private RespContrato vrespcontrato;
  
  public RespContrato sendEmail() {
    BaseDatos db = new BaseDatos(POOLTR);
    String result;
    EnvioLink lk = new EnvioLink();
    UUID uuid = UUID.randomUUID();
    String tokenCustomer = uuid.toString();
    
    vrespcontrato = new RespContrato();
    
    // OBTENER LOS PARAMETROS DE CONFIGURACION PARA ENVIAR EMAIL
    this.protocolo = this.parametros.consulta("EMAIL_SCHEME");
    this.host = this.parametros.consulta("EMAIL_HOST");
    this.port = this.parametros.consulta("EMAIL_PORT");
    this.service = this.parametros.consulta("EMAIL_SERVICE");
    this.remitente = this.parametros.consulta("EMAIL_REMITENTE");
    this.asunto = this.parametros.consulta("EMAIL_ASUNTO");
    this.mensaje = this.parametros.consulta("EMAIL_CONTENT");
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
      
      this.mensaje = this.mensaje.replace("_LINK_", this.link);
      this.mensaje = this.mensaje.replace("_NAME_", this.cliente);
      this.mensaje = this.mensaje.replace("_DATE_", this.creado);
    
      String soap = 
              "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
              "  <soapenv:Header/>\n" +
              "  <soapenv:Body>\n" +
              "    <tem:enviar>\n" +
              "      <tem:desde>"+this.remitente+"</tem:desde>\n" +
              "      <tem:destinatarios>"+this.email+"</tem:destinatarios>\n" +
              "      <tem:asunto>"+this.asunto+"</tem:asunto>\n" +
              "      <tem:cuerpo_mensaje><![CDATA["+this.mensaje+"]]></tem:cuerpo_mensaje>\n" +
              "      <tem:archivo_binario></tem:archivo_binario>\n" +
              "      <tem:nombre_archivo></tem:nombre_archivo>\n" +
              "      <tem:nombre_base_html></tem:nombre_base_html>\n" +
              "    </tem:enviar>\n" +
              "  </soapenv:Body>\n" +
              "</soapenv:Envelope>";

      try {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/xml");
        RequestBody body = RequestBody.Companion.create(soap, mediaType);
        Request request = new Request.Builder()
                .url(this.protocolo + "://" + this.host + ":" + this.port + this.service)
                .method("POST", body)
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("SOAPAction", "http://tempuri.org/ienvioCorreo/enviar")
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        int code = response.code();
        String xmlData = response.body().string();

        if (code != 200) {
          vrespcontrato.error(xmlData, Integer.toString(code));
          return vrespcontrato;
        }

        // Peticion exitosa
        result = Xml.parseXml(Xml.leerXml(xmlData), "enviarResult");
        String[] resultado = result.split(";");
        if (resultado[0].equals("0")) {
          db.Ejecutar("UPDATE CD_DOC_CASO SET TOKEN = '"+tokenCustomer+"', USER_UPDATE = '"+this.usuario+"', DATE_UPDATE = SYSDATE, STATUS = 6 WHERE ID_CUSTOMER ="+this.customer);
          
          vrespcontrato.exito(this.customer, "Correo enviado satisfactoriamente");
          return vrespcontrato;
        } else {
          vrespcontrato.error(xmlData, Integer.toString(code));
          return vrespcontrato;
        }
      } catch(Exception ex){
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
        vrespcontrato.error("Error al enviar correo", ex.getMessage());
        return vrespcontrato;
      }
    }
  }
}
