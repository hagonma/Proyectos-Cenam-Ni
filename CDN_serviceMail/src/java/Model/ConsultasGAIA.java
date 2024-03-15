 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import org.json.JSONArray;
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class ConsultasGAIA
 {
   int pantalla;
   int rol;
   int estado;
   int id;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  31 */   public Helper hp = new Helper();
/*  32 */   public ConexionOracle con = new ConexionOracle();
   public ConsultasGAIA() {
     try {
/*  35 */       this.con.ip = "192.168.3.2";
/*  36 */       this.con.port = "1522";
/*  37 */       this.con.bd = "GAIASV";
/*  38 */       this.con.usr = "CDN_GAIA";
/*  39 */       this.con.pass = "DRgkJV_Xwl2E";
/*  40 */       this.con.Conectar();
/*  41 */     } catch (Exception exception) {}
   }
 
   
   public String[][] DesgloseFijo(String nd) throws SQLException, Exception {
/*  46 */     nd = nd.toUpperCase();
/*  47 */     String queryNcli = null;
/*  48 */     String query = null;
/*  49 */     String[][] datos = new String[1][5];
/*  50 */     String ncli = null;
/*  51 */     String queryNdos_grp = null;
/*  52 */     String ndos_grp = null;
/*  53 */     String queryNds_factura = null;
/*  54 */     String nds_factura = "";
 
     
     try {
/*  58 */       queryNcli = "SELECT NCLI\nFROM OPS$GAIA_CC.DESGLOSE_SALDO_FIJOSV \nWHERE ND = '" + nd + "'\n" + "AND ROWNUM = 1";
 
 
       
/*  62 */       ResultSet consulta = this.con.Consultar(queryNcli);
/*  63 */       if (consulta == null) {
/*  64 */         this.con.CerrarConsulta();
/*  65 */         throw new Exception(this.con.error);
       } 
/*  67 */       if (!consulta.next()) {
         
/*  69 */         String consultaExistenciaSQL = "SELECT /*+ INDEX(A IDX_16) */ 1\nFROM OPS$GAIA_CC.PARQUE_ACTIVOS_IW A\nWHERE A.ND = '" + nd + "'\n" + "AND ROWNUM = 1";
 
 
         
/*  73 */         ResultSet consultaExistencia = this.con.Consultar(consultaExistenciaSQL);
/*  74 */         if (consultaExistencia == null) {
/*  75 */           this.con.CerrarConsulta();
/*  76 */           throw new Exception(this.con.error);
         } 
/*  78 */         if (!consultaExistencia.next()) {
/*  79 */           this.con.CerrarConsulta();
/*  80 */           throw new Exception("Servicio no encontrado: " + nd);
         } 
/*  82 */         ncli = "SIN_SALDO";
       } else {
         
         do {
/*  86 */           ncli = consulta.getString("NCLI");
/*  87 */         } while (consulta.next());
       } 
       
/*  90 */       if (ncli != null && !ncli.equals("SIN_SALDO")) {
         
/*  92 */         queryNdos_grp = "SELECT /*+ INDEX(A IDX_16) */ NDOS_GRP\nFROM OPS$GAIA_CC.PARQUE_ACTIVOS_IW A\nWHERE A.NCLI = " + ncli + "\n" + "AND A.ND = '" + nd + "'\n" + "AND ROWNUM = 1";
 
 
 
 
         
/*  98 */         ResultSet consulta2 = this.con.Consultar(queryNdos_grp);
         
/* 100 */         if (!consulta2.next()) {
/* 101 */           ndos_grp = " IS NULL";
         } else {
           do {
/* 104 */             if (consulta2.getString("NDOS_GRP") == null || consulta2.getString("NDOS_GRP").trim().isEmpty()) {
/* 105 */               ndos_grp = " IS NULL";
             } else {
/* 107 */               ndos_grp = " = " + consulta2.getString("NDOS_GRP");
             } 
/* 109 */           } while (consulta2.next());
         } 
       } 
 
       
/* 114 */       if (ndos_grp != null && !ndos_grp.equals("SIN_SALDO")) {
         
/* 116 */         queryNds_factura = "SELECT CAST(A.ND AS CHAR(15)) AS ND\nFROM OPS$GAIA_CC.PARQUE_ACTIVOS_IW A\nWHERE A.NCLI = " + ncli + "\n" + "AND A.NDOS_GRP" + ndos_grp;
 
 
 
         
/* 121 */         ResultSet consulta3 = this.con.Consultar(queryNds_factura);
         
/* 123 */         if (!consulta3.next()) {
/* 124 */           nds_factura = "SIN_SALDO";
         } else {
           do {
/* 127 */             nds_factura = nds_factura + "'" + consulta3.getString("ND").trim() + "',";
/* 128 */           } while (consulta3.next());
         } 
         
/* 131 */         if (nds_factura != null && !nds_factura.equals("SIN_SALDO") && !nds_factura.equals("")) {
/* 132 */           nds_factura = nds_factura.substring(0, nds_factura.length() - 1);
         }
       } 
       
/* 136 */       if (ncli != null && nds_factura != null && !ncli.equals("SIN_SALDO") && !nds_factura.equals("SIN_SALDO") && !nds_factura.equals("")) {
         
/* 138 */         query = "SELECT A.NCLI,\n       CAST(TO_CHAR(MAX(TO_DATE(A.FECHA_VENCIMIENTO, 'MMDDYYYY')), 'DD/MM/YYYY')AS CHAR(10)) AS FECHA_LIMITE_PAGO,\n       CAST((CASE WHEN TRIM(TO_CHAR(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(NVL(SUM(A.SALDOVIGENTE),0) + (SELECT NVL(SUM(MNT_PAI_ECH),0)\n                                                                                                                       FROM OPS$GAIA_CC.T_FINANCIAMIENTOS_DS B \n                                                                                                                       WHERE B.NCLI = " + ncli + "\n" + "                                                                                                                       AND B.ND IN(" + nds_factura + ") \n" + "                                                                                                                       AND B.MNT_SOLDE_ECH > 0\n" + "                                                                                                                       AND B.DATPAI_ECH <= (SELECT MAX(TO_DATE(C.FECHA_VENCIMIENTO, 'MMDDYYYY'))\n" + "                                                                                                                                            FROM OPS$GAIA_CC.DESGLOSE_SALDO_FIJOSV C\n" + "                                                                                                                                            WHERE C.ESTADO = 'ABIERTA'\n" + "                                                                                                                                            AND C.NCLI = " + ncli + "\n" + "                                                                                                                                            AND C.ND IN(" + nds_factura + "))),2),3),4),5),6),7),8),9),10),11) ,'999999999.99')) = '.00' THEN '0.00' \n" + "             ELSE TRIM(TO_CHAR(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(NVL(SUM(A.SALDOVIGENTE),0) + (SELECT NVL(SUM(MNT_PAI_ECH),0)\n" + "                                                                                                                  FROM OPS$GAIA_CC.T_FINANCIAMIENTOS_DS B \n" + "                                                                                                                  WHERE B.NCLI = " + ncli + "\n" + "                                                                                                                  AND B.ND IN(" + nds_factura + ") \n" + "                                                                                                                  AND B.MNT_SOLDE_ECH > 0\n" + "                                                                                                                  AND B.DATPAI_ECH <= (SELECT MAX(TO_DATE(C.FECHA_VENCIMIENTO, 'MMDDYYYY'))\n" + "                                                                                                                                       FROM OPS$GAIA_CC.DESGLOSE_SALDO_FIJOSV C\n" + "                                                                                                                                       WHERE C.ESTADO = 'ABIERTA'\n" + "                                                                                                                                       AND C.NCLI = " + ncli + "\n" + "                                                                                                                                       AND C.ND IN(" + nds_factura + "))),2),3),4),5),6),7),8),9),10),11) ,'999999999.99'))\n" + "            END) AS CHAR(10)) AS TOTAL_PAGAR_FACTURA,\n" + "       CAST(NVL((SELECT (CASE WHEN TRIM(TO_CHAR(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(NVL(SUM(B.MNT_SOLDE_ECH),0),2),3),4),5),6),7),8),9),10),11) ,'999999999.99')) = '.00' THEN '0.00' \n" + "                         ELSE TRIM(TO_CHAR(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(NVL(SUM(B.MNT_SOLDE_ECH),0),2),3),4),5),6),7),8),9),10),11) ,'999999999.99'))\n" + "                    END) \n" + "                 FROM OPS$GAIA_CC.T_FINANCIAMIENTOS_DS B \n" + "                 WHERE B.NCLI = " + ncli + "\n" + "                 AND B.ND IN(" + nds_factura + ") \n" + "                 AND B.MNT_SOLDE_ECH > 0\n" + "                 AND B.DATPAI_ECH <= (SELECT MAX(TO_DATE(C.FECHA_VENCIMIENTO, 'MMDDYYYY'))\n" + "                                      FROM OPS$GAIA_CC.DESGLOSE_SALDO_FIJOSV C\n" + "                                      WHERE C.ESTADO = 'ABIERTA'\n" + "                                      AND C.NCLI = " + ncli + "\n" + "                                      AND C.ND IN(" + nds_factura + "))),'0.00') AS CHAR(10)) AS TOTAL_FINANCIAMIENTO, \n" + "       CAST((CASE WHEN TRIM(TO_CHAR(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(NVL(SUM(A.SALDOVENCIDO),0) + (SELECT NVL(SUM(MNT_PAI_ECH),0)\n" + "                                                                                                                       FROM OPS$GAIA_CC.T_FINANCIAMIENTOS_DS B \n" + "                                                                                                                       WHERE B.NCLI = " + ncli + "\n" + "                                                                                                                       AND B.ND IN(" + nds_factura + ") \n" + "                                                                                                                       AND B.MNT_SOLDE_ECH > 0\n" + "                                                                                                                       AND B.DATPAI_ECH <= SYSDATE),2),3),4),5),6),7),8),9),10),11) ,'999999999.99')) = '.00' THEN '0.00' \n" + "             ELSE TRIM(TO_CHAR(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(ROUND(NVL(SUM(A.SALDOVENCIDO),0) + (SELECT NVL(SUM(MNT_PAI_ECH),0)\n" + "                                                                                                                  FROM OPS$GAIA_CC.T_FINANCIAMIENTOS_DS B \n" + "                                                                                                                  WHERE B.NCLI = " + ncli + "\n" + "                                                                                                                  AND B.ND IN(" + nds_factura + ") \n" + "                                                                                                                  AND B.MNT_SOLDE_ECH > 0\n" + "                                                                                                                  AND B.DATPAI_ECH <= SYSDATE),2),3),4),5),6),7),8),9),10),11) ,'999999999.99'))\n" + "            END) AS CHAR(15)) AS TOTAL_SALDO_PENDIENTE\n" + "FROM OPS$GAIA_CC.DESGLOSE_SALDO_FIJOSV A\n" + "WHERE A.ESTADO = 'ABIERTA'\n" + "AND A.NCLI = " + ncli + "\n" + "AND A.ND IN(" + nds_factura + ")\n" + "GROUP BY A.NCLI";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
         
/* 192 */         ResultSet consulta2 = this.con.Consultar(query);
         
/* 194 */         if (!consulta2.next()) {
/* 195 */           datos[0][0] = null;
/* 196 */           datos[0][1] = null;
/* 197 */           datos[0][2] = null;
/* 198 */           datos[0][3] = null;
/* 199 */           datos[0][4] = null;
         } else {
           do {
/* 202 */             datos[0][0] = consulta2.getString("NCLI");
/* 203 */             datos[0][1] = consulta2.getString("FECHA_LIMITE_PAGO").trim();
/* 204 */             if (consulta2.getString("TOTAL_PAGAR_FACTURA").trim().substring(0, 1).equals(".")) {
/* 205 */               datos[0][2] = "0" + consulta2.getString("TOTAL_PAGAR_FACTURA").trim();
             } else {
/* 207 */               datos[0][2] = consulta2.getString("TOTAL_PAGAR_FACTURA").trim();
             } 
/* 209 */             if (consulta2.getString("TOTAL_FINANCIAMIENTO").trim().substring(0, 1).equals(".")) {
/* 210 */               datos[0][3] = "0" + consulta2.getString("TOTAL_FINANCIAMIENTO").trim();
             } else {
/* 212 */               datos[0][3] = consulta2.getString("TOTAL_FINANCIAMIENTO").trim();
             } 
/* 214 */             if (consulta2.getString("TOTAL_SALDO_PENDIENTE").trim().substring(0, 1).equals(".")) {
/* 215 */               datos[0][4] = "0" + consulta2.getString("TOTAL_SALDO_PENDIENTE").trim();
             } else {
/* 217 */               datos[0][4] = consulta2.getString("TOTAL_SALDO_PENDIENTE").trim();
             } 
/* 219 */           } while (consulta2.next());
         } 
       } 
       
/* 223 */       this.con.CerrarConsulta();
/* 224 */       return datos;
     }
/* 226 */     catch (SQLException e) {
/* 227 */       this.con.CerrarConsulta();
/* 228 */       datos = new String[1][2];
/* 229 */       datos[0][0] = "ERROR";
/* 230 */       datos[0][1] = e.toString();
/* 231 */       return datos;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ConsultasGAIA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */