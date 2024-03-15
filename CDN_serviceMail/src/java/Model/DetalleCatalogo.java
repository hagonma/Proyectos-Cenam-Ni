 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
  
 public class DetalleCatalogo
 {
/*  23 */   public Helper hp = new Helper();
/*  24 */   public ConexionOracle con = new ConexionOracle();
   
   public ResultSet rs;
   
   public PreparedStatement ps;
   public JSONArray responseArray;
   
   public int getId() {
/*  32 */     return this.id;
   }
   int id; int estado; String codigo;
   public void setId(int id) {
/*  36 */     this.id = id;
   }
   
   public int getEstado() {
/*  40 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/*  44 */     this.estado = estado;
   }
   
   public String getCodigo() {
/*  48 */     return this.codigo;
   }
   
   public void setCodigo(String codigo) {
/*  52 */     this.codigo = codigo;
   }
   
   public ResultSet select(String id) throws SQLException, Exception {
/*  56 */     JSONObject jsonObj = new JSONObject();
/*  57 */     this.responseArray = new JSONArray();
     try {
/*  59 */       this.con.Conectar();
/*  60 */       String query = "SELECT * FROM CDN_DETALLE_MAIL WHERE ID_DETALLE <> 0";
/*  61 */       if (id != null) {
/*  62 */         query = query + " AND ID_DETALLE = " + id;
       }
       
/*  65 */       this.rs = this.con.Consultar(query);
/*  66 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  67 */       int columna = rsmd.getColumnCount();
       
/*  69 */       while (this.rs.next()) {
/*  70 */         JSONObject rows = new JSONObject();
/*  71 */         for (int i = 1; i <= columna; i++) {
/*  72 */           String columnaBD = rsmd.getColumnName(i);
/*  73 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/*  75 */         this.responseArray.put(rows);
       } 
/*  77 */       this.hp.setArrayResponse(200, this.responseArray);
/*  78 */       this.con.CerrarConsulta();
/*  79 */     } catch (SQLException ex) {
/*  80 */       this.hp.setArrayResponse(400, this.responseArray);
/*  81 */       this.con.CerrarConsulta();
     } 
/*  83 */     return this.rs;
   }
   
   public ResultSet selectCodigo(String id) throws SQLException, Exception {
/*  87 */     JSONObject jsonObj = new JSONObject();
/*  88 */     this.responseArray = new JSONArray();
     try {
/*  90 */       this.con.Conectar();
/*  91 */       String query = "SELECT DISTINCT(CODIGO) FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1";
/*  92 */       if (id != null) {
/*  93 */         query = query + " AND CODIGO = " + id;
       }
       
/*  96 */       this.rs = this.con.Consultar(query);
/*  97 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  98 */       int columna = rsmd.getColumnCount();
       
/* 100 */       while (this.rs.next()) {
/* 101 */         JSONObject rows = new JSONObject();
/* 102 */         for (int i = 1; i <= columna; i++) {
/* 103 */           String columnaBD = rsmd.getColumnName(i);
/* 104 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 106 */         this.responseArray.put(rows);
       } 
/* 108 */       this.hp.setArrayResponse(200, this.responseArray);
/* 109 */       this.con.CerrarConsulta();
/* 110 */     } catch (SQLException ex) {
/* 111 */       this.hp.setArrayResponse(400, this.responseArray);
/* 112 */       this.con.CerrarConsulta();
     } 
/* 114 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\DetalleCatalogo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */