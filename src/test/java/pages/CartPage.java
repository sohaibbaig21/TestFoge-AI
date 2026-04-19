package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locator
    private By checkoutBtn = By.id("checkout");

    public void clickCheckout() {
        // Wait for the URL first to ensure navigation finished
        wait.until(ExpectedConditions.urlContains("cart.html"));

        // Wait for button and click
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }
}