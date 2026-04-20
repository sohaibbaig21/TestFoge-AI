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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Wait for and fill fields
        WebElement fNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        fNameField.clear();
        fNameField.sendKeys(fName);

        driver.findElement(By.id("last-name")).clear();
        driver.findElement(By.id("last-name")).sendKeys(lName);

        driver.findElement(By.id("postal-code")).clear();
        driver.findElement(By.id("postal-code")).sendKeys(zip);

        // 2. Force the click on the Continue button via JS
        WebElement continueBtn = driver.findElement(By.id("continue"));
        js.executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        js.executeScript("arguments[0].click();", continueBtn);

        // 3. Wait for URL transition
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