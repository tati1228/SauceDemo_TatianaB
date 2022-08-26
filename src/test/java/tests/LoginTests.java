package tests;

import helpers.FileFormat;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataParser;

public class LoginTests extends BaseTest {

    @Test(groups = {"Smoke", "Regression"})
    public void positiveLoginTest() {
        loginPage.login(USERNAME, PASSWORD);
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "Inventory page should be opened.");
    }

    @DataProvider
    public Object[][] jsonLoginDataProvider() {
        TestDataParser parser = new TestDataParser(FileFormat.JSON);
        return parser.readLinearStructure("test-data/login-data.json");
    }

    @Test(dataProvider = "jsonLoginDataProvider", groups = {"Negative", "Regression"})
    public void negativeLoginTest(String userName, String password, String errorMessage) {
        loginPage.login(userName, password);
        Assert.assertTrue(loginPage.isErrorMessageContainerDisplayed(), "Error message should be displayed.");
        Assert.assertEquals(loginPage.getErrorMessageText(), errorMessage, "Message should be: "+errorMessage);
    }
}
