package Utils;

import org.w3c.dom.Document;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Properties;

public class Mail {

    private static String to;
    private static String from;
    private static Session session;

    public static void configurar() {
        File xml = new File("src/main/resources/Configuracion.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);

            from = doc.getElementsByTagName("EmailFrom").item(0).getTextContent();
            to = doc.getElementsByTagName("EmailEmergencia").item(0).getTextContent();
            String username = doc.getElementsByTagName("EmailUsuario").item(0).getTextContent();
            String password = doc.getElementsByTagName("EmailContrasena").item(0).getTextContent();
            String host = doc.getElementsByTagName("EmailHost").item(0).getTextContent();


            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "2525");

            session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }});


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void enviarEmail(String titulo, String cuerpo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(titulo);
            message.setText(cuerpo);

            Transport.send(message);
            System.out.println("Mensaje enviado...");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
