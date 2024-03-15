 package Controller;
 
 import java.io.File;
 import java.net.URL;
 import java.net.URLClassLoader;
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.Driver;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.Properties;
 import oracle.jdbc.driver.OracleDriver;
 
 
 public class ConexionOracle
 {
   public Connection conexion;
   public PreparedStatement ps;
   public ResultSet rs;
   public CallableStatement cstmt;
/*  27 */   public String ip = "";
/*  28 */   public String port = "";
/*  29 */   public String bd = "";
/*  30 */   public String usr = "";
/*  31 */   public String pass = "";
/*  32 */   public String error = "";
 

   public Connection Conectar() throws Exception {

/*  63 */     DriverManager.registerDriver((Driver)new OracleDriver());
     
/*  65 */     if (this.ip == "") {
 
 
       
     this.ip = "172.18.125.181";
     this.port = "3871";
      this.bd = "CDNRG";
      this.usr = "CDNRG";
      this.pass = "C1@r0cdn#";

/*this.ip = "oracledes02-scan";
                this.port = "3871";
                this.bd = "DMISC";
                this.usr = "MACTPRE";
                this.pass = "asdowe25ds";*/
     } 
 
 
     
/*  80 */     String url = new String("jdbc:oracle:thin:@" + this.ip + ":" + this.port + "/" + this.bd);
     try {
/*  82 */       this.conexion = DriverManager.getConnection(url, this.usr, this.pass);
       
/*  84 */       return this.conexion;
/*  85 */     } catch (Exception exc) {
/*  86 */       System.out.println("Error al tratar de abrir la base de Datos" + this.bd + " : " + exc);
       
/*  88 */       return null;
     } 
   }
 
 
 
   
   public Connection CerrarConsulta() throws SQLException {
     try {
/*  97 */       this.ps.close();
/*  98 */       this.rs.close();
/*  99 */       this.conexion.close();
/* 100 */       this.conexion = null;
 
 
       
/* 104 */       return this.conexion;
/* 105 */     } catch (Exception exc) {
       
/* 107 */       return null;
     } 
   }
 
 
   
   public ResultSet Consultar(String SQL) throws SQLException {
/* 114 */     this.ps = null;
/* 115 */     this.rs = null;
     
     try {
/* 118 */       this.ps = this.conexion.prepareStatement(SQL);
/* 119 */       this.rs = this.ps.executeQuery();
       
/* 121 */       return this.rs;
/* 122 */     } catch (Exception exc) {
/* 123 */       System.out.println("Error al tratar de ejecutar el query: " + exc);
/* 124 */       return null;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ConexionOracle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */