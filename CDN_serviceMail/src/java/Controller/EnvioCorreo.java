/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Parametro;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author AVP GUATEMALA
 */
public class EnvioCorreo {
    
    String host,from;

    public String getHost(int pais) throws Exception {
        Parametro param = new Parametro();
        param.getByName("HOST_MAIL", pais);
        JSONObject jobj = new JSONObject(param.hp.getResponse());
        JSONArray jarr  = jobj.getJSONArray("Response");
        JSONObject jobjRow = jarr.getJSONObject(0);
        return jobjRow.getString("VALOR");
    }   

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom(int pais) throws Exception {
        Parametro param = new Parametro();
        param.getByName("FROM_MAIL", pais);
        JSONObject jobj = new JSONObject(param.hp.getResponse());
        JSONArray jarr = jobj.getJSONArray("Response");
        JSONObject jobjRow = jarr.getJSONObject(0);
        return jobjRow.getString("VALOR");
    }   

    public void setFrom(String from) {
        this.from = from;
    }

    public static void send(String host,String from,String correoCliente, String htmlBody, Map<String, String> mapInlineImages) throws AddressException, MessagingException {

        String subject  = "Bienvenido a Claro";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);

        Session session = Session.getInstance(properties);
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(from));
        InternetAddress[] toAddresses = {new InternetAddress(correoCliente)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();

            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);

                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(imagePart);
            }
        }
        msg.setContent(multipart);
        Transport.send(msg);
    }

}
