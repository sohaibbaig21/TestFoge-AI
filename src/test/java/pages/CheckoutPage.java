package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillDetailsAndContinue(String fName, String lName, String zip) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Wait for the page to be ready
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));

        // 2. Inject values directly into the HTML 'value' attribute
        // This bypasses keyboard simulation entirely for maximum reliability
        js.executeScript("document.getElementById('first-name').value='" + fName + "';");
        js.executeScript("document.getElementById('last-name').value='" + lName + "';");
        js.executeScript("document.getElementById('postal-code').value='" + zip + "';");

        // 3. Force the form to submit
        WebElement continueBtn = driver.findElement(By.id("continue"));
        js.executeScript("arguments[0].click();", continueBtn);

        // 4. Verification Gate
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void completeOrder() {
        WebElement finishBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finishBtn);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }
}