package Utils;

import org.testng.asserts.SoftAssert;
public class SoftAssertionUtil {
    private static SoftAssertionUtil instance;
    private SoftAssert softAssert;

    private SoftAssertionUtil() {
        softAssert = new SoftAssert();
    }

    public static synchronized SoftAssertionUtil getInstance() {
        if (instance == null) {
            instance = new SoftAssertionUtil();
        }
        return instance;
    }

    public void assertEquals(Object actual, Object expected, String message) {
        softAssert.assertEquals(actual, expected, message);
    }

    public void assertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
    }

    public void assertFalse(boolean condition, String message) {
        softAssert.assertFalse(condition, message);
    }

    public void assertAll() {
        softAssert.assertAll();
    }
}
