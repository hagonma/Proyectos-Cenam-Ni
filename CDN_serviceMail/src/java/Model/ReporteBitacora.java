 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 public class ReporteBitacora
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
   
   public ResultSet select(String id, int pais) throws SQLException, Exception {
/* 39 */     JSONObject jsonObj = new JSONObject();
/* 40 */     this.responseArray = new JSONArray();
     try {
/* 42 */       this.con.Conectar();
/* 43 */       String query = "SELECT A.VALOR AS NUMERO,A.DESCRIPCION,A.FECHA_HORA,B.NOMBRE FROM CDN_BITACORA A INNER JOIN CDN_PAIS B ON B.ID = A.PAIS_ID WHERE A.ID <> 0";
       
/* 45 */       if (id != null) {
/* 46 */         query = query + " AND A.NUMERO = " + id;
       }
/* 48 */       if (pais != 0) {
/* 49 */         query = query + " AND B.ID = " + pais;
       }
       
/* 52 */       query = query + " ORDER BY A.FECHA_HORA DESC";
       
/* 54 */       this.rs = this.con.Consultar(query);
/* 55 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 56 */       int columna = rsmd.getColumnCount();
       
/* 58 */       while (this.rs.next()) {
/* 59 */         JSONObject rows = new JSONObject();
/* 60 */         for (int i = 1; i <= columna; i++) {
/* 61 */           String columnaBD = rsmd.getColumnName(i);
/* 62 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 64 */         this.responseArray.put(rows);
       } 
/* 66 */       this.hp.setArrayResponse(200, this.responseArray);
/* 67 */       this.con.CerrarConsulta();
/* 68 */     } catch (SQLException ex) {
/* 69 */       this.hp.setArrayResponse(400, this.responseArray);
/* 70 */       this.con.CerrarConsulta();
     } 
/* 72 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ReporteBitacora.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */