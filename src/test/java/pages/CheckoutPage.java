package pages;

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

    @FindBy(id = "continue")
    WebElement continueBtn;

    @FindBy(id = "finish")
    WebElement finishBtn;

    public void fillDetails(String f, String l, String p) {
        wait.until(ExpectedConditions.visibilityOf(firstName));
        firstName.clear(); // Good practice to clear before typing
        firstName.sendKeys(f);
        lastName.clear();
        lastName.sendKeys(l);
        postalCode.clear();
        postalCode.sendKeys(p);

        continueBtn.click();

        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void completeOrder() {
        // Ensure the button is actually ready for a click
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
        // Wait for the final confirmation page
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }
}