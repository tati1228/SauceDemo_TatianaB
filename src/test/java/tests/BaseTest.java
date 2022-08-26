package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.*;

public class BaseTest {
    protected WebDriver driver;
    public LoginPage loginPage;
    protected ProductsPage productsPage;
    protected DetailsPage detailsPage;
    protected CartPage cartPage;
    protected CheckoutStepOnePage checkoutStepOnePage;
    protected CheckoutStepTwoPage checkoutStepTwoPage;
    protected CheckoutCompletePage checkoutCompletePage;

    protected String USERNAME = "standard_user";
    protected String PASSWORD = "secret_sauce";
    protected String FIRST_NAME = "Elon";
    protected String LAST_NAME = "Musk";
    protected String ZIP_CODE = "77005";

    protected String THANK_YOU_MESSAGE = "THANK YOU FOR YOUR ORDER";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        String browser = System.getProperty("browser");
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            driver = new ChromeDriver(options);
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new Error("Only chrome and edge are supported at the moment");
        }
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        detailsPage = new DetailsPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigate() {
        loginPage.open();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
