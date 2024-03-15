 package Controller;
 
 import Model.DetalleCatalogo;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("CodigoCatalogo")
 public class CodigoCatalogoResource
 {
   @Context
   private UriInfo context;
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id) throws Exception {
/* 45 */     DetalleCatalogo param = new DetalleCatalogo();
/* 46 */     param.selectCodigo(id);
/* 47 */     return param.hp.getResponse();
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\CodigoCatalogoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */