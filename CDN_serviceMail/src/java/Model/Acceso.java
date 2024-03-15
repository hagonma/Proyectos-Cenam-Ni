 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 public class Acceso
 {
   int pantalla;
   int rol;
   int estado;
   int id;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  27 */   public Helper hp = new Helper();
/*  28 */   public ConexionOracle con = new ConexionOracle();
   
   public int getPantalla() {
/*  31 */     return this.pantalla;
   }
   
   public void setPantalla(int pantalla) {
/*  35 */     this.pantalla = pantalla;
   }
   
   public int getRol() {
/*  39 */     return this.rol;
   }
   
   public void setRol(int rol) {
/*  43 */     this.rol = rol;
   }
   
   public int getEstado() {
/*  47 */     return this.estado;
   }
   
   public void setEstado(int estado) {
/*  51 */     this.estado = estado;
   }
   
   public int getId() {
/*  55 */     return this.id;
   }
   
   public void setId(int id) {
/*  59 */     this.id = id;
   }
   
   public ResultSet select(String id, int rol, int pantalla) throws SQLException, Exception {
/*  63 */     JSONObject jsonObj = new JSONObject();
/*  64 */     this.responseArray = new JSONArray();
     try {
/*  66 */       this.con.Conectar();
/*  67 */       String query = "SELECT A.ID,A.PANTALLA_ID,A.ROL_ID,A.ESTADO,C.NOMBRE AS PANTALLA,C.URL,B.NOMBRE AS ROL FROM CDN_ACCESO A INNER JOIN CDN_ROL B ON A.ROL_ID = B.ID INNER JOIN CDN_PANTALLA C ON A.PANTALLA_ID = C.ID WHERE A.ESTADO=1";
/*  68 */       if (id != null) {
/*  69 */         query = query + " AND A.ID = " + id;
       }
/*  71 */       if (rol != 0) {
/*  72 */         query = query + " AND A.ROL_ID = " + rol;
       }
/*  74 */       if (pantalla != 0) {
/*  75 */         query = query + " AND A.PANTALLA_ID = " + pantalla;
       }
/*  77 */       this.rs = this.con.Consultar(query);
/*  78 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  79 */       int columna = rsmd.getColumnCount();
       
/*  81 */       while (this.rs.next()) {
/*  82 */         JSONObject rows = new JSONObject();
/*  83 */         for (int i = 1; i <= columna; i++) {
/*  84 */           String columnaBD = rsmd.getColumnName(i);
/*  85 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/*  87 */         this.responseArray.put(rows);
       } 
/*  89 */       this.hp.setArrayResponse(200, this.responseArray);
/*  90 */       this.con.CerrarConsulta();
/*  91 */     } catch (SQLException ex) {
/*  92 */       this.hp.setArrayResponse(400, this.responseArray);
/*  93 */       this.con.CerrarConsulta();
     } 
/*  95 */     return this.rs;
   }
 
   
   public int insert() throws Exception, SQLException {
/* 100 */     int retorno = 0;
/* 101 */     JSONObject jsonObj = new JSONObject();
     try {
/* 103 */       this.con.Conectar();
/* 104 */       String query = "INSERT INTO CDN_ACCESO(PANTALLA_ID,ROL_ID,ESTADO) VALUES (?,?,?)";
/* 105 */       this.ps = this.con.conexion.prepareStatement(query);
/* 106 */       this.ps.setInt(1, getPantalla());
/* 107 */       this.ps.setInt(2, getRol());
/* 108 */       this.ps.setInt(3, getEstado());
       
/* 110 */       int executar = this.ps.executeUpdate();
/* 111 */       retorno = executar;
/* 112 */       this.con.conexion.close();
/* 113 */       jsonObj.put("TransactSQL", true);
/* 114 */       this.hp.setResponse(200, jsonObj);
/* 115 */     } catch (SQLException ex) {
/* 116 */       this.con.conexion.close();
/* 117 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 118 */       this.hp.setResponse(400, jsonObj);
/* 119 */       retorno = 0;
     } 
/* 121 */     return retorno;
   }
 
   
   public int update() throws Exception, SQLException {
/* 126 */     int retorno = 0;
/* 127 */     JSONObject jsonObj = new JSONObject();
     try {
/* 129 */       this.con.Conectar();
/* 130 */       String query = "UPDATE CDN_ACCESO SET PANTALLA_ID=?, ROL_ID=? WHERE ID = ?";
/* 131 */       this.ps = this.con.conexion.prepareStatement(query);
/* 132 */       this.ps.setInt(1, getPantalla());
/* 133 */       this.ps.setInt(2, getRol());
/* 134 */       this.ps.setInt(3, getId());
/* 135 */       int executar = this.ps.executeUpdate();
/* 136 */       retorno = executar;
/* 137 */       this.con.conexion.close();
/* 138 */       jsonObj.put("TransactSQL", true);
/* 139 */       this.hp.setResponse(200, jsonObj);
/* 140 */     } catch (SQLException ex) {
/* 141 */       this.con.conexion.close();
/* 142 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 143 */       this.hp.setResponse(400, jsonObj);
/* 144 */       retorno = 0;
     } 
/* 146 */     return retorno;
   }
 
   
   public int delete() throws Exception {
/* 151 */     int retorno = 0;
/* 152 */     JSONObject jsonObj = new JSONObject();
     try {
/* 154 */       this.con.Conectar();
/* 155 */       String query = "UPDATE CDN_ACCESO SET ESTADO =?  WHERE ID = ?";
/* 156 */       this.ps = this.con.conexion.prepareStatement(query);
/* 157 */       this.ps.setInt(1, getEstado());
/* 158 */       this.ps.setInt(2, getId());
/* 159 */       int executar = this.ps.executeUpdate();
/* 160 */       retorno = executar;
/* 161 */       this.con.conexion.close();
/* 162 */       jsonObj.put("TransactSQL", true);
/* 163 */       this.hp.setResponse(200, jsonObj);
/* 164 */     } catch (SQLException ex) {
/* 165 */       this.con.conexion.close();
/* 166 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 167 */       this.hp.setResponse(400, jsonObj);
/* 168 */       retorno = 0;
     } 
/* 170 */     return retorno;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Acceso.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */