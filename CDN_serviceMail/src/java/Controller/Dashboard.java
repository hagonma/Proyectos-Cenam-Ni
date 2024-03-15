 package Controller;
 
 import Model.DashboardModel;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import javax.ws.rs.GET;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("genericDashboard")
 public class Dashboard
 {
   @Context
   private UriInfo context;
   
   @GET
   @Produces({"application/json"})
   @Path("/Day")
   public String getJsonDay() {
/* 57 */     DashboardModel dashboard = new DashboardModel();
     try {
/* 59 */       dashboard.select("DIA");
/* 60 */       return dashboard.hp.getResponse();
/* 61 */     } catch (Exception ex) {
/* 62 */       Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 63 */       return null;
     } 
   }
   
   @GET
   @Produces({"application/json"})
   @Path("/Month")
   public String getJsonMonth() {
/* 71 */     DashboardModel dashboard = new DashboardModel();
     try {
/* 73 */       dashboard.select("MES");
/* 74 */       return dashboard.hp.getResponse();
/* 75 */     } catch (Exception ex) {
/* 76 */       Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 77 */       return null;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\Dashboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */