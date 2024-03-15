package Controller;

import Controller.EnvioCorreo;
import Model.Select;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;


public class cuerpoCorreo
{
/*   21 */   public Select sel = new Select();

  
  public String cuerpo(JSONObject respuesta) throws JSONException, Exception {
/*   25 */     String htmlMonto, htmlFechaPago, correoCliente = respuesta.getString("CORREO_CLIENTE");
/*   26 */     String equipo = respuesta.getString("EQUIPO");
/*   27 */     String fechaPago = respuesta.getString("FECHA_PAGO");
/*   28 */     String vigencia = respuesta.getString("VIGENCIA_CONTRATO");
/*   29 */     String servicio = respuesta.getString("SERVICIO");
/*   30 */     String cuota = respuesta.getString("CUOTA");
/*   31 */     String fechaRenovacion = respuesta.getString("FECHA_RENOVACION");
/*   32 */     String pais = respuesta.getString("IDPAIS");
/*   33 */     String pais_id = respuesta.getString("PAIS_ID");
/*   34 */     String marcacion = respuesta.getString("MARCACION");
/*   35 */     String num_cc = respuesta.getString("NUM_CC");
/*   36 */     String link = respuesta.getString("LINK");
/*   37 */     String factura = respuesta.getString("FACTURA");
/*   38 */     String fechaContratacion1 = respuesta.getString("FECHA_CONTRATACION1");
/*   39 */     String tmcode = respuesta.getString("TMCODE");
/*   40 */     String claromusicahtml = "";
/*   41 */     String internethtml = "";
/*   42 */     String llamadahtml = "";
/*   43 */     String sinfronterahtml = "";
/*   44 */     String smshtml = "";
/*   45 */     String redeshtml = "";
/*   46 */     String descriptLlamadas = this.sel.getLlamadas(tmcode, pais);
/*   47 */     String descriptMensajes = this.sel.getMensajes(tmcode, pais);
/*   48 */     String descriptRedes = this.sel.getRedes(tmcode, pais);
/*   49 */     String descriptInternet = this.sel.getInternet(tmcode, pais);
/*   50 */     String descriptSinFronteras = this.sel.getSinFronteras(tmcode, pais);
/*   51 */     String descriptClaroMusica = this.sel.getClaroMusica(tmcode, pais);
/*   52 */     String descriptEquipo = this.sel.getEquipo(equipo, pais);
/*   53 */     String nombrePlan = this.sel.getNombrePlan(tmcode, pais);


    
/*   57 */     EnvioCorreo envio = new EnvioCorreo();
/*   58 */     String host = envio.getHost(Integer.parseInt(pais));
/*   59 */     String from = envio.getFrom(Integer.parseInt(pais));
/*   60 */     Map<String, String> inlineImages = new HashMap<>();
    
/*   62 */     if (!descriptRedes.equals("0")) {
/*   63 */       redeshtml = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:redes_sociales\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptRedes + "</center>\n" + "<br></td></center>\n";
    
    }
    else {
      
/*   68 */       redeshtml = "<td></td>";
    } 
/*   70 */     if (!descriptInternet.equals("0")) {
/*   71 */       internethtml = "<br><center><td style=\"max-width: 500px;\" width=\"500px\">\n<p><center><img src=\"cid:internet\" style=\"width: 100px; height: 100px;\" width=\"100px\" height=\"100\"></center></p>\n<center style=\"font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptInternet + "</center>\n" + "<br></td></center> \n";
    
    }
    else {
      
/*   76 */       internethtml = "<td></td>";
    } 
/*   78 */     if (!descriptSinFronteras.equals("0")) {
/*   79 */       sinfronterahtml = "<center><td style=\"max-width: 500px;\" width=\"500px\">\n<p><center><img src=\"cid:sin_fronteras\" style=\"width: 140px; height: 120px;\" width=\"140px\" height=\"120\"></center></p>\n<center style=\"font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptSinFronteras + "</center>\n" + "</td></center>\n";
    
    }
    else {
      
/*   84 */       sinfronterahtml = "<td></td>";
    } 
/*   86 */     if (!descriptMensajes.equals("0")) {
/*   87 */       smshtml = "<br><center><td style=\"max-width: 500px;\" width=\"500px\">\n<p><center><img src=\"cid:mensajes\" style=\"width: 100px; height: 80px;\" width=\"100px\" height=\"80\"></center></p>\n<center style=\"font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptMensajes + "</center>\n" + "<br></td></center>\n";
    
    }
    else {
      
/*   92 */       smshtml = "<td></td>";
    } 
/*   94 */     if (!descriptLlamadas.equals("0")) {
/*   95 */       llamadahtml = "<br><center><td style=\"max-width: 500px;\" width=\"500px\">\n<p><center><img src=\"cid:llamadas_ilimitadas\" style=\"width: 112px; height: 90px;\" width=\"112px\" height=\"90\"></center></p>\n<center style=\"font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptLlamadas + "</center>\n" + "<br></td></center>\n";
    
    }
    else {
      
/*  100 */       llamadahtml = "<td></td>";
    } 
    
/*  103 */     if (!descriptClaroMusica.equals("0")) {
/*  104 */       claromusicahtml = "<br><center><td style=\"max-width: 500px;\" width=\"500px\">\n<p><center><img src=\"cid:claro_musica\" style=\"width: 185px; height: 80px;\" width=\"185px\" height=\"80\"></center></p>\n<center style=\"font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptClaroMusica + "</center>\n" + "<br></td></center>\n";
    
    }
    else {
      
/*  109 */       claromusicahtml = "<td></td>";
    } 
    
/*  112 */     if (!descriptEquipo.equals("0")) {
/*  113 */       equipo = descriptEquipo;
    }
    
/*  116 */     if (!nombrePlan.equals("0")) {
/*  117 */       servicio = nombrePlan;
    }
/*  119 */     if (pais_id.equals("503")) {
/*  120 */       htmlMonto = "";
/*  121 */       htmlFechaPago = "";
    } else {
      
/*  124 */       htmlMonto = "<tr>\n            <th>Cuota mensual:</th>\n            <td>" + cuota + "</td> \n" + "          </tr>\n";


      
/*  128 */       htmlFechaPago = "<tr>\n            <th>Fecha m&#225;xima de pago:</th>\n            <td>" + fechaPago + "</td> \n" + "          </tr>\n";
    } 


    
/*  134 */     StringBuffer body = new StringBuffer("<html><head>\n<meta http-equiv=\"Content-Type\" content=\"text/html\">\n<meta charset=\"utf-8\">\n        <title>Formato Correo</title>\n        <style type=\"text/css\">\n          body{\n            font-family: Yu Gothic Medium;\n            font-size: 18px;\n          }\n          div{\n            font-family: Yu Gothic Medium;\n            font-size: 18px;\n          }\n          .nombre{\n            font-weight: bold;\n          }\n          p{\n            text-align: justify;\n          }\n          th{\n            text-align: right;\n            border-bottom: 1px solid #0097a9; \n          }\n          .final{\n            text-align: justify;\n          }\n          #detalles tr td{\n            border-bottom: 1px solid #0097a9;\n            font-family: Yu Gothic light;\n          }\n          #imagenes>td{\n            padding-left: 30px;\n            border-bottom: 1px solid #0097a9;\n            min-width: 33%;\n          }\n          .contacto{\n            vertical-align: super;\n            display: inline-block;\n          }\n          .vinculo{\n           font-weight: bold;\n          }\n          .contactotexto{\n            vertical-align: super;\n            display: inline-block;\n          }\n        </style>\n        <link href=\"./styles/fonts.css\" rel=\"stylesheet\">        \n    </head>\n    <div style=\"max-width: 1000px;\">\n      <body width=\"800px;\">\n      <img src=\"cid:header3\" style=\"width: 100%; max-width: 1280px;\">\n      <div style=\"margin-left: 40px; width: 80%;\">\n        <span class=\"nombre\">Tu experiencia Claro comienza aqu&#237;</span> con la red m&#225;s r&#225;pida de Telecomunicaciones de Centroam&#233;rica.\n        <p>A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s datos comun&#237;cate al <span class=\"nombre\">" + num_cc + "</span> o ac&#233;rcate a tu Tienda Claro m&#225;s cercana.</p>\n" + "      </div>\n" + "      <center>\n" + "      <div>\n" + "        <table width=\"80%\" id=\"detalles\">\n" + "          <tr>\n" + "            <th>Servicio adquirido:</th>\n" + "            <td>" + servicio + "</td> \n" + "          </tr>\n" + "              " + htmlMonto + "" + "              " + htmlFechaPago + "" + "          <tr>\n" + "            <th>Fecha contrataci&#243;n:</th>\n" + "            <td>" + fechaContratacion1 + "</td> \n" + "          </tr>          \n" + "          <tr>\n" + "            <th>Vigencia contrato:</th>\n" + "            <td>" + vigencia + "</td> \n" + "          </tr>     \n" + "          <tr>\n" + "            <th>Fecha renovaci&#243;n:</th>\n" + "            <td>" + fechaRenovacion + "</td> \n" + "          </tr>\n" + "          <tr>\n" + "            <th>Env&#237;o factura electr&#243;nica:</th>\n" + "            <td><a href=\"" + factura + "\">" + correoCliente + "</a></td> \n" + "          </tr>          \n" + "        </table>           \n" + "      </div>\n" + "      </center>\n" + "      <br>\n" + "      <div>\n" + "        <div style=\"text-align: center; background-color: #e6e6e6\">\n" + "          <br><br><center><img src=\"cid:detalle_plan_adquirido\" style=\"width: 600px; height: 100px;\" width=\"600px\" height=\"100\"></center>            \n" + "          <br>\n" + "          <center>\n" + "            <table width=\"100%\" style=\"background-color: #e6e6e6;\">\n" + "              <tr>\n" + "              " + smshtml + "" + "              " + llamadahtml + "" + "              " + internethtml + "" + "              </tr>\n" + "              <tr>\n" + "              " + sinfronterahtml + "" + "              " + redeshtml + "" + "              " + claromusicahtml + "" + "              </tr>\n" + "             </table>\n" + "             <br><br>  " + "      </div>\n" + "      <a href=\"" + link + "\"><img src=\"cid:footer\" style=\"width: 100%;\"></a>\n" + "      </div>            \n" + "                  <img src=\"cid:blanco.png\" style=\"width: 1px; height: 1px;\" width=\"1px\" height=\"1\">\n" + "    </body>\n" + "    </div>\n" + "</html>\n" + "\n" + "");


    
/*  651 */     switch (pais_id) {
      case "502":
/*  653 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footer.png");
        break;
      case "503":
/*  656 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footerSV.png");
        break;
      case "504":
/*  659 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footerHN.png");
        break;
      case "505":
/*  662 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footerNI.png");
        break;
      case "506":
/*  665 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footerCR.png");
        break;
      default:
/*  668 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footer.png");
        break;
    } 


    
/*  679 */     inlineImages.put("header3", "//app//weblogicChRe//IMAGENES//img//header3.png");
/*  680 */     inlineImages.put("detalle_plan_adquirido", "//app//weblogicChRe//IMAGENES//img//detalle_plan_adquirido.png");
/*  681 */     inlineImages.put("redes_sociales", "//app//weblogicChRe//IMAGENES//img//redes_sociales.png");
/*  682 */     inlineImages.put("llamadas_ilimitadas", "//app//weblogicChRe//IMAGENES//img//llamadas_ilimitadas.png");
/*  683 */     inlineImages.put("mensajes", "//app//weblogicChRe//IMAGENES//img//mensajes.png");
/*  684 */     inlineImages.put("internet", "//app//weblogicChRe//IMAGENES//img//internet.png");
/*  685 */     inlineImages.put("sin_fronteras", "//app//weblogicChRe//IMAGENES//img//sin_fronteras.png");
/*  686 */     inlineImages.put("claro_musica", "//app//weblogicChRe//IMAGENES//img//claro_musica.png");
    
    try {
/*  689 */       EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/*  690 */       return "Email sent.";
/*  691 */     } catch (Exception ex) {

      
/*  694 */       return "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    } 
  }


  public String cuerpoFijoRenovado(JSONObject respuesta) throws JSONException, Exception {
/*  713 */     String fechaContratacion = respuesta.getString("FECHA_CONTRATACION2");
/*  714 */     String correoCliente = respuesta.getString("CORREO_CLIENTE");
/*  715 */     String equipo = respuesta.getString("EQUIPO");
/*  716 */     String fechaPago = respuesta.getString("FECHA_PAGO");
    
/*  718 */     String vigencia = respuesta.getString("VIGENCIA_CONTRATO");
/*  719 */     String servicio = respuesta.getString("SERVICIO");
/*  720 */     String cuota = respuesta.getString("CUOTA");
/*  721 */     String pais = respuesta.getString("IDPAIS");
/*  722 */     String clarovideo = respuesta.getString("CLARO_VIDEO");
/*  723 */     String internet = respuesta.getString("INTERNET");
/*  724 */     String llamada = respuesta.getString("LLAMADA");
/*  725 */     String television = respuesta.getString("TELEVISION");
/*  726 */     String fox = respuesta.getString("FOX");
/*  727 */     String hbo = respuesta.getString("HBO");
/*  728 */     String num_cc = respuesta.getString("NUM_CC");
/*  729 */     String link = respuesta.getString("LINK");
/*  730 */     String factura = respuesta.getString("FACTURA");
/*  731 */     String marcacion = respuesta.getString("MARCACION");
/*  732 */     String fechaInstalacion = respuesta.getString("FECHA_INSTALACION");
/*  733 */     String pais_smpp = respuesta.getString("IDPAIS_SMPP");
/*  734 */     String ilimitadas_claro = respuesta.getString("ILIMITADAS_A_CLARO");
/*  735 */     String tipo_plan_tv = respuesta.getString("TIPO_PLAN_TV");
/*  736 */     String velocidad = respuesta.getString("VELOCIDAD");
/*  737 */     String numContrato = respuesta.getString("CONTRATO");
/*  738 */     String numReferencia = respuesta.getString("NUMERO_REFERENCIA");
    
/*  740 */     String servicioHtml = "";
/*  741 */     String cuotaMensualHtml = "";
/*  742 */     String fechaPagoHtml = "";
/*  743 */     String fechaContratacionHtml = "";
/*  744 */     String vigenciaContratoHtml = "";
/*  745 */     String fechaInstalacionhtml = "";
/*  746 */     String equipohtml = "";
/*  747 */     String clarovideohtml = "";
/*  748 */     String internethtml = "";
/*  749 */     String llamadahtml = "";
/*  750 */     String televisionhtml = "";
/*  751 */     String foxhtml = "";
/*  752 */     String hbohtml = "";
/*  753 */     String returnMessage = "";
/*  754 */     String extraServicesHtml = "";
/*  755 */     String extraServicesImgUrl = "";
/*  756 */     String mainServicesHtml = "";
/*  757 */     String mainServicesDescHtml = "";
/*  758 */     String mainServiceImgUrl = "";

    
/*  761 */     String pais_id = respuesta.getString("PAIS_ID");

    
/*  764 */     String claromusicahtml = "";
/*  765 */     String sinfronterahtml = "";
/*  766 */     String smshtml = "";
/*  767 */     String redeshtml = "";
/*  768 */     String telefonohtml = "";
/*  769 */     String tvhtml = "";
/*  770 */     String mundohtml = "";





    
/*  777 */     String descriptEquipo = this.sel.getEquipo(equipo, pais);


    
/*  781 */     String htmlFOX = "";
/*  782 */     String htmlHBO = "";
/*  783 */     String htmlClaroVideo = "";

    
/*  786 */     EnvioCorreo envio = new EnvioCorreo();
/*  787 */     String host = envio.getHost(Integer.parseInt(pais_smpp));
/*  788 */     String from = envio.getFrom(Integer.parseInt(pais_smpp));
/*  789 */     String descriptRedes = "";




    
/*  795 */     Map<String, String> inlineImages = new HashMap<>();

    
/*  798 */     String descripcionTelefono = "LINEA FIJA";
/*  799 */     String descripciontipoplan = "";
/*  800 */     if (tipo_plan_tv.equals("1")) {
/*  801 */       descripciontipoplan = "AVANZADA";
    } else {
/*  803 */       descripciontipoplan = "BASICA";
    } 
/*  805 */     String descripciontelevisionhtml = "TELEVISION " + descripciontipoplan;
/*  806 */     String descripcionInternet = "INTERNET";
/*  807 */     descriptRedes = descripcionInternet;
/*  808 */     String tv = television;
/*  809 */     String telefono = llamada;
/*  810 */     String inter = internet;
/*  811 */     String parteArriba = "<td></td>";
/*  812 */     String parteAbajo = "<td></td>";
/*  813 */     String detalleCuota = "";
/*  814 */     String detalleNumeroReferencia = "";
/*  815 */     String detalleVigencia = "";
    
/*  817 */     if (pais_id.equals("504")) {
/*  818 */       DecimalFormat formateador = new DecimalFormat("####.##");
/*  819 */       double rentaBD = Double.valueOf(cuota).doubleValue();
/*  820 */       double rentaIVAHN = rentaBD * 0.15D;
/*  821 */       double rentaHonduras = rentaBD + rentaIVAHN;
/*  822 */       cuota = "$. " + String.valueOf(formateador.format(rentaHonduras));
    } 

    
/*  826 */     if (pais_id.equals("505")) {
/*  827 */       detalleCuota = "";
    } else {
      
/*  830 */       detalleCuota = "          <tr>\n            <th>Cuota mensual:</th>\n            <td>" + cuota + "</td> \n" + "          </tr>          \n";
    } 

    
/*  840 */     String detallePlanImagenes = "";
/*  841 */     if (pais_id.equals("507")) {
/*  842 */       detallePlanImagenes = "";
/*  843 */       telefono = "0";
/*  844 */       tv = "0";
/*  845 */       inter = "0";
/*  846 */       hbo = "0";
/*  847 */       fox = "0";
/*  848 */       clarovideo = "0";
/*  849 */       detalleNumeroReferencia = "         <tr>\n            <th>N&#250;mero de Tel&#233;fono:</th>\n            <td>" + numReferencia + "</td> \n" + "          </tr>\n";


    
    }
    else {


      
/*  858 */       detalleNumeroReferencia = "";
/*  859 */       detalleVigencia = "";
/*  860 */       detallePlanImagenes = "         \n          <br><br><center><img src=\"cid:detalle_plan_adquirido\" style=\"width: 600px; height: 100px;\" width=\"600px\" height=\"100\"></center>            \n";
      
/*  862 */       detalleVigencia = "          <tr>\n            <th>Vigencia del contrato:</th>\n            <td>" + vigencia + "</td> \n" + "          </tr>\n";
    } 

    
/*  872 */     if (telefono.equals("1") && tv.equals("1") && inter.equals("1")) {
      
/*  874 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:ilimitadas\" style=\"width: 350px; height: 190px;\" width=\"280px\" height=\"120\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionTelefono + "</center>\n" + "<br></td></center>\n" + "<br><center><td style=\"max-width: 500px;\" width=500px;>\n" + "<p><center><img src=\"cid:televisionfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\"></center></p>\n" + "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripciontelevisionhtml + "</center>\n" + "<br></td></center>\n" + "<br><center><td style=\"max-width: 500px;\" width=500px;>\n" + "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\"></center></p>\n" + "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionInternet + "</center>\n" + "<br></td></center>\n";

    
    }
/*  889 */     else if (telefono.equals("1") && tv.equals("1") && inter.equals("0")) {
      
/*  891 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:ilimitadas\" style=\"width: 350px; height: 190px;\" width=\"270px\" height=\"110\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionTelefono + "</center>\n" + "<br></td></center>\n" + "<br><center><td style=\"max-width: 500px;\" width=500px;>\n" + "<p><center><img src=\"cid:televisionfija\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n" + "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripciontelevisionhtml + "</center>\n" + "<br></td><td></td></center>\n";

    
    }
/*  901 */     else if (telefono.equals("1") && tv.equals("0") && inter.equals("1")) {
      
/*  903 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:ilimitadas\" style=\"width: 350px; height: 190px;\" width=\"270px\" height=\"110\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionTelefono + "</center>\n" + "<br></td></center>\n" + "<br><center><td style=\"max-width: 500px;\" width=500px;>\n" + "<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n" + "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionInternet + "</center>\n" + "<br></td><td></td></center>\n";


    
    }
/*  913 */     else if (telefono.equals("1") && tv.equals("0") && inter.equals("0")) {
      
/*  915 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:ilimitadas\" style=\"width: 350px; height: 190px;\" width=\"270px\" height=\"110\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionTelefono + "</center>\n" + "<br></td><td></td><td></td></center>\n";


    }
/*  921 */     else if (telefono.equals("0") && tv.equals("1") && inter.equals("1")) {
      
/*  923 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripcionInternet + "</center>\n" + "<br></td></center>\n" + "<br><center><td style=\"max-width: 500px;\" width=500px;>\n" + "<p><center><img src=\"cid:televisionfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\"></center></p>\n" + "<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripciontelevisionhtml + "</center>\n" + "<br></td><td></td></center>\n";

    
    }
/*  935 */     else if (telefono.equals("0") && tv.equals("1") && inter.equals("0")) {
      
/*  937 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:televisionfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descripciontelevisionhtml + "</center>\n" + "<br></td><td></td><td></td></center>\n";

    
    }
/*  944 */     else if (telefono.equals("0") && tv.equals("0") && inter.equals("1")) {
      
/*  946 */       parteArriba = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:internetfija\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptRedes + "</center>\n" + "<br></td><td></td><td></td></center>\n";
    } 

    
/*  958 */     if (clarovideo.equals("1") && fox.equals("1") && hbo.equals("1")) {
      
/*  960 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:clarovideo\" style=\"width: 250px; height: 90px;\" width=\"350px\" height=\"190\"></center></p>\n<br></td></center>\n<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:fox\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td></center>\n<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:hbo\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td></center>\n";

    
    }
/*  976 */     else if (clarovideo.equals("1") && fox.equals("1") && hbo.equals("0")) {
      
/*  978 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:clarovideo\" style=\"width: 250px; height: 90px;\" width=\"350px\" height=\"190\"></center></p>\n<br></td></center>\n<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:fox\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td><td></td></center>\n";

    
    }
/*  988 */     else if (clarovideo.equals("1") && fox.equals("0") && hbo.equals("1")) {
      
/*  990 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:clarovideo\" style=\"width: 250px; height: 90px;\" width=\"350px\" height=\"190\"></center></p>\n<br></td></center>\n<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:hbo\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td><td></td></center>\n";


    
    }
/* 1002 */     else if (clarovideo.equals("1") && fox.equals("0") && hbo.equals("0")) {
      
/* 1004 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:clarovideo\" style=\"width: 250px; height: 90px;\" width=\"350px\" height=\"190\"></center></p>\n<br></td><td></td><td></td></center>\n";



    
    }
/* 1010 */     else if (clarovideo.equals("0") && fox.equals("1") && hbo.equals("1")) {
      
/* 1012 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:fox\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td></center>\n<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:hbo\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td><td></td></center>\n";

    
    }
/* 1021 */     else if (clarovideo.equals("0") && fox.equals("1") && hbo.equals("0")) {
      
/* 1023 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:televisionfija\" style=\"width: 250px; height: 90px;\" width=\"280px\" height=\"120\"></center></p>\n<center style=\" font-weight: bold; font-size: 14px; margin-top: 0px; text-align: center;\">" + descriptRedes + "</center>\n" + "<br></td><td></td><td></td></center>\n";

    
    }
/* 1030 */     else if (clarovideo.equals("0") && fox.equals("0") && hbo.equals("1")) {
      
/* 1032 */       parteAbajo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:hbo\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td><td></td><td></td></center>\n";
    } 

    
/* 1077 */     htmlClaroVideo = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:clarovideo\" style=\"width: 250px; height: 90px;\" width=\"350px\" height=\"190\"></center></p>\n<br></td></center>\n";


/* 1085 */     htmlFOX = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:fox\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td></center>\n";

    
/* 1098 */     htmlHBO = "<br><center><td style=\"max-width: 500px;\" width=500px;>\n<p><center><img src=\"cid:hbo\" style=\"width: 250px; height: 90px;\" width=\"250px\" height=\"90\"></center></p>\n<br></td></center>\n";

    
/* 1153 */     if (!descriptEquipo.equals("0")) {
/* 1154 */       equipo = descriptEquipo;
    }

    
/* 1159 */     String hola1 = "";
/* 1160 */     String hola2 = "";
/* 1161 */     String hola3 = "";

    
/* 1170 */     if (pais_id.equals("503")) {
/* 1171 */       String htmlMonto = "";
/* 1172 */       String htmlFechaPago = "";
    } else {
      
/* 1175 */       String htmlMonto = "<tr>\n            <th>Cuota mensual:</th>\n            <td>" + cuota + "</td> \n" + "          </tr>\n";


      
/* 1179 */       String htmlFechaPago = "<tr>\n            <th>Fecha m&#225;xima de pago:</th>\n            <td>" + fechaPago + "</td> \n" + "          </tr>\n";
    } 

    
/* 1185 */     StringBuffer body = new StringBuffer("<html><head>\n<meta http-equiv=\"Content-Type\" content=\"text/html\">\n<meta charset=\"utf-8\">\n        <title>Formato Correo</title>\n        <style type=\"text/css\">\n          body{\n            font-family: Yu Gothic Medium;\n            font-size: 18px;\n          }\n          div{\n            font-family: Yu Gothic Medium;\n            font-size: 18px;\n          }\n          .nombre{\n            font-weight: bold;\n          }\n          p{\n            text-align: justify;\n          }\n          th{\n            text-align: right;\n            border-bottom: 1px solid #0097a9; \n          }\n          .final{\n            text-align: justify;\n          }\n          #detalles tr td{\n            border-bottom: 1px solid #0097a9;\n            font-family: Yu Gothic light;\n          }\n          #imagenes>td{\n            padding-left: 30px;\n            border-bottom: 1px solid #0097a9;\n            min-width: 33%;\n          }\n          .contacto{\n            vertical-align: super;\n            display: inline-block;\n          }\n          .vinculo{\n           font-weight: bold;\n          }\n          .contactotexto{\n            vertical-align: super;\n            display: inline-block;\n          }\n        </style>\n        <link href=\"./styles/fonts.css\" rel=\"stylesheet\">        \n    </head>\n    <div style=\"max-width: 1000px;\">\n      <body width=\"800px;\">\n      <img src=\"cid:header2Fijo\" style=\"width: 100%; max-width: 1280px;\">\n      <div style=\"margin-left: 40px; width: 80%;\">\n        <span class=\"nombre\">Tu experiencia Claro comienza aqu&#237;</span> con la red m&#225;s r&#225;pida de Telecomunicaciones de Centroam&#233;rica.\n        <p>A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s datos comun&#237;cate al <span class=\"nombre\">" + num_cc + "</span> o ac&#233;rcate a tu Tienda Claro m&#225;s cercana.</p>\n" + "      </div>\n" + "      <center>\n" + "      <div>\n" + "        <table width=\"80%\" id=\"detalles\">\n" + "          <tr>\n" + "            <th>Servicio adquirido:</th>\n" + "            <td>" + servicio + "</td> \n" + "          </tr>\n" + detalleCuota + "          <tr>\n" + "          <tr>\n" + "            <th>Fecha limite de pago:</th>\n" + "            <td>" + fechaPago + "</td> \n" + "          </tr>     \n" + "          <tr>\n" + "            <th>Fecha de contrataci&#243;n:</th>\n" + "            <td>" + fechaContratacion + "</td> \n" + "          </tr>\n" + detalleVigencia + "         <tr>\n" + "            <th>Contrato:</th>\n" + "            <td>" + numContrato + "</td> \n" + "          </tr>\n" + detalleNumeroReferencia + "          <tr>\n" + "            <th>Env&#237;o factura electr&#243;nica:</th>\n" + "            <td><a href=\"" + factura + "\">" + correoCliente + "</a></td> \n" + "          </tr>          \n" + "        </table>           \n" + "      </div>\n" + "      </center>\n" + "      <br>\n" + "      <div>\n" + "        <div style=\"text-align: center; background-color: #e6e6e6\">\n" + detallePlanImagenes + "          <br>\n" + "          <center>\n" + "            <table width=\"100%\" style=\"background-color: #e6e6e6;\">\n" + "              <tr>\n" + "              " + parteArriba + "" + "              </tr>\n" + "              <tr>\n" + "              " + parteAbajo + "" + "              </tr>\n" + "             </table>\n" + "             <br><br>  " + "      </div>\n" + "      <a href=\"" + link + "\"><img src=\"cid:footer\" style=\"width: 100%;\"></a>\n" + "      </div>            \n" + "                  <img src=\"cid:blanco.png\" style=\"width: 1px; height: 1px;\" width=\"1px\" height=\"1\">\n" + "    </body>\n" + "    </div>\n" + "</html>\n" + "\n" + "");

    
/* 1307 */     switch (pais_id) {
      case "502":
/* 1309 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footer.png");
        break;
      case "503":
/* 1312 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//HN VF.JPG");
        break;
      case "504":
/* 1315 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//HNVF_1.png");
        break;
      
      case "505":
/* 1319 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//nuevoFooterNI.png");
        break;
      case "506":
/* 1322 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//footerCR.png");
        break;
      case "507":
/* 1325 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//FooterPA_1.png");
        break;
      
      default:
/* 1329 */         inlineImages.put("footer", "//app//weblogicChRe//IMAGENES//img//FooterPA_1.png");
        break;
    } 
/* 1332 */     inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 1333 */     inlineImages.put("detalle_plan_adquirido", "//app//weblogicChRe//IMAGENES//img//detalle_plan_adquirido.png");
/* 1334 */     inlineImages.put("ilimitadas", "//app//weblogicChRe//IMAGENES//img//IconosClaro-06_1.png");
/* 1335 */     inlineImages.put("televisionfija", "//app//weblogicChRe//IMAGENES//img//IconosClaro-04_1.png");
/* 1336 */     inlineImages.put("internetfija", "//app//weblogicChRe//IMAGENES//img//logointernet.png");
/* 1337 */     inlineImages.put("clarovideo", "//app//weblogicChRe//IMAGENES//img//clarovideo2.png");
/* 1338 */     inlineImages.put("hbo", "//app//weblogicChRe//IMAGENES//img//hbo2.png");
/* 1339 */     inlineImages.put("fox", "//app//weblogicChRe//IMAGENES//img//fox2.png");


    try {
/* 1353 */       EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 1354 */       return "Email sent.";
/* 1355 */     } catch (Exception ex) {
/* 1356 */       return "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    } 
  }

  
  public static String cuerpoFijo(JSONObject respuesta) throws JSONException, Exception {
/* 1420 */     String fechaContratacion = respuesta.getString("FECHA_CONTRATACION2");
/* 1421 */     String correoCliente = respuesta.getString("CORREO_CLIENTE");
/* 1422 */     String equipo = respuesta.getString("EQUIPO");
/* 1423 */     String fechaPago = respuesta.getString("FECHA_PAGO");
/* 1424 */     String vigencia = respuesta.getString("VIGENCIA_CONTRATO");
/* 1425 */     String servicio = respuesta.getString("SERVICIO");
/* 1426 */     String cuota = respuesta.getString("CUOTA");
/* 1427 */     String pais = respuesta.getString("IDPAIS");
/* 1428 */     String clarovideo = respuesta.getString("CLARO_VIDEO");
/* 1429 */     String internet = respuesta.getString("INTERNET");
/* 1430 */     String llamada = respuesta.getString("LLAMADA");
/* 1431 */     String television = respuesta.getString("TELEVISION");
/* 1432 */     String fox = respuesta.getString("FOX");
/* 1433 */     String hbo = respuesta.getString("HBO");
/* 1434 */     String num_cc = respuesta.getString("NUM_CC");
/* 1435 */     String link = respuesta.getString("LINK");
/* 1436 */     String factura = respuesta.getString("FACTURA");
/* 1437 */     String marcacion = respuesta.getString("MARCACION");
/* 1438 */     String fechaInstalacion = respuesta.getString("FECHA_INSTALACION");
/* 1439 */     String pais_smpp = respuesta.getString("IDPAIS_SMPP");
/* 1440 */     String ilimitadas_claro = respuesta.getString("ILIMITADAS_A_CLARO");
/* 1441 */     String tipo_plan_tv = respuesta.getString("TIPO_PLAN_TV");
/* 1442 */     String velocidad = respuesta.getString("VELOCIDAD");
/* 1443 */     String servicioHtml = "";
/* 1444 */     String cuotaMensualHtml = "";
/* 1445 */     String fechaPagoHtml = "";
/* 1446 */     String fechaContratacionHtml = "";
/* 1447 */     String vigenciaContratoHtml = "";
/* 1448 */     String fechaInstalacionhtml = "";
/* 1449 */     String equipohtml = "";
/* 1450 */     String clarovideohtml = "";
/* 1451 */     String internethtml = "";
/* 1452 */     String llamadahtml = "";
/* 1453 */     String televisionhtml = "";
/* 1454 */     String foxhtml = "";
/* 1455 */     String hbohtml = "";
/* 1456 */     String returnMessage = "";
/* 1457 */     String extraServicesHtml = "";
/* 1458 */     String extraServicesImgUrl = "";
/* 1459 */     String mainServicesHtml = "";
/* 1460 */     String mainServicesDescHtml = "";
/* 1461 */     String mainServiceImgUrl = "";
    
/* 1463 */     EnvioCorreo envio = new EnvioCorreo();
/* 1464 */     String host = envio.getHost(Integer.parseInt(pais_smpp));
/* 1465 */     String from = envio.getFrom(Integer.parseInt(pais_smpp));
    
/* 1467 */     if (pais.equals("502")) {
      
/* 1469 */       if (!servicio.equals("0")) {
/* 1470 */         servicioHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Servicio adquirido:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + servicio + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1479 */         servicioHtml = "";
      } 
      
/* 1482 */       if (!cuota.equals("N/A")) {
/* 1483 */         cuotaMensualHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Cuota mensual:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + cuota + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1492 */         cuotaMensualHtml = "";
      } 
      
/* 1495 */       if (!fechaPago.equals("0")) {
/* 1496 */         fechaPagoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de pago:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaPago + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1505 */         fechaPagoHtml = "";
      } 
      
/* 1508 */       if (!fechaContratacion.equals("0")) {
/* 1509 */         fechaContratacionHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de contrataci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaContratacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1518 */         fechaContratacionHtml = "";
      } 
      
/* 1521 */       if (!vigencia.equals("0")) {
/* 1522 */         vigenciaContratoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Vigencia contrato:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + vigencia + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1531 */         vigenciaContratoHtml = "";
      } 
      
/* 1534 */       if (!fechaInstalacion.equals("0")) {
/* 1535 */         fechaInstalacionhtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha instalaci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaInstalacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1544 */         fechaInstalacionhtml = "";
      } 
      
/* 1547 */       if (!equipo.equals("0")) {
/* 1548 */         equipohtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Equipo:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + equipo + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 1557 */         equipohtml = "";
      } 
      
/* 1560 */       Map<String, String> inlineImages = new HashMap<>();
      
/* 1562 */       if (!llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 1563 */         String llamadasIlimitadasHtml = "";
/* 1564 */         if (!ilimitadas_claro.equals("0")) {
/* 1565 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             LLAMADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             ILIMITADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             A todos los operadores\n        </td>\n    </tr>\n</table>\n";

        
        }
        else {


          
/* 1583 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             L&#237;nea Fija\n        </td>\n    </tr>\n</table>\n";
        } 


        
/* 1592 */         String tipoPlanTvHtml = "";
/* 1593 */         if (!tipo_plan_tv.equals("0")) {
/* 1594 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             TV\n        </td>\n    </tr>\n    <tr>\n        <td>\n             " + tipo_plan_tv + "\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";


    
        }
        else {



          
/* 1607 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Televisi&#243;n\n        </td>\n    </tr>\n</table>\n";
        } 


        
/* 1616 */         String velocidadHtml = "";
/* 1617 */         if (!velocidad.equals("0")) {
/* 1618 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             " + velocidad + " DE\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>\n" + "             INTERNET\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";




        
        }
        else {




          
/* 1631 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Internet\n        </td>\n    </tr>\n    <tr>\n        <td>\n             Residencial\n        </td>\n    </tr>\n</table>\n";
        } 


/* 1645 */         mainServiceImgUrl = "Llamadas_Television_Internet";
/* 1646 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1647 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + llamadasIlimitadasHtml + tipoPlanTvHtml + velocidadHtml + "</td>\n";
/* 1648 */       } else if (!llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 1649 */         String llamadasIlimitadasHtml = "";
/* 1650 */         if (!ilimitadas_claro.equals("0")) {
/* 1651 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             LLAMADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             ILIMITADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             A todos los operadores\n        </td>\n    </tr>\n</table>\n";


        
        }
        else {


/* 1669 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             L&#237;nea Fija\n        </td>\n    </tr>\n</table>\n";
        } 


        
/* 1678 */         String tipoPlanTvHtml = "";
/* 1679 */         if (!tipo_plan_tv.equals("0")) {
/* 1680 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             TV\n        </td>\n    </tr>\n    <tr>\n        <td>\n             " + tipo_plan_tv + "\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";




        
        }
        else {

          
/* 1693 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Televisi&#243;n\n        </td>\n    </tr>\n</table>\n";
        } 

       
/* 1702 */         mainServiceImgUrl = "Llamadas_Television";
/* 1703 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1704 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + llamadasIlimitadasHtml + tipoPlanTvHtml + "</td>\n";
/* 1705 */       } else if (!llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 1706 */         String llamadasIlimitadasHtml = "";
/* 1707 */         if (!ilimitadas_claro.equals("0")) {
/* 1708 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             LLAMADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             ILIMITADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             A todos los operadores\n        </td>\n    </tr>\n</table>\n";


        
        }
        else {


          
/* 1726 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             L&#237;nea Fija\n        </td>\n    </tr>\n</table>\n";
        } 


        
/* 1735 */         String velocidadHtml = "";
/* 1736 */         if (!velocidad.equals("0")) {
/* 1737 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             " + velocidad + " DE\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>\n" + "             INTERNET\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";

       
        }
        else {

          
/* 1750 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Internet\n        </td>\n    </tr>\n    <tr>\n        <td>\n             Residencial\n        </td>\n    </tr>\n</table>\n";
        } 

        
/* 1763 */         mainServiceImgUrl = "Llamadas_Internet";
/* 1764 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1765 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + llamadasIlimitadasHtml + velocidadHtml + "</td>\n";
/* 1766 */       } else if (!llamada.equals("0") && television.equals("0") && internet.equals("0")) {
/* 1767 */         String llamadasIlimitadasHtml = "";
/* 1768 */         if (!ilimitadas_claro.equals("0")) {
/* 1769 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             LLAMADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             ILIMITADAS\n        </td>\n    </tr>\n    <tr>\n        <td>\n             A todos los operadores\n        </td>\n    </tr>\n</table>\n";


        
        }
        else {

          
/* 1787 */           llamadasIlimitadasHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             L&#237;nea Fija\n        </td>\n    </tr>\n</table>\n";
        } 

        
/* 1796 */         mainServiceImgUrl = "Llamadas";
/* 1797 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1798 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + llamadasIlimitadasHtml + "</td>\n";
/* 1799 */       } else if (llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
        
/* 1801 */         String tipoPlanTvHtml = "";
/* 1802 */         if (!tipo_plan_tv.equals("0")) {
/* 1803 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             TV\n        </td>\n    </tr>\n    <tr>\n        <td>\n             " + tipo_plan_tv + "\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";

        
        }
        else {


          
/* 1816 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Televisi&#243;n\n        </td>\n    </tr>\n</table>\n";
        } 

        
/* 1825 */         String velocidadHtml = "";
/* 1826 */         if (!velocidad.equals("0")) {
/* 1827 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             " + velocidad + " DE\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>\n" + "             INTERNET\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";

        
        }
        else {

          
/* 1840 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Internet\n        </td>\n    </tr>\n    <tr>\n        <td>\n             Residencial\n        </td>\n    </tr>\n</table>\n";
        } 

        
/* 1853 */         mainServiceImgUrl = "Television_Internet";
/* 1854 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1855 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + tipoPlanTvHtml + velocidadHtml + "</td>\n";
/* 1856 */       } else if (llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 1857 */         String tipoPlanTvHtml = "";
/* 1858 */         if (!tipo_plan_tv.equals("0")) {
/* 1859 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             TV\n        </td>\n    </tr>\n    <tr>\n        <td>\n             " + tipo_plan_tv + "\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";

        
        }
        else {

          
/* 1872 */           tipoPlanTvHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Televisi&#243;n\n        </td>\n    </tr>\n</table>\n";
        } 


        
/* 1881 */         mainServiceImgUrl = "Television";
/* 1882 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1883 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + tipoPlanTvHtml + "</td>\n";
/* 1884 */       } else if (llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 1885 */         String velocidadHtml = "";
/* 1886 */         if (!velocidad.equals("0")) {
/* 1887 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             " + velocidad + " DE\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>\n" + "             INTERNET\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";

        
        }
        else {

          
/* 1900 */           velocidadHtml = "<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td>\n             Internet\n        </td>\n    </tr>\n    <tr>\n        <td>\n             Residencial\n        </td>\n    </tr>\n</table>\n";
        } 


/* 1913 */         mainServiceImgUrl = "Internet";
/* 1914 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 1915 */         mainServicesDescHtml = "<td class=\"mainServicesDesc\">\n" + velocidadHtml + "</td>\n";
      } 
      
/* 1918 */       if (!mainServiceImgUrl.equals("")) {
/* 1919 */         mainServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + mainServiceImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }


      
/* 1930 */       if (!clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 1931 */         extraServicesImgUrl = "ClaroVideo_FOX_HBO";
/* 1932 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 1933 */       } else if (!clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 1934 */         extraServicesImgUrl = "ClaroVideo_FOX";
/* 1935 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 1936 */       } else if (!clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 1937 */         extraServicesImgUrl = "ClaroVideo_HBO";
/* 1938 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 1939 */       } else if (!clarovideo.equals("0") && fox.equals("0") && hbo.equals("0")) {
/* 1940 */         extraServicesImgUrl = "ClaroVideo";
/* 1941 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 1942 */       } else if (clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 1943 */         extraServicesImgUrl = "FOX_HBO";
/* 1944 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 1945 */       } else if (clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 1946 */         extraServicesImgUrl = "FOX";
/* 1947 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 1948 */       } else if (clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 1949 */         extraServicesImgUrl = "HBO";
/* 1950 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
      } 
      
/* 1953 */       if (!extraServicesImgUrl.equals("")) {
/* 1954 */         extraServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + extraServicesImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }


      
/* 1965 */       StringBuffer body = new StringBuffer("<html>\n   <head>\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n      <title>Bienvenido a Claro</title>\n      <style type=\"text/css\">\n         body {\n            margin: 0; \n            padding: 0; \n            min-width: 100% !important;\n         }\n        .content {\n            width: 100%; \n            max-width: 900px;\n         }\n         @media only screen and (min-device-width: 601px) {\n            .content {\n               width: 900px !important;\n            }\n         }\n         .col {\n            width: 100%;\n         }\n         .h1 {\n            font-size: 33px; \n            line-height: 38px; \n            font-weight: bold;\n         }\n         .servicesTr{\n            text-align: -webkit-center;\n         }\n         .h1, .h2, .bodycopy {\n            font-family: Yu Gothic light;\n            text-align: justify;\n         }\n         .innerpadding {\n            padding: 0px 30px 30px 30px;\n         }\n         .innerpadding2 {\n            padding: 20px 30px 0px 30px;\n         }\n         .borderbottom {\n            border-bottom: 0px solid #f2eeed;\n         }\n         .mainServicesImg {\n            text-align: center;\n         }\n         .extraServices{\n            vertical-align: bottom;\n            text-align: -webkit-center;\n         }\n         .mainServicesDesc {\n            font-weight: bold; \n            font-size: 1.5vw;\n            font-family: Yu Gothic medium;\n            text-align: center;\n            display: flex;\n            width:100%;\n         }\n         .mainServicesDescMarcacion {\n            font-weight: bold; \n            font-size: 20px;\n            text-align: center;\n         }\n         .mainServicesMargin{\n            margin-bottom: 3%;\n         }\n         .h2 {\n            padding: 0 0 15px 0; \n            font-size: 20px; \n            line-height: 22px;\n         }\n         .detailsTittle {\n            color: black; \n            font-weight: bold; \n            font-size: 20px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .bodycopy {\n            font-size: 20px; \n            line-height: 22px;\n         }\n         .bodycopyContact{\n            text-align: left;\n            font-family: Yu Gothic medium;\n         }\n         .bodycopyReminder{\n            text-align: center !important;\n         }\n         .description{\n            text-align: right;\n         }\n         .descriptionResult{\n            text-align: left;\n         }\n         .information{\n            border-bottom:0.001em solid gray;\n         }\n         img {\n            height: auto;\n         }\n         .footer {\n            padding: 20px 30px 15px 30px;\n         }\n      </style>\n   </head>\n   <body yahoo bgcolor=\"#ffffff\">\n      <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n            <td>\n               <!--[if (gte mso 9)|(IE)]>\n               <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                   <tr>\n                       <td>\n                           <![endif]-->\n                              <table class=\"content\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                                 <tr>\n                                    <td class=\"header\">\n                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                           <tr>\n                                               <td>\n                                                   <img src=\"cid:header2Fijo\" width=\"100%\" style\"max-width:900px;\" border=\"0\" alt=\"\" / >\n                                               </td>\n                                           </tr>\n                                       </table>\n                                    </td> \n                                 </tr>\n                                 <tr>\n                                    <td class=\"innerpadding borderbottom\">\n                                       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                                          <tr>\n                                             <td class=\"h2\">\n                                                <b>Tu experiencia Claro comienza aqu&#237;</b>, con la red mas grande de Telecomunicaciones de Centroam&#233;rica.\n                                             </td>\n                                          </tr>\n                                          <tr>\n                                             <td class=\"bodycopy\">\n                                                A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s informaci&#243;n comun&#237;cate al <b>" + num_cc + "</b> o acercate a tu Tienda Claro m&#225;s cercana.\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding borderbottom\">\n" + "                                       <!--[if (gte mso 9)|(IE)]>\n" + "                                          <table width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "                                             <tr>\n" + "                                                <td>\n" + "                                                   <![endif]-->\n" + "                                                      <table class=\"col\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \n" + "                                                         <tr>\n" + "                                                            <td>\n" + "                                                               <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" + servicioHtml + cuotaMensualHtml + fechaPagoHtml + fechaContratacionHtml + vigenciaContratoHtml + fechaInstalacionhtml + equipohtml + "                                                                  <tr class=\"information\">\n" + "                                                                     <td class=\"bodycopy description information\">\n" + "                                                                        <b>Env&#237;o factura electr&#243;nica:</b>\n" + "                                                                     </td>\n" + "                                                                     <td class=\"bodycopy descriptionResult information\">\n" + "                                                                        <a href=\"" + factura + "\">" + correoCliente + "</a>\n" + "                                                                     </td>\n" + "                                                                  </tr>\n" + "                                                               </table>\n" + "                                                            </td>\n" + "                                                         </tr>\n" + "                                                      </table>\n" + "                                                   <!--[if (gte mso 9)|(IE)]>\n" + "                                                </td>\n" + "                                             </tr>\n" + "                                          </table>\n" + "                                       <![endif]-->\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\" bgcolor=\"#e6e6e6\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td class=\"detailsTittle\">\n" + "                                                Detalles del plan adquirido:\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + mainServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + mainServicesDescHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + extraServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <img src=\"cid:reminderPayways\" width=\"100%\">\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td>\n" + "                                                            <img src=\"cid:MiClaro_MarcacionGT\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td class=\"authorizedBanks\">\n" + "                                                            <img src=\"cid:bancosautorizadosFija\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <a href=\"" + link + "\"><img src=\"cid:footer2FijoGT\" width=\"100%\"></a>\n" + "                                             </td> \n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"footer\" bgcolor=\"red\">\n" + "                                    </td>\n" + "                                 </tr>\n" + "                              </table>\n" + "                           <!--[if (gte mso 9)|(IE)]>\n" + "                        </td>\n" + "                     </tr>\n" + "                  </table>\n" + "               <![endif]-->\n" + "            </td>\n" + "         </tr>\n" + "      </table>\n" + "    </body>\n" + "</html>");


      
/* 2237 */       inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 2238 */       inlineImages.put("bancosautorizadosFija", "//app//weblogicChRe//IMAGENES//img//bancosautorizadosFija.png");
/* 2239 */       inlineImages.put("footer2FijoGT", "//app//weblogicChRe//IMAGENES//img//footer2FijoGT.png");
/* 2240 */       inlineImages.put("reminderPayways", "//app//weblogicChRe//IMAGENES//img//reminderPayways.png");
/* 2241 */       inlineImages.put("MiClaro_MarcacionGT", "//app//weblogicChRe//IMAGENES//img//MiClaro_MarcacionGT.png");


      
      try {
/* 2250 */         if (correoCliente.contains("@") || correoCliente.contains(".")) {
/* 2251 */           EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 2252 */           returnMessage = "Email sent.";
        } else {
/* 2254 */           returnMessage = "Email not sent. Invalid email.";
        } 
/* 2256 */       } catch (Exception ex) {

        
/* 2259 */         returnMessage = "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      }
    
/* 2262 */     } else if (pais.equals("503")) {
      
/* 2264 */       if (!servicio.equals("0")) {
/* 2265 */         servicioHtml = "<tr>\n    <th>Servicio adquirido:</th>\n    <td>" + servicio + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2270 */         servicioHtml = "";
      } 
      
/* 2273 */       if (!cuota.equals("N/A")) {
/* 2274 */         cuotaMensualHtml = " <tr>\n   <th>Cuota mensual:</th>\n   <td>" + cuota + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2279 */         cuotaMensualHtml = "";
      } 
      
/* 2282 */       if (!fechaPago.equals("0")) {
/* 2283 */         fechaPagoHtml = "<tr>\n   <th>Fecha de pago:</th>\n   <td>" + fechaPago + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2288 */         fechaPagoHtml = "";
      } 
      
/* 2291 */       if (!fechaContratacion.equals("0")) {
/* 2292 */         fechaContratacionHtml = "<tr>\n   <th>Fecha contrataci&#243;n:</th>\n   <td>" + fechaContratacion + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2297 */         fechaContratacionHtml = "";
      } 
      
/* 2300 */       if (!vigencia.equals("0")) {
/* 2301 */         vigenciaContratoHtml = "<tr>\n   <th>Vigencia contrato:</th>\n   <td>" + vigencia + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2306 */         vigenciaContratoHtml = "";
      } 
      
/* 2309 */       if (!fechaInstalacion.equals("0")) {
/* 2310 */         fechaInstalacionhtml = "<tr>\n    <th>Fecha instalaci&#243;n:</th>\n    <td>" + fechaInstalacion + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2315 */         fechaInstalacionhtml = "";
      } 
      
/* 2318 */       if (!equipo.equals("0")) {
/* 2319 */         equipohtml = "<tr>\n    <th>Equipo:</th>\n    <td>" + equipo + "</td>\n" + "</tr>\n";
      
      }
      else {
        
/* 2324 */         equipohtml = "";
      } 
      
/* 2327 */       if (!llamada.equals("0")) {
/* 2328 */         llamadahtml = "<td style=\"width: 33%;\">\n                              <center><img src=\"cid:llamadas_ilimitadasFijo\"></center>\n                              <center style=\"font-weight: 500; font-size: 23px; margin-top: 0px; text-align: center;\">\n                                 L&#205;NEA FIJA\n                              </center>\n                           </td>";

      
      }
      else {

        
/* 2335 */         llamadahtml = "";
      } 
/* 2337 */       if (!television.equals("0")) {
/* 2338 */         televisionhtml = "<td style=\"width: 33%;\">\n                              <center><img src=\"cid:televisionFijo\"></center>\n                              <center style=\"font-weight: 500; font-size: 23px; margin-top: 0px; text-align: center;\">\n                                 TELEVISI&#211;N\n                              </center>\n                           </td>";

      
      }
      else {

        
/* 2345 */         televisionhtml = "";
      } 
/* 2347 */       if (!internet.equals("0")) {
/* 2348 */         internethtml = "<td style=\"width: 33%; position: relative; top: 15px;\">\n                              <p></p>\n                              <center><img src=\"cid:internetFijo\" style=\"margin-bottom: 15px;\"></center>\n                              <center style=\"font-weight: 500; font-size: 23px; margin-top: -30px; text-align: center;\">\n                                 INTERNET\n                              </center>\n                              <center style=\"font-weight: 500; font-size: 23px; margin-top: 0px; text-align: center;\">\n                                 RESIDENCIAL\n                              </center>\n                           </td>";



      
      }
      else {



        
/* 2359 */         internethtml = "";
      } 
/* 2361 */       if (!clarovideo.equals("0")) {
/* 2362 */         clarovideohtml = "<td style=\"width: 33%;\">\n                              <p></p>\n                              <center><img src=\"cid:ClaroVideo\" style=\"margin-top: 20px;\"></center>\n                           </td>";
      
      }
      else {
        
/* 2367 */         clarovideohtml = "";
      } 
/* 2369 */       if (!fox.equals("0")) {
/* 2370 */         foxhtml = "<td style=\"width: 33%;\">\n                              <p></p>\n                              <center><img src=\"cid:FOX\" style=\"margin-top: 30px;\"></center>\n                           </td>";
      
      }
      else {
        
/* 2375 */         foxhtml = "";
      } 
/* 2377 */       if (!hbo.equals("0")) {
/* 2378 */         hbohtml = "<td style=\"width: 33%;\">\n                              <p></p>\n                              <center><img src=\"cid:HBO\" style=\"margin-top: 30px;\"></center>\n                           </td>";
      
      }
      else {
        
/* 2383 */         hbohtml = "";
      } 
      
/* 2386 */       StringBuffer body = new StringBuffer("<html>\n   <head>\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=Windows-1252\">\n      <title>Bienvenido a Claro</title>\n      <style type=\"text/css\">\n         body{\n         font-family: Yu Gothic Medium;\n         }\n         .nombre{\n         font-weight: bold;\n         }\n         p{\n         text-align: justify;\n         }\n         th{\n         text-align: right;\n         border-bottom: 1px solid #0097a9; \n         font-size: 20px;\n         }\n         td{\n         text-align: left;\n         font-size: 20px;\n         }\n         .final{\n         text-align: justify;\n         }\n         #detalles tr td{\n         border-bottom: 1px solid #0097a9;\n         font-family: Yu Gothic light;\n         }\n         #imagenes>td{\n         padding-left: 30px;\n         border-bottom: 1px solid #0097a9;\n         min-width: 33%;\n         }\n         .contacto{\n         vertical-align: super;\n         display: inline-block;\n         }\n         .vinculo{\n         font-weight: bold;\n         }\n         .contactotexto{\n         vertical-align: super;\n         display: inline-block;\n         }\n      </style>\n      <link href=\"./styles/fonts.css\" rel=\"stylesheet\">\n   </head>\n   <body>\n      <div style=\"max-width: 1000px;\">\n         <img src=\"cid:header2Fijo\" style=\"width: 100%; max-width: 1280px;\">\n         <div style=\"margin-left: 40px; width: 80%; margin-top: 30px;\">\n            <p style=\"font-size:27px; font-family: Yu Gothic light; margin-left: 60px; line-height: 27px; display: inline-block;\">Tu experiencia Claro comienza aqu&#237;, con la red mas grande de Telecomunicaciones de Centroam&#233;rica. A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s informaci&#243;n comun&#237;cate al\n               <span class=\"nombre\">" + num_cc + "</span> o acercate a tu Tienda Claro m&#225;s cercana.\n" + "            </p>\n" + "         </div>\n" + "         <center>\n" + "            <div>\n" + "               <table width=\"80%\" id=\"detalles\">\n" + "                  <tbody>\n" + servicioHtml + cuotaMensualHtml + fechaPagoHtml + fechaContratacionHtml + vigenciaContratoHtml + fechaInstalacionhtml + equipohtml + "                     <tr>\n" + "                        <th>Env&#237;o factura electr&#243;nica:</th>\n" + "                        <td><a href=\"" + factura + "\">" + correoCliente + "</a>\n" + "                        </td>\n" + "                     </tr>\n" + "                  </tbody>\n" + "               </table>\n" + "            </div>\n" + "         </center>\n" + "         <br>\n" + "         <div>\n" + "            <div style=\"text-align: center; background-color: #e6e6e6\">\n" + "               <span style=\"color: red; font-weight: bold; font-size: 24px;\">DETALLES DEL PLAN ADQUIRIDO:</span>\n" + "               <br>\n" + "               <center>\n" + "                  <table width=\"85%\" style=\"background-color: #e6e6e6;\">\n" + "                     <tbody>\n" + "                        <tr>\n" + "                         " + llamadahtml + "" + "                         " + televisionhtml + "" + "                         " + internethtml + "" + "                       </tr>\n" + "                     </tbody>\n" + "                  </table>\n" + "                  <table width=\"85%\" style=\"background-color: #e6e6e6;\">\n" + "                      <tbody>\n" + "                       <tr>\n" + "                          " + clarovideohtml + "" + "                          " + foxhtml + "" + "                          " + hbohtml + "" + "                       </tr>\n" + "                     </tbody>\n" + "                  </table>\n" + "                  <center style=\"font-weight: 500; font-size: 21.5px; margin-bottom: 20px; margin-top: 20px\">Recuerda que puedes pagar tu factura y consultar tu saldo de las siguientes formas</center>\n" + "               </center>\n" + "               <center>\n" + "                  <table width=\"85%\" style=\"background-color: #e6e6e6;\">\n" + "                     <tbody>\n" + "                        <tr>\n" + "                           <td style=\"width:33%;\">\n" + "                              <p></p>\n" + "                              <center><img src=\"cid:miclaroFijo\"></center>\n" + "                              <p></p>\n" + "                           </td>\n" + "                            <td style=\"width:33%;\">\n" + "                              <p><center><img src=\"cid:marcacionFijo2\"></center></p>\n" + "                              <center style=\"font-weight: 600; font-size: 21px; margin-bottom: 20px;\">Llamando al " + marcacion + "</center>\n" + "                            </td> \n" + "                        </tr>\n" + "                     </tbody>\n" + "                  </table>\n" + "                  <center><img src=\"cid:bancosautorizadosFija\" style=\"width: 800px; height: 100px;\" width=\"800px\" height=\"125\"></center>\n" + "               </center>\n" + "            </div>\n" + "            <table width=\"100%\">\n" + "               <tbody>\n" + "                  <tr>\n" + "                     <td style=\"max-width: 100%; margin-top: 12.5%; display: flex;\">\n" + "                        <img src=\"cid:marcacionFijo\" style=\"width: 80px; height: 60px; margin-left: 10%;\" width=\"60px\" height=\"40\">\n" + "                           <div style=\"margin-top: 15px;\">Cont&#225;ctanos al\n" + "                              <span style=\"font-weight: bold;\">" + num_cc + "</span> o visitanos en Tiendas Claro \n" + "                           </div>\n" + "                     </td>\n" + "                     <td>" + "                       <a href=\"" + link + "\"><img src=\"cid:footer2FijoGT\"></a>\n" + "                     </td>" + "                     <td style=\"max-width: 33%;\"></td>\n" + "                  </tr>\n" + "               </tbody>\n" + "            </table>\n" + "         </div>\n" + "         <div style=\"width:100%; background-color: red; height: 35px; margin-top: 20px;\"></div>\n" + "      </div>\n" + "   </body>\n" + "</html>");


      
/* 2522 */       Map<String, String> inlineImages = new HashMap<>();



      
/* 2539 */       inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 2540 */       inlineImages.put("llamadas_ilimitadasFijo", "//app//weblogicChRe//IMAGENES//img//llamadas_ilimitadasFijo.png");
/* 2541 */       inlineImages.put("televisionFijo", "//app//weblogicChRe//IMAGENES//img//televisionFijo.png");
/* 2542 */       inlineImages.put("internetFijo", "//app//weblogicChRe//IMAGENES//img//internetFijo.png");
/* 2543 */       inlineImages.put("HBO", "//app//weblogicChRe//IMAGENES//img//HBO.png");
/* 2544 */       inlineImages.put("ClaroVideo", "//app//weblogicChRe//IMAGENES//img//ClaroVideo.png");
/* 2545 */       inlineImages.put("FOX", "//app//weblogicChRe//IMAGENES//img//FOX.png");
/* 2546 */       inlineImages.put("miclaroFijo", "//app//weblogicChRe//IMAGENES//img//miclaroFijo.png");
/* 2547 */       inlineImages.put("marcacionFijo", "//app//weblogicChRe//IMAGENES//img//marcacionFijo.png");
/* 2548 */       inlineImages.put("marcacionFijo2", "//app//weblogicChRe//IMAGENES//img//marcacionFijo2.png");
/* 2549 */       inlineImages.put("marcacionEspecialFijo", "//app//weblogicChRe//IMAGENES//img//marcacionEspecialFijo.png");
/* 2550 */       inlineImages.put("bancosautorizadosFija", "//app//weblogicChRe//IMAGENES//img//bancosautorizadosFija.png");
/* 2551 */       inlineImages.put("footer2FijoGT", "//app//weblogicChRe//IMAGENES//img//footer2FijoGT.png");

      
      try {
/* 2555 */         if (correoCliente.contains("@") || correoCliente.contains(".")) {
/* 2556 */           EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 2557 */           returnMessage = "Email sent.";
        } else {
/* 2559 */           returnMessage = "Email not sent. Invalid email.";
        } 
/* 2561 */       } catch (Exception ex) {

        
/* 2564 */         returnMessage = "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      }
    
/* 2567 */     } else if (pais.equals("504")) {
      
/* 2569 */       if (!servicio.equals("0")) {
/* 2570 */         servicioHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Servicio adquirido:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + servicio + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2579 */         servicioHtml = "";
      } 
      
/* 2582 */       if (!cuota.equals("N/A")) {
/* 2583 */         cuotaMensualHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Cuota mensual:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + cuota + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2592 */         cuotaMensualHtml = "";
      } 
      
/* 2595 */       if (!fechaPago.equals("0")) {
/* 2596 */         fechaPagoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de pago:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaPago + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2605 */         fechaPagoHtml = "";
      } 
      
/* 2608 */       if (!fechaContratacion.equals("0")) {
/* 2609 */         fechaContratacionHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de contrataci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaContratacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2618 */         fechaContratacionHtml = "";
      } 
      
/* 2621 */       if (!vigencia.equals("0")) {
/* 2622 */         vigenciaContratoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Vigencia contrato:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + vigencia + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2631 */         vigenciaContratoHtml = "";
      } 
      
/* 2634 */       if (!fechaInstalacion.equals("0")) {
/* 2635 */         fechaInstalacionhtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha instalaci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaInstalacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2644 */         fechaInstalacionhtml = "";
      } 
      
/* 2647 */       if (!equipo.equals("0")) {
/* 2648 */         equipohtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Equipo:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + equipo + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 2657 */         equipohtml = "";
      } 
      
/* 2660 */       Map<String, String> inlineImages = new HashMap<>();
      
/* 2662 */       if (!llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 2663 */         mainServiceImgUrl = "Llamadas_Television_Internet";
/* 2664 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 2665 */       } else if (!llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 2666 */         mainServiceImgUrl = "Llamadas_Television";
/* 2667 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 2668 */       } else if (!llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 2669 */         mainServiceImgUrl = "Llamadas_Internet";
/* 2670 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 2671 */       } else if (!llamada.equals("0") && television.equals("0") && internet.equals("0")) {
/* 2672 */         mainServiceImgUrl = "Llamadas";
/* 2673 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 2674 */       } else if (llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 2675 */         mainServiceImgUrl = "Television_Internet";
/* 2676 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 2677 */       } else if (llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 2678 */         mainServiceImgUrl = "Television";
/* 2679 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 2680 */       } else if (llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 2681 */         mainServiceImgUrl = "Internet";
/* 2682 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
      } 
      
/* 2685 */       if (!mainServiceImgUrl.equals("")) {
/* 2686 */         mainServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + mainServiceImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }

      
/* 2697 */       if (!clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 2698 */         extraServicesImgUrl = "ClaroVideo_FOX_HBO";
/* 2699 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 2700 */       } else if (!clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 2701 */         extraServicesImgUrl = "ClaroVideo_FOX";
/* 2702 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 2703 */       } else if (!clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 2704 */         extraServicesImgUrl = "ClaroVideo_HBO";
/* 2705 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 2706 */       } else if (!clarovideo.equals("0") && fox.equals("0") && hbo.equals("0")) {
/* 2707 */         extraServicesImgUrl = "ClaroVideo";
/* 2708 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 2709 */       } else if (clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 2710 */         extraServicesImgUrl = "FOX_HBO";
/* 2711 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 2712 */       } else if (clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 2713 */         extraServicesImgUrl = "FOX";
/* 2714 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 2715 */       } else if (clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 2716 */         extraServicesImgUrl = "HBO";
/* 2717 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
      } 
      
/* 2720 */       if (!extraServicesImgUrl.equals("")) {
/* 2721 */         extraServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + extraServicesImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }

      
/* 2732 */       StringBuffer body = new StringBuffer("<html>\n   <head>\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n      <title>Bienvenido a Claro</title>\n      <style type=\"text/css\">\n         body {\n            margin: 0; \n            padding: 0; \n            min-width: 100% !important;\n         }\n        .content {\n            width: 100%; \n            max-width: 900px;\n         }\n         @media only screen and (min-device-width: 601px) {\n            .content {\n               width: 900px !important;\n            }\n         }\n         .col {\n            width: 100%;\n         }\n         .h1 {\n            font-size: 33px; \n            line-height: 38px; \n            font-weight: bold;\n         }\n         .servicesTr{\n            text-align: -webkit-center;\n         }\n         .h1, .h2, .bodycopy {\n            font-family: Yu Gothic light;\n            text-align: justify;\n         }\n         .innerpadding {\n            padding: 0px 30px 30px 30px;\n         }\n         .innerpadding2 {\n            padding: 20px 30px 0px 30px;\n         }\n         .borderbottom {\n            border-bottom: 0px solid #f2eeed;\n         }\n         .mainServicesImg {\n            text-align: center;\n         }\n         .extraServices{\n            vertical-align: bottom;\n            text-align: -webkit-center;\n         }\n         .mainServicesDesc {\n            font-weight: bold; \n            font-size: 19px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .mainServicesDescMarcacion {\n            font-weight: bold; \n            font-size: 20px;\n            text-align: center;\n         }\n         .mainServicesMargin{\n            margin-bottom: 3%;\n         }\n         .h2 {\n            padding: 0 0 15px 0; \n            font-size: 20px; \n            line-height: 22px;\n         }\n         .detailsTittle {\n            color: black; \n            font-weight: bold; \n            font-size: 20px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .bodycopy {\n            font-size: 20px; \n            line-height: 22px;\n         }\n         .bodycopyContact{\n            text-align: left;\n            font-family: Yu Gothic medium;\n         }\n         .bodycopyReminder{\n            text-align: center !important;\n         }\n         .description{\n            text-align: right;\n         }\n         .descriptionResult{\n            text-align: left;\n         }\n         .information{\n            border-bottom:0.001em solid gray;\n         }\n         img {\n            height: auto;\n         }\n         .footer {\n            padding: 20px 30px 15px 30px;\n         }\n      </style>\n   </head>\n   <body yahoo bgcolor=\"#ffffff\">\n      <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n            <td>\n               <!--[if (gte mso 9)|(IE)]>\n               <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                   <tr>\n                       <td>\n                           <![endif]-->\n                              <table class=\"content\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                                 <tr>\n                                    <td class=\"header\">\n                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                           <tr>\n                                               <td>\n                                                   <img src=\"cid:header2Fijo\" width=\"100%\" style\"max-width:900px;\" border=\"0\" alt=\"\" / >\n                                               </td>\n                                           </tr>\n                                       </table>\n                                    </td> \n                                 </tr>\n                                 <tr>\n                                    <td class=\"innerpadding borderbottom\">\n                                       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                                          <tr>\n                                             <td class=\"h2\">\n                                                <b>Tu experiencia Claro comienza aqu&#237;</b>, con la red mas grande de Telecomunicaciones de Centroam&#233;rica.\n                                             </td>\n                                          </tr>\n                                          <tr>\n                                             <td class=\"bodycopy\">\n                                                A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s informaci&#243;n comun&#237;cate al <b>" + num_cc + "</b> o acercate a tu Tienda Claro m&#225;s cercana.\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding borderbottom\">\n" + "                                       <!--[if (gte mso 9)|(IE)]>\n" + "                                          <table width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "                                             <tr>\n" + "                                                <td>\n" + "                                                   <![endif]-->\n" + "                                                      <table class=\"col\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \n" + "                                                         <tr>\n" + "                                                            <td>\n" + "                                                               <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" + servicioHtml + cuotaMensualHtml + fechaPagoHtml + fechaContratacionHtml + vigenciaContratoHtml + fechaInstalacionhtml + equipohtml + "                                                                  <tr class=\"information\">\n" + "                                                                     <td class=\"bodycopy description information\">\n" + "                                                                        <b>Env&#237;o factura electr&#243;nica:</b>\n" + "                                                                     </td>\n" + "                                                                     <td class=\"bodycopy descriptionResult information\">\n" + "                                                                        <a href=\"" + factura + "\">" + correoCliente + "</a>\n" + "                                                                     </td>\n" + "                                                                  </tr>\n" + "                                                               </table>\n" + "                                                            </td>\n" + "                                                         </tr>\n" + "                                                      </table>\n" + "                                                   <!--[if (gte mso 9)|(IE)]>\n" + "                                                </td>\n" + "                                             </tr>\n" + "                                          </table>\n" + "                                       <![endif]-->\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\" bgcolor=\"#e6e6e6\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td class=\"detailsTittle\">\n" + "                                                Detalles del plan adquirido:\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + mainServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + extraServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <img src=\"cid:reminderPayways\" width=\"100%\">\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td>\n" + "                                                            <img src=\"cid:MiClaro_MarcacionHN\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td class=\"authorizedBanks\">\n" + "                                                            <img src=\"cid:bancosautorizadosFija\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <a href=\"" + link + "\"><img src=\"cid:footer2FijoHN\" width=\"100%\"></a>\n" + "                                             </td> \n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"footer\" bgcolor=\"red\">\n" + "                                    </td>\n" + "                                 </tr>\n" + "                              </table>\n" + "                           <!--[if (gte mso 9)|(IE)]>\n" + "                        </td>\n" + "                     </tr>\n" + "                  </table>\n" + "               <![endif]-->\n" + "            </td>\n" + "         </tr>\n" + "      </table>\n" + "    </body>\n" + "</html>");


      
/* 2998 */       inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 2999 */       inlineImages.put("bancosautorizadosFija", "//app//weblogicChRe//IMAGENES//img//bancosautorizadosFija.png");
/* 3000 */       inlineImages.put("footer2FijoHN", "//app//weblogicChRe//IMAGENES//img//footer2FijoHN.png");
/* 3001 */       inlineImages.put("reminderPayways", "//app//weblogicChRe//IMAGENES//img//reminderPayways.png");
/* 3002 */       inlineImages.put("MiClaro_MarcacionHN", "//app//weblogicChRe//IMAGENES//img//MiClaro_MarcacionHN.png");



      
      try {
/* 3010 */         if (correoCliente.contains("@") || correoCliente.contains(".")) {
/* 3011 */           EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 3012 */           returnMessage = "Email sent.";
        } else {
/* 3014 */           returnMessage = "Email not sent. Invalid email.";
        } 
/* 3016 */       } catch (Exception ex) {

        
/* 3019 */         returnMessage = "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      }
    
/* 3022 */     } else if (pais.equals("505")) {
      
/* 3024 */       if (!servicio.equals("0")) {
/* 3025 */         servicioHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Servicio adquirido:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + servicio + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3034 */         servicioHtml = "";
      } 
      
/* 3037 */       if (!cuota.equals("N/A")) {
/* 3038 */         cuotaMensualHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Cuota mensual:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + cuota + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3047 */         cuotaMensualHtml = "";
      } 
      
/* 3050 */       if (!fechaPago.equals("0")) {
/* 3051 */         fechaPagoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha m&#225;xima de pago:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaPago + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3060 */         fechaPagoHtml = "";
      } 
      
/* 3063 */       if (!fechaContratacion.equals("0")) {
/* 3064 */         fechaContratacionHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de contrataci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaContratacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3073 */         fechaContratacionHtml = "";
      } 
      
/* 3076 */       if (!vigencia.equals("0")) {
/* 3077 */         vigenciaContratoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Vigencia contrato:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + vigencia + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3086 */         vigenciaContratoHtml = "";
      } 
      
/* 3089 */       if (!fechaInstalacion.equals("0")) {
/* 3090 */         fechaInstalacionhtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha instalaci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaInstalacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3099 */         fechaInstalacionhtml = "";
      } 
      
/* 3102 */       if (!equipo.equals("0")) {
/* 3103 */         equipohtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Equipo:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + equipo + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3112 */         equipohtml = "";
      } 
      
/* 3115 */       Map<String, String> inlineImages = new HashMap<>();
      
/* 3117 */       if (!llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 3118 */         mainServiceImgUrl = "Llamadas_Television_InternetNI";
/* 3119 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3120 */       } else if (!llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 3121 */         mainServiceImgUrl = "Llamadas_TelevisionNI";
/* 3122 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3123 */       } else if (!llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 3124 */         mainServiceImgUrl = "Llamadas_InternetNI";
/* 3125 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3126 */       } else if (!llamada.equals("0") && television.equals("0") && internet.equals("0")) {
/* 3127 */         mainServiceImgUrl = "LlamadasNI";
/* 3128 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3129 */       } else if (llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 3130 */         mainServiceImgUrl = "Television_Internet";
/* 3131 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3132 */       } else if (llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 3133 */         mainServiceImgUrl = "Television";
/* 3134 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3135 */       } else if (llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 3136 */         mainServiceImgUrl = "Internet";
/* 3137 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
      } 
      
/* 3140 */       if (!mainServiceImgUrl.equals("")) {
/* 3141 */         mainServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + mainServiceImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }


      
/* 3152 */       if (!clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 3153 */         extraServicesImgUrl = "ClaroVideo_FOX_HBO";
/* 3154 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3155 */       } else if (!clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 3156 */         extraServicesImgUrl = "ClaroVideo_FOX";
/* 3157 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3158 */       } else if (!clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 3159 */         extraServicesImgUrl = "ClaroVideo_HBO";
/* 3160 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3161 */       } else if (!clarovideo.equals("0") && fox.equals("0") && hbo.equals("0")) {
/* 3162 */         extraServicesImgUrl = "ClaroVideo";
/* 3163 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3164 */       } else if (clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 3165 */         extraServicesImgUrl = "FOX_HBO";
/* 3166 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3167 */       } else if (clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 3168 */         extraServicesImgUrl = "FOX";
/* 3169 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3170 */       } else if (clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 3171 */         extraServicesImgUrl = "HBO";
/* 3172 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
      } 
      
/* 3175 */       if (!extraServicesImgUrl.equals("")) {
/* 3176 */         extraServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + extraServicesImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }


      
/* 3187 */       StringBuffer body = new StringBuffer("<html>\n   <head>\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n      <title>Bienvenido a Claro</title>\n      <style type=\"text/css\">\n         body {\n            margin: 0; \n            padding: 0; \n            min-width: 100% !important;\n         }\n        .content {\n            width: 100%; \n            max-width: 900px;\n         }\n         @media only screen and (min-device-width: 601px) {\n            .content {\n               width: 900px !important;\n            }\n         }\n         .col {\n            width: 100%;\n         }\n         .h1 {\n            font-size: 33px; \n            line-height: 38px; \n            font-weight: bold;\n         }\n         .servicesTr{\n            text-align: -webkit-center;\n         }\n         .h1, .h2, .bodycopy {\n            font-family: Yu Gothic light;\n            text-align: justify;\n         }\n         .innerpadding {\n            padding: 0px 30px 30px 30px;\n         }\n         .innerpadding2 {\n            padding: 20px 30px 0px 30px;\n         }\n         .borderbottom {\n            border-bottom: 0px solid #f2eeed;\n         }\n         .mainServicesImg {\n            text-align: center;\n         }\n         .extraServices{\n            vertical-align: bottom;\n            text-align: -webkit-center;\n         }\n         .mainServicesDesc {\n            font-weight: bold; \n            font-size: 19px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .mainServicesDescMarcacion {\n            font-weight: bold; \n            font-size: 20px;\n            text-align: center;\n         }\n         .mainServicesMargin{\n            margin-bottom: 3%;\n         }\n         .h2 {\n            padding: 0 0 15px 0; \n            font-size: 20px; \n            line-height: 22px;\n         }\n         .detailsTittle {\n            color: black; \n            font-weight: bold; \n            font-size: 20px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .bodycopy {\n            font-size: 20px; \n            line-height: 22px;\n         }\n         .bodycopyContact{\n            text-align: left;\n            font-family: Yu Gothic medium;\n         }\n         .bodycopyReminder{\n            text-align: center !important;\n         }\n         .description{\n            text-align: right;\n         }\n         .descriptionResult{\n            text-align: left;\n         }\n         .information{\n            border-bottom:0.001em solid gray;\n         }\n         img {\n            height: auto;\n         }\n         .footer {\n            padding: 20px 30px 15px 30px;\n         }\n      </style>\n   </head>\n   <body yahoo bgcolor=\"#ffffff\">\n      <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n            <td>\n               <!--[if (gte mso 9)|(IE)]>\n               <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                   <tr>\n                       <td>\n                           <![endif]-->\n                              <table class=\"content\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                                 <tr>\n                                    <td class=\"header\">\n                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                           <tr>\n                                               <td>\n                                                   <img src=\"cid:header2Fijo\" width=\"100%\" style\"max-width:900px;\" border=\"0\" alt=\"\" / >\n                                               </td>\n                                           </tr>\n                                       </table>\n                                    </td> \n                                 </tr>\n                                 <tr>\n                                    <td class=\"innerpadding borderbottom\">\n                                       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                                          <tr>\n                                             <td class=\"h2\">\n                                                <b>Tu experiencia Claro comienza aqu&#237;</b>, con la red mas grande de Telecomunicaciones de Centroam&#233;rica.\n                                             </td>\n                                          </tr>\n                                          <tr>\n                                             <td class=\"bodycopy\">\n                                                A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s informaci&#243;n comun&#237;cate al <b>" + num_cc + "</b> o acercate a tu Tienda Claro m&#225;s cercana.\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding borderbottom\">\n" + "                                       <!--[if (gte mso 9)|(IE)]>\n" + "                                          <table width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "                                             <tr>\n" + "                                                <td>\n" + "                                                   <![endif]-->\n" + "                                                      <table class=\"col\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \n" + "                                                         <tr>\n" + "                                                            <td>\n" + "                                                               <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" + servicioHtml + cuotaMensualHtml + fechaPagoHtml + fechaContratacionHtml + vigenciaContratoHtml + fechaInstalacionhtml + equipohtml + "                                                                  <tr class=\"information\">\n" + "                                                                     <td class=\"bodycopy description information\">\n" + "                                                                        <b>Env&#237;o factura electr&#243;nica:</b>\n" + "                                                                     </td>\n" + "                                                                     <td class=\"bodycopy descriptionResult information\">\n" + "                                                                        <a href=\"" + factura + "\">" + correoCliente + "</a>\n" + "                                                                     </td>\n" + "                                                                  </tr>\n" + "                                                               </table>\n" + "                                                            </td>\n" + "                                                         </tr>\n" + "                                                      </table>\n" + "                                                   <!--[if (gte mso 9)|(IE)]>\n" + "                                                </td>\n" + "                                             </tr>\n" + "                                          </table>\n" + "                                       <![endif]-->\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\" bgcolor=\"#e6e6e6\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td class=\"detailsTittle\">\n" + "                                                Detalles del plan adquirido:\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + mainServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + extraServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <img src=\"cid:reminderPayways\" width=\"100%\">\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td>\n" + "                                                            <img src=\"cid:MiClaro_MarcacionNI\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td class=\"authorizedBanks\">\n" + "                                                            <img src=\"cid:bancosautorizadosFija\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <a href=\"" + link + "\"><img src=\"cid:footer2FijoNI\" width=\"100%\"></a>\n" + "                                             </td> \n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"footer\" bgcolor=\"red\">\n" + "                                    </td>\n" + "                                 </tr>\n" + "                              </table>\n" + "                           <!--[if (gte mso 9)|(IE)]>\n" + "                        </td>\n" + "                     </tr>\n" + "                  </table>\n" + "               <![endif]-->\n" + "            </td>\n" + "         </tr>\n" + "      </table>\n" + "    </body>\n" + "</html>");


      
/* 3453 */       inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 3454 */       inlineImages.put("bancosautorizadosFija", "//app//weblogicChRe//IMAGENES//img//bancosautorizadosFija.png");
/* 3455 */       inlineImages.put("footer2FijoNI", "//app//weblogicChRe//IMAGENES//img//footer2FijoNI.png");
/* 3456 */       inlineImages.put("reminderPayways", "//app//weblogicChRe//IMAGENES//img//reminderPayways.png");
/* 3457 */       inlineImages.put("MiClaro_MarcacionNI", "//app//weblogicChRe//IMAGENES//img//MiClaro_MarcacionNI.png");


      try {
/* 3465 */         if (correoCliente.contains("@") || correoCliente.contains(".")) {
/* 3466 */           EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 3467 */           returnMessage = "Email sent.";
        } else {
/* 3469 */           returnMessage = "Email not sent. Invalid email.";
        } 
/* 3471 */       } catch (Exception ex) {

        
/* 3474 */         returnMessage = "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      }
    
/* 3477 */     } else if (pais.equals("506")) {
      
/* 3479 */       if (!servicio.equals("0")) {
/* 3480 */         servicioHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Servicio adquirido:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + servicio + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3489 */         servicioHtml = "";
      } 
      
/* 3492 */       if (!cuota.equals("N/A")) {
/* 3493 */         cuotaMensualHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Cuota mensual del servicio:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        &#8353;." + cuota + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3502 */         cuotaMensualHtml = "";
      } 
      
/* 3505 */       if (!fechaPago.equals("0")) {
/* 3506 */         fechaPagoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de pago:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaPago + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3515 */         fechaPagoHtml = "";
      } 
      
/* 3518 */       if (!fechaContratacion.equals("0")) {
/* 3519 */         fechaContratacionHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de contrataci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaContratacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3528 */         fechaContratacionHtml = "";
      } 
      
/* 3531 */       if (!vigencia.equals("0")) {
/* 3532 */         vigenciaContratoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Vigencia contrato:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + vigencia + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3541 */         vigenciaContratoHtml = "";
      } 
      
/* 3544 */       if (!fechaInstalacion.equals("0")) {
/* 3545 */         fechaInstalacionhtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha instalaci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaInstalacion + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3554 */         fechaInstalacionhtml = "";
      } 
      
/* 3557 */       if (!equipo.equals("0")) {
/* 3558 */         equipohtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Equipo:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + equipo + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3567 */         equipohtml = "";
      } 
      
/* 3570 */       Map<String, String> inlineImages = new HashMap<>();
      
/* 3572 */       if (!llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 3573 */         mainServiceImgUrl = "Llamadas_Television_Internet";
/* 3574 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3575 */       } else if (!llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 3576 */         mainServiceImgUrl = "Llamadas_Television";
/* 3577 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3578 */       } else if (!llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 3579 */         mainServiceImgUrl = "Llamadas_Internet";
/* 3580 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3581 */       } else if (!llamada.equals("0") && television.equals("0") && internet.equals("0")) {
/* 3582 */         mainServiceImgUrl = "Llamadas";
/* 3583 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3584 */       } else if (llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 3585 */         mainServiceImgUrl = "Television_Internet";
/* 3586 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3587 */       } else if (llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 3588 */         mainServiceImgUrl = "Television";
/* 3589 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 3590 */       } else if (llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 3591 */         mainServiceImgUrl = "Internet";
/* 3592 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
      } 
      
/* 3595 */       if (!mainServiceImgUrl.equals("")) {
/* 3596 */         mainServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + mainServiceImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }

      
/* 3607 */       if (!clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 3608 */         extraServicesImgUrl = "ClaroVideo_FOX_HBO";
/* 3609 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3610 */       } else if (!clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 3611 */         extraServicesImgUrl = "ClaroVideo_FOX";
/* 3612 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3613 */       } else if (!clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 3614 */         extraServicesImgUrl = "ClaroVideo_HBO";
/* 3615 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3616 */       } else if (!clarovideo.equals("0") && fox.equals("0") && hbo.equals("0")) {
/* 3617 */         extraServicesImgUrl = "ClaroVideo";
/* 3618 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3619 */       } else if (clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 3620 */         extraServicesImgUrl = "FOX_HBO";
/* 3621 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3622 */       } else if (clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 3623 */         extraServicesImgUrl = "FOX";
/* 3624 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 3625 */       } else if (clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 3626 */         extraServicesImgUrl = "HBO";
/* 3627 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
      } 
      
/* 3630 */       if (!extraServicesImgUrl.equals("")) {
/* 3631 */         extraServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + extraServicesImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }

      
/* 3642 */       StringBuffer body = new StringBuffer("<html>\n   <head>\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n      <title>Bienvenido a Claro</title>\n      <style type=\"text/css\">\n         body {\n            margin: 0; \n            padding: 0; \n            min-width: 100% !important;\n         }\n        .content {\n            width: 100%; \n            max-width: 900px;\n         }\n         @media only screen and (min-device-width: 601px) {\n            .content {\n               width: 900px !important;\n            }\n         }\n         .col {\n            width: 100%;\n         }\n         .h1 {\n            font-size: 33px; \n            line-height: 38px; \n            font-weight: bold;\n         }\n         .servicesTr{\n            text-align: -webkit-center;\n         }\n         .h1, .h2, .bodycopy {\n            font-family: Yu Gothic light;\n            text-align: justify;\n         }\n         .innerpadding {\n            padding: 0px 30px 30px 30px;\n         }\n         .innerpadding2 {\n            padding: 20px 30px 0px 30px;\n         }\n         .borderbottom {\n            border-bottom: 0px solid #f2eeed;\n         }\n         .mainServicesImg {\n            text-align: center;\n         }\n         .extraServices{\n            vertical-align: bottom;\n            text-align: -webkit-center;\n         }\n         .mainServicesDesc {\n            font-weight: bold; \n            font-size: 19px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .mainServicesDescMarcacion {\n            font-weight: bold; \n            font-size: 20px;\n            text-align: center;\n         }\n         .mainServicesMargin{\n            margin-bottom: 3%;\n         }\n         .h2 {\n            padding: 0 0 15px 0; \n            font-size: 20px; \n            line-height: 22px;\n         }\n         .detailsTittle {\n            color: black; \n            font-weight: bold; \n            font-size: 20px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .bodycopy {\n            font-size: 20px; \n            line-height: 22px;\n         }\n         .bodycopyContact{\n            text-align: left;\n            font-family: Yu Gothic medium;\n         }\n         .bodycopyReminder{\n            text-align: center !important;\n         }\n         .description{\n            text-align: right;\n         }\n         .descriptionResult{\n            text-align: left;\n         }\n         .information{\n            border-bottom:0.001em solid gray;\n         }\n         img {\n            height: auto;\n         }\n         .footer {\n            padding: 20px 30px 15px 30px;\n         }\n      </style>\n   </head>\n   <body yahoo bgcolor=\"#ffffff\">\n      <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n            <td>\n               <!--[if (gte mso 9)|(IE)]>\n               <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                   <tr>\n                       <td>\n                           <![endif]-->\n                              <table class=\"content\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                                 <tr>\n                                    <td class=\"header\">\n                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                           <tr>\n                                               <td>\n                                                   <img src=\"cid:header2Fijo\" width=\"100%\" style\"max-width:900px;\" border=\"0\" alt=\"\" / >\n                                               </td>\n                                           </tr>\n                                       </table>\n                                    </td> \n                                 </tr>\n                                 <tr>\n                                    <td class=\"innerpadding borderbottom\">\n                                       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                                          <tr>\n                                             <td class=\"h2\">\n                                                <b>Tu experiencia Claro comienza aqu&#237;</b>, con la red mas grande de Telecomunicaciones de Centroam&#233;rica.\n                                             </td>\n                                          </tr>\n                                          <tr>\n                                             <td class=\"bodycopy\">\n                                                A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s informaci&#243;n comun&#237;cate al <b>" + num_cc + "</b> o acercate a tu Tienda Claro m&#225;s cercana.\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding borderbottom\">\n" + "                                       <!--[if (gte mso 9)|(IE)]>\n" + "                                          <table width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "                                             <tr>\n" + "                                                <td>\n" + "                                                   <![endif]-->\n" + "                                                      <table class=\"col\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \n" + "                                                         <tr>\n" + "                                                            <td>\n" + "                                                               <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" + servicioHtml + cuotaMensualHtml + fechaPagoHtml + fechaContratacionHtml + vigenciaContratoHtml + fechaInstalacionhtml + equipohtml + "                                                                  <tr class=\"information\">\n" + "                                                                     <td class=\"bodycopy description information\">\n" + "                                                                        <b>Env&#237;o factura electr&#243;nica:</b>\n" + "                                                                     </td>\n" + "                                                                     <td class=\"bodycopy descriptionResult information\">\n" + "                                                                        <a href=\"" + factura + "\">" + correoCliente + "</a>\n" + "                                                                     </td>\n" + "                                                                  </tr>\n" + "                                                               </table>\n" + "                                                            </td>\n" + "                                                         </tr>\n" + "                                                      </table>\n" + "                                                   <!--[if (gte mso 9)|(IE)]>\n" + "                                                </td>\n" + "                                             </tr>\n" + "                                          </table>\n" + "                                       <![endif]-->\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\" bgcolor=\"#e6e6e6\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td class=\"detailsTittle\">\n" + "                                                Detalles del plan adquirido:\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + mainServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + extraServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <img src=\"cid:reminderPayways\" width=\"100%\">\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td>\n" + "                                                            <img src=\"cid:MiClaro_MarcacionCR\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td class=\"authorizedBanks\">\n" + "                                                            <img src=\"cid:bancosautorizadosFija\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <a href=\"" + link + "\"><img src=\"cid:footer2FijoCR\" width=\"100%\"></a>\n" + "                                             </td> \n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"footer\" bgcolor=\"red\">\n" + "                                    </td>\n" + "                                 </tr>\n" + "                              </table>\n" + "                           <!--[if (gte mso 9)|(IE)]>\n" + "                        </td>\n" + "                     </tr>\n" + "                  </table>\n" + "               <![endif]-->\n" + "            </td>\n" + "         </tr>\n" + "      </table>\n" + "    </body>\n" + "</html>");

      
/* 3908 */       inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 3909 */       inlineImages.put("bancosautorizadosFija", "//app//weblogicChRe//IMAGENES//img//bancosautorizadosFija.png");
/* 3910 */       inlineImages.put("footer2FijoCR", "//app//weblogicChRe//IMAGENES//img//footer2FijoCR.png");
/* 3911 */       inlineImages.put("reminderPayways", "//app//weblogicChRe//IMAGENES//img//reminderPayways.png");
/* 3912 */       inlineImages.put("MiClaro_MarcacionCR", "//app//weblogicChRe//IMAGENES//img//MiClaro_MarcacionCR.png");


      
      try {
/* 3920 */         if (correoCliente.contains("@") || correoCliente.contains(".")) {
/* 3921 */           EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 3922 */           returnMessage = "Email sent.";
        } else {
/* 3924 */           returnMessage = "Email not sent. Invalid email.";
        } 
/* 3926 */       } catch (Exception ex) {

        
/* 3929 */         returnMessage = "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      }
    
/* 3932 */     } else if (pais.equals("507")) {
      
/* 3934 */       if (!servicio.equals("0")) {
/* 3935 */         servicioHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Servicio adquirido:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + servicio + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3944 */         servicioHtml = "";
      } 
      
/* 3947 */       if (!cuota.equals("N/A")) {
/* 3948 */         cuotaMensualHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Cuota mensual:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + cuota + " + impuestos\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3957 */         cuotaMensualHtml = "";
      } 
      
/* 3960 */       if (!fechaPago.equals("0")) {
/* 3961 */         fechaPagoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha l&#237;mite de pago:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaPago + "\n" + "    </td>\n" + "</tr>\n";


      
      }
      else {


        
/* 3970 */         fechaPagoHtml = "";
      } 
      
/* 3973 */       if (!fechaContratacion.equals("0")) {
/* 3974 */         fechaContratacionHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha de contrataci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaContratacion + "\n" + "    </td>\n" + "</tr>\n";

      
      }
      else {

        
/* 3983 */         fechaContratacionHtml = "";
      } 
      
/* 3986 */       if (!vigencia.equals("0")) {
/* 3987 */         vigenciaContratoHtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Vigencia contrato:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + vigencia + "\n" + "    </td>\n" + "</tr>\n";

      
      }
      else {


        
/* 3996 */         vigenciaContratoHtml = "";
      } 
      
/* 3999 */       if (!fechaInstalacion.equals("0")) {
/* 4000 */         fechaInstalacionhtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Fecha instalaci&#243;n:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + fechaInstalacion + "\n" + "    </td>\n" + "</tr>\n";

      
      }
      else {

        
/* 4009 */         fechaInstalacionhtml = "";
      } 
      
/* 4012 */       if (!equipo.equals("0")) {
/* 4013 */         equipohtml = "<tr class=\"information\">\n    <td class=\"bodycopy description information\">\n        <b>Equipo:</b>\n    </td>\n    <td class=\"bodycopy descriptionResult information\">\n        " + equipo + "\n" + "    </td>\n" + "</tr>\n";

      
      }
      else {

        
/* 4022 */         equipohtml = "";
      } 
      
/* 4025 */       Map<String, String> inlineImages = new HashMap<>();
      
/* 4027 */       if (!llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 4028 */         mainServiceImgUrl = "Llamadas_Television_Internet";
/* 4029 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 4030 */       } else if (!llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 4031 */         mainServiceImgUrl = "Llamadas_Television";
/* 4032 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 4033 */       } else if (!llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 4034 */         mainServiceImgUrl = "Llamadas_Internet";
/* 4035 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 4036 */       } else if (!llamada.equals("0") && television.equals("0") && internet.equals("0")) {
/* 4037 */         mainServiceImgUrl = "Llamadas";
/* 4038 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 4039 */       } else if (llamada.equals("0") && !television.equals("0") && !internet.equals("0")) {
/* 4040 */         mainServiceImgUrl = "Television_Internet";
/* 4041 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 4042 */       } else if (llamada.equals("0") && !television.equals("0") && internet.equals("0")) {
/* 4043 */         mainServiceImgUrl = "Television";
/* 4044 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
/* 4045 */       } else if (llamada.equals("0") && television.equals("0") && !internet.equals("0")) {
/* 4046 */         mainServiceImgUrl = "Internet";
/* 4047 */         inlineImages.put(mainServiceImgUrl, "//app//weblogicChRe//IMAGENES//img//" + mainServiceImgUrl + ".png");
      } 
      
/* 4050 */       if (!mainServiceImgUrl.equals("")) {
/* 4051 */         mainServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + mainServiceImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }


      
/* 4062 */       if (!clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 4063 */         extraServicesImgUrl = "ClaroVideo_FOX_HBO";
/* 4064 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 4065 */       } else if (!clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 4066 */         extraServicesImgUrl = "ClaroVideo_FOX";
/* 4067 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 4068 */       } else if (!clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 4069 */         extraServicesImgUrl = "ClaroVideo_HBO";
/* 4070 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 4071 */       } else if (!clarovideo.equals("0") && fox.equals("0") && hbo.equals("0")) {
/* 4072 */         extraServicesImgUrl = "ClaroVideo";
/* 4073 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 4074 */       } else if (clarovideo.equals("0") && !fox.equals("0") && !hbo.equals("0")) {
/* 4075 */         extraServicesImgUrl = "FOX_HBO";
/* 4076 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 4077 */       } else if (clarovideo.equals("0") && !fox.equals("0") && hbo.equals("0")) {
/* 4078 */         extraServicesImgUrl = "FOX";
/* 4079 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
/* 4080 */       } else if (clarovideo.equals("0") && fox.equals("0") && !hbo.equals("0")) {
/* 4081 */         extraServicesImgUrl = "HBO";
/* 4082 */         inlineImages.put(extraServicesImgUrl, "//app//weblogicChRe//IMAGENES//img//" + extraServicesImgUrl + ".png");
      } 
      
/* 4085 */       if (!extraServicesImgUrl.equals("")) {
/* 4086 */         extraServicesHtml = "<td>\n    <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n             <td>\n                 <img src=\"cid:" + extraServicesImgUrl + "\" width=\"100%\">\n" + "             </td>\n" + "         </tr>\n" + "    </table>\n" + "</td>\n";
      }

      
/* 4097 */       StringBuffer body = new StringBuffer("<html>\n   <head>\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n      <title>Bienvenido a Claro</title>\n      <style type=\"text/css\">\n         body {\n            margin: 0; \n            padding: 0; \n            min-width: 100% !important;\n         }\n        .content {\n            width: 100%; \n            max-width: 900px;\n         }\n         @media only screen and (min-device-width: 601px) {\n            .content {\n               width: 900px !important;\n            }\n         }\n         .col {\n            width: 100%;\n         }\n         .h1 {\n            font-size: 33px; \n            line-height: 38px; \n            font-weight: bold;\n         }\n         .servicesTr{\n            text-align: -webkit-center;\n         }\n         .h1, .h2, .bodycopy {\n            font-family: Yu Gothic light;\n            text-align: justify;\n         }\n         .innerpadding {\n            padding: 0px 30px 30px 30px;\n         }\n         .innerpadding2 {\n            padding: 20px 30px 0px 30px;\n         }\n         .borderbottom {\n            border-bottom: 0px solid #f2eeed;\n         }\n         .mainServicesImg {\n            text-align: center;\n         }\n         .extraServices{\n            vertical-align: bottom;\n            text-align: -webkit-center;\n         }\n         .mainServicesDesc {\n            font-weight: bold; \n            font-size: 19px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .mainServicesDescMarcacion {\n            font-weight: bold; \n            font-size: 20px;\n            text-align: center;\n         }\n         .mainServicesMargin{\n            margin-bottom: 3%;\n         }\n         .h2 {\n            padding: 0 0 15px 0; \n            font-size: 20px; \n            line-height: 22px;\n         }\n         .detailsTittle {\n            color: black; \n            font-weight: bold; \n            font-size: 20px;\n            font-family: Yu Gothic medium;\n            text-align: center;\n         }\n         .bodycopy {\n            font-size: 20px; \n            line-height: 22px;\n         }\n         .bodycopyContact{\n            text-align: left;\n            font-family: Yu Gothic medium;\n         }\n         .bodycopyReminder{\n            text-align: center !important;\n         }\n         .description{\n            text-align: right;\n         }\n         .descriptionResult{\n            text-align: left;\n         }\n         .information{\n            border-bottom:0.001em solid gray;\n         }\n         img {\n            height: auto;\n         }\n         .footer {\n            padding: 20px 30px 15px 30px;\n         }\n      </style>\n   </head>\n   <body yahoo bgcolor=\"#ffffff\">\n      <table width=\"100%\" bgcolor=\"#ffffff\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n         <tr>\n            <td>\n               <!--[if (gte mso 9)|(IE)]>\n               <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                   <tr>\n                       <td>\n                           <![endif]-->\n                              <table class=\"content\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                                 <tr>\n                                    <td class=\"header\">\n                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                                           <tr>\n                                               <td>\n                                                   <img src=\"cid:header2Fijo\" width=\"100%\" style\"max-width:900px;\" border=\"0\" alt=\"\" / >\n                                               </td>\n                                           </tr>\n                                       </table>\n                                    </td> \n                                 </tr>\n                                 <tr>\n                                    <td class=\"innerpadding borderbottom\">\n                                       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n                                          <tr>\n                                             <td class=\"h2\">\n                                                <b>Tu experiencia Claro comienza aqu&#237;</b>, con la red mas grande de Telecomunicaciones de Centroam&#233;rica.\n                                             </td>\n                                          </tr>\n                                          <tr>\n                                             <td class=\"bodycopy\">\n                                                A continuaci&#243;n encontrar&#225;s informaci&#243;n detallada sobre tu servicio, si deseas m&#225;s informaci&#243;n comun&#237;cate al <b>" + num_cc + "</b> o acercate a tu Tienda Claro m&#225;s cercana.\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding borderbottom\">\n" + "                                       <!--[if (gte mso 9)|(IE)]>\n" + "                                          <table width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "                                             <tr>\n" + "                                                <td>\n" + "                                                   <![endif]-->\n" + "                                                      <table class=\"col\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \n" + "                                                         <tr>\n" + "                                                            <td>\n" + "                                                               <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" + servicioHtml + cuotaMensualHtml + fechaPagoHtml + fechaContratacionHtml + vigenciaContratoHtml + fechaInstalacionhtml + equipohtml + "                                                                  <tr class=\"information\">\n" + "                                                                     <td class=\"bodycopy description information\">\n" + "                                                                        <b>Env&#237;o factura electr&#243;nica:</b>\n" + "                                                                     </td>\n" + "                                                                     <td class=\"bodycopy descriptionResult information\">\n" + "                                                                        <a href=\"" + factura + "\">" + correoCliente + "</a>\n" + "                                                                     </td>\n" + "                                                                  </tr>\n" + "                                                               </table>\n" + "                                                            </td>\n" + "                                                         </tr>\n" + "                                                      </table>\n" + "                                                   <!--[if (gte mso 9)|(IE)]>\n" + "                                                </td>\n" + "                                             </tr>\n" + "                                          </table>\n" + "                                       <![endif]-->\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\" bgcolor=\"#e6e6e6\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td class=\"detailsTittle\">\n" + "                                                Detalles del plan adquirido:\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + mainServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + extraServicesHtml + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <img src=\"cid:reminderPayways\" width=\"100%\">\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainServicesMargin\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                                    <tr>\n" + "                                                        <td>\n" + "                                                            <img src=\"cid:centrosDePago\" width=\"100%\">\n" + "                                                        </td>\n" + "                                                    </tr>\n" + "                                                </table>\n" + "                                             </td>\n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"innerpadding2 borderbottom\">\n" + "                                       <table width=\"100%\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" + "                                          <tr>\n" + "                                             <td>\n" + "                                                <a href=\"" + link + "\"><img src=\"cid:footer2FijoPA\" width=\"100%\"></a>\n" + "                                             </td> \n" + "                                          </tr>\n" + "                                       </table>\n" + "                                    </td>\n" + "                                 </tr>\n" + "                                 <tr>\n" + "                                    <td class=\"footer\" bgcolor=\"red\">\n" + "                                    </td>\n" + "                                 </tr>\n" + "                              </table>\n" + "                           <!--[if (gte mso 9)|(IE)]>\n" + "                        </td>\n" + "                     </tr>\n" + "                  </table>\n" + "               <![endif]-->\n" + "            </td>\n" + "         </tr>\n" + "      </table>\n" + "    </body>\n" + "</html>");


      
/* 4350 */       inlineImages.put("header2Fijo", "//app//weblogicChRe//IMAGENES//img//header2Fijo_1.png");
/* 4351 */       inlineImages.put("footer2FijoPA", "//app//weblogicChRe//IMAGENES//img//footer2FijoPA.png");
/* 4352 */       inlineImages.put("reminderPayways", "//app//weblogicChRe//IMAGENES//img//reminderPayways.png");
/* 4353 */       inlineImages.put("centrosDePago", "//app//weblogicChRe//IMAGENES//img//centrosDePago.png");


      
      try {
/* 4361 */         if (correoCliente.contains("@") || correoCliente.contains(".")) {
/* 4362 */           EnvioCorreo.send(host, from, correoCliente, body.toString(), inlineImages);
/* 4363 */           returnMessage = "Email sent.";
        } else {
/* 4365 */           returnMessage = "Email not sent. Invalid email.";
        } 
/* 4367 */       } catch (Exception ex) {

        
/* 4370 */         returnMessage = "Email not sent." + ex.toString() + " " + cuerpoCorreo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
      } 
    } else {
      
/* 4374 */       returnMessage = "Error en pais.";
    } 
    
/* 4377 */     return returnMessage;
  }
}

