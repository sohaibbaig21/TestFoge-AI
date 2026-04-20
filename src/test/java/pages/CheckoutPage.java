package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "first-name")
    WebElement firstName;

    @FindBy(id = "last-name")
    WebElement lastName;

    @FindBy(id = "postal-code")
    WebElement postalCode;

    public void fillDetails(String fName, String lName, String zip) {
        // 1. Wait for fields and clear them before typing
        WebElement fNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        fNameField.clear();
        fNameField.sendKeys(fName);

        WebElement lNameField = driver.findElement(By.id("last-name"));
        lNameField.clear();
        lNameField.sendKeys(lName);

        WebElement zipField = driver.findElement(By.id("postal-code"));
        zipField.clear();
        zipField.sendKeys(zip);

        // 2. Use JavaScript Click for the Continue button
        // This bypasses any issues where the button is "covered" or not responding to standard clicks
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", continueBtn);

        // 3. Wait for the URL change to verify the click worked
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void completeOrder() {
        WebElement finishBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));

        // Scroll and Click via JavaScript to handle potential footer overlaps
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", finishBtn);
        js.executeScript("arguments[0].click();", finishBtn);

        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }
}