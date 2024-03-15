 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 import org.jsoup.Jsoup;
 import org.jsoup.safety.Whitelist;
 
 
 
 
 
 
 
 
 
 public class Rol
 {
   int id;
   int pais;
   int estado;
   String nombre;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  31 */   public Helper hp = new Helper();
/*  32 */   public ConexionOracle con = new ConexionOracle();
   
   public int getId() {
/*  35 */     return this.id;
   }
   
   public void setId(int id) {
/*  39 */     this.id = id;
   }
   
   public int getPais() {
/*  43 */     return this.pais;
   }
   
   public int getEstado() {
/*  47 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/*  51 */     this.estado = estado;
   }
   
   public void setPais(int pais) {
/*  55 */     this.pais = pais;
   }
   
   public String getNombre() {
/*  59 */     return this.nombre;
   }
   
   public void setNombre(String nombre) {
/*  63 */     this.nombre = Jsoup.clean(nombre, Whitelist.basic());
/*  64 */     this.nombre = this.nombre.replaceAll("<[^>]*>", "");
   }
   
   public ResultSet select(String id, int pais) throws SQLException, Exception {
/*  68 */     JSONObject jsonObj = new JSONObject();
/*  69 */     this.responseArray = new JSONArray();
     try {
/*  71 */       this.con.Conectar();
/*  72 */       String query = "SELECT A.ID,A.NOMBRE,A.PAIS_ID,B.NOMBRE AS PAIS FROM CDN_ROL A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.ID WHERE A.ESTADO=1";
/*  73 */       if (id != null) {
/*  74 */         query = query + " AND A.ID = " + id;
       }
/*  76 */       if (pais != 0) {
/*  77 */         query = query + " AND A.PAIS_ID = " + pais;
       }
/*  79 */       this.rs = this.con.Consultar(query);
/*  80 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  81 */       int columna = rsmd.getColumnCount();
       
/*  83 */       while (this.rs.next()) {
/*  84 */         JSONObject rows = new JSONObject();
/*  85 */         for (int i = 1; i <= columna; i++) {
/*  86 */           String columnaBD = rsmd.getColumnName(i);
/*  87 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/*  89 */         this.responseArray.put(rows);
       } 
/*  91 */       this.hp.setArrayResponse(200, this.responseArray);
/*  92 */       this.con.CerrarConsulta();
/*  93 */     } catch (SQLException ex) {
/*  94 */       this.hp.setArrayResponse(400, this.responseArray);
/*  95 */       this.con.CerrarConsulta();
     } 
/*  97 */     return this.rs;
   }
 
   
   public int insert() throws Exception, SQLException {
/* 102 */     int retorno = 0;
/* 103 */     JSONObject jsonObj = new JSONObject();
     try {
/* 105 */       this.con.Conectar();
/* 106 */       String query = "INSERT INTO CDN_ROL(NOMBRE,PAIS_ID,ESTADO) VALUES (?,?,?)";
/* 107 */       this.ps = this.con.conexion.prepareStatement(query);
/* 108 */       this.ps.setString(1, getNombre());
/* 109 */       this.ps.setInt(2, getPais());
/* 110 */       this.ps.setInt(3, getEstado());
       
/* 112 */       int executar = this.ps.executeUpdate();
/* 113 */       retorno = executar;
/* 114 */       this.con.conexion.close();
/* 115 */       jsonObj.put("TransactSQL", true);
/* 116 */       this.hp.setResponse(200, jsonObj);
/* 117 */     } catch (SQLException ex) {
/* 118 */       this.con.conexion.close();
/* 119 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 120 */       this.hp.setResponse(400, jsonObj);
/* 121 */       retorno = 0;
     } 
/* 123 */     return retorno;
   }
 
   
   public int update() throws Exception, SQLException {
/* 128 */     int retorno = 0;
/* 129 */     JSONObject jsonObj = new JSONObject();
     try {
/* 131 */       this.con.Conectar();
/* 132 */       String query = "UPDATE CDN_ROL SET NOMBRE =?,PAIS_ID=? WHERE ID = ?";
/* 133 */       this.ps = this.con.conexion.prepareStatement(query);
/* 134 */       this.ps.setString(1, getNombre());
/* 135 */       this.ps.setInt(2, getPais());
/* 136 */       this.ps.setInt(3, getId());
/* 137 */       int executar = this.ps.executeUpdate();
/* 138 */       retorno = executar;
/* 139 */       this.con.conexion.close();
/* 140 */       jsonObj.put("TransactSQL", true);
/* 141 */       this.hp.setResponse(200, jsonObj);
/* 142 */     } catch (SQLException ex) {
/* 143 */       this.con.conexion.close();
/* 144 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 145 */       this.hp.setResponse(400, jsonObj);
/* 146 */       retorno = 0;
     } 
/* 148 */     return retorno;
   }
 
   
   public int delete() throws Exception {
/* 153 */     int retorno = 0;
/* 154 */     JSONObject jsonObj = new JSONObject();
     try {
/* 156 */       this.con.Conectar();
/* 157 */       String query = "UPDATE CDN_ROL SET ESTADO =?  WHERE ID = ?";
/* 158 */       this.ps = this.con.conexion.prepareStatement(query);
/* 159 */       this.ps.setInt(1, getEstado());
/* 160 */       this.ps.setInt(2, getId());
/* 161 */       int executar = this.ps.executeUpdate();
/* 162 */       retorno = executar;
/* 163 */       this.con.conexion.close();
/* 164 */       jsonObj.put("TransactSQL", true);
/* 165 */       this.hp.setResponse(200, jsonObj);
/* 166 */     } catch (SQLException ex) {
/* 167 */       this.con.conexion.close();
/* 168 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 169 */       this.hp.setResponse(400, jsonObj);
/* 170 */       retorno = 0;
     } 
/* 172 */     return retorno;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Rol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */