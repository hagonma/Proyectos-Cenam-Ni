/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import com.claro.controller.HomeZoneControler;
import java.io.IOException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Marvin Urias
 */
@Path("homeZone")
public class HomeZone {

    @Context
    private UriInfo context;
    private HomeZoneControler controlador;

    /**
     * Creates a new instance of SearchingResource
     */
    public HomeZone() {
        this.controlador = new HomeZoneControler();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @Path("consultarCeldas")
    public Response consultarCeldas(String cadenaJson) throws IOException {
        System.out.println(cadenaJson);
        String respuesta = this.controlador.consultarCeldas(cadenaJson);
        return Response.ok(respuesta).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();

    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @Path("cambioCliente")
    public Response cambioCliente(String cadenaJson) throws IOException {
        System.out.println(cadenaJson);
        String respuesta = this.controlador.cambioCliente(cadenaJson);
        return Response.ok(respuesta).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();

    }
}
