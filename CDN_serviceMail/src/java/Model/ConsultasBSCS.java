package Model;

import Controller.ConexionOracle;
import Controller.Helper;
import Model.Select;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONObject;









public class ConsultasBSCS
{
  int pantalla;
  int rol;
  int estado;
  int id;
  public ResultSet rs;
  public PreparedStatement ps;
  public JSONArray responseArray;
/*   32 */   public Helper hp = new Helper();
/*   33 */   public ConexionOracle con = new ConexionOracle();
/*   34 */   public ConexionOracle conO = new ConexionOracle();
  
  public ConsultasBSCS() {
/*   37 */     this.con.ip = "192.168.4.59";
/*   38 */     this.con.port = "1524";
/*   39 */     this.con.bd = "BSCS";
/*   40 */     this.con.usr = "CDN_USER_BSCS";
/*   41 */     this.con.pass = "appP_8eAa8sQ";
  }
  
  public ResultSet getMora(String id) throws SQLException, Exception {
/*   45 */     this.con.usr = "READ";
/*   46 */     this.con.pass = "READ";
/*   47 */     JSONObject jsonObj = new JSONObject();
/*   48 */     this.responseArray = new JSONArray();
    try {
/*   50 */       this.con.Conectar();

      
/*   53 */       String query = "SELECT VALOR_FINAL FROM FT_SERVICIO WHERE SERVICIO_CODIGO='IMORA' AND CLIENTE_CODIGO = '" + id + "'";
/*   54 */       this.rs = this.con.Consultar(query);
/*   55 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*   56 */       int columna = rsmd.getColumnCount();
      
/*   58 */       while (this.rs.next()) {
/*   59 */         JSONObject rows = new JSONObject();
/*   60 */         for (int i = 1; i <= columna; i++) {
/*   61 */           String columnaBD = rsmd.getColumnName(i);
/*   62 */           rows.put(columnaBD, this.rs.getString(columnaBD));
        } 
/*   64 */         this.responseArray.put(rows);
      } 
/*   66 */       this.hp.setArrayResponse(200, this.responseArray);
/*   67 */       this.con.CerrarConsulta();
/*   68 */     } catch (SQLException ex) {
/*   69 */       this.hp.setArrayResponse(400, this.responseArray);
/*   70 */       this.con.CerrarConsulta();
    } 
/*   72 */     return this.rs;
  }
  
  public ResultSet findNumberByDPI(String id) throws SQLException, Exception {
/*   76 */     JSONObject jsonObj = new JSONObject();
/*   77 */     this.responseArray = new JSONArray();
    try {
/*   79 */       this.con.Conectar();
/*   80 */       String query = "    SELECT A.DN_NUM TELEFONO_TITULAR       FROM SYSADM.DIRECTORY_NUMBER A      WHERE A.PLCODE = 1001        AND A.DN_ID = (SELECT /*+ INDEX_SS_DESC(B) */ B.DN_ID                     FROM SYSADM.CONTR_SERVICES_CAP B                    WHERE TRIM(B.CS_DEACTIV_DATE) IS NULL                      AND B.CO_ID = (SELECT /*+ INDEX_DESC(C) */ C.CO_ID                                       FROM SYSADM.CONTRACT_ALL C                                      WHERE C.PLCODE = 1001                                        AND C.CUSTOMER_ID = (SELECT /*+ PARALLEL(D, 2) */ D.CUSTOMER_ID                                                               FROM SYSADM.CUSTOMER_ALL D                                                              WHERE D.COSTCENTER_ID = 1                                                                AND D.PASSPORTNO = '" + id + "'" + "                                                            )))";














      
/*   96 */       this.rs = this.con.Consultar(query);
/*   97 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*   98 */       int columna = rsmd.getColumnCount();
      
/*  100 */       while (this.rs.next()) {
/*  101 */         JSONObject rows = new JSONObject();
/*  102 */         for (int i = 1; i <= columna; i++) {
/*  103 */           String columnaBD = rsmd.getColumnName(i);
/*  104 */           rows.put(columnaBD, this.rs.getString(columnaBD));
        } 
/*  106 */         this.responseArray.put(rows);
      } 
/*  108 */       this.hp.setArrayResponse(200, this.responseArray);
/*  109 */       this.con.CerrarConsulta();
/*  110 */     } catch (SQLException ex) {
/*  111 */       this.hp.setArrayResponse(400, this.responseArray);
/*  112 */       this.con.CerrarConsulta();
    } 
/*  114 */     return this.rs;
  }
  
  public ResultSet findFinancing(String id) throws SQLException, Exception {
/*  118 */     JSONObject jsonObj = new JSONObject();
/*  119 */     this.responseArray = new JSONArray();
    try {
/*  121 */       this.con.Conectar();

      
/*  124 */       String query = "SELECT  nvl(ROUND(ROUND(SUM((FINA_AMOUNT / FINA_QUOTA)),4),2),0.00) AS TOTAL FROM FINANCING WHERE \nCUSTOMER_ID = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";

      
/*  127 */       this.rs = this.con.Consultar(query);
/*  128 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  129 */       int columna = rsmd.getColumnCount();
      
/*  131 */       while (this.rs.next()) {
/*  132 */         JSONObject rows = new JSONObject();
/*  133 */         for (int i = 1; i <= columna; i++) {
/*  134 */           String columnaBD = rsmd.getColumnName(i);
/*  135 */           rows.put(columnaBD, this.rs.getString(columnaBD));
        } 
/*  137 */         this.responseArray.put(rows);
      } 
/*  139 */       this.hp.setArrayResponse(200, this.responseArray);
/*  140 */       this.con.CerrarConsulta();
/*  141 */     } catch (SQLException ex) {
/*  142 */       this.hp.setArrayResponse(400, this.responseArray);
/*  143 */       this.con.CerrarConsulta();
    } 
/*  145 */     return this.rs;
  }
  
  public ResultSet findSaldoVencido(String id) throws SQLException, Exception {
/*  149 */     JSONObject jsonObj = new JSONObject();
/*  150 */     this.responseArray = new JSONArray();
    try {
/*  152 */       this.con.Conectar();
/*  153 */       String query = "select \nCA.customer_id,\ndn.dn_num telefono,\nnvl((select sum(oa.ohopnamt_doc) \n    from ORDERHDR_ALL oa where oa.customer_id=ca.CUSTOMER_ID and oa.ohstatus='IN' and trunc(oa.OHDUEDATE)<trunc(sysdate) group by oa.customer_id),0) saldo_vencido, \nnvl((\n    select sum(oa.ohopnamt_doc) \n    from ORDERHDR_ALL oa \n    where oa.customer_id=ca.CUSTOMER_ID and oa.ohstatus='IN' group by oa.customer_id),0) saldo_total,nvl((select to_char(max(oa.OHDUEDATE),'dd/mm/yyyy') \n    from ORDERHDR_ALL oa where oa.customer_id=ca.CUSTOMER_ID and oa.ohstatus='IN'),CA.co_expir_date) fecha_vencimiento, nvl((select count(case oa1.ohopnamt_doc when 0 then null else oa1.ohopnamt_doc end ) from ORDERHDR_ALL oa1 where oa1.customer_id=ca.CUSTOMER_ID and oa1.ohstatus='IN' and trunc(oa1.OHDUEDATE)<trunc(sysdate) group by oa1.customer_id),0) mora,c_all.custcode from directory_number dn \nleft join contr_services_cap csc on csc.dn_id=dn.dn_id \nleft join contract_all ca        on ca.co_id=csc.co_id \nleft join contract_history ch    on ch.co_id=ca.co_id \nleft join customer_all c_all        on c_all.customer_id = CA.customer_id where\ncsc.seqno = (select max(csc1.seqno) from contr_services_cap csc1 where CSC1.co_id=csc.co_id) AND ch.ch_seqno = (SELECT MAX (ch1.ch_seqno)FROM contract_history ch1 WHERE ch1.co_id = ch.co_id) \nand ch.ch_status in ('a','s') \nand dn.dn_num IN( '" + id + "')";





















      
/*  176 */       this.rs = this.con.Consultar(query);
/*  177 */       ResultSetMetaData rsmd = this.rs.getMetaData();
/*  178 */       int columna = rsmd.getColumnCount();
      
/*  180 */       while (this.rs.next()) {
/*  181 */         JSONObject rows = new JSONObject();
/*  182 */         for (int i = 1; i <= columna; i++) {
/*  183 */           String columnaBD = rsmd.getColumnName(i);
/*  184 */           rows.put(columnaBD, this.rs.getString(columnaBD));
        } 
/*  186 */         this.responseArray.put(rows);
      } 
/*  188 */       this.hp.setArrayResponse(200, this.responseArray);
/*  189 */       this.con.CerrarConsulta();
/*  190 */     } catch (SQLException ex) {
/*  191 */       this.hp.setArrayResponse(400, this.responseArray);
/*  192 */       this.con.CerrarConsulta();
    } 
/*  194 */     return this.rs;
  }
  
  public String findSaldoByNumber(String number) throws SQLException, Exception {
/*  198 */     JSONObject jsonObj = new JSONObject();
    try {
/*  200 */       this.con.Conectar();
      
/*  202 */       CallableStatement cs = null;
/*  203 */       cs = this.con.conexion.prepareCall("{? = call interfaz.wa_pkg_cdn_reg.fn_info_ohref (?)}");
/*  204 */       cs.registerOutParameter(1, 12);
/*  205 */       cs.setString(2, number);
/*  206 */       cs.execute();
      
/*  208 */       String retorno = cs.getString(1);
      
/*  210 */       jsonObj.put("Text", retorno);
/*  211 */       this.hp.setResponse(200, jsonObj);
/*  212 */       this.con.CerrarConsulta();
/*  213 */     } catch (SQLException ex) {
/*  214 */       this.hp.setResponse(400, jsonObj);
/*  215 */       this.con.CerrarConsulta();
    } 
/*  217 */     return this.hp.getResponse();
  }
  
  public ResultSet llamadasBSCS(String id) throws SQLException, Exception {
/*  221 */     String dato = "";
/*  222 */     String equipos = "";
    try {
/*  224 */       this.con.Conectar();
/*  225 */       String query = "SELECT  COUNT(G.DES) AS SERVICIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND G.DES like '%llamada%'                    \n    AND I.DN_NUM = '" + id + "'";

































      
/*  260 */       ResultSet resultado = this.con.Consultar(query);
/*  261 */       while (resultado.next()) {
/*  262 */         dato = resultado.getString("SERVICIO");
      }
/*  264 */       if (dato.isEmpty()) {
/*  265 */         equipos = "0";
      } else {
/*  267 */         equipos = dato;
      } 
/*  269 */       String update = "UPDATE CDN_MAIL SET LLAMADA =" + equipos + " WHERE ID=" + id;
/*  270 */       this.con.CerrarConsulta();
/*  271 */       this.conO.Conectar();
/*  272 */       this.conO.Consultar(update);
/*  273 */       this.conO.CerrarConsulta();
/*  274 */       return this.rs;
/*  275 */     } catch (SQLException e) {
/*  276 */       this.con.CerrarConsulta();
/*  277 */       return null;
    } 
  }
  
  public ResultSet smsBSCS(String id) throws SQLException, Exception {
/*  282 */     String dato = "";
/*  283 */     String equipos = "";
    try {
/*  285 */       this.con.Conectar();
/*  286 */       String query = "SELECT  COUNT(G.DES) AS SERVICIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND G.DES like '%SMS%'                    \n    AND I.DN_NUM = '" + id + "'";

































      
/*  321 */       ResultSet resultado = this.con.Consultar(query);
/*  322 */       while (resultado.next()) {
/*  323 */         dato = resultado.getString("SERVICIO");
      }
/*  325 */       if (dato.isEmpty()) {
/*  326 */         equipos = "0";
      } else {
/*  328 */         equipos = dato;
      } 
/*  330 */       String update = "UPDATE CDN_MAIL SET SMS =" + equipos + " WHERE ID=" + id;
/*  331 */       this.con.CerrarConsulta();
/*  332 */       this.conO.Conectar();
/*  333 */       this.conO.Consultar(update);
/*  334 */       this.conO.CerrarConsulta();
/*  335 */       return this.rs;
/*  336 */     } catch (SQLException e) {
/*  337 */       this.con.CerrarConsulta();
/*  338 */       return null;
    } 
  }
  
  public ResultSet musicaBSCS(String id) throws SQLException, Exception {
/*  343 */     String dato = "";
/*  344 */     String equipos = "";
    try {
/*  346 */       this.con.Conectar();
/*  347 */       String query = "SELECT  COUNT(G.DES) AS SERVICIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND G.DES like '%MUSICA%'                    \n    AND I.DN_NUM = '" + id + "'";

































      
/*  382 */       ResultSet resultado = this.con.Consultar(query);
/*  383 */       while (resultado.next()) {
/*  384 */         dato = resultado.getString("SERVICIO");
      }
/*  386 */       if (dato.isEmpty()) {
/*  387 */         equipos = "0";
      } else {
/*  389 */         equipos = dato;
      } 
/*  391 */       String update = "UPDATE CDN_MAIL SET CLAROMUSICA =" + equipos + " WHERE ID=" + id;
/*  392 */       this.con.CerrarConsulta();
/*  393 */       this.conO.Conectar();
/*  394 */       this.conO.Consultar(update);
/*  395 */       this.conO.CerrarConsulta();
/*  396 */       return this.rs;
/*  397 */     } catch (SQLException e) {
/*  398 */       this.con.CerrarConsulta();
/*  399 */       return null;
    } 
  }
  
  public ResultSet nombrePlan(String id) throws SQLException, Exception {
/*  404 */     String dato = "";
/*  405 */     String equipos = "";
    try {
/*  407 */       this.con.Conectar();
/*  408 */       String query = "SELECT E.DES SERVICIO\nFROM SYSADM.PROFILE_SERVICE A,\n     SYSADM.PR_SERV_STATUS_HIST B,\n     SYSADM.PR_SERV_SPCODE_HIST C,\n     SYSADM.RATEPLAN_HIST D,\n     SYSADM.RATEPLAN E,\n     SYSADM.MPUSPTAB F,\n     SYSADM.MPUSNTAB G,\n     SYSADM.MPULKTMB H,\n     SYSADM.DIRECTORY_NUMBER I,\n     SYSADM.CONTR_SERVICES_CAP J\nWHERE J.DN_ID = I.DN_ID\nAND TRIM(CS_DEACTIV_DATE) IS NULL\nAND A.CO_ID = J.CO_ID\nAND B.CO_ID = A.CO_ID \nAND B.HISTNO = A.STATUS_HISTNO\nAND B.STATUS = 'A'\nAND C.CO_ID = A.CO_ID\nAND C.HISTNO = A.SPCODE_HISTNO\nAND D.CO_ID = A.CO_ID\nAND E.TMCODE = D.TMCODE\nAND H.TMCODE = D.TMCODE\nAND H.SPCODE = C.SPCODE\nAND H.SNCODE = A.SNCODE\nAND F.SPCODE = C.SPCODE\nAND G.SNCODE = A.SNCODE\nAND D.SEQNO = (SELECT MAX(K.SEQNO)\n               FROM SYSADM.RATEPLAN_HIST K\n               WHERE K.CO_ID = A.CO_ID)\nAND H.VSCODE = (SELECT MAX(L.VSCODE)\n                FROM SYSADM.MPULKTMB L\n                WHERE L.TMCODE = D.TMCODE\n                AND L.VSDATE <= D.TMCODE_DATE)\nAND I.DN_NUM = '" + id + "'\n" + "GROUP BY E.DES                    ";

































      
/*  443 */       ResultSet resultado = this.con.Consultar(query);
/*  444 */       while (resultado.next()) {
/*  445 */         dato = resultado.getString("SERVICIO");
      }
/*  447 */       if (dato.isEmpty()) {
/*  448 */         equipos = "0";
      } else {
/*  450 */         equipos = dato;
      } 
/*  452 */       String update = "UPDATE CDN_MAIL SET SERVICIO = '" + equipos + "' WHERE ID=" + id;
/*  453 */       this.con.CerrarConsulta();
/*  454 */       this.conO.Conectar();
/*  455 */       this.conO.Consultar(update);
/*  456 */       this.conO.CerrarConsulta();
/*  457 */       return this.rs;
/*  458 */     } catch (SQLException e) {
/*  459 */       this.con.CerrarConsulta();
/*  460 */       return null;
    } 
  }
  
  public ResultSet sinfronteraBSCS(String id) throws SQLException, Exception {
/*  465 */     String dato = "";
/*  466 */     String equipos = "";
    try {
/*  468 */       this.con.Conectar();
/*  469 */       String query = "SELECT  COUNT(F.DES) AS SERVICIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND F.DES like '%SIN FRONTER%'                    \n    AND I.DN_NUM = '" + id + "'";

































      
/*  504 */       ResultSet resultado = this.con.Consultar(query);
/*  505 */       while (resultado.next()) {
/*  506 */         dato = resultado.getString("SERVICIO");
      }
/*  508 */       if (dato.isEmpty()) {
/*  509 */         equipos = "0";
      } else {
/*  511 */         equipos = dato;
      } 
/*  513 */       String update = "UPDATE CDN_MAIL SET SINFRONTERA =" + equipos + " WHERE ID=" + id;
/*  514 */       this.con.CerrarConsulta();
/*  515 */       this.conO.Conectar();
/*  516 */       this.conO.Consultar(update);
/*  517 */       this.conO.CerrarConsulta();
/*  518 */       return this.rs;
/*  519 */     } catch (SQLException e) {
/*  520 */       this.con.CerrarConsulta();
/*  521 */       return null;
    } 
  }
  
  public ResultSet internetBSCS(String id) throws SQLException, Exception {
/*  526 */     String dato = "";
/*  527 */     String equipos = "";
    try {
/*  529 */       this.con.Conectar();
/*  530 */       String query = "SELECT  COUNT(F.DES) AS SERVICIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND F.DES like '%Internet%'                    \n    AND I.DN_NUM = '" + id + "'";

































      
/*  565 */       ResultSet resultado = this.con.Consultar(query);
/*  566 */       while (resultado.next()) {
/*  567 */         dato = resultado.getString("SERVICIO");
      }
/*  569 */       if (dato.isEmpty()) {
/*  570 */         equipos = "0";
      } else {
/*  572 */         equipos = dato;
      } 
/*  574 */       String update = "UPDATE CDN_MAIL SET INTERNET =1 WHERE ID=" + id;
/*  575 */       this.con.CerrarConsulta();
/*  576 */       this.conO.Conectar();
/*  577 */       this.conO.Consultar(update);
/*  578 */       this.conO.CerrarConsulta();
/*  579 */       return this.rs;
/*  580 */     } catch (SQLException e) {
/*  581 */       this.con.CerrarConsulta();
/*  582 */       return null;
    } 
  }
  
  public ResultSet correoclienteBSCS(String id) throws SQLException, Exception {
/*  587 */     String dato = "";
/*  588 */     String equipos = "";
/*  589 */     String datos = "";
/*  590 */     String equiposs = "";
/*  591 */     String nombre = "";
/*  592 */     String nombres = "";
    try {
/*  594 */       this.conO.Conectar();
/*  595 */       String consulta = "SELECT * FROM CDN_MAIL WHERE ID=" + id + "";
/*  596 */       ResultSet result = this.conO.Consultar(consulta);
/*  597 */       while (result.next()) {
/*  598 */         dato = result.getString("CUSTCODE");
      }
/*  600 */       if (dato.isEmpty()) {
/*  601 */         equipos = "0";
      } else {
/*  603 */         equipos = dato;
      } 
/*  605 */       this.con.Conectar();
/*  606 */       String query = "SELECT B.CCEMAIL AS EMAIL,(B.CCFNAME || ' ' ||B.CCLNAME) AS NAME \nFROM CUSTOMER_ALL A\nINNER JOIN CCONTACT_ALL B ON A.CUSTOMER_ID = B.CUSTOMER_ID\nWHERE B.CCBILL='X' \nAND B.CCEMAIL IS NOT NULL\nAND A.CUSTCODE = '" + equipos + "'";




      
/*  612 */       ResultSet resultado = this.con.Consultar(query);
/*  613 */       while (resultado.next()) {
/*  614 */         datos = resultado.getString("EMAIL");
/*  615 */         nombre = resultado.getString("NAME");
      } 
/*  617 */       if (datos.isEmpty()) {
/*  618 */         equiposs = "0";
      } else {
/*  620 */         equiposs = datos;
      } 
      
/*  623 */       if (nombre.isEmpty()) {
/*  624 */         nombres = "0";
      } else {
/*  626 */         nombres = nombre;
      } 
/*  628 */       this.conO.CerrarConsulta();
      
/*  630 */       this.conO.Conectar();
      
/*  632 */       String update = "UPDATE CDN_MAIL SET CORREO_CLIENTE ='" + equiposs + "',NOMBRE_CLIENTE='" + nombres + "' WHERE ID=" + id;
      
/*  634 */       this.con.CerrarConsulta();
      
/*  636 */       this.conO.Consultar(update);
/*  637 */       this.conO.CerrarConsulta();
/*  638 */       return this.rs;
/*  639 */     } catch (SQLException e) {
/*  640 */       this.con.CerrarConsulta();
/*  641 */       return null;
    } 
  }
  
  public ResultSet montoBSCSGT(String id) throws SQLException, Exception {
/*  646 */     String dato = "";
/*  647 */     String equipos = "";
/*  648 */     String datos = "";
/*  649 */     String equiposs = "";
/*  650 */     String datoss = "";
/*  651 */     String equiposss = "";
    
    try {
/*  654 */       this.con.Conectar();
/*  655 */       String query = "SELECT  SUM(H.ACCESSFEE) AS PRECIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND I.DN_NUM = '" + id + "'";


































      
/*  691 */       Select sel = new Select();
      
/*  693 */       String tmcode = sel.getTMCODE(id);
      
/*  695 */       if (tmcode.equals("6819") || tmcode.equals("6820")) {
/*  696 */         query = query + " AND A.SNCODE NOT IN(717)";
      }

      
/*  700 */       ResultSet resultado = this.con.Consultar(query);
/*  701 */       while (resultado.next()) {
/*  702 */         dato = resultado.getString("PRECIO");
      }
/*  704 */       if (dato.isEmpty()) {
/*  705 */         equipos = "0";
      } else {
/*  707 */         equipos = dato;
      } 
      
/*  710 */       String consulta = "SELECT NVL(ROUND(SUM((TOTAL_AMOUNT-COUPLING_AMOUNT ) / FINA_QUOTA),3),0) AS TOTAL FROM FINANCING WHERE DN_NUM = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";
/*  711 */       ResultSet result = this.con.Consultar(consulta);
/*  712 */       while (result.next()) {
/*  713 */         datos = result.getString("TOTAL");
      }
/*  715 */       if (datos.isEmpty()) {
/*  716 */         equiposs = "0";
      } else {
/*  718 */         equiposs = datos;
      } 
/*  720 */       float x = Float.parseFloat(equipos);
/*  721 */       float x1 = Float.parseFloat(equiposs);
/*  722 */       float suma = x + x1;
      
/*  724 */       DecimalFormat formato1 = new DecimalFormat("#.00");
/*  725 */       String suma1 = formato1.format(suma);
      
/*  727 */       this.conO.Conectar();
/*  728 */       String moneda = "SELECT B.MONEDA as MONEDA FROM CDN_MAIL A\nINNER JOIN CDN_PAIS B ON B.CODIGO = A.PAIS_ID\nWHERE A.ID='" + id + "'";

      
/*  731 */       ResultSet resul = this.conO.Consultar(moneda);
/*  732 */       while (resul.next()) {
/*  733 */         datoss = resul.getString("MONEDA");
      }
/*  735 */       if (datos.isEmpty()) {
/*  736 */         equiposss = "0";
      } else {
/*  738 */         equiposss = datoss;
      } 
      
/*  741 */       String monto = equiposss + suma1;
      
/*  743 */       String update = "UPDATE CDN_MAIL SET CUOTA ='" + monto + "' WHERE ID=" + id;
/*  744 */       this.con.CerrarConsulta();
/*  745 */       this.conO.Consultar(update);
/*  746 */       this.conO.CerrarConsulta();
/*  747 */       return this.rs;
/*  748 */     } catch (SQLException e) {
/*  749 */       this.con.CerrarConsulta();
/*  750 */       return null;
    } 
  }
  
  public ResultSet montoBSCSNI(String id) throws SQLException, Exception {
/*  755 */     String dato = "";
/*  756 */     String equipos = "";
/*  757 */     String datos = "";
/*  758 */     String equiposs = "";
/*  759 */     String datoss = "";
/*  760 */     String equiposss = "";
    try {
/*  762 */       this.con.Conectar();
/*  763 */       String query = "SELECT  SUM(H.ACCESSFEE) AS PRECIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n                    AND A.SNCODE NOT IN(2031,1614,1615)\n    AND I.DN_NUM = '" + id + "'";

































      
/*  798 */       ResultSet resultado = this.con.Consultar(query);
/*  799 */       while (resultado.next()) {
/*  800 */         dato = resultado.getString("PRECIO");
      }
/*  802 */       if (dato.isEmpty()) {
/*  803 */         equipos = "0";
      } else {
/*  805 */         equipos = dato;
      } 
      
/*  808 */       String consulta = "SELECT NVL(ROUND(SUM((TOTAL_AMOUNT-COUPLING_AMOUNT ) / FINA_QUOTA),3),0) AS TOTAL FROM FINANCING WHERE DN_NUM = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";
/*  809 */       ResultSet result = this.con.Consultar(consulta);
/*  810 */       while (result.next()) {
/*  811 */         datos = result.getString("TOTAL");
      }
/*  813 */       if (datos.isEmpty()) {
/*  814 */         equiposs = "0";
      } else {
/*  816 */         equiposs = datos;
      } 
/*  818 */       Double x = Double.valueOf(Double.parseDouble(equipos));
/*  819 */       Double x1 = Double.valueOf(Double.parseDouble(equiposs));
      
/*  821 */       Double suma = Double.valueOf(x.doubleValue() * 0.15D + x.doubleValue() + x1.doubleValue());
/*  822 */       DecimalFormat formato1 = new DecimalFormat("#.00");
/*  823 */       String suma1 = formato1.format(suma);
      
/*  825 */       this.conO.Conectar();
/*  826 */       String moneda = "SELECT B.MONEDA as MONEDA FROM CDN_MAIL A\nINNER JOIN CDN_PAIS B ON B.CODIGO = A.PAIS_ID\nWHERE A.ID='" + id + "'";

      
/*  829 */       ResultSet resul = this.conO.Consultar(moneda);
/*  830 */       while (resul.next()) {
/*  831 */         datoss = resul.getString("MONEDA");
      }
/*  833 */       if (datos.isEmpty()) {
/*  834 */         equiposss = "0";
      } else {
/*  836 */         equiposss = datoss;
      } 
      
/*  839 */       String monto = equiposss + suma1;
      
/*  841 */       String update = "UPDATE CDN_MAIL SET CUOTA ='" + monto + "' WHERE ID=" + id;
/*  842 */       this.con.CerrarConsulta();
/*  843 */       this.conO.Consultar(update);
/*  844 */       this.conO.CerrarConsulta();
/*  845 */       return this.rs;
/*  846 */     } catch (SQLException e) {
/*  847 */       this.con.CerrarConsulta();
/*  848 */       return null;
    } 
  }
  
  public ResultSet montoBSCSSV(String id) throws SQLException, Exception {
/*  853 */     String dato = "";
/*  854 */     String equipos = "";
/*  855 */     String datos = "";
/*  856 */     String equiposs = "";
/*  857 */     String datoss = "";
/*  858 */     String equiposss = "";
    try {
/*  860 */       this.con.Conectar();
/*  861 */       String query = "SELECT  SUM(H.ACCESSFEE) AS PRECIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n    AND I.DN_NUM = '" + id + "'";
































      
/*  895 */       ResultSet resultado = this.con.Consultar(query);
/*  896 */       while (resultado.next()) {
/*  897 */         dato = resultado.getString("PRECIO");
      }
/*  899 */       if (dato.isEmpty()) {
/*  900 */         equipos = "0";
      } else {
/*  902 */         equipos = dato;
      } 
      
/*  905 */       String consulta = "SELECT NVL(ROUND(SUM((TOTAL_AMOUNT-COUPLING_AMOUNT ) / FINA_QUOTA),3),0) AS TOTAL FROM FINANCING WHERE DN_NUM = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";
/*  906 */       ResultSet result = this.con.Consultar(consulta);
/*  907 */       while (result.next()) {
/*  908 */         datos = result.getString("TOTAL");
      }
/*  910 */       if (datos.isEmpty()) {
/*  911 */         equiposs = "0";
      } else {
/*  913 */         equiposs = datos;
      } 
/*  915 */       Double x = Double.valueOf(Double.parseDouble(equipos));
/*  916 */       Double x1 = Double.valueOf(Double.parseDouble(equiposs));
      
/*  918 */       Double suma = Double.valueOf(x.doubleValue() + x.doubleValue() / 1.13D * 0.05D + x1.doubleValue());
/*  919 */       DecimalFormat formato1 = new DecimalFormat("#.00");
/*  920 */       String suma1 = formato1.format(suma);
      
/*  922 */       this.conO.Conectar();
/*  923 */       String moneda = "SELECT B.MONEDA as MONEDA FROM CDN_MAIL A\nINNER JOIN CDN_PAIS B ON B.CODIGO = A.PAIS_ID\nWHERE A.ID='" + id + "'";

      
/*  926 */       ResultSet resul = this.conO.Consultar(moneda);
/*  927 */       while (resul.next()) {
/*  928 */         datoss = resul.getString("MONEDA");
      }
/*  930 */       if (datos.isEmpty()) {
/*  931 */         equiposss = "0";
      } else {
/*  933 */         equiposss = datoss;
      } 
      
/*  936 */       String monto = equiposss + suma1;
      
/*  938 */       String update = "UPDATE CDN_MAIL SET CUOTA ='" + monto + "' WHERE ID=" + id;
/*  939 */       this.con.CerrarConsulta();
/*  940 */       this.conO.Consultar(update);
/*  941 */       this.conO.CerrarConsulta();
/*  942 */       return this.rs;
/*  943 */     } catch (SQLException e) {
/*  944 */       this.con.CerrarConsulta();
/*  945 */       return null;
    } 
  }
  
  public ResultSet montoBSCSHN(String id) throws SQLException, Exception {
/*  950 */     String dato = "";
/*  951 */     String equipos = "";
/*  952 */     String datos = "";
/*  953 */     String equiposs = "";
/*  954 */     String datoss = "";
/*  955 */     String equiposss = "";
    try {
/*  957 */       this.con.Conectar();
/*  958 */       String query = "SELECT  SUM(H.ACCESSFEE) AS PRECIO\nFROM SYSADM.PROFILE_SERVICE A,\n         SYSADM.PR_SERV_STATUS_HIST B,\n         SYSADM.PR_SERV_SPCODE_HIST C,\n         SYSADM.RATEPLAN_HIST D,\n         SYSADM.RATEPLAN E,\n         SYSADM.MPUSPTAB F,\n         SYSADM.MPUSNTAB G,\n         SYSADM.MPULKTMB H,\n         SYSADM.DIRECTORY_NUMBER I,\n         SYSADM.CONTR_SERVICES_CAP J\n    WHERE J.DN_ID = I.DN_ID\n    AND TRIM(CS_DEACTIV_DATE) IS NULL\n    AND A.CO_ID = J.CO_ID\n    AND B.CO_ID = A.CO_ID \n    AND B.HISTNO = A.STATUS_HISTNO\n    AND B.STATUS = 'A'\n    AND C.CO_ID = A.CO_ID\n    AND C.HISTNO = A.SPCODE_HISTNO\n    AND D.CO_ID = A.CO_ID\n    AND E.TMCODE = D.TMCODE\n    AND H.TMCODE = D.TMCODE\n    AND H.SPCODE = C.SPCODE\n    AND H.SNCODE = A.SNCODE\n    AND F.SPCODE = C.SPCODE \n    AND G.SNCODE = A.SNCODE\n    AND D.SEQNO = (SELECT MAX(K.SEQNO)\n                   FROM SYSADM.RATEPLAN_HIST K\n                   WHERE K.CO_ID = A.CO_ID)\n    AND H.VSCODE = (SELECT MAX(L.VSCODE)\n                    FROM SYSADM.MPULKTMB L\n                    WHERE L.TMCODE = D.TMCODE\n                    AND L.VSDATE <= D.TMCODE_DATE)\n                    AND A.SNCODE NOT IN(2031,1614,1615)\n    AND I.DN_NUM = '" + id + "'";

































      
/*  993 */       ResultSet resultado = this.con.Consultar(query);
/*  994 */       while (resultado.next()) {
/*  995 */         dato = resultado.getString("PRECIO");
      }
/*  997 */       if (dato.isEmpty()) {
/*  998 */         equipos = "0";
      } else {
/* 1000 */         equipos = dato;
      } 
      
/* 1003 */       String consulta = "SELECT NVL(ROUND(SUM((TOTAL_AMOUNT-COUPLING_AMOUNT ) / FINA_QUOTA),3),0) AS TOTAL FROM FINANCING WHERE DN_NUM = '" + id + "' AND FINA_OPEN_QUOTA <> 0 ";
/* 1004 */       ResultSet result = this.con.Consultar(consulta);
/* 1005 */       while (result.next()) {
/* 1006 */         datos = result.getString("TOTAL");
      }
/* 1008 */       if (datos.isEmpty()) {
/* 1009 */         equiposs = "0";
      } else {
/* 1011 */         equiposs = datos;
      } 
/* 1013 */       Double x = Double.valueOf(Double.parseDouble(equipos));
/* 1014 */       Double x1 = Double.valueOf(Double.parseDouble(equiposs));
      
/* 1016 */       Double suma = Double.valueOf(x.doubleValue() * 0.15D + x.doubleValue() + x1.doubleValue());
/* 1017 */       DecimalFormat formato1 = new DecimalFormat("#.00");
/* 1018 */       String suma1 = formato1.format(suma);
      
/* 1020 */       this.conO.Conectar();
/* 1021 */       String moneda = "SELECT B.MONEDA as MONEDA FROM CDN_MAIL A\nINNER JOIN CDN_PAIS B ON B.CODIGO = A.PAIS_ID\nWHERE A.ID='" + id + "'";

      
/* 1024 */       ResultSet resul = this.conO.Consultar(moneda);
/* 1025 */       while (resul.next()) {
/* 1026 */         datoss = resul.getString("MONEDA");
      }
/* 1028 */       if (datos.isEmpty()) {
/* 1029 */         equiposss = "0";
      } else {
/* 1031 */         equiposss = datoss;
      } 
      
/* 1034 */       String monto = equiposss + suma1;
      
/* 1036 */       String update = "UPDATE CDN_MAIL SET CUOTA ='" + monto + "' WHERE ID=" + id;
/* 1037 */       this.con.CerrarConsulta();
/* 1038 */       this.conO.Consultar(update);
/* 1039 */       this.conO.CerrarConsulta();
/* 1040 */       return this.rs;
/* 1041 */     } catch (SQLException e) {
/* 1042 */       this.con.CerrarConsulta();
/* 1043 */       return null;
    } 
  }
  
  public ResultSet fechaPagoSV(String id) throws SQLException, Exception {
/* 1048 */     String dato = "";
/* 1049 */     String equipos = "";
/* 1050 */     String ciclo = "";
/* 1051 */     String ciclos = "";
/* 1052 */     String fecha = "";
/* 1053 */     String fechas = "";
    try {
/* 1055 */       this.conO.Conectar();
/* 1056 */       String consulta = "SELECT * FROM CDN_MAIL WHERE ID=" + id + "";
/* 1057 */       ResultSet result = this.conO.Consultar(consulta);
/* 1058 */       while (result.next()) {
/* 1059 */         dato = result.getString("CUSTCODE");
/* 1060 */         ciclo = result.getString("CICLO_FACT");
      } 
/* 1062 */       if (dato.isEmpty()) {
/* 1063 */         equipos = "0";
      } else {
/* 1065 */         equipos = dato;
      } 
/* 1067 */       if (ciclo.isEmpty()) {
/* 1068 */         ciclos = "0";
      } else {
/* 1070 */         ciclos = ciclo;
      } 
      
/* 1073 */       this.con.Conectar();
/* 1074 */       String query = "SELECT *\nFROM\n  (SELECT (CASE\n               WHEN SUBSTR(B.CCLNAME, 0, 1) BETWEEN 'A' AND 'F' THEN TO_CHAR(TO_DATE(C.FECHA_01, 'DD/MM/YY'))\n               WHEN SUBSTR(B.CCLNAME, 0, 1) BETWEEN 'G' AND 'M' THEN TO_CHAR(TO_DATE(C.FECHA_02, 'DD/MM/YY'))\n               WHEN SUBSTR(B.CCLNAME, 0, 1) BETWEEN 'N' AND 'Z' THEN TO_CHAR(TO_DATE(C.FECHA_03, 'DD/MM/YY'))\n           END) AS FECHA_PAGO\n   FROM CUSTOMER_ALL A\n   CROSS JOIN WAFM_FECHA_VENCIMIENTO_FACTURA C\n   INNER JOIN CCONTACT_ALL B ON A.CUSTOMER_ID = B.CUSTOMER_ID\n   WHERE B.CCBILL='X'\n     AND B.CCEMAIL IS NOT NULL\n     AND C.CICLO_FACTURACION = " + ciclos + "\n" + "     AND A.CUSTCODE = '" + equipos + "'\n" + "   ORDER BY C.FECHA_02 DESC)\n" + "WHERE ROWNUM < 2";














      
/* 1090 */       ResultSet resultado = this.con.Consultar(query);
/* 1091 */       while (resultado.next()) {
/* 1092 */         fecha = resultado.getString("FECHA_PAGO");
      }
/* 1094 */       if (fecha.isEmpty()) {
/* 1095 */         fechas = "0";
      } else {
/* 1097 */         fechas = fecha;
      } 
/* 1099 */       this.conO.CerrarConsulta();
/* 1100 */       this.conO.Conectar();
      
/* 1102 */       String update = "UPDATE CDN_MAIL SET FECHA_PAGO ='" + fechas + "' WHERE ID=" + id;
      
/* 1104 */       this.con.CerrarConsulta();
      
/* 1106 */       this.conO.Consultar(update);
/* 1107 */       this.conO.CerrarConsulta();
/* 1108 */       return this.rs;
/* 1109 */     } catch (SQLException e) {
/* 1110 */       this.con.CerrarConsulta();
/* 1111 */       return null;
    } 
  }
}


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ConsultasBSCS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */