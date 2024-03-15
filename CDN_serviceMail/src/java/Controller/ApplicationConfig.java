 package Controller;
 import Controller.CatalogoResource;
 import Controller.CodigoCatalogoResource;
 import Controller.Dashboard;
 import Controller.DesgloseSaldoFijoResource;
 import Controller.DesgloseSaldoResource;
 import Controller.LDAPResource;
 import Controller.PaisResource;
 import Controller.PruebaMailResource;
 import Controller.ReporteSMSResource;
 import Controller.SendMailFijoResource;
 import Controller.TipoMensajeResource;
 import java.util.HashSet;
 import java.util.Set;
 import javax.ws.rs.ApplicationPath;
 import javax.ws.rs.core.Application;
 
 @ApplicationPath("webresources")
 public class ApplicationConfig extends Application {
   public Set<Class<?>> getClasses() {
/* 20 */     Set<Class<?>> resources = new HashSet<>();
/* 21 */     addRestResourceClasses(resources);
/* 22 */     return resources;
   }
 
 
 
 
 
   
   private void addRestResourceClasses(Set<Class<?>> resources) {
/* 31 */     resources.add(Controller.AccesoResource.class);
/* 32 */
 resources.add(Controller.CatalogoResource.class);
/* 33 */
 resources.add(Controller.CodigoCatalogoResource.class);
/* 34 */
 resources.add(Controller.Dashboard.class);
/* 35 */
 resources.add(Controller.DesgloseSaldoFijoResource.class);
/* 36 */
 resources.add(Controller.DesgloseSaldoResource.class);
/* 37 */
 resources.add(Controller.DetalleCatalogoResource.class);
/* 38 */
 resources.add(Controller.EnviarSMSGenericoResource.class);
/* 39 */
 resources.add(Controller.LDAPResource.class);
/* 40 */
 resources.add(Controller.MensajeResource.class);
/* 41 */
 resources.add(Controller.ModificacionRentaResource.class);
/* 42 */
 resources.add(Controller.PaisResource.class);
/* 43 */
 resources.add(Controller.PantallaResource.class);
/* 44 */
 resources.add(Controller.ParametroResource.class);
/* 45 */
 resources.add(Controller.PruebaMailResource.class);
/* 46 */
 resources.add(Controller.ReporteBitacoraResource.class);
/* 47 */
 resources.add(Controller.ReporteMailResource.class);
/* 48 */
 resources.add(Controller.ReporteSMSResource.class);
/* 49 */
 resources.add(Controller.RolResource.class);
/* 50 */
 resources.add(Controller.SendMailCRResource.class);
/* 51 */
 resources.add(Controller.SendMailFijoResource.class);
/* 52 */
 resources.add(Controller.SendMailResource.class);
/* 53 */
 resources.add(Controller.TipoMensajeResource.class);
/* 54 */
 resources.add(Controller.UsuarioResource.class);
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ApplicationConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */