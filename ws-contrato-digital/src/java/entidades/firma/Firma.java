package entidades.firma;

import dto.contrato.ListaPdfFirmas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import utils.BaseDatos;
import utils.Parametros;
import static utils.Variables.POOLTR;

/**
 *
 * @author marlon.rosalio
 */
public class Firma implements Serializable {
  
  @Getter
  @Setter
  private String token;
  @Getter
  @Setter
  private String acepta;
  @Getter
  @Setter
  private String comentario;
  @Getter
  @Setter
  private String firma;
  @Getter
  @Setter
  private String identidadFrente;
  @Getter
  @Setter
  private String identidadDorso;
  @Getter
  @Setter
  private RespFirma vrespfirma;
  
  public RespFirma getPdfFirmas(){
    vrespfirma = new RespFirma();
    Parametros parametro = new Parametros(POOLTR);
    String query = parametro.consulta("GET_PDF_FIRMA");
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      ArrayList<ListaPdfFirmas> listaPdf;
      
      if (token == null || token.isEmpty()) {
        vrespfirma.error(token, "El token no puede ser nulo", "1");
      } else {
      
        listaPdf = db.getPdfFirmas(query, token);
        
        if (listaPdf.isEmpty()) {
          vrespfirma.error(token, "El token ingresado es invalido o ya expiró", "1");
        } else {
          vrespfirma.setContrato(listaPdf);
          vrespfirma.exito(token, "Lista de contratos");
          
        }
      }
      
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      vrespfirma.error(token, "Error al obtener contratos", ex.getMessage().replace("\n", ""));
    }
    
    return vrespfirma;
  }
  
  public RespFirma guardaFirma() {
    vrespfirma = new RespFirma();
    String customer;
    String existe;
    
    if (this.token == null || this.token.isEmpty()) {
      vrespfirma.error(token, "El token no puede ser nulo", "1");
    } else if (this.acepta == null || this.acepta.isEmpty()) {
      vrespfirma.error(token, "El parametro acepta no puede ser nulo", "1");
    }  else {
      
      // ACEPTA CONTRATO
      if (this.acepta.equals("1")) {
        if (this.firma == null || this.firma.isEmpty()) {
          vrespfirma.error(token, "La firma no puede ser nula", "1");
        } else if (this.identidadFrente == null || this.identidadFrente.isEmpty()) {
          vrespfirma.error("token", "El documento de identidad frontal no puede ser nulo", "1");
        } else {
          Parametros parametro = new Parametros(POOLTR);
          String query = parametro.consulta("GET_CUSTOMER_FIRMA");
          BaseDatos db = new BaseDatos(POOLTR);

          customer = db.consulta(query.replace("?", "'"+this.token+"'"));

          if (customer.isEmpty()) {
            vrespfirma.error(token, "El token ingresado es invalido o ya expiró", "1");
          } else {

            existe = db.consulta("SELECT ID_CUSTOMER RESULTADO FROM CD_DOC_FIRMA WHERE ID_CUSTOMER = "+customer);

            if (existe.isEmpty()) {
              String savefirma = db.insertFirma(customer, this.acepta, this.firma, this.identidadFrente, this.identidadDorso, "");

              if (savefirma.equals("true")) {
                String updContrato = db.Ejecutar("UPDATE CD_DOC_CASO SET USER_UPDATE = 'CLIENTE', DATE_UPDATE = SYSDATE, STATUS = 7 WHERE ID_CUSTOMER = "+customer);

                if (updContrato.equals("true")) {
                  vrespfirma.exito(token, "Firma enviada correctamente");
                } else {
                  vrespfirma.error(token, "Error al enviar la firma", savefirma);
                }
                
              } else {
                vrespfirma.error(token, "Error al enviar la firma", savefirma);
              }
            } else {
              String savefirma = db.updateFirma(customer, this.acepta, this.firma, this.identidadFrente, this.identidadDorso, "");

              if (savefirma.equals("true")) {
                String updContrato = db.Ejecutar("UPDATE CD_DOC_CASO SET USER_UPDATE = 'CLIENTE', DATE_UPDATE = SYSDATE, STATUS = 7 WHERE ID_CUSTOMER = "+customer);
                
                if (updContrato.equals("true")) {
                  vrespfirma.exito(token, "Firma enviada correctamente");
                } else {
                  vrespfirma.error(token, "Error al enviar la firma", savefirma);
                }
                
              } else {
                vrespfirma.error(token, "Error al enviar la firma", savefirma);
              }
            }
          }
        }
      }
      
      // RECHAZA CONTRATO
      if (this.acepta.equals("0")) {
        if (this.comentario == null || this.comentario.isEmpty()) {
          vrespfirma.error(token, "El comentario no puede ser nulo", "1");
        } else {
          Parametros parametro = new Parametros(POOLTR);
          String query = parametro.consulta("GET_CUSTOMER_FIRMA");
          BaseDatos db = new BaseDatos(POOLTR);

          customer = db.consulta(query.replace("?", "'"+this.token+"'"));

          if (customer.isEmpty()) {
            vrespfirma.error(token, "El token ingresado es invalido o ya expiró", "1");
          } else {

            existe = db.consulta("SELECT ID_CUSTOMER RESULTADO FROM CD_DOC_FIRMA WHERE ID_CUSTOMER = "+customer);

            if (existe.isEmpty()) {
              String savefirma = db.insertFirma(customer, this.acepta, "", "", "", this.comentario);

              if (savefirma.equals("true")) {
                String updContrato = db.Ejecutar("UPDATE CD_DOC_CASO SET USER_UPDATE = 'CLIENTE', DATE_UPDATE = SYSDATE, STATUS = 8 WHERE ID_CUSTOMER = "+customer);

                if (updContrato.equals("true")) {
                  vrespfirma.exito(token, "Comentarios enviados correctamente");
                } else {
                  vrespfirma.error(token, "Error al enviar los comentarios", savefirma);
                }
                
              } else {
                vrespfirma.error(token, "Error al enviar los comentarios", savefirma);
              }
            } else {
              String savefirma = db.updateFirma(customer, this.acepta, "", "", "", this.comentario);

              if (savefirma.equals("true")) {
                String updContrato = db.Ejecutar("UPDATE CD_DOC_CASO SET USER_UPDATE = 'CLIENTE', DATE_UPDATE = SYSDATE, STATUS = 8 WHERE ID_CUSTOMER = "+customer);
                
                if (updContrato.equals("true")) {
                  vrespfirma.exito(token, "Comentarios enviados correctamente");
                } else {
                  vrespfirma.error(token, "Error al enviar los comentarios", savefirma);
                }
                
              } else {
                vrespfirma.error(token, "Error al enviar los comentarios", savefirma);
              }
            }
          }
        }
      }
      
      // OTRO VALOR
      if (!this.acepta.equals("0") && !this.acepta.equals("1")) {
        vrespfirma.error(token, "El valor de acepta no es reconocido", "1");
      }
    }
            
    return vrespfirma;
  }
}
