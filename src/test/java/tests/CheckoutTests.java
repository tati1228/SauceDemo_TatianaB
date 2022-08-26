package tests;

import helpers.FileFormat;
import org.testng.Assert;
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
        Assert.assertEquals(checkoutCompletePage.getHeaderText(), THANK_YOU_MESSAGE, "There should be " + THANK_YOU_MESSAGE + " message.");
        checkoutCompletePage.clickBackHomeButton();
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "The inventory page should be opened.");
    }

    @Test(groups = {"Regression"})
    public void positiveCheckoutContinueShoppingTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.clickAddToCartButton("Sauce Labs Bike Light");
        productsPage.clickShoppingCartLink();
        Assert.assertTrue(cartPage.isYourCartTitleDisplayed(), "'Your cart' page title should be displayed.");
        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "The inventory page should be opened.");
    }

    @Test(groups = {"Regression"})
    public void positiveCheckoutAfterCancelTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.clickAddToCartButton("Sauce Labs Bike Light");
        productsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.clickCancelButton();
        Assert.assertTrue(cartPage.isYourCartTitleDisplayed(), "'Your cart' page title should be displayed.");
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutStepOnePage.clickContinueButton();
        checkoutStepTwoPage.clickCancelButton();
        productsPage.clickAddToCartButton("Sauce Labs Bolt T-Shirt");
        productsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutStepOnePage.clickContinueButton();
        checkoutStepTwoPage.clickFinishButton();
        Assert.assertEquals(checkoutCompletePage.getHeaderText(), THANK_YOU_MESSAGE, "There should be " + THANK_YOU_MESSAGE + " message.");
        checkoutCompletePage.clickBackHomeButton();
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "The inventory page should be opened.");
    }

    @Test(groups = {"Smoke", "Negative"})
    public void negativeCheckoutEmptyFieldsTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.clickAddToCartButton("Sauce Labs Bike Light");
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
