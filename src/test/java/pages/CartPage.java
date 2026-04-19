package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        // Initialize the wait with a 10-second timeout
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "checkout")
    WebElement checkoutBtn;

    public void clickCheckout() {
        // 1. Wait for the URL to contain 'cart' to ensure we are on the right page
        wait.until(ExpectedConditions.urlContains("cart.html"));

        // 2. Wait for the button to be visible and clickable
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }
}