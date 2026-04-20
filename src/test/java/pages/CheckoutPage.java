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
        // Fill fields with explicit waits
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys(fName);
        driver.findElement(By.id("last-name")).sendKeys(lName);
        driver.findElement(By.id("postal-code")).sendKeys(zip);

        // Aggressive Click: Scroll, then JS click
        WebElement continueBtn = driver.findElement(By.id("continue"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);

        // Wait for the URL to change to Step Two
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void completeOrder() {
        WebElement finishBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finishBtn);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }
}