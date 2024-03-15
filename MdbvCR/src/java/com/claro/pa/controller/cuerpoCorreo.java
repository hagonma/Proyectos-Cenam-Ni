/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.claro.pa.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
*
* @author AVP GUATEMALA
*/
public class cuerpoCorreo {

    //private static String path = "C:/Users/AVP GUATEMALA/Documents/mdbcr/img/";
    //private static String path = "//app//appv2//img//";
    private static String path = "//app//weblogicChRe//IMAGENES//img//Fijos-CR//";
    
    public Select sel = new Select();

    public String cuerpoFijoRenovadoPro(JSONObject respuesta) throws JSONException, Exception {
        String fechaContratacion = respuesta.getString("FECHA_CONTRATACION");
        String correoCliente = respuesta.getString("CORREO_CLIENTE");
        String equipo = respuesta.getString("EQUIPO");
        String fechaPago = respuesta.getString("PAGO");
        String numeroTelefono = respuesta.getString("CLIENTE");
        String parseFECHA = "";
        
        if (fechaPago.isEmpty() || fechaPago == null) {
            parseFECHA = fechaPago;
        }else if (fechaPago.contains("-")){
           String[] mesPago = fechaPago.split("-");
            parseFECHA = mesPago[0];
        }else if (fechaPago.contains("/")){
           String[] mesPago = fechaPago.split("/");
            parseFECHA = mesPago[0];
        }
        
        String vigencia = respuesta.getString("VIGENCIA_CONTRATO");
        String servicio = respuesta.getString("SERVICIO");
        String cuota = respuesta.getString("CUOTA");
        String pais = respuesta.getString("IDPAIS");
        String clarovideo = respuesta.getString("CLARO_VIDEO");
        String internet = respuesta.getString("INTERNET");
        String llamada = respuesta.getString("LLAMADA");
        String television = respuesta.getString("TELEVISION");
        String fox = respuesta.getString("FOX");
        String hbo = respuesta.getString("HBO");
        String num_cc = respuesta.getString("NUM_CC");
        String link = respuesta.getString("LINK");
        String factura = respuesta.getString("FACTURA");
        String marcacion = respuesta.getString("MARCACION");
        String fechaInstalacion = "0";
//      String fechaInstalacion = respuesta.getString("FECHA_INSTALACION");
        String pais_smpp = respuesta.getString("IDPAIS_SMPP");
        String ilimitadas_claro = respuesta.getString("ILIMITADAS_A_CLARO");
        String planes = respuesta.getString("TIPO_PLAN_TV");
        String tipo_plan_tv="";
        String planUc="";
        if (planes.contains("-")){
            String[] planTvIn = planes.split("-");
            tipo_plan_tv = planTvIn[1].toString();
            planUc = planTvIn[0].toString();
        }else{
            tipo_plan_tv=planes;    
        }
        String velocidad = respuesta.getString("VELOCIDAD");
        //String numContrato = respuesta.getString("CONTRATO");
        String numContrato = respuesta.getString("CLIENTE");
        String numReferencia = respuesta.getString("NUMERO_REFERENCIA");
        double rentaHonduras;
        String servicioHtml = "";
        String cuotaMensualHtml = "";
        String fechaPagoHtml = "";
        String fechaContratacionHtml = "";
        String vigenciaContratoHtml = "";
        String fechaInstalacionhtml = "";
        String equipohtml = "";
        String clarovideohtml = "";
        String internethtml = "";
        String llamadahtml = "";
        String televisionhtml = "";
        String foxhtml = "";
        String hbohtml = "";
        String returnMessage = "";
        String extraServicesHtml = "";
        String extraServicesImgUrl = "";
        String mainServicesHtml = "";
        String mainServicesDescHtml = "";
        String mainServiceImgUrl = "";
        //String fechaRenovacion = respuesta.getString("FECHA_RENOVACION");
        String pais_id = respuesta.getString("PAIS_ID");
        //String fechaContratacion1 = respuesta.getString("FECHA_CONTRATACION1");
        //String tmcode = respuesta.getString("TMCODE");
        String claromusicahtml = "";
        String sinfronterahtml = "";
        String smshtml = "";
        String redeshtml = "";
        String telefonohtml = "";
        String tvhtml="";
        String mundohtml="";
        /*   String descriptLlamadas = sel.getLlamadas(tmcode, pais);
        String descriptMensajes = sel.getMensajes(tmcode, pais);
        //  String descriptRedes = sel.getRedes(tmcode, pais);
        String descriptInternet = sel.getInternet(tmcode, pais);
        String descriptSinFronteras = sel.getSinFronteras(tmcode, pais);
        String descriptClaroMusica = sel.getClaroMusica(tmcode, pais);*/
        //String descriptEquipo = sel.getEquipo(equipo, pais);
        String descriptEquipo = "";
        //String nombrePlan = sel.getNombrePlan(tmcode, pais);
        String htmlMonto;
        String htmlFechaPago;
        String htmlFOX = "";
        String htmlHBO = "";
        String htmlClaroVideo = "";
        
        EnvioCorreo envio = new EnvioCorreo();
        String host = envio.getHost(Integer.parseInt(pais_smpp));
        String from = envio.getFrom(Integer.parseInt(pais_smpp));
        String descriptRedes = "" ;
        //String descriptRedes = "hola"; 
        Map<String, String> inlineImages = new HashMap<String, String>();
      
        String descripcionTelefono = "";
        String descripciontipoplan = "";
        //String descripciontelevisionhtml = "TELEVISION " + descripciontipoplan;
        String descripciontelevisionhtml = "";
        String descripcionInternet =  "";
        descriptRedes = descripcionInternet;
        String tv = television;
        String telefono = llamada;
        String  inter = internet;
        String parteArriba = "<td></td>";
        String parteAbajo ="<td></td>";
        String detalleCuota = "";
        String detalleNumeroReferencia = "";
        String detalleVigencia = "";

//            detalleVigencia ="          <tr>\n"
//                + "            <th>Vigencia de contrato:</th>\n"
//                + "            <td> " + vigencia + " Meses</td> \n"
//                + "          </tr>          \n";

            detalleCuota ="          <tr>\n"
                + "            <th>Cuota mensual(Sin impuestos):</th>\n"
                + "            <td> CRC. " + cuota + "</td> \n"
                + "          </tr>          \n";
        
        String detallePlanImagenes = "";
        
        if (telefono.equals("1")&&!numeroTelefono.equals("0")) {
                detalleNumeroReferencia =           "         <tr>\n"
                + "            <th>N&#250;mero de Tel&#233;fono:</th>\n"
                + "            <td>" + numeroTelefono + "</td> \n"
                + "          </tr>\n";       
            }
        
        if (velocidad.equals("0")){
            velocidad = "";
        }
       
        String plus="";

        
        if (inter.equals("1")) {
            parteArriba +="<br><center><td style=\"max-width: 500px;\" width=500px;>\n";  
             
            /*if((tv.equals("1"))||(telefono.equals("1"))){
                    plus= "<img src=\"cid:Plus\"></center></p>\n";
                    inlineImages.put("Plus", this.path+"CR//plus.png");
            }else{
                    plus="";
                 }*/
            
            switch (velocidad) {
                case "50Mbps":
                    parteArriba += "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                        //inlineImages.put("internetfija", "//app//appv2//img//CR//UC1.png");
                        inlineImages.put("internetfija", this.path+"CR//UC1.png");
                    break;
                case "200Mbps":
                    parteArriba += "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                        //inlineImages.put("internetfija", "//app//appv2//img//CR//UC2.png");
                        inlineImages.put("internetfija", this.path+"CR//UC2.png");
                    break;
                case "400Mbps":
                    parteArriba += "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                        //inlineImages.put("internetfija", "//app//appv2//img//CR//UC3.png");
                        inlineImages.put("internetfija", this.path+"CR//UC3.png");
                    break;
                case "600Mbps":
                    parteArriba += "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                        //inlineImages.put("internetfija", "//app//appv2//img//CR//UC4.png");
                        inlineImages.put("internetfija", this.path+"CR//UC4.png");
                    break;
                    case "1024Mbps":
                    parteArriba += "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                        //inlineImages.put("internetfija", "//app//appv2//img//CR//UC5.png");
                        inlineImages.put("internetfija", this.path+"CR//UC5.png");
                    break;
                default:
                    parteArriba += "<p><center><img src=\"cid:InternetGenerico\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                    //inlineImages.put("InternetGenerico", "//app//appv2//img//InternetGenerico.png");
                    inlineImages.put("InternetGenerico", this.path+"InternetGenerico.png");
                    
                    parteArriba +=  "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + velocidad + "</center>\n"
                + "<br></td></center>\n";
                    break;
                    
            }
            parteArriba +=  "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionInternet + "</center>\n"
                + "<br></td></center>\n";
            
                    if((tv.equals("1"))||(telefono.equals("1"))){
                    parteArriba +="<br><center><td style=\"max-width: 500px;\" width=500px;>";
                    parteArriba += "<p><center><img src=\"cid:Plus\"></center></p>\n";
                    inlineImages.put("Plus", this.path+"CR//plus.png");
                    parteArriba +=  "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\"> </center>\n"
                    + "<br></td></center>\n";
                    }
                  
        }
        
        if (tv.equals("1")) {
            parteArriba += "<br><center><td style=\"max-width: 500px;\" width=500px;>\n";
            /*if(telefono.equals("1")){
                    plus= "<img src=\"cid:Plus\"></center></p>\n";
                    inlineImages.put("Plus", this.path+"CR//plus.png");
            }else{
                    plus="";
                 }*/
            if(tipo_plan_tv.equals("1")){
                descripciontelevisionhtml = "";
                parteArriba += "<p><center><img src=\"cid:TelevisionAvanzada\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                //inlineImages.put("TelevisionAvanzada", "//app//appv2//img//SATAV.png");
                inlineImages.put("TelevisionAvanzada", this.path+"SATAV.png");
            }else if(tipo_plan_tv.equals("2")){
                parteArriba += "<p><center><img src=\"cid:TelevisionAvanzadaPlus\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                descripciontelevisionhtml= "";
                //inlineImages.put("TelevisionAvanzadaPlus", "//app//appv2//img//SATAVP.png");
                inlineImages.put("TelevisionAvanzadaPlus", this.path+"SATAVP.png");
            }else if(tipo_plan_tv.equals("3")){
                parteArriba += "<p><center><img src=\"cid:TelevisionBasica\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                descripciontelevisionhtml= "";
                //inlineImages.put("TelevisionBasica", "//app//appv2//img//SATB.png");
                inlineImages.put("TelevisionBasica", this.path+"SATB.png");
            }else if(tipo_plan_tv.equals("4")){
                parteArriba += "<p><center><img src=\"cid:TelevisionAvanzada\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                descripciontelevisionhtml= "";
                //inlineImages.put("TelevisionAvanzada", "//app//appv2//img//CR//TV1.png");
                inlineImages.put("TelevisionAvanzada", this.path+"CR/TV1.png");
            }else if(tipo_plan_tv.equals("5")){
                parteArriba += "<p><center><img src=\"cid:TelevisionAvanzadaPlus\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                descripciontelevisionhtml= "";
                //inlineImages.put("TelevisionAvanzadaPlus", "//app//appv2//img//CR//TV2.png");
                inlineImages.put("TelevisionAvanzadaPlus", this.path+"CR/TV2.png");
            }else if(tipo_plan_tv.equals("7")){
                parteArriba += "<p><center><img src=\"cid:TelevisionMiniBasica\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                descripciontipoplan= "";
                //inlineImages.put("TelevisionMiniBasica", "//app//appv2//img//SATMIN.png");
                inlineImages.put("TelevisionMiniBasica", this.path+"SATMIN.png");
            }else{
                parteArriba += "<p><center><img src=\"cid:TelevisionGenerico\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\">"+plus+"</center></p>\n";
                //descripciontipoplan= "Generico";
                //inlineImages.put("TelevisionGenerico", "//app//appv2//img//TelevisionGenerico.png");
                inlineImages.put("TelevisionGenerico", this.path+"TelevisionGenerico.png");
            }
            parteArriba += "<br></td></center>\n";
            
            if(telefono.equals("1")){
                    parteArriba +="<br><center><td style=\"max-width: 500px;\" width=500px;>\n";
                    parteArriba += "<p><center><img src=\"cid:Plus\"></center></p>\n";
                    inlineImages.put("Plus", this.path+"CR//plus.png");
                    parteArriba +=  "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\"> </center>\n"
                    + "<br></td></center>\n";
            }
        }
        
        if (telefono.equals("1")) {
            parteArriba += "<br><center><td style=\"max-width: 500px;\" width=500px;>\n"
                + "<p><center><img src=\"cid:ilimitadas\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\"></center></p>\n"
                + "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionTelefono + "</center>\n"
                + "<br></td></center>\n";
            //inlineImages.put("ilimitadas", "//app//appv2//img//CR//LineaFija.png");
            inlineImages.put("ilimitadas", this.path+"CR//LineaFija.png");
        }
        
        if (clarovideo.equals("1")) {
            parteAbajo +=  "<br><center><td style=\"max-width: 500px;\" width=500px;>\n"
                    + "<p><center><img src=\"cid:clarovideo\" style=\"width: 250px; height: 90px;\" width=\"350px\" height=\"190\"></center></p>\n"
                    + "<br></td></center>\n"
                    + "\n";
            //inlineImages.put("clarovideo", "//app//appv2//img//clarovideo.png");
            inlineImages.put("clarovideo", this.path+"clarovideo.png");
        }
        if (fox.equals("1")) {
            parteAbajo +="<br><center><td style=\"max-width: 500px;\" width=500px;>\n"
                    + "<p><center><img src=\"cid:fox\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n"                    
                    + "<br></td></center>\n";
            //inlineImages.put("fox", "//app//appv2//img//fox.png");
            inlineImages.put("fox", this.path+"fox.png");
        }
        if (hbo.equals("1")) {
            parteAbajo +="<br><center><td style=\"max-width: 500px;\" width=500px;>\n"
                    + "<p><center><img src=\"cid:hbo\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n"
                    + "<br></td></center>\n";
            //inlineImages.put("hbo", "//app//appv2//img//hbo.png");
            inlineImages.put("hbo", this.path+"hbo.png");
        }
        if (!descriptEquipo.equals("0")) {
            equipo = descriptEquipo;
        }
        //     String numeroContrato = "2,113,086";
        //    String Numeroreferencia= "63114967//62244124";

        if (pais_id.equals("503")) {
            htmlMonto = "";
            htmlFechaPago ="";
        } else {
            htmlMonto = "<tr>\n"
                    + "            <th>Cuota mensual:</th>\n"
                    + "            <td>" + cuota + "</td> \n"
                    + "          </tr>\n";
            htmlFechaPago = "<tr>\n"
                    + "            <th>Fecha límite de pago:</th>\n"
                    + "            <td>" + parseFECHA + "</td> \n"
                    + "          </tr>\n";
        }
                StringBuffer body = new StringBuffer("<html><head>\n"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html\">\n"
                + "<meta charset=\"utf-8\">\n"
                + "        <title>Formato Correo</title>\n"
                + "        <style type=\"text/css\">\n"
                + "          body{\n"
                + "            font-family: Yu Gothic Medium;\n"
                + "            font-size: 18px;\n"
                + "          }\n"
                + "          div{\n"
                + "            font-family: Yu Gothic Medium;\n"
                + "            font-size: 18px;\n"
                + "          }\n"
                + "          .nombre{\n"
                + "            font-weight: bold;\n"
                + "          }\n"
                + "          p{\n"
                + "            text-align: justify;\n"
                + "          }\n"
                + "          th{\n"
                + "            text-align: right;\n"
                + "            border-bottom: 1px solid #0097a9; \n"
                + "          }\n"
                + "          .final{\n"
                + "            text-align: justify;\n"
                + "          }\n"
                + "          #detalles tr td{\n"
                + "            border-bottom: 1px solid #0097a9;\n"
                + "            font-family: Yu Gothic light;\n"
                + "          }\n"
                + "          #imagenes>td{\n"
                + "            padding-left: 30px;\n"
                + "            border-bottom: 1px solid #0097a9;\n"
                + "            min-width: 33%;\n"
                + "          }\n"
                + "          .contacto{\n"
                + "            vertical-align: super;\n"
                + "            display: inline-block;\n"
                + "          }\n"
                + "          .vinculo{\n"
                + "           font-weight: bold;\n"
                + "          }\n"
                + "          .contactotexto{\n"
                + "            vertical-align: super;\n"
                + "            display: inline-block;\n"
                + "          }\n"  
                + "        </style>\n"
                + "        <link href=\"./styles/fonts.css\" rel=\"stylesheet\">        \n"
                + "    </head>\n"
                + "    <div style=\"max-width: 1000px;\">\n"
                + "      <body width=\"800px;\">\n"
                + "      <img src=\"cid:header2Fijo\" style=\"width: 100%; max-width: 1280px;\">\n"
                + "      <div style=\"margin-left: 40px; width: 80%;\">\n"
                + "        <span class=\"nombre\">Tu experiencia Claro comienza aqu&#237;</span> con la red m&#225;s r&#225;pida de Telecomunicaciones de Centroam&#233;rica.\n"
                + "        <p>A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s datos comun&#237;cate al <span class=\"nombre\">" + num_cc + "</span> o ac&#233;rcate a tu Tienda Claro m&#225;s cercana.</p>\n"
                + "      </div>\n"
                + "      <center>\n"
                + "      <div>\n"
                + "        <table width=\"80%\" id=\"detalles\">\n"
                + "          <tr>\n"
                + "            <th>Servicio adquirido:</th>\n"
                + "            <td>" + servicio + "</td> \n"
                + "          </tr>\n"
        
                        +detalleCuota
                        /*                + "          <tr>\n"
                  + "            <th>Cuota mensual:</th>\n"
                + "            <td>" + cuota + "</td> \n"
                + "          </tr>          \n"*/
                + "          <tr>\n"
                /*+ "          <tr>\n"
                + "            <th>Fecha limite de pago:</th>\n"
                + "            <td>" + parseFECHA + "</td> \n"
                + "          </tr>     \n"*/
                + "          <tr>\n"
                + "            <th>Fecha de contrataci&#243;n:</th>\n"
                + "            <td>" + fechaContratacion + "</td> \n"
                + "          </tr>\n"
                        +detalleVigencia
                + "         <tr>\n"
                + "            <th>Referencia de pago:</th>\n"
                + "            <td>" + numContrato + "</td> \n"
                + "          </tr>\n"
              +detalleNumeroReferencia
                        /*          + "         <tr>\n"
                + "            <th>N&#250;mero de Tel&#233;fono:</th>\n"
                + "            <td>" + numReferencia + "</td> \n"
                + "          </tr>\n"*/
                + "          <tr>\n"
                + "            <th>Env&#237;o factura electr&#243;nica:</th>\n"
                + "            <td><a href=\"" + factura + "\">" + correoCliente + "</a></td> \n"
                + "          </tr>          \n"
                + "        </table>           \n"
                + "      </div>\n"
                + "      </center>\n"
                + "      <br>\n"
                + "      <div>\n"
                + "        <div style=\"text-align: center; background-color: #e6e6e6\">\n"
                + detallePlanImagenes
                + "          <br>\n"
                + "          <center>\n"
                + "            <table width=\"100%\" style=\"background-color: #e6e6e6;\">\n"
                + "              <tr>\n"
                        + "              " + parteArriba + ""
         
                + "              </tr>\n"
                + "              <tr>\n"
                + "              " + parteAbajo + ""
                + "              </tr>\n"
                + "             </table>\n"
               + "             <br><br>  "
                + "      </div>\n"
                + "      <a href=\"" + link + "\"><img src=\"cid:footer\" style=\"width: 100%;\"></a>\n"
                + "      </div>            \n"
                + "                  <img src=\"cid:blanco.png\" style=\"width: 1px; height: 1px;\" width=\"1px\" height=\"1\">\n"
                + "    </body>\n"
                + "    </div>\n"
                + "</html>\n"
                + "\n"
                + "");
        switch (pais_id) {
            case "502":
                inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footer.png");
                break;
            case "503":
                 inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//HN VF.JPG");
                break;
            case "504":
                inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//HN VF.JPG");
                break;
            case "505":
                 inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//nuevoFooterNI.png");
                break;
            case "506":
                //inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footerCR.png");
                //inlineImages.put("footer", "//app//appv2//img//CR//FooterCR.png");
                inlineImages.put("footer", this.path+"CR//FooterCR.png");
                break;
            case "507":
            //inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//Footer PA.png");
                inlineImages.put("footer", "//app//appv2//img//image.png");
                break;
            default:
                // inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footer.png");
                //inlineImages.put("footer", "//app//appv2//img//image.png");
                inlineImages.put("footer", this.path+"image.png");
                //inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//Footer PA.png");
        }
        /*inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo.png");
        inlineImages.put("detalle_plan_adquirido", "//app//weblogicChRe//IMAGENES//img//detalle_plan_adquirido.png");
        inlineImages.put("ilimitadas", "//app//weblogicChRe//IMAGENES//img//Iconos Claro-06.png");
        inlineImages.put("televisionfija", "//app//weblogicChRe//IMAGENES//img//Iconos Claro-04.png");
        inlineImages.put("internetfija", "//app//weblogicChRe//IMAGENES//img//logointernet.png");
        inlineImages.put("clarovideo", "//app//weblogicChRe//IMAGENES//img//clarovideo2.png");
        inlineImages.put("hbo", "//app//weblogicChRe//IMAGENES//img//hbo2.png");
        inlineImages.put("fox", "//app//weblogicChRe//IMAGENES//img//fox2.png");*/
        //inlineImages.put("header2Fijo", "//app//appv2//img//header2Fijo.png");
        inlineImages.put("header2Fijo", this.path+"header2Fijo.png");

        //inlineImages.put("detalle_plan_adquirido", "//app//appv2//img//detalle_plan_adquirido.png");
        
        //inlineImages.put("televisionfija", "//app//appv2//img//televisionfija.png");
        //inlineImages.put("internetfija", "//app//appv2//img//internetfija.png");
        
         
        //DESCOMENTAR inlineImages.put("header3", "//app//weblogicChRe//IMAGENES//img//header3.png");
        //DESCOMENTAR inlineImages.put("detalle_plan_adquirido", "//app//weblogicChRe//IMAGENES//img//detalle_plan_adquirido.png");
        //inlineImages.put("redes_sociales", "//app//weblogicChRe//IMAGENES//img//redes_sociales.png");
        //inlineImages.put("llamadas_ilimitadas", "//app//weblogicChRe//IMAGENES//img//llamadas_ilimitadas.png");
        //inlineImages.put("mensajes", "//app//weblogicChRe//IMAGENES//img//mensajes.png");
        //inlineImages.put("internet", "//app//weblogicChRe//IMAGENES//img//internet.png");
        //inlineImages.put("sin_fronteras", "//app//weblogicChRe//IMAGENES//img//sin_fronteras.png");
        // inlineImages.put("claro_musica", "//app//weblogicChRe//IMAGENES//img//claro_musica.png");
        try { 
           // correoCliente = "gustavoa.oliva@claro.com.gt";
            //System.out.println(body.toString());
            envio.send(host, from, correoCliente, body.toString(), inlineImages);
            return "Email sent.";
        } catch (Exception ex) {
            return "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        }
    }
  
}
