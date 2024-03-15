package entidades.contrato;

import dto.contrato.ListaContratos;
import dto.contrato.ListaPdf;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author marlon.rosalio
 */
public class RespContrato implements Serializable {
  
  private String transaccion;
  private String resultado;
  private String comentario;
  private String imprimir;
  private String codigo;
  private List<ListaContratos> contratos;
  private List<ListaPdf> pdf;

    
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
  
  public List<ListaContratos> getContratos() {
    return contratos;
  }

  public void setContratos(List<ListaContratos> contratos) {
    this.contratos = contratos;
  }
  
  public List<ListaPdf> getPdf() {
    return pdf;
  }

  public void setPdf(List<ListaPdf> pdf) {
    this.pdf = pdf;
  }
  
  public void error(String mensaje, String codigo) {
    this.setTransaccion("-1");
    this.setComentario(mensaje);
    this.setCodigo(codigo);
    this.setResultado("Error");
  }
  
  public void exito(String sec, String mensaje) {
    this.setTransaccion(sec);
    this.setComentario(mensaje);
    this.setCodigo("");
    this.setResultado("Exito");
  }
}
