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
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("inventory"));
    }

    // TEST 1: LOGIN
    @Test(priority = 1)
    public void TC01_Login() {
        loginHelper();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed");
    }

    // TEST 2: LOGOUT
    @Test(priority = 2)
    public void TC05_Logout() {
        loginHelper();
        driver.findElement(By.id("react-burger-menu-btn")).click();

        WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

        // Use JavaScript to ensure the click happens during the animation
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }
    // TEST 3: ADD TO CART & OPEN CART
    @Test(priority = 3)
    public void TC02_AddBackpackAndOpenCart() {
        loginHelper();

        // 1. Add Backpack using direct ID click via JS
        ((JavascriptExecutor) driver).executeScript("document.getElementById('add-to-cart-sauce-labs-backpack').click();");

        // 2. Open Cart
        ProductsPage products = new ProductsPage(driver);
        products.openCart(); // Ensure this method in ProductsPage uses JavascriptExecutor too!

        // 3. Verify URL
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("cart"));
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Failed to navigate to cart");
    }
    /* Commented out for incremental testing

    @Test(priority = 3)
    public void TC02_AddItems() { ... }

    @Test(priority = 4)
    public void TC03_CheckoutFlow() { ... }
    */
    // TEST 4: CHECKOUT FORM & OVERVIEW
    @Test(priority = 4)
    public void TC03_CheckoutFlow() {
        loginHelper();

        // 1. Direct navigation to Step One to save time and reduce flakiness
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 2. Fill details with explicit waits for each field
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Sohaib");
        driver.findElement(By.id("last-name")).sendKeys("Baig");
        driver.findElement(By.id("postal-code")).sendKeys("75500");

        // 3. Force click the Continue button
        WebElement continueBtn = driver.findElement(By.id("continue"));
        js.executeScript("arguments[0].click();", continueBtn);

        // 4. Verify we reached Step Two (Overview)
        boolean reachedStepTwo = wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        Assert.assertTrue(reachedStepTwo, "Failed to reach the Overview page");
    }
}
