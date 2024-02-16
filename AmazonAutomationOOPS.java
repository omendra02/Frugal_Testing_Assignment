// more clean and organised than AmazonPurchaseAutomation
//implemented some OOPS concepts
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Set;

    class AmazonPurchaseHelper {
        private final WebDriver driver;
        private final WebDriverWait wait;

        public AmazonPurchaseHelper(WebDriver driver, WebDriverWait wait) {
            this.driver = driver;
            this.wait = wait;
        }

        public void applyFiltersAndPurchase() {
            navigateToAmazon();
            applySearch("mobile");
            applyFilters();
            clickOnFirstSearchResult();
            switchToNewTab();
            scrollDownAndAddToCart();
            navigateToCart();
        }

        private void navigateToAmazon() {
            driver.get("https://www.amazon.in/");
            validatePageTitle("Amazon");
        }

        private void validatePageTitle(String expectedTitle) {
            if (driver.getTitle().contains(expectedTitle)) {
                System.out.println("Landed on the correct page.");
            } else {
                System.out.println("Did not land on the expected page.");
            }
            System.out.println("URL: " + driver.getCurrentUrl());
            System.out.println("Title: " + driver.getTitle());
        }

        private void applySearch(String searchQuery) {
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys(searchQuery);
            searchBox.submit();
        }

        private void applyFilters() {
            WebElement fourStarsFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='reviewsRefinements']//i[@class='a-icon a-icon-star-medium a-star-medium-4']")));
            fourStarsFilter.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='a-icon a-icon-star-medium a-star-medium-4']")));
            System.out.println("Applied 4 stars filter.");

            WebElement priceRangeFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='priceRefinements']//span[@class='a-size-base a-color-base'][contains(text(),'₹10,000 - ₹20,000')]")));
            priceRangeFilter.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'₹10,000 - ₹20,000')]")));
            System.out.println("Price filter applied.");
        }

        private void clickOnFirstSearchResult() {
            WebElement firstSearchResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/span/div/div/div/div[2]/div/div/div[1]/h2/a/span")));
            firstSearchResult.click();
            System.out.println("Clicked on the first search result.");
        }

        private void switchToNewTab() {
            String originalWindowHandle = driver.getWindowHandle();
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        }

        private void scrollDownAndAddToCart() {
            int scrollPixels = 500;
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scrollPixels + ");");
            System.out.println("Scrolled down.");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
            addToCartButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("attach-sidesheet-view-cart-button")));
            System.out.println("Added phone to cart.");
        }

        private void navigateToCart() {
            WebElement goToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("attach-sidesheet-view-cart-button")));
            goToCartButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("activeCartViewForm")));
            System.out.println("Navigated to the cart.");
        }
    }

    public class AmazonAutomationOOPS {
        public static void main(String[] args) {
            // Set the path to the ChromeDriver executable
            System.setProperty("webdriver.chrome.driver", "D://Omendra_UPES_500084970//selenium jars and drivers//drivers//chromedriver//chromedriver.exe");

            // Create ChromeOptions instance for incognito mode
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");

            // Create a new instance of the ChromeDriver with incognito mode
            WebDriver driver = new ChromeDriver(options);

            // Create WebDriverWait instance with a timeout of 20 seconds
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Create an instance of AmazonPurchaseHelper
            AmazonPurchaseHelper amazonHelper = new AmazonPurchaseHelper(driver, wait);

            // Use the helper to perform the purchase automation
            amazonHelper.applyFiltersAndPurchase();

            // Quit the driver, closing all associated windows
            driver.quit();
        }
    }


