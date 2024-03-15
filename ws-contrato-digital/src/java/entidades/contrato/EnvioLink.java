package entidades.contrato;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import utils.Parametros;
import static utils.Variables.POOLTR;
import utils.Xml;

/**
 *
 * @author marlon.rosalio
 */
public class EnvioLink implements Serializable {
  private String protocolo;
  private String host;
  private String port;
  private String service;
  private String external;
  private Parametros parametros = new Parametros(POOLTR);
  
  
  public String getLink(String token){
    String link;
    
    // OBTENER LOS PARAMETROS DE CONFIGURACION PARA CONSULTAR SHORTLINK
    this.protocolo = this.parametros.consulta("LINK_SCHEME");
    this.host = this.parametros.consulta("LINK_HOST");
    this.port = this.parametros.consulta("LINK_PORT");
    this.service = this.parametros.consulta("LINK_SERVICE");
    this.external = this.parametros.consulta("LINK_EXTERNAL");
    
    String soap = 
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice/\">\n" +
            " <soapenv:Header/>\n" +
            " <soapenv:Body>\n" +
            "   <web:get_shortlink>\n" +
            "     <url><![CDATA["+this.external+token+"]]></url>\n" +
            "     <type>dynamic</type>\n" +
            "     <country>506</country>\n" +
            "     <creation>0</creation>\n" +
            "   </web:get_shortlink>\n" +
            " </soapenv:Body>\n" + 
            "</soapenv:Envelope>";

    try {
      OkHttpClient client = new OkHttpClient().newBuilder().build();
      okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/xml");
      RequestBody body = RequestBody.Companion.create(soap, mediaType);
      Request request = new Request.Builder()
              .url(this.protocolo + "://" + this.host + ":" + this.port + this.service)
              .method("POST", body)
              .addHeader("Content-Type", "text/xml")
              .build();
      
      okhttp3.Response response = client.newCall(request).execute();
      int code = response.code();
      String xmlData = response.body().string();
      
      if (code != 200) {
        link = "";
        return link;
      }
      
      link = Xml.parseXml(Xml.leerXml(xmlData), "shortlink");
      return link;
    } catch(Exception ex){
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      link = "";
      return link;
    }
  }
}
