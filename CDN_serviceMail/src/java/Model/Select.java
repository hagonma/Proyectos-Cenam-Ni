 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import Controller.cuerpoCorreo;
 import Model.Parametro;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 public class Select
 {
   int id;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  28 */   public Helper hp = new Helper();
/*  29 */   public ConexionOracle con = new ConexionOracle();
/*  30 */   public Parametro par = new Parametro();
   
   public int getId() {
/*  33 */     return this.id;
   }
   
   public void setId(int id) {
/*  37 */     this.id = id;
   }
   
   public String select(String id) throws SQLException, Exception {
/*  41 */     JSONObject jsonObj = new JSONObject();
/*  42 */     cuerpoCorreo body = new cuerpoCorreo();
/*  43 */     this.responseArray = new JSONArray();
/*  44 */     String response = "";
     try {
/*  46 */       this.con.Conectar();
/*  47 */       String query = "SELECT B.ID AS IDPAIS, B.MARCACION, B.NUM_CC,B.FACTURA,B.LINK, TO_CHAR(TO_DATE(A.FECHA_CONTRATACION, 'DD/MM/YY')) AS FECHA_CONTRATACION1, A.* FROM CDN_MAIL A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.CODIGO";
/*  48 */       if (id != null) {
/*  49 */         query = query + " WHERE A.ID = " + id;
       }
       
/*  52 */       this.rs = this.con.Consultar(query);
/*  53 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  54 */       int columna = rsmd.getColumnCount();
       
/*  56 */       while (this.rs.next()) {
/*  57 */         JSONObject rows = new JSONObject();
/*  58 */         for (int i = 1; i <= columna; i++) {
/*  59 */           String columnaBD = rsmd.getColumnName(i);
/*  60 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/*  62 */         this.responseArray.put(rows);
/*  63 */         response = body.cuerpo(rows);
       } 
/*  65 */       this.hp.setArrayResponse(200, this.responseArray);
/*  66 */       this.con.CerrarConsulta();
/*  67 */     } catch (SQLException ex) {
/*  68 */       response = ex.toString();
/*  69 */       this.hp.setArrayResponse(400, this.responseArray);
/*  70 */       this.con.CerrarConsulta();
     } 
/*  72 */     return response;
   }
   
   public String selectFijo(String id, String pais) throws SQLException, Exception {
/*  76 */     JSONObject jsonObj = new JSONObject();
/*  77 */     cuerpoCorreo body = new cuerpoCorreo();
/*  78 */     this.responseArray = new JSONArray();
/*  79 */     String response = "";
/*  80 */     if (!id.equals("CARGA_DIARIA")) {
       try {
/*  82 */         this.con.Conectar();
/*  83 */         String query = "SELECT  B.CODIGO AS IDPAIS, B.ID AS IDPAIS_SMPP, NVL(TRIM(B.MARCACION),0) MARCACION, NVL(TRIM(B.NUM_CC),0) NUM_CC, NVL(TRIM(B.FACTURA),0) FACTURA, NVL(TRIM(B.LINK),0) LINK, TO_CHAR(A.FECHA_CONTRATACION, 'DD/MM/YYYY') AS FECHA_CONTRATACION2,  A.* FROM CDN_MAIL A INNER JOIN CDN_PAIS B ON A.PAIS_ID = B.CODIGO";
/*  84 */         if (id != null) {
/*  85 */           query = query + " WHERE B.CODIGO = " + pais + " AND A.ID = " + id;
         }
         
/*  88 */         this.rs = this.con.Consultar(query);
/*  89 */         ResultSetMetaData rsmd = this.rs.getMetaData();
/*  90 */         int columna = rsmd.getColumnCount();
         
/*  92 */         while (this.rs.next()) {
/*  93 */           JSONObject rows = new JSONObject();
/*  94 */           for (int i = 1; i <= columna; i++) {
/*  95 */             String columnaBD = rsmd.getColumnName(i);
/*  96 */             rows.put(columnaBD, this.rs.getString(columnaBD));
           } 
/*  98 */           this.responseArray.put(rows);
/*  99 */           response = body.cuerpoFijoRenovado(rows);
         } 
/* 101 */         this.hp.setArrayResponse(200, this.responseArray);
/* 102 */         this.con.CerrarConsulta();
/* 103 */       } catch (SQLException ex) {
/* 104 */         response = ex.toString();
/* 105 */         this.hp.setArrayResponse(400, this.responseArray);
/* 106 */         this.con.CerrarConsulta();
       } 
     } else {
/* 109 */       response = "Primera Carga " + pais + ", no se accede a mandar correo.";
     } 
/* 111 */     return response;
   }
   
   public String getTMCODE(String id) throws SQLException, Exception {
/* 115 */     String dato = "";
/* 116 */     String equipos = "";
     try {
/* 118 */       this.con.Conectar();
/* 119 */       String query = "SELECT TMCODE FROM CDN_MAIL WHERE ID = " + id;
/* 120 */       ResultSet resultado = this.con.Consultar(query);
       
/* 122 */       while (resultado.next()) {
/* 123 */         dato = resultado.getString("TMCODE");
       }
/* 125 */       if (dato.isEmpty()) {
/* 126 */         equipos = "0";
       } else {
/* 128 */         equipos = dato;
       } 
/* 130 */       this.con.CerrarConsulta();
/* 131 */       return equipos;
/* 132 */     } catch (SQLException e) {
/* 133 */       this.con.CerrarConsulta();
/* 134 */       return null;
     } 
   }
   
   public String getLlamadas(String codigo, String pais) throws SQLException, Exception {
/* 139 */     String dato = "";
/* 140 */     String equipos = "";
/* 141 */     String idLlamada = this.par.getParametro("LLAMADAS_MAIL", pais);
     try {
/* 143 */       this.con.Conectar();
/* 144 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 145 */       ResultSet resultado = this.con.Consultar(query);
       
/* 147 */       while (resultado.next()) {
/* 148 */         dato = resultado.getString("DESCRIPCION");
       }
/* 150 */       if (dato.isEmpty()) {
/* 151 */         equipos = "0";
       } else {
/* 153 */         equipos = dato;
       } 
/* 155 */       this.con.CerrarConsulta();
/* 156 */       return equipos;
/* 157 */     } catch (SQLException e) {
/* 158 */       this.con.CerrarConsulta();
/* 159 */       return null;
     } 
   }
   
   public String getMensajes(String codigo, String pais) throws SQLException, Exception {
/* 164 */     String dato = "";
/* 165 */     String equipos = "";
/* 166 */     String idLlamada = this.par.getParametro("MENSAJES_MAIL", pais);
     try {
/* 168 */       this.con.Conectar();
/* 169 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 170 */       ResultSet resultado = this.con.Consultar(query);
       
/* 172 */       while (resultado.next()) {
/* 173 */         dato = resultado.getString("DESCRIPCION");
       }
/* 175 */       if (dato.isEmpty()) {
/* 176 */         equipos = "0";
       } else {
/* 178 */         equipos = dato;
       } 
/* 180 */       this.con.CerrarConsulta();
/* 181 */       return equipos;
/* 182 */     } catch (SQLException e) {
/* 183 */       this.con.CerrarConsulta();
/* 184 */       return null;
     } 
   }
   
   public String getRedes(String codigo, String pais) throws SQLException, Exception {
/* 189 */     String dato = "";
/* 190 */     String equipos = "";
/* 191 */     String idLlamada = this.par.getParametro("REDES_MAIL", pais);
     try {
/* 193 */       this.con.Conectar();
/* 194 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 195 */       ResultSet resultado = this.con.Consultar(query);
       
/* 197 */       while (resultado.next()) {
/* 198 */         dato = resultado.getString("DESCRIPCION");
       }
/* 200 */       if (dato.isEmpty()) {
/* 201 */         equipos = "0";
       } else {
/* 203 */         equipos = dato;
       } 
/* 205 */       this.con.CerrarConsulta();
/* 206 */       return equipos;
/* 207 */     } catch (SQLException e) {
/* 208 */       this.con.CerrarConsulta();
/* 209 */       return null;
     } 
   }
   
   public String getInternet(String codigo, String pais) throws SQLException, Exception {
/* 214 */     String dato = "";
/* 215 */     String equipos = "";
/* 216 */     String idLlamada = this.par.getParametro("INTERNET_MAIL", pais);
     try {
/* 218 */       this.con.Conectar();
/* 219 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 220 */       ResultSet resultado = this.con.Consultar(query);
       
/* 222 */       while (resultado.next()) {
/* 223 */         dato = resultado.getString("DESCRIPCION");
       }
/* 225 */       if (dato.isEmpty()) {
/* 226 */         equipos = "0";
       } else {
/* 228 */         equipos = dato;
       } 
/* 230 */       this.con.CerrarConsulta();
/* 231 */       return equipos;
/* 232 */     } catch (SQLException e) {
/* 233 */       this.con.CerrarConsulta();
/* 234 */       return null;
     } 
   }
   
   public String getSinFronteras(String codigo, String pais) throws SQLException, Exception {
/* 239 */     String dato = "";
/* 240 */     String equipos = "";
/* 241 */     String idLlamada = this.par.getParametro("SINFRONTERAS_MAIL", pais);
     try {
/* 243 */       this.con.Conectar();
/* 244 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 245 */       ResultSet resultado = this.con.Consultar(query);
       
/* 247 */       while (resultado.next()) {
/* 248 */         dato = resultado.getString("DESCRIPCION");
       }
/* 250 */       if (dato.isEmpty()) {
/* 251 */         equipos = "0";
       } else {
/* 253 */         equipos = dato;
       } 
/* 255 */       this.con.CerrarConsulta();
/* 256 */       return equipos;
/* 257 */     } catch (SQLException e) {
/* 258 */       this.con.CerrarConsulta();
/* 259 */       return null;
     } 
   }
   
   public String getEquipo(String codigo, String pais) throws SQLException, Exception {
/* 264 */     String dato = "";
/* 265 */     String equipos = "";
/* 266 */     String idLlamada = this.par.getParametro("EQUIPO_MAIL", pais);
     try {
/* 268 */       this.con.Conectar();
/* 269 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 270 */       ResultSet resultado = this.con.Consultar(query);
       
/* 272 */       while (resultado.next()) {
/* 273 */         dato = resultado.getString("DESCRIPCION");
       }
/* 275 */       if (dato.isEmpty()) {
/* 276 */         equipos = "0";
       } else {
/* 278 */         equipos = dato;
       } 
/* 280 */       this.con.CerrarConsulta();
/* 281 */       return equipos;
/* 282 */     } catch (SQLException e) {
/* 283 */       this.con.CerrarConsulta();
/* 284 */       return null;
     } 
   }
   
   public String getNombrePlan(String codigo, String pais) throws SQLException, Exception {
/* 289 */     String dato = "";
/* 290 */     String equipos = "";
/* 291 */     String idLlamada = this.par.getParametro("NOMBRE_PLAN", pais);
     try {
/* 293 */       this.con.Conectar();
/* 294 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 295 */       ResultSet resultado = this.con.Consultar(query);
       
/* 297 */       while (resultado.next()) {
/* 298 */         dato = resultado.getString("DESCRIPCION");
       }
/* 300 */       if (dato.isEmpty()) {
/* 301 */         equipos = "0";
       } else {
/* 303 */         equipos = dato;
       } 
/* 305 */       this.con.CerrarConsulta();
/* 306 */       return equipos;
/* 307 */     } catch (SQLException e) {
/* 308 */       this.con.CerrarConsulta();
/* 309 */       return null;
     } 
   }
   
   public String getClaroMusica(String codigo, String pais) throws SQLException, Exception {
/* 314 */     String dato = "";
/* 315 */     String equipos = "";
/* 316 */     String idLlamada = this.par.getParametro("CLAROMUSICA_MAIL", pais);
     try {
/* 318 */       this.con.Conectar();
/* 319 */       String query = "SELECT DESCRIPCION FROM CDN_CATALOGO_MAIL WHERE ESTADO = 1 AND ID_DETALLE=" + idLlamada + " AND CODIGO = '" + codigo + "' AND ID_PAIS =" + pais + "";
/* 320 */       ResultSet resultado = this.con.Consultar(query);
       
/* 322 */       while (resultado.next()) {
/* 323 */         dato = resultado.getString("DESCRIPCION");
       }
/* 325 */       if (dato.isEmpty()) {
/* 326 */         equipos = "0";
       } else {
/* 328 */         equipos = dato;
       } 
/* 330 */       this.con.CerrarConsulta();
/* 331 */       return equipos;
/* 332 */     } catch (SQLException e) {
/* 333 */       this.con.CerrarConsulta();
/* 334 */       return null;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Select.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */