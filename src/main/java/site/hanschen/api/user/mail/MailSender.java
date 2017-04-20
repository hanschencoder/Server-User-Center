package site.hanschen.api.user.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import site.hanschen.api.user.utils.TextUtils;

/**
 * @author HansChen
 */
public class MailSender extends Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private String     username;
    private String     password;
    private String     host;
    private String     port;
    private Properties props;

    public MailSender(String username, String password, String host, String port) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(host)) {
            throw new IllegalArgumentException("argument not right");
        }
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        init();
    }

    private void init() {

        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    public boolean send(String to, String subject, Multipart content) {
        if (!TextUtils.isEmailValid(to) || content == null) {
            return false;
        }

        try {
            Session session = Session.getInstance(props, this);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            InternetAddress addressTo = new InternetAddress(to);
            msg.setRecipient(Message.RecipientType.TO, addressTo);

            msg.setSentDate(new Date());
            msg.setSubject(subject);
            msg.setContent(content);

            Transport.send(msg);
            return true;

        } catch (Throwable t) {
            logger.error("send email failed, ", t);
            return false;
        }
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
