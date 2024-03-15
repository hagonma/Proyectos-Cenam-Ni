 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 public class ReporteSMS
 {
   int id;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  27 */   public Helper hp = new Helper();
/*  28 */   public ConexionOracle con = new ConexionOracle(); String origen; String msg; String status;
   String date_sended;
   String sistema;
   String date_registered;
   
   public String getAskedService() {
/*  34 */     if (this.askedService != null) {
/*  35 */       if (this.askedService.isEmpty()) {
/*  36 */         return "";
       }
/*  38 */       return this.askedService;
     } 
     
/*  41 */     return "";
   }
   String askedService; int prioridad; int queue; int pais_id; int a_demanda; Long destino;
   
   public void setAskedService(String askedService) {
/*  46 */     this.askedService = askedService;
   }
 
 
   
   public int getA_demanda() {
/*  52 */     return this.a_demanda;
   }
   
   public void setA_demanda(int a_demanda) {
/*  56 */     this.a_demanda = a_demanda;
   }
   
   public String getOrigen() {
/*  60 */     return this.origen;
   }
   
   public void setOrigen(String origen) {
/*  64 */     this.origen = origen;
   }
   
   public String getMsg() {
/*  68 */     return this.msg;
   }
   
   public void setMsg(String msg) {
/*  72 */     this.msg = msg;
   }
   
   public String getStatus() {
/*  76 */     return this.status;
   }
   
   public void setStatus(String status) {
/*  80 */     this.status = status;
   }
   
   public String getDate_sended() {
/*  84 */     return this.date_sended;
   }
   
   public void setDate_sended(String date_sended) {
/*  88 */     this.date_sended = date_sended;
   }
   
   public String getSistema() {
/*  92 */     return this.sistema;
   }
   
   public void setSistema(String sistema) {
/*  96 */     this.sistema = sistema;
   }
   
   public String getDate_registered() {
/* 100 */     return this.date_registered;
   }
   
   public void setDate_registered(String date_registered) {
/* 104 */     this.date_registered = date_registered;
   }
   
   public Long getDestino() {
/* 108 */     return this.destino;
   }
   
   public void setDestino(Long destino) {
/* 112 */     this.destino = destino;
   }
   
   public int getPrioridad() {
/* 116 */     return this.prioridad;
   }
   
   public void setPrioridad(int prioridad) {
/* 120 */     this.prioridad = prioridad;
   }
   
   public int getQueue() {
/* 124 */     return this.queue;
   }
   
   public void setQueue(int queue) {
/* 128 */     this.queue = queue;
   }
   
   public int getPais_id() {
/* 132 */     return this.pais_id;
   }
   
   public void setPais_id(int pais_id) {
/* 136 */     this.pais_id = pais_id;
   }
   
   public int getId() {
/* 140 */     return this.id;
   }
   
   public void setId(int id) {
/* 144 */     this.id = id;
   }
   
   public ResultSet select(String id, int pais, String fechaInicio, String fechaFin, String keyword) throws SQLException, Exception {
/* 148 */     JSONObject jsonObj = new JSONObject();
/* 149 */     this.responseArray = new JSONArray();
     try {
/* 151 */       this.con.Conectar();
/* 152 */       String query = "SELECT B.CODIGO AS PAIS,A.DESTINO AS NUMERO_ORIGEN,B.NUM_CC AS MARCACION, ASKEDSERVICE ,TO_CHAR(TO_DATE(A.DATE_SENDED, 'DD/MM/YYYY'))AS FECHA_ENVIO, A.MSG AS TEXTO_MSG FROM TB_SMS A INNER JOIN CDN_PAIS B ON B.ID = A.PAIS_ID WHERE A.DESTINO <> 0";
       
/* 154 */       if (id == null && pais == 0 && fechaInicio == null && fechaFin == null) {
         
/* 156 */         this.hp.setArrayResponse(200, this.responseArray);
       }
       else {
         
/* 160 */         if (id != null) {
/* 161 */           query = query + " AND A.DESTINO = " + id;
         }
/* 163 */         if (fechaInicio != null && fechaFin != null && pais != 0 && keyword != null) {
/* 164 */           query = query + " AND B.ID = " + pais;
/* 165 */           query = query + " AND A.DATE_SENDED BETWEEN TO_DATE('" + fechaInicio + "', 'YYYY/MM/DD') AND TO_DATE('" + fechaFin + "', 'YYYY/MM/DD')+0.999 ";
/* 166 */           query = query + " AND UPPER(MSG)  like '%" + keyword.toUpperCase() + "%'";
         } else {
/* 168 */           query = query + " AND A.DATE_SENDED = SYSDATE ";
         } 
         
/* 171 */         this.rs = this.con.Consultar(query);
/* 172 */         ResultSetMetaData rsmd = this.rs.getMetaData();
/* 173 */         int columna = rsmd.getColumnCount();
         
/* 175 */         while (this.rs.next()) {
/* 176 */           JSONObject rows = new JSONObject();
/* 177 */           for (int i = 1; i <= columna; i++) {
/* 178 */             String columnaBD = rsmd.getColumnName(i);
/* 179 */             rows.put(columnaBD, this.rs.getString(columnaBD));
           } 
/* 181 */           this.responseArray.put(rows);
         } 
/* 183 */         this.hp.setArrayResponse(200, this.responseArray);
       } 
       
/* 186 */       this.con.CerrarConsulta();
/* 187 */     } catch (SQLException ex) {
/* 188 */       this.hp.setArrayResponse(400, this.responseArray);
/* 189 */       this.con.CerrarConsulta();
     } 
/* 191 */     return this.rs;
   }
 
   
   public int insert() throws Exception, SQLException {
/* 196 */     int retorno = 0;
/* 197 */     JSONObject jsonObj = new JSONObject();
     try {
/* 199 */       this.con.Conectar();
/* 200 */       String query = "INSERT INTO TB_SMS(ORIGEN,DESTINO,MSG,STATUS,SISTEMA,PRIORIDAD,QUEUE,PAIS_ID,A_DEMANDA, ASKEDSERVICE) VALUES (?,?,?,?,?,?,?,?,?,?)";
/* 201 */       this.ps = this.con.conexion.prepareStatement(query);
/* 202 */       this.ps.setString(1, getOrigen());
/* 203 */       this.ps.setLong(2, getDestino().longValue());
/* 204 */       this.ps.setString(3, getMsg());
/* 205 */       this.ps.setString(4, getStatus());
       
/* 207 */       this.ps.setString(5, getSistema());
/* 208 */       this.ps.setInt(6, getPrioridad());
       
/* 210 */       this.ps.setInt(7, getQueue());
/* 211 */       this.ps.setInt(8, getPais_id());
/* 212 */       this.ps.setInt(9, getA_demanda());
/* 213 */       this.ps.setString(10, getAskedService());
/* 214 */       int executar = this.ps.executeUpdate();
/* 215 */       retorno = executar;
/* 216 */       this.con.conexion.close();
/* 217 */       jsonObj.put("TransactSQL", true);
/* 218 */       this.hp.setResponse(200, jsonObj);
/* 219 */     } catch (SQLException ex) {
/* 220 */       this.con.conexion.close();
/* 221 */       jsonObj.put("ErrorSQL", ex.getMessage());
/* 222 */       this.hp.setResponse(400, jsonObj);
/* 223 */       retorno = 0;
     } 
/* 225 */     return retorno;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ReporteSMS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */