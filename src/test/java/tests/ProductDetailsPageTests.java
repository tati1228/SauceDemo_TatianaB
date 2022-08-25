package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import helpers.FileFormat;
import utils.TestDataParser;


public class ProductDetailsPageTests extends BaseTest {

    @DataProvider
    public Object[][] yamlProductDataProvider() {
        TestDataParser parser = new TestDataParser(FileFormat.YAML);
        return parser.readLinearStructure("test-data/product-details.yaml");
    }

    @Test(dataProvider = "yamlProductDataProvider", groups = {"Regression"})
    public void productDetailsTest(String name, String description, String price) {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.openProductDetails(name);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(detailsPage.isProductNameCorrect(name), "Product name should be " + name);
        softAssert.assertTrue(detailsPage.isProductDescriptionCorrect(description),
                "Product description should be: " + description);
        softAssert.assertTrue(detailsPage.isProductPriceCorrect(price), "Product price should be: " + price);
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] jsonProductNameDataProvider() {
        TestDataParser parser = new TestDataParser(FileFormat.JSON);
        return parser.readLinearStructure("test-data/product-name.json");
    }


    @Test(dataProvider = "jsonProductNameDataProvider", groups = {"Regression"})
    public void addRemoveProductTest(String name) {
        loginPage.login(USERNAME, PASSWORD);
        productsPage.openProductDetails(name);
        detailsPage.clickAddToCartButton();
        Assert.assertTrue(detailsPage.isProductAddedToCart(), "Product should be added to the cart.");
        detailsPage.clickRemoveButton();
        Assert.assertFalse(detailsPage.isProductAddedToCart(), "Product should be removed from the cart.");
    }

}
