package ws;

import entidades.contrato.Contratos;
import entidades.contrato.Crear;
import entidades.contrato.EnvioEmail;
import entidades.contrato.EnvioSms;
import entidades.contrato.RespContrato;
import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST Web Service
 *
 * @author marlon.rosalio
 */
@Path("contratos")
public class ContratosResource {

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of ContratosResource
   */
  public ContratosResource() {
  }

  /**
   * Retrieves representation of an instance of ws.ContratosResource
   * @return an instance of java.lang.String
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("generar")
  public Response nuevoContrato(@Valid Crear dcrear) {
    RespContrato nuevo = dcrear.creaContrato();
    
    return Response.status(Response.Status.CREATED).entity(nuevo).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("guarda-pdf")
  public Response guardaPdf(@Valid Crear dcrear) {
    RespContrato nuevo = dcrear.guardaPdf();
    
    return Response.status(Response.Status.CREATED).entity(nuevo).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("generados/asesor")
  public Response getContratosAsesor(@Valid Contratos dcontratos) {
    RespContrato contratos = dcontratos.getCasosAsesor();
        
    return Response.status(Response.Status.CREATED).entity(contratos).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("generados/backoffice")
  public Response getContratosBackoffice(@Valid Contratos dcontratos) {
    RespContrato contratos = dcontratos.getCasosBackoffice();
        
    return Response.status(Response.Status.CREATED).entity(contratos).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("generados/supervisor")
  public Response getContratosSupervisor(@Valid Contratos dcontratos) {
    RespContrato contratos = dcontratos.getCasosSupervisor();
        
    return Response.status(Response.Status.CREATED).entity(contratos).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("get-pdf")
  public Response getPdf(@Valid Contratos dcontratos) {
    RespContrato pdf = dcontratos.getPdf();
    
    return Response.status(Response.Status.CREATED).entity(pdf).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("get-contrato")
  public String getContrato(@Valid Contratos dcontratos) {
    String contrato = dcontratos.getContrato();
    
    return contrato;
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("update-contrato")
  public Response updateContrato(@Valid Crear dcrear) {
    RespContrato update = dcrear.updateContrato();
    
    return Response.status(Response.Status.CREATED).entity(update).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("send-backoffice")
  public Response sendBackoffice(@Valid Contratos dcontratos) {
    RespContrato update = dcontratos.sendBackoffice();
    
    return Response.status(Response.Status.CREATED).entity(update).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("send-pdf")
  public Response sendPdf(@Valid Contratos dcontratos) {
    RespContrato update = dcontratos.sendPdf();
    
    return Response.status(Response.Status.CREATED).entity(update).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("send-sms")
  public Response sendSms(@Valid EnvioSms denvio) {
    RespContrato envio = denvio.sendSms();
    
    return Response.status(Response.Status.CREATED).entity(envio).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("send-email")
  public Response sendEmail(@Valid EnvioEmail denvio) {
    RespContrato envio = denvio.sendEmail();
    
    return Response.status(Response.Status.CREATED).entity(envio).build();
  }
}
