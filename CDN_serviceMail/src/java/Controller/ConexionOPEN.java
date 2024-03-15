 package Controller;
 
 import java.sql.CallableStatement;
 import java.sql.Connection;
 import java.sql.Driver;
 import java.sql.DriverManager;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import oracle.jdbc.driver.OracleDriver;
 
 
 
 
 
 
 
 
 
 public class ConexionOPEN
 {
   public Connection conexion;
   public Statement ps;
   public ResultSet rs;
   public CallableStatement cstmt;
/*  26 */   public String ip = "";
/*  27 */   public String port = "";
/*  28 */   public String bd = "";
/*  29 */   public String usr = "";
/*  30 */   public String pass = "";
 
 
 
   
   public Connection Conectar(String pais) throws Exception {
/*  36 */     DriverManager.registerDriver((Driver)new OracleDriver());
     
/*  38 */     if (this.ip == "") {
/*  39 */       if (pais.equals("504")) {
/*  40 */         this.ip = "172.18.125.180";
/*  41 */         this.port = "3871";
/*  42 */         this.bd = "OPENHND";
/*  43 */         this.usr = "CDNHN";
/*  44 */         this.pass = "C1@r0cdn#";
/*  45 */       } else if (pais.equals("505")) {
/*  46 */         this.ip = "172.24.4.228";
/*  47 */         this.port = "1521";
/*  48 */         this.bd = "SMARTFX";
/*  49 */         this.usr = "CLAROVIDEO";
/*  50 */         this.pass = "claro2010";
/*  51 */       } else if (pais.equals("507")) {
/*  52 */         this.ip = "172.18.125.180";
/*  53 */         this.port = "3871";
/*  54 */         this.bd = "SMARTPNM";
/*  55 */         this.usr = "CDNPA";
/*  56 */         this.pass = "C1@r0cdn#";
       } 
     }
 
 
     
/*  62 */     String url = new String("jdbc:oracle:thin:@" + this.ip + ":" + this.port + "/" + this.bd);
     try {
/*  64 */       this.conexion = DriverManager.getConnection(url, this.usr, this.pass);
/*  65 */       System.out.println("Conexion a Base de Datos " + this.ip + " Ok");
/*  66 */       return this.conexion;
/*  67 */     } catch (Exception exc) {
       
/*  69 */       System.out.println("Error al tratar de abrir la base de Datos" + this.ip + " : " + exc);
/*  70 */       return null;
     } 
   }
 
 
   
   public void CerrarConsulta() throws SQLException {
     try {
/*  78 */       this.ps.close();
/*  79 */       this.rs.close();
/*  80 */       if (this.ps == null && this.rs == null) {
/*  81 */         System.out.println("La conexion de consulta fue cerrada");
       }
/*  83 */     } catch (Exception exc) {
/*  84 */       System.out.println("Error al tratar de cerrar las conexiones: " + exc);
     } 
   }
 
   
   public void CerrarConexion() throws SQLException {
     try {
/*  91 */       this.conexion.close();
/*  92 */       this.conexion = null;
/*  93 */       if (this.conexion == null) {
/*  94 */         System.out.println("La conexion de consulta fue cerrada");
       }
/*  96 */     } catch (Exception exc) {
/*  97 */       System.out.println("Error al tratar de cerrar las conexiones: " + exc);
     } 
   }
 
 
   
   public ResultSet Consultar(String SQL) throws SQLException {
/* 104 */     this.ps = null;
/* 105 */     this.rs = null;
     
     try {
/* 108 */       this.ps = this.conexion.createStatement(1004, 1007);
/* 109 */       this.rs = this.ps.executeQuery(SQL);
       
/* 111 */       return this.rs;
/* 112 */     } catch (Exception exc) {
/* 113 */       System.out.println("Error al tratar de ejecutar el query: " + exc);
/* 114 */       return null;
     } 
   }
 
 
   
   public ResultSet ConsultarDesglose(String SQL) throws SQLException {
/* 121 */     this.ps = null;
/* 122 */     this.rs = null;
     
/* 124 */     this.ps = this.conexion.createStatement(1004, 1007);
/* 125 */     this.rs = this.ps.executeQuery(SQL);
     
/* 127 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ConexionOPEN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */