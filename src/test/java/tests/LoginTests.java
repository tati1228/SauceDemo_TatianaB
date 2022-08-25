package tests;

import helpers.FileFormat;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataParser;

public class LoginTests extends BaseTest {

    @Test(groups = {"Smoke", "Regression"})
    public void positiveLoginTest() {
        loginPage.login(USERNAME, PASSWORD);
        Assert.assertTrue("Inventory page should be opened.", productsPage.isHeaderContainerDisplayed());
    }

    @DataProvider
    public Object[][] jsonLoginDataProvider() {
        TestDataParser parser = new TestDataParser(FileFormat.JSON);
        return parser.readLinearStructure("test-data/login-data.json");
    }

    @Test(dataProvider = "jsonLoginDataProvider", groups = {"Negative", "Regression"})
    public void negativeLoginTest(String userName, String password, String errorMessage) {
        loginPage.login(userName, password);
        Assert.assertTrue("Error message should be displayed.", loginPage.isErrorMessageContainerDisplayed());
        Assert.assertEquals("Message should be: "+errorMessage, loginPage.getErrorMessageText(), errorMessage);
    }
}
