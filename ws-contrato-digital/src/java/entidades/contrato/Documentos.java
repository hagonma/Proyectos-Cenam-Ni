package entidades.contrato;

import java.util.ArrayList;
import java.util.List;
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
public class Documentos {
  
  @Getter
  @Setter
  private String customer;
  @Getter
  @Setter
  private RespContrato vrespcontrato;
  
  /*public RespContrato getDocumentos(){
    vrespcontrato = new RespContrato();
    Parametros consulta = new Parametros(POOLTR);
    String queryDoc = consulta.consulta("GET_DOCUMENTOS");
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      ArrayList<ListaDocumentos> listaDocumentos;
      ArrayList<Contrato> infoContrato;
      
      listaDocumentos = db.getDocumentos(queryDoc);
      infoContrato = db.getInfoContrato();
      
      vrespcontrato.setDocumentos(listaDocumentos);
      vrespcontrato.setContrato(infoContrato);
      vrespcontrato.exito("0", "Lista de Documentos");
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      vrespcontrato.error("Error al obtener documentos", ex.getMessage());
    }
        
    return vrespcontrato;
  }*/
}
