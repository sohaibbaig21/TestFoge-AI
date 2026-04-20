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

    @Test(priority = 1)
    public void TC01_Login() { loginHelper(); }

    @Test(priority = 2)
    public void TC02_AddItemsAndOpenCart() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        // Using direct JS clicks via driver if PageObjects fail
        ((JavascriptExecutor) driver).executeScript("document.getElementById('add-to-cart-sauce-labs-backpack').click();");
        products.openCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Failed to open cart");
    }

    @Test(priority = 3)
    public void TC03_CheckoutFlow() {
        loginHelper();
        driver.get("https://www.saucedemo.com/cart.html");

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetailsAndContinue("Sohaib", "Baig", "75500");

        Assert.assertTrue(driver.getCurrentUrl().contains("step-two"), "Failed to reach Overview");
    }

    @Test(priority = 4)
    public void TC04_FinalizeOrder() {
        loginHelper();
        driver.get("https://www.saucedemo.com/checkout-step-one.html");

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetailsAndContinue("Sohaib", "Baig", "75500");
        checkout.completeOrder();

        Assert.assertTrue(driver.getCurrentUrl().contains("complete"), "Order not finished");
    }

    @Test(priority = 5)
    public void TC05_Logout() {
        loginHelper();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }
}