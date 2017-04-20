package site.hanschen.api.user.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * @author HansChen
 */
public class MultipartBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MultipartBuilder.class);

    static {

    }

    public static MultipartBuilder newBuilder() {
        return new MultipartBuilder();
    }

    private BodyPart textBody;
    private BodyPart attachment;

    private MultipartBuilder() {
    }

    public MultipartBuilder setText(String text) {
        try {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);
            textBody = messageBodyPart;
        } catch (MessagingException ignored) {
            logger.error("setText failed: ", ignored);
        }
        return this;
    }

    public MultipartBuilder setAttachment(String filePath, String fileName) {

        try {
            BodyPart bodyPart = new MimeBodyPart();
            javax.activation.DataSource source = new FileDataSource(filePath);
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(fileName);
            attachment = bodyPart;
        } catch (MessagingException ignored) {
            logger.error("setAttachment failed: ", ignored);
        }
        return this;
    }

    public Multipart build() {
        MimeMultipart multipart = new MimeMultipart();
        try {
            if (textBody != null) {
                multipart.addBodyPart(textBody);
            }
            if (attachment != null) {
                multipart.addBodyPart(attachment);
            }
        } catch (MessagingException ignored) {
            logger.error("build multipart failed: ", ignored);
        }
        return multipart;
    }
}
