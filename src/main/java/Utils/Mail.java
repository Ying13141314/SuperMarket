package Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    private static final String to;
    private static final String from;
    private static final String username;
    private static final String password;
    private static final String host;
    private static Properties props;
    private static Session session;


    static {
        // Put recipient’s address
        to = "test@example.com";
        // Put sender’s address
        from = "from@example.com";
        username = "e2d31d7403ca6f";//username generated by Mailtrap
        password = "abb9420872c7c6";//password generated by Mailtrap
        // Paste host address from the SMTP settings tab in your Mailtrap Inbox
        host = "smtp.mailtrap.io";

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//it’s optional in Mailtrap
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");// use one of the options in the SMTP settings tab in your Mailtrap Inbox

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public static void enviarEmail(String titulo, String cuerpo) {
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(from));

            // Set To: header field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(titulo);

            // Put the content of your message
            message.setText(cuerpo);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
