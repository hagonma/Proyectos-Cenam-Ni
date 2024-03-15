/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.beans;

/**
 *
 * @author Diego Lopez
 */
public class UserLDAP {
    private String response;
    private String nombre_completo;
    private String mail;

    public UserLDAP(String response, String nombre_completo, String mail) {
        this.response = response;
        this.nombre_completo = nombre_completo;
        this.mail = mail;
    }
    
    public UserLDAP() {
        this.response = "";
        this.nombre_completo = "";
        this.mail = "";
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
}
