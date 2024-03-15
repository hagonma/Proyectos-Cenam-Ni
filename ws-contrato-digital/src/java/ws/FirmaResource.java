package ws;

import entidades.firma.Firma;
import entidades.firma.RespFirma;
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
@Path("firma")
public class FirmaResource {

  @Context
  private UriInfo context;

  /**
   * Creates a new instance of FirmaResource
   */
  public FirmaResource() {
  }

  /**
   * Retrieves representation of an instance of ws.FirmaResource
   * @return an instance of java.lang.String
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("contrato")
  public Response getPdf(@Valid Firma dfirma) {
    RespFirma pdf = dfirma.getPdfFirmas();
    
    return Response.status(Response.Status.CREATED).entity(pdf).build();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({MediaType.APPLICATION_JSON + utils.Variables.UTF})
  @Path("send-firma")
  public Response guardafirma(@Valid Firma dfirma) {
    RespFirma firma = dfirma.guardaFirma();
    
    return Response.status(Response.Status.CREATED).entity(firma).build();
  }
}
