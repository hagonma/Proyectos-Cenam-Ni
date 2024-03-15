/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import com.claro.beans.KmlFiles;
import com.claro.data.DbModel;
import com.claro.data.DecryptCryptoJs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

import org.apache.log4j.Logger;
import org.apache.commons.io.input.BOMInputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * REST Web Service
 *
 * @author Diego Lopez
 */
@Path("files")
public class Files {
    
    private DbModel base;
    
    static Logger logger = Logger.getLogger(Files.class);
    private String codigoPais="503";
    private String codLog="MTTO-KML-FILES";
    private String errLoadFile="Error en guardar el archivo: ";
    private String jsonErrLoadFile="{\"code\":-1,\"msg\":\"Error en guardar el archivo. Comunicarse con el administrador.\"}";
    private String SAVE_FOLDER;

    public Files() {
        this.base = new DbModel();
    }
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFile(
        @FormDataParam("file") InputStream fileInputString,
        @FormDataParam("file") FormDataContentDisposition fileInputDetails,
        @FormDataParam("tecno") String tecnologia,
        @FormDataParam("user") String pUser) {
      
      String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codigoPais));
      String resultado ="";
      
      //Se determina si el usuario tiene acceso a actualizar archivos
      String accessModifyKml=base.getValidateAccess("{\"user\":\""+pUser+"\", \"page\":\"MTTO-KMLS\"}");
      
      String P_USUARIO=DecryptCryptoJs.decrypt(pUser);
      this.base.regLogApp("Usuario: "+P_USUARIO+ " carga archivo: "+fileInputDetails.getFileName(), codLog, codigoPais);
      
      if (accessModifyKml.contains("\"code\":\"0\"")){
        //Se verifica que se envie el nombre de las tecnologias requeridas
        String KML_FILES=base.getParam("KML_FILES",Integer.parseInt(codigoPais));
        String[] files=KML_FILES.split("\\|");
        boolean nomCorrect=false;

               
        //Se verifica que tenga la extensión correcta
        if (fileInputDetails.getFileName().toUpperCase().contains(EXT_FILE.toUpperCase()))
          {
            
            for (String file: files){
            if(tecnologia.equals(file)){
                    nomCorrect=true;
                    break;
                }
            }
              
            if (nomCorrect){
            //Mover archivo a carpeta de BACKUP
            try{
              SAVE_FOLDER=base.getParam("PATH_BACKUP_KML",Integer.parseInt(codigoPais));
              String fileBackupLocation = SAVE_FOLDER + tecnologia.trim()+EXT_FILE;

              SAVE_FOLDER=base.getPathKml(Integer.parseInt(codigoPais));
              String fileLocation = SAVE_FOLDER + tecnologia.trim()+EXT_FILE;

              Process p = Runtime.getRuntime().exec("mv "+fileLocation+" "+fileBackupLocation);
              this.base.regLogApp("Se ejecuta comando: "+"mv "+fileLocation+" "+fileBackupLocation, codLog, codigoPais);
              p.waitFor();
            }catch (Exception ex){
                logger.error("Error "  + ex.getMessage());
                return "{\"code\":-2,\"msg\":\"Error en trasladar archivo antigua a backup. Comunicarse con el administrador.\"}";
            }

            try{
                //Subir archivo nuevo
                resultado=upload(
                                  fileInputString,
                                  fileInputDetails,
                                  tecnologia);

                //Actualizar variables de memoria
                  KmlFiles f =new KmlFiles();
                  ApplicationConfig.Kmls=f.getKmls();
            }catch (Exception ex){
                this.base.regLogApp("Error en cargar archivo: "+ex.getMessage(), "MTTO-KML-FILES", codigoPais);
                return "{\"code\":-3,\"msg\":\"Error en cargar archivo. Comunicarse con el administrador.\"}";
            }
          } else{
             return "{\"code\":-4,\"msg\":\"El nombre del archivo no es permitido\"}";
          }
        }else{
             return "{\"code\":-5,\"msg\":\"El archivo no posee la extensión "+EXT_FILE+"\"}";
        }
      }else{
          return "{\"code\":-6,\"msg\":\"Acceso denegado\"}";
      }
      
      return resultado;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("validKmls")
    public String validKmls() {
        String KML_FILES=base.getParam("KML_FILES",Integer.parseInt(codigoPais));
        String[] files=KML_FILES.split("\\|");
        String KMLs="[";

        for (String file: files){
            KMLs+="{\"value\":\""+file+"\"},";
        }
        
        KMLs=KMLs.substring(0, KMLs.length()-1)+"]";
         
        return KMLs;
    }
    
    public String upload(
        InputStream fileInputStringBom,
        FormDataContentDisposition fileInputDetails,
        String tecnologia){
        
      String resultado = "{\"code\":0,\"msg\":\"El Archivo no fue cargado.\"}";
      
      SAVE_FOLDER=base.getPathKml(Integer.parseInt(codigoPais));
      
      String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codigoPais));
      //SAVE_FOLDER="c://temp//";
      
      String fileLocation = SAVE_FOLDER + tecnologia.trim()+EXT_FILE;
      String status = null;
      NumberFormat myFormat = NumberFormat.getInstance();
      myFormat.setGroupingUsed(true);
      
      OutputStream out = null;
      // Save the file 
      try {
        BOMInputStream fileInputString = new BOMInputStream(fileInputStringBom);
        if (fileInputString.hasBOM ()) {
            // tiene una lista de materiales UTF-8
            this.base.regLogApp("El archivo "+tecnologia.trim()+EXT_FILE+ " posee una codificación UTF-8 BOM, se quita BOM para leer correctamente el KML.", codLog, codigoPais);
        }
        
       File file = new File(fileLocation);
       
       out = new FileOutputStream(file);
       byte[] buffer = new byte[1024];
       int bytes = 0;
       long file_size = 0; 
       while ((bytes = fileInputString.read(buffer)) != -1) {
        out.write(buffer, 0, bytes);
        file_size += bytes;
       }
              
       Process p =Runtime.getRuntime().exec("chmod 777 "+fileLocation);
       this.base.regLogApp("Se ejecuta comando: "+"chmod 777 "+fileLocation, codLog, codigoPais);
       p.waitFor();
       
       logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s", 
            fileInputDetails.getFileName(), myFormat.format(file_size)));

       status = "El archivo ha sido subido a:" + fileLocation 
                   + ", tamaño: " + myFormat.format(file_size) + " bytes";
       
       resultado = "{\"code\":1,\"msg\":\"El Archivo fue cargado exitosamente.\"}";
       this.base.regLogApp(status, codLog, codigoPais);
      } catch (IOException ex) {
        this.base.regLogApp(errLoadFile+ex.getMessage(), codLog, codigoPais);
        logger.error(errLoadFile  + fileLocation);
        ex.printStackTrace();
        resultado = jsonErrLoadFile;
      } catch (InterruptedException ex) {
        this.base.regLogApp(errLoadFile+ex.getMessage(), codLog, codigoPais);
        logger.error(errLoadFile  + fileLocation);
        ex.printStackTrace();
        resultado = jsonErrLoadFile;
      }finally{
        try{
            out.flush();  
            out.close();
        } catch (IOException ex) {
            this.base.regLogApp(errLoadFile+ex.getMessage(), codLog, codigoPais);
            logger.error(errLoadFile  + fileLocation);
            ex.printStackTrace();
            resultado = jsonErrLoadFile;
        }
      }
      
      return resultado;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("lastUpdatedFiles/{userId}")
    public String lastUpdatedFiles(@PathParam("userId") String pUser) {
        //Se determina si el usuario tiene acceso a actualizar archivos
        //String accessModifyKml=base.getValidateAccess("{\"user\":\""+pUser+"\", \"page\":\"MTTO-KMLS\"}");
        String accessModifyKml="\"code\":\"0\"";
        if (accessModifyKml.contains("\"code\":\"0\"")){
            return base.getlastUpdatedFiles(codigoPais);
        }else{
            return "{\"code\":-6,\"msg\":\"Acceso denegado\"}";
        }
    }
    
    @GET
    @Path("/download/{file}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFileWithGet(@PathParam("file") String file) {
        String pathKml=base.getPathKml(Integer.parseInt(codigoPais));
        String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codigoPais));
        File fileDownload = new File(pathKml + file+EXT_FILE);
        ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + file+EXT_FILE);
        return response.build();
    }
    
     @GET
    @Path("/downloadBK/{file}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadBKFileWithGet(@PathParam("file") String file) {
        String pathKml=base.getParam("PATH_BACKUP_KML",Integer.parseInt(codigoPais));
        String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codigoPais));
        File fileDownload = new File(pathKml + file+EXT_FILE);
        ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=" + file+EXT_FILE);
        return response.build();
    }
}
