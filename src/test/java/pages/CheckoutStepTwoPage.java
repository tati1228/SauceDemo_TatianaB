package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage extends BasePageLoggedIn {
    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    By finishButton = By.id("finish");
    By cancelCheckoutButton = By.id("cancel");

    public void clickFinishButton() {
        driver.findElement(finishButton).click();
    }

    public void clickCancelButton() {
        driver.findElement(cancelCheckoutButton).click();
    }

}
