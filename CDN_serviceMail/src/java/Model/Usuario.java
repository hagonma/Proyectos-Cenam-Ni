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
 
 public class Usuario
 {
   int id;
   int rol;
   int pais;
   int estado;
   String nombre;
   String username;
   String password;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  26 */   public Helper hp = new Helper();
/*  27 */   public ConexionOracle con = new ConexionOracle();
 
   
   public int getId() {
/*  31 */     return this.id;
   }
   
   public void setId(int id) {
/*  35 */     this.id = id;
   }
   
   public int getRol() {
/*  39 */     return this.rol;
   }
   
   public void setRol(int rol) {
/*  43 */     this.rol = rol;
   }
   
   public int getPais() {
/*  47 */     return this.pais;
   }
   
   public void setPais(int pais) {
/*  51 */     this.pais = pais;
   }
   
   public int getEstado() {
/*  55 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/*  59 */     this.estado = estado;
   }
   
   public String getNombre() {
/*  63 */     return this.nombre;
   }
   
   public void setNombre(String nombre) {
/*  67 */     this.nombre = Jsoup.clean(nombre, Whitelist.basic());
/*  68 */     this.nombre = this.nombre.replaceAll("<[^>]*>", "");
   }
   
   public String getUsername() {
/*  72 */     return this.username;
   }
   
   public void setUsername(String username) {
/*  76 */     this.username = Jsoup.clean(username, Whitelist.basic());
/*  77 */     this.username = this.username.replaceAll("<[^>]*>", "");
   }
   
   public String getPassword() {
/*  81 */     return this.password;
   }
   
   public void setPassword(String password) {
/*  85 */     this.password = Jsoup.clean(password, Whitelist.basic());
/*  86 */     this.password = this.password.replaceAll("<[^>]*>", "");
   }
   
   public ResultSet select(String id, int pais, int rol, String nombre) throws SQLException, Exception {
/*  90 */     JSONObject jsonObj = new JSONObject();
/*  91 */     this.responseArray = new JSONArray();
     try {
/*  93 */       this.con.Conectar();
/*  94 */       String query = "SELECT A.ID,A.NOMBRE,A.USERNAME,A.PAIS_ID,A.ROL_ID,B.NOMBRE AS PAIS,C.NOMBRE AS ROL FROM CDN_USUARIO A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.ID INNER JOIN CDN_ROL C ON A.ROL_ID = C.ID WHERE A.ESTADO=1";
/*  95 */       if (id != null) {
/*  96 */         query = query + " AND A.ID = " + id;
       }
/*  98 */       if (pais != 0) {
/*  99 */         query = query + " AND A.PAIS_ID = " + pais;
       }
/* 101 */       if (rol != 0) {
/* 102 */         query = query + " AND A.ROL_ID = " + rol;
       }
/* 104 */       if (nombre != null) {
/* 105 */         query = query + " AND A.NOMBRE = " + id;
       }
/* 107 */       this.rs = this.con.Consultar(query);
/* 108 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 109 */       int columna = rsmd.getColumnCount();
       
/* 111 */       while (this.rs.next()) {
/* 112 */         JSONObject rows = new JSONObject();
/* 113 */         for (int i = 1; i <= columna; i++) {
/* 114 */           String columnaBD = rsmd.getColumnName(i);
/* 115 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 117 */         this.responseArray.put(rows);
       } 
/* 119 */       this.hp.setArrayResponse(200, this.responseArray);
/* 120 */       this.con.CerrarConsulta();
/* 121 */     } catch (SQLException ex) {
/* 122 */       this.hp.setArrayResponse(400, this.responseArray);
/* 123 */       this.con.CerrarConsulta();
     } 
/* 125 */     return this.rs;
   }
 
   
   public int insert() throws Exception, SQLException {
/* 130 */     int retorno = 0;
/* 131 */     JSONObject jsonObj = new JSONObject();
     try {
/* 133 */       this.con.Conectar();
/* 134 */       String query = "INSERT INTO CDN_USUARIO(NOMBRE,USERNAME,PAIS_ID,ROL_ID,ESTADO) VALUES (?,?,?,?,?)";
/* 135 */       this.ps = this.con.conexion.prepareStatement(query);
/* 136 */       this.ps.setString(1, getNombre());
/* 137 */       this.ps.setString(2, getUsername());
/* 138 */       this.ps.setInt(3, getPais());
/* 139 */       this.ps.setInt(4, getRol());
/* 140 */       this.ps.setInt(5, getEstado());
       
/* 142 */       int executar = this.ps.executeUpdate();
/* 143 */       retorno = executar;
/* 144 */       this.con.conexion.close();
/* 145 */       jsonObj.put("TransactSQL", true);
/* 146 */       this.hp.setResponse(200, jsonObj);
/* 147 */     } catch (SQLException ex) {
/* 148 */       this.con.conexion.close();
/* 149 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 150 */       this.hp.setResponse(400, jsonObj);
/* 151 */       retorno = 0;
     } 
/* 153 */     return retorno;
   }
 
   
   public int update() throws Exception, SQLException {
/* 158 */     int retorno = 0;
/* 159 */     JSONObject jsonObj = new JSONObject();
     try {
/* 161 */       this.con.Conectar();
/* 162 */       String query = "UPDATE CDN_USUARIO SET NOMBRE=?, USERNAME=?, PAIS_ID=?, ROL_ID=? WHERE ID = ?";
/* 163 */       this.ps = this.con.conexion.prepareStatement(query);
/* 164 */       this.ps.setString(1, getNombre());
/* 165 */       this.ps.setString(2, getUsername());
/* 166 */       this.ps.setInt(3, getPais());
/* 167 */       this.ps.setInt(4, getRol());
/* 168 */       this.ps.setInt(5, getId());
/* 169 */       int executar = this.ps.executeUpdate();
/* 170 */       retorno = executar;
/* 171 */       this.con.conexion.close();
/* 172 */       jsonObj.put("TransactSQL", true);
/* 173 */       this.hp.setResponse(200, jsonObj);
/* 174 */     } catch (SQLException ex) {
/* 175 */       this.con.conexion.close();
/* 176 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 177 */       this.hp.setResponse(400, jsonObj);
/* 178 */       retorno = 0;
     } 
/* 180 */     return retorno;
   }
 
   
   public int delete() throws Exception {
/* 185 */     int retorno = 0;
/* 186 */     JSONObject jsonObj = new JSONObject();
     try {
/* 188 */       this.con.Conectar();
/* 189 */       String query = "UPDATE CDN_USUARIO SET ESTADO =?  WHERE ID = ?";
/* 190 */       this.ps = this.con.conexion.prepareStatement(query);
/* 191 */       this.ps.setInt(1, getEstado());
/* 192 */       this.ps.setInt(2, getId());
/* 193 */       int executar = this.ps.executeUpdate();
/* 194 */       retorno = executar;
/* 195 */       this.con.conexion.close();
/* 196 */       jsonObj.put("TransactSQL", true);
/* 197 */       this.hp.setResponse(200, jsonObj);
/* 198 */     } catch (SQLException ex) {
/* 199 */       this.con.conexion.close();
/* 200 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 201 */       this.hp.setResponse(400, jsonObj);
/* 202 */       retorno = 0;
     } 
/* 204 */     return retorno;
   }
   
   public ResultSet getUser(String nombre, int pais) throws SQLException, Exception {
/* 208 */     JSONObject jsonObj = new JSONObject();
/* 209 */     this.responseArray = new JSONArray();
     try {
/* 211 */       this.con.Conectar();
/* 212 */       String query = "SELECT * FROM CDN_USUARIO WHERE ESTADO = 1 AND USERNAME = '" + nombre + "'";
/* 213 */       if (pais != 0) {
/* 214 */         query = query + " AND PAIS_ID = " + pais;
       }
/* 216 */       this.rs = this.con.Consultar(query);
/* 217 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 218 */       int columna = rsmd.getColumnCount();
       
/* 220 */       while (this.rs.next()) {
/* 221 */         JSONObject rows = new JSONObject();
/* 222 */         for (int i = 1; i <= columna; i++) {
/* 223 */           String columnaBD = rsmd.getColumnName(i);
/* 224 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 226 */         this.responseArray.put(rows);
       } 
/* 228 */       this.hp.setArrayResponse(200, this.responseArray);
/* 229 */       this.con.CerrarConsulta();
/* 230 */     } catch (SQLException ex) {
/* 231 */       this.hp.setArrayResponse(400, this.responseArray);
/* 232 */       this.con.CerrarConsulta();
     } 
/* 234 */     return this.rs;
   }
   
   public ResultSet validateURL(String url, int rol) throws SQLException, Exception {
/* 238 */     JSONObject jsonObj = new JSONObject();
/* 239 */     this.responseArray = new JSONArray();
     try {
/* 241 */       this.con.Conectar();
/* 242 */       String query = "SELECT * FROM CDN_ACCESO A JOIN CDN_PANTALLA B on A.PANTALLA_ID = B.ID WHERE A.ROL_ID =" + rol + " AND B.URL = '" + url + "' AND A.ESTADO =1";
       
/* 244 */       this.rs = this.con.Consultar(query);
/* 245 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 246 */       int columna = rsmd.getColumnCount();
       
/* 248 */       while (this.rs.next()) {
/* 249 */         JSONObject rows = new JSONObject();
/* 250 */         for (int i = 1; i <= columna; i++) {
/* 251 */           String columnaBD = rsmd.getColumnName(i);
/* 252 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 254 */         this.responseArray.put(rows);
       } 
/* 256 */       this.hp.setArrayResponse(200, this.responseArray);
/* 257 */       this.con.CerrarConsulta();
/* 258 */     } catch (SQLException ex) {
/* 259 */       this.hp.setArrayResponse(400, this.responseArray);
/* 260 */       this.con.CerrarConsulta();
     } 
/* 262 */     return this.rs;
   }
   public String getToken(int idUser) throws SQLException, Exception {
/* 265 */     JSONObject jsonObj = new JSONObject();
/* 266 */     this.responseArray = new JSONArray();
/* 267 */     String token = null;
     
     try {
/* 270 */       this.con.Conectar();
/* 271 */       String query = "SELECT ID,USERNAME,TOKEN FROM CDN_USUARIO WHERE ESTADO=1 AND FECHA_HORA_TOKEN > SYSDATE";
/* 272 */       if (idUser != 0) {
/* 273 */         query = query + " AND ID = " + idUser;
       }
/* 275 */       this.rs = this.con.Consultar(query);
/* 276 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 277 */       int columna = rsmd.getColumnCount();
       
/* 279 */       while (this.rs.next()) {
/* 280 */         JSONObject rows = new JSONObject();
/* 281 */         for (int i = 1; i <= columna; i++) {
/* 282 */           String columnaBD = rsmd.getColumnName(i);
/* 283 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 285 */         this.responseArray.put(rows);
/* 286 */         token = this.rs.getString("TOKEN");
       } 
/* 288 */       this.hp.setArrayResponse(200, this.responseArray);
/* 289 */       this.con.CerrarConsulta();
/* 290 */     } catch (SQLException ex) {
/* 291 */       this.hp.setArrayResponse(400, this.responseArray);
/* 292 */       this.con.CerrarConsulta();
     } 
/* 294 */     return token;
   }
   
   public ResultSet setToken(int idUser) throws SQLException, Exception {
/* 298 */     JSONObject jsonObj = new JSONObject();
/* 299 */     this.responseArray = new JSONArray();
     try {
/* 301 */       this.con.Conectar();
/* 302 */       String query = "UPDATE CDN_USUARIO set TOKEN = ORA_HASH(SYSDATE),FECHA_HORA_TOKEN =(SYSDATE+1) WHERE ID = " + idUser + "";
/* 303 */       this.rs = this.con.Consultar(query);
/* 304 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 305 */       int columna = rsmd.getColumnCount();
/* 306 */       JSONObject rows = new JSONObject();
/* 307 */       this.responseArray.put(rows);
/* 308 */       this.hp.setArrayResponse(200, this.responseArray);
/* 309 */       this.con.CerrarConsulta();
/* 310 */     } catch (SQLException ex) {
/* 311 */       this.hp.setArrayResponse(400, this.responseArray);
/* 312 */       this.con.CerrarConsulta();
     } 
/* 314 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Usuario.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */