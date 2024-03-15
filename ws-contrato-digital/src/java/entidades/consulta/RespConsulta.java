/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades.consulta;

import java.io.Serializable;

/**
 *
 * @author marlon.rosalio
 */
public class RespConsulta implements Serializable {
  private String resultado;
  private String comentario;
  private String alerta;
  private String imprimir;

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
  
  public String getAlerta() {
    return alerta;
  }

  public void setAlerta(String alerta) {
    this.alerta = alerta;
  }

  public String getImprimir() {
    return imprimir;
  }

  public void setImprimir(String imprimir) {
    this.imprimir = imprimir;
  }

  public void error(String mensaje) {
    this.setComentario(mensaje);
    this.setResultado("Error");
    this.setAlerta("error");
  }

  public void exito(String sec,String mensaje) {
    this.setComentario(mensaje);
    this.setImprimir("Exito");
    this.setResultado("Exito");
  }
}