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
        logger.logInfo(String.format("Login to SauceDemo with username '%s' and password '%s'", USERNAME, PASSWORD));
        loginPage.login(USERNAME, PASSWORD);
        logger.logInfo(String.format("Open the '%s' product page.", productName));
        productsPage.openProductDetails(productName);
        logger.logInfo(String.format("Add the '%s' product to the shopping cart.", productName));
        detailsPage.clickAddToCartButton();
        logger.logInfo("Open the shopping cart page.");
        detailsPage.clickShoppingCartLink();
        logger.logInfo("Go to checkout.");
        cartPage.clickCheckoutButton();
        logger.logInfo(String.format("Enter personal data: '%s', '%s', '%s'.", FIRST_NAME, LAST_NAME, ZIP_CODE));
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        logger.logInfo("Click Continue button.");
        checkoutStepOnePage.clickContinueButton();
        logger.logInfo("Finish checkout.");
        checkoutStepTwoPage.clickFinishButton();
        logger.logInfo(String.format("Check that there is a '%s' message on the page", THANK_YOU_MESSAGE));
        Assert.assertEquals(checkoutCompletePage.getHeaderText(), THANK_YOU_MESSAGE,
                "There should be " + THANK_YOU_MESSAGE + " message.");
        logger.logInfo("Click Back Home button");
        checkoutCompletePage.clickBackHomeButton();
        logger.logInfo("Check that the inventory page is opened.");
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "The inventory page should be opened.");
    }

    @Test(groups = {"Regression"})
    public void positiveCheckoutContinueShoppingTest() {
        logger.logInfo(String.format("Login to SauceDemo with username '%s' and password '%s'", USERNAME, PASSWORD));
        loginPage.login(USERNAME, PASSWORD);
        logger.logInfo("Add a product to the shopping cart");
        productsPage.clickAddToCartButton("Sauce Labs Bike Light");
        logger.logInfo("Open the shopping cart");
        productsPage.clickShoppingCartLink();
        logger.logInfo("Check that 'Your cart' page is opened.");
        Assert.assertTrue(cartPage.isYourCartTitleDisplayed(), "'Your cart' page title should be displayed.");
        logger.logInfo("Click the 'Continue shopping' button.");
        cartPage.clickContinueShoppingButton();
        logger.logInfo("Check that the inventory page is opened.");
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "The inventory page should be opened.");
    }

    @Test(groups = {"Regression"})
    public void positiveCheckoutAfterCancelTest() {
        logger.logInfo(String.format("Login to SauceDemo with username '%s' and password '%s'", USERNAME, PASSWORD));
        loginPage.login(USERNAME, PASSWORD);
        logger.logInfo("Add a product to the shopping cart.");
        productsPage.clickAddToCartButton("Sauce Labs Bike Light");
        logger.logInfo("Open the shopping cart.");
        productsPage.clickShoppingCartLink();
        logger.logInfo("Click 'Checkout' button.");
        cartPage.clickCheckoutButton();
        logger.logInfo("Cancel checkout.");
        checkoutStepOnePage.clickCancelButton();
        logger.logInfo("Check that the shopping cart page is opened.");
        Assert.assertTrue(cartPage.isYourCartTitleDisplayed(), "'Your cart' page title should be displayed.");
        logger.logInfo("Click 'Checkout' button.");
        cartPage.clickCheckoutButton();
        logger.logInfo(String.format("Enter personal data: '%s', '%s', '%s'.", FIRST_NAME, LAST_NAME, ZIP_CODE));
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        logger.logInfo("Click 'Continue' button.");
        checkoutStepOnePage.clickContinueButton();
        logger.logInfo("Cancel checkout.");
        checkoutStepTwoPage.clickCancelButton();
        productsPage.clickAddToCartButton("Sauce Labs Bolt T-Shirt");
        productsPage.clickShoppingCartLink();
        cartPage.clickCheckoutButton();
        logger.logInfo(String.format("Enter personal data: '%s', '%s', '%s'.", FIRST_NAME, LAST_NAME, ZIP_CODE));
        checkoutStepOnePage.setPersonalData(FIRST_NAME, LAST_NAME, ZIP_CODE);
        checkoutStepOnePage.clickContinueButton();
        checkoutStepTwoPage.clickFinishButton();
        logger.logInfo(String.format("Check that there is a '$s' message.", THANK_YOU_MESSAGE));
        Assert.assertEquals(checkoutCompletePage.getHeaderText(), THANK_YOU_MESSAGE,
                "There should be " + THANK_YOU_MESSAGE + " message.");
        logger.logInfo("Click 'Back Home' button.");
        checkoutCompletePage.clickBackHomeButton();
        logger.logInfo("Check that the inventory page is opened.");
        Assert.assertTrue(productsPage.isHeaderContainerDisplayed(), "The inventory page should be opened.");
    }

    @Test(groups = {"Smoke", "Negative"})
    public void negativeCheckoutEmptyFieldsTest() {
        logger.logInfo(String.format("Login to SauceDemo with username '%s' and password '%s'", USERNAME, PASSWORD));
        loginPage.login(USERNAME, PASSWORD);
        logger.logInfo("Add a product to the shoping cart.");
        productsPage.clickAddToCartButton("Sauce Labs Bike Light");
        logger.logInfo("Open the shopping cart.");
        productsPage.clickShoppingCartLink();
        logger.logInfo("Click 'Checkout' button.");
        cartPage.clickCheckoutButton();
        logger.logInfo("Click 'Continue' button.");
        checkoutStepOnePage.clickContinueButton();
        logger.logInfo("Check that error messages appear if required fields are missed.");
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
