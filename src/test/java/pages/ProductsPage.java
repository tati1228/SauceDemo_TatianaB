package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductsPage extends BasePageLoggedIn {

    String itemContainer = "//div[@class='inventory_item_name' and text()='%s']/ancestor::div[@class='inventory_item']";
    By selectLocator = By.cssSelector("select[class='product_sort_container']");
    By headerContainer = By.id("header_container");
    By itemDetailsLink = By.cssSelector("a[id$='title_link']");
    By addRemoveButton = By.tagName("button");
    By addToCartButton = By.cssSelector("button[id^='add-to-cart']");
    By productPrice = By.cssSelector("div.inventory_item_price");
    By productDescription = By.cssSelector("div.inventory_item_desc");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }


    public boolean isHeaderContainerDisplayed() {
        return driver.findElement(headerContainer).isDisplayed();
    }


    public void addRemoveAllProducts() {
        for (WebElement product : getAllProductContainers()) {
            product.findElement(addRemoveButton).click();
        }
    }

    public int getNumberOfProductsPerPage() {
        return getAllProductContainers().size();
    }

    public Boolean isCartItemsCounterCorrect(String expectedCounter) {
        String actualCounter = getCartItemsCounter();
        return actualCounter.equals(expectedCounter);
    }

    private String getCartItemsCounter() {
        try {
            return driver.findElement(shoppingCartBadge).getText();
        } catch (Exception NoSuchElementException) {
            return "";
        }
    }

    public String getProductPrice(String productName) {
        WebElement productContainer = itemContainer(productName);
        return productContainer.findElement(productPrice).getText();
    }

    public Boolean isProductPriceCorrect(String name, String expectedPrice) {
        return getProductPrice(name).equals(expectedPrice);
    }

    public String getProductDescription(String productName) {
        WebElement productContainer = itemContainer(productName);
        return productContainer.findElement(productDescription).getText();
    }

    public Boolean isProductDescriptionCorrect(String name, String expectedDescription) {
        return getProductDescription(name).equals(expectedDescription);
    }

    public void openProductDetails(String productName) {
        WebElement productContainer = itemContainer(productName);
        productContainer.findElement(itemDetailsLink).click();
    }

    public WebElement itemContainer(String productName) {
        return driver.findElement(By.xpath(String.format(itemContainer, productName)));
    }

    public void clickAddToCartButton(String productName) {
        itemContainer(productName).findElement(addToCartButton).click();
    }


    public List<String> getSelectOptionList() {
        Select select = new Select(driver.findElement(selectLocator));
        List<WebElement> allOptions = select.getOptions();
        List<String> allOptionsNames = allOptions.stream().map(option -> option.getText()).toList();
        return allOptionsNames;
    }

    private List<WebElement> getAllProductContainers() {
        List<WebElement> allProductContainers = driver.findElements(By.cssSelector("div.inventory_item"));
        return allProductContainers;
    }
}
