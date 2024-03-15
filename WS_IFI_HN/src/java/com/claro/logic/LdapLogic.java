/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.claro.logic;

import com.claro.data.DbModel;
import com.google.gson.Gson;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author Marvin Urias
 */
public class LdapLogic {

    public String validate(String user, String pass, int pais) {
        Gson gson = new Gson();
        // RECUPERAR INFORMACION DE LDAP
        DbModel dbManager = new DbModel();
        String dominio = dbManager.getDominioLdap(pais);
        String dns = dbManager.getDnsLdap(pais);

        //Configurar petición
        String URL_LDAP = "ldap://" + dns + ":" + 389;
        String DOMAIN = "@" + dominio;

        Hashtable<String, String> environment = new Hashtable<String, String>();
        DirContext dirContext;
        boolean connected = false;
        //System.out.println(URL_LDAP);
        //System.out.println(DOMAIN);
        //System.out.println(user);
        //System.out.println(pass);
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, URL_LDAP);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, user + DOMAIN);
        environment.put(Context.SECURITY_CREDENTIALS, pass);

        try {
            if (user.length() > 1 && pass.length() > 1) {
                if (dbManager.validarUsuario(user)) {
                    dirContext = new InitialDirContext(environment);
                    return "1";
                } else {
                    return "2";
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(LdapLogic.class.getName()).log(Level.SEVERE, null,ex.getMessage());
            String[] datos = ex.getMessage().split(",");
            String[] error = datos[2].trim().split(" ");
            return error[1];
        }
        return "0";
    }

    public boolean searchLdap(String user, String psw, String url, String port, String domainldap) {

        String URL_LDAP = "ldap://" + url + ":" + port;
        String DOMAIN = "@" + domainldap;

        Hashtable<String, String> environment = new Hashtable<String, String>();
        DirContext dirContext;
        boolean connected = false;
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, URL_LDAP);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, user + DOMAIN);
        environment.put(Context.SECURITY_CREDENTIALS, psw);
        try {
            if (user.length() > 1 && psw.length() > 1) {
                dirContext = new InitialDirContext(environment);
                //System.out.println("Connected..");
                //System.out.println(dirContext);
            }
        } catch (Exception ex) {
            Logger.getLogger(LdapLogic.class.getName()).log(Level.SEVERE, null,ex.getMessage());
            return false;
        }

        return true;

    }

    public String getMessage(String respuesta) {
        String mensaje = "No identificado";
        switch (respuesta) {
            case "525":
                mensaje = "El usuario no se encuentra";
                break;
            case "52e":
                mensaje = "Las credenciales del usuario no son válidas";
                break;
            case "530":
                mensaje = "El usuario no puede iniciar sesión en este momento.";
                break;
            case "531":
                mensaje = "El usuario no puede iniciar sesión en esta estación de trabajo.";
                break;
            case "532":
                mensaje = "La contraseña ha caducado.";
                break;
            case "533":
                mensaje = "Esta cuenta de usuario se ha desactivado.";
                break;
            case "701":
                mensaje = "Esta cuenta de usuario ha caducado";
                break;
            case "773":
                mensaje = "El usuario debe restablecer su contraseña.";
                break;
            case "775":
                mensaje = "La cuenta de usuario se ha bloqueado.";
                break;
            case "2":
                mensaje = "No tiene permiso para acceder a este sistema.";
                break;
        }
        return mensaje;
    }

}
