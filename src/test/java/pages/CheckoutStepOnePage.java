package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePageLoggedIn {
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    By firstNameInput = By.id("first-name");
    By lastNameInput = By.id("last-name");
    By zipCodeInput = By.id("postal-code");
    By continueButton = By.id("continue");
    By cancelButton = By.id("cancel");
    By errorMessage = By.cssSelector("h3[data-test='error']");

    public void setPersonalData(String firstName, String lastName, String zipCode) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(zipCodeInput).sendKeys(zipCode);
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }

    public void clickCancelButton() {
        driver.findElement(cancelButton).click();
    }

    public void setFirstNameValue(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void setLastNameValue(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void setZipCodeValue(String zipCode) {
        driver.findElement(zipCodeInput).sendKeys(zipCode);
    }

    public String getErrorMessage() {
        String message = driver.findElement(errorMessage).getText();
        return message;
    }
}
