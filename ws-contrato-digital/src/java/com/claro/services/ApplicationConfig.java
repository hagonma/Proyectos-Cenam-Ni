/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import java.io.File;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Marlon Rosalio
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

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.CatalogosResource.class);
    resources.add(ws.ContratosResource.class);
    resources.add(ws.FirmaResource.class);
    }
}
