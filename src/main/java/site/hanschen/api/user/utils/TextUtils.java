package site.hanschen.api.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HansChen
 */
public class TextUtils {

    private TextUtils() {
    }

    public static boolean isEmailValid(String email) {
        if (isEmpty(email)) {
            return false;
        }
        String regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }
}
