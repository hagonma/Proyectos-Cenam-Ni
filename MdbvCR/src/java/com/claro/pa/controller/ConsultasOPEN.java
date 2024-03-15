/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.controller;


import com.claro.pa.dao.DataAccessObject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import org.json.JSONArray;

/**
 *
 * @author pablo.cabrera
 */
public class ConsultasOPEN extends DataAccessObject {
    
    int pantalla, rol, estado, id;
    public ResultSet rs;
    public ResultSet rsQ;


    public PreparedStatement ps;
    public JSONArray responseArray;
    public Helper hp = new Helper();
    public ConexionOPEN con = new ConexionOPEN();
    public ConexionOracle conO = new ConexionOracle(); 
    public ConexionOracle conO2 = new ConexionOracle();
    public String pais;
    public String queryCRM ="";
    public String queryDatosClienteOne="";
    public String queryDatosClienteDT="";
    public String queryBundlesOne="";
    public String queryBundlesDT="";
    public String queryFHOne="";
    public String queryFHDT="";
    public String queryNumeroSingle ="";
    public String queryNumeroDT ="";
    

    public ConsultasOPEN(String idpais) {
        pais = idpais;
        
    }
    
    public void obtenerQuerys() throws SQLException, Exception{

        try {      
                connect2();
                String consulta1="";
                consulta1 ="SELECT CONSULTA FROM CDN_CAT_CONSULTAS_CR WHERE ID_CONSULTA = 1";
                stm = conNew2.prepareStatement(consulta1);
                rst = stm.executeQuery();
            while(rst.next()){
                queryCRM = rst.getString("CONSULTA");
            }
                }catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
        
        try {    
             connect2();
        String consultaOnePlay="";
                consultaOnePlay ="SELECT CONSULTA FROM CDN_CAT_CONSULTAS_CR WHERE ID_CONSULTA = 2";
            stm = conNew2.prepareStatement(consultaOnePlay);
                rst = stm.executeQuery();
            while(rst.next()){
                queryDatosClienteOne = rst.getString("CONSULTA");
            }
                }catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
        
        try { 
            connect2();
                String consultaDT="";
                consultaDT ="SELECT CONSULTA FROM CDN_CAT_CONSULTAS_CR WHERE ID_CONSULTA = 4";
            stm = conNew2.prepareStatement(consultaDT);
                rst = stm.executeQuery();
            while(rst.next()){
                queryDatosClienteDT = rst.getString("CONSULTA");
            }
                }catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
        
        try {    
            connect2();
        String consultaOneFlags="";
                consultaOneFlags ="SELECT CONSULTA FROM CDN_CAT_CONSULTAS_CR WHERE ID_CONSULTA = 3";
            stm = conNew2.prepareStatement(consultaOneFlags);
                rst = stm.executeQuery();
            while(rst.next()){
                queryBundlesOne = rst.getString("CONSULTA");
            }
                }catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
        
        try {    
            connect2();
        String consultaDTFlags="";
                consultaDTFlags ="SELECT CONSULTA FROM CDN_CAT_CONSULTAS_CR WHERE ID_CONSULTA = 5";
            stm = conNew2.prepareStatement(consultaDTFlags);
                rst = stm.executeQuery();
            while(rst.next()){
                queryBundlesDT = rst.getString("CONSULTA");
            }
                }catch(SQLException e){
			e.printStackTrace();
		}finally{
			disconnect();
		}
        obtenerInformacion(queryCRM, queryDatosClienteOne ,queryDatosClienteDT, queryBundlesOne, queryBundlesDT);
    }
    
    public ResultSet obtenerInformacion(String consulta1, String consulta2, String consulta3, String consulta4, String consulta5) throws SQLException, Exception{ 

                String query = "";
                query=consulta1;
            
            //consulta inicial
        try{
            connect3();
            stm = conNew3.prepareStatement(query);
            rst = stm.executeQuery();
            while(rst.next()){
                
        String id_cliente = "";
        String cliente = "";
        String servicio_adquirido = "";
        double cuotaPlan = 0;
        int cuotaServicios = 0;
        String cuota_mensual = "";
        String fecha_pago = "";
        String fecha_contratacion = "";
        String vigencia_contrato = "";
        String fecha_instalacion = "";
        String equipo = "";
        String email = "";
        String television = "";
        String linea_fija = "";
        String internet = "";
        String claro_video = "";
        String fox = "";
        String hbo = "";
        String update = "";
        String paisAb = "";
        String contrato = "";
        String numeroReferencia = "";
        String basico = "";
        String avanzado = "";
        String velocidadInternet = "";
        String _tipo_tv = "";
        String tipoPlan = "";
        String planCombinado = "";
        String tipoCliente="";
        String numeroTelefono="";
        
                id_cliente = rst.getString("CLIENTE");
                fecha_instalacion = rst.getString("RenovacionContrato");
                contrato = rst.getString("CONTRATO");
                tipoCliente = rst.getString("TipoCliente");
                vigencia_contrato = rst.getString("VigenciaContrato");
                fecha_contratacion = rst.getString("FechaContrato");
                
                
                if (tipoCliente.equals("OnePlay")){ 
                    //consulta 2
                   try{
                        connect();
                        stm = conNew.prepareStatement(consulta2);
                        stm.setString(1, contrato);
                        stm.setString(2, contrato);
                        rst2 = stm.executeQuery();
                    while(rst2.next()){
                servicio_adquirido = rst2.getString("SpCodeDes");
                cliente = rst2.getString("NombreCliente");
                fecha_pago = rst2.getString("FechaPago");
                email = rst2.getString("Correo");
                numeroReferencia = rst2.getString("NumeroReferencia");  
                numeroTelefono = rst2.getString("NumeroServicio");

                    }
                   }catch(SQLException e){
			e.printStackTrace();
			disconnect();
		}finally{
		}                   
                try{
                        connect();
                        stm = conNew.prepareStatement(consulta4);
                        stm.setString(1, contrato);
                        rst3 = stm.executeQuery();
                    while(rst3.next()){
                        
                if(tipoPlan.equals("0")||tipoPlan.equals("")){
                    tipoPlan =((rst3.getString("TipoPlan"))!=null)?rst3.getString("TipoPlan"):"";
                }
		if (_tipo_tv.equals("0")||_tipo_tv.equals("")){
			_tipo_tv = ((rst3.getString("TipoPlanTV"))!=null)?rst3.getString("TipoPlanTV"):"";			
                }
                if(velocidadInternet.equals("")){
                velocidadInternet = ((rst3.getString("velocidad"))!=null)?rst3.getString("velocidad"):"";
                }
                    cuotaPlan=cuotaPlan+rst3.getDouble("CuotaTotal");
                if (linea_fija.equals("0")||linea_fija.equals("")){
                linea_fija = ((rst3.getString("llamada"))!=null)?rst3.getString("llamada"):"";
		}
                if (internet.equals("0")||internet.equals("")){
                internet = ((rst3.getString("internet"))!=null)?rst3.getString("internet"):"";
		}
                if (television.equals("0")||television.equals("")){
                television = ((rst3.getString("television"))!=null)?rst3.getString("television"):"";
		}
                if (claro_video.equals("0")||claro_video.equals("")){
                claro_video = ((rst3.getString("ClaroVideo"))!=null)?rst3.getString("ClaroVideo"):"";
		}
                if (fox.equals("0")||fox.equals("")){
		fox = ((rst3.getString("FOX"))!=null)?rst3.getString("FOX"):"";
		}
                if (hbo.equals("0")||hbo.equals("")){
                hbo = ((rst3.getString("HBO"))!=null)?rst3.getString("HBO"):""; 
		}
                    }
                   }catch(SQLException e){
			e.printStackTrace();
			disconnect();
		}finally{
		}
                DecimalFormat df = new DecimalFormat("###.##");
                cuota_mensual=String.valueOf(df.format(cuotaPlan));
                
                }else{
                    //consulta 2.1
                   try{
                        connect();
                        stm = conNew.prepareStatement(consulta3);
                        stm.setString(1, contrato);
                        stm.setString(2, contrato);
                        rst2 = stm.executeQuery();
                    while(rst2.next()){
                servicio_adquirido = rst2.getString("SpCodeDes");
                cliente = rst2.getString("NombreCliente");
                fecha_pago = rst2.getString("FechaPago");
                email = rst2.getString("Correo");
                numeroReferencia = rst2.getString("NumeroReferencia");  
                numeroTelefono = rst2.getString("NumeroServicio");
                    }
                   }catch(SQLException e){
			e.printStackTrace();
			disconnect();
		}finally{
		}
                try{
                        connect();
                        stm = conNew.prepareStatement(consulta5);
                        stm.setString(1, contrato);
                        rst3 = stm.executeQuery();
                    while(rst3.next()){
                        
                if(tipoPlan.equals("0")||tipoPlan.equals("")){
                    tipoPlan =((rst3.getString("TipoPlan"))!=null)?rst3.getString("TipoPlan"):"";
                }
		if (_tipo_tv.equals("0")||_tipo_tv.equals("")){
			_tipo_tv = ((rst3.getString("TipoPlanTV"))!=null)?rst3.getString("TipoPlanTV"):"";			
                }
                if(velocidadInternet.equals("")){
                velocidadInternet = ((rst3.getString("velocidad"))!=null)?rst3.getString("velocidad"):"";
                }

                    cuotaPlan=cuotaPlan+rst3.getDouble("CuotaTotal");
                if (linea_fija.equals("0")||linea_fija.equals("")){
                linea_fija = ((rst3.getString("llamada"))!=null)?rst3.getString("llamada"):"";
		}
                if (internet.equals("0")||internet.equals("")){
                internet = ((rst3.getString("internet"))!=null)?rst3.getString("internet"):"";
		}
                if (television.equals("0")||television.equals("")){
                television = ((rst3.getString("television"))!=null)?rst3.getString("television"):"";
		}
                if (claro_video.equals("0")||claro_video.equals("")){
                claro_video = ((rst3.getString("ClaroVideo"))!=null)?rst3.getString("ClaroVideo"):"";
		}
                if (fox.equals("0")||fox.equals("")){
		fox = ((rst3.getString("FOX"))!=null)?rst3.getString("FOX"):"";
		}
                if (hbo.equals("0")||hbo.equals("")){
                hbo = ((rst3.getString("HBO"))!=null)?rst3.getString("HBO"):""; 
		}
                    }
                   }catch(SQLException e){
			e.printStackTrace();
			disconnect();
		}finally{
		}
                //DecimalFormat df = new DecimalFormat("###.##");
                //cuota_mensual=String.valueOf(df.format(cuotaPlan));
                
                BigDecimal bd = new BigDecimal(cuotaPlan).setScale(2, RoundingMode.HALF_UP);
                String dec = String.format("%.2f", bd);
                cuota_mensual = dec;
                
                }
                String tipo_tv = "";
                
                if(_tipo_tv == null||_tipo_tv.isEmpty()){
                   basico = "0";
                }
                
                if(velocidadInternet.equals("")){
                   velocidadInternet = "0";
                }
               if(_tipo_tv!=null){
                if(_tipo_tv.equals("SAT-AV")){
                    tipo_tv = "1";
                }else if(_tipo_tv.equals("SAT-AVP")){
                    tipo_tv = "2";
                }else if(_tipo_tv.equals("SAT-BA")){
                    tipo_tv = "3";
                }else if(_tipo_tv.equals("GPON-AVHD")){
                    tipo_tv = "4";
                }else if(_tipo_tv.equals("GPON-AVHDP")){
                    tipo_tv = "5";
                }else if(_tipo_tv.equals("0")){
                    tipo_tv = "6";
                }else if(_tipo_tv.equals("SAT-MINI")){
                    tipo_tv = "7";
                }else{
                    tipo_tv = "0";
                }
                }else{
                    tipo_tv = "0";   
               }
               
               if(tipoPlan.equals("0")||tipoPlan.equals("")){
               planCombinado = tipo_tv;
               }else{
               planCombinado = tipoPlan+"-"+tipo_tv;
               }
               
                if(id_cliente == null||id_cliente.isEmpty()){
                   id_cliente = "0";
                }
                if(numeroReferencia == null||numeroReferencia.isEmpty()){
                   numeroReferencia = "0";
                }
                
                if(contrato == null||contrato.isEmpty()){
                   contrato = "0";
                }
                if(cliente == null||cliente.isEmpty()){
                   cliente = "0";
                }
                if(servicio_adquirido == null||servicio_adquirido.isEmpty()){
                   servicio_adquirido = "0";
                }
                if(cuota_mensual == null||cuota_mensual.isEmpty()){
                   cuota_mensual = "0";
                }
                if(fecha_pago == null||fecha_pago.isEmpty()){
                   fecha_pago = "0";
                }
                if(fecha_contratacion == null||fecha_contratacion.isEmpty()){
                   fecha_contratacion = "0";
                }
                if(vigencia_contrato == null||vigencia_contrato.isEmpty()){
                   vigencia_contrato = "0";
                }
                if(fecha_instalacion == null||fecha_instalacion.isEmpty()){
                   fecha_instalacion = "0";
                }
                if(equipo == null||equipo.isEmpty()){
                   equipo = "0";
                }
                if(email == null||email.isEmpty()){
                   email = "0";
                }
                if(television == null||television.isEmpty()){
                   television = "0";
                }
                if(linea_fija == null||linea_fija.isEmpty()){
                   linea_fija = "0";
                }if(internet == null||internet.isEmpty()||internet.equals("0")){
                   internet = "0";
                }
                if(claro_video == null||claro_video.isEmpty()){
                   claro_video = "0";
                }
                if(fox == null||fox.isEmpty()){
                   fox = "0";
                }
                if(hbo == null||hbo.isEmpty()){
                   hbo = "0";
                }
                if(numeroTelefono == null||numeroTelefono.isEmpty()){
                    numeroTelefono="0";
                }
                if(!linea_fija.equals("0")){
                    linea_fija="1";
                }
                
                Conector validador = new Conector("oracleprd05-scan", "CDNRG", "C1@r0cdn#", "3871", "CDNRG");
                String [][]resultado1;
                validador.conectar();
                
                if(fecha_pago.length()>19){
                fecha_pago  = fecha_pago.substring(0,19);   
                }
                if(fecha_contratacion.length()>19){
                fecha_contratacion  = fecha_contratacion.substring(0,19);
                }
                if(fecha_instalacion.length()>19){
                fecha_instalacion  = fecha_instalacion.substring(0,19);
                }
                
                if(fecha_pago.equals("0")){
                fecha_pago  = "SYSDATE";   
                }
                if(fecha_contratacion.equals("0")){
                fecha_contratacion  = "SYSDATE";
                }
                if(fecha_instalacion.equals("0")){
                fecha_instalacion  =  "SYSDATE";
                }


                resultado1 = validador.consultas("SELECT FECHA_ENVIO FROM CDN_MAIL WHERE ID = '"+id_cliente+"' AND PAIS_ID = '"+this.pais+"'");
                if (resultado1.length == 0) { 
                    try{
                   if (!velocidadInternet.equals("0")){
                       internet="1";
                   }
                   connect2();
                    update = "INSERT INTO CDN_MAIL (ID, NOMBRE_CLIENTE, SERVICIO, CUOTA, FECHA_PAGO, FECHA_CONTRATACION, VIGENCIA_CONTRATO, FECHA_INSTALACION, EQUIPO, CORREO_CLIENTE, PAIS_ID, FECHA_ENVIO, INTERNET, LLAMADA, TELEVISION, CLARO_VIDEO, FOX, HBO, CONTRATO, NUMERO_REFERENCIA, TIPO_PLAN_TV, VELOCIDAD, NUMERO_SERVICIO)\n" +
                    "VALUES ("+id_cliente+",'"+cliente+"','"+servicio_adquirido+"','"+cuota_mensual+"',TO_DATE('"+fecha_pago+"','YYYY-MM-DD HH24:MI:SS'),TO_DATE('"+fecha_contratacion+"','YYYY-MM-DD HH24:MI:SS'),'"+vigencia_contrato+"',TO_CHAR(TO_DATE('"+fecha_instalacion+"','YYYY-MM-DD HH24:MI:SS'),'DD/MM/YYYY'),'"+equipo+"','"+email+"',"+this.pais+",SYSDATE,"+internet+","+linea_fija+","+television+","+claro_video+","+fox+","+hbo+",'"+contrato+"','"+numeroReferencia+"','"+planCombinado+"','"+velocidadInternet+"','"+numeroTelefono+"')";  
                   
                    System.out.println(update);
                        stm = conNew2.prepareStatement(update);
                        rst4 = stm.executeQuery();
                    }catch(SQLException e){
			e.printStackTrace();
			disconnect();
		}finally{
		}
                       
                    validador.desconectar();
                    if (email.equals("0")){
                        
                    } else{
                        Select param  = new Select();
                        System.out.println("Envio");
                        param.selectFijo(id_cliente, pais);
                    }    
                }else{
                }
                }
		}catch(SQLException e){
			e.printStackTrace();
		disconnect();
		}finally{
                disconnect();
		}
           
            return rs;            
        
    }
    
    
}
