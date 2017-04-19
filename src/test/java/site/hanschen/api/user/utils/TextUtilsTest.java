package site.hanschen.api.user.utils;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * @author HansChen
 */
public class TextUtilsTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void isEmailValid() throws Exception {
        assertTrue(TextUtils.isEmailValid("123456789@qq.com"));
        assertTrue(TextUtils.isEmailValid("shensky711@gmail.com"));
        assertTrue(!TextUtils.isEmailValid("12345@6789@qq.com"));
    }

    @Test
    public void isEmpty() throws Exception {
    }

}