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
 
 
 
 
 
 
 
 public class Mensaje
 {
   int id;
   int tipo_mensaje;
   int pais;
   int estado;
   String descripcion;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  30 */   public Helper hp = new Helper();
/*  31 */   public ConexionOracle con = new ConexionOracle();
   
   public int getId() {
/*  34 */     return this.id;
   }
   
   public void setId(int id) {
/*  38 */     this.id = id;
   }
   
   public int getTipo_mensaje() {
/*  42 */     return this.tipo_mensaje;
   }
   
   public void setTipo_mensaje(int tipo_mensaje) {
/*  46 */     this.tipo_mensaje = tipo_mensaje;
   }
   
   public int getPais() {
/*  50 */     return this.pais;
   }
   
   public void setPais(int pais) {
/*  54 */     this.pais = pais;
   }
   
   public int getEstado() {
/*  58 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/*  62 */     this.estado = estado;
   }
   
   public String getDescripcion() {
/*  66 */     return this.descripcion;
   }
   
   public void setDescripcion(String descripcion) {
/*  70 */     this.descripcion = Jsoup.clean(descripcion, Whitelist.basic());
/*  71 */     this.descripcion = this.descripcion.replaceAll("<[^>]*>", "");
   }
 
   
   public ResultSet select(String id, int pais, int tipoMensaje) throws SQLException, Exception {
/*  76 */     JSONObject jsonObj = new JSONObject();
/*  77 */     this.responseArray = new JSONArray();
     try {
/*  79 */       this.con.Conectar();
/*  80 */       String query = "SELECT A.ID,A.DESCRIPCION,A.ID_TIPO_MENSAJE,A.ID_PAIS,B.NOMBRE AS PAIS,C.DESCRIPCION AS TIPO_MENSAJE FROM CDN_MENSAJE A INNER JOIN CDN_PAIS B ON A.ID_PAIS = B.ID INNER JOIN CDN_TIPO_MENSAJE C ON A.ID_TIPO_MENSAJE = C.ID WHERE A.ESTADO=1";
/*  81 */       if (id != null) {
/*  82 */         query = query + " AND A.ID = " + id;
       }
/*  84 */       if (pais != 0) {
/*  85 */         query = query + " AND A.ID_PAIS = " + pais;
       }
/*  87 */       if (tipoMensaje != 0) {
/*  88 */         query = query + " AND A.ID_TIPO_MENSAJE = " + tipoMensaje;
       }
       
/*  91 */       this.rs = this.con.Consultar(query);
/*  92 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  93 */       int columna = rsmd.getColumnCount();
       
/*  95 */       while (this.rs.next()) {
/*  96 */         JSONObject rows = new JSONObject();
/*  97 */         for (int i = 1; i <= columna; i++) {
/*  98 */           String columnaBD = rsmd.getColumnName(i);
/*  99 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 101 */         this.responseArray.put(rows);
       } 
/* 103 */       this.hp.setArrayResponse(200, this.responseArray);
/* 104 */       this.con.CerrarConsulta();
/* 105 */     } catch (SQLException ex) {
/* 106 */       this.hp.setArrayResponse(400, this.responseArray);
/* 107 */       this.con.CerrarConsulta();
     } 
/* 109 */     return this.rs;
   }
 
 
   
   public int insert() throws Exception, SQLException {
/* 115 */     int retorno = 0;
/* 116 */     JSONObject jsonObj = new JSONObject();
     try {
/* 118 */       this.con.Conectar();
/* 119 */       String query = "INSERT INTO CDN_MENSAJE(DESCRIPCION,ID_TIPO_MENSAJE,ID_PAIS,ESTADO) VALUES (?,?,?,?)";
/* 120 */       this.ps = this.con.conexion.prepareStatement(query);
/* 121 */       this.ps.setString(1, getDescripcion());
/* 122 */       this.ps.setInt(2, getTipo_mensaje());
/* 123 */       this.ps.setInt(3, getPais());
/* 124 */       this.ps.setInt(4, getEstado());
       
/* 126 */       int executar = this.ps.executeUpdate();
/* 127 */       retorno = executar;
/* 128 */       this.con.conexion.close();
/* 129 */       jsonObj.put("TransactSQL", true);
/* 130 */       this.hp.setResponse(200, jsonObj);
/* 131 */     } catch (SQLException ex) {
/* 132 */       this.con.conexion.close();
/* 133 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 134 */       this.hp.setResponse(400, jsonObj);
/* 135 */       retorno = 0;
     } 
/* 137 */     return retorno;
   }
 
   
   public int update() throws Exception, SQLException {
/* 142 */     int retorno = 0;
/* 143 */     JSONObject jsonObj = new JSONObject();
     try {
/* 145 */       this.con.Conectar();
/* 146 */       String query = "UPDATE CDN_MENSAJE SET DESCRIPCION=?, ID_TIPO_MENSAJE=?, ID_PAIS=? WHERE ID = ?";
/* 147 */       this.ps = this.con.conexion.prepareStatement(query);
/* 148 */       this.ps.setString(1, getDescripcion());
/* 149 */       this.ps.setInt(2, getTipo_mensaje());
/* 150 */       this.ps.setInt(3, getPais());
/* 151 */       this.ps.setInt(4, getId());
/* 152 */       int executar = this.ps.executeUpdate();
/* 153 */       retorno = executar;
/* 154 */       this.con.conexion.close();
/* 155 */       jsonObj.put("TransactSQL", true);
/* 156 */       this.hp.setResponse(200, jsonObj);
/* 157 */     } catch (SQLException ex) {
/* 158 */       this.con.conexion.close();
/* 159 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 160 */       this.hp.setResponse(400, jsonObj);
/* 161 */       retorno = 0;
     } 
/* 163 */     return retorno;
   }
 
   
   public int delete() throws Exception {
/* 168 */     int retorno = 0;
/* 169 */     JSONObject jsonObj = new JSONObject();
     try {
/* 171 */       this.con.Conectar();
/* 172 */       String query = "UPDATE CDN_MENSAJE SET ESTADO =?  WHERE ID = ?";
/* 173 */       this.ps = this.con.conexion.prepareStatement(query);
/* 174 */       this.ps.setInt(1, getEstado());
/* 175 */       this.ps.setInt(2, getId());
/* 176 */       int executar = this.ps.executeUpdate();
/* 177 */       retorno = executar;
/* 178 */       this.con.conexion.close();
/* 179 */       jsonObj.put("TransactSQL", true);
/* 180 */       this.hp.setResponse(200, jsonObj);
/* 181 */     } catch (SQLException ex) {
/* 182 */       this.con.conexion.close();
/* 183 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 184 */       this.hp.setResponse(400, jsonObj);
/* 185 */       retorno = 0;
     } 
/* 187 */     return retorno;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Mensaje.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */