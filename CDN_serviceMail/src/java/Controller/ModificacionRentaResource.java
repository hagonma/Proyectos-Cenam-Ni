/*      */ package Controller;
/*      */ 
/*      */ import Controller.ConexionOPEN;
/*      */ import Controller.ConexionOracle;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import javax.ws.rs.Consumes;
/*      */ import javax.ws.rs.GET;
/*      */ import javax.ws.rs.PUT;
/*      */ import javax.ws.rs.Path;
/*      */ import javax.ws.rs.Produces;
/*      */ import javax.ws.rs.QueryParam;
/*      */ import javax.ws.rs.core.Context;
/*      */ import javax.ws.rs.core.UriInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Path("ModificacionRenta")
/*      */ public class ModificacionRentaResource
/*      */ {
/*      */   @Context
/*      */   private UriInfo context;
/*      */   
/*      */   public ModificacionRentaResource() {
/*   36 */     System.out.println("Web service invocado");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GET
/*      */   @Produces({"application/json"})
/*      */   public String getJson(@QueryParam("codigoPais") int codigoPais, @QueryParam("tipoServicio") int tipoServicio, @QueryParam("tipoCambio") int tipoCambio, @QueryParam("tipoMovimiento") int tipoMovimiento) {
/*   50 */     int resultado = 0;
/*      */     
/*   52 */     if (codigoPais == 502) {
/*   53 */       if (tipoServicio == 1) {
/*   54 */         if (tipoCambio == 1) {
/*   55 */           if (tipoMovimiento != 1)
/*      */           {
/*   57 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*   60 */         else if (tipoCambio == 2) {
/*   61 */           if (tipoMovimiento == 1) {
/*      */             
/*   63 */             ConexionOracle conBSCS = new ConexionOracle();
/*      */             
/*   65 */             conBSCS.ip = "192.168.4.59";
/*   66 */             conBSCS.port = "1524";
/*   67 */             conBSCS.bd = "BSCS";
/*   68 */             conBSCS.usr = "CDN_USER_BSCS";
/*   69 */             conBSCS.pass = "appP_8eAa8sQ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*   77 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*   84 */               String mensaje_parametrizado = "";
/*   85 */               conCDNREG.Conectar();
/*   86 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_GT_A_CAM_PLA_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*   92 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/*   93 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/*   96 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/*   97 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/*   99 */               conCDNREG.CerrarConsulta();
/*  100 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/*  101 */                 conBSCS.Conectar();
/*  102 */                 ResultSet info1 = conBSCS.Consultar("SELECT A.CO_ID,\n       B.PLCODE,\n       B.CUSTOMER_ID\nFROM SYSADM.RATEPLAN_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND B.PLCODE IN(1001)\nAND A.TMCODE_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND A.SEQNO > 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  120 */                 ResultSet info2 = null;
/*  121 */                 ResultSet verificaExistencia = null;
/*  122 */                 if (info1 != null && info1.next()) {
/*      */                   do
/*      */                   {
/*      */                     
/*  126 */                     info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS,\n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMM')) IDPERIODO, \n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMMDD')) IDCALENDARIO, \n" + "       A.CO_ID CONTRATO, \n" + "       I.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  132 */                         .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN_ANTERIOR,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                         FROM SYSADM.RATEPLAN_HIST D\n" + "                                         WHERE D.CO_ID = C.CO_ID))) PLAN_ANTERIOR,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                          FROM SYSADM.RATEPLAN_HIST D\n" + "                          WHERE D.CO_ID = C.CO_ID))) PLAN,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       2 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.RATEPLAN_HIST A,\n" + "     SYSADM.CONTR_SERVICES_CAP H,\n" + "     SYSADM.DIRECTORY_NUMBER I,\n" + "     SYSADM.CCONTACT_ALL J\n" + "WHERE A.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  172 */                         .getInt("CO_ID") + "\n" + "AND H.CO_ID = " + info1
/*  173 */                         .getInt("CO_ID") + "\n" + "AND H.CS_DEACTIV_DATE IS NULL\n" + "AND I.DN_ID = H.DN_ID\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */                         
/*  176 */                         .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "GROUP BY A.CO_ID, I.DN_NUM, TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME))");
/*      */ 
/*      */                     
/*  179 */                     if (info2 == null || !info2.next()) {
/*      */                       continue;
/*      */                     }
/*      */                     do {
/*  183 */                       conCDNREG.Conectar();
/*  184 */                       verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                           
/*  186 */                           .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/*  187 */                           .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/*  188 */                           .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/*  189 */                           .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/*  190 */                           .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/*  191 */                           .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/*  192 */                           .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                       
/*  196 */                       if (verificaExistencia == null || !verificaExistencia.next()) {
/*  197 */                         conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN_ANTERIOR, PLAN_ANTERIOR, CODIGO_PLAN, PLAN, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/*  198 */                             .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN_ANTERIOR") + "','" + info2.getString("PLAN_ANTERIOR") + "','" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                       }
/*  200 */                       conCDNREG.CerrarConsulta();
/*  201 */                     } while (info2.next());
/*      */                   }
/*  203 */                   while (info1.next());
/*      */                 }
/*  205 */                 conBSCS.CerrarConsulta();
/*  206 */                 conCDNREG.Conectar();
/*  207 */                 conCDNREG.Consultar("COMMIT");
/*  208 */                 conCDNREG.Consultar("DELETE FROM CDNRG.MODIFICACION_RENTA_REG A\nWHERE A.IDPAIS = " + codigoPais + "\n" + "AND A.TIPO_SERVICIO = 1 \n" + "AND A.TIPO_CAMBIO = 2 \n" + "AND A.TIPO_MOVIMIENTO = 1 \n" + "AND A.ESTATUS_ENVIO = 0\n" + "AND TRUNC(ENTRY_DATE) BETWEEN TRUNC(SYSDATE) - 1 AND TRUNC(SYSDATE)\n" + "AND NOT EXISTS(SELECT 123\n" + "               FROM CDNRG.CDN_CATALOGO_MAIL K,\n" + "                    CDNRG.CDN_DETALLE_MAIL L,\n" + "                    CDNRG.CDN_PAIS M\n" + "               WHERE M.CODIGO = " + codigoPais + "\n" + "               AND M.ID = K.ID_PAIS\n" + "               AND K.ID_DETALLE = L.ID_DETALLE\n" + "               AND L.CODIGO = 'Nombre Plan'\n" + "               AND TRIM(A.CODIGO_PLAN) = TRIM(K.CODIGO))");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  225 */                 conCDNREG.Consultar("COMMIT");
/*  226 */                 conCDNREG.CerrarConsulta();
/*  227 */                 conCDNREG.Conectar();
/*  228 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  236 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/*  238 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/*  239 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/*  240 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/*  243 */                 String mensaje = mensaje_parametrizado;
/*  244 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/*  248 */                     for (int j = 0; j < parametros.size(); j++) {
/*  249 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/*  251 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  260 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/*  261 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/*  262 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/*  263 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/*  265 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/*  266 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/*  267 */                     mensaje = mensaje_parametrizado;
/*  268 */                   } while (info3.next());
/*      */                 }
/*  270 */                 conCDNREG.Consultar("COMMIT");
/*  271 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/*  273 */               resultado = 1;
/*  274 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/*  277 */           else if (tipoMovimiento == 2) {
/*      */           
/*      */           } 
/*  280 */         } else if (tipoCambio == 3) {
/*  281 */           if (tipoMovimiento != 1)
/*      */           {
/*  283 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  286 */         else if (tipoCambio == 4) {
/*  287 */           if (tipoMovimiento != 1)
/*      */           {
/*  289 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  292 */         else if (tipoCambio == 5) {
/*  293 */           if (tipoMovimiento != 1)
/*      */           {
/*  295 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  298 */         else if (tipoCambio == 6) {
/*  299 */           if (tipoMovimiento != 1)
/*      */           {
/*  301 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  304 */         else if (tipoCambio == 7) {
/*  305 */           if (tipoMovimiento != 1)
/*      */           {
/*  307 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  310 */         else if (tipoCambio == 8) {
/*  311 */           if (tipoMovimiento != 1)
/*      */           {
/*  313 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  316 */         else if (tipoCambio == 9 && 
/*  317 */           tipoMovimiento != 1) {
/*      */           
/*  319 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*  323 */       else if (tipoServicio == 2) {
/*  324 */         if (tipoCambio == 1) {
/*  325 */           if (tipoMovimiento != 1)
/*      */           {
/*  327 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  330 */         else if (tipoCambio == 2) {
/*  331 */           if (tipoMovimiento != 1)
/*      */           {
/*  333 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  336 */         else if (tipoCambio == 3) {
/*  337 */           if (tipoMovimiento != 1)
/*      */           {
/*  339 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  342 */         else if (tipoCambio == 4) {
/*  343 */           if (tipoMovimiento != 1)
/*      */           {
/*  345 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  348 */         else if (tipoCambio == 5) {
/*  349 */           if (tipoMovimiento != 1)
/*      */           {
/*  351 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  354 */         else if (tipoCambio == 6) {
/*  355 */           if (tipoMovimiento != 1)
/*      */           {
/*  357 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  360 */         else if (tipoCambio == 7) {
/*  361 */           if (tipoMovimiento != 1)
/*      */           {
/*  363 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  366 */         else if (tipoCambio == 8) {
/*  367 */           if (tipoMovimiento != 1)
/*      */           {
/*  369 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  372 */         else if (tipoCambio == 9 && 
/*  373 */           tipoMovimiento != 1) {
/*      */           
/*  375 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  380 */     } else if (codigoPais == 503) {
/*  381 */       if (tipoServicio == 1) {
/*  382 */         if (tipoCambio == 1) {
/*  383 */           if (tipoMovimiento != 1)
/*      */           {
/*  385 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  388 */         else if (tipoCambio == 2) {
/*  389 */           if (tipoMovimiento == 1) {
/*      */             
/*  391 */             ConexionOracle conBSCS = new ConexionOracle();
/*  392 */             conBSCS.ip = "192.168.4.59";
/*  393 */             conBSCS.port = "1524";
/*  394 */             conBSCS.bd = "BSCS";
/*  395 */             conBSCS.usr = "CDN_USER_BSCS";
/*  396 */             conBSCS.pass = "appP_8eAa8sQ";
/*  397 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/*  399 */               String mensaje_parametrizado = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  407 */               conCDNREG.Conectar();
/*  408 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_SV_A_CAM_PLA_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  414 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/*  415 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/*  418 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/*  419 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/*  421 */               conCDNREG.CerrarConsulta();
/*  422 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/*  423 */                 conBSCS.Conectar();
/*  424 */                 ResultSet info1 = conBSCS.Consultar("SELECT A.CO_ID,\n       B.PLCODE,\n       B.CUSTOMER_ID\nFROM SYSADM.RATEPLAN_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND B.PLCODE IN(1021)\nAND A.TMCODE_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND A.SEQNO > 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  433 */                 ResultSet info2 = null;
/*  434 */                 ResultSet verificaExistencia = null;
/*  435 */                 if (info1 != null && info1.next()) {
/*      */                   do
/*      */                   {
/*      */                     
/*  439 */                     info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS,\n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMM')) IDPERIODO, \n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMMDD')) IDCALENDARIO, \n" + "       A.CO_ID CONTRATO, \n" + "       I.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  445 */                         .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN_ANTERIOR,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                         FROM SYSADM.RATEPLAN_HIST D\n" + "                                         WHERE D.CO_ID = C.CO_ID))) PLAN_ANTERIOR,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                          FROM SYSADM.RATEPLAN_HIST D\n" + "                          WHERE D.CO_ID = C.CO_ID))) PLAN,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       2 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.RATEPLAN_HIST A,\n" + "     SYSADM.CONTR_SERVICES_CAP H,\n" + "     SYSADM.DIRECTORY_NUMBER I,\n" + "     SYSADM.CCONTACT_ALL J\n" + "WHERE A.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  485 */                         .getInt("CO_ID") + "\n" + "AND H.CO_ID = " + info1
/*  486 */                         .getInt("CO_ID") + "\n" + "AND H.CS_DEACTIV_DATE IS NULL\n" + "AND I.DN_ID = H.DN_ID\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */                         
/*  489 */                         .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "GROUP BY A.CO_ID, I.DN_NUM, TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME))");
/*      */ 
/*      */                     
/*  492 */                     if (info2 == null || !info2.next()) {
/*      */                       continue;
/*      */                     }
/*      */                     do {
/*  496 */                       conCDNREG.Conectar();
/*  497 */                       verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                           
/*  499 */                           .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/*  500 */                           .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/*  501 */                           .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/*  502 */                           .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/*  503 */                           .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/*  504 */                           .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/*  505 */                           .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                       
/*  509 */                       if (verificaExistencia == null || !verificaExistencia.next()) {
/*  510 */                         conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN_ANTERIOR, PLAN_ANTERIOR, CODIGO_PLAN, PLAN, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/*  511 */                             .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN_ANTERIOR") + "','" + info2.getString("PLAN_ANTERIOR") + "','" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                       }
/*  513 */                       conCDNREG.CerrarConsulta();
/*  514 */                     } while (info2.next());
/*      */                   }
/*  516 */                   while (info1.next());
/*      */                 }
/*  518 */                 conBSCS.CerrarConsulta();
/*  519 */                 conCDNREG.Conectar();
/*  520 */                 conCDNREG.Consultar("COMMIT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  538 */                 conCDNREG.Consultar("COMMIT");
/*  539 */                 conCDNREG.CerrarConsulta();
/*  540 */                 conCDNREG.Conectar();
/*  541 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  549 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/*  551 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/*  552 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/*  553 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/*  556 */                 String mensaje = mensaje_parametrizado;
/*  557 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/*  561 */                     for (int j = 0; j < parametros.size(); j++) {
/*  562 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/*  564 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  573 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/*  574 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/*  575 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/*  576 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/*  578 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/*  579 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/*  580 */                     mensaje = mensaje_parametrizado;
/*  581 */                   } while (info3.next());
/*      */                 }
/*  583 */                 conCDNREG.Consultar("COMMIT");
/*  584 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/*  586 */               resultado = 1;
/*  587 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/*  590 */           else if (tipoMovimiento == 2) {
/*      */           
/*      */           } 
/*  593 */         } else if (tipoCambio == 3) {
/*  594 */           if (tipoMovimiento != 1)
/*      */           {
/*  596 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  599 */         else if (tipoCambio == 4) {
/*  600 */           if (tipoMovimiento != 1)
/*      */           {
/*  602 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  605 */         else if (tipoCambio == 5) {
/*  606 */           if (tipoMovimiento != 1)
/*      */           {
/*  608 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  611 */         else if (tipoCambio == 6) {
/*  612 */           if (tipoMovimiento != 1)
/*      */           {
/*  614 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  617 */         else if (tipoCambio == 7) {
/*  618 */           if (tipoMovimiento != 1)
/*      */           {
/*  620 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  623 */         else if (tipoCambio == 8) {
/*  624 */           if (tipoMovimiento != 1)
/*      */           {
/*  626 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  629 */         else if (tipoCambio == 9 && 
/*  630 */           tipoMovimiento != 1) {
/*      */           
/*  632 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*  636 */       else if (tipoServicio == 2) {
/*  637 */         if (tipoCambio == 1) {
/*  638 */           if (tipoMovimiento != 1)
/*      */           {
/*  640 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  643 */         else if (tipoCambio == 2) {
/*  644 */           if (tipoMovimiento != 1)
/*      */           {
/*  646 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  649 */         else if (tipoCambio == 3) {
/*  650 */           if (tipoMovimiento != 1)
/*      */           {
/*  652 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  655 */         else if (tipoCambio == 4) {
/*  656 */           if (tipoMovimiento != 1)
/*      */           {
/*  658 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  661 */         else if (tipoCambio == 5) {
/*  662 */           if (tipoMovimiento != 1)
/*      */           {
/*  664 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  667 */         else if (tipoCambio == 6) {
/*  668 */           if (tipoMovimiento != 1)
/*      */           {
/*  670 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  673 */         else if (tipoCambio == 7) {
/*  674 */           if (tipoMovimiento != 1)
/*      */           {
/*  676 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  679 */         else if (tipoCambio == 8) {
/*  680 */           if (tipoMovimiento != 1)
/*      */           {
/*  682 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  685 */         else if (tipoCambio == 9 && 
/*  686 */           tipoMovimiento != 1) {
/*      */           
/*  688 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  693 */     } else if (codigoPais == 504) {
/*  694 */       if (tipoServicio == 1) {
/*  695 */         if (tipoCambio == 1) {
/*  696 */           if (tipoMovimiento != 1)
/*      */           {
/*  698 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  701 */         else if (tipoCambio == 2) {
/*  702 */           if (tipoMovimiento == 1) {
/*      */             
/*  704 */             ConexionOracle conBSCS = new ConexionOracle();
/*  705 */             conBSCS.ip = "192.168.4.59";
/*  706 */             conBSCS.port = "1524";
/*  707 */             conBSCS.bd = "BSCS";
/*  708 */             conBSCS.usr = "CDN_USER_BSCS";
/*  709 */             conBSCS.pass = "appP_8eAa8sQ";
/*  710 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  719 */               String mensaje_parametrizado = "";
/*  720 */               conCDNREG.Conectar();
/*  721 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_HN_A_CAM_PLA_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  727 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/*  728 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/*  731 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/*  732 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/*  734 */               conCDNREG.CerrarConsulta();
/*  735 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/*  736 */                 conBSCS.Conectar();
/*  737 */                 ResultSet info1 = conBSCS.Consultar("SELECT A.CO_ID,\n       B.PLCODE,\n       B.CUSTOMER_ID\nFROM SYSADM.RATEPLAN_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND B.PLCODE IN(1124)\nAND A.TMCODE_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND A.SEQNO > 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  746 */                 ResultSet info2 = null;
/*  747 */                 ResultSet verificaExistencia = null;
/*  748 */                 if (info1 != null && info1.next()) {
/*      */                   do
/*      */                   {
/*      */                     
/*  752 */                     info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS,\n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMM')) IDPERIODO, \n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMMDD')) IDCALENDARIO, \n" + "       A.CO_ID CONTRATO, \n" + "       I.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  758 */                         .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN_ANTERIOR,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                         FROM SYSADM.RATEPLAN_HIST D\n" + "                                         WHERE D.CO_ID = C.CO_ID))) PLAN_ANTERIOR,                \n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                          FROM SYSADM.RATEPLAN_HIST D\n" + "                          WHERE D.CO_ID = C.CO_ID))) PLAN,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE,                                   \n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       2 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.RATEPLAN_HIST A,\n" + "     SYSADM.CONTR_SERVICES_CAP H,\n" + "     SYSADM.DIRECTORY_NUMBER I,\n" + "     SYSADM.CCONTACT_ALL J\n" + "WHERE A.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  798 */                         .getInt("CO_ID") + "\n" + "AND H.CO_ID = " + info1
/*  799 */                         .getInt("CO_ID") + "\n" + "AND H.CS_DEACTIV_DATE IS NULL\n" + "AND I.DN_ID = H.DN_ID\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */                         
/*  802 */                         .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "GROUP BY A.CO_ID, I.DN_NUM, TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME))");
/*      */ 
/*      */                     
/*  805 */                     if (info2 == null || !info2.next()) {
/*      */                       continue;
/*      */                     }
/*      */                     do {
/*  809 */                       conCDNREG.Conectar();
/*  810 */                       verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                           
/*  812 */                           .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/*  813 */                           .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/*  814 */                           .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/*  815 */                           .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/*  816 */                           .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/*  817 */                           .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/*  818 */                           .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                       
/*  822 */                       if (verificaExistencia == null || !verificaExistencia.next()) {
/*  823 */                         conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN_ANTERIOR, PLAN_ANTERIOR, CODIGO_PLAN, PLAN, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/*  824 */                             .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN_ANTERIOR") + "','" + info2.getString("PLAN_ANTERIOR") + "','" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                       }
/*  826 */                       conCDNREG.CerrarConsulta();
/*  827 */                     } while (info2.next());
/*      */                   }
/*  829 */                   while (info1.next());
/*      */                 }
/*  831 */                 conBSCS.CerrarConsulta();
/*  832 */                 conCDNREG.Conectar();
/*  833 */                 conCDNREG.Consultar("COMMIT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  852 */                 conCDNREG.Consultar("COMMIT");
/*  853 */                 conCDNREG.CerrarConsulta();
/*  854 */                 conCDNREG.Conectar();
/*  855 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  863 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/*  865 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/*  866 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/*  867 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/*  870 */                 String mensaje = mensaje_parametrizado;
/*  871 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/*  875 */                     for (int j = 0; j < parametros.size(); j++) {
/*  876 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/*  878 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  887 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/*  888 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/*  889 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/*  890 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/*  892 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/*  893 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/*  894 */                     mensaje = mensaje_parametrizado;
/*  895 */                   } while (info3.next());
/*      */                 }
/*  897 */                 conCDNREG.Consultar("COMMIT");
/*  898 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/*  900 */               resultado = 1;
/*  901 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/*  904 */           else if (tipoMovimiento == 2) {
/*      */           
/*      */           } 
/*  907 */         } else if (tipoCambio == 3) {
/*  908 */           if (tipoMovimiento != 1)
/*      */           {
/*  910 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  913 */         else if (tipoCambio == 4) {
/*  914 */           if (tipoMovimiento != 1)
/*      */           {
/*  916 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  919 */         else if (tipoCambio == 5) {
/*  920 */           if (tipoMovimiento != 1)
/*      */           {
/*  922 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  925 */         else if (tipoCambio == 6) {
/*  926 */           if (tipoMovimiento != 1)
/*      */           {
/*  928 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  931 */         else if (tipoCambio == 7) {
/*  932 */           if (tipoMovimiento != 1)
/*      */           {
/*  934 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  937 */         else if (tipoCambio == 8) {
/*  938 */           if (tipoMovimiento != 1)
/*      */           {
/*  940 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/*  943 */         else if (tipoCambio == 9 && 
/*  944 */           tipoMovimiento != 1) {
/*      */           
/*  946 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*  950 */       else if (tipoServicio == 2) {
/*  951 */         if (tipoCambio == 1) {
/*  952 */           if (tipoMovimiento == 1)
/*      */           {
/*  954 */             ConexionOPEN conOPEN = new ConexionOPEN();
/*  955 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/*  957 */               String mensaje_parametrizado = "";
/*  958 */               conCDNREG.Conectar();
/*  959 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRF_HN_A_PAQ_ADI_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  965 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/*  966 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/*  969 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/*  970 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/*  972 */               conCDNREG.CerrarConsulta();
/*  973 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/*  974 */                 conOPEN.Conectar("504");
/*  975 */                 ResultSet verificaExistencia = null;
/*  976 */                 ResultSet info2 = null;
/*  977 */                 info2 = conOPEN.Consultar("select idpais,idperiodo,idcalendario,contrato,telefono,nombre,codigo_pais, codigo_plan,plan,codigo_paquete, count(codigo_paquete)||' - '||paquete paquete\n    ,(CASE WHEN TRIM(TO_CHAR(round(sum(renta*1.15),2),'999999999.99')) = '.00' THEN '0.00' ELSE TRIM(TO_CHAR(round(sum(renta*1.15),2),'999999999.99')) END) renta, entry_date,fecha_efectiva,hora_efectiva,tipo_servicio,tipo_cambio,tipo_movimiento,estatus_envio\nfrom (\nselect '504' idpais, to_char(aa.attention_date,'yyyymm') idperiodo, to_char(aa.attention_date,'yyyymmdd') idcalendario\n    ,(select sesususc from smart.servsusc where sesunuse = aa.product_id ) contrato\n    ,'504'||lpad(substr(smart.regexp_replace(upper(aa.contact_phone_number),'[A-Z|a-z|)|(| |-|*|_|!|#|$|%|&|/|=|?|¡|¿|{|}|<|>|.|,|:|;|@|ñ|Ñ|º|+|Á|.|Ó|.]',''),1,8),8,0) telefono\n    ,(select suscnomb from smart.servsusc, smart.suscripc where susccodi=sesususc and sesunuse = aa.product_id ) nombre    \n    ,1124 codigo_pais\n    ,(select cc.commercial_plan_id from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) codigo_plan\n    ,(select cc.name from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) plan\n    ,bb.class_service_id codigo_paquete\n    ,(select ee.description from smart.ps_class_service ee where ee.class_service_id =bb.class_service_id ) paquete\n    ,nvl((select ff.valor_base_tarifa \n        from smart.planes ff, smart.servsusc gg\n        where gg.sesunuse = aa.product_id\n        and gg.sesuplfa=ff.id_plan_fact\n        and ff.id_comp = 98 \n        and id_clas = bb.class_service_id\n        and rownum <= 1),0) renta \n    ,to_char(aa.attention_date, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\n    ,to_char(aa.attention_date, 'DD/MM/YYYY') FECHA_EFECTIVA\n    ,to_char(aa.attention_date, 'HH24:MI:SS') HORA_EFECTIVA\n    ,2 tipo_servicio\n    ,1 tipo_cambio\n    ,case when aa.package_type_id = 599 then 2\n          when aa.package_type_id = 600 then 1\n     end tipo_movimiento\n    ,0 estatus_envio     \nfrom smart.mo_packages aa , smart.mo_component bb\nwhere aa.package_type_id in (600)\nand aa.package_id=bb.package_id\nand aa.attention_date BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nand aa.motive_status_id = 14\nand bb.component_type_id = 98\nand bb.class_service_id in (2501,3156,3157,5051,5052,5053,5054,5063,5064,5090,5091,8010,8011,8033)\n)\ngroup by idpais,idperiodo,idcalendario,contrato,telefono,nombre,codigo_pais, codigo_plan,plan,codigo_paquete, paquete\n    ,entry_date,fecha_efectiva,hora_efectiva,tipo_servicio,tipo_cambio,tipo_movimiento,estatus_envio");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1015 */                 if (info2 != null && info2.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1019 */                     conCDNREG.Conectar();
/* 1020 */                     verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                         
/* 1022 */                         .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 1023 */                         .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 1024 */                         .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 1025 */                         .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 1026 */                         .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 1027 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 1028 */                         .getString("HORA_EFECTIVA") + "'\n" + "AND CODIGO_PAQUETE = '" + info2
/* 1029 */                         .getString("CODIGO_PAQUETE") + "'\n" + "AND TIPO_SERVICIO = 2\n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                     
/* 1033 */                     if (verificaExistencia == null || !verificaExistencia.next()) {
/* 1034 */                       conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN, PLAN, CODIGO_PAQUETE, PAQUETE, RENTA, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 1035 */                           .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "','" + info2.getString("CODIGO_PAQUETE") + "','" + info2.getString("PAQUETE") + "','" + info2.getString("RENTA") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                     }
/* 1037 */                     conCDNREG.CerrarConsulta();
/* 1038 */                   } while (info2.next());
/*      */                 }
/* 1040 */                 conOPEN.CerrarConsulta();
/* 1041 */                 conCDNREG.Conectar();
/* 1042 */                 conCDNREG.Consultar("COMMIT");
/* 1043 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1051 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 1053 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 1054 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 1055 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 1058 */                 String mensaje = mensaje_parametrizado;
/* 1059 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1063 */                     for (int j = 0; j < parametros.size(); j++) {
/* 1064 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 1066 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1075 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 1076 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 1077 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 1078 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 1080 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 1081 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 1082 */                     mensaje = mensaje_parametrizado;
/* 1083 */                   } while (info3.next());
/*      */                 }
/* 1085 */                 conCDNREG.Consultar("COMMIT");
/* 1086 */                 conCDNREG.CerrarConsulta();
/* 1087 */                 conOPEN.CerrarConexion();
/*      */               } 
/* 1089 */               resultado = 1;
/* 1090 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/* 1093 */           else if (tipoMovimiento == 2)
/*      */           {
/* 1095 */             ConexionOPEN conOPEN = new ConexionOPEN();
/* 1096 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 1098 */               String mensaje_parametrizado = "";
/* 1099 */               conCDNREG.Conectar();
/* 1100 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRF_HN_B_PAQ_ADI_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1106 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 1107 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 1110 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 1111 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 1113 */               conCDNREG.CerrarConsulta();
/* 1114 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 1115 */                 conOPEN.Conectar("504");
/* 1116 */                 ResultSet verificaExistencia = null;
/* 1117 */                 ResultSet info2 = null;
/* 1118 */                 info2 = conOPEN.Consultar("select idpais,idperiodo,idcalendario,contrato,telefono,nombre,codigo_pais, codigo_plan,plan,codigo_paquete, count(codigo_paquete)||' - '||paquete paquete\n    ,(CASE WHEN TRIM(TO_CHAR(round(sum(renta*1.15),2),'999999999.99')) = '.00' THEN '0.00' ELSE TRIM(TO_CHAR(round(sum(renta*1.15),2),'999999999.99')) END) renta, entry_date,fecha_efectiva,hora_efectiva,tipo_servicio,tipo_cambio,tipo_movimiento,estatus_envio\nfrom (\nselect '504' idpais, to_char(aa.attention_date,'yyyymm') idperiodo, to_char(aa.attention_date,'yyyymmdd') idcalendario\n    ,(select sesususc from smart.servsusc where sesunuse = aa.product_id ) contrato\n    ,'504'||lpad(substr(smart.regexp_replace(upper(aa.contact_phone_number),'[A-Z|a-z|)|(| |-|*|_|!|#|$|%|&|/|=|?|¡|¿|{|}|<|>|.|,|:|;|@|ñ|Ñ|º|+|Á|.|Ó|.]',''),1,8),8,0) telefono\n    ,(select suscnomb from smart.servsusc, smart.suscripc where susccodi=sesususc and sesunuse = aa.product_id ) nombre    \n    ,1124 codigo_pais\n    ,(select cc.commercial_plan_id from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) codigo_plan\n    ,(select cc.name from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) plan\n    ,bb.class_service_id codigo_paquete\n    ,(select ee.description from smart.ps_class_service ee where ee.class_service_id =bb.class_service_id ) paquete\n    ,nvl((select ff.valor_base_tarifa \n        from smart.planes ff, smart.servsusc gg\n        where gg.sesunuse = aa.product_id\n        and gg.sesuplfa=ff.id_plan_fact\n        and ff.id_comp = 98 \n        and id_clas = bb.class_service_id\n        and rownum <= 1),0) renta \n    ,to_char(aa.attention_date, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\n    ,to_char(aa.attention_date, 'DD/MM/YYYY') FECHA_EFECTIVA\n    ,to_char(aa.attention_date, 'HH24:MI:SS') HORA_EFECTIVA\n    ,2 tipo_servicio\n    ,1 tipo_cambio\n    ,case when aa.package_type_id = 599 then 2\n          when aa.package_type_id = 600 then 1\n     end tipo_movimiento\n    ,0 estatus_envio     \nfrom smart.mo_packages aa , smart.mo_component bb\nwhere aa.package_type_id in (599)\nand aa.package_id=bb.package_id\nand aa.attention_date BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nand aa.motive_status_id = 14\nand bb.component_type_id = 98\nand bb.class_service_id in (2501,3156,3157,5051,5052,5053,5054,5063,5064,5090,5091,8010,8011,8033)\n)\ngroup by idpais,idperiodo,idcalendario,contrato,telefono,nombre,codigo_pais, codigo_plan,plan,codigo_paquete, paquete\n    ,entry_date,fecha_efectiva,hora_efectiva,tipo_servicio,tipo_cambio,tipo_movimiento,estatus_envio");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1156 */                 if (info2 != null && info2.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1160 */                     conCDNREG.Conectar();
/* 1161 */                     verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                         
/* 1163 */                         .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 1164 */                         .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 1165 */                         .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 1166 */                         .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 1167 */                         .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 1168 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 1169 */                         .getString("HORA_EFECTIVA") + "'\n" + "AND CODIGO_PAQUETE = '" + info2
/* 1170 */                         .getString("CODIGO_PAQUETE") + "'\n" + "AND TIPO_SERVICIO = 2\n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2");
/*      */ 
/*      */ 
/*      */                     
/* 1174 */                     if (verificaExistencia == null || !verificaExistencia.next()) {
/* 1175 */                       conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN, PLAN, CODIGO_PAQUETE, PAQUETE, RENTA, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 1176 */                           .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "','" + info2.getString("CODIGO_PAQUETE") + "','" + info2.getString("PAQUETE") + "','" + info2.getString("RENTA") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                     }
/* 1178 */                     conCDNREG.CerrarConsulta();
/* 1179 */                   } while (info2.next());
/*      */                 }
/* 1181 */                 conOPEN.CerrarConsulta();
/* 1182 */                 conCDNREG.Conectar();
/* 1183 */                 conCDNREG.Consultar("COMMIT");
/* 1184 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1192 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 1194 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 1195 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 1196 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 1199 */                 String mensaje = mensaje_parametrizado;
/* 1200 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1204 */                     for (int j = 0; j < parametros.size(); j++) {
/* 1205 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 1207 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1216 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 1217 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 1218 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 1219 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 1221 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 1222 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 1223 */                     mensaje = mensaje_parametrizado;
/* 1224 */                   } while (info3.next());
/*      */                 }
/* 1226 */                 conCDNREG.Consultar("COMMIT");
/* 1227 */                 conCDNREG.CerrarConsulta();
/* 1228 */                 conOPEN.CerrarConexion();
/*      */               } 
/* 1230 */               resultado = 1;
/* 1231 */             } catch (Exception exception) {}
/*      */           }
/*      */         
/*      */         }
/* 1235 */         else if (tipoCambio == 2) {
/* 1236 */           if (tipoMovimiento != 1)
/*      */           {
/* 1238 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1241 */         else if (tipoCambio == 3) {
/* 1242 */           if (tipoMovimiento != 1)
/*      */           {
/* 1244 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1247 */         else if (tipoCambio == 4) {
/* 1248 */           if (tipoMovimiento != 1)
/*      */           {
/* 1250 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1253 */         else if (tipoCambio == 5) {
/* 1254 */           if (tipoMovimiento != 1)
/*      */           {
/* 1256 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1259 */         else if (tipoCambio == 6) {
/* 1260 */           if (tipoMovimiento != 1)
/*      */           {
/* 1262 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1265 */         else if (tipoCambio == 7) {
/* 1266 */           if (tipoMovimiento != 1)
/*      */           {
/* 1268 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1271 */         else if (tipoCambio == 8) {
/* 1272 */           if (tipoMovimiento != 1)
/*      */           {
/* 1274 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1277 */         else if (tipoCambio == 9 && 
/* 1278 */           tipoMovimiento != 1) {
/*      */           
/* 1280 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 1285 */     } else if (codigoPais == 505) {
/* 1286 */       if (tipoServicio == 1) {
/* 1287 */         if (tipoCambio == 1) {
/* 1288 */           if (tipoMovimiento != 1)
/*      */           {
/* 1290 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1293 */         else if (tipoCambio == 2) {
/* 1294 */           if (tipoMovimiento == 1) {
/*      */             
/* 1296 */             ConexionOracle conBSCS = new ConexionOracle();
/* 1297 */             conBSCS.ip = "192.168.4.59";
/* 1298 */             conBSCS.port = "1524";
/* 1299 */             conBSCS.bd = "BSCS";
/* 1300 */             conBSCS.usr = "CDN_USER_BSCS";
/* 1301 */             conBSCS.pass = "appP_8eAa8sQ";
/* 1302 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 1304 */               String mensaje_parametrizado = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1312 */               conCDNREG.Conectar();
/* 1313 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_NI_A_CAM_PLA_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1319 */               System.out.println("Realizado");
/* 1320 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 1321 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 1324 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 1325 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 1327 */               conCDNREG.CerrarConsulta();
/* 1328 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 1329 */                 conBSCS.Conectar();
/*      */                 
/* 1331 */                 ResultSet info1 = conBSCS.Consultar("SELECT A.CO_ID,\n       B.PLCODE,\n       B.CUSTOMER_ID\nFROM SYSADM.RATEPLAN_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND B.PLCODE IN(1121)\nAND A.TMCODE_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND A.SEQNO > 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1340 */                 ResultSet info2 = null;
/* 1341 */                 ResultSet verificaExistencia = null;
/* 1342 */                 if (info1 != null && info1.next()) {
/*      */                   do
/*      */                   {
/*      */                     
/* 1346 */                     info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS,\n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMM')) IDPERIODO, \n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMMDD')) IDCALENDARIO, \n" + "       A.CO_ID CONTRATO, \n" + "       I.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1352 */                         .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN_ANTERIOR,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                         FROM SYSADM.RATEPLAN_HIST D\n" + "                                         WHERE D.CO_ID = C.CO_ID))) PLAN_ANTERIOR,                \n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                          FROM SYSADM.RATEPLAN_HIST D\n" + "                          WHERE D.CO_ID = C.CO_ID))) PLAN,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE,                                   \n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       2 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.RATEPLAN_HIST A,\n" + "     SYSADM.CONTR_SERVICES_CAP H,\n" + "     SYSADM.DIRECTORY_NUMBER I,\n" + "     SYSADM.CCONTACT_ALL J\n" + "WHERE A.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1392 */                         .getInt("CO_ID") + "\n" + "AND H.CO_ID = " + info1
/* 1393 */                         .getInt("CO_ID") + "\n" + "AND H.CS_DEACTIV_DATE IS NULL\n" + "AND I.DN_ID = H.DN_ID\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */                         
/* 1396 */                         .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "GROUP BY A.CO_ID, I.DN_NUM, TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME))");
/*      */ 
/*      */                     
/* 1399 */                     if (info2 == null || !info2.next()) {
/*      */                       continue;
/*      */                     }
/*      */                     do {
/* 1403 */                       conCDNREG.Conectar();
/* 1404 */                       verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                           
/* 1406 */                           .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 1407 */                           .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 1408 */                           .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 1409 */                           .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 1410 */                           .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 1411 */                           .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 1412 */                           .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                       
/* 1416 */                       if (verificaExistencia == null || !verificaExistencia.next()) {
/* 1417 */                         conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN_ANTERIOR, PLAN_ANTERIOR, CODIGO_PLAN, PLAN, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 1418 */                             .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN_ANTERIOR") + "','" + info2.getString("PLAN_ANTERIOR") + "','" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                       }
/* 1420 */                       conCDNREG.CerrarConsulta();
/* 1421 */                     } while (info2.next());
/*      */                   }
/* 1423 */                   while (info1.next());
/*      */                 }
/* 1425 */                 conBSCS.CerrarConsulta();
/* 1426 */                 conCDNREG.Conectar();
/* 1427 */                 conCDNREG.Consultar("COMMIT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1447 */                 conCDNREG.CerrarConsulta();
/* 1448 */                 conCDNREG.Conectar();
/* 1449 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1457 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 1459 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 1460 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 1461 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 1464 */                 String mensaje = mensaje_parametrizado;
/* 1465 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1469 */                     for (int j = 0; j < parametros.size(); j++) {
/* 1470 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 1472 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1481 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 1482 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 1483 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 1484 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 1486 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 1487 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 1488 */                     mensaje = mensaje_parametrizado;
/* 1489 */                   } while (info3.next());
/*      */                 }
/* 1491 */                 conCDNREG.Consultar("COMMIT");
/* 1492 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/* 1494 */               resultado = 1;
/* 1495 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/* 1498 */           else if (tipoMovimiento == 2) {
/*      */           
/*      */           } 
/* 1501 */         } else if (tipoCambio == 3) {
/* 1502 */           if (tipoMovimiento != 1)
/*      */           {
/* 1504 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1507 */         else if (tipoCambio == 4) {
/* 1508 */           if (tipoMovimiento != 1)
/*      */           {
/* 1510 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1513 */         else if (tipoCambio == 5) {
/* 1514 */           if (tipoMovimiento != 1)
/*      */           {
/* 1516 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1519 */         else if (tipoCambio == 6) {
/* 1520 */           if (tipoMovimiento != 1)
/*      */           {
/* 1522 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1525 */         else if (tipoCambio == 7) {
/* 1526 */           if (tipoMovimiento != 1)
/*      */           {
/* 1528 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1531 */         else if (tipoCambio == 8) {
/* 1532 */           if (tipoMovimiento != 1)
/*      */           {
/* 1534 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1537 */         else if (tipoCambio == 9 && 
/* 1538 */           tipoMovimiento != 1) {
/*      */           
/* 1540 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/* 1544 */       else if (tipoServicio == 2) {
/* 1545 */         if (tipoCambio == 1) {
/* 1546 */           if (tipoMovimiento == 1)
/*      */           {
/* 1548 */             ConexionOPEN conOPEN = new ConexionOPEN();
/* 1549 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 1551 */               String mensaje_parametrizado = "";
/* 1552 */               conCDNREG.Conectar();
/* 1553 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRF_NI_A_PAQ_ADI_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1559 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 1560 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 1563 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 1564 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 1566 */               conCDNREG.CerrarConsulta();
/* 1567 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 1568 */                 conOPEN.Conectar("505");
/* 1569 */                 ResultSet verificaExistencia = null;
/* 1570 */                 ResultSet info2 = null;
/* 1571 */                 info2 = conOPEN.Consultar("select idpais,idperiodo,idcalendario,contrato,telefono,nombre,codigo_pais,codigo_plan,plan, codigo_paquete, paquete\n    ,renta,entry_date,fecha_efectiva,hora_efectiva,tipo_servicio,tipo_cambio,tipo_movimiento, estatus_envio\nfrom (\nselect '505' idpais, to_char(aa.attention_date,'yyyymm') idperiodo, to_char(aa.attention_date,'yyyymmdd') idcalendario\n    ,(select sesususc from smart.servsusc where sesunuse = aa.product_id ) contrato\n    ,'505'||lpad(substr(regexp_replace(upper(aa.contact_phone_number),'[A-Z|a-z|)|(| |-|*|_|!|#|$|%|&|/|=|?|¡|¿|{|}|<|>|.|,|:|;|@|ñ|Ñ|º|+|Á|.|Ó|.]',''),1,8),8,0) telefono\n    ,(select suscnomb from smart.servsusc, smart.suscripc where susccodi=sesususc and sesunuse = aa.product_id ) nombre    \n    ,1121 codigo_pais\n    ,(select cc.commercial_plan_id from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) codigo_plan\n    ,(select cc.name from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) plan\n    ,bb.class_service_id codigo_paquete\n    ,(select ee.description from smart.ps_class_service ee where ee.class_service_id =bb.class_service_id ) paquete\n    ,null renta \n    ,to_char(aa.attention_date, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\n    ,to_char(aa.attention_date, 'DD/MM/YYYY') FECHA_EFECTIVA\n    ,to_char(aa.attention_date, 'HH24:MI:SS') HORA_EFECTIVA\n    ,2 tipo_servicio\n    ,1 tipo_cambio\n    ,case when aa.package_type_id = 599 then 2\n          when aa.package_type_id = 600 then 1\n     end tipo_movimiento\n    ,0 estatus_envio     \nfrom smart.mo_packages aa , smart.mo_component bb\nwhere aa.package_type_id in (600)\nand aa.package_id=bb.package_id\nand aa.attention_date BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nand aa.motive_status_id = 14\nand bb.component_type_id = 98\n--and aa.package_id = 12185954\nunion all\nselect '505' idpais, to_char(aa.attention_date,'yyyymm') idperiodo, to_char(aa.attention_date,'yyyymmdd') idcalendario\n    ,(select sesususc from smart.servsusc where sesunuse = aa.product_id ) contrato\n    ,'505'||lpad(substr(regexp_replace(upper(aa.contact_phone_number),'[A-Z|a-z|)|(| |-|*|_|!|#|$|%|&|/|=|?|¡|¿|{|}|<|>|.|,|:|;|@|ñ|Ñ|º|+|Á|.|Ó|.]',''),1,8),8,0) telefono\n    ,(select suscnomb from smart.servsusc, smart.suscripc where susccodi=sesususc and sesunuse = aa.product_id ) nombre    \n    ,1121 codigo_pais\n    ,(select cc.commercial_plan_id from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) codigo_plan\n    ,(select cc.name from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) plan\n    ,bb.class_service_id codigo_paquete\n    ,(select ee.description from smart.ps_class_service ee where ee.class_service_id =bb.class_service_id ) paquete\n    ,null renta \n    ,to_char(aa.attention_date, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\n    ,to_char(aa.attention_date, 'DD/MM/YYYY') FECHA_EFECTIVA\n    ,to_char(aa.attention_date, 'HH24:MI:SS') HORA_EFECTIVA\n    ,2 tipo_servicio\n    ,1 tipo_cambio\n    ,case when tt.motive_type_id = 12 then 2\n          when tt.motive_type_id = 49 then 1\n     end tipo_movimiento\n    ,0 estatus_envio \nfrom smart.mo_motive rr, smart.pr_component_request tt, smart.mo_packages aa, smart.pr_component bb\nwhere 1=1\nand rr.motive_id=tt.motive_id\n--and rr.package_id = 41321189       ---solicitud prueba\nand rr.package_id=aa.package_id\nand aa.package_id = tt.package_id\nand aa.package_type_id = 501\nand tt.motive_type_id = 49\nand aa.attention_date BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nand tt.component_id = bb.component_id\nand bb.component_type_id = 98\n)\nwhere codigo_paquete in (3157,3160,3420,3421,3511,3531,3667,3668,3669,3670,3712,3713,3731,3732,3733)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1633 */                 if (info2 != null && info2.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1637 */                     conCDNREG.Conectar();
/* 1638 */                     verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                         
/* 1640 */                         .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 1641 */                         .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 1642 */                         .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 1643 */                         .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 1644 */                         .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 1645 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 1646 */                         .getString("HORA_EFECTIVA") + "'\n" + "AND CODIGO_PAQUETE = '" + info2
/* 1647 */                         .getString("CODIGO_PAQUETE") + "'\n" + "AND TIPO_SERVICIO = 2\n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                     
/* 1651 */                     if (verificaExistencia == null || !verificaExistencia.next()) {
/* 1652 */                       conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN, PLAN, CODIGO_PAQUETE, PAQUETE, RENTA, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 1653 */                           .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "','" + info2.getString("CODIGO_PAQUETE") + "','" + info2.getString("PAQUETE") + "','" + info2.getString("RENTA") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                     }
/* 1655 */                     conCDNREG.CerrarConsulta();
/* 1656 */                   } while (info2.next());
/*      */                 }
/* 1658 */                 conOPEN.CerrarConsulta();
/* 1659 */                 conCDNREG.Conectar();
/* 1660 */                 conCDNREG.Consultar("COMMIT");
/* 1661 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1669 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 1671 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 1672 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 1673 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 1676 */                 String mensaje = mensaje_parametrizado;
/* 1677 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1681 */                     for (int j = 0; j < parametros.size(); j++) {
/* 1682 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 1684 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1693 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 1694 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 1695 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 1696 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 1698 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 1699 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 1700 */                     mensaje = mensaje_parametrizado;
/* 1701 */                   } while (info3.next());
/*      */                 }
/* 1703 */                 conCDNREG.Consultar("COMMIT");
/* 1704 */                 conCDNREG.CerrarConsulta();
/* 1705 */                 conOPEN.CerrarConexion();
/*      */               } 
/* 1707 */               resultado = 1;
/* 1708 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/* 1711 */           else if (tipoMovimiento == 2)
/*      */           {
/* 1713 */             ConexionOPEN conOPEN = new ConexionOPEN();
/* 1714 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 1716 */               String mensaje_parametrizado = "";
/* 1717 */               conCDNREG.Conectar();
/* 1718 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRF_NI_B_PAQ_ADI_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1724 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 1725 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 1728 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 1729 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 1731 */               conCDNREG.CerrarConsulta();
/* 1732 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 1733 */                 conOPEN.Conectar("505");
/* 1734 */                 ResultSet verificaExistencia = null;
/* 1735 */                 ResultSet info2 = null;
/* 1736 */                 info2 = conOPEN.Consultar("select idpais,idperiodo,idcalendario,contrato,telefono,nombre,codigo_pais,codigo_plan,plan, codigo_paquete, paquete\n    ,renta,entry_date,fecha_efectiva,hora_efectiva,tipo_servicio,tipo_cambio,tipo_movimiento, estatus_envio\nfrom (\nselect '505' idpais, to_char(aa.attention_date,'yyyymm') idperiodo, to_char(aa.attention_date,'yyyymmdd') idcalendario\n    ,(select sesususc from smart.servsusc where sesunuse = aa.product_id ) contrato\n    ,'505'||lpad(substr(regexp_replace(upper(aa.contact_phone_number),'[A-Z|a-z|)|(| |-|*|_|!|#|$|%|&|/|=|?|¡|¿|{|}|<|>|.|,|:|;|@|ñ|Ñ|º|+|Á|.|Ó|.]',''),1,8),8,0) telefono\n    ,(select suscnomb from smart.servsusc, smart.suscripc where susccodi=sesususc and sesunuse = aa.product_id ) nombre    \n    ,1121 codigo_pais\n    ,(select cc.commercial_plan_id from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) codigo_plan\n    ,(select cc.name from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) plan\n    ,bb.class_service_id codigo_paquete\n    ,(select ee.description from smart.ps_class_service ee where ee.class_service_id =bb.class_service_id ) paquete\n    ,null renta \n    ,to_char(aa.attention_date, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\n    ,to_char(aa.attention_date, 'DD/MM/YYYY') FECHA_EFECTIVA\n    ,to_char(aa.attention_date, 'HH24:MI:SS') HORA_EFECTIVA\n    ,2 tipo_servicio\n    ,1 tipo_cambio\n    ,case when aa.package_type_id = 599 then 2\n          when aa.package_type_id = 600 then 1\n     end tipo_movimiento\n    ,0 estatus_envio     \nfrom smart.mo_packages aa , smart.mo_component bb\nwhere aa.package_type_id in (599)\nand aa.package_id=bb.package_id\nand aa.attention_date BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nand aa.motive_status_id = 14\nand bb.component_type_id = 98\n--and aa.package_id = 12185954\nunion all\nselect '505' idpais, to_char(aa.attention_date,'yyyymm') idperiodo, to_char(aa.attention_date,'yyyymmdd') idcalendario\n    ,(select sesususc from smart.servsusc where sesunuse = aa.product_id ) contrato\n    ,'505'||lpad(substr(regexp_replace(upper(aa.contact_phone_number),'[A-Z|a-z|)|(| |-|*|_|!|#|$|%|&|/|=|?|¡|¿|{|}|<|>|.|,|:|;|@|ñ|Ñ|º|+|Á|.|Ó|.]',''),1,8),8,0) telefono\n    ,(select suscnomb from smart.servsusc, smart.suscripc where susccodi=sesususc and sesunuse = aa.product_id ) nombre    \n    ,1121 codigo_pais\n    ,(select cc.commercial_plan_id from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) codigo_plan\n    ,(select cc.name from smart.cc_commercial_plan cc, smart.pr_product dd where cc.commercial_plan_id = dd.commercial_plan_id and dd.product_id = aa.product_id) plan\n    ,bb.class_service_id codigo_paquete\n    ,(select ee.description from smart.ps_class_service ee where ee.class_service_id =bb.class_service_id ) paquete\n    ,null renta \n    ,to_char(aa.attention_date, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\n    ,to_char(aa.attention_date, 'DD/MM/YYYY') FECHA_EFECTIVA\n    ,to_char(aa.attention_date, 'HH24:MI:SS') HORA_EFECTIVA\n    ,2 tipo_servicio\n    ,1 tipo_cambio\n    ,case when tt.motive_type_id = 12 then 2\n          when tt.motive_type_id = 49 then 1\n     end tipo_movimiento\n    ,0 estatus_envio \nfrom smart.mo_motive rr, smart.pr_component_request tt, smart.mo_packages aa, smart.pr_component bb\nwhere 1=1\nand rr.motive_id=tt.motive_id\n--and rr.package_id = 41321189       ---solicitud prueba\nand rr.package_id=aa.package_id\nand aa.package_id = tt.package_id\nand aa.package_type_id = 501\nand tt.motive_type_id = 12\nand aa.attention_date BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nand tt.component_id = bb.component_id\nand bb.component_type_id = 98\n)\nwhere codigo_paquete in (3157,3160,3420,3421,3511,3531,3667,3668,3669,3670,3712,3713,3731,3732,3733)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1798 */                 if (info2 != null && info2.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1802 */                     conCDNREG.Conectar();
/* 1803 */                     verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                         
/* 1805 */                         .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 1806 */                         .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 1807 */                         .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 1808 */                         .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 1809 */                         .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 1810 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 1811 */                         .getString("HORA_EFECTIVA") + "'\n" + "AND CODIGO_PAQUETE = '" + info2
/* 1812 */                         .getString("CODIGO_PAQUETE") + "'\n" + "AND TIPO_SERVICIO = 2\n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2");
/*      */ 
/*      */ 
/*      */                     
/* 1816 */                     if (verificaExistencia == null || !verificaExistencia.next()) {
/* 1817 */                       conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN, PLAN, CODIGO_PAQUETE, PAQUETE, RENTA, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 1818 */                           .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "','" + info2.getString("CODIGO_PAQUETE") + "','" + info2.getString("PAQUETE") + "','" + info2.getString("RENTA") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                     }
/* 1820 */                     conCDNREG.CerrarConsulta();
/* 1821 */                   } while (info2.next());
/*      */                 }
/* 1823 */                 conOPEN.CerrarConsulta();
/* 1824 */                 conCDNREG.Conectar();
/* 1825 */                 conCDNREG.Consultar("COMMIT");
/* 1826 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1834 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 1836 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 1837 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 1838 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 1841 */                 String mensaje = mensaje_parametrizado;
/* 1842 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 1846 */                     for (int j = 0; j < parametros.size(); j++) {
/* 1847 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 1849 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 2 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 1858 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 1859 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 1860 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 1861 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 1863 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 1864 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 1865 */                     mensaje = mensaje_parametrizado;
/* 1866 */                   } while (info3.next());
/*      */                 }
/* 1868 */                 conCDNREG.Consultar("COMMIT");
/* 1869 */                 conCDNREG.CerrarConsulta();
/* 1870 */                 conOPEN.CerrarConexion();
/*      */               } 
/* 1872 */               resultado = 1;
/* 1873 */             } catch (Exception exception) {}
/*      */           }
/*      */         
/*      */         }
/* 1877 */         else if (tipoCambio == 2) {
/* 1878 */           if (tipoMovimiento != 1)
/*      */           {
/* 1880 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1883 */         else if (tipoCambio == 3) {
/* 1884 */           if (tipoMovimiento != 1)
/*      */           {
/* 1886 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1889 */         else if (tipoCambio == 4) {
/* 1890 */           if (tipoMovimiento != 1)
/*      */           {
/* 1892 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1895 */         else if (tipoCambio == 5) {
/* 1896 */           if (tipoMovimiento != 1)
/*      */           {
/* 1898 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1901 */         else if (tipoCambio == 6) {
/* 1902 */           if (tipoMovimiento != 1)
/*      */           {
/* 1904 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1907 */         else if (tipoCambio == 7) {
/* 1908 */           if (tipoMovimiento != 1)
/*      */           {
/* 1910 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1913 */         else if (tipoCambio == 8) {
/* 1914 */           if (tipoMovimiento != 1)
/*      */           {
/* 1916 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 1919 */         else if (tipoCambio == 9 && 
/* 1920 */           tipoMovimiento != 1) {
/*      */           
/* 1922 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 1927 */     } else if (codigoPais == 506) {
/* 1928 */       if (tipoServicio == 1) {
/* 1929 */         if (tipoCambio == 1) {
/* 1930 */           if (tipoMovimiento == 1)
/*      */           {
/* 1932 */             ConexionOracle conBSCS = new ConexionOracle();
/* 1933 */             conBSCS.ip = "172.17.241.216";
/* 1934 */             conBSCS.port = "3871";
/* 1935 */             conBSCS.bd = "BSCSCRI";
/* 1936 */             conBSCS.usr = "READ";
/* 1937 */             conBSCS.pass = "READ";
/* 1938 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 1940 */               String mensaje_parametrizado = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1948 */               conCDNREG.Conectar();
/* 1949 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_CR_A_SIN_FRO_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1955 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 1956 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 1959 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 1960 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 1962 */               conCDNREG.CerrarConsulta();
/* 1963 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 1964 */                 conBSCS.Conectar();
/* 1965 */                 ResultSet info1 = conBSCS.Consultar("SELECT A.CO_ID,\n       B.CUSTOMER_ID,\n       B.PLCODE,\n       A.HISTNO,\n       B.TMCODE,\n       A.SNCODE,\n       TO_CHAR(A.ENTRY_DATE, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\nFROM SYSADM.PR_SERV_STATUS_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND A.STATUS = 'A'\nAND B.PLCODE IN(2011) \nAND A.SNCODE IN(1773,1686)\nAND A.ENTRY_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND TRUNC(A.ENTRY_DATE) != TRUNC(B.CO_ACTIVATED)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1980 */                 ResultSet info2 = null;
/* 1981 */                 ResultSet verificaExistencia = null;
/* 1982 */                 if (info1 != null && info1.next()) {
/*      */                   do
/*      */                   {
/*      */                     
/* 1986 */                     info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS," + "       TO_NUMBER(TO_CHAR(TO_DATE('" + info1
/* 1987 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'YYYYMM')) IDPERIODO,\n" + "       TO_NUMBER(TO_CHAR(TO_DATE('" + info1
/* 1988 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'YYYYMMDD')) IDCALENDARIO,\n" + "       B.CO_ID CONTRATO,\n" + "       G.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */                         
/* 1992 */                         .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       D.TMCODE CODIGO_PLAN,\n" + "       H.DES PLAN,\n" + "       B.SPCODE CODIGO_PAQUETE,\n" + "       I.DES PAQUETE,\n" + "       B.SNCODE CODIGO_SERVICIO,\n" + "       C.DES SERVICIO,\n" + "       TRIM(TO_CHAR(D.ACCESSFEE,'999999999.99')) RENTA,\n" + "       '" + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2000 */                         .getString("ENTRY_DATE") + "' ENTRY_DATE,\n" + "       TO_CHAR(TO_DATE('" + info1
/* 2001 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(TO_DATE('" + info1
/* 2002 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       1 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.PR_SERV_SPCODE_HIST B,\n" + "     SYSADM.MPUSNTAB C,\n" + "     SYSADM.MPULKTMB D,\n" + "     SYSADM.CONTR_SERVICES_CAP F,\n" + "     SYSADM.DIRECTORY_NUMBER G,\n" + "     SYSADM.RATEPLAN H,\n" + "     SYSADM.MPUSPTAB I,\n" + "     SYSADM.CCONTACT_ALL J,\n" + "     SYSADM.PROFILE_SERVICE K\n" + "WHERE B.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2016 */                         .getInt("CO_ID") + "\n" + "AND B.SNCODE = " + info1
/* 2017 */                         .getInt("SNCODE") + "\n" + "AND C.SNCODE = B.SNCODE\n" + "AND D.TMCODE = " + info1
/*      */                         
/* 2019 */                         .getInt("TMCODE") + "\n" + "AND D.SPCODE = B.SPCODE\n" + "AND D.SNCODE = B.SNCODE\n" + "AND D.ACCESSFEE > 0\n" + "AND F.CO_ID = B.CO_ID\n" + "AND F.CS_DEACTIV_DATE IS NULL\n" + "AND G.DN_ID = F.DN_ID\n" + "AND H.TMCODE = D.TMCODE\n" + "AND I.SPCODE = B.SPCODE\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2028 */                         .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "AND K.STATUS_HISTNO = " + info1
/*      */                         
/* 2030 */                         .getInt("HISTNO") + "\n" + "AND K.CO_ID = B.CO_ID\n" + "AND B.HISTNO = K.SPCODE_HISTNO\n" + "AND D.VSDATE = (SELECT MAX(E.VSDATE)\n" + "                FROM SYSADM.MPULKTMB E\n" + "                WHERE E.TMCODE = D.TMCODE\n" + "                AND E.SPCODE = B.SPCODE\n" + "                AND E.SNCODE = B.SNCODE\n" + "                AND E.VSDATE <= TO_DATE('" + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2038 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'))");
/* 2039 */                     if (info2 == null || !info2.next()) {
/*      */                       continue;
/*      */                     }
/*      */                     do {
/* 2043 */                       conCDNREG.Conectar();
/* 2044 */                       verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                           
/* 2046 */                           .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 2047 */                           .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 2048 */                           .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 2049 */                           .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 2050 */                           .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 2051 */                           .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 2052 */                           .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                       
/* 2056 */                       if (verificaExistencia == null || !verificaExistencia.next()) {
/* 2057 */                         conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN, PLAN, CODIGO_PAQUETE, PAQUETE, CODIGO_SERVICIO, SERVICIO, RENTA, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 2058 */                             .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "','" + info2.getString("CODIGO_PAQUETE") + "','" + info2.getString("PAQUETE") + "','" + info2.getString("CODIGO_SERVICIO") + "','" + info2.getString("SERVICIO") + "','" + info2.getString("RENTA") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                       }
/* 2060 */                       conCDNREG.CerrarConsulta();
/* 2061 */                     } while (info2.next());
/*      */                   }
/* 2063 */                   while (info1.next());
/*      */                 }
/* 2065 */                 conBSCS.CerrarConsulta();
/* 2066 */                 conCDNREG.Conectar();
/* 2067 */                 conCDNREG.Consultar("COMMIT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2085 */                 conCDNREG.Consultar("COMMIT");
/* 2086 */                 conCDNREG.CerrarConsulta();
/* 2087 */                 conCDNREG.Conectar();
/* 2088 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2096 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 2098 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 2099 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 2100 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 2103 */                 String mensaje = mensaje_parametrizado;
/* 2104 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 2108 */                     for (int j = 0; j < parametros.size(); j++) {
/* 2109 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 2111 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2120 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 2121 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 2122 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 2123 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 2125 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 2126 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 2127 */                     mensaje = mensaje_parametrizado;
/* 2128 */                   } while (info3.next());
/*      */                 }
/* 2130 */                 conCDNREG.Consultar("COMMIT");
/* 2131 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/* 2133 */               resultado = 1;
/* 2134 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/* 2137 */           else if (tipoMovimiento == 2)
/*      */           {
/* 2139 */             ConexionOracle conBSCS = new ConexionOracle();
/* 2140 */             conBSCS.ip = "172.17.241.216";
/* 2141 */             conBSCS.port = "3871";
/* 2142 */             conBSCS.bd = "BSCSCRI";
/* 2143 */             conBSCS.usr = "READ";
/* 2144 */             conBSCS.pass = "READ";
/* 2145 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 2147 */               String mensaje_parametrizado = "";
/* 2148 */               conCDNREG.Conectar();
/* 2149 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_CR_B_SIN_FRO_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2155 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 2156 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 2159 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 2160 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 2162 */               conCDNREG.CerrarConsulta();
/* 2163 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 2164 */                 conBSCS.Conectar();
/* 2165 */                 ResultSet info1 = conBSCS.Consultar("SELECT A.CO_ID,\n       B.CUSTOMER_ID,\n       B.PLCODE,\n       A.HISTNO,\n       B.TMCODE,\n       A.SNCODE,\n       TO_CHAR(A.ENTRY_DATE, 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE\nFROM SYSADM.PR_SERV_STATUS_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND A.STATUS = 'D'\nAND B.PLCODE IN(2011) \nAND A.SNCODE IN(1773,1686)\nAND A.ENTRY_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND TRUNC(A.ENTRY_DATE) != TRUNC(B.CO_ACTIVATED)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2180 */                 ResultSet info2 = null;
/* 2181 */                 ResultSet verificaExistencia = null;
/* 2182 */                 if (info1 != null && info1.next()) {
/*      */                   do
/*      */                   {
/*      */                     
/* 2186 */                     info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS," + "       TO_NUMBER(TO_CHAR(TO_DATE('" + info1
/* 2187 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'YYYYMM')) IDPERIODO,\n" + "       TO_NUMBER(TO_CHAR(TO_DATE('" + info1
/* 2188 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'YYYYMMDD')) IDCALENDARIO,\n" + "       B.CO_ID CONTRATO,\n" + "       G.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */                         
/* 2192 */                         .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       D.TMCODE CODIGO_PLAN,\n" + "       H.DES PLAN,\n" + "       B.SPCODE CODIGO_PAQUETE,\n" + "       I.DES PAQUETE,\n" + "       B.SNCODE CODIGO_SERVICIO,\n" + "       C.DES SERVICIO,\n" + "       TRIM(TO_CHAR(D.ACCESSFEE,'999999999.99')) RENTA,\n" + "       '" + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2200 */                         .getString("ENTRY_DATE") + "' ENTRY_DATE,\n" + "       TO_CHAR(TO_DATE('" + info1
/* 2201 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(TO_DATE('" + info1
/* 2202 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       1 TIPO_CAMBIO,\n" + "       2 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.PR_SERV_SPCODE_HIST B,\n" + "     SYSADM.MPUSNTAB C,\n" + "     SYSADM.MPULKTMB D,\n" + "     SYSADM.CONTR_SERVICES_CAP F,\n" + "     SYSADM.DIRECTORY_NUMBER G,\n" + "     SYSADM.RATEPLAN H,\n" + "     SYSADM.MPUSPTAB I,\n" + "     SYSADM.CCONTACT_ALL J,\n" + "     SYSADM.PROFILE_SERVICE K\n" + "WHERE B.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2216 */                         .getInt("CO_ID") + "\n" + "AND B.SNCODE = " + info1
/* 2217 */                         .getInt("SNCODE") + "\n" + "AND C.SNCODE = B.SNCODE\n" + "AND D.TMCODE = " + info1
/*      */                         
/* 2219 */                         .getInt("TMCODE") + "\n" + "AND D.SPCODE = B.SPCODE\n" + "AND D.SNCODE = B.SNCODE\n" + "AND D.ACCESSFEE > 0\n" + "AND F.CO_ID = B.CO_ID\n" + "AND F.CS_DEACTIV_DATE IS NULL\n" + "AND G.DN_ID = F.DN_ID\n" + "AND H.TMCODE = D.TMCODE\n" + "AND I.SPCODE = B.SPCODE\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2228 */                         .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "AND K.STATUS_HISTNO = " + info1
/*      */                         
/* 2230 */                         .getInt("HISTNO") + "\n" + "AND K.CO_ID = B.CO_ID\n" + "AND B.HISTNO = K.SPCODE_HISTNO\n" + "AND D.VSDATE = (SELECT MAX(E.VSDATE)\n" + "                FROM SYSADM.MPULKTMB E\n" + "                WHERE E.TMCODE = D.TMCODE\n" + "                AND E.SPCODE = B.SPCODE\n" + "                AND E.SNCODE = B.SNCODE\n" + "                AND E.VSDATE <= TO_DATE('" + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2238 */                         .getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'))");
/* 2239 */                     if (info2 == null || !info2.next()) {
/*      */                       continue;
/*      */                     }
/*      */                     do {
/* 2243 */                       conCDNREG.Conectar();
/* 2244 */                       verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                           
/* 2246 */                           .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 2247 */                           .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 2248 */                           .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 2249 */                           .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 2250 */                           .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 2251 */                           .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 2252 */                           .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2");
/*      */ 
/*      */ 
/*      */                       
/* 2256 */                       if (verificaExistencia == null || !verificaExistencia.next()) {
/* 2257 */                         conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN, PLAN, CODIGO_PAQUETE, PAQUETE, CODIGO_SERVICIO, SERVICIO, RENTA, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 2258 */                             .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "','" + info2.getString("CODIGO_PAQUETE") + "','" + info2.getString("PAQUETE") + "','" + info2.getString("CODIGO_SERVICIO") + "','" + info2.getString("SERVICIO") + "','" + info2.getString("RENTA") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                       }
/* 2260 */                       conCDNREG.CerrarConsulta();
/* 2261 */                     } while (info2.next());
/*      */                   }
/* 2263 */                   while (info1.next());
/*      */                 }
/* 2265 */                 conBSCS.CerrarConsulta();
/* 2266 */                 conCDNREG.Conectar();
/* 2267 */                 conCDNREG.Consultar("COMMIT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2285 */                 conCDNREG.Consultar("COMMIT");
/* 2286 */                 conCDNREG.CerrarConsulta();
/* 2287 */                 conCDNREG.Conectar();
/* 2288 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2296 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 2298 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 2299 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 2300 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 2303 */                 String mensaje = mensaje_parametrizado;
/* 2304 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 2308 */                     for (int j = 0; j < parametros.size(); j++) {
/* 2309 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 2311 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 1 \n" + "AND TIPO_MOVIMIENTO = 2 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2320 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 2321 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 2322 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 2323 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 2325 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 2326 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 2327 */                     mensaje = mensaje_parametrizado;
/* 2328 */                   } while (info3.next());
/*      */                 }
/* 2330 */                 conCDNREG.Consultar("COMMIT");
/* 2331 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/* 2333 */               resultado = 1;
/* 2334 */             } catch (Exception exception) {}
/*      */           }
/*      */         
/*      */         }
/* 2338 */         else if (tipoCambio == 2) {
/* 2339 */           if (tipoMovimiento == 1) {
/*      */             
/* 2341 */             ConexionOracle conBSCS = new ConexionOracle();
/* 2342 */             conBSCS.ip = "172.17.241.216";
/* 2343 */             conBSCS.port = "3871";
/* 2344 */             conBSCS.bd = "BSCSCRI";
/* 2345 */             conBSCS.usr = "READ";
/* 2346 */             conBSCS.pass = "READ";
/* 2347 */             ConexionOracle conCRMFUCR = new ConexionOracle();
/* 2348 */             conCRMFUCR.ip = "CEN-TLG-DBX";
/* 2349 */             conCRMFUCR.port = "3873";
/* 2350 */             conCRMFUCR.bd = "CR_GEVENUE";
/* 2351 */             conCRMFUCR.usr = "CDN_CRM_FU_CR";
/* 2352 */             conCRMFUCR.pass = "Gv833_cPioXl";
/* 2353 */             ConexionOracle conCDNREG = new ConexionOracle();
/*      */             try {
/* 2355 */               String mensaje_parametrizado = "";
/* 2356 */               conCDNREG.Conectar();
/* 2357 */               ResultSet mensaje_parametrizadoRS = conCDNREG.Consultar("SELECT (CASE WHEN ESTADO = 1 AND TRIM(VALOR) IS NOT NULL THEN TRIM(VALOR)\n             ELSE 'DESACTIVADO'\n        END) MENSAJE\nFROM CDNRG.CDN_PARAMETRO\nWHERE NOMBRE = 'MRM_CR_A_CAM_PLA_SP'\nAND ROWNUM = 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2363 */               if (mensaje_parametrizadoRS == null || !mensaje_parametrizadoRS.next()) {
/* 2364 */                 mensaje_parametrizado = "DESACTIVADO";
/*      */               } else {
/*      */                 do {
/* 2367 */                   mensaje_parametrizado = mensaje_parametrizadoRS.getString("MENSAJE");
/* 2368 */                 } while (mensaje_parametrizadoRS.next());
/*      */               } 
/* 2370 */               conCDNREG.CerrarConsulta();
/* 2371 */               if (!mensaje_parametrizado.equals("") && !mensaje_parametrizado.equals("DESACTIVADO")) {
/* 2372 */                 conBSCS.Conectar();
/* 2373 */                 HashMap<String, ResultSet> resultSetLists = new HashMap<>();
/* 2374 */                 ResultSet infoBSCS = conBSCS.Consultar("SELECT A.CO_ID,\n       B.PLCODE,\n       B.CUSTOMER_ID\nFROM SYSADM.RATEPLAN_HIST A,\n     SYSADM.CONTRACT_ALL B\nWHERE B.CO_ID = A.CO_ID\nAND B.PLCODE IN(2011)\nAND A.TMCODE_DATE BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nAND A.SEQNO > 1");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2383 */                 resultSetLists.put("BSCS", infoBSCS);
/* 2384 */                 conCRMFUCR.Conectar();
/* 2385 */                 ResultSet infoCRMFUCR = conCRMFUCR.Consultar("SELECT A.CONTRACT_ID CO_ID,\n       2011 PLCODE,\n       H.CUSTOMER_ID_EXTERNAL CUSTOMER_ID\nFROM CRM_AMX_CENAM_CR_FU.TBL_POST_SALE_ORDER A\nINNER JOIN CRM_AMX_CENAM_CR_FU.TBL_POST_SALE_ORDER_TYPE B ON B.POST_SALE_ORDER_TYPE_ID = A.POST_SALE_ORDER_TYPE_ID\nINNER JOIN CRM_AMX_CENAM_CR_FU.TBL_FU_RENEWAL_ORDER C ON C.CUSTOMER_ID = A.CUSTOMER_ID AND C.FU_RENEWAL_ORDER_ID = A.EXTERNAL_ENTITY_ID \nINNER JOIN CRM_AMX_CENAM_CR_FU.TBL_FU_RENEWAL_ORDER_ITEM D ON D.FU_RENEWAL_ORDER_ID = C.FU_RENEWAL_ORDER_ID\nINNER JOIN CRM_AMX_CENAM_CR_FU.TBL_QT_QUOTES F ON F.QT_QUOTE_ID = D.QT_QUOTE_ID\nINNER JOIN CRM_AMX_CENAM_CR_FU.TBL_QT_QUOTE_OFFERS G ON G.QT_QUOTE_ID = F.QT_QUOTE_ID\nINNER JOIN CRM_AMX_CENAM_CR_FU.TBL_FU_SUBSCRIPTIONS H ON H.SUBSCRIPTION_ID_EXTERNAL = A.CONTRACT_ID AND H.CUSTOMER_ID_INTERNAL = A.CUSTOMER_ID AND H.SUBSCRIPTION_ID = D.FU_SUBSCRIPTION_ID\nWHERE B.CODE = 'REN'\nAND A.CANCELATION_REASON_ID IS NULL\nAND A.CREATION_DATE_TIME BETWEEN SYSDATE - 900 / (24*60*60) AND SYSDATE\nGROUP BY A.CONTRACT_ID, H.CUSTOMER_ID_EXTERNAL");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2399 */                 resultSetLists.put("CRMFU", infoCRMFUCR);
/* 2400 */                 ResultSet info1 = null;
/* 2401 */                 ResultSet info2 = null;
/* 2402 */                 ResultSet verificaExistencia = null;
/* 2403 */                 label1189: for (String str : resultSetLists.keySet()) {
/* 2404 */                   info1 = resultSetLists.get(str);
/* 2405 */                   if (info1 == null || !info1.next()) {
/*      */                     continue;
/*      */                   }
/*      */                   while (true) {
/* 2409 */                     if (str.equals("BSCS")) {
/* 2410 */                       info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS,\n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMM')) IDPERIODO, \n" + "       TO_NUMBER(TO_CHAR(MAX(A.TMCODE_DATE), 'YYYYMMDD')) IDCALENDARIO, \n" + "       A.CO_ID CONTRATO, \n" + "       I.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2416 */                           .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN_ANTERIOR,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                         FROM SYSADM.RATEPLAN_HIST D\n" + "                                         WHERE D.CO_ID = C.CO_ID))) PLAN_ANTERIOR,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID)) CODIGO_PLAN,\n" + "       (SELECT E.DES\n" + "        FROM SYSADM.RATEPLAN E\n" + "        WHERE E.TMCODE = (SELECT C.TMCODE\n" + "                          FROM SYSADM.RATEPLAN_HIST C\n" + "                          WHERE C.CO_ID = A.CO_ID\n" + "                          AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                          FROM SYSADM.RATEPLAN_HIST D\n" + "                          WHERE D.CO_ID = C.CO_ID))) PLAN,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(MAX(A.TMCODE_DATE), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       2 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.RATEPLAN_HIST A,\n" + "     SYSADM.CONTR_SERVICES_CAP H,\n" + "     SYSADM.DIRECTORY_NUMBER I,\n" + "     SYSADM.CCONTACT_ALL J\n" + "WHERE A.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2456 */                           .getInt("CO_ID") + "\n" + "AND H.CO_ID = " + info1
/* 2457 */                           .getInt("CO_ID") + "\n" + "AND H.CS_DEACTIV_DATE IS NULL\n" + "AND I.DN_ID = H.DN_ID\n" + "AND J.CUSTOMER_ID = " + info1
/*      */ 
/*      */                           
/* 2460 */                           .getInt("CUSTOMER_ID") + "\n" + "AND J.CCSEQ = 1\n" + "GROUP BY A.CO_ID, I.DN_NUM, TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME))");
/*      */                     }
/*      */                     else {
/*      */                       
/* 2464 */                       info2 = conBSCS.Consultar("SELECT " + codigoPais + " IDPAIS,\n" + "       TO_NUMBER(TO_CHAR(MAX(K.CO_MODDATE), 'YYYYMM')) IDPERIODO, \n" + "       TO_NUMBER(TO_CHAR(MAX(K.CO_MODDATE), 'YYYYMMDD')) IDCALENDARIO, \n" + "       A.CO_ID CONTRATO, \n" + "       I.DN_NUM TELEFONO,\n" + "       TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME)) NOMBRE,\n" + "       " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2470 */                           .getInt("PLCODE") + " CODIGO_PAIS,\n" + "       NVL(TRIM((SELECT C.TMCODE||'-'||CC.SPCODE\n" + "                 FROM SYSADM.RATEPLAN_HIST C,\n" + "                      SYSADM.PR_SERV_SPCODE_HIST CC,\n" + "                      SYSADM.MPUSNTAB CCC\n" + "                 WHERE C.CO_ID = A.CO_ID\n" + "                 AND CC.CO_ID = C.CO_ID\n" + "                 AND CCC.SNCODE = CC.SNCODE\n" + "                 AND CCC.SHDES = 'TELEF'\n" + "                 AND TRUNC(C.TMCODE_DATE) = TRUNC(CC.VALID_FROM_DATE)\n" + "                 AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                FROM SYSADM.RATEPLAN_HIST D\n" + "                                WHERE D.CO_ID = C.CO_ID))), NVL(TRIM((SELECT Z.TMCODE||'-'||R.SPCODE\n" + "                                                             FROM SYSADM.PR_SERV_SPCODE_HIST Q,\n" + "                                                                  SYSADM.MPUSPTAB R,\n" + "                                                                  SYSADM.MPUSNTAB S,\n" + "                                                                  SYSADM.RATEPLAN_HIST Z\n" + "                                                             WHERE R.SPCODE = Q.SPCODE\n" + "                                                             AND S.SNCODE = Q.SNCODE\n" + "                                                             AND S.SHDES = 'TELEF'\n" + "                                                             AND Z.CO_ID = Q.CO_ID\n" + "                                                             AND TRUNC(Z.TMCODE_DATE) = TRUNC(Q.VALID_FROM_DATE)\n" + "                                                             AND Z.TMCODE_DATE = (SELECT MAX(ZZ.TMCODE_DATE)\n" + "                                                                                  FROM SYSADM.RATEPLAN_HIST ZZ\n" + "                                                                                  WHERE ZZ.CO_ID = Z.CO_ID\n" + "                                                                                  AND TRUNC(ZZ.TMCODE_DATE) = TRUNC(Z.TMCODE_DATE))\n" + "                                                             AND Q.CO_ID = A.CO_ID\n" + "                                                             AND NOT EXISTS (SELECT 123\n" + "                                                                             FROM SYSADM.PR_SERV_STATUS_HIST T,\n" + "                                                                                  SYSADM.PR_SERV_SPCODE_HIST U,\n" + "                                                                                  SYSADM.PROFILE_SERVICE V,\n" + "                                                                                  SYSADM.MPUSNTAB W,\n" + "                                                                                  SYSADM.MPUSPTAB X\n" + "                                                                             WHERE T.STATUS = 'A'\n" + "                                                                             AND U.CO_ID = T.CO_ID\n" + "                                                                             AND U.SNCODE = T.SNCODE\n" + "                                                                             AND V.CO_ID = T.CO_ID\n" + "                                                                             AND V.STATUS_HISTNO = T.HISTNO\n" + "                                                                             AND U.HISTNO = V.SPCODE_HISTNO\n" + "                                                                             AND W.SNCODE = T.SNCODE\n" + "                                                                             AND W.SHDES = 'TELEF'\n" + "                                                                             AND X.SPCODE = U.SPCODE\n" + "                                                                             AND T.CO_ID = Q.CO_ID\n" + "                                                                             AND U.SPCODE = R.SPCODE\n" + "                                                                             AND ROWNUM = 1)\n" + "                                                             AND ROWNUM = 1)),(SELECT Z.TMCODE||'-'||R.SPCODE\n" + "                                                                               FROM SYSADM.PR_SERV_SPCODE_HIST Q,\n" + "                                                                                    SYSADM.MPUSPTAB R,\n" + "                                                                                    SYSADM.MPUSNTAB S,\n" + "                                                                                    SYSADM.RATEPLAN_HIST Z\n" + "                                                                               WHERE R.SPCODE = Q.SPCODE\n" + "                                                                               AND S.SNCODE = Q.SNCODE\n" + "                                                                               AND S.SHDES = 'TELEF'\n" + "                                                                               AND Z.CO_ID = Q.CO_ID\n" + "                                                                               AND TRUNC(Z.TMCODE_DATE) = TRUNC(Q.VALID_FROM_DATE)\n" + "                                                                               AND Z.TMCODE_DATE = (SELECT MAX(ZZ.TMCODE_DATE)\n" + "                                                                                                    FROM SYSADM.RATEPLAN_HIST ZZ\n" + "                                                                                                    WHERE ZZ.CO_ID = Z.CO_ID\n" + "                                                                                                    AND TRUNC(ZZ.TMCODE_DATE) = TRUNC(Z.TMCODE_DATE))\n" + "                                                                               AND Q.CO_ID = A.CO_ID\n" + "                                                                               AND ROWNUM = 1))) CODIGO_PLAN_ANTERIOR,\n" + "       NVL(TRIM((SELECT CCCC.DES\n" + "                 FROM SYSADM.RATEPLAN_HIST C,\n" + "                      SYSADM.PR_SERV_SPCODE_HIST CC,\n" + "                      SYSADM.MPUSNTAB CCC,\n" + "                      SYSADM.MPUSPTAB CCCC\n" + "                 WHERE C.CO_ID = A.CO_ID\n" + "                 AND CC.CO_ID = C.CO_ID\n" + "                 AND CCC.SNCODE = CC.SNCODE\n" + "                 AND CCC.SHDES = 'TELEF'\n" + "                 AND CC.SPCODE = CCCC.SPCODE\n" + "                 AND TRUNC(C.TMCODE_DATE) = TRUNC(CC.VALID_FROM_DATE)\n" + "                 AND C.SEQNO = (SELECT MAX(D.SEQNO) - 1\n" + "                                FROM SYSADM.RATEPLAN_HIST D\n" + "                                WHERE D.CO_ID = C.CO_ID))), (SELECT R.DES\n" + "                                                             FROM SYSADM.PR_SERV_SPCODE_HIST Q,\n" + "                                                                  SYSADM.MPUSPTAB R,\n" + "                                                                  SYSADM.MPUSNTAB S\n" + "                                                             WHERE R.SPCODE = Q.SPCODE\n" + "                                                             AND S.SNCODE = Q.SNCODE\n" + "                                                             AND S.SHDES = 'TELEF'\n" + "                                                             AND Q.CO_ID = 1339740\n" + "                                                             AND NOT EXISTS (SELECT 123\n" + "                                                                             FROM SYSADM.PR_SERV_STATUS_HIST T,\n" + "                                                                                  SYSADM.PR_SERV_SPCODE_HIST U,\n" + "                                                                                  SYSADM.PROFILE_SERVICE V,\n" + "                                                                                  SYSADM.MPUSNTAB W,\n" + "                                                                                  SYSADM.MPUSPTAB X\n" + "                                                                             WHERE T.STATUS = 'A'\n" + "                                                                             AND U.CO_ID = T.CO_ID\n" + "                                                                             AND U.SNCODE = T.SNCODE\n" + "                                                                             AND V.CO_ID = T.CO_ID\n" + "                                                                             AND V.STATUS_HISTNO = T.HISTNO\n" + "                                                                             AND U.HISTNO = V.SPCODE_HISTNO\n" + "                                                                             AND W.SNCODE = T.SNCODE\n" + "                                                                             AND W.SHDES = 'TELEF'\n" + "                                                                             AND X.SPCODE = U.SPCODE\n" + "                                                                             AND T.CO_ID = Q.CO_ID\n" + "                                                                             AND U.SPCODE = R.SPCODE\n" + "                                                                             AND ROWNUM = 1)\n" + "                                                             AND ROWNUM = 1)) PLAN_ANTERIOR,\n" + "       (SELECT C.TMCODE\n" + "        FROM SYSADM.RATEPLAN_HIST C\n" + "        WHERE C.CO_ID = A.CO_ID\n" + "        AND C.SEQNO = (SELECT MAX(D.SEQNO)\n" + "                       FROM SYSADM.RATEPLAN_HIST D\n" + "                       WHERE D.CO_ID = C.CO_ID))||'-'||(SELECT P.SPCODE\n" + "                                                        FROM SYSADM.PR_SERV_STATUS_HIST L,\n" + "                                                             SYSADM.PR_SERV_SPCODE_HIST M,\n" + "                                                             SYSADM.PROFILE_SERVICE N,\n" + "                                                             SYSADM.MPUSNTAB O,\n" + "                                                             SYSADM.MPUSPTAB P\n" + "                                                        WHERE L.STATUS = 'A'\n" + "                                                        AND M.CO_ID = L.CO_ID\n" + "                                                        AND M.SNCODE = L.SNCODE\n" + "                                                        AND N.CO_ID = L.CO_ID\n" + "                                                        AND N.STATUS_HISTNO = L.HISTNO\n" + "                                                        AND M.HISTNO = N.SPCODE_HISTNO\n" + "                                                        AND O.SNCODE = L.SNCODE\n" + "                                                        AND O.SHDES = 'TELEF'\n" + "                                                        AND P.SPCODE = M.SPCODE\n" + "                                                        AND L.CO_ID = A.CO_ID\n" + "                                                        AND ROWNUM = 1) CODIGO_PLAN,\n" + "       (SELECT P.DES\n" + "        FROM SYSADM.PR_SERV_STATUS_HIST L,\n" + "             SYSADM.PR_SERV_SPCODE_HIST M,\n" + "             SYSADM.PROFILE_SERVICE N,\n" + "             SYSADM.MPUSNTAB O,\n" + "             SYSADM.MPUSPTAB P\n" + "        WHERE L.STATUS = 'A'\n" + "        AND M.CO_ID = L.CO_ID\n" + "        AND M.SNCODE = L.SNCODE\n" + "        AND N.CO_ID = L.CO_ID\n" + "        AND N.STATUS_HISTNO = L.HISTNO\n" + "        AND M.HISTNO = N.SPCODE_HISTNO\n" + "        AND O.SNCODE = L.SNCODE\n" + "        AND O.SHDES = 'TELEF'\n" + "        AND P.SPCODE = M.SPCODE\n" + "        AND L.CO_ID = A.CO_ID\n" + "        AND ROWNUM = 1) PLAN,\n" + "       TO_CHAR(MAX(K.CO_MODDATE), 'DD/MM/YYYY HH12:MI:SS AM') ENTRY_DATE,\n" + "       TO_CHAR(MAX(K.CO_MODDATE), 'DD/MM/YYYY') FECHA_EFECTIVA,\n" + "       TO_CHAR(MAX(K.CO_MODDATE), 'HH24:MI:SS') HORA_EFECTIVA,\n" + "       1 TIPO_SERVICIO,\n" + "       2 TIPO_CAMBIO,\n" + "       1 TIPO_MOVIMIENTO,\n" + "       0 ESTATUS_ENVIO\n" + "FROM SYSADM.RATEPLAN_HIST A,\n" + "     SYSADM.CONTR_SERVICES_CAP H,\n" + "     SYSADM.DIRECTORY_NUMBER I,\n" + "     SYSADM.CCONTACT_ALL J,\n" + "     SYSADM.CONTRACT_ALL K        \n" + "WHERE A.CO_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2622 */                           .getInt("CO_ID") + "\n" + "AND H.CO_ID = A.CO_ID\n" + "AND H.CS_DEACTIV_DATE IS NULL\n" + "AND I.DN_ID = H.DN_ID\n" + "AND K.CO_ID = A.CO_ID\n" + "AND K.CUSTOMER_ID = " + info1
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 2627 */                           .getInt("CUSTOMER_ID") + "\n" + "AND J.CUSTOMER_ID = K.CUSTOMER_ID\n" + "AND J.CCSEQ = 1\n" + "GROUP BY A.CO_ID, I.DN_NUM, TRIM(TRIM(J.CCFNAME) || ' ' || TRIM(J.CCLNAME))\n" + "HAVING MAX(K.CO_MODDATE) BETWEEN SYSDATE - (900 * 2) / (24*60*60) AND SYSDATE");
/*      */                     } 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2633 */                     if (info2 != null && info2.next()) {
/*      */                       
/*      */                       do {
/*      */                         
/* 2637 */                         conCDNREG.Conectar();
/* 2638 */                         verificaExistencia = conCDNREG.Consultar("SELECT 123\nFROM CDNRG.MODIFICACION_RENTA_REG\nWHERE IDPAIS = " + info2
/*      */                             
/* 2640 */                             .getInt("IDPAIS") + "\n" + "AND IDPERIODO = " + info2
/* 2641 */                             .getInt("IDPERIODO") + "\n" + "AND IDCALENDARIO = " + info2
/* 2642 */                             .getInt("IDCALENDARIO") + "\n" + "AND CONTRATO = " + info2
/* 2643 */                             .getInt("CONTRATO") + "\n" + "AND CODIGO_PAIS = " + info2
/* 2644 */                             .getInt("CODIGO_PAIS") + "\n" + "AND FECHA_EFECTIVA = '" + info2
/* 2645 */                             .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info2
/* 2646 */                             .getString("HORA_EFECTIVA") + "'\n" + "AND TIPO_SERVICIO = 1\n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1");
/*      */ 
/*      */ 
/*      */                         
/* 2650 */                         if (verificaExistencia == null || !verificaExistencia.next()) {
/* 2651 */                           conCDNREG.Consultar("INSERT INTO CDNRG.MODIFICACION_RENTA_REG (IDPAIS, IDPERIODO, IDCALENDARIO, CONTRATO, TELEFONO, NOMBRE, CODIGO_PAIS, CODIGO_PLAN_ANTERIOR, PLAN_ANTERIOR, CODIGO_PLAN, PLAN, ENTRY_DATE, FECHA_EFECTIVA, HORA_EFECTIVA, TIPO_SERVICIO, TIPO_CAMBIO, TIPO_MOVIMIENTO, ESTATUS_ENVIO)\nVALUES (" + info2
/* 2652 */                               .getInt("IDPAIS") + "," + info2.getInt("IDPERIODO") + "," + info2.getInt("IDCALENDARIO") + "," + info2.getInt("CONTRATO") + ",'" + info2.getString("TELEFONO") + "','" + info2.getString("NOMBRE") + "'," + info2.getInt("CODIGO_PAIS") + ",'" + info2.getString("CODIGO_PLAN_ANTERIOR") + "','" + info2.getString("PLAN_ANTERIOR") + "','" + info2.getString("CODIGO_PLAN") + "','" + info2.getString("PLAN") + "',TO_DATE('" + info2.getString("ENTRY_DATE") + "', 'DD/MM/YYYY HH12:MI:SS AM'),'" + info2.getString("FECHA_EFECTIVA") + "','" + info2.getString("HORA_EFECTIVA") + "'," + info2.getInt("TIPO_SERVICIO") + "," + info2.getInt("TIPO_CAMBIO") + "," + info2.getInt("TIPO_MOVIMIENTO") + "," + info2.getInt("ESTATUS_ENVIO") + ")");
/*      */                         }
/* 2654 */                         conCDNREG.CerrarConsulta();
/* 2655 */                       } while (info2.next());
/*      */                     }
/* 2657 */                     if (!info1.next())
/*      */                       continue label1189; 
/*      */                   } 
/* 2660 */                 }  conBSCS.CerrarConsulta();
/* 2661 */                 conCRMFUCR.CerrarConsulta();
/* 2662 */                 conCDNREG.Conectar();
/* 2663 */                 conCDNREG.Consultar("COMMIT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2681 */                 conCDNREG.Consultar("COMMIT");
/* 2682 */                 conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG A\nSET A.PLAN = (SELECT B.DESCRIPCION\n              FROM CDNRG.CDN_CATALOGO_MAIL B,\n                   CDNRG.CDN_DETALLE_MAIL C,\n                   CDNRG.CDN_PAIS D\n              WHERE D.CODIGO = A.IDPAIS \n              AND D.ID = B.ID_PAIS\n              AND B.ID_DETALLE = C.ID_DETALLE\n              AND C.CODIGO = 'Nombre Plan'\n              AND TRIM(A.CODIGO_PLAN) = TRIM(B.CODIGO))\nWHERE A.IDPAIS = 506\nAND A.TIPO_SERVICIO = 1\nAND A.TIPO_CAMBIO = 2\nAND A.TIPO_MOVIMIENTO = 1\nAND A.ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2697 */                 conCDNREG.Consultar("COMMIT");
/*      */                 
/* 2699 */                 conCDNREG.CerrarConsulta();
/* 2700 */                 conCDNREG.Conectar();
/* 2701 */                 ResultSet info3 = conCDNREG.Consultar("SELECT * \nFROM CDNRG.MODIFICACION_RENTA_REG \nWHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2709 */                 LinkedList<String> parametros = new LinkedList<>();
/*      */                 
/* 2711 */                 for (int i = 0; i < mensaje_parametrizado.length(); i++) {
/* 2712 */                   if (mensaje_parametrizado.charAt(i) == '{') {
/* 2713 */                     parametros.add(mensaje_parametrizado.substring(i + 1, mensaje_parametrizado.indexOf("}", i)));
/*      */                   }
/*      */                 } 
/* 2716 */                 String mensaje = mensaje_parametrizado;
/* 2717 */                 if (info3 != null && info3.next()) {
/*      */                   
/*      */                   do {
/*      */                     
/* 2721 */                     for (int j = 0; j < parametros.size(); j++) {
/* 2722 */                       mensaje = mensaje.replace("{" + (String)parametros.get(j) + "}", info3.getString(parametros.get(j)));
/*      */                     }
/* 2724 */                     conCDNREG.Consultar("UPDATE CDNRG.MODIFICACION_RENTA_REG\nSET MENSAJE_FINAL = '" + mensaje + "',\n" + "    FECHA_ENVIO = SYSDATE,\n" + "    ESTATUS_ENVIO = 1\n" + "WHERE IDPAIS = " + codigoPais + "\n" + "AND TIPO_SERVICIO = 1 \n" + "AND TIPO_CAMBIO = 2 \n" + "AND TIPO_MOVIMIENTO = 1 \n" + "AND ESTATUS_ENVIO = 0\n" + "AND CONTRATO = " + info3
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2733 */                         .getInt("CONTRATO") + "\n" + "AND TELEFONO = '" + info3
/* 2734 */                         .getString("TELEFONO") + "'\n" + "AND FECHA_EFECTIVA = '" + info3
/* 2735 */                         .getString("FECHA_EFECTIVA") + "'\n" + "AND HORA_EFECTIVA = '" + info3
/* 2736 */                         .getString("HORA_EFECTIVA") + "'");
/*      */                     
/* 2738 */                     conCDNREG.Consultar("INSERT INTO CDNRG.TB_SMS (ORIGEN, DESTINO, MSG, STATUS, DATE_SENDED, SISTEMA, PRIORIDAD, DATE_REGISTERED, QUEUE, PAIS_ID, A_DEMANDA)\nVALUES ('CLARO','" + info3
/* 2739 */                         .getString("TELEFONO") + "','" + mensaje + "','P', SYSDATE,'R',10,SYSDATE,1," + codigoPais + ",1)");
/* 2740 */                     mensaje = mensaje_parametrizado;
/* 2741 */                   } while (info3.next());
/*      */                 }
/* 2743 */                 conCDNREG.Consultar("COMMIT");
/* 2744 */                 conCDNREG.CerrarConsulta();
/*      */               } 
/* 2746 */               resultado = 1;
/* 2747 */             } catch (Exception exception) {}
/*      */           
/*      */           }
/* 2750 */           else if (tipoMovimiento == 2) {
/*      */           
/*      */           } 
/* 2753 */         } else if (tipoCambio == 3) {
/* 2754 */           if (tipoMovimiento != 1)
/*      */           {
/* 2756 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2759 */         else if (tipoCambio == 4) {
/* 2760 */           if (tipoMovimiento != 1)
/*      */           {
/* 2762 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2765 */         else if (tipoCambio == 5) {
/* 2766 */           if (tipoMovimiento != 1)
/*      */           {
/* 2768 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2771 */         else if (tipoCambio == 6) {
/* 2772 */           if (tipoMovimiento != 1)
/*      */           {
/* 2774 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2777 */         else if (tipoCambio == 7) {
/* 2778 */           if (tipoMovimiento != 1)
/*      */           {
/* 2780 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2783 */         else if (tipoCambio == 8) {
/* 2784 */           if (tipoMovimiento != 1)
/*      */           {
/* 2786 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2789 */         else if (tipoCambio == 9 && 
/* 2790 */           tipoMovimiento != 1) {
/*      */           
/* 2792 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/* 2796 */       else if (tipoServicio == 2) {
/* 2797 */         if (tipoCambio == 1) {
/* 2798 */           if (tipoMovimiento != 1)
/*      */           {
/* 2800 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2803 */         else if (tipoCambio == 2) {
/* 2804 */           if (tipoMovimiento != 1)
/*      */           {
/* 2806 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2809 */         else if (tipoCambio == 3) {
/* 2810 */           if (tipoMovimiento != 1)
/*      */           {
/* 2812 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2815 */         else if (tipoCambio == 4) {
/* 2816 */           if (tipoMovimiento != 1)
/*      */           {
/* 2818 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2821 */         else if (tipoCambio == 5) {
/* 2822 */           if (tipoMovimiento != 1)
/*      */           {
/* 2824 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2827 */         else if (tipoCambio == 6) {
/* 2828 */           if (tipoMovimiento != 1)
/*      */           {
/* 2830 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2833 */         else if (tipoCambio == 7) {
/* 2834 */           if (tipoMovimiento != 1)
/*      */           {
/* 2836 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2839 */         else if (tipoCambio == 8) {
/* 2840 */           if (tipoMovimiento != 1)
/*      */           {
/* 2842 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2845 */         else if (tipoCambio == 9 && 
/* 2846 */           tipoMovimiento != 1) {
/*      */           
/* 2848 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 2853 */     } else if (codigoPais == 507) {
/* 2854 */       if (tipoServicio == 1) {
/* 2855 */         if (tipoCambio == 1) {
/* 2856 */           if (tipoMovimiento != 1)
/*      */           {
/* 2858 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2861 */         else if (tipoCambio == 2) {
/* 2862 */           if (tipoMovimiento != 1)
/*      */           {
/* 2864 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2867 */         else if (tipoCambio == 3) {
/* 2868 */           if (tipoMovimiento != 1)
/*      */           {
/* 2870 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2873 */         else if (tipoCambio == 4) {
/* 2874 */           if (tipoMovimiento != 1)
/*      */           {
/* 2876 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2879 */         else if (tipoCambio == 5) {
/* 2880 */           if (tipoMovimiento != 1)
/*      */           {
/* 2882 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2885 */         else if (tipoCambio == 6) {
/* 2886 */           if (tipoMovimiento != 1)
/*      */           {
/* 2888 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2891 */         else if (tipoCambio == 7) {
/* 2892 */           if (tipoMovimiento != 1)
/*      */           {
/* 2894 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2897 */         else if (tipoCambio == 8) {
/* 2898 */           if (tipoMovimiento != 1)
/*      */           {
/* 2900 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2903 */         else if (tipoCambio == 9 && 
/* 2904 */           tipoMovimiento != 1) {
/*      */           
/* 2906 */           if (tipoMovimiento == 2);
/*      */         }
/*      */       
/*      */       }
/* 2910 */       else if (tipoServicio == 2) {
/* 2911 */         if (tipoCambio == 1) {
/* 2912 */           if (tipoMovimiento != 1)
/*      */           {
/* 2914 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2917 */         else if (tipoCambio == 2) {
/* 2918 */           if (tipoMovimiento != 1)
/*      */           {
/* 2920 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2923 */         else if (tipoCambio == 3) {
/* 2924 */           if (tipoMovimiento != 1)
/*      */           {
/* 2926 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2929 */         else if (tipoCambio == 4) {
/* 2930 */           if (tipoMovimiento != 1)
/*      */           {
/* 2932 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2935 */         else if (tipoCambio == 5) {
/* 2936 */           if (tipoMovimiento != 1)
/*      */           {
/* 2938 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2941 */         else if (tipoCambio == 6) {
/* 2942 */           if (tipoMovimiento != 1)
/*      */           {
/* 2944 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2947 */         else if (tipoCambio == 7) {
/* 2948 */           if (tipoMovimiento != 1)
/*      */           {
/* 2950 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2953 */         else if (tipoCambio == 8) {
/* 2954 */           if (tipoMovimiento != 1)
/*      */           {
/* 2956 */             if (tipoMovimiento == 2);
/*      */           }
/*      */         }
/* 2959 */         else if (tipoCambio == 9 && 
/* 2960 */           tipoMovimiento != 1) {
/*      */           
/* 2962 */           if (tipoMovimiento == 2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2968 */     System.out.println("Termino ws...");
/* 2969 */     return "{RESPONSE:" + resultado + "}";
/*      */   }
/*      */   
/*      */   @PUT
/*      */   @Consumes({"application/json"})
/*      */   public void putJson(String content) {}
/*      */ }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\ModificacionRentaResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */