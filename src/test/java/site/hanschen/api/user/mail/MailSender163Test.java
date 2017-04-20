package site.hanschen.api.user.mail;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author HansChen
 */
public class MailSender163Test {

    MailSender163 sender;

    @Test
    public void sendEmail() throws Exception {
        String username = "user_hanschen@163.com";
        String password = "12345678abc";
        sender = new MailSender163(username, password);

        MultipartBuilder builder = MultipartBuilder.newBuilder();
        builder.setText("UnitTest For MailSender 1");
        assertTrue(sender.send("648524018@qq.com", "UnitTest 1", builder.build()));

        builder = MultipartBuilder.newBuilder();
        builder.setText("UnitTest For MailSender 2");
        assertTrue(sender.send("648524018@qq.com", "UnitTest 2", builder.build()));

    }
}