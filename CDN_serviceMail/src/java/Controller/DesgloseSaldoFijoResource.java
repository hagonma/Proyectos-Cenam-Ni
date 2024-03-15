 package Controller;
 
 import Model.ConsultasBSCSCR;
 import Model.ConsultasGAIA;
 import Model.ConsultasOPEN;
 import Model.ConsultasPisa;
 import java.text.DecimalFormat;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import javax.ws.rs.QueryParam;
 import javax.ws.rs.core.Context;
 import javax.ws.rs.core.UriInfo;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("DesgloseSaldoFijo")
 public class DesgloseSaldoFijoResource
 {
   @Context
   private UriInfo context;
   
   @GET
   @Produces({"application/json"})
   public String getJson(@QueryParam("id") String id, @QueryParam("idpais") String idpais, @QueryParam("canal") String canal) {
/*  52 */     String mensaje = "Error: Indefinido, mensaje no enviado id: " + id + ", idpais: " + idpais;
/*  53 */     DecimalFormat formatter = new DecimalFormat("#,##0.00");
/*  54 */     if (idpais.equals("502")) {
       
/*  56 */       String fechaLimitePago = null;
/*  57 */       String totalPagarFactura = null;
/*  58 */       String totalFinanciamiento = null;
/*  59 */       String totalSaldoPendiente = null;
/*  60 */       String pagado = null;
/*  61 */       String financiamientoTexto1 = null;
/*  62 */       if (canal.equals("SMS")) {
/*  63 */         financiamientoTexto1 = " \\nCuota mensual financiamiento Q.";
       } else {
/*  65 */         financiamientoTexto1 = " \\nFinanciamientos Q.";
       } 
/*  67 */       String SaldoVencidoTexto1 = " \\nSaldo pendiente Q.";
/*  68 */       String financiamientoTexto2 = ".";
/*  69 */       String financiamientoTextoFinal = "";
/*  70 */       String SaldoVencidoTexto2 = ".";
/*  71 */       String SaldoVencidoTextoFinal = "";
       
/*  73 */       ConsultasPisa con = new ConsultasPisa();
       
       try {
/*  76 */         String[][] datos = con.DesgloseFijo(id);
/*  77 */         fechaLimitePago = datos[0][1];
/*  78 */         totalPagarFactura = formatter.format(Double.parseDouble(datos[0][2]));
/*  79 */         totalSaldoPendiente = formatter.format(Double.parseDouble(datos[0][3]));
/*  80 */         totalFinanciamiento = formatter.format(Double.parseDouble(datos[0][4]));
/*  81 */         pagado = datos[0][5];
         
/*  83 */         if (totalPagarFactura == null) {
/*  84 */           totalPagarFactura = "0.00";
         }
         
/*  87 */         if (totalFinanciamiento == null) {
/*  88 */           totalFinanciamiento = "0.00";
         }
         
/*  91 */         if (totalSaldoPendiente == null) {
/*  92 */           totalSaldoPendiente = "0.00";
         }
         
/*  95 */         if (pagado == null) {
/*  96 */           pagado = "1";
         }
       }
/*  99 */       catch (Exception e) {
/* 100 */         mensaje = "Error: Consulta PISA {Excepcion: " + e.toString() + "}, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
       try {
/* 103 */         if (fechaLimitePago == null && fechaLimitePago.isEmpty() && totalPagarFactura == null && totalPagarFactura.isEmpty() && totalFinanciamiento == null && totalFinanciamiento.isEmpty() && totalSaldoPendiente == null && totalSaldoPendiente.isEmpty()) {
/* 104 */           mensaje = "Error: Campos vacios, mensaje no enviado id: " + id + ", idpais: " + idpais;
         }
/* 106 */         else if (pagado.equals("0")) {
/* 107 */           if (!totalFinanciamiento.equals("0.00")) {
/* 108 */             financiamientoTextoFinal = financiamientoTexto1 + totalFinanciamiento + financiamientoTexto2;
           }
/* 110 */           if (!totalSaldoPendiente.equals("0.00")) {
/* 111 */             SaldoVencidoTextoFinal = SaldoVencidoTexto1 + totalSaldoPendiente + SaldoVencidoTexto2;
           }
/* 113 */           if (canal.equals("SMS")) {
/* 114 */             mensaje = "El total a pagar de su factura con fecha limite de pago " + fechaLimitePago + " es de Q." + totalPagarFactura + "." + financiamientoTextoFinal + SaldoVencidoTextoFinal;
           }
           else {
             
/* 118 */             mensaje = "Numero de servicio: " + id + " \\nTotal a pagar Q." + totalPagarFactura + "." + SaldoVencidoTextoFinal + financiamientoTextoFinal + " \\nFecha limite de pago: " + fechaLimitePago;
           } 
         } else {
/* 121 */           mensaje = "Estimado cliente, no tienes saldo pendiente de pago.";
         }
       
/* 124 */       } catch (Exception e) {
/* 125 */         mensaje = "Error: Campos vacios, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
     } 
     
/* 129 */     if (idpais.equals("503")) {
       
/* 131 */       String fechaLimitePago = null;
/* 132 */       String totalPagarFactura = null;
/* 133 */       String totalFinanciamiento = null;
/* 134 */       String totalSaldoPendiente = null;
/* 135 */       String financiamientoTexto1 = null;
/* 136 */       if (canal.equals("SMS")) {
/* 137 */         financiamientoTexto1 = " \\nTotal financiamiento $.";
       } else {
/* 139 */         financiamientoTexto1 = " \\nFinanciamientos Q.";
       } 
/* 141 */       String SaldoVencidoTexto1 = " \\nSaldo vencido $.";
/* 142 */       String financiamientoTexto2 = ".";
/* 143 */       String financiamientoTextoFinal = "";
       
/* 145 */       String SaldoVencidoTexto2 = ".";
/* 146 */       String SaldoVencidoTextoFinal = "";
       
/* 148 */       ConsultasGAIA con = new ConsultasGAIA();
       
       try {
/* 151 */         String[][] datos = con.DesgloseFijo(id);
         
/* 153 */         if (datos != null && datos[0][0] != null) {
/* 154 */           if (datos[0][0].equals("ERROR")) {
/* 155 */             throw new Exception(datos[0][1]);
           }
/* 157 */           fechaLimitePago = datos[0][1];
/* 158 */           totalPagarFactura = formatter.format(Double.parseDouble(datos[0][2]));
/* 159 */           totalFinanciamiento = formatter.format(Double.parseDouble(datos[0][3]));
/* 160 */           totalSaldoPendiente = formatter.format(Double.parseDouble(datos[0][4]));
/* 161 */           if (!totalFinanciamiento.equals("0.00")) {
/* 162 */             financiamientoTextoFinal = financiamientoTexto1 + totalFinanciamiento + financiamientoTexto2;
           }
/* 164 */           if (!totalSaldoPendiente.equals("0.00")) {
/* 165 */             SaldoVencidoTextoFinal = SaldoVencidoTexto1 + totalSaldoPendiente + SaldoVencidoTexto2;
           }
/* 167 */           if (canal.equals("SMS")) {
/* 168 */             mensaje = "El total a pagar de su factura con fecha limite de pago " + fechaLimitePago + " es de $." + totalPagarFactura + "." + SaldoVencidoTextoFinal;
           }
           else {
             
/* 172 */             mensaje = "Numero de servicio: " + id + " \\nTotal a pagar $." + totalPagarFactura + "." + SaldoVencidoTextoFinal + " \\nFecha limite de pago: " + fechaLimitePago;
           } 
         } else {
           
/* 176 */           mensaje = "Estimado cliente, no tienes saldo pendiente de pago.";
         }
       
/* 179 */       } catch (Exception e) {
/* 180 */         mensaje = "Error: Consulta GAIA {Excepcion: " + e.toString() + "}, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
     } 
 
     
/* 185 */     if (idpais.equals("504")) {
       
/* 187 */       String fechaLimitePago = null;
/* 188 */       String totalPagarFactura = null;
/* 189 */       String totalFinanciamiento = null;
/* 190 */       String totalSaldoPendiente = null;
/* 191 */       String financiamientoTexto1 = null;
/* 192 */       if (canal.equals("SMS")) {
/* 193 */         financiamientoTexto1 = " \\nCuota mensual financiamiento $.";
       } else {
/* 195 */         financiamientoTexto1 = " \\nFinanciamientos $.";
       } 
/* 197 */       String financiamientoTexto2 = ".";
/* 198 */       String financiamientoTextoFinal = "";
/* 199 */       String SaldoVencidoTexto1 = " \\nSaldo vencido $.";
/* 200 */       String SaldoVencidoTexto2 = ".";
/* 201 */       String SaldoVencidoTextoFinal = "";
       
/* 203 */       ConsultasOPEN con = new ConsultasOPEN(idpais);
       
       try {
/* 206 */         String[][] datos = con.DesgloseFijo(id);
/* 207 */         if (datos != null && datos[0][0] != null) {
/* 208 */           if (datos[0][0].equals("ERROR")) {
/* 209 */             throw new Exception(datos[0][1]);
           }
/* 211 */           fechaLimitePago = datos[0][1];
/* 212 */           totalPagarFactura = formatter.format(Double.parseDouble(datos[0][2]));
/* 213 */           totalFinanciamiento = formatter.format(Double.parseDouble(datos[0][3]));
/* 214 */           totalSaldoPendiente = formatter.format(Double.parseDouble(datos[0][4]));
/* 215 */           if (!totalFinanciamiento.equals("0.00")) {
/* 216 */             financiamientoTextoFinal = financiamientoTexto1 + totalFinanciamiento + financiamientoTexto2;
           }
/* 218 */           if (!totalSaldoPendiente.equals("0.00")) {
/* 219 */             SaldoVencidoTextoFinal = SaldoVencidoTexto1 + totalSaldoPendiente + SaldoVencidoTexto2;
           }
/* 221 */           if (canal.equals("SMS")) {
/* 222 */             mensaje = "El total a pagar de su factura con fecha limite de pago " + fechaLimitePago + " es de $." + totalPagarFactura + "." + financiamientoTextoFinal + SaldoVencidoTextoFinal;
           }
           else {
             
/* 226 */             mensaje = "Numero de servicio: " + id + " \\nTotal a pagar $." + totalPagarFactura + "." + SaldoVencidoTextoFinal + financiamientoTextoFinal + " \\nFecha limite de pago: " + fechaLimitePago;
           } 
         } else {
           
/* 230 */           mensaje = "Estimado cliente, no tienes saldo pendiente de pago.";
         }
       
/* 233 */       } catch (Exception e) {
/* 234 */         mensaje = "Error: Consulta OPEN {Excepcion: " + e.toString() + "}, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
     } 
 
     
/* 239 */     if (idpais.equals("505")) {
       
/* 241 */       String fechaLimitePago = null;
/* 242 */       String totalPagarFactura = null;
/* 243 */       String totalSaldoPendiente = null;
/* 244 */       String SaldoVencidoTexto1 = " \\nSaldo vencido C$.";
/* 245 */       String SaldoVencidoTexto2 = ".";
/* 246 */       String SaldoVencidoTextoFinal = "";
       
/* 248 */       ConsultasOPEN con = new ConsultasOPEN(idpais);
       
       try {
/* 251 */         String[][] datos = con.DesgloseFijo(id);
/* 252 */         if (datos != null && datos[0][0] != null) {
/* 253 */           if (datos[0][0].equals("ERROR")) {
/* 254 */             throw new Exception(datos[0][1]);
           }
/* 256 */           fechaLimitePago = datos[0][1];
/* 257 */           totalPagarFactura = formatter.format(Double.parseDouble(datos[0][2]));
/* 258 */           totalSaldoPendiente = formatter.format(Double.parseDouble(datos[0][4]));
/* 259 */           if (!totalSaldoPendiente.equals("0.00")) {
/* 260 */             SaldoVencidoTextoFinal = SaldoVencidoTexto1 + totalSaldoPendiente + SaldoVencidoTexto2;
           }
           
/* 263 */           if (canal.equals("SMS")) {
/* 264 */             mensaje = "El total a pagar de su factura con fecha limite de pago " + fechaLimitePago + " es de C$." + totalPagarFactura + "." + SaldoVencidoTextoFinal;
           } else {
/* 266 */             mensaje = "Numero de servicio: " + id + " \\nTotal a pagar C$." + totalPagarFactura + "." + SaldoVencidoTextoFinal + " \\nFecha limite de pago: " + fechaLimitePago;
           } 
         } else {
           
/* 270 */           mensaje = "Estimado cliente, no tienes saldo pendiente de pago.";
         }
       
/* 273 */       } catch (Exception e) {
/* 274 */         mensaje = "Error: Consulta OPEN {Excepcion: " + e.toString() + "}, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
     } 
 
     
/* 279 */     if (idpais.equals("506")) {
       
/* 281 */       String fechaLimitePago = null;
/* 282 */       String totalPagarFactura = null;
/* 283 */       String totalSaldoPendiente = null;
/* 284 */       String SaldoVencidoTexto1 = " \\nSaldo vencido C.";
/* 285 */       String SaldoVencidoTexto2 = ".";
/* 286 */       String SaldoVencidoTextoFinal = "";
       
/* 288 */       ConsultasBSCSCR conBSCS = new ConsultasBSCSCR();
       
       try {
/* 291 */         String[][] datos = conBSCS.DesgloseFijo(id);
/* 292 */         if (datos != null && datos[0][0] != null) {
/* 293 */           if (datos[0][0].equals("ERROR")) {
/* 294 */             throw new Exception(datos[0][1]);
           }
/* 296 */           fechaLimitePago = datos[0][1];
/* 297 */           totalPagarFactura = formatter.format(Double.parseDouble(datos[0][2]));
/* 298 */           totalSaldoPendiente = formatter.format(Double.parseDouble(datos[0][4]));
/* 299 */           if (!totalSaldoPendiente.equals("0.00")) {
/* 300 */             SaldoVencidoTextoFinal = SaldoVencidoTexto1 + totalSaldoPendiente + SaldoVencidoTexto2;
           }
           
/* 303 */           if (canal.equals("SMS")) {
/* 304 */             mensaje = "El total a pagar de su factura con fecha limite de pago " + fechaLimitePago + " es de C." + totalPagarFactura + "." + SaldoVencidoTextoFinal;
           } else {
/* 306 */             mensaje = "Numero de servicio: " + id + " \\nTotal a pagar C." + totalPagarFactura + "." + SaldoVencidoTextoFinal + " \\nFecha limite de pago: " + fechaLimitePago;
           } 
         } else {
           
/* 310 */           mensaje = "Estimado cliente, no tienes saldo pendiente de pago.";
         }
       
/* 313 */       } catch (Exception e) {
/* 314 */         mensaje = "Error: Consulta BSCS {Excepcion: " + e.toString() + "}, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
     } 
     
/* 318 */     if (idpais.equals("507")) {
       
/* 320 */       String fechaLimitePago = null;
/* 321 */       String totalPagarFactura = null;
/* 322 */       String totalSaldoPendiente = null;
/* 323 */       String SaldoVencidoTexto1 = " \\nSaldo vencido B/.";
/* 324 */       String SaldoVencidoTexto2 = ".";
/* 325 */       String SaldoVencidoTextoFinal = "";
       
/* 327 */       ConsultasOPEN con = new ConsultasOPEN(idpais);
       
       try {
/* 330 */         String[][] datos = con.DesgloseFijo(id);
/* 331 */         if (datos != null && datos[0][0] != null) {
/* 332 */           if (datos[0][0].equals("ERROR")) {
/* 333 */             throw new Exception(datos[0][1]);
           }
/* 335 */           fechaLimitePago = datos[0][1];
/* 336 */           totalPagarFactura = formatter.format(Double.parseDouble(datos[0][2]));
/* 337 */           totalSaldoPendiente = formatter.format(Double.parseDouble(datos[0][3]));
/* 338 */           if (!totalSaldoPendiente.equals("0.00")) {
/* 339 */             SaldoVencidoTextoFinal = SaldoVencidoTexto1 + totalSaldoPendiente + SaldoVencidoTexto2;
           }
/* 341 */           if (canal.equals("SMS")) {
/* 342 */             mensaje = "El total a pagar de su factura con fecha limite de pago " + fechaLimitePago + " es de B/." + totalPagarFactura + "." + SaldoVencidoTextoFinal;
           } else {
/* 344 */             mensaje = "Numero de servicio: " + id + " \\nTotal a pagar B/." + totalPagarFactura + "." + SaldoVencidoTextoFinal + " \\nFecha limite de pago: " + fechaLimitePago;
           } 
         } else {
           
/* 348 */           mensaje = "Estimado cliente, no tienes saldo pendiente de pago.";
         }
       
/* 351 */       } catch (Exception e) {
/* 352 */         mensaje = "Error: Consulta OPEN {Excepcion: " + e.toString() + "}, mensaje no enviado id: " + id + ", idpais: " + idpais;
       } 
     } 
 
     
/* 357 */     return mensaje;
   }
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\DesgloseSaldoFijoResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */