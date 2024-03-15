/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.beans;

import com.claro.data.DbModel;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Lopez
 */
public class KmlFiles {
    
    protected static List<File> Kmls;
    protected File f;
    private DbModel base;
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(KmlFiles.class);
    
    public KmlFiles() {
        try{
            this.base = new DbModel();
            Kmls = new ArrayList<File>();
            String codigoPais="504";
            
            String pathKml=base.getPathKml(Integer.parseInt(codigoPais));
            String EXT_FILE=base.getParam("EXT_FILE",Integer.parseInt(codigoPais));
            String KML_FILES=base.getParam("KML_FILES",Integer.parseInt(codigoPais));
            String[] files=KML_FILES.split("\\|");
            
            /* Prueba locales*/
            /*
            String pathKml="C:/kml/HN/";
            String EXT_FILE=".KML";
            String KML_FILES="4G|3G";
            String[] files=KML_FILES.split("\\|");
            */
            for (String file: files){
                System.out.println("Path: "+pathKml+file+EXT_FILE);
                f=new File(pathKml+file+EXT_FILE);
                Kmls.add(f);
            }
            
            logger.info("Se cargaron los archivos de KML en memoria.");
            this.base.regLogApp("Se realiza la carga de archivos kml a memoria en "+pathKml, "KML-LOAD-FILES", codigoPais);
        }catch(Exception e){
            Logger.getLogger(DbModel.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public static List<File> getKmls() {
        return Kmls;
    }

}
