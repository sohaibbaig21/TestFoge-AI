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

        // 1. Wait for and fill the First Name
        WebElement fNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        fNameField.clear();
        fNameField.sendKeys(fName);

        // 2. Fill the Last Name
        WebElement lNameField = driver.findElement(By.id("last-name"));
        lNameField.clear();
        lNameField.sendKeys(lName);

        // 3. Fill the Zip and use .submit() instead of clicking the button
        // .submit() is a Selenium method that works on any element within a <form>
        WebElement zipField = driver.findElement(By.id("postal-code"));
        zipField.clear();
        zipField.sendKeys(zip);

        // Fallback: If submit doesn't trigger, we use the JS click on the continue button
        try {
            zipField.submit();
        } catch (Exception e) {
            WebElement continueBtn = driver.findElement(By.id("continue"));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
        }

        // 4. Wait for URL change to Step Two
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