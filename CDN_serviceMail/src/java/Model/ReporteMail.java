 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 public class ReporteMail
 {
   int id;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/* 27 */   public Helper hp = new Helper();
/* 28 */   public ConexionOracle con = new ConexionOracle();
   
   public int getId() {
/* 31 */     return this.id;
   }
   
   public void setId(int id) {
/* 35 */     this.id = id;
   }
   
   public ResultSet select(String id, int pais, String fechaInicio, String fechaFin) throws SQLException, Exception {
/* 39 */     JSONObject jsonObj = new JSONObject();
/* 40 */     this.responseArray = new JSONArray();
     
/* 42 */     this.con.Conectar();
/* 43 */     String query = "SELECT A.*,B.*,TO_CHAR(TO_DATE(A.FECHA_ENVIO, 'DD/MM/YYYY'))AS FECHAENVIOMAIL FROM CDN_MAIL A INNER JOIN CDN_PAIS B ON B.CODIGO = A.PAIS_ID WHERE A.CORREO_CLIENTE IS NOT NULL ";
 
     
     try {
/* 47 */       if (id == null && pais == 0 && fechaInicio == null && fechaFin == null) {
         
/* 49 */         this.hp.setArrayResponse(200, this.responseArray);
       }
       else {
         
/* 53 */         if (id != null) {
/* 54 */           query = query + " AND A.NUMERO = " + id;
         }
/* 56 */         if (fechaInicio != null && fechaFin != null && pais != 0) {
/* 57 */           query = query + " AND B.ID = " + pais;
/* 58 */           query = query + " AND A.FECHA_ENVIO BETWEEN TO_DATE('" + fechaInicio + "', 'YYYY/MM/DD') AND TO_DATE('" + fechaFin + "', 'YYYY/MM/DD')+0.999 ";
         } else {
/* 60 */           query = query + " AND A.FECHA_ENVIO = SYSDATE ";
         } 
         
/* 63 */         this.rs = this.con.Consultar(query);
/* 64 */         ResultSetMetaData rsmd = this.rs.getMetaData();
/* 65 */         int columna = rsmd.getColumnCount();
         
/* 67 */         while (this.rs.next()) {
/* 68 */           JSONObject rows = new JSONObject();
/* 69 */           for (int i = 1; i <= columna; i++) {
/* 70 */             String columnaBD = rsmd.getColumnName(i);
/* 71 */             rows.put(columnaBD, this.rs.getString(columnaBD));
           } 
/* 73 */           this.responseArray.put(rows);
         } 
/* 75 */         this.hp.setArrayResponse(200, this.responseArray);
       } 
       
/* 78 */       this.con.CerrarConsulta();
/* 79 */     } catch (SQLException ex) {
/* 80 */       this.hp.setArrayResponse(400, this.responseArray);
/* 81 */       this.con.CerrarConsulta();
     } 
/* 83 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ReporteMail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */