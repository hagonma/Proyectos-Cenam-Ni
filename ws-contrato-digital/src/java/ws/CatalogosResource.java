package ws;

import java.text.ParseException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utils.BaseDatos;
import static utils.Variables.POOLTR;

/**
 * REST Web Service
 *
 * @author marlon.rosalio
 */
@Path("catalogos")
public class CatalogosResource {

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of CatalogosResource
   */
  public CatalogosResource() {
  }

  /**
   * Retrieves representation of an instance of ws.CatalogosResource
   * @return an instance of java.lang.String
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("tipo-docs")
  public String getTipoDocs() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String docs = db.getTipoDocs();
    return docs;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("provincia")
  public String getProvincia() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String provincias = db.getProvincias();
    return provincias;
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("canton")
  public String getCanton(String cadena) throws ParseException {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String cantones = db.getCantones(cadena);
    return cantones;
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("distrito")
  public String getDistrito(String cadena) throws ParseException {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String distritos = db.getDistritos(cadena);
    return distritos;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("direccion-referencia")
  public String getDirRef() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String docs = db.getDirRef();
    return docs;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("direccion-lado")
  public String getDirLado() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String docs = db.getDirLado();
    return docs;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("marca-terminal")
  public String getMarcaTerminal() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String docs = db.getMarcaTerminal();
    return docs;
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("modelo-terminal")
  public String getModeloTerminal(String cadena) throws ParseException {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String modelos = db.getModeloTerminal(cadena);
    return modelos;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("tipo-servicio")
  public String getTipoServicio() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String servicio = db.getTipoServicio();
    return servicio;
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("planes")
  public String getPlanes(String cadena) throws ParseException {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String planes = db.getPlanes(cadena);
    return planes;
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("detalle-plan")
  public String getDetallePlan(String cadena) throws ParseException {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String detalle = db.getDetallePlan(cadena);
    return detalle;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("ciclo-facturacion")
  public String getCicloFact() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String segmento = db.getCicloFact();
    return segmento;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("financiamiento")
  public String getFinancing() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String plazo = db.getFinancing();
    return plazo;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("plazo-contrato")
  public String getPlazo() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String plazo = db.getPlazo();
    return plazo;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("medio-pago")
  public String getMedioPago() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String pago = db.getMedioPago();
    return pago;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("operador")
  public String getOperador() {
    BaseDatos db = new BaseDatos(POOLTR);
    
    String docs = db.getOperador();
    return docs;
  }
}
