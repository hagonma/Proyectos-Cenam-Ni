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
 
 
 
 
 
 
 public class Parametro
 {
   String nombre;
   String descripcion;
   String valor;
   int id;
   int pais;
   int estado;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  30 */   public Helper hp = new Helper();
/*  31 */   public ConexionOracle con = new ConexionOracle();
   
   public String getNombre() {
/*  34 */     return this.nombre;
   }
   
   public void setNombre(String nombre) {
/*  38 */     this.nombre = Jsoup.clean(nombre, Whitelist.basic());
/*  39 */     this.nombre = this.nombre.replaceAll("<[^>]*>", "");
   }
   
   public int getId() {
/*  43 */     return this.id;
   }
   
   public void setId(int id) {
/*  47 */     this.id = id;
   }
   
   public String getDescripcion() {
/*  51 */     return this.descripcion;
   }
   
   public void setDescripcion(String descripcion) {
/*  55 */     this.descripcion = Jsoup.clean(descripcion, Whitelist.basic());
/*  56 */     this.descripcion = this.descripcion.replaceAll("<[^>]*>", "");
   }
   
   public String getValor() {
/*  60 */     return this.valor;
   }
   
   public void setValor(String valor) {
/*  64 */     this.valor = Jsoup.clean(valor, Whitelist.basic());
/*  65 */     this.valor = this.valor.replaceAll("<[^>]*>", "");
   }
   
   public int getPais() {
/*  69 */     return this.pais;
   }
   
   public void setPais(int pais) {
/*  73 */     this.pais = pais;
   }
   
   public int getEstado() {
/*  77 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/*  81 */     this.estado = estado;
   }
   
   public ResultSet select(String id, int pais) throws SQLException, Exception {
/*  85 */     JSONObject jsonObj = new JSONObject();
/*  86 */     this.responseArray = new JSONArray();
     try {
/*  88 */       this.con.Conectar();
/*  89 */       String query = "SELECT A.ID,A.NOMBRE,A.DESCRIPCION,A.VALOR,A.PAIS_ID,A.ESTADO,B.NOMBRE AS PAIS FROM CDN_PARAMETRO A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.ID WHERE A.ESTADO=1";
/*  90 */       if (id != null) {
/*  91 */         query = query + " AND A.ID = " + id;
       }
/*  93 */       if (pais != 0) {
/*  94 */         query = query + " AND A.PAIS_ID = " + pais;
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
 
   
   public int insert() throws Exception, SQLException {
/* 119 */     int retorno = 0;
/* 120 */     JSONObject jsonObj = new JSONObject();
     try {
/* 122 */       this.con.Conectar();
/* 123 */       String query = "INSERT INTO CDN_PARAMETRO(NOMBRE,DESCRIPCION,VALOR,PAIS_ID,ESTADO) VALUES (?,?,?,?,?)";
/* 124 */       this.ps = this.con.conexion.prepareStatement(query);
/* 125 */       this.ps.setString(1, getNombre());
/* 126 */       this.ps.setString(2, getDescripcion());
/* 127 */       this.ps.setString(3, getValor());
/* 128 */       this.ps.setInt(4, getPais());
/* 129 */       this.ps.setInt(5, getEstado());
       
/* 131 */       int executar = this.ps.executeUpdate();
/* 132 */       retorno = executar;
/* 133 */       this.con.conexion.close();
/* 134 */       jsonObj.put("TransactSQL", true);
/* 135 */       this.hp.setResponse(200, jsonObj);
/* 136 */     } catch (SQLException ex) {
/* 137 */       this.con.conexion.close();
/* 138 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 139 */       this.hp.setResponse(400, jsonObj);
/* 140 */       retorno = 0;
     } 
/* 142 */     return retorno;
   }
 
   
   public int update() throws Exception, SQLException {
/* 147 */     int retorno = 0;
/* 148 */     JSONObject jsonObj = new JSONObject();
     try {
/* 150 */       this.con.Conectar();
/* 151 */       String query = "UPDATE CDN_PARAMETRO SET NOMBRE=?, DESCRIPCION=?, VALOR=?, PAIS_ID=? WHERE ID = ?";
/* 152 */       this.ps = this.con.conexion.prepareStatement(query);
/* 153 */       this.ps.setString(1, getNombre());
/* 154 */       this.ps.setString(2, getDescripcion());
/* 155 */       this.ps.setString(3, getValor());
/* 156 */       this.ps.setInt(4, getPais());
/* 157 */       this.ps.setInt(5, getId());
/* 158 */       int executar = this.ps.executeUpdate();
/* 159 */       retorno = executar;
/* 160 */       this.con.conexion.close();
/* 161 */       jsonObj.put("TransactSQL", true);
/* 162 */       this.hp.setResponse(200, jsonObj);
/* 163 */     } catch (SQLException ex) {
/* 164 */       this.con.conexion.close();
/* 165 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 166 */       this.hp.setResponse(400, jsonObj);
/* 167 */       retorno = 0;
     } 
/* 169 */     return retorno;
   }
 
   
   public int delete() throws Exception {
/* 174 */     int retorno = 0;
/* 175 */     JSONObject jsonObj = new JSONObject();
     try {
/* 177 */       this.con.Conectar();
/* 178 */       String query = "UPDATE CDN_PARAMETRO SET ESTADO =?  WHERE ID = ?";
/* 179 */       this.ps = this.con.conexion.prepareStatement(query);
/* 180 */       this.ps.setInt(1, getEstado());
/* 181 */       this.ps.setInt(2, getId());
/* 182 */       int executar = this.ps.executeUpdate();
/* 183 */       retorno = executar;
/* 184 */       this.con.conexion.close();
/* 185 */       jsonObj.put("TransactSQL", true);
/* 186 */       this.hp.setResponse(200, jsonObj);
/* 187 */     } catch (SQLException ex) {
/* 188 */       this.con.conexion.close();
/* 189 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 190 */       this.hp.setResponse(400, jsonObj);
/* 191 */       retorno = 0;
     } 
/* 193 */     return retorno;
   }
   
   public ResultSet getByName(String nombre, int pais) throws SQLException, Exception {
/* 197 */     JSONObject jsonObj = new JSONObject();
/* 198 */     this.responseArray = new JSONArray();
     try {
/* 200 */       this.con.Conectar();
/* 201 */       String query = "SELECT * FROM CDN_PARAMETRO WHERE ESTADO = 1 AND NOMBRE = '" + nombre + "'";
/* 202 */       if (pais != 0) {
/* 203 */         query = query + " AND PAIS_ID = " + pais;
       }
/* 205 */       this.rs = this.con.Consultar(query);
/* 206 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 207 */       int columna = rsmd.getColumnCount();
       
/* 209 */       while (this.rs.next()) {
/* 210 */         JSONObject rows = new JSONObject();
/* 211 */         for (int i = 1; i <= columna; i++) {
/* 212 */           String columnaBD = rsmd.getColumnName(i);
/* 213 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 215 */         this.responseArray.put(rows);
       } 
/* 217 */       this.hp.setArrayResponse(200, this.responseArray);
/* 218 */       this.con.CerrarConsulta();
/* 219 */     } catch (SQLException ex) {
/* 220 */       this.hp.setArrayResponse(400, this.responseArray);
/* 221 */       this.con.CerrarConsulta();
     } 
/* 223 */     return this.rs;
   }
   
   public String getParametro(String nombre, String pais) throws SQLException, Exception {
/* 227 */     String dato = "";
/* 228 */     String equipos = "";
     try {
/* 230 */       this.con.Conectar();
/* 231 */       String query = "SELECT VALOR FROM CDN_PARAMETRO WHERE NOMBRE = '" + nombre + "' AND PAIS_ID = '" + pais + "'";
/* 232 */       ResultSet resultado = this.con.Consultar(query);
       
/* 234 */       while (resultado.next()) {
/* 235 */         dato = resultado.getString("VALOR");
       }
/* 237 */       if (dato.isEmpty()) {
/* 238 */         equipos = "0";
       } else {
/* 240 */         equipos = dato;
       } 
/* 242 */       this.con.CerrarConsulta();
/* 243 */       return equipos;
/* 244 */     } catch (SQLException e) {
/* 245 */       this.con.CerrarConsulta();
/* 246 */       return null;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Parametro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */