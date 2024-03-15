 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
  
 public class TipoMensaje
 {
   int id;
   String nombre;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/* 28 */   public Helper hp = new Helper();
/* 29 */   public ConexionOracle con = new ConexionOracle();
   
   public int getId() {
/* 32 */     return this.id;
   }
   
   public void setId(int id) {
/* 36 */     this.id = id;
   }
   
   public String getNombre() {
/* 40 */     return this.nombre;
   }
   
   public void setNombre(String nombre) {
/* 44 */     this.nombre = nombre;
   }
   
   public ResultSet select(String id) throws SQLException, Exception {
/* 48 */     JSONObject jsonObj = new JSONObject();
/* 49 */     this.responseArray = new JSONArray();
     try {
/* 51 */       this.con.Conectar();
/* 52 */       String query = "SELECT * FROM CDN_TIPO_MENSAJE WHERE ESTADO=1";
/* 53 */       if (id != null) {
/* 54 */         query = query + " AND ID = " + id;
       }
       
/* 57 */       this.rs = this.con.Consultar(query);
/* 58 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 59 */       int columna = rsmd.getColumnCount();
       
/* 61 */       while (this.rs.next()) {
/* 62 */         JSONObject rows = new JSONObject();
/* 63 */         for (int i = 1; i <= columna; i++) {
/* 64 */           String columnaBD = rsmd.getColumnName(i);
/* 65 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 67 */         this.responseArray.put(rows);
       } 
/* 69 */       this.hp.setArrayResponse(200, this.responseArray);
/* 70 */       this.con.CerrarConsulta();
/* 71 */     } catch (SQLException ex) {
/* 72 */       this.hp.setArrayResponse(400, this.responseArray);
/* 73 */       this.con.CerrarConsulta();
     } 
/* 75 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\TipoMensaje.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */