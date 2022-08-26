package tests;

import helpers.FileFormat;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.TestDataParser;

import java.util.ArrayList;
import java.util.List;

public class InventoryTests extends BaseTest {

    @DataProvider
    public Object[][] yamlProductDataProvider() {
        TestDataParser parser = new TestDataParser(FileFormat.YAML);
        return parser.readLinearStructure("test-data/product-details.yaml");
    }

    @Test(dataProvider = "yamlProductDataProvider", groups = {"Smoke", "Regression"})
    public void productDataTest(String name, String description, String price) {
        loginPage.login(USERNAME, PASSWORD);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(productsPage.isProductPriceCorrect(name, price), "Product price should be equal to " + price);
        softAssert.assertTrue(productsPage.isProductDescriptionCorrect(name, description), "Product description should be: " + description);
        softAssert.assertAll();
    }

    @Test(groups = {"Regression"})
    public void inventorySortingTest() {
        loginPage.login(USERNAME, PASSWORD);
        List<String> expectedOptionNames = new ArrayList<>();
        expectedOptionNames.add("Name (A to Z)");
        expectedOptionNames.add("Name (Z to A)");
        expectedOptionNames.add("Price (low to high)");
        expectedOptionNames.add("Price (high to low)");
        List<String> allOptionsNames = productsPage.getSelectOptionList();
        Assert.assertTrue(allOptionsNames.equals(expectedOptionNames), "List of sorting options should be correct.");
    }

    @Test(groups = {"Regression"})
    public void cartItemsCounterTest() {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.addRemoveAllProducts();
        String productsPerPage = Integer.toString(productsPage.getNumberOfProductsPerPage());
        Assert.assertTrue(productsPage.isCartItemsCounterCorrect(productsPerPage), "There should be six products in the cart.");
        productsPage.addRemoveAllProducts();
        Assert.assertTrue(productsPage.isCartItemsCounterCorrect(""), "There should be no products in the cart.");
    }

    @Test(groups = {"Regression"})
    public void socialLinksTest() {
        loginPage.login(USERNAME, PASSWORD);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(productsPage.isTwitterLinkDisplayed(), "Twitter link should be present.");
        softAssert.assertTrue(productsPage.isFacebookLinkDisplayed(), "Facebook link should be present.");
        softAssert.assertTrue(productsPage.isLinkedInLinkDisplayed(), "LinkedIn link should be present.");
        softAssert.assertAll();
    }

    @Test(groups = {"Regression"})
    public void menuItemsTest() {
        loginPage.login(USERNAME, PASSWORD);
        SoftAssert softAssert = new SoftAssert();
        productsPage.menuButtonClick();
        Assert.assertTrue(productsPage.isMenuDisplayed(), "Menu should be opened");
        softAssert.assertTrue(productsPage.isAllItemsMenuItemDisplayed(), "'ALL ITEMS' menu option should be present.");
        softAssert.assertTrue(productsPage.isAboutMenuItemDisplayed(), "'ABOUT' menu option should be present.");
        softAssert.assertTrue(productsPage.isLogoutMenuItemDisplayed(), "'LOGOUT' menu option should be present.");
        softAssert.assertTrue(productsPage.isResetMenuButtonDisplayed(), "'RESET APP STATE' menu option should be present.");
        productsPage.closeMenuButtonClick();
        Assert.assertTrue(productsPage.isMenuClosed(), "Menu should be closed.");
        softAssert.assertAll();
    }

}
