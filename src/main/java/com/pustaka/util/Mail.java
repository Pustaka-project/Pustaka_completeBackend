package com.pustaka.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {


public static String sendMessage(String email, String message, String subject)
{

    String mailSentResult = "";
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", MailConstants.MAIL_SMTP_HOST);
    properties.setProperty("mail.smtp.port", MailConstants.MAIL_SMTP_PORT);
    properties.setProperty("mail.smtp.auth", MailConstants.MAIL_SMTP_AUTH);
    properties.setProperty("mail.smtp.starttls.enable", MailConstants.MAIL_STARTTLS_ENABLE);
    Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator()
    {
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(MailConstants.MAIL_ID_FROM_ADDRESS,
                    MailConstants.MAIL_ID_FROM_PASSWORD);
        }
    });

    try
    {
        Message msg = new MimeMessage(session);
        msg.setContent(message, "text/html");
        msg.setFrom(new InternetAddress(MailConstants.MAIL_ID_FROM_ADDRESS));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        //message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCC));
        msg.setSubject(subject);
        Transport transport = session.getTransport(MailConstants.MAIL_SMTP_TRANSPORT);
        //System.out.println("Transport created");
        try
        {
            //log.debug("Sending message");
            transport.connect(MailConstants.MAIL_SMTP_HOST, Integer.parseInt(MailConstants.MAIL_SMTP_PORT),
                    MailConstants.MAIL_ID_FROM_ADDRESS, MailConstants.MAIL_ID_FROM_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            //log.debug("Mail sent successfully");
            mailSentResult = "success";
        } catch (Exception ex)
        {
            //log.error("Email not sent exception ." + ex);
            mailSentResult = "Email not sent exception ";
        } finally
        {
            transport.close();
            //log.debug("Close and terminate the connection.");
        }
    } catch (Exception e)
    {
        //log.error("error during mail " + e);
        mailSentResult = "error during email.";
    } finally
    {

    }
    return mailSentResult;
}
}
