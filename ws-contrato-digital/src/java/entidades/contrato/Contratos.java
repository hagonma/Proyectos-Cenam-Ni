package entidades.contrato;

import dto.contrato.ListaContratos;
import dto.contrato.ListaPdf;
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
public class Contratos {
  
  @Getter
  @Setter
  private String user;
  @Getter
  @Setter
  private String perfil;
  @Getter
  @Setter
  private String customer;
  @Getter
  @Setter
  private RespContrato vrespcontrato;
  
  public RespContrato getCasosAsesor(){
    vrespcontrato = new RespContrato();
    Parametros consulta = new Parametros(POOLTR);
    String query = consulta.consulta("GET_CASOS_ASESOR");
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      ArrayList<ListaContratos> listaContratos;
      
      listaContratos = db.getCasosAsesor(query, user);
      vrespcontrato.setContratos(listaContratos);
      vrespcontrato.exito("0", "Lista de Contratos");
      
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      vrespcontrato.error("Error al obtener contratos", ex.getMessage());
    }
    
    return vrespcontrato;
  }
  
  public RespContrato getCasosBackoffice(){
    vrespcontrato = new RespContrato();
    Parametros consulta = new Parametros(POOLTR);
    String query = consulta.consulta("GET_CASOS_BACKOFFICE");
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      ArrayList<ListaContratos> listaContratos;
      
      listaContratos = db.getCasosBackoffice(query);
      vrespcontrato.setContratos(listaContratos);
      vrespcontrato.exito("0", "Lista de Contratos");
      
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      vrespcontrato.error("Error al obtener contratos", ex.getMessage());
    }
    
    return vrespcontrato;
  }
  
  public RespContrato getCasosSupervisor(){
    vrespcontrato = new RespContrato();
    Parametros consulta = new Parametros(POOLTR);
    String query = consulta.consulta("GET_CASOS_BACKOFFICE");
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      ArrayList<ListaContratos> listaContratos;
      
      listaContratos = db.getCasosSupervisor(query);
      vrespcontrato.setContratos(listaContratos);
      vrespcontrato.exito("0", "Lista de Contratos");
      
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      vrespcontrato.error("Error al obtener contratos", ex.getMessage());
    }
    
    return vrespcontrato;
  }
  
  public RespContrato getPdf(){
    vrespcontrato = new RespContrato();
    Parametros consulta = new Parametros(POOLTR);
    String query = consulta.consulta("GET_PDF");
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      ArrayList<ListaPdf> listaPdf;
      
      listaPdf = db.getPdf(query, customer);
      vrespcontrato.setPdf(listaPdf);
      vrespcontrato.exito("0", "Lista de Pdf");
      
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      vrespcontrato.error("Error al obtener pdf", ex.getMessage());
    }
    
    return vrespcontrato;
  }
  
  public String getContrato(){
    String query = "SELECT FN_CD_CONTRATO('"+this.customer+"', '"+this.user+"', '"+this.perfil+"') RESULTADO FROM DUAL";
    String contrato = "";
    
    try {
      BaseDatos db = new BaseDatos(POOLTR);
      
      contrato = db.consulta(query);      
    } catch (Exception ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO,ex.getMessage());
      contrato = ex.getMessage();
    }
    
    return contrato;
  }
  
  public RespContrato sendBackoffice() {
    vrespcontrato = new RespContrato();
    BaseDatos db = new BaseDatos(POOLTR);
    
    String estado = db.consulta("SELECT STATUS RESULTADO FROM CD_DOC_CASO WHERE ID_CUSTOMER = "+this.customer);
    System.out.println("Estado actual: "+estado);
    
    if (!(estado.equals("1") || estado.equals("2"))) {
      vrespcontrato.error("El caso se encuentra en un estado donde no puede ser enviado a backoffice", "1");
    } else {

      String update = db.Ejecutar("UPDATE CD_DOC_CASO SET STATUS = 3, USER_UPDATE = '"+this.user+"', DATE_UPDATE = SYSDATE WHERE ID_CUSTOMER = "+this.customer);

      if (update.equals("true")) {
        vrespcontrato.exito(this.customer, "Contrato editado");
      } else {
        vrespcontrato.error("Error al enviar a backoffice", update);
      }

    }
    
    return vrespcontrato;
  }
  
  public RespContrato sendPdf() {
    vrespcontrato = new RespContrato();
    BaseDatos db = new BaseDatos(POOLTR);
    
    String estado = db.consulta("SELECT STATUS RESULTADO FROM CD_DOC_CASO WHERE ID_CUSTOMER = "+this.customer);
    System.out.println("Estado actual: "+estado);
    
    if (!(estado.equals("3") || estado.equals("4") || estado.equals("5"))) {
      vrespcontrato.error("El caso se encuentra en un estado donde no puede ser generado un nuevo pdf", "1");
    } else {

      String update = db.Ejecutar("UPDATE CD_DOC_CASO SET STATUS = 5, USER_UPDATE = '"+this.user+"', DATE_UPDATE = SYSDATE WHERE ID_CUSTOMER = "+this.customer);

      if (update.equals("true")) {
        vrespcontrato.exito(this.customer, "Contrato editado");
      } else {
        vrespcontrato.error("Error al generar el pdf", update);
      }

    }
    
    return vrespcontrato;
  }
}
