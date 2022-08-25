package tests;

import helpers.FileFormat;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.TestDataParser;

public class CheckoutTests extends BaseTest {

    @DataProvider
    public Object[][] jsonProductDataProvider() {
        TestDataParser parser = new TestDataParser(FileFormat.JSON);
        return parser.readLinearStructure("test-data/product-name.json");
    }

    @Test(dataProvider = "jsonProductDataProvider", groups = {"Smoke", "Regression"})
    public void positiveCheckoutTest(String productName) {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.openProductDetails(productName);
        detailsPage.clickAddToCartButton();
        detailsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutStepOnePage.clickContinueButton();
        checkoutStepTwoPage.clickFinishButton();
        Assert.assertEquals("There should be " + THANK_YOU_MESSAGE + " message.", THANK_YOU_MESSAGE, checkoutCompletePage.getHeaderText());
        checkoutCompletePage.clickBackHomeButton();
        Assert.assertTrue("The inventory page should be opened.", productsPage.isHeaderContainerDisplayed());
    }

    @Test(groups = {"Regression"})
    public void positiveCheckoutContinueShoppingTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.clickAddRemoveButton(2);
        productsPage.clickShoppingCartLink();
        Assert.assertTrue("'Your cart' page title should be displayed.", cartPage.isYourCartTitleDisplayed());
        cartPage.clickContinueShoppingButton();
        Assert.assertTrue("The inventory page should be opened.", productsPage.isHeaderContainerDisplayed());
    }

    @Test(groups = {"Regression"})
    public void positiveCheckoutAfterCancelTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.clickAddRemoveButton(2);
        productsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.clickCancelButton();
        Assert.assertTrue("'Your cart' page title should be displayed.", cartPage.isYourCartTitleDisplayed());
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutStepOnePage.clickContinueButton();
        checkoutStepTwoPage.clickCancelButton();
        productsPage.clickAddRemoveButton(3);
        productsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutStepOnePage.clickContinueButton();
        checkoutStepTwoPage.clickFinishButton();
        Assert.assertEquals("There should be " + THANK_YOU_MESSAGE + " message.", THANK_YOU_MESSAGE, checkoutCompletePage.getHeaderText());
        checkoutCompletePage.clickBackHomeButton();
        Assert.assertTrue("The inventory page should be opened.", productsPage.isHeaderContainerDisplayed());
    }

    @Test(groups = {"Smoke", "Negative"})
    public void negativeCheckoutEmptyFieldsTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.clickAddRemoveButton(2);
        productsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.clickContinueButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkoutStepOnePage.getErrorMessage(), "Error: First Name is required",
                "There should be an 'Error: First Name is required' message.");
        checkoutStepOnePage.setPersonalData(FIRST_NAME, "", "");
        checkoutStepOnePage.clickContinueButton();
        softAssert.assertEquals(checkoutStepOnePage.getErrorMessage(), "Error: Last Name is required",
                "There should be an 'Error: Last Name is required' message.");
        checkoutStepOnePage.setPersonalData("", LAST_NAME, "");
        checkoutStepOnePage.clickContinueButton();
        softAssert.assertEquals(checkoutStepOnePage.getErrorMessage(), "Error: Postal Code is required",
                "There should be an 'Error: Postal Code is required' message.");
        softAssert.assertAll();
    }
}
