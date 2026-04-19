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
        // This wait is CRITICAL for GitHub Actions
        wait.until(ExpectedConditions.visibilityOf(firstName));
        firstName.sendKeys(f);
        lastName.sendKeys(l);
        postalCode.sendKeys(p);
        continueBtn.click(); // Click continue inside here to progress the state
    }

    public void completeOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
    }
}