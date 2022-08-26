package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetailsPage extends BasePageLoggedIn {
    public DetailsPage(WebDriver driver) {
        super(driver);
    }

    By addToCartButton = By.cssSelector("button[id^='add-to-cart']");
    By removeFromCartButton = By.cssSelector("button[id^='remove']");
    By productName = By.cssSelector("div[class='inventory_details_name large_size']");
    By productDescription = By.cssSelector("div[class='inventory_details_desc large_size']");
    By productPrice = By.cssSelector("div.inventory_details_price");

    public void clickAddToCartButton() {
        driver.findElement(addToCartButton).click();
    }

    public void clickRemoveButton() {
        driver.findElement(removeFromCartButton).click();
    }

    private String getProductName() {
        return driver.findElement(productName).getText();
    }

    public Boolean isProductNameCorrect(String expectedProductName) {
        return getProductName().equals(expectedProductName);
    }

    private String getProductDescription() {
        return driver.findElement(productDescription).getText();
    }

    public Boolean isProductDescriptionCorrect(String expectedProductDescription) {
        return getProductDescription().equals(expectedProductDescription);
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public Boolean isProductPriceCorrect(String expectedPrice) {
        return getProductPrice().equals(expectedPrice);
    }

    public Boolean isProductAddedToCart() {
        try {
            return driver.findElement(shoppingCartBadge).isDisplayed();
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }
}
