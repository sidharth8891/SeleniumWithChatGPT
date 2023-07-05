package tests;

import Utils.SoftAssertionUtil;
import org.testng.annotations.Test;
public class SoftAssertionExampleTest {
    private SoftAssertionUtil softAssert = SoftAssertionUtil.getInstance();

    @Test
    public void exampleTest() {
        // Perform some test actions

        softAssert.assertEquals("actualValue", "expectedValue", "Assertion message");

        // Perform more test actions

        softAssert.assertAll();
    }
}
