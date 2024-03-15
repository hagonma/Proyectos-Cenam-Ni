 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 public class Catalogo
 {
   int ID_CATALOGO;
   int ID_DETALLE;
   int ESTADO;
   int ID_PAIS;
   String CODIGO;
   String DESCRIPCION;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  28 */   public Helper hp = new Helper();
/*  29 */   public ConexionOracle con = new ConexionOracle();
   
   public int getID_CATALOGO() {
/*  32 */     return this.ID_CATALOGO;
   }
   
   public void setID_CATALOGO(int ID_CATALOGO) {
/*  36 */     this.ID_CATALOGO = ID_CATALOGO;
   }
   
   public int getID_DETALLE() {
/*  40 */     return this.ID_DETALLE;
   }
   
   public void setID_DETALLE(int ID_DETALLE) {
/*  44 */     this.ID_DETALLE = ID_DETALLE;
   }
   
   public int getESTADO() {
/*  48 */     return this.ESTADO;
   }
   
   public void setESTADO(int ESTADO) {
/*  52 */     this.ESTADO = ESTADO;
   }
   
   public int getID_PAIS() {
/*  56 */     return this.ID_PAIS;
   }
   
   public void setID_PAIS(int ID_PAIS) {
/*  60 */     this.ID_PAIS = ID_PAIS;
   }
   
   public String getCODIGO() {
/*  64 */     return this.CODIGO;
   }
   
   public void setCODIGO(String CODIGO) {
/*  68 */     this.CODIGO = CODIGO;
   }
   
   public String getDESCRIPCION() {
/*  72 */     return this.DESCRIPCION;
   }
   
   public void setDESCRIPCION(String DESCRIPCION) {
/*  76 */     this.DESCRIPCION = DESCRIPCION;
   }
   
   public ResultSet select(int idCatalogo, int pais, int idDetalle, String codigo) throws SQLException, Exception {
/*  80 */     JSONObject jsonObj = new JSONObject();
/*  81 */     this.responseArray = new JSONArray();
     try {
/*  83 */       this.con.Conectar();
/*  84 */       String query = "SELECT A.ID_DETALLE,A.ID_PAIS, A.ID_CATALOGO,A.CODIGO,B.CODIGO AS DETALLE,A.DESCRIPCION,C.NOMBRE FROM CDN_CATALOGO_MAIL A INNER JOIN CDN_DETALLE_MAIL B ON B.ID_DETALLE = A.ID_DETALLE INNER JOIN CDN_PAIS C ON C.ID = A.ID_PAIS WHERE A.ESTADO = 1";
/*  85 */       if (idCatalogo != 0) {
/*  86 */         query = query + " AND A.ID_CATALOGO = " + idCatalogo;
       }
/*  88 */       if (pais != 0) {
/*  89 */         query = query + " AND A.ID_PAIS = " + pais;
       }
/*  91 */       if (idDetalle != 0) {
/*  92 */         query = query + " AND A.ID_DETALLE = " + idDetalle;
       }
/*  94 */       if (codigo != null) {
/*  95 */         query = query + " AND A.CODIGO = " + codigo;
       }
/*  97 */       this.rs = this.con.Consultar(query);
/*  98 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  99 */       int columna = rsmd.getColumnCount();
       
/* 101 */       while (this.rs.next()) {
/* 102 */         JSONObject rows = new JSONObject();
/* 103 */         for (int i = 1; i <= columna; i++) {
/* 104 */           String columnaBD = rsmd.getColumnName(i);
/* 105 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 107 */         this.responseArray.put(rows);
       } 
/* 109 */       this.hp.setArrayResponse(200, this.responseArray);
/* 110 */       this.con.CerrarConsulta();
/* 111 */     } catch (SQLException ex) {
/* 112 */       this.hp.setArrayResponse(400, this.responseArray);
/* 113 */       this.con.CerrarConsulta();
     } 
/* 115 */     return this.rs;
   }
 
   
   public int insert() throws Exception, SQLException {
/* 120 */     int retorno = 0;
/* 121 */     JSONObject jsonObj = new JSONObject();
     try {
/* 123 */       this.con.Conectar();
/* 124 */       String query = "INSERT INTO CDN_CATALOGO_MAIL(CODIGO,ID_DETALLE,DESCRIPCION,ESTADO,ID_PAIS) VALUES (?,?,?,?,?)";
/* 125 */       this.ps = this.con.conexion.prepareStatement(query);
/* 126 */       this.ps.setString(1, getCODIGO());
/* 127 */       this.ps.setInt(2, getID_DETALLE());
/* 128 */       this.ps.setString(3, getDESCRIPCION());
/* 129 */       this.ps.setInt(4, getESTADO());
/* 130 */       this.ps.setInt(5, getID_PAIS());
       
/* 132 */       int executar = this.ps.executeUpdate();
/* 133 */       retorno = executar;
/* 134 */       this.con.conexion.close();
/* 135 */       jsonObj.put("TransactSQL", true);
/* 136 */       this.hp.setResponse(200, jsonObj);
/* 137 */     } catch (SQLException ex) {
/* 138 */       this.con.conexion.close();
/* 139 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 140 */       this.hp.setResponse(400, jsonObj);
/* 141 */       retorno = 0;
     } 
/* 143 */     return retorno;
   }
 
   
   public int update() throws Exception, SQLException {
/* 148 */     int retorno = 0;
/* 149 */     JSONObject jsonObj = new JSONObject();
     try {
/* 151 */       this.con.Conectar();
/* 152 */       String query = "UPDATE CDN_CATALOGO_MAIL SET CODIGO=?, ID_DETALLE=?, DESCRIPCION=?, ID_PAIS=? WHERE ID_CATALOGO = ?";
/* 153 */       this.ps = this.con.conexion.prepareStatement(query);
       
/* 155 */       this.ps.setString(1, getCODIGO());
/* 156 */       this.ps.setInt(2, getID_DETALLE());
/* 157 */       this.ps.setString(3, getDESCRIPCION());
/* 158 */       this.ps.setInt(4, getID_PAIS());
/* 159 */       this.ps.setInt(5, getID_CATALOGO());
/* 160 */       int executar = this.ps.executeUpdate();
/* 161 */       retorno = executar;
/* 162 */       this.con.conexion.close();
/* 163 */       jsonObj.put("TransactSQL", true);
/* 164 */       this.hp.setResponse(200, jsonObj);
/* 165 */     } catch (SQLException ex) {
/* 166 */       this.con.conexion.close();
/* 167 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 168 */       this.hp.setResponse(400, jsonObj);
/* 169 */       retorno = 0;
     } 
/* 171 */     return retorno;
   }
 
   
   public int delete() throws Exception {
/* 176 */     int retorno = 0;
/* 177 */     JSONObject jsonObj = new JSONObject();
     try {
/* 179 */       this.con.Conectar();
/* 180 */       String query = "UPDATE CDN_CATALOGO_MAIL SET ESTADO =?  WHERE ID_CATALOGO = ?";
/* 181 */       this.ps = this.con.conexion.prepareStatement(query);
/* 182 */       this.ps.setInt(1, getESTADO());
/* 183 */       this.ps.setInt(2, getID_CATALOGO());
/* 184 */       int executar = this.ps.executeUpdate();
/* 185 */       retorno = executar;
/* 186 */       this.con.conexion.close();
/* 187 */       jsonObj.put("TransactSQL", true);
/* 188 */       this.hp.setResponse(200, jsonObj);
/* 189 */     } catch (SQLException ex) {
/* 190 */       this.con.conexion.close();
/* 191 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 192 */       this.hp.setResponse(400, jsonObj);
/* 193 */       retorno = 0;
     } 
/* 195 */     return retorno;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Catalogo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */