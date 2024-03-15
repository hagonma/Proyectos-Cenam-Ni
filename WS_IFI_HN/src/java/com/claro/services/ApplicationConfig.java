/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import com.claro.beans.KmlFiles;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Marvin Urias
 */
@javax.ws.rs.ApplicationPath("services")
public class ApplicationConfig extends Application {
    
    public static List<File> Kmls;
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }


    public ApplicationConfig() throws FileNotFoundException, IOException {
        KmlFiles f =new KmlFiles();
        this.Kmls=f.getKmls();
    }    

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.claro.services.CheckResource.class);
        resources.add(com.claro.services.Files.class);
        resources.add(com.claro.services.HomeZone.class);
        resources.add(com.claro.services.ManchaCoberturaResource.class);
        resources.add(com.claro.services.MunicipalTaxService.class);
        resources.add(com.claro.services.SearchingResource.class);
        resources.add(com.claro.services.SearchingResourceKml.class);
    }
}
