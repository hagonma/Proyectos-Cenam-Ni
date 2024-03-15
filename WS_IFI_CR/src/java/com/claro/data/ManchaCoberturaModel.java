/* 
 * Clase utilizada para la consulta de cargas de precios
 */
package com.claro.data;

import com.claro.services.ApplicationConfig;

import com.jcraft.jsch.Session;

import com.claro.beans.RespContainsPolygon;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;

import com.vividsolutions.jts.geom.*;
import de.micromata.opengis.kml.v_2_2_0.LineStyle;
import de.micromata.opengis.kml.v_2_2_0.PolyStyle;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Diego Lopez
 */
public class ManchaCoberturaModel {

    private DbModel base;
    JsonParser parser;
    JSch sftp = new JSch();
    Session session = null;
    Channel channel;
    ChannelSftp sftpChannel = null;
    InputStream bi = null;
    private static final String ERROR_CLOSE_BD = "Error al cerrar conexion a la base de datos. ";
    private String codigoPais="506";

    
    public ManchaCoberturaModel() {
        this.base = new DbModel();
        this.parser = new JsonParser();       
    }
    
    public String crearKmlsManchaDeCobertura(String key, String codePais) throws UnsupportedEncodingException, IOException  {
         
        ManchaCoberturaModel l = new ManchaCoberturaModel();
        String creacionKmlTecno="";
        String code="0";
        String msg="SUCCESS";
        String respuesta = jsonMessage("-999","Error al crear KMLs de Mancha de Cobertura","");
        String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codePais));
        String pathKml=base.getPathKml(Integer.parseInt(codePais));
        
        int cantFiles=ApplicationConfig.Kmls.size();
        int indexFileSelect=0;
        String respLog="";
        
        do
        {
            try{
                ArrayList<String> sectoresNoSaturados=new ArrayList<String>();
                File file = ApplicationConfig.Kmls.get(indexFileSelect);
                indexFileSelect+=1;
                sectoresNoSaturados=getIngSectorsNoSaturados(file.getName().replace(EXT_FILE, "").trim(),codePais);
                creacionKmlTecno=creacionKmlTecno+"{\""+file.getName().replace(EXT_FILE, "").trim()+"\":\"";
                creacionKmlTecno=creacionKmlTecno+l.parseKml(file,pathKml, file.getName().replace(EXT_FILE, "").trim(),EXT_FILE, sectoresNoSaturados)+"\"},";
             } catch (Exception ex) {
                    code="-2";
                    msg="ERROR";
                    creacionKmlTecno=ex.toString();
                    indexFileSelect=cantFiles;
                }
        }
        while(cantFiles>indexFileSelect);
        int len=creacionKmlTecno.length();
        creacionKmlTecno=creacionKmlTecno.substring(0, len-1);
        respuesta = jsonMessage(code,msg,creacionKmlTecno);
        return respuesta;
    }
        
    private String parseKml(File kml_file,String pathKml, String FileName, String FileExt, ArrayList<String> sectoresNoSaturados) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(new Class[]{Kml.class}).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper()
            {
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
                {
                    return namespaceUri.matches("http://www.w3.org/\\d{4}/Atom") ? "atom"
                            : (
                            namespaceUri.matches("urn:oasis:names:tc:ciq:xsdschema:xAL:.*?") ? "xal"
                                    : (
                                    namespaceUri.matches("http://www.google.com/kml/ext/.*?") ? "gx"
                                            : (
                                            namespaceUri.matches("http://www.opengis.net/kml/.*?") ? ""
                                                    : (
                                                    null
                                                    )
                                            )
                                    )
                            );
                }
            });

           //Lectura de KML
            Kml kml = Kml.unmarshal(kml_file);
            Feature feature = kml.getFeature();
            Document documentCoberturas = parseFeature(feature, sectoresNoSaturados, FileName);
            
            //Creación de nuevo KML
            Kml kmlCobertura = new Kml();
            StringWriter sw = new StringWriter();
            kmlCobertura.setFeature(documentCoberturas);
            marshaller.marshal(kmlCobertura, new File(pathKml+FileName+"_COBERTURA"+FileExt)); 
            return "SUCCESS";
        }catch(Exception ex){
            System.out.println("Error parseKml: "+ex.toString());
            ex.printStackTrace();
            return "ERROR "+ex.toString();
        }
    }
    
    private Document parseFeature(Feature feature, ArrayList<String> sectoresNoSaturados, String FileName) {
        Document documentCoberturas= new Document();
        if(feature != null) {
            //################# COBERTURA
            Style cobertura = new Style();
            PolyStyle polyStyleC= new PolyStyle();
            //Estilo de poligono
            polyStyleC.setColor("7F660066");
            polyStyleC.setFill(true);
            polyStyleC.setFill(true);
            polyStyleC.setOutline(false);
            cobertura.setPolyStyle(polyStyleC);
            
            //Estilo de linea
            LineStyle lineStyleC=new LineStyle();
            lineStyleC.setColor("7F660066");
            lineStyleC.setWidth(1.0);
            cobertura.setLineStyle(lineStyleC);
            
            //List Style Selector
            List<StyleSelector> coberturaList =Arrays.asList(cobertura);
            
            //################# SIN COBERTURA
            Style sinCobertura = new Style();
            PolyStyle polyStyleSC= new PolyStyle();
            //Estilo de poligono
            polyStyleSC.setColor("7F660066");
            polyStyleSC.setFill(true);
            polyStyleSC.setFill(true);
            polyStyleSC.setOutline(false);
            sinCobertura.setPolyStyle(polyStyleSC);
            
            //Estilo de linea
            LineStyle lineStyleSC=new LineStyle();
            lineStyleSC.setColor("7F660066");
            lineStyleSC.setWidth(1.0);
            sinCobertura.setLineStyle(lineStyleSC);
            
            //List Style Selector
            List<StyleSelector> sinCoberturaList =Arrays.asList(sinCobertura);
            
            //Se inicializa analisis de KML
            if(feature instanceof Document) {
                Document document = (Document) feature;
                documentCoberturas = new Document();
                 for (int j=0; j< document.getFeature().size(); j++){
                    Folder folder = (Folder) document.getFeature().get(j);
                    for (int i = 0; i < folder.getFeature().size(); i++) {
                        if(folder.getFeature().get(i) instanceof Placemark) {
                            Placemark placemark = (Placemark) folder.getFeature().get(i);
                            String coberturasFijas=base.getParam("LOGIC_READ_KMLS_RED_FIJA",Integer.parseInt(codigoPais));
                            if(sectoresNoSaturados.contains(quitCDATA(placemark.getName()))){
                                folder.getFeature().get(i).setStyleSelector(coberturaList);
                                documentCoberturas.addToFeature(folder.getFeature().get(i));
                            }else if(coberturasFijas.contains(FileName)){
                                folder.getFeature().get(i).setStyleSelector(coberturaList);
                                documentCoberturas.addToFeature(folder.getFeature().get(i));
                            }
                        }
                    }
                }
            }else if(feature instanceof Folder) {
                Folder folder = (Folder) feature;
                documentCoberturas = new Document();
                for (int i = 0; i < folder.getFeature().size(); i++) {
                        if(folder.getFeature().get(i) instanceof Placemark) {
                            Placemark placemark = (Placemark) folder.getFeature().get(i);
                            String coberturasFijas=base.getParam("LOGIC_READ_KMLS_RED_FIJA",Integer.parseInt(codigoPais));
                            if(sectoresNoSaturados.contains(quitCDATA(placemark.getName()))){
                                folder.getFeature().get(i).setStyleSelector(coberturaList);
                                documentCoberturas.addToFeature(folder.getFeature().get(i));
                            }else if(coberturasFijas.contains(FileName)){
                                folder.getFeature().get(i).setStyleSelector(coberturaList);
                                documentCoberturas.addToFeature(folder.getFeature().get(i));
                            }
                        }
                    }
            }
        }
        return documentCoberturas;
    }
    
    public String regLogConsulta(String P_USER, String P_PAIS, String P_NO_INTERACTION, String P_REQUEST, String P_RESPONSE, String P_COUNTRY_ID, String P_CLIENTE, double P_TIPO_SERVICIO, String P_DIR,
                                    double P_LAT, double P_LNG, double P_STATE_ID, String P_DESC_POLIGONO, String P_TECNOLOGIA) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        String respuesta = "-1";

        conn = this.base.getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT FN_IFI_REG_LOG (?,?,?,?,?,?,?,?,?,?,?,?,?,?) RESP FROM DUAL";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_USER);
            pstmt.setString(2, P_PAIS);
            pstmt.setString(3, P_NO_INTERACTION);
            pstmt.setString(4, P_REQUEST);
            pstmt.setString(5, P_RESPONSE);
            pstmt.setString(6, P_COUNTRY_ID);
            pstmt.setString(7, P_CLIENTE);
            pstmt.setDouble(8, P_TIPO_SERVICIO);
            pstmt.setString(9, P_DIR);
            pstmt.setDouble(10, P_LAT);
            pstmt.setDouble(11, P_LNG);
            pstmt.setDouble(12, P_STATE_ID);
            pstmt.setString(13, P_DESC_POLIGONO);
            pstmt.setString(14, P_TECNOLOGIA);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            if (rs.next()) {
                respuesta = rs.getString("RESP");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManchaCoberturaModel.class.getName()).log(Level.SEVERE, null,"REGISTRAR LOG IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(ManchaCoberturaModel.class.getName()).log(Level.SEVERE, null,"REGISTRAR LOG IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public ArrayList<String> getIngSectorsSaturados(String P_TECNO, String P_PAIS) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        ArrayList<String> respuesta = new ArrayList<String>();

        conn = this.base.getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT DISTINCT\n" +
                    "ING_SECTOR\n" +
                    "FROM TB_IFI_SITE_GEODATA_DIST_SEC\n" +
                    "WHERE TECNOLOGIA=? AND COUNTRY_ID=?\n" +
                    "AND ID_STATE_CELL=0";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_TECNO);
            pstmt.setString(2, P_PAIS);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            while (rs.next()) {
                respuesta.add(rs.getString("ING_SECTOR"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManchaCoberturaModel.class.getName()).log(Level.SEVERE, null,"getIngSectorsSaturados IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(ManchaCoberturaModel.class.getName()).log(Level.SEVERE, null,"getIngSectorsSaturados IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public ArrayList<String> getIngSectorsNoSaturados(String P_TECNO, String P_PAIS) {

        /* 
            Metodo utilizado para consultar las cargas segun estado dado 
         */
        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query;

        ArrayList<String> respuesta = new ArrayList<String>();

        conn = this.base.getConnection();

        try {
            //Obtener todas las cargas propuestas
            query = "SELECT DISTINCT\n" +
                    "ING_SECTOR\n" +
                    "FROM TB_IFI_SITE_GEODATA_DIST_SEC\n" +
                    "WHERE TECNOLOGIA=? AND COUNTRY_ID=?\n" +
                    "AND ID_STATE_CELL=1";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, P_TECNO);
            pstmt.setString(2, P_PAIS);
            rs = pstmt.executeQuery();

            //Agregar los resultados a listado de cargas
            while (rs.next()) {
                respuesta.add(rs.getString("ING_SECTOR"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManchaCoberturaModel.class.getName()).log(Level.SEVERE, null,"getIngSectorsNoSaturados IFI -- error consulta base: " + ex.getMessage());
            try {
                conn.close();
            } catch (SQLException e1) {
                Logger.getLogger(ManchaCoberturaModel.class.getName()).log(Level.SEVERE, null,"getIngSectorsNoSaturados IFI -- Error al cerrar conexion a la base de datos" + e1.getMessage());
            }
        }finally{
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null,ERROR_CLOSE_BD + e.getMessage());
            }
        }

        return respuesta;
    }
    
    public String jsonMessage(String code, String msg,String KMLs){
        return "{\"code\":\""+code+"\",\"mensaje\":\""+msg+"\", \"KMLS\":["+KMLs+"]}";
    }
    
    public String quitCDATA(String etiqueta){
        etiqueta = (etiqueta==null)?"":etiqueta;
        return etiqueta.replace("!\\[CDATA\\[", "").replace("\\]\\]>", "");
    }
}
