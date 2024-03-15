package entidades.firma;

import dto.contrato.ListaPdf;
import dto.contrato.ListaPdfFirmas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marlon.rosalio
 */
public class RespFirma implements Serializable {
  private String transaccion;
  private String resultado;
  private String comentario;
  private String imprimir;
  private String codigo;
  private List<ListaPdfFirmas> contrato;

  public String getTransaccion() {
    return transaccion;
  }

  public void setTransaccion(String transaccion) {
    this.transaccion = transaccion;
  }

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public String getImprimir() {
    return imprimir;
  }

  public void setImprimir(String imprimir) {
    this.imprimir = imprimir;
  }
  
  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
    
  public List<ListaPdfFirmas> getContrato() {
    return contrato;
  }

  public void setContrato(List<ListaPdfFirmas> contrato) {
    this.contrato = contrato;
  }
  
  public void error(String transaccion, String mensaje, String codigo) {
    this.setTransaccion(transaccion);
    this.setComentario(mensaje);
    this.setCodigo(codigo);
    this.setResultado("Error");
  }
  
  public void exito(String transaccion, String mensaje) {
    this.setTransaccion(transaccion);
    this.setComentario(mensaje);    
    this.setResultado("Exito");
  }

   /* void setContrato(ArrayList<ListaPdfFirmas> listaPdf) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    
}
