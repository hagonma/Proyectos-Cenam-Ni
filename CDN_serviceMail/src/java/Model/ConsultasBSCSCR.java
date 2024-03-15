 package Model;
 
 import Controller.ConexionOracle;
 import Controller.Helper;
 import java.sql.CallableStatement;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 public class ConsultasBSCSCR
 {
   int pantalla;
   int rol;
   int estado;
   int id;
   public ResultSet rs;
   public PreparedStatement ps;
   public JSONArray responseArray;
/*  30 */   public Helper hp = new Helper();
/*  31 */   public ConexionOracle con = new ConexionOracle();
/*  32 */   public ConexionOracle conO = new ConexionOracle();
   
   public ConsultasBSCSCR() {
/*  35 */     this.con.ip = "172.17.241.216";
/*  36 */     this.con.port = "3871";
/*  37 */     this.con.bd = "BSCSCRI";
 
     
/*  40 */     this.con.usr = "READ";
/*  41 */     this.con.pass = "READ";
   }
   
   public ResultSet llamadasBSCS(String id) throws SQLException, Exception {
/*  45 */     String dato = "";
/*  46 */     String equipos = "";
     try {
/*  48 */       this.con.Conectar();
/*  49 */       String query = "SELECT COUNT(*) AS SERVICIO\nFROM   PR_SERV_SPCODE_HIST C, \n       PROFILE_SERVICE A, \n       RATEPLAN_HIST B, \n       MPULKTMB D,\n       MPUSNTAB E,\n       MPUSPTAB F\nWHERE  C.CO_ID = A.CO_ID \n       AND D.TMCODE = B.TMCODE \n       AND D.SPCODE = C.SPCODE \n       AND F.SPCODE = C.SPCODE \n       AND D.SNCODE = A.SNCODE\n       AND D.SNCODE = E.SNCODE\n       AND C.SNCODE = A.SNCODE \n       AND B.CO_ID = A.CO_ID \n       AND E.DES LIKE '%Minutos%'\n       AND B.SEQNO = (SELECT MAX(SEQNO) \n                       FROM   RATEPLAN_HIST \n                       WHERE  CO_ID = B.CO_ID) \n       AND C.VALID_FROM_DATE = (SELECT MAX(VALID_FROM_DATE) \n                                 FROM   PR_SERV_SPCODE_HIST \n                                 WHERE  CO_ID = C.CO_ID \n                                        AND SNCODE = C.SNCODE) \n       AND D.VSCODE = (SELECT MAX(VSCODE) \n                        FROM   MPULKTMB\n                        WHERE  TMCODE = D.TMCODE \n                               AND SPCODE = D.SPCODE \n                               AND SNCODE = D.SNCODE) \n       AND A.CO_ID = (SELECT H.CO_ID CONTRATO  \n                       FROM   DIRECTORY_NUMBER G \n                              INNER JOIN CONTR_SERVICES_CAP H \n                                      ON H.DN_ID = G.DN_ID \n                       WHERE  TRIM(H.CS_DEACTIV_DATE) IS NULL \n                              AND G.DN_NUM = '" + id + "') ";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/*  83 */       ResultSet resultado = this.con.Consultar(query);
/*  84 */       while (resultado.next()) {
/*  85 */         dato = resultado.getString("SERVICIO");
       }
/*  87 */       if (dato.isEmpty()) {
/*  88 */         equipos = "0";
       } else {
/*  90 */         equipos = dato;
       } 
/*  92 */       String update = "UPDATE CDN_MAIL SET LLAMADA =" + equipos + " WHERE ID=" + id;
/*  93 */       this.con.CerrarConsulta();
/*  94 */       this.conO.Conectar();
/*  95 */       this.conO.Consultar(update);
/*  96 */       this.conO.CerrarConsulta();
/*  97 */       return this.rs;
/*  98 */     } catch (SQLException e) {
/*  99 */       this.con.CerrarConsulta();
/* 100 */       return null;
     } 
   }
   
   public ResultSet smsBSCS(String id) throws SQLException, Exception {
/* 105 */     String dato = "";
/* 106 */     String equipos = "";
     try {
/* 108 */       this.con.Conectar();
/* 109 */       String query = "SELECT COUNT(*) AS SERVICIO\nFROM   PR_SERV_SPCODE_HIST C, \n       PROFILE_SERVICE A, \n       RATEPLAN_HIST B, \n       MPULKTMB D,\n       MPUSNTAB E,\n       MPUSPTAB F\nWHERE  C.CO_ID = A.CO_ID \n       AND D.TMCODE = B.TMCODE \n       AND D.SPCODE = C.SPCODE \n       AND F.SPCODE = C.SPCODE \n       AND D.SNCODE = A.SNCODE\n       AND D.SNCODE = E.SNCODE\n       AND C.SNCODE = A.SNCODE \n       AND B.CO_ID = A.CO_ID \n       AND E.DES LIKE '%SMS%'\n       AND B.SEQNO = (SELECT MAX(SEQNO) \n                       FROM   RATEPLAN_HIST \n                       WHERE  CO_ID = B.CO_ID) \n       AND C.VALID_FROM_DATE = (SELECT MAX(VALID_FROM_DATE) \n                                 FROM   PR_SERV_SPCODE_HIST \n                                 WHERE  CO_ID = C.CO_ID \n                                        AND SNCODE = C.SNCODE) \n       AND D.VSCODE = (SELECT MAX(VSCODE) \n                        FROM   MPULKTMB\n                        WHERE  TMCODE = D.TMCODE \n                               AND SPCODE = D.SPCODE \n                               AND SNCODE = D.SNCODE) \n       AND A.CO_ID = (SELECT H.CO_ID CONTRATO  \n                       FROM   DIRECTORY_NUMBER G \n                              INNER JOIN CONTR_SERVICES_CAP H \n                                      ON H.DN_ID = G.DN_ID \n                       WHERE  TRIM(H.CS_DEACTIV_DATE) IS NULL \n                              AND G.DN_NUM = '" + id + "') ";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 143 */       ResultSet resultado = this.con.Consultar(query);
/* 144 */       while (resultado.next()) {
/* 145 */         dato = resultado.getString("SERVICIO");
       }
/* 147 */       if (dato.isEmpty()) {
/* 148 */         equipos = "0";
       } else {
/* 150 */         equipos = dato;
       } 
/* 152 */       String update = "UPDATE CDN_MAIL SET SMS =" + equipos + " WHERE ID=" + id;
/* 153 */       this.con.CerrarConsulta();
/* 154 */       this.conO.Conectar();
/* 155 */       this.conO.Consultar(update);
/* 156 */       this.conO.CerrarConsulta();
/* 157 */       return this.rs;
/* 158 */     } catch (SQLException e) {
/* 159 */       this.con.CerrarConsulta();
/* 160 */       return null;
     } 
   }
   
   public ResultSet musicaBSCS(String id) throws SQLException, Exception {
/* 165 */     String dato = "";
/* 166 */     String equipos = "";
     try {
/* 168 */       this.con.Conectar();
/* 169 */       String query = "SELECT COUNT(*) AS SERVICIO\nFROM   PR_SERV_SPCODE_HIST C, \n       PROFILE_SERVICE A, \n       RATEPLAN_HIST B, \n       MPULKTMB D,\n       MPUSNTAB E,\n       MPUSPTAB F\nWHERE  C.CO_ID = A.CO_ID \n       AND D.TMCODE = B.TMCODE \n       AND D.SPCODE = C.SPCODE \n       AND F.SPCODE = C.SPCODE \n       AND D.SNCODE = A.SNCODE\n       AND D.SNCODE = E.SNCODE\n       AND C.SNCODE = A.SNCODE \n       AND B.CO_ID = A.CO_ID \n       AND E.DES LIKE '%MUSICA%'\n       AND B.SEQNO = (SELECT MAX(SEQNO) \n                       FROM   RATEPLAN_HIST \n                       WHERE  CO_ID = B.CO_ID) \n       AND C.VALID_FROM_DATE = (SELECT MAX(VALID_FROM_DATE) \n                                 FROM   PR_SERV_SPCODE_HIST \n                                 WHERE  CO_ID = C.CO_ID \n                                        AND SNCODE = C.SNCODE) \n       AND D.VSCODE = (SELECT MAX(VSCODE) \n                        FROM   MPULKTMB\n                        WHERE  TMCODE = D.TMCODE \n                               AND SPCODE = D.SPCODE \n                               AND SNCODE = D.SNCODE) \n       AND A.CO_ID = (SELECT H.CO_ID CONTRATO  \n                       FROM   DIRECTORY_NUMBER G \n                              INNER JOIN CONTR_SERVICES_CAP H \n                                      ON H.DN_ID = G.DN_ID \n                       WHERE  TRIM(H.CS_DEACTIV_DATE) IS NULL \n                              AND G.DN_NUM = '" + id + "') ";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 203 */       ResultSet resultado = this.con.Consultar(query);
/* 204 */       while (resultado.next()) {
/* 205 */         dato = resultado.getString("SERVICIO");
       }
/* 207 */       if (dato.isEmpty()) {
/* 208 */         equipos = "0";
       } else {
/* 210 */         equipos = dato;
       } 
/* 212 */       String update = "UPDATE CDN_MAIL SET CLAROMUSICA =" + equipos + " WHERE ID=" + id;
/* 213 */       this.con.CerrarConsulta();
/* 214 */       this.conO.Conectar();
/* 215 */       this.conO.Consultar(update);
/* 216 */       this.conO.CerrarConsulta();
/* 217 */       return this.rs;
/* 218 */     } catch (SQLException e) {
/* 219 */       this.con.CerrarConsulta();
/* 220 */       return null;
     } 
   }
   
   public ResultSet sinfronteraBSCS(String id) throws SQLException, Exception {
/* 225 */     String dato = "";
/* 226 */     String equipos = "";
     try {
/* 228 */       this.con.Conectar();
/* 229 */       String query = "SELECT COUNT(*) AS SERVICIO\nFROM   PR_SERV_SPCODE_HIST C, \n       PROFILE_SERVICE A, \n       RATEPLAN_HIST B, \n       MPULKTMB D,\n       MPUSNTAB E,\n       MPUSPTAB F\nWHERE  C.CO_ID = A.CO_ID \n       AND D.TMCODE = B.TMCODE \n       AND D.SPCODE = C.SPCODE \n       AND F.SPCODE = C.SPCODE \n       AND D.SNCODE = A.SNCODE\n       AND D.SNCODE = E.SNCODE\n       AND C.SNCODE = A.SNCODE \n       AND B.CO_ID = A.CO_ID \n       AND E.DES LIKE '%SIN FRONTER%'\n       AND B.SEQNO = (SELECT MAX(SEQNO) \n                       FROM   RATEPLAN_HIST \n                       WHERE  CO_ID = B.CO_ID) \n       AND C.VALID_FROM_DATE = (SELECT MAX(VALID_FROM_DATE) \n                                 FROM   PR_SERV_SPCODE_HIST \n                                 WHERE  CO_ID = C.CO_ID \n                                        AND SNCODE = C.SNCODE) \n       AND D.VSCODE = (SELECT MAX(VSCODE) \n                        FROM   MPULKTMB\n                        WHERE  TMCODE = D.TMCODE \n                               AND SPCODE = D.SPCODE \n                               AND SNCODE = D.SNCODE) \n       AND A.CO_ID = (SELECT H.CO_ID CONTRATO  \n                       FROM   DIRECTORY_NUMBER G \n                              INNER JOIN CONTR_SERVICES_CAP H \n                                      ON H.DN_ID = G.DN_ID \n                       WHERE  TRIM(H.CS_DEACTIV_DATE) IS NULL \n                              AND G.DN_NUM = '" + id + "') ";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 263 */       ResultSet resultado = this.con.Consultar(query);
/* 264 */       while (resultado.next()) {
/* 265 */         dato = resultado.getString("SERVICIO");
       }
/* 267 */       if (dato.isEmpty()) {
/* 268 */         equipos = "0";
       } else {
/* 270 */         equipos = dato;
       } 
/* 272 */       String update = "UPDATE CDN_MAIL SET SINFRONTERA =" + equipos + " WHERE ID=" + id;
/* 273 */       this.con.CerrarConsulta();
/* 274 */       this.conO.Conectar();
/* 275 */       this.conO.Consultar(update);
/* 276 */       this.conO.CerrarConsulta();
/* 277 */       return this.rs;
/* 278 */     } catch (SQLException e) {
/* 279 */       this.con.CerrarConsulta();
/* 280 */       return null;
     } 
   }
   
   public ResultSet internetBSCS(String id) throws SQLException, Exception {
/* 285 */     String dato = "";
/* 286 */     String equipos = "";
     try {
/* 288 */       this.con.Conectar();
/* 289 */       String query = "SELECT COUNT(*) AS SERVICIO\nFROM   PR_SERV_SPCODE_HIST C, \n       PROFILE_SERVICE A, \n       RATEPLAN_HIST B, \n       MPULKTMB D,\n       MPUSNTAB E,\n       MPUSPTAB F\nWHERE  C.CO_ID = A.CO_ID \n       AND D.TMCODE = B.TMCODE \n       AND D.SPCODE = C.SPCODE \n       AND F.SPCODE = C.SPCODE \n       AND D.SNCODE = A.SNCODE\n       AND D.SNCODE = E.SNCODE\n       AND C.SNCODE = A.SNCODE \n       AND B.CO_ID = A.CO_ID \n       AND E.DES LIKE '%Navegación%'\n       AND B.SEQNO = (SELECT MAX(SEQNO) \n                       FROM   RATEPLAN_HIST \n                       WHERE  CO_ID = B.CO_ID) \n       AND C.VALID_FROM_DATE = (SELECT MAX(VALID_FROM_DATE) \n                                 FROM   PR_SERV_SPCODE_HIST \n                                 WHERE  CO_ID = C.CO_ID \n                                        AND SNCODE = C.SNCODE) \n       AND D.VSCODE = (SELECT MAX(VSCODE) \n                        FROM   MPULKTMB\n                        WHERE  TMCODE = D.TMCODE \n                               AND SPCODE = D.SPCODE \n                               AND SNCODE = D.SNCODE) \n       AND A.CO_ID = (SELECT H.CO_ID CONTRATO  \n                       FROM   DIRECTORY_NUMBER G \n                              INNER JOIN CONTR_SERVICES_CAP H \n                                      ON H.DN_ID = G.DN_ID \n                       WHERE  TRIM(H.CS_DEACTIV_DATE) IS NULL \n                              AND G.DN_NUM = '" + id + "') ";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 323 */       ResultSet resultado = this.con.Consultar(query);
/* 324 */       while (resultado.next()) {
/* 325 */         dato = resultado.getString("SERVICIO");
       }
/* 327 */       if (dato.isEmpty()) {
/* 328 */         equipos = "0";
       } else {
/* 330 */         equipos = dato;
       } 
/* 332 */       String update = "UPDATE CDN_MAIL SET INTERNET =" + equipos + " WHERE ID=" + id;
/* 333 */       this.con.CerrarConsulta();
/* 334 */       this.conO.Conectar();
/* 335 */       this.conO.Consultar(update);
/* 336 */       this.conO.CerrarConsulta();
/* 337 */       return this.rs;
/* 338 */     } catch (SQLException e) {
/* 339 */       this.con.CerrarConsulta();
/* 340 */       return null;
     } 
   }
   
   public ResultSet correoclienteBSCS(String id) throws SQLException, Exception {
/* 345 */     String dato = "";
/* 346 */     String equipos = "";
/* 347 */     String datos = "";
/* 348 */     String equiposs = "";
/* 349 */     String nombre = "";
/* 350 */     String nombres = "";
     try {
/* 352 */       this.conO.Conectar();
/* 353 */       String consulta = "SELECT * FROM CDN_MAIL WHERE ID=" + id + "";
/* 354 */       ResultSet result = this.conO.Consultar(consulta);
/* 355 */       while (result.next()) {
/* 356 */         dato = result.getString("CUSTCODE");
       }
/* 358 */       if (dato.isEmpty()) {
/* 359 */         equipos = "0";
       } else {
/* 361 */         equipos = dato;
       } 
/* 363 */       this.con.Conectar();
/* 364 */       String query = "SELECT B.CCEMAIL AS EMAIL,(B.CCFNAME || ' ' ||B.CCLNAME) AS NAME \nFROM CUSTOMER_ALL A\nINNER JOIN CCONTACT_ALL B ON A.CUSTOMER_ID = B.CUSTOMER_ID\nWHERE B.CCBILL='X' \nAND B.CCEMAIL IS NOT NULL\nAND A.CUSTCODE = '" + equipos + "'";
 
 
 
 
       
/* 370 */       ResultSet resultado = this.con.Consultar(query);
/* 371 */       while (resultado.next()) {
/* 372 */         datos = resultado.getString("EMAIL");
/* 373 */         nombre = resultado.getString("NAME");
       } 
/* 375 */       if (datos.isEmpty()) {
/* 376 */         equiposs = "0";
       } else {
/* 378 */         equiposs = datos;
       } 
       
/* 381 */       if (nombre.isEmpty()) {
/* 382 */         nombres = "0";
       } else {
/* 384 */         nombres = nombre;
       } 
       
/* 387 */       this.conO.CerrarConsulta();
       
/* 389 */       this.conO.Conectar();
/* 390 */       String update = "UPDATE CDN_MAIL SET CORREO_CLIENTE ='" + equiposs + "',NOMBRE_CLIENTE='" + nombres + "' WHERE ID=" + id;
/* 391 */       this.con.CerrarConsulta();
       
/* 393 */       this.conO.Consultar(update);
/* 394 */       this.conO.CerrarConsulta();
/* 395 */       return this.rs;
/* 396 */     } catch (SQLException e) {
/* 397 */       this.con.CerrarConsulta();
/* 398 */       return null;
     } 
   }
   
   public ResultSet montoBSCS(String id) throws SQLException, Exception {
/* 403 */     String dato = "";
/* 404 */     String equipos = "";
/* 405 */     String datos = "";
/* 406 */     String equiposs = "";
/* 407 */     String datoss = "";
/* 408 */     String equiposss = "";
     try {
/* 410 */       this.con.Conectar();
/* 411 */       String query = "select  sum(r.monto) AS PRECIO\nfrom (\nselect dn.dn_num as numero, contr.co_id as contrato, round(sum(mp.accessfee)*1.13,2) as monto\nfrom contract_all contr\n    inner join pr_serv_spcode_hist b on contr.co_id = b.co_id and b.histno in (select max(x.histno) from pr_serv_spcode_hist x \n                                                                               where x.co_id = b.co_id and x.sncode = b.sncode group by x.sncode, x.co_id)\n    inner join rateplan_hist rt on rt.co_id = b.co_id and contr.co_id=rt.co_id and seqno = (select max(r.seqno) from rateplan_hist r \n                                                                                            where rt.co_id = r.co_id)\n    inner join mpusptab m on m.spcode = b.spcode\n    inner join mpulktmb mp on mp.tmcode = rt.tmcode and mp.sncode = b.sncode and mp.spcode = b.spcode\n                    and mp.vscode = (select max(x.vscode) from mpulktmb x where x.tmcode=mp.tmcode and x.spcode = mp.spcode and x.sncode = mp.sncode)\n    inner join mpusntab u on u.sncode = b.sncode \n    inner join pr_serv_status_hist pvser on pvser.co_id = b.co_id and pvser.histno in (select max(x.histno) from pr_serv_status_hist x \n                                                                                       where x.co_id = b.co_id and x.sncode = b.sncode) \n               AND pvser.status in ('A','S')\n    inner join contr_services_cap cc on cc.co_id = contr.co_id and cc.cs_deactiv_date is null\n    inner join directory_number dn on cc.dn_id = dn.dn_id\nWhere contr.CH_STATUS in ('a','s')\n    and dn.dn_num = '" + id + "'\n" + "group by dn.dn_num, contr.co_id\n" + "union\n" + "select dn.dn_num as numero, contr.co_id as contrato, round(bv.ACCESSFEE * 1.13,2) AS monto\n" + "from contract_all contr\n" + "     inner join pr_serv_spcode_hist b on contr.co_id = b.co_id \n" + "                and b.histno in (select max(x.histno) from pr_serv_spcode_hist x where x.co_id = b.co_id and x.sncode = b.sncode)\n" + "     inner join rateplan_hist rt on rt.co_id = b.co_id and rt.seqno = (select max(r.seqno) from rateplan_hist r \n" + "                                                                                            where rt.co_id = r.co_id)\n" + "     inner join mpusptab m on m.spcode = b.spcode\n" + "     inner join mpulktmb mp on mp.tmcode = rt.tmcode and mp.sncode = b.sncode and mp.spcode = b.spcode\n" + "                    and mp.vscode = (select max(x.vscode) from mpulktmb x where x.tmcode=mp.tmcode and x.spcode = mp.spcode and x.sncode = mp.sncode)\n" + "     inner join mpusntab u on u.sncode = b.sncode\n" + "     inner join parameter_value pv on pv.co_id = contr.co_id and pv.parameter_id = 10134\n" + "                and pv.prm_seqno = (select max(v.prm_seqno) from parameter_value v where v.co_id = pv.co_id and v.parameter_id = 10134)\n" + "     inner join mpulkpvm z on z.pv_combi_id = mp.pv_combi_id and (pv.prm_value_string = z.prm_value_string or pv.PRM_VALUE_NUMBER = z.prm_value_string)\n" + "     inner join mpulkpvb bv on mp.pv_combi_id = bv.pv_combi_id and bv.set_id = z.set_id\n" + "     inner join contr_services_cap cc on cc.co_id = contr.co_id and cc.cs_deactiv_date is null\n" + "     inner join directory_number dn on cc.dn_id = dn.dn_id\n" + "where contr.ch_status in ('a','s')\n" + "        and b.sncode in ('1305')\n" + "        and dn.dn_num = '" + id + "'\n" + "union\n" + "select f.dn_num, f.co_id, (select fq.quota_amount from financing_quotas fq where fq.quota_id = 2 and fq.fina_id = f.fina_id) monto\n" + "from financing f\n" + "where \n" + "fina_open_quota > 2 and fina_open_amount > 1000 \n" + "and f.dn_num = '" + id + "'\n" + ") r\n" + "group by r.numero, r.contrato";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 459 */       ResultSet resultado = this.con.Consultar(query);
/* 460 */       while (resultado.next()) {
/* 461 */         dato = resultado.getString("PRECIO");
       }
/* 463 */       if (dato.isEmpty()) {
/* 464 */         equipos = "0";
       } else {
/* 466 */         equipos = dato;
       } 
       
/* 469 */       String consulta = "SELECT NVL(ROUND(SUM((TOTAL_AMOUNT-COUPLING_AMOUNT ) / FINA_QUOTA),3),0) AS TOTAL FROM FINANCING WHERE DN_NUM = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";
/* 470 */       ResultSet result = this.con.Consultar(consulta);
/* 471 */       while (result.next()) {
/* 472 */         datos = result.getString("TOTAL");
       }
/* 474 */       if (datos.isEmpty()) {
/* 475 */         equiposs = "0";
       } else {
/* 477 */         equiposs = datos;
       } 
/* 479 */       float x = Float.parseFloat(equipos);
/* 480 */       float suma = x;
/* 481 */       this.conO.Conectar();
/* 482 */       String moneda = "SELECT B.MONEDA as MONEDA FROM CDN_MAIL A\nINNER JOIN CDN_PAIS B ON B.CODIGO = A.PAIS_ID\nWHERE A.ID='" + id + "'";
 
       
/* 485 */       ResultSet resul = this.conO.Consultar(moneda);
/* 486 */       while (resul.next()) {
/* 487 */         datoss = resul.getString("MONEDA");
       }
/* 489 */       if (datos.isEmpty()) {
/* 490 */         equiposss = "0";
       } else {
/* 492 */         equiposss = datoss;
       } 
       
/* 495 */       String monto = equiposss + Float.toString(suma);
       
/* 497 */       String update = "UPDATE CDN_MAIL SET CUOTA ='" + monto + "' WHERE ID=" + id;
/* 498 */       this.con.CerrarConsulta();
/* 499 */       this.conO.Consultar(update);
/* 500 */       this.conO.CerrarConsulta();
/* 501 */       return this.rs;
/* 502 */     } catch (SQLException e) {
/* 503 */       this.con.CerrarConsulta();
/* 504 */       return null;
     } 
   }
   
   public String findSaldoByNumber(String number) throws SQLException, Exception {
/* 509 */     JSONObject jsonObj = new JSONObject();
     try {
/* 511 */       this.con.usr = "CDN_USER_BSCSCR";
/* 512 */       this.con.pass = "CdN#A_$2019b";
/* 513 */       this.con.Conectar();
       
/* 515 */       CallableStatement cs = null;
/* 516 */       cs = this.con.conexion.prepareCall("{? = call regsoporte.wa_pkg_cdn_reg.fn_info_ohref (?)}");
/* 517 */       cs.registerOutParameter(1, 12);
/* 518 */       cs.setString(2, number);
/* 519 */       cs.execute();
       
/* 521 */       String retorno = cs.getString(1);
       
/* 523 */       jsonObj.put("Text", retorno);
/* 524 */       this.hp.setResponse(200, jsonObj);
/* 525 */       this.con.CerrarConsulta();
/* 526 */     } catch (SQLException ex) {
/* 527 */       this.hp.setResponse(400, jsonObj);
/* 528 */       this.con.CerrarConsulta();
     } 
/* 530 */     return this.hp.getResponse();
   }
   
   public ResultSet findFinancing(String id) throws SQLException, Exception {
/* 534 */     JSONObject jsonObj = new JSONObject();
/* 535 */     this.responseArray = new JSONArray();
     try {
/* 537 */       this.con.Conectar();
/* 538 */       String query = "SELECT  nvl(ROUND(SUM((TOTAL_AMOUNT-COUPLING_AMOUNT ) / FINA_QUOTA),3),0) AS TOTAL FROM FINANCING WHERE\nDN_NUM = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";
 
       
/* 541 */       this.rs = this.con.Consultar(query);
/* 542 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 543 */       int columna = rsmd.getColumnCount();
       
/* 545 */       while (this.rs.next()) {
/* 546 */         JSONObject rows = new JSONObject();
/* 547 */         for (int i = 1; i <= columna; i++) {
/* 548 */           String columnaBD = rsmd.getColumnName(i);
/* 549 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 551 */         this.responseArray.put(rows);
       } 
/* 553 */       this.hp.setArrayResponse(200, this.responseArray);
/* 554 */       this.con.CerrarConsulta();
/* 555 */     } catch (SQLException ex) {
/* 556 */       this.hp.setArrayResponse(400, this.responseArray);
/* 557 */       this.con.CerrarConsulta();
     } 
/* 559 */     return this.rs;
   }
   
   public ResultSet findSaldoVencido(String id) throws SQLException, Exception {
/* 563 */     JSONObject jsonObj = new JSONObject();
/* 564 */     this.responseArray = new JSONArray();
     try {
/* 566 */       this.con.Conectar();
/* 567 */       String query = "select \nCA.customer_id,\ndn.dn_num telefono,\nnvl((select sum(oa.ohopnamt_doc) \n    from ORDERHDR_ALL oa where oa.customer_id=ca.CUSTOMER_ID and oa.ohstatus='IN' and trunc(oa.OHDUEDATE)<trunc(sysdate) group by oa.customer_id),0) saldo_vencido, \nnvl((\n    select sum(oa.ohopnamt_doc) \n    from ORDERHDR_ALL oa \n    where oa.customer_id=ca.CUSTOMER_ID and oa.ohstatus='IN' group by oa.customer_id),0) saldo_total,(select to_char(max(oa.OHDUEDATE),'dd/mm/yyyy') \n    from ORDERHDR_ALL oa where oa.customer_id=ca.CUSTOMER_ID and oa.ohstatus='IN') fecha_vencimiento,  nvl((select count(case oa1.ohopnamt_doc when 0 then null else oa1.ohopnamt_doc end ) from ORDERHDR_ALL oa1 \nwhere oa1.customer_id=ca.CUSTOMER_ID and oa1.ohstatus='IN' and trunc(oa1.OHDUEDATE)<trunc(sysdate) group by oa1.customer_id),0) mora,c_all.custcode from directory_number dn \nleft join contr_services_cap csc on csc.dn_id=dn.dn_id \nleft join contract_all ca        on ca.co_id=csc.co_id \nleft join contract_history ch    on ch.co_id=ca.co_id \nleft join customer_all c_all        on c_all.customer_id = CA.customer_id where\ncsc.seqno = (select max(csc1.seqno) from contr_services_cap csc1 where CSC1.co_id=csc.co_id) AND ch.ch_seqno = (SELECT MAX (ch1.ch_seqno)FROM contract_history ch1 WHERE ch1.co_id = ch.co_id) \nand ch.ch_status in ('a','s') \nand dn.dn_num IN( '" + id + "')";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 591 */       this.rs = this.con.Consultar(query);
/* 592 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/* 593 */       int columna = rsmd.getColumnCount();
       
/* 595 */       while (this.rs.next()) {
/* 596 */         JSONObject rows = new JSONObject();
/* 597 */         for (int i = 1; i <= columna; i++) {
/* 598 */           String columnaBD = rsmd.getColumnName(i);
/* 599 */           rows.put(columnaBD, this.rs.getString(columnaBD));
         } 
/* 601 */         this.responseArray.put(rows);
       } 
/* 603 */       this.hp.setArrayResponse(200, this.responseArray);
/* 604 */       this.con.CerrarConsulta();
/* 605 */     } catch (SQLException ex) {
/* 606 */       this.hp.setArrayResponse(400, this.responseArray);
/* 607 */       this.con.CerrarConsulta();
     } 
/* 609 */     return this.rs;
   }
   
   public ResultSet serviciosFijosBSCS(String id) throws SQLException, Exception {
/* 613 */     String television = null;
/* 614 */     String llamadas = null;
/* 615 */     String internet = null;
/* 616 */     String claro_video = null;
/* 617 */     String fox = null;
/* 618 */     String hbo = null;
     try {
/* 620 */       this.con.Conectar();
/* 621 */       String query = "SELECT NVL(MAX((CASE WHEN D.SNCODE IN(SELECT SNCODE\n                                     FROM SYSADM.MPUSNTAB\n                                     WHERE UPPER(DES) LIKE '%TV%') THEN 1 ELSE 0 END)),0) TELEVISION,\n      NVL(MAX((CASE WHEN D.SNCODE IN(SELECT SNCODE\n                                     FROM SYSADM.MPUSNTAB\n                                     WHERE UPPER(DES) LIKE '%VOZ%LFI%') THEN 1 ELSE 0 END)),0) LLAMADAS,\n      NVL(MAX((CASE WHEN D.SNCODE IN(119) THEN 1 ELSE 0 END)),0) INTERNET,\n      NVL(MAX((CASE WHEN D.SNCODE IN(SELECT SNCODE\n                                     FROM SYSADM.MPUSNTAB\n                                     WHERE UPPER(DES) LIKE '%CLARO%VIDEO%') THEN 1 ELSE 0 END)),0) CLARO_VIDEO,\n      NVL(MAX((CASE WHEN D.SNCODE IN(SELECT SNCODE\n                                     FROM SYSADM.MPUSNTAB\n                                     WHERE UPPER(DES) LIKE '%FOX%') THEN 1 ELSE 0 END)),0) FOX,\n      NVL(MAX((CASE WHEN D.SNCODE IN(SELECT SNCODE\n                                     FROM SYSADM.MPUSNTAB\n                                     WHERE UPPER(DES) LIKE '%HBO%') THEN 1 ELSE 0 END)),0) HBO\nFROM SYSADM.CUSTOMER_ALL A\nINNER JOIN SYSADM.CONTRACT_ALL B ON B.CUSTOMER_ID = A.CUSTOMER_ID\nINNER JOIN SYSADM.PROFILE_SERVICE C ON C.CO_ID = B.CO_ID\nINNER JOIN SYSADM.PR_SERV_STATUS_HIST D ON D.CO_ID = C.CO_ID AND D.HISTNO = C.STATUS_HISTNO AND D.SNCODE = C.SNCODE\nLEFT JOIN SYSADM.MPUSNTAB D ON D.SNCODE = D.SNCODE\nWHERE A.CUSTCODE = " + id;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 643 */       ResultSet resultado = this.con.Consultar(query);
/* 644 */       while (resultado.next()) {
/* 645 */         television = resultado.getString("TELEVISION");
/* 646 */         llamadas = resultado.getString("LLAMADAS");
/* 647 */         internet = resultado.getString("INTERNET");
/* 648 */         claro_video = resultado.getString("CLARO_VIDEO");
/* 649 */         fox = resultado.getString("FOX");
/* 650 */         hbo = resultado.getString("HBO");
       } 
/* 652 */       if (television.equals(null)) {
/* 653 */         television = "0";
       }
       
/* 656 */       if (llamadas.equals(null)) {
/* 657 */         llamadas = "0";
       }
       
/* 660 */       if (internet.equals(null)) {
/* 661 */         internet = "0";
       }
       
/* 664 */       if (claro_video.equals(null)) {
/* 665 */         claro_video = "0";
       }
       
/* 668 */       if (fox.equals(null)) {
/* 669 */         fox = "0";
       }
       
/* 672 */       if (hbo.equals(null)) {
/* 673 */         hbo = "0";
       }
       
/* 676 */       String update = "UPDATE CDNRG.CDN_MAIL SET TELEVISION = " + television + ", " + "    LLAMADA = " + llamadas + ", " + "    INTERNET = " + internet + ", " + "    CLARO_VIDEO = " + claro_video + ", " + "    FOX = " + fox + ", " + "    HBO = " + hbo + " " + "WHERE ID = " + id;
 
 
 
 
 
 
       
/* 684 */       this.con.CerrarConsulta();
/* 685 */       this.conO.Conectar();
/* 686 */       this.conO.Consultar(update);
/* 687 */       this.conO.CerrarConsulta();
/* 688 */       return this.rs;
/* 689 */     } catch (SQLException e) {
/* 690 */       this.con.CerrarConsulta();
/* 691 */       return null;
     } 
   }
 
   
   public String[][] DesgloseFijo(String customer_id) throws SQLException, Exception {
/* 697 */     String query1 = null;
     
/* 699 */     String[][] datos = (String[][])null;
     
     try {
/* 702 */       query1 = "SELECT A.CUSTOMER_ID,\n       TO_CHAR(MAX(A.OHDUEDATE), 'DD/MM/YYYY') FECHA_LIMITE_PAGO,\n       (CASE WHEN TRIM(TO_CHAR(NVL(SUM(A.OHOPNAMT_GL), 0), '9999999.99'))  LIKE '.%' THEN '0'||TRIM(TO_CHAR(NVL(SUM(A.OHOPNAMT_GL), 0), '9999999.99'))\n             ELSE TRIM(TO_CHAR(NVL(SUM(A.OHOPNAMT_GL), 0), '9999999.99'))\n        END) TOTAL_PAGAR_FACTURA,\n       (CASE WHEN TRIM(TO_CHAR(NVL((SELECT SUM(B.FINA_OPEN_AMOUNT) \n                                    FROM FINANCING.FINANCING B \n                                    WHERE B.CUSTOMER_ID = A.CUSTOMER_ID), 0), '9999999.99'))  LIKE '.%' THEN '0'||TRIM(TO_CHAR(NVL((SELECT SUM(B.FINA_OPEN_AMOUNT) \n                                                                                                                                    FROM FINANCING.FINANCING B \n                                                                                                                                    WHERE B.CUSTOMER_ID = A.CUSTOMER_ID), 0), '9999999.99'))\n             ELSE TRIM(TO_CHAR(NVL((SELECT SUM(B.FINA_OPEN_AMOUNT) \n                                    FROM FINANCING.FINANCING B \n                                    WHERE B.CUSTOMER_ID = A.CUSTOMER_ID), 0), '9999999.99'))\n        END) CUOTA_MENSUAL_FINANCIAMIENTO,\n       (CASE WHEN TRIM(TO_CHAR(NVL((SELECT SUM(B.OHOPNAMT_GL) \n                                    FROM SYSADM.ORDERHDR_ALL B \n                                    WHERE B.OHINVTYPE = 5\n                                    AND B.OHSTATUS = 'IN'\n                                    AND B.CUSTOMER_ID = A.CUSTOMER_ID\n                                    AND B.OHDUEDATE < TRUNC(SYSDATE)), 0), '9999999.99'))  LIKE '.%' THEN '0'||TRIM(TO_CHAR(NVL((SELECT SUM(B.OHOPNAMT_GL) \n                                                                                                                                FROM SYSADM.ORDERHDR_ALL B \n                                                                                                                                WHERE B.OHINVTYPE = 5\n                                                                                                                                AND B.OHSTATUS = 'IN'\n                                                                                                                                AND B.CUSTOMER_ID = A.CUSTOMER_ID\n                                                                                                                                AND B.OHDUEDATE < TRUNC(SYSDATE)), 0), '9999999.99'))\n             ELSE TRIM(TO_CHAR(NVL((SELECT SUM(B.OHOPNAMT_GL) \n                                    FROM SYSADM.ORDERHDR_ALL B \n                                    WHERE B.OHINVTYPE = 5\n                                    AND B.OHSTATUS = 'IN'\n                                    AND B.CUSTOMER_ID = A.CUSTOMER_ID\n                                    AND B.OHDUEDATE < TRUNC(SYSDATE)), 0), '9999999.99'))\n        END) SALDO_PENDIENTE\nFROM SYSADM.ORDERHDR_ALL A\nWHERE A.OHINVTYPE = 5\nAND A.OHSTATUS = 'IN'\nAND A.CUSTOMER_ID = " + customer_id + "\n" + "GROUP BY A.CUSTOMER_ID \n" + "HAVING SUM(A.OHOPNAMT_GL) > 0";
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       
/* 740 */       this.con.Conectar();
/* 741 */       ResultSet consulta = this.con.Consultar(query1);
/* 742 */       if (consulta != null) {
/* 743 */         datos = new String[1][5];
/* 744 */         while (consulta.next()) {
/* 745 */           datos[0][0] = consulta.getString("CUSTOMER_ID");
/* 746 */           datos[0][1] = consulta.getString("FECHA_LIMITE_PAGO");
/* 747 */           if (consulta.getString("TOTAL_PAGAR_FACTURA").substring(0, 1).equals(".")) {
/* 748 */             datos[0][2] = "0" + consulta.getString("TOTAL_PAGAR_FACTURA");
           } else {
/* 750 */             datos[0][2] = consulta.getString("TOTAL_PAGAR_FACTURA");
           } 
/* 752 */           if (consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO").substring(0, 1).equals(".")) {
/* 753 */             datos[0][3] = "0" + consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO");
           } else {
/* 755 */             datos[0][3] = consulta.getString("CUOTA_MENSUAL_FINANCIAMIENTO");
           } 
/* 757 */           if (consulta.getString("SALDO_PENDIENTE").substring(0, 1).equals(".")) {
/* 758 */             datos[0][4] = "0" + consulta.getString("SALDO_PENDIENTE"); continue;
           } 
/* 760 */           datos[0][4] = consulta.getString("SALDO_PENDIENTE");
         } 
       } 
       
/* 764 */       if (datos == null || datos[0][0] == null) {
 
         
/* 767 */         String consultaExistenciaSQL = "SELECT 1\nFROM SYSADM.CUSTOMER_ALL\nWHERE CUSTOMER_ID = " + customer_id + "\n" + "AND ROWNUM = 1";
 
 
         
         try {
/* 772 */           ResultSet consultaExistencia = this.con.Consultar(consultaExistenciaSQL);
/* 773 */           if (consultaExistencia == null || !consultaExistencia.next()) {
/* 774 */             throw new Exception("Servicio no encontrado: " + customer_id);
           }
/* 776 */         } catch (Exception ex) {
/* 777 */           throw new Exception(ex.toString());
         } 
       } 
/* 780 */       this.con.CerrarConsulta();
       
/* 782 */       return datos;
     }
/* 784 */     catch (SQLException e) {
/* 785 */       this.con.CerrarConsulta();
/* 786 */       datos = new String[1][2];
/* 787 */       datos[0][0] = "ERROR";
/* 788 */       datos[0][1] = e.toString();
/* 789 */       return datos;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ConsultasBSCSCR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */