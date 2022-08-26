package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePageLoggedIn {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    By checkoutButton = By.id("checkout");
    By yourCartTitle = By.cssSelector("span[class='title']");
    By continueShoppingButton = By.id("continue-shopping");

    public Boolean isYourCartTitleDisplayed() {
        try {
            return driver.findElement(yourCartTitle).isDisplayed();
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }

    public void clickContinueShoppingButton() {
        driver.findElement(continueShoppingButton).click();
    }

    public void clickCheckoutButton() {
        driver.findElement(checkoutButton).click();
    }
}
