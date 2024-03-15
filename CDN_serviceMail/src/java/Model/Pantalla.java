 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 public class Pantalla
 {
   int id;
   int estado;
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
   
   public int getEstado() {
/* 40 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/* 44 */     this.estado = estado;
   }
   
   public String getNombre() {
/* 48 */     return this.nombre;
   }
   
   public void setNombre(String nombre) {
/* 52 */     this.nombre = nombre;
   }
   
   public ResultSet select(String id) throws SQLException, Exception {
/* 56 */     JSONObject jsonObj = new JSONObject();
/* 57 */     this.responseArray = new JSONArray();
     try {
/* 59 */       this.con.Conectar();
/* 60 */       String query = "SELECT * FROM CDN_PANTALLA WHERE ESTADO=1";
/* 61 */       if (id != null) {
/* 62 */         query = query + " AND ID = " + id;
       }
       
/* 65 */       this.rs = this.con.Consultar(query);
/* 66 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 67 */       int columna = rsmd.getColumnCount();
       
/* 69 */       while (this.rs.next()) {
/* 70 */         JSONObject rows = new JSONObject();
/* 71 */         for (int i = 1; i <= columna; i++) {
/* 72 */           String columnaBD = rsmd.getColumnName(i);
/* 73 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 75 */         this.responseArray.put(rows);
       } 
/* 77 */       this.hp.setArrayResponse(200, this.responseArray);
/* 78 */       this.con.CerrarConsulta();
/* 79 */     } catch (SQLException ex) {
/* 80 */       this.hp.setArrayResponse(400, this.responseArray);
/* 81 */       this.con.CerrarConsulta();
     } 
/* 83 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Pantalla.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */