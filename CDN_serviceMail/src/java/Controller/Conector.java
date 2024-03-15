 package Controller;
 
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.Statement;
 
 
 
 
 
 
 public class Conector
 {
   String IP;
   String USER;
   String PASS;
   String PORT;
   String SERVICE_NAME;
   Connection c1;
   
   public Conector(String ip, String user, String password, String port, String service_name) {
/*  24 */     this.IP = ip;
/*  25 */     this.USER = user;
/*  26 */     this.PASS = password;
/*  27 */     this.PORT = port;
/*  28 */     this.SERVICE_NAME = service_name;
   }
 
   
   public void conectar() {
     try {
/*  34 */       Class.forName("oracle.jdbc.OracleDriver").newInstance();
/*  35 */       this.c1 = DriverManager.getConnection("jdbc:oracle:thin:@" + this.IP + ":" + this.PORT + "/" + this.SERVICE_NAME, this.USER, this.PASS);
       
/*  37 */       System.out.println("Conectado");
     }
/*  39 */     catch (Exception e) {
/*  40 */       System.out.println(e.getMessage());
     } 
   }
 
 
   
   public void desconectar() {
     try {
/*  48 */       this.c1.commit();
/*  49 */       this.c1.close();
/*  50 */       System.out.println("Desconectado");
/*  51 */     } catch (Exception e) {
/*  52 */       System.out.println(e.getMessage());
     } 
   }
 
 
   
   public void Transacciones(String SQL) {
     try {
/*  60 */       conectar();
/*  61 */       Statement st = this.c1.createStatement();
/*  62 */       st.executeUpdate(SQL);
/*  63 */       System.out.println("Transaccion exitosa");
/*  64 */       desconectar();
/*  65 */     } catch (Exception e) {
/*  66 */       System.out.println(e.getMessage());
     } 
   }
 
   
   public void rollBCK() {
     try {
/*  73 */       this.c1.rollback();
/*  74 */     } catch (Exception exception) {}
   }
 
 
   
   public String[][] consultas(String SQL) {
/*  80 */     String[][] datos = (String[][])null;
/*  81 */     int filas = 0, columnas = 0, i = 0;
 
     
     try {
/*  85 */       Statement st = this.c1.createStatement(1004, 1007);
/*  86 */       ResultSet rs = st.executeQuery(SQL);
/*  87 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  88 */       columnas = rsmd.getColumnCount();
/*  89 */       rs.last();
/*  90 */       filas = rs.getRow();
/*  91 */       rs.beforeFirst();
       
/*  93 */       datos = new String[filas][columnas];
       
/*  95 */       while (rs.next())
       {
/*  97 */         for (int j = 0; j < columnas; j++) {
/*  98 */           datos[i][j] = rs.getString(j + 1);
         }
         
/* 101 */         i++;
       }
     
     }
/* 105 */     catch (Exception e) {
/* 106 */       System.out.println(e.getMessage());
/* 107 */       desconectar();
     } 
     
/* 110 */     return datos;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\Conector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */