package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageLoggedIn extends BasePage {

    public BasePageLoggedIn(WebDriver driver) {
        super(driver);
    }

    protected By shoppingCartLink = By.cssSelector("a[class='shopping_cart_link']");
    protected By shoppingCartBadge = By.cssSelector("span.shopping_cart_badge");
    protected By burgerButton = By.id("react-burger-menu-btn");
    protected By crossButton = By.id("react-burger-cross-btn");
    protected By menuContainer = By.cssSelector("div.bm-menu-wrap");
    protected By inventoryMenuItem = By.id("inventory_sidebar_link");
    protected By aboutMenuItem = By.id("about_sidebar_link");
    protected By logoutMenuItem = By.id("logout_sidebar_link");
    protected By resetMenuItem = By.id("reset_sidebar_link");
    protected By twitterLink = By.cssSelector("li.social_twitter");
    protected By facebookLink = By.cssSelector("li.social_twitter");
    protected By linkedInLink = By.cssSelector("li.social_linkedin");

    WebDriverWait webDriverWait = new WebDriverWait(driver, 1);

    public void clickShoppingCartLink() {
        driver.findElement(shoppingCartLink).click();
    }

    public Boolean isTwitterLinkDisplayed() {
        try {
            return driver.findElement(twitterLink).isDisplayed();
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }

    public Boolean isFacebookLinkDisplayed() {
        try {
            return driver.findElement(facebookLink).isDisplayed();
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }

    public Boolean isLinkedInLinkDisplayed() {
        try {
            return driver.findElement(linkedInLink).isDisplayed();
        } catch (Exception NoSuchElementException) {
            return false;
        }
    }

    public void menuButtonClick() {
        driver.findElement(burgerButton).click();
    }

    public Boolean isMenuDisplayed() {
        try {
            webDriverWait.until(ExpectedConditions.attributeToBe(driver.findElement(menuContainer),
                    "aria-hidden", "false"));
            return true;
        } catch (Exception TimeoutException) {
            return false;
        }
    }

    public Boolean isMenuClosed() {
        webDriverWait.until(ExpectedConditions.attributeToBe(driver.findElement(menuContainer),
                "aria-hidden", "true"));
        return driver.findElement(menuContainer).getAttribute("aria-hidden").equals("true");
    }

    public Boolean isAllItemsMenuItemDisplayed() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inventoryMenuItem));
        return driver.findElement(inventoryMenuItem).isDisplayed();
    }

    public Boolean isAboutMenuItemDisplayed() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(aboutMenuItem));
        return driver.findElement(aboutMenuItem).isDisplayed();
    }

    public Boolean isLogoutMenuItemDisplayed() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(logoutMenuItem));
        return driver.findElement(logoutMenuItem).isDisplayed();
    }

    public Boolean isResetMenuButtonDisplayed() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(resetMenuItem));
        return driver.findElement(resetMenuItem).isDisplayed();
    }

    public void closeMenuButtonClick() {
        driver.findElement(crossButton).click();
    }
}
