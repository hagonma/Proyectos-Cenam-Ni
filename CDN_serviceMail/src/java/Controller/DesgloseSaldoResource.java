 package Controller;
 
 import Controller.DesgloseSaldoFijoResource;
 import Controller.EnviarSMSGenericoResource;
 import Model.ConsultasBSCS;
 import Model.ConsultasBSCSCR;
 import Model.Parametro;
 import java.text.DecimalFormat;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 import org.json.JSONArray;
 import org.json.JSONObject;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("DesgloseSaldo")
 public class DesgloseSaldoResource
 {
   @Context
   private UriInfo context;
   public String sistema;
   
   @GET
   @Produces({"application/xml"})
   public String getJson(@QueryParam("id") String id, @QueryParam("canal") String canal, @QueryParam("destino") String destino, @QueryParam("codpais") String codpais) throws Exception {
/*  58 */     String returnString = "", returnCode = "0", pais = "", idGenerico = "", idPais = "", moneda = "", saldoTotal = "", saldo = "", fechaVencimiento = "", num = "";
/*  59 */     DecimalFormat formatea = new DecimalFormat("###,###.##");
/*  60 */     float montoFin = 0.0F;
/*  61 */     Float totalFactura = Float.valueOf(montoFin);
/*  62 */     int res = 0;
     try {
/*  64 */       pais = codpais;
       
/*  66 */       switch (pais) {
         case "502":
/*  68 */           this.sistema = "SGT";
/*  69 */           idGenerico = "640";
/*  70 */           idPais = "101";
/*  71 */           moneda = "Q";
           break;
         case "503":
/*  74 */           this.sistema = "SSV";
/*  75 */           idGenerico = "660";
/*  76 */           idPais = "102";
/*  77 */           moneda = "$";
           break;
         case "504":
/*  80 */           this.sistema = "SHN";
/*  81 */           idGenerico = "620";
/*  82 */           idPais = "103";
/*  83 */           moneda = "$";
           break;
         case "505":
/*  86 */           this.sistema = "SNI";
/*  87 */           idGenerico = "621";
/*  88 */           idPais = "104";
/*  89 */           moneda = "C$";
           break;
         case "506":
/*  92 */           this.sistema = "SCR";
/*  93 */           idGenerico = "623";
/*  94 */           idPais = "106";
/*  95 */           moneda = "C.";
           break;
         case "507":
/*  98 */           this.sistema = "SPA";
/*  99 */           idGenerico = "";
/* 100 */           idPais = "105";
           break;
       } 
/* 103 */       num = null;
/* 104 */       String idConsulta = id;
/* 105 */       ConsultasBSCSCR conBSCSCR = new ConsultasBSCSCR();
/* 106 */       ConsultasBSCS conBSCS = new ConsultasBSCS();
 
 
       
/* 110 */       DesgloseSaldoFijoResource dsfr = new DesgloseSaldoFijoResource();
/* 111 */       String resultadoFijo = null;
/* 112 */       if (!pais.equals("503") || idConsulta.toUpperCase().contains("H") || ((pais.equals("503") || pais.equals("502")) && !idConsulta.toUpperCase().contains("FACTURA"))) {
/* 113 */         resultadoFijo = dsfr.getJson(idConsulta, pais, canal);
       }
/* 115 */       else if (idConsulta.toUpperCase().contains("FACTURA")) {
/* 116 */         resultadoFijo = "Error";
/* 117 */         res = 1;
       } 
 
       
/* 121 */       if (resultadoFijo.contains("Error")) {
         
/* 123 */         id = id.toUpperCase().replace("FACTURA ", "");
/* 124 */         if (id.length() > 8) {
/* 125 */           num = id.substring(3, id.length());
         } else {
/* 127 */           num = pais + "" + id;
         } 
         
/* 130 */         id = num;
/* 131 */         res = 1;
       } else {
/* 133 */         res = 2;
       } 
 
 
       
/* 138 */       String[] parts = null;
/* 139 */       int i = 0;
/* 140 */       if (res == 1) {
/* 141 */         if (id.length() == 11) {
 
 
           
/* 145 */           JSONObject jobSaldoVenc, job, jobMora = null, jobjRowMora = null;
/* 146 */           JSONArray jarrMora = null;
/* 147 */           if (pais.equals("506")) {
/* 148 */             conBSCSCR.findSaldoVencido(id);
/* 149 */             jobSaldoVenc = new JSONObject(conBSCSCR.hp.getResponse());
/* 150 */             job = new JSONObject(conBSCSCR.findSaldoByNumber(id));
           } else {
/* 152 */             conBSCS.findSaldoVencido(id);
/* 153 */             jobSaldoVenc = new JSONObject(conBSCS.hp.getResponse());
/* 154 */             job = new JSONObject(conBSCS.findSaldoByNumber(id));
           } 
 
 
 
           
/* 160 */           JSONObject job2 = job.getJSONObject("Response");
/* 161 */           String string = job2.get("Text").toString();
/* 162 */           String lastCharacter = string.substring(string.length() - 1, string.length());
/* 163 */           if (lastCharacter.equals(";")) {
/* 164 */             string = string + "0";
           }
/* 166 */           parts = string.split(";");
/* 167 */           for (String str : parts) {
/* 168 */             if (str != null) {
/* 169 */               i++;
             }
           } 
/* 172 */           JSONArray jarrVenci = jobSaldoVenc.getJSONArray("Response");
           
/* 174 */           JSONObject jobjRowSaldo = jarrVenci.getJSONObject(0);
           
/* 176 */           saldo = jobjRowSaldo.getString("SALDO_VENCIDO");
/* 177 */           saldoTotal = jobjRowSaldo.getString("SALDO_TOTAL");
/* 178 */           fechaVencimiento = jobjRowSaldo.getString("FECHA_VENCIMIENTO");
/* 179 */           String customerID = jobjRowSaldo.getString("CUSTOMER_ID");
/* 180 */           String custCode = jobjRowSaldo.getString("CUSTCODE");
/* 181 */           String mora = jobjRowSaldo.getString("MORA");
/* 182 */           String moraValor = "0.0";
 
 
 
 
 
 
 
 
 
 
           
/* 194 */           totalFactura = Float.valueOf(Float.parseFloat(saldoTotal));
/* 195 */           String totalFacturaTxt = parts[2];
/* 196 */           String response = "";
 
 
 
           
/* 201 */           saldoTotal = formatea.format(Float.parseFloat(saldoTotal));
/* 202 */           if (Integer.parseInt(mora) > 3) {
 
             
/* 205 */             response = "ERROR";
/* 206 */             returnCode = "-1";
/* 207 */             returnString = response;
/* 208 */           } else if (totalFactura.floatValue() >= 0.01D) {
             JSONObject jobFinancing;
             
/* 211 */             if (pais.equals("504")) {
/* 212 */               response = response + "El total a pagar de tu factura con fecha limite de pago " + fechaVencimiento + " es de " + moneda + saldoTotal;
/* 213 */             } else if (pais.equals("506")) {
/* 214 */               response = response + "El total a pagar de su factura con fecha limite de pago " + fechaVencimiento + " es de " + moneda + saldoTotal;
/* 215 */               i = 0;
             } else {
/* 217 */               response = response + "El total a pagar de su factura con fecha limite de pago " + fechaVencimiento + " es de " + moneda + saldoTotal;
             } 
 
 
             
/* 222 */             if (!pais.equals("506") && 
/* 223 */               i > 6) {
/* 224 */               if (pais.equals("505") || pais.equals("503"))
/* 225 */                 response = response + "\\n Lineas dentro del contrato: "; 
/* 226 */               for (int j = 3; j <= i - 2; j++) {
/* 227 */                 if (parts[j].length() > 8) {
/* 228 */                   response = response + "\\n " + parts[j].substring(3, 11);
                 } else {
/* 230 */                   response = response + "\\n " + parts[j];
                 } 
                 
/* 233 */                 if (!pais.equals("505") && !pais.equals("503"))
/* 234 */                   response = response + " " + moneda + "" + parts[j + 1].substring(3, parts[j + 1].length()); 
/* 235 */                 j++;
               } 
             } 
 
             
/* 240 */             if (pais.equals("506")) {
/* 241 */               conBSCSCR.findFinancing(customerID);
/* 242 */               jobFinancing = new JSONObject(conBSCSCR.hp.getResponse());
             } else {
/* 244 */               conBSCS.findFinancing(customerID);
/* 245 */               jobFinancing = new JSONObject(conBSCS.hp.getResponse());
             } 
             
/* 248 */             JSONArray jarr = jobFinancing.getJSONArray("Response");
/* 249 */             if (jarr.length() > 0) {
/* 250 */               JSONObject jobjRow = jarr.getJSONObject(0);
/* 251 */               montoFin += Float.parseFloat(jobjRow.getString("TOTAL"));
               
/* 253 */               if (montoFin >= 0.01D && 
/* 254 */                 !pais.equals("505") && !pais.equals("506") && !pais.equals("503"))
               {
 
 
 
 
 
                 
/* 262 */                 response = response + "\\n Cuota mensual financiamiento(s) " + moneda + " " + formatea.format(montoFin);
               }
             } 
 
 
             
/* 268 */             if (Float.parseFloat(saldo) >= 0.01D) {
/* 269 */               if (pais.equals("504") || pais.equals("505") || pais.equals("503")) {
/* 270 */                 response = response + "\\n Saldo vencido " + moneda + " " + formatea.format(Float.parseFloat(saldo));
               } else {
/* 272 */                 response = response + "\\n Saldo pendiente " + moneda + " " + formatea.format(Float.parseFloat(saldo));
               } 
             }
             
/* 276 */             if (pais.equals("502") && 
/* 277 */               Float.parseFloat(parts[i - 1]) >= 0.01D) {
/* 278 */               response = response + "\\n Mora " + moneda + " " + parts[i - 1];
             }
 
             
/* 282 */             returnString = response;
/* 283 */             returnCode = "0";
           } else {
/* 285 */             if (pais.equals("504")) {
/* 286 */               returnString = "Estimado cliente, no tienes saldo pendiente de pago.";
             } else {
/* 288 */               returnString = "Estimado cliente, no tiene saldo pendiente de pago.";
             } 
/* 290 */             returnCode = "0";
           } 
         } else {
           
/* 294 */           returnString = "Servicio no encontrado, por favor revisa los datos ingresados.";
/* 295 */           returnCode = "0";
         } 
/* 297 */       } else if (res == 0) {
/* 298 */         returnString = "Servicio no encontrado, por favor revisa los datos ingresados.";
/* 299 */         returnCode = "0";
       } else {
/* 301 */         returnString = resultadoFijo;
/* 302 */         returnCode = "0";
       } 
/* 304 */     } catch (Exception e) {
/* 305 */       returnString = "Error.";
/* 306 */       returnCode = "-1";
     } 
/* 308 */     if (canal.equals("USSD")) {
/* 309 */       if (res == 1) {
/* 310 */         if (totalFactura.floatValue() < 0.01D) {
/* 311 */           if (pais.equals("504")) {
/* 312 */             returnString = "Estimado cliente, no tienes saldo pendiente de pago.";
           } else {
/* 314 */             returnString = "Estimado cliente, no tiene saldo pendiente de pago.";
           } 
/* 316 */         } else if (!returnString.isEmpty() && !returnString.toUpperCase().contains("ERROR")) {
/* 317 */           returnString = "Telefono: " + num.replace("502", "").replace("503", "").replace("504", "").replace("505", "").replace("506", "") + "\\n";
/* 318 */           returnString = returnString + "Total a pagar: " + moneda + " " + saldoTotal + "\\n";
/* 319 */           if (Float.parseFloat(saldo) >= 0.01D) {
/* 320 */             if (pais.equals("503") || pais.equals("505")) {
/* 321 */               returnString = returnString + "Saldo vencido: " + moneda + " " + formatea.format(Float.parseFloat(saldo)) + "\\n";
             } else {
/* 323 */               returnString = returnString + "Saldo pendiente: " + moneda + " " + formatea.format(Float.parseFloat(saldo)) + "\\n";
             } 
           }
           
/* 327 */           if (montoFin >= 0.01D && !pais.equals("503") && !pais.equals("505") && !pais.equals("506")) {
/* 328 */             returnString = returnString + "Financiamientos: " + moneda + " " + formatea.format(montoFin) + "\\n";
           }
/* 330 */           returnString = returnString + "Fecha limite de pago: " + fechaVencimiento + "\\n";
         } else {
/* 332 */           returnString = "Servicio no encontrado, por favor revisa los datos ingresados.";
         } 
       }
       
/* 336 */       Parametro param = new Parametro();
/* 337 */       param.getByName("SALTO_LINEA", 101);
/* 338 */       JSONObject jobj = new JSONObject(param.hp.getResponse());
/* 339 */       JSONArray jarr = jobj.getJSONArray("Response");
/* 340 */       JSONObject jobjRow = jarr.getJSONObject(0);
/* 341 */       String saltoLinea = jobjRow.getString("VALOR");
 
       
/* 344 */       return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><ssrvresponse>      <message>" + returnString
         
/* 346 */         .replace("\\n", saltoLinea) + "</message>" + "     <TangoResponseCode>" + returnCode + "</TangoResponseCode>" + "</ssrvresponse>";
     } 
 
     
/* 350 */     EnviarSMSGenericoResource esgt = new EnviarSMSGenericoResource();
/* 351 */     esgt.setSistema(this.sistema);
/* 352 */     String mensaje = returnString, mensajep1 = "", mensajep2 = "", params = "404";
     
/* 354 */     if (mensaje.length() > 160) {
/* 355 */       mensajep1 = mensaje.substring(0, 160);
/* 356 */       int ultimoCaracter = mensajep1.lastIndexOf(" ");
/* 357 */       mensajep1 = mensaje.substring(0, ultimoCaracter);
/* 358 */       mensajep2 = mensaje.substring(ultimoCaracter, mensaje.length());
     } else {
/* 360 */       mensajep1 = mensaje;
     } 
/* 362 */     if (mensajep1.isEmpty() || mensajep1.toUpperCase().contains("ERROR"))
     {
       
/* 365 */       mensajep1 = "Servicio no encontrado, por favor revisa los datos ingresados.";
     }
     
/* 368 */     String params1 = "{ \"descripcion\" : \"" + mensajep1 + "\" ,  \"pais\" : \"" + idPais + "\" ,  \"numero\" : \"" + destino + "\",  \"id\" : \"\" }";
/* 369 */     String params2 = "{}";
/* 370 */     String params3 = "{}";
/* 371 */     esgt.setAskedService(id.toString());
/* 372 */     esgt.postJson(params1);
/* 373 */     if (!mensajep2.isEmpty()) {
/* 374 */       Thread.sleep(1000L);
/* 375 */       params2 = "{ \"descripcion\" : \"" + mensajep2 + "\" ,  \"pais\" : \"" + idPais + "\" ,  \"numero\" : \"" + destino + "\",  \"id\" : \"\" }";
/* 376 */       esgt.postJson(params2);
     } 
     
/* 379 */     Thread.sleep(1000L);
/* 380 */     if (!returnCode.equals("-1") && !mensajep1.equals("Estimado cliente, no tiene saldo pendiente de pago.") && !mensajep1.equals("Estimado cliente, no tienes saldo pendiente de pago.") && !mensajep1.equals("Servicio no encontrado, por favor revisa los datos ingresados.") && !pais.equals("503") && !pais.equals("507")) {
/* 381 */       params3 = "{ \"descripcion\" : \"\" ,  \"pais\" : \"" + idPais + "\" ,  \"numero\" : \"" + destino + "\",  \"id\" : \"" + idGenerico + "\" }";
/* 382 */       esgt.postJson(params3);
     } 
     
/* 385 */     if (pais.equals("503") && res == 1 && !returnCode.equals("-1") && !mensajep1.equals("Estimado cliente, no tiene saldo pendiente de pago.") && !mensajep1.equals("Estimado cliente, no tienes saldo pendiente de pago.") && !mensajep1.equals("Servicio no encontrado, por favor revisa los datos ingresados.")) {
/* 386 */       params3 = "{ \"descripcion\" : \"\" ,  \"pais\" : \"" + idPais + "\" ,  \"numero\" : \"" + destino + "\",  \"id\" : \"" + idGenerico + "\" }";
/* 387 */       esgt.postJson(params3);
     } 
     
/* 390 */     params = "[" + params1 + "," + params2 + "," + params3 + "]";
/* 391 */     return params;
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\DesgloseSaldoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */