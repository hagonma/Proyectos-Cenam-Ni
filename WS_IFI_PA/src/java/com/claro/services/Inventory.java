/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import com.claro.controller.InventoryControler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
@Path("inventory")
public class Inventory {

    @Context
    private UriInfo context;
    private InventoryControler controlador;

    /**
     * Creates a new instance of SearchingResource
     */
    public Inventory() {
        this.controlador = new InventoryControler();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @Path("changeStatusPort")
    public Response changeStatusPort(String cadenaJson) throws UnsupportedEncodingException, IOException {
        //System.out.println(cadenaJson);
        String respuesta = this.controlador.changeStatusPort(cadenaJson);
        return Response.ok(respuesta).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();

    }
}
