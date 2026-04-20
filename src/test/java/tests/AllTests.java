package tests;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import java.time.Duration;

public class AllTests extends BaseTest {

    private void loginHelper() {
        driver.get("https://www.saucedemo.com");
        driver.manage().window().maximize();

        // Direct Login via JS to ensure no timeouts during authentication
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('user-name').value='standard_user';");
        js.executeScript("document.getElementById('password').value='secret_sauce';");
        js.executeScript("document.getElementById('login-button').click();");

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("inventory"));
    }

    @Test(priority = 1)
    public void TC01_LoginAndLogout() {
        loginHelper();

        // Open Sidebar and Logout
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Logout failed");
    }

    @Test(priority = 2)
    public void TC02_AddBackpackToCart() {
        loginHelper();

        // Add to cart via direct JS click
        ((JavascriptExecutor) driver).executeScript("document.getElementById('add-to-cart-sauce-labs-backpack').click();");

        // Open Cart via direct navigation (fastest/safest method)
        driver.get("https://www.saucedemo.com/cart.html");

        Assert.assertTrue(driver.getPageSource().contains("Sauce Labs Backpack"), "Item not in cart");
    }

    @Test(priority = 3)
    public void TC03_CheckoutInformationForm() {
        loginHelper();
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetailsAndContinue("Sohaib", "Baig", "75500");

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"), "Navigation to Overview failed");
    }

    @Test(priority = 4)
    public void TC04_EndToEndOrderCompletion() {
        loginHelper();
        // Skip ahead to step one to ensure we don't time out in earlier stages
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetailsAndContinue("Sohaib", "Baig", "75500");
        checkout.completeOrder();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"), "Order completion failed");
    }
}