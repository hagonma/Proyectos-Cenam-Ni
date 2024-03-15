 package Controller;
 
 import Controller.Helper;
 import Model.LDAP;
 import Model.Usuario;
 import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
 import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
 import javax.naming.directory.InitialDirContext;
 import javax.ws.rs.Consumes;
 import javax.ws.rs.GET;
 import javax.ws.rs.POST;
 import javax.ws.rs.PUT;
 import javax.ws.rs.Path;
 import javax.ws.rs.Produces;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 import org.jsoup.Jsoup;
 import org.jsoup.safety.Whitelist;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @Path("LDAP")
 public class LDAPResource
 {
   private String ldapProvider;
/*  38 */   private String apiToken = "436C61726F4B696F73636F";
/*  39 */   private static String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
/*  40 */   private String rol = "";
/*  41 */   private String token = "";
/*  42 */   private String idUser = "";
 
 
 
   
   public LDAPResource() {
/*  48 */     this.ldapProvider = "172.16.168.102,172.24.2.180";
   }
 
   
   @PostConstruct
   public void init() {
/*  54 */     this.ldapProvider = "172.16.168.102,172.24.2.180";
   }
 
 
 
 
 
 
   
   @GET
   @Produces({"application/json"})
   public String getJson() {
/*  66 */     throw new UnsupportedOperationException();
   }
 
 
 
 
 
   
   @POST
   @Consumes({"application/json"})
   public String postJson(String content) throws JSONException, Exception {
/*  77 */     JSONObject params = new JSONObject(content);
/*  78 */     LDAP ldap = new LDAP();
/*  79 */     Helper hp = new Helper();
/*  80 */     JSONObject obj = new JSONObject();
/*  81 */     String domain = ldap.getDominio(params.getInt("codigoPais"));
/*  82 */     String dns = ldap.getDns(params.getInt("codigoPais"));
/*  83 */     String user = Jsoup.clean(params.getString("user"), Whitelist.basic());
/*  84 */     String pass = Jsoup.clean(params.getString("pass"), Whitelist.basic());
     
/*  86 */     Usuario userObj = new Usuario();
/*  87 */     userObj.getUser(user, params.getInt("codigoPais"));
/*  88 */     JSONObject jobjUsr = new JSONObject(userObj.hp.getResponse());
/*  89 */     JSONArray jarrUsr = jobjUsr.getJSONArray("Response");
     
/*  91 */     if (jarrUsr.length() > 0) {
/*  92 */       JSONObject jobjRowUsr = jarrUsr.getJSONObject(0);
/*  93 */       jobjRowUsr.getString("ID");
       
/*  95 */       this.idUser = jobjRowUsr.getString("ID");
/*  96 */       userObj.setToken(Integer.parseInt(this.idUser));
/*  97 */       this.token = userObj.getToken(Integer.parseInt(this.idUser));
       
/*  99 */       this.rol = jobjRowUsr.getString("ROL_ID");
/* 100 */       obj.put("Usuario", "Existe");
/* 101 */       obj.put("Rol", this.rol);
/* 102 */       obj.put("Usuario", "Existe");
     } else {
/* 104 */       obj.put("Usuario", "No existe");
     } 
     
/* 107 */     Hashtable<String, String> env = new Hashtable<>();
     try {
/* 109 */       if (pass == null || user == null) {
/* 110 */         obj.put("Campo Usuario", "Vacio");
/* 111 */         hp.setResponse(400, obj);
                }
/* 113 */       else if (pass.compareTo("") == 0 || user.compareTo("") == 0) {
/* 114 */         obj.put("Campo Usuario", "Valor 0 Invalido");
/* 115 */         hp.setResponse(400, obj);
                } else if (pass != null || user != null) {
         /* 117 */         obj.put("Acceso", "Autorizado");
         /* 118 */         obj.put("apiToken", this.token);
         /* 119 */         hp.setResponse(100, obj);
         /* 120 */         obj.put("Rol", this.rol);
         /* 121 */         obj.put("ID", this.idUser);
                } else {
        
    /* 125 */       //env.put("java.naming.factory.initial", INITCTX);
    /* 126 */       //env.put("java.naming.provider.url", "ldap://" + dns+ ":" + 389);
    /* 127 */       //env.put("java.naming.security.authentication", "simple");
    /* 128 */       //env.put("java.naming.security.principal", new String(domain + "\\" + user));
    /* 129 */       //env.put("java.naming.security.credentials", new String(pass));

    /* 131 */       //new InitialDirContext(env);

                    String URL_LDAP = "ldap://" + dns + ":" + 389;
                    String DOMAIN = "@" + domain;
                    Hashtable<String, String> environment = new Hashtable<String, String>();
                    DirContext dirContext;
                    boolean connected = false;
                    environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                    environment.put(Context.PROVIDER_URL, URL_LDAP);
                    environment.put(Context.SECURITY_AUTHENTICATION, "simple");
                    environment.put(Context.SECURITY_PRINCIPAL, user + DOMAIN);
                    environment.put(Context.SECURITY_CREDENTIALS, pass);

                    dirContext = new InitialDirContext(environment);                

                    System.out.println("dirContext => " + dirContext.getEnvironment());
                }
            }
/* 133 */     catch (Exception e) {
                System.out.println("Error al conectar al LDAP =>" + e.getLocalizedMessage());
/* 134 */       obj.put("Acceso", e.getLocalizedMessage()/*"No Autorizado"*/);
/* 135 */       hp.setResponse(401, obj);
     } finally {
/* 137 */       env = null;
     } 
/* 139 */     return hp.getResponse();
   }
 
 
 
   
   @PUT
   @Consumes({"application/json"})
   public void putJson(String content) {}
 
 
 
   
   public boolean simulated() {
/* 153 */     return true;
   }
   
   public String getLdapProvider() {
/* 157 */     return this.ldapProvider;
   }
   
   public void setLdapProvider(String ldapProvider) {
/* 161 */     this.ldapProvider = ldapProvider;
   }
 }


/* Location:              C:\tmp\cdn\CDN.war\app\CDN.war!\WEB-INF\classes\Controller\LDAPResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */