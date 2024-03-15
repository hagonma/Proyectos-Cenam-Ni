 package Controller;
 
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 
 
 
 
 
 
 
 
 public class ConexionAS400
 {
   public Connection conexion;
   public PreparedStatement ps;
   public ResultSet rs;
   public CallableStatement cstmt;
/* 23 */   public String ip = "";
/* 24 */   public String port = "";
/* 25 */   public String bd = "";
/* 26 */   public String usr = "";
/* 27 */   public String pass = "";
 
 
 
   
   public Connection Conectar() throws Exception {
/* 33 */     String ip = "172.17.249.165", port = "1521", bd = "orcl", usr = "system", pass = "password";
     try {
/* 35 */       Class.forName("com.ibm.as400.access.AS400JDBCDriver");
/* 36 */       this.conexion = DriverManager.getConnection("jdbc:as400://172.20.5.100/;libraries=GUAV1,GUARDBV1,TFSOBMX1,QTEMP;prompt=false;naming=sql;errors=full", "PUSER", "CDNPR0JECT");
/* 37 */       System.out.println("Conexion a Base de Datos " + ip + " Ok");
/* 38 */       return this.conexion;
/* 39 */     } catch (Exception exc) {
/* 40 */       System.out.println("Error al tratar de abrir la base de Datos" + ip + " : " + exc);
/* 41 */       return null;
     } 
   }
 
 
   
   public Connection CerrarConsulta() throws SQLException {
     try {
/* 49 */       this.ps.close();
/* 50 */       this.rs.close();
/* 51 */       this.conexion.close();
/* 52 */       this.conexion = null;
/* 53 */       if (this.conexion == null) {
/* 54 */         System.out.println("La conexion de consulta fue cerrada");
       }
/* 56 */       return this.conexion;
/* 57 */     } catch (Exception exc) {
/* 58 */       System.out.println("Error al tratar de cerrar las conexiones: " + exc);
/* 59 */       return null;
     } 
   }
 
 
   
   public ResultSet Consultar(String SQL) throws SQLException {
/* 66 */     this.ps = null;
/* 67 */     this.rs = null;
     
     try {
/* 70 */       this.ps = this.conexion.prepareStatement(SQL);
/* 71 */       this.rs = this.ps.executeQuery();
       
/* 73 */       return this.rs;
/* 74 */     } catch (Exception exc) {
/* 75 */       System.out.println("Error al tratar de ejecutar el query: " + exc);
/* 76 */       return null;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ConexionAS400.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */