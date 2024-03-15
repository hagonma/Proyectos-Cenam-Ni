 package Model;
 
 import Controller.ConexionAS400;
 import Controller.ConexionOracle;
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.net.HttpURLConnection;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.text.DecimalFormat;
 import java.util.Calendar;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 
 
 
 
 
 public class ConsultasPisa
 {
/*  27 */   public ConexionAS400 con = new ConexionAS400();
/*  28 */   public ConexionOracle conO = new ConexionOracle();
   public ResultSet rs;
   
   public ResultSet CorreoCliente(String contrato) throws SQLException, Exception {
/*  32 */     String dato = "";
/*  33 */     String equipos = "";
     
     try {
/*  36 */       this.con.Conectar();
/*  37 */       String query = "SELECT * FROM GUARDBV1.SVRIMAIL WHERE SVIMTSER||DIGITS(SVIMTLIN)=" + contrato + " AND SVIMSEC=0";
/*  38 */       ResultSet resultado = this.con.Consultar(query);
/*  39 */       while (resultado.next()) {
/*  40 */         dato = resultado.getString("SVIMTCEL");
       }
/*  42 */       if (dato.isEmpty()) {
/*  43 */         equipos = "No tiene correo";
       } else {
/*  45 */         equipos = dato;
       } 
       
/*  48 */       String update = "UPDATE CDN_MAIL SET CORREO_CLIENTE = LOWER('" + equipos + "') WHERE ID=" + contrato;
/*  49 */       this.con.CerrarConsulta();
       
/*  51 */       this.conO.Conectar();
/*  52 */       this.conO.Consultar(update);
/*  53 */       this.conO.CerrarConsulta();
/*  54 */       return this.rs;
/*  55 */     } catch (SQLException e) {
/*  56 */       this.con.CerrarConsulta();
/*  57 */       return null;
     } 
   }
   
   public ResultSet EquipoFinanciado(String contrato) throws SQLException, Exception {
/*  62 */     String dato = "";
/*  63 */     String equipos = "";
     
     try {
/*  66 */       this.con.Conectar();
/*  67 */       String query = "SELECT * FROM GUAV1.SVGTINFPRE WHERE SVINBEXC||DIGITS(SVINBLNÑ)=" + contrato;
/*  68 */       ResultSet resultado = this.con.Consultar(query);
/*  69 */       while (resultado.next()) {
/*  70 */         dato = dato + resultado.getString("SVINMADEL") + ",";
       }
/*  72 */       if (dato.isEmpty()) {
/*  73 */         equipos = "No tiene financiamiento";
       } else {
/*  75 */         int cantidad = 1;
/*  76 */         equipos = dato.substring(0, dato.length() - cantidad);
       } 
       
/*  79 */       String update = "UPDATE CDN_MAIL SET EQUIPO = '" + equipos + "' WHERE ID=" + contrato;
/*  80 */       this.con.CerrarConsulta();
       
/*  82 */       this.conO.Conectar();
/*  83 */       this.conO.Consultar(update);
/*  84 */       this.conO.CerrarConsulta();
/*  85 */       return this.rs;
/*  86 */     } catch (SQLException e) {
/*  87 */       this.con.CerrarConsulta();
/*  88 */       return null;
     } 
   }
   
   public ResultSet VigenciaContrato(String contrato) throws SQLException, Exception {
/*  93 */     String dato = "";
/*  94 */     String equipos = "";
     
     try {
/*  97 */       this.con.Conectar();
/*  98 */       String query = "SELECT * FROM GUAV1.SVGTRETEL2 WHERE RETTELEF=" + contrato;
/*  99 */       ResultSet resultado = this.con.Consultar(query);
       
/* 101 */       if (resultado != null) {
/* 102 */         while (resultado.next()) {
/* 103 */           dato = resultado.getString("RETMESCO");
         }
       }
/* 106 */       if (dato.isEmpty()) {
/* 107 */         equipos = "Sin fecha limite";
       } else {
/* 109 */         int cantidad = 1;
/* 110 */         equipos = dato.concat(" meses");
       } 
 
 
 
 
       
/* 117 */       String update = "UPDATE CDN_MAIL SET VIGENCIA_CONTRATO = '" + equipos + "' WHERE ID=" + contrato;
/* 118 */       this.con.CerrarConsulta();
       
/* 120 */       this.conO.Conectar();
/* 121 */       this.conO.Consultar(update);
/* 122 */       this.conO.CerrarConsulta();
/* 123 */       return this.rs;
/* 124 */     } catch (SQLException e) {
/* 125 */       this.con.CerrarConsulta();
/* 126 */       return null;
     } 
   }
 
   
   public ResultSet FechaPago(String contrato) throws SQLException, Exception {
/* 132 */     String dato = "";
/* 133 */     String equipos = "";
/* 134 */     String ciclo_fact = "";
     
/* 136 */     Calendar fecha = Calendar.getInstance();
/* 137 */     int año = fecha.get(1) - 1;
/* 138 */     int mes = fecha.get(2);
     
     try {
/* 141 */       String query1 = "SELECT TO_CHAR(FECHA_CONTRATACION, 'YYYY') ANO, TO_CHAR(FECHA_CONTRATACION, 'MM') MES, CICLO_FACTURACION FROM CDN_MAIL WHERE ID=" + contrato;
/* 142 */       this.conO.Conectar();
/* 143 */       ResultSet ciclo = this.conO.Consultar(query1);
/* 144 */       while (ciclo.next()) {
/* 145 */         ciclo_fact = ciclo.getString("CICLO_FACTURACION");
/* 146 */         año = Integer.parseInt(ciclo.getString("ANO")) - 1;
/* 147 */         mes = Integer.parseInt(ciclo.getString("MES"));
       } 
/* 149 */       this.conO.CerrarConsulta();
       
/* 151 */       this.con.Conectar();
/* 152 */       String query = "SELECT * FROM GUAV1.BLCITRL1 WHERE CITANO=" + año + " AND CITMES=" + mes + " AND CITCIC=" + ciclo_fact;
/* 153 */       ResultSet resultado = this.con.Consultar(query);
/* 154 */       while (resultado.next()) {
/* 155 */         dato = resultado.getString("CITFEP");
       }
/* 157 */       if (dato.isEmpty()) {
/* 158 */         equipos = "No tiene fecha";
       } else {
/* 160 */         int cantidad = 1;
/* 161 */         equipos = dato;
       } 
       
/* 164 */       String update = "UPDATE CDN_MAIL SET FECHA_PAGO = TO_CHAR(TO_DATE(" + equipos + ", 'YYYYMMDD'), 'DD')||' de cada mes' WHERE ID=" + contrato;
/* 165 */       this.con.CerrarConsulta();
       
/* 167 */       this.conO.Conectar();
/* 168 */       this.conO.Consultar(update);
/* 169 */       this.conO.CerrarConsulta();
/* 170 */       return this.rs;
/* 171 */     } catch (SQLException e) {
/* 172 */       this.con.CerrarConsulta();
/* 173 */       return null;
     } 
   }
   
   public String[][] SOAPRequest(String uri, String methodHTTP, String contenType, String parameterJSON, String Action) {
/* 178 */     String respuesta = "";
     
     try {
/* 181 */       URL url = new URL(uri);
/* 182 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 183 */       connection.setRequestMethod(methodHTTP);
/* 184 */       connection.setRequestProperty("Content-Type", contenType);
/* 185 */       if (!Action.isEmpty()) {
/* 186 */         connection.setRequestProperty("SOAPAction", Action);
       }
/* 188 */       if (parameterJSON != null) {
/* 189 */         connection.setDoOutput(true);
         
/* 191 */         OutputStream os = connection.getOutputStream();
/* 192 */         String json = parameterJSON;
         
/* 194 */         os.write(json.getBytes());
       } 
       
/* 197 */       InputStream inputStream = connection.getInputStream();
/* 198 */       StringBuilder sb = new StringBuilder();
/* 199 */       BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
       
       String read;
/* 202 */       while ((read = br.readLine()) != null) {
/* 203 */         sb.append(read);
       }
/* 205 */       connection.disconnect();
/* 206 */       String[][] datos = new String[1][7];
/* 207 */       DecimalFormat df = new DecimalFormat("0.00");
/* 208 */       df.setMaximumFractionDigits(2);
       try {
/* 210 */         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         
/* 212 */         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
/* 213 */         String newSB = sb.toString().replace("Ã©", "e");
         
/* 215 */         if (newSB.toUpperCase().contains("&LT;TELEFONO&GT;")) {
/* 216 */           datos[0][0] = null;
/* 217 */           datos[0][1] = newSB.substring(newSB.toUpperCase().indexOf("&LT;FECHAVENCIMIENTO&GT;") + "&LT;FECHAVENCIMIENTO&GT;".length(), newSB.toUpperCase().indexOf("&LT;/FECHAVENCIMIENTO&GT;")).substring(6, 8) + "/" + newSB.substring(newSB.toUpperCase().indexOf("&LT;FECHAVENCIMIENTO&GT;") + "&LT;FECHAVENCIMIENTO&GT;".length(), newSB.toUpperCase().indexOf("&LT;/FECHAVENCIMIENTO&GT;")).substring(4, 6) + "/" + newSB.substring(newSB.toUpperCase().indexOf("&LT;FECHAVENCIMIENTO&GT;") + "&LT;FECHAVENCIMIENTO&GT;".length(), newSB.toUpperCase().indexOf("&LT;/FECHAVENCIMIENTO&GT;")).substring(0, 4);
/* 218 */           datos[0][2] = df.format((Float.parseFloat(newSB.substring(newSB.toUpperCase().indexOf("&LT;SALDOACTUAL&GT;") + "&LT;SALDOACTUAL&GT;".length(), newSB.toUpperCase().indexOf("&LT;/SALDOACTUAL&GT;"))) / 100.0F));
           
/* 220 */           datos[0][3] = df.format(((Float.parseFloat(newSB.substring(newSB.toUpperCase().indexOf("&LT;ADEUDOANTERIOR&GT;") + "&LT;ADEUDOANTERIOR&GT;".length(), newSB.toUpperCase().indexOf("&LT;/ADEUDOANTERIOR&GT;"))) - Float.parseFloat(newSB.substring(newSB.toUpperCase().indexOf("&LT;PAGOS&GT;") + "&LT;PAGOS&GT;".length(), newSB.toUpperCase().indexOf("&LT;/PAGOS&GT;"))) + Float.parseFloat(newSB.substring(newSB.toUpperCase().indexOf("&LT;AJUSTES&GT;") + "&LT;AJUSTES&GT;".length(), newSB.toUpperCase().indexOf("&LT;/AJUSTES&GT;")))) / 100.0F));
/* 221 */           if (datos[0][3].contains("-")) {
/* 222 */             datos[0][3] = "0.00";
           }
/* 224 */           if (Integer.parseInt(newSB.substring(newSB.toUpperCase().indexOf("&LT;SALDOACTUAL&GT;") + "&LT;SALDOACTUAL&GT;".length(), newSB.toUpperCase().indexOf("&LT;/SALDOACTUAL&GT;"))) > 0) {
/* 225 */             datos[0][5] = "0";
           } else {
/* 227 */             datos[0][5] = "1";
           } 
/* 229 */           datos[0][6] = String.format("%02d", new Object[] { Integer.valueOf(Integer.parseInt(newSB.substring(newSB.toUpperCase().indexOf("&LT;CICLOFACT&GT;") + "&LT;CICLOFACT&GT;".length(), newSB.toUpperCase().indexOf("&LT;/CICLOFACT&GT;")))) });
         } else {
/* 231 */           datos = (String[][])null;
         } 
         
/* 234 */         return datos;
       }
/* 236 */       catch (ParserConfigurationException ex) {
/* 237 */         System.out.println("Error bb" + ex);
         
/* 239 */         return (String[][])null;
       }
     
/* 242 */     } catch (MalformedURLException ex) {
/* 243 */       System.out.println("MalFormed URL " + ex);
/* 244 */       return (String[][])null;
/* 245 */     } catch (IOException ex) {
/* 246 */       System.out.println("IO Excep: " + ex);
/* 247 */       return (String[][])null;
     } 
   }
   
   public String[][] InvokeWS(String facturacon) throws Exception {
/* 252 */     String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service\" xmlns:bean=\"http://bean\">\n   <soapenv:Header/>\n   <soapenv:Body>\n      <ser:procesaPeticion>\n         <ser:peticion>\n            <bean:telefono>" + facturacon + "</bean:telefono>\n" + "            <bean:transaccion>03</bean:transaccion>\n" + "         </ser:peticion>\n" + "      </ser:procesaPeticion>\n" + "   </soapenv:Body>\n" + "</soapenv:Envelope>";
 
 
 
 
 
 
 
 
 
     
/* 263 */     String[][] respuesta = SOAPRequest("http://172.20.95.25:9080/zntapp_ws_caja/services/ConsultaSaldo?wsdl", "POST", "text/xml;charset=UTF-8", request, "http://service/ConsultaSaldo/procesaPeticionRequest");
/* 264 */     return respuesta;
   }
 
   
   public String[][] DesgloseFijo(String virtual) throws SQLException, Exception {
/* 269 */     virtual = String.valueOf(Integer.parseInt(virtual));
/* 270 */     Calendar fecha = Calendar.getInstance();
/* 271 */     fecha.add(2, 1);
/* 272 */     int añoActual = fecha.get(1);
/* 273 */     String mesActual = String.format("%02d", new Object[] { Integer.valueOf(fecha.get(2)) });
/* 274 */     fecha.add(1, -1);
/* 275 */     int mes = fecha.get(2);
/* 276 */     fecha.add(2, -1);
/* 277 */     int año = fecha.get(1);
/* 278 */     String mesAnterior = String.format("%02d", new Object[] { Integer.valueOf(fecha.get(2)) });
/* 279 */     if (mesAnterior.equals("00")) {
/* 280 */       mesAnterior = "12";
     }
/* 282 */     String cicloF = "";
/* 283 */     String fechaPago = "";
/* 284 */     String facturacon = "";
     
/* 286 */     this.con.Conectar();
     
/* 288 */     String primeraParteVirtual = String.valueOf(Integer.parseInt(("00000000" + virtual).substring(virtual.length()).substring(0, 4)));
/* 289 */     String segundaParteVirtual = ("00000000" + virtual).substring(virtual.length()).substring(4, 8);
/* 290 */     int primeraParteFacturacon = 0;
/* 291 */     int segundaParteFacturacon = 0;
     
/* 293 */     String queryFacturacon = "SELECT CONBEX,\n       CONBLN\nFROM CONTROL \nWHERE CONMEX = " + primeraParteVirtual + "\n" + "AND CONMLN = " + segundaParteVirtual + "\n" + "AND CONMRS = 0\n" + "FETCH FIRST 1 ROW ONLY";
 
 
 
 
 
 
     
/* 301 */     ResultSet resultadoFacturacon = this.con.Consultar(queryFacturacon);
     
/* 303 */     if (resultadoFacturacon.next()) {
       
       do {
         
/* 307 */         facturacon = ("0000" + resultadoFacturacon.getString("CONBEX")).substring(resultadoFacturacon.getString("CONBEX").length()) + ("0000" + resultadoFacturacon.getString("CONBLN")).substring(resultadoFacturacon.getString("CONBLN").length());
/* 308 */         primeraParteFacturacon = Integer.parseInt(resultadoFacturacon.getString("CONBEX"));
/* 309 */         segundaParteFacturacon = Integer.parseInt(resultadoFacturacon.getString("CONBLN"));
/* 310 */       } while (resultadoFacturacon.next());
     }
/* 312 */     facturacon = ("00000000" + facturacon).substring(facturacon.length());
/* 313 */     String[][] response = InvokeWS(facturacon);
     
/* 315 */     if (response == null) {
       
/* 317 */       String queryCiclo = "SELECT MAX(CONCYC) CICLO_FACTURACION\nFROM CONTROL \nWHERE CONBEX  = " + primeraParteFacturacon + "\n" + "AND CONBLN  = " + segundaParteFacturacon + "\n" + "AND CONMRS = 0";
 
 
 
 
       
/* 323 */       ResultSet resultadoCiclo = this.con.Consultar(queryCiclo);
       
/* 325 */       if (resultadoCiclo.next()) {
         
         do {
           
/* 329 */           cicloF = resultadoCiclo.getString("CICLO_FACTURACION");
/* 330 */         } while (resultadoCiclo.next());
       }
       
/* 333 */       String queryFechaPago = "SELECT CITFEP FECHA_PAGO\nFROM GUAV1.BLCITRL1\nWHERE CITANO = " + año + "\n" + "AND CITMES = " + mes + "\n" + "AND CITCIC = " + cicloF;
 
 
 
 
       
/* 339 */       cicloF = String.format("%02d", new Object[] { Integer.valueOf(Integer.parseInt(cicloF)) });
       
/* 341 */       ResultSet resultado = this.con.Consultar(queryFechaPago);
/* 342 */       if (!resultado.next()) {
/* 343 */         fechaPago = "Sin fecha de pago";
       } else {
         do {
/* 346 */           fechaPago = resultado.getString("FECHA_PAGO");
/* 347 */         } while (resultado.next());
       } 
       
/* 350 */       String[][] datos = new String[1][6];
       
/* 352 */       facturacon = ("0000000000" + facturacon).substring(facturacon.length());
       
/* 354 */       String str1 = facturacon.substring(0, 6);
/* 355 */       String str2 = facturacon.substring(6, 10);
 
       
       try {
/* 359 */         String query1 = "SELECT CONCAT(ARFMEX,ARFMLN) AS VIRTUAL,\n       (ARFPBL + ARFLSC) - ARFPMT AS TOTAL_A_PAGAR,\n       ARFLSC AS SALDO_PENDIENTE,\n       (CASE WHEN (ARFPBL + ARFLSC) <= ARFPMT THEN 1 ELSE 0 END) AS PAGADO\nFROM SUBAR\nWHERE ARFBEX = " + primeraParteVirtual + "\n" + "AND ARFBLN = " + segundaParteVirtual + "\n" + "AND ARFMRS = 0\n" + "GROUP BY CONCAT(ARFMEX,ARFMLN), ARFBDT, ARFPBL, ARFLSC, ARFLSC, ARFPMT\n" + "ORDER BY ARFBDT DESC\n" + "FETCH FIRST 1 ROW ONLY";
 
 
 
 
 
 
 
 
 
         
/* 370 */         String query2 = "";
/* 371 */         ResultSet consulta = this.con.Consultar(query1);
         
/* 373 */         if (consulta.next()) {
           
           do {
             
/* 377 */             datos[0][0] = consulta.getString("VIRTUAL");
/* 378 */             datos[0][2] = consulta.getString("TOTAL_A_PAGAR");
/* 379 */             datos[0][3] = consulta.getString("SALDO_PENDIENTE");
/* 380 */             datos[0][5] = consulta.getString("PAGADO");
/* 381 */           } while (consulta.next());
         }
         
/* 384 */         if (fechaPago.isEmpty()) {
/* 385 */           query2 = "SELECT ADD_MONTHS(DATE(TRIM(CONCAT(CONCAT(CONCAT(CONCAT(SUBSTR(CHAR(ARFBDT),1,4),'-'),SUBSTR(CHAR(ARFBDT),5,2)), '-'),SUBSTR(CHAR(ARFBDT),7,8)))),1) AS FECHA_PAGO\nFROM SUBAR\nWHERE ARFBEX = " + primeraParteVirtual + "\n" + "AND ARFBLN = " + segundaParteVirtual + "\n" + "AND ARFMRS = 0\n" + "AND ARFBDT <  99999999\n" + "GROUP BY ARFBDT\n" + "ORDER BY ARFBDT DESC\n" + "FETCH FIRST 1 ROW ONLY";
 
 
 
 
 
 
 
 
           
/* 395 */           consulta = this.con.Consultar(query2);
           
/* 397 */           if (consulta.next()) {
             
             do {
               
/* 401 */               datos[0][1] = consulta.getString("FECHA_PAGO");
/* 402 */             } while (consulta.next());
           }
         } else {
           
/* 406 */           fechaPago = fechaPago.substring(6, 8) + "/" + fechaPago.substring(4, 6) + "/" + añoActual;
/* 407 */           datos[0][1] = fechaPago;
         } 
         
/* 410 */         String str3 = "SELECT DSEEXC||DSELNÑ AS LINEA_PADRE,\n      SUM(DSEMCH) AS CUOTA_FINANC\nFROM BLNV" + mesActual + cicloF + "SE\n" + "WHERE DSEEXC ='" + str1 + "'\n" + "AND DSELNÑ ='" + str2 + "'\n" + "AND DSEITM IN (SELECT EQFITM\n" + "               FROM BLITMEQPFI)\n" + "GROUP BY DSEEXC||DSELNÑ";
 
 
 
 
 
 
 
         
/* 419 */         ResultSet resultSet1 = this.con.Consultar(str3);
         
/* 421 */         if (resultSet1 == null || !resultSet1.next()) {
/* 422 */           str3 = "SELECT DSEEXC||DSELNÑ AS LINEA_PADRE,\n      SUM(DSEMCH) AS CUOTA_FINANC\nFROM BLNV" + mesAnterior + cicloF + "SE\n" + "WHERE DSEEXC ='" + str1 + "'\n" + "AND DSELNÑ ='" + str2 + "'\n" + "AND DSEITM IN (SELECT EQFITM\n" + "               FROM BLITMEQPFI)\n" + "GROUP BY DSEEXC||DSELNÑ";
 
 
 
 
 
 
 
           
/* 431 */           ResultSet resultadoFinanciamiento2 = this.con.Consultar(str3);
/* 432 */           if (resultadoFinanciamiento2 == null || !resultadoFinanciamiento2.next()) {
/* 433 */             datos[0][4] = "0.00";
           } else {
             do {
/* 436 */               datos[0][4] = resultadoFinanciamiento2.getString("CUOTA_FINANC");
/* 437 */             } while (resultadoFinanciamiento2.next());
           } 
         } else {
           do {
/* 441 */             datos[0][4] = resultSet1.getString("CUOTA_FINANC");
/* 442 */           } while (resultSet1.next());
         } 
         
/* 445 */         this.con.CerrarConsulta();
         
/* 447 */         return datos;
       }
/* 449 */       catch (SQLException e) {
/* 450 */         this.con.CerrarConsulta();
/* 451 */         return (String[][])null;
       } 
     } 
/* 454 */     response[0][0] = facturacon;
/* 455 */     facturacon = ("0000000000" + facturacon).substring(facturacon.length());
/* 456 */     String arfbex = facturacon.substring(0, 6);
/* 457 */     String arfbln = facturacon.substring(6, 10);
/* 458 */     String queryFinanciamiento = "SELECT DSEEXC||DSELNÑ AS LINEA_PADRE,\n      SUM(DSEMCH) AS CUOTA_FINANC\nFROM BLNV" + mesActual + response[0][6] + "SE\n" + "WHERE DSEEXC ='" + arfbex + "'\n" + "AND DSELNÑ ='" + arfbln + "'\n" + "AND DSEITM IN (SELECT EQFITM\n" + "               FROM BLITMEQPFI)\n" + "GROUP BY DSEEXC||DSELNÑ";
 
 
 
 
 
 
 
     
/* 467 */     ResultSet resultadoFinanciamiento = this.con.Consultar(queryFinanciamiento);
     
/* 469 */     if (resultadoFinanciamiento == null || !resultadoFinanciamiento.next()) {
/* 470 */       queryFinanciamiento = "SELECT DSEEXC||DSELNÑ AS LINEA_PADRE,\n      SUM(DSEMCH) AS CUOTA_FINANC\nFROM BLNV" + mesAnterior + response[0][6] + "SE\n" + "WHERE DSEEXC ='" + arfbex + "'\n" + "AND DSELNÑ ='" + arfbln + "'\n" + "AND DSEITM IN (SELECT EQFITM\n" + "               FROM BLITMEQPFI)\n" + "GROUP BY DSEEXC||DSELNÑ";
 
 
 
 
 
 
 
       
/* 479 */       ResultSet resultadoFinanciamiento2 = this.con.Consultar(queryFinanciamiento);
/* 480 */       if (resultadoFinanciamiento2 == null || !resultadoFinanciamiento2.next()) {
/* 481 */         response[0][4] = "0.00";
       } else {
         do {
/* 484 */           response[0][4] = resultadoFinanciamiento2.getString("CUOTA_FINANC");
/* 485 */         } while (resultadoFinanciamiento2.next());
       } 
     } else {
       do {
/* 489 */         response[0][4] = resultadoFinanciamiento.getString("CUOTA_FINANC");
/* 490 */       } while (resultadoFinanciamiento.next());
     } 
     
/* 493 */     this.con.CerrarConsulta();
/* 494 */     return response;
   }
 
   
   public int encontrarNegocio(String numero) throws SQLException, Exception {
/* 499 */     String dato = "";
/* 500 */     int res = 0;
     
     try {
/* 503 */       this.con.Conectar();
/* 504 */       String query = "SELECT CONCYC FROM CONTROLL5 WHERE CONMEX||DIGITS(CONMLN)=" + numero + " AND CONMRS=0 ";
/* 505 */       ResultSet resultado = this.con.Consultar(query);
/* 506 */       while (resultado.next()) {
/* 507 */         dato = dato + resultado.getString("CONCYC") + "";
       }
/* 509 */       if (dato.isEmpty()) {
/* 510 */         res = 0;
       } else {
/* 512 */         res = Integer.parseInt(dato);
       } 
/* 514 */       this.con.CerrarConsulta();
/* 515 */       return res;
/* 516 */     } catch (SQLException e) {
/* 517 */       this.con.CerrarConsulta();
/* 518 */       return res;
     } 
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Model\ConsultasPisa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */