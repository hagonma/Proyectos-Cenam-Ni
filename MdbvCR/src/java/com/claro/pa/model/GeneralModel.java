/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.pa.model;

/**
 *
 * @author Claro
 */
public class GeneralModel {
    /*id_cliente = resultado.getString("CLIENTE");
                fecha_instalacion = resultado.getString("RenovacionContrato");
                contrato = resultado.getString("CONTRATO");
                tipoCliente = resultado.getString("TipoCliente");
*/    
    private String idCliente;
    private String renovacionContrato;
    private String contrato;
    private String tipoCliente;

    public String getCliente() {
        return idCliente;
    }

    public void setIdCliente(String cliente) {
        this.idCliente = cliente;
    }

    public String getRenovacionContrato() {
        return renovacionContrato;
    }

    public void setRenovacionContrato(String renovacionContrato) {
        this.renovacionContrato = renovacionContrato;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String TipoCliente) {
        this.tipoCliente = TipoCliente;
    }
    
    
}
