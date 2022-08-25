package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePageLoggedIn {
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    By completeHeader = By.cssSelector("h2[class='complete-header']");
    By backHomeButton = By.id("back-to-products");

    public void clickBackHomeButton() {
        driver.findElement(backHomeButton).click();
    }

    public String getHeaderText() {
        return driver.findElement(completeHeader).getText();
    }
}
