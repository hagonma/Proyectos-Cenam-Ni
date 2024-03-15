package utils;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author marlon.rosalio
 */
public class Xml {
  
  private Xml() {
    throw new IllegalStateException("Utility class");
  }
  
  public static Document leerXml(String respuesta) {
    Document document = null;
    
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.parse(new InputSource(new StringReader(respuesta)));
    } catch (Exception ex) {
      Logger.getLogger(Xml.class.getName()).log(Level.SEVERE, ex.getMessage());
    }
    
    return document;
  }
  
  public static String parseXml(Document documento, String valor) {
    return documento.getElementsByTagName(valor).item(0).getTextContent();
  }
}
