/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.services;

import com.claro.controller.ManchaCoberturaControler;
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
@Path("mancha-cobertura-kml")
public class ManchaCoberturaResource {

    @Context
    private UriInfo context;
    private ManchaCoberturaControler controlador;

    /**
     * Creates a new instance of SearchingResource
     */
    public ManchaCoberturaResource() {
        this.controlador = new ManchaCoberturaControler();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @Path("createKmlsManchaCobertura")
    public Response crearKmlsManchasCobertura(String cadenaJson) throws UnsupportedEncodingException, IOException {
        //System.out.println(cadenaJson);
        String respuesta = this.controlador.crearKmlsManchasCobertura(cadenaJson);
        return Response.ok(respuesta).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();

    }
}
