 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 public class DashboardModel
 {
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  26 */   public Helper hp = new Helper();
/*  27 */   public ConexionOracle con = new ConexionOracle();
   String fecha;
   String proyecto;
   
   public JSONArray getResponseArray() {
/*  32 */     return this.responseArray;
   }
   String cantidad; String pais; String seqnum;
   public void setResponseArray(JSONArray responseArray) {
/*  36 */     this.responseArray = responseArray;
   }
   
   public Helper getHp() {
/*  40 */     return this.hp;
   }
   
   public void setHp(Helper hp) {
/*  44 */     this.hp = hp;
   }
   
   public String getFecha() {
/*  48 */     return this.fecha;
   }
   
   public void setFecha(String fecha) {
/*  52 */     this.fecha = fecha;
   }
   
   public String getProyecto() {
/*  56 */     return this.proyecto;
   }
   
   public void setProyecto(String proyecto) {
/*  60 */     this.proyecto = proyecto;
   }
   
   public String getCantidad() {
/*  64 */     return this.cantidad;
   }
   
   public void setCantidad(String cantidad) {
/*  68 */     this.cantidad = cantidad;
   }
   
   public String getPais() {
/*  72 */     return this.pais;
   }
   
   public void setPais(String pais) {
/*  76 */     this.pais = pais;
   }
   
   public String getSeqnum() {
/*  80 */     return this.seqnum;
   }
   
   public void setSeqnum(String seqnum) {
/*  84 */     this.seqnum = seqnum;
   }
   
   public ResultSet select(String tipo) throws SQLException, Exception {
/*  88 */     JSONObject jsonObj = new JSONObject();
/*  89 */     this.responseArray = new JSONArray();
 
 
 
 
 
 
     
     try {
/*  98 */       this.con.Conectar();
/*  99 */       String query = "SELECT REPORT.* FROM (\nSELECT REPORT.*, ROW_NUMBER() OVER (ORDER BY FECHA DESC) AS SEQNUM\n    FROM TBL_CDN_REPORT REPORT\n   WHERE 1 = 1 \n        AND REPORT.TIPO_REPORTE = '" + tipo + "'\n" + "        )REPORT\n" + "WHERE SEQNUM <= 100";
 
 
 
 
 
       
/* 106 */       System.out.println(query);
/* 107 */       this.rs = this.con.Consultar(query);
/* 108 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 109 */       int columna = rsmd.getColumnCount();
/* 110 */       while (this.rs.next()) {
/* 111 */         JSONObject rows = new JSONObject();
/* 112 */         for (int i = 1; i <= columna; i++) {
/* 113 */           String columnaBD = rsmd.getColumnName(i);
/* 114 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 116 */         this.responseArray.put(rows);
       } 
/* 118 */       this.hp.setArrayResponse(200, this.responseArray);
/* 119 */       this.con.CerrarConsulta();
/* 120 */     } catch (SQLException ex) {
/* 121 */       this.hp.setArrayResponse(400, this.responseArray);
/* 122 */       this.con.CerrarConsulta();
     } 
/* 124 */     return this.rs;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\Dashboardclass
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */