package site.hanschen.api.user.mail;

/**
 * @author HansChen
 */
public class MailSender163 extends MailSender {

    public MailSender163(String username, String password) {
        super(username, password, "smtp.163.com", "465");
    }
}
