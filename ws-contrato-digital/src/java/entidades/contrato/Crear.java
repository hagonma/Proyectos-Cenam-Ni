package entidades.contrato;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import utils.BaseDatos;
import static utils.Variables.POOLTR;
import static utils.Variables.SEQCONTRACT;

/**
 *
 * @author marlon.rosalio
 */
public class Crear implements Serializable {
  @Getter
  @Setter
  private String nombre;
  @Getter
  @Setter
  private String tipo_identidad;
  @Getter
  @Setter
  private String numero_identidad;
  @Getter
  @Setter
  private String correo;
  @Getter
  @Setter
  private String telefono_movil;
  @Getter
  @Setter
  private String telefono_fijo;
  @Getter
  @Setter
  private String provincia;
  @Getter
  @Setter
  private String canton;
  @Getter
  @Setter
  private String distrito;
  @Getter
  @Setter
  private String barrio;
  @Getter
  @Setter
  private String direccion;
  @Getter
  @Setter
  private String casa;
  @Getter
  @Setter
  private String color;
  @Getter
  @Setter
  private String lado;
  @Getter
  @Setter
  private String marca;
  @Getter
  @Setter
  private String modelo;
  @Getter
  @Setter
  private String imei;
  @Getter
  @Setter
  private String sim;
  @Getter
  @Setter
  private String servicio;
  @Getter
  @Setter
  private String customer_id;
  @Getter
  @Setter
  private String no_contrato;
  @Getter
  @Setter
  private String telefono_contrato;
  @Getter
  @Setter
  private String no_orden;
  @Getter
  @Setter
  private String fac_venta;
  @Getter
  @Setter
  private String fac_deposito;
  @Getter
  @Setter
  private String monto_deposito;
  @Getter
  @Setter
  private String ciclo;
  @Getter
  @Setter
  private String plan;
  @Getter
  @Setter
  private String financiamiento;
  @Getter
  @Setter
  private String plazo;
  @Getter
  @Setter
  private String pago;
  @Getter
  @Setter
  private String numero_porta;
  @Getter
  @Setter
  private String nip;
  @Getter
  @Setter
  private String operador;
  @Getter
  @Setter
  private String movil_sub;
  @Getter
  @Setter
  private String movil_fin;
  @Getter
  @Setter
  private String movil_con;
  @Getter
  @Setter
  private String prima;
  @Getter
  @Setter
  private String pagare;
  @Getter
  @Setter
  private String latitud;
  @Getter
  @Setter
  private String longitud;
  @Getter
  @Setter
  private String azul_2g;
  @Getter
  @Setter
  private String azul_3g;
  @Getter
  @Setter
  private String azul_4g;
  @Getter
  @Setter
  private String verde_2g;
  @Getter
  @Setter
  private String verde_3g;
  @Getter
  @Setter
  private String verde_4g;
  @Getter
  @Setter
  private String amarillo_2g;
  @Getter
  @Setter
  private String amarillo_3g;
  @Getter
  @Setter
  private String amarillo_4g;
  @Getter
  @Setter
  private String rojo_2g;
  @Getter
  @Setter
  private String rojo_3g;
  @Getter
  @Setter
  private String rojo_4g;
  @Getter
  @Setter
  private String nombre_tarjeta;
  @Getter
  @Setter
  private String cedula_tarjeta;
  @Getter
  @Setter
  private String numero_tarjeta;
  @Getter
  @Setter
  private String mes_tarjeta;
  @Getter
  @Setter
  private String anio_tarjeta;
  @Getter
  @Setter
  private String customer;
  @Getter
  @Setter
  private String id_doc;
  @Getter
  @Setter
  private String pdf;
  @Getter
  @Setter
  private String idcontrato;
  @Getter
  @Setter
  private String modulo;
  @Getter
  @Setter
  private String usuario;
  @Getter
  @Setter
  private String control;
  @Getter
  @Setter
  private RespContrato vrespcontrato;


  public RespContrato creaContrato() {
    vrespcontrato = new RespContrato();
    String secuencia;
    BaseDatos db = new BaseDatos(POOLTR);
    
    secuencia = db.consulta(SEQCONTRACT);
    String cliente = db.Ejecutar("INSERT INTO CD_DOC_CUSTOMER (ID_CUSTOMER, NOMBRE, TYPE_DOC, NUM_DOC, EMAIL, TEL_MOVIL, TEL_FIJO, FECHA_CREACION)\n"
              + "VALUES ("+secuencia+", '"+this.nombre+"', '"+this.tipo_identidad+"', '"+this.numero_identidad+"', '"+this.correo+"', '"+this.telefono_movil+"', '"+this.telefono_fijo+"', SYSDATE )");
    
    if (cliente.equals("true")) {
      String direccion = db.Ejecutar("INSERT INTO CD_DOC_DIRECCION (ID_CUSTOMER, PROVINCIA, CANTON, DISTRITO, BARRIO, DIRECCION, CASA, COLOR, LADO)\n"
              + "VALUES ("+secuencia+", '"+this.provincia+"', '"+this.canton+"', '"+this.distrito+"', '"+this.barrio+"', '"
              + this.direccion+"', '"+this.casa+"', '"+this.color+"', '"+this.lado+"')");
      
      if (direccion.equals("true")) {
        String terminal = db.Ejecutar("INSERT INTO CD_DOC_TERMINAL (ID_CUSTOMER, MARCA, MODELO, IMEI_SERIE, SIM)\n"
                + "VALUES ("+secuencia+", '"+this.marca+"', '"+this.modelo+"', '"+this.imei+"', '"+this.sim+"')");
        
        if (terminal.equals("true")) {
          String contrato = db.Ejecutar("INSERT INTO CD_DOC_CONTRATO (ID_CUSTOMER, SERVICIO, CUSTOMER_ID, NO_CONTRATO, TELEFONO, NO_ORDEN, FACTURA_VENTA,"
                  + " FACTURA_DEPOS, MONTO_DEPOSITO, CICLO, PLAN_SERVICIO, FINANCIAMIENTO, PLAZO, MEDIO_PAGO)\n"
                  + "VALUES ("+secuencia+", '"+this.servicio+"', '"+this.customer_id+"', '"+this.no_contrato+"', '"+this.telefono_contrato+"', '"+this.no_orden+"', "
                  + "'"+this.fac_venta+"', '"+this.fac_deposito+"', '"+this.monto_deposito+"', '"+this.ciclo+"', '"+this.plan+"', '"+this.financiamiento+"', '"+this.plazo+"', '"+this.pago+"')");
          
          if (contrato.equals("true")) {
            String portabilidad = db.Ejecutar("INSERT INTO CD_DOC_PORTABILIDAD (ID_CUSTOMER, NUMERO, NIP, OPERADOR)\n"
                      + "VALUES ("+secuencia+", '"+this.numero_porta+"', '"+this.nip+"', '"+this.operador+"')");
            
            if (portabilidad.equals("true")) {              
              String precios = db.Ejecutar("INSERT INTO CD_DOC_PRECIO_EQUIPO (ID_CUSTOMER, MOVIL_SUB, MOVIL_FIN, MOVIL_CON, PRIMA, PAGARE)\n"
                        + "VALUES ("+secuencia+", '"+this.movil_sub+"', '"+this.movil_fin+"', '"+this.movil_con+"', '"+this.prima+"', '"+this.pagare+"')");

              if (precios.equals("true")) {
                String cobertura = db.Ejecutar("INSERT INTO CD_DOC_COBERTURA (ID_CUSTOMER, LATITUD, LONGITUD, AZUL_2G, AZUL_3G, AZUL_4G, VERDE_2G, VERDE_3G, VERDE_4G, "
                          + "AMARILLO_2G, AMARILLO_3G, AMARILLO_4G, ROJO_2G, ROJO_3G, ROJO_4G)\n"
                          + "VALUES ("+secuencia+", '"+this.latitud+"', '"+this.longitud+"', '"+this.azul_2g+"', '"+this.azul_3g+"', '"+this.azul_4g+"', '"+this.verde_2g+"', '"+this.verde_3g+"', '"+this.verde_4g+"', "
                          + "'"+this.amarillo_2g+"', '"+this.amarillo_3g+"', '"+this.amarillo_4g+"', '"+this.rojo_2g+"', '"+this.rojo_3g+"', '"+this.rojo_4g+"')");

                if (cobertura.equals("true")) {
                  String debito = db.Ejecutar("INSERT INTO CD_DOC_DEBITO (ID_CUSTOMER, NOMBRE, CEDULA, NUMERO, MES_VENCE, ANIO_VENCE)\n"
                            + "VALUES ("+secuencia+", '"+this.nombre_tarjeta+"', '"+this.cedula_tarjeta+"', '"+this.numero_tarjeta+"', '"+this.mes_tarjeta+"', '"+this.anio_tarjeta+"')");

                  if (debito.equals("true")) {
                    String caso = db.Ejecutar("INSERT INTO CD_DOC_CASO (ID_CUSTOMER, USER_CREATE, DATE_CREATE, STATUS) VALUES ("+secuencia+", '"+this.usuario+"', SYSDATE, 1)");

                    if (caso.equals("true")) {
                      vrespcontrato.exito(secuencia, "Contrato generado");
                    } else {
                      vrespcontrato.error("Error al generar el contrato", caso);
                    }

                  } else {
                    vrespcontrato.error("Error al insertar Información de Debito Automático", debito);
                  }

                } else {
                  vrespcontrato.error("Error al insertar Información de Cobertura", cobertura);
                }

              } else {
                vrespcontrato.error("Error al insertar Información de Precios de Equipos", precios);
              }
              
            } else {
              vrespcontrato.error("Error al insertar Información de Portabilidad", portabilidad);
            }
            
          } else {
            vrespcontrato.error("Error al insertar Información del Contrato", contrato);
          }
          
        } else {
          vrespcontrato.error("Error al insertar Información del Terminal", terminal);
        }
        
      } else {
        vrespcontrato.error("Error al insertar Dirección del Cliente", direccion);
      }
      
    } else {
      vrespcontrato.error("Error al insertar Información Personal del Cliente", cliente);
    }
    
    return vrespcontrato;
  }
  
  public RespContrato guardaPdf() {
    vrespcontrato = new RespContrato();
    BaseDatos db = new BaseDatos(POOLTR);
    
    String existe = db.consulta("SELECT STATUS RESULTADO FROM CD_DOC_PDF WHERE ID_CUSTOMER = "+this.customer +" AND ID_DOC = "+this.id_doc);
    
    if (existe.equals("1")){
      String savepdf = db.updatePdf(this.customer, this.id_doc, this.pdf);
      
      if (savepdf.equals("true")) {
        vrespcontrato.exito(this.customer, "PDF guardado");
      } else {
        vrespcontrato.error("Error al guardar el PDF", savepdf);
      }
    } else {
      String savepdf = db.insertPdf(this.customer, this.id_doc, this.pdf);
      
      if (savepdf.equals("true")) {
        vrespcontrato.exito(this.customer, "PDF guardado");
      } else {
        vrespcontrato.error("Error al guardar el PDF", savepdf);
      }
    }
    
    return vrespcontrato;
  }
  
  public RespContrato updateContrato() {
    vrespcontrato = new RespContrato();
    BaseDatos db = new BaseDatos(POOLTR);
    
    String estado = db.consulta("SELECT STATUS RESULTADO FROM CD_DOC_CASO WHERE ID_CUSTOMER = "+this.idcontrato);
    System.out.println("Estado actual: "+estado);
    
    if (this.modulo.equals("asesor") && !(estado.equals("1") || estado.equals("2"))) {
      System.out.println("es asesor");
      vrespcontrato.error("El caso se encuentra en un estado donde no puede ser editado", "1");
    } else if (this.modulo.equals("backoffice") && !(estado.equals("3") || estado.equals("4") || estado.equals("5") || estado.equals("8"))) {
      System.out.println("es backoffice");
      vrespcontrato.error("El caso se encuentra en un estado donde no puede ser editado", "1");
    } else {
      String cliente = db.Ejecutar("UPDATE CD_DOC_CUSTOMER SET NOMBRE = '"+this.nombre+"', TYPE_DOC = '"+this.tipo_identidad+"', NUM_DOC = '"+this.numero_identidad+"', EMAIL = '"+this.correo+"', "
                + "TEL_MOVIL = '"+this.telefono_movil+"', TEL_FIJO = '"+this.telefono_fijo+"', FECHA_MODIFICACION = SYSDATE, STATUS = 2 WHERE ID_CUSTOMER = "+this.idcontrato);

      if (cliente.equals("true")) {
        String direccion = db.Ejecutar("UPDATE CD_DOC_DIRECCION SET PROVINCIA = '"+this.provincia+"', CANTON = '"+this.canton+"', DISTRITO = '"+this.distrito+"', BARRIO = '"+this.barrio+"', DIRECCION = '"+this.direccion+"', "
                + "CASA = '"+this.casa+"', COLOR = '"+this.color+"', LADO = '"+this.lado+"' WHERE ID_CUSTOMER = "+this.idcontrato);

        if (direccion.equals("true")) {
          String terminal = db.Ejecutar("UPDATE CD_DOC_TERMINAL SET MARCA = '"+this.marca+"', MODELO = '"+this.modelo+"', IMEI_SERIE = '"+this.imei+"', SIM = '"+this.sim+"' WHERE ID_CUSTOMER = "+this.idcontrato);

          if (terminal.equals("true")) {
            String contrato = db.Ejecutar("UPDATE CD_DOC_CONTRATO SET SERVICIO = '"+this.servicio+"', CUSTOMER_ID = '"+this.customer_id+"', NO_CONTRATO = '"+this.no_contrato+"', TELEFONO = '"+this.telefono_contrato+"', "
                    + "NO_ORDEN = '"+this.no_orden+"', FACTURA_VENTA = '"+this.fac_venta+"', FACTURA_DEPOS = '"+this.fac_deposito+"', MONTO_DEPOSITO = '"+this.monto_deposito+"', CICLO = '"+this.ciclo+"', "
                    + "PLAN_SERVICIO = '"+this.plan+"', FINANCIAMIENTO = '"+this.financiamiento+"', PLAZO = '"+this.plazo+"', MEDIO_PAGO = '"+this.pago+"' WHERE ID_CUSTOMER = "+this.idcontrato);

            if (contrato.equals("true")) {
              String portabilidad = db.Ejecutar("UPDATE CD_DOC_PORTABILIDAD SET NUMERO = '"+this.numero_porta+"', NIP = '"+this.nip+"', OPERADOR = '"+this.operador+"' WHERE ID_CUSTOMER = "+this.idcontrato);

              if (portabilidad.equals("true")) {
                String precios = db.Ejecutar("UPDATE CD_DOC_PRECIO_EQUIPO SET MOVIL_SUB = '"+this.movil_sub+"', MOVIL_FIN = '"+this.movil_fin+"', MOVIL_CON = '"+this.movil_con+"', PRIMA = '"+this.prima+"', PAGARE = '"+this.pagare+"' WHERE ID_CUSTOMER = "+this.idcontrato);

                if (precios.equals("true")) {
                  String cobertura = db.Ejecutar("UPDATE CD_DOC_COBERTURA SET LATITUD = '"+this.latitud+"', LONGITUD = '"+this.longitud+"', AZUL_2G = '"+this.azul_2g+"', AZUL_3G = '"+this.azul_3g+"', AZUL_4G = '"+this.azul_4g+"', VERDE_2G = '"+this.verde_2g+"', VERDE_3G = '"+this.verde_3g+"', "
                          + "VERDE_4G = '"+this.verde_4g+"', AMARILLO_2G = '"+this.amarillo_2g+"', AMARILLO_3G = '"+this.amarillo_3g+"', AMARILLO_4G = '"+this.amarillo_4g+"', ROJO_2G = '"+this.rojo_2g+"', ROJO_3G = '"+this.rojo_3g+"', ROJO_4G = '"+this.rojo_4g+"' WHERE ID_CUSTOMER = "+this.idcontrato);

                  if (cobertura.equals("true")) {
                    String debito = db.Ejecutar("UPDATE CD_DOC_DEBITO SET NOMBRE = '"+this.nombre_tarjeta+"', CEDULA = '"+this.cedula_tarjeta+"', NUMERO = '"+this.numero_tarjeta+"', MES_VENCE = '"+this.mes_tarjeta+"', ANIO_VENCE = '"+this.anio_tarjeta+"' WHERE ID_CUSTOMER = "+this.idcontrato);

                    if (debito.equals("true")) {
                      String update = db.Ejecutar("UPDATE CD_DOC_CASO SET STATUS = "+this.control+", USER_UPDATE = '"+this.usuario+"', DATE_UPDATE = SYSDATE WHERE ID_CUSTOMER = "+this.idcontrato);

                      if (update.equals("true")) {
                        String removepdf = db.Ejecutar("DELETE FROM CD_DOC_PDF WHERE ID_CUSTOMER = "+this.idcontrato);
                        
                        vrespcontrato.exito(this.idcontrato, "Contrato editado");
                      } else {
                        vrespcontrato.error("Error al editar el contrato", update);
                      }

                    } else {
                      vrespcontrato.error("Error al editar Información de Debito Automático", debito);
                    }

                  } else {
                    vrespcontrato.error("Error al editar Información de Cobertura", cobertura);
                  }

                } else {
                  vrespcontrato.error("Error al editar Información de Precios de Equipos", precios);
                }

              } else {
                vrespcontrato.error("Error al editar Información de Portabilidad", portabilidad);
              }

            } else {
              vrespcontrato.error("Error al editar Información del Contrato", contrato);
            }

          } else {
            vrespcontrato.error("Error al editar Información del Terminal", terminal);
          }

        } else {
          vrespcontrato.error("Error al editar Dirección del Cliente", direccion);
        }

      } else {
        vrespcontrato.error("Error al editar Información Personal del Cliente", cliente);
      }
    }
    
    return vrespcontrato;
  }
}
