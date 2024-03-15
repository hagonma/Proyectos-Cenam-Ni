package com.claro.cr;

import com.claro.pa.controller.ConsultasOPEN;
import com.claro.pa.controller.Helper;
import com.claro.pa.controller.Select;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author AVP GUATEMALA
 */
@Path("service")
public class EnvioMailCr {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EnvioMailPanama
     */
    public EnvioMailCr() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("envioMail")
    public String envioMailBienvenidaFijoPA(@QueryParam("id") String id, @QueryParam("idpais") String idpais) {
        Helper help = new Helper();
        JSONObject job = new JSONObject();
        try {
            //TODO return proper representation object
            Select param  = new Select();
            if(idpais.equals("506") && id.equals("CARGA_DIARIA")){
                ConsultasOPEN conOPEN = new ConsultasOPEN(idpais);
                conOPEN.obtenerQuerys();
            }
            //Método que envía el Correo.
            if (!idpais.isEmpty() && !id.isEmpty() ) {
                String resultMail = param.selectFijo(id, idpais);
                job.put("Envio", resultMail);
                help.setResponse(200,job);
            }else{
                job.put("Envio", "Sin parametros enviados");
                help.setResponse(200,job);
            }
        } catch (JSONException ex) {
            try {
                job.put("Envio", ex.getLocalizedMessage() + " - " + ex.getStackTrace());
                help.setResponse(200,job);
                Logger.getLogger(EnvioMailCr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex1) {
                Logger.getLogger(EnvioMailCr.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            try {
                job.put("Envio", ex.toString());
                help.setResponse(200,job);
                Logger.getLogger(EnvioMailCr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex1) {
                Logger.getLogger(EnvioMailCr.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(EnvioMailCr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return help.getResponse();
    }

}