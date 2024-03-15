/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import com.claro.data.DbModelMunicipalTax;
import javax.ws.rs.PathParam;


/**
 * REST Web Service
 *
 * @author hamilton.gonzalez
 */

@Path("municipalTaxes")
public class MunicipalTaxService {

    @Context
    private UriInfo context;
    

    public MunicipalTaxService() {
        //Constructor vacio
    }
    
    
    
    
    
    
    //**************************************************
    //departamentos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("departamentos/{codPais}")
    public String getDepartamento (@PathParam("codPais") Integer pais) {   
        DbModelMunicipalTax dbManager = new DbModelMunicipalTax();
        String departamento = dbManager.getDepartamento(pais);       
        return departamento;
    }
    
    
      
    
    
    
    //**************************************************
    //municipios
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("municipios/{codDepto}")
    public String getMunicipio (@PathParam("codDepto") Integer depto) {   
        DbModelMunicipalTax dbManager = new DbModelMunicipalTax();
        String municipio = dbManager.getMunicipio(depto);
        return municipio;
    }
    
        
    
    
    //**************************************************
    //permisos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("PermiMunicipales/{codMunic}")
    public String getPermisosMunicipales (@PathParam("codMunic") Integer munic) {   
        DbModelMunicipalTax dbManager = new DbModelMunicipalTax();
        String permiso = dbManager.getPermisosMunicipales(munic);
        return permiso;
    }

    
    
    

}
