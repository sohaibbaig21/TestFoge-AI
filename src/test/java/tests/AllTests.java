package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import org.testng.annotations.Listeners;
import java.time.Duration;

@Listeners(utils.TestListener.class)
public class AllTests extends BaseTest {

    private void loginHelper() {
        driver.get("https://www.saucedemo.com");
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");
    }

    // 🔹 TC01 - Login Test: Verifies that a standard user can log in successfully and view the inventory.
    @Test(priority = 1)
    public void TC01_LoginTest() {
        loginHelper();
        Assert.assertTrue(driver.findElements(By.className("inventory_list")).size() > 0, "Login failed!");
    }

    // 🔹 TC02 - Add Backpack: Verifies that the 'Sauce Labs Backpack' can be added to the cart.
    @Test(priority = 2)
    public void TC02_AddBackpack() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        Assert.assertTrue(driver.getPageSource().contains("Remove"), "Backpack not added!");
    }

    // 🔹 TC03 - Add Bike Light: Verifies that the 'Sauce Labs Bike Light' can be added to the cart.
    @Test(priority = 3)
    public void TC03_AddBike() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBike();
        Assert.assertTrue(driver.getPageSource().contains("Remove"), "Bike light not added!");
    }

    // 🔹 TC04 - Open Cart: Verifies that clicking the cart icon successfully navigates to the Cart page.
    @Test(priority = 4)
    public void TC04_OpenCart() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.openCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Cart page didn't load!");
    }

    // 🔹 TC05 - Checkout Page Load: Verifies that the checkout process starts correctly after adding items.
    @Test(priority = 5)
    public void TC05_CheckoutPage() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Checkout page didn't load!");
    }

    // 🔹 TC06 - Fill Checkout Form: Verifies that a user can fill in information and reach the overview page.
    @Test(priority = 6)
    public void TC06_FillCheckout() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("Sohaib", "Baig", "75500");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"), "Failed to reach Overview page!");
    }

    // 🔹 TC07 - Complete Order: Verifies the full end-to-end flow from adding an item to receiving the 'Thank You' confirmation.
    @Test(priority = 7)
    public void TC07_CompleteOrder() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("Sohaib", "Baig", "75500");
        checkout.completeOrder();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
        Assert.assertTrue(driver.getPageSource().contains("Thank you for your order!"), "Order completion failed!");
    }

    // 🔹 TC08 - Logout Test: Verifies that a user can successfully log out and is redirected back to the login page.
    @Test(priority = 8)
    public void TC08_LogoutTest() {
        loginHelper();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn"))).click();

        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        logoutLink.click();

        WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login-button")));
        Assert.assertTrue(loginBtn.isDisplayed(), "Logout failed - Login button not visible!");
    }

    // 🔹 TC09 - Invalid Login: Verifies that a locked-out or incorrect user receives the appropriate error message.
    @Test(priority = 9)
    public void TC09_InvalidLogin() {
        driver.get("https://www.saucedemo.com");
        LoginPage login = new LoginPage(driver);
        login.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(driver.getPageSource().contains("Epic sadface"), "Error message not displayed!");
    }

    // 🔹 TC10 - Page Load Test: A simple sanity check to ensure the base website title is correct.
    @Test(priority = 10)
    public void TC10_PageLoad() {
        driver.get("https://www.saucedemo.com");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Page title mismatch!");
    }
}