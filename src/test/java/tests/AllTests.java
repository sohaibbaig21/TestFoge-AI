package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        // Ensure window is maximized for element visibility
        driver.manage().window().maximize();
        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");
    }

    @Test(priority = 1)
    public void TC01_LoginTest() {
        loginHelper();
        Assert.assertTrue(driver.findElements(By.className("inventory_list")).size() > 0, "Login failed!");
    }

    @Test(priority = 2)
    public void TC02_AddBackpack() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        Assert.assertTrue(driver.getPageSource().contains("Remove"), "Backpack not added!");
    }

    @Test(priority = 3)
    public void TC03_AddBike() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBike();
        Assert.assertTrue(driver.getPageSource().contains("Remove"), "Bike light not added!");
    }

    @Test(priority = 4)
    public void TC04_OpenCart() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.openCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Cart page didn't load!");
    }
    @Test(priority = 5)
    public void TC05_CheckoutPage() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        // Use a wait instead of a direct Assert on the URL
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isCorrectUrl = wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        Assert.assertTrue(isCorrectUrl, "Checkout page didn't load!");
    }

    @Test(priority = 6)
    public void TC06_FillCheckout() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();
        new CartPage(driver).clickCheckout();
        new CheckoutPage(driver).fillDetails("Sohaib", "Baig", "75500");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"), "Failed to reach Overview page!");
    }

    @Test(priority = 7)
    public void TC07_CompleteOrder() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();
        new CartPage(driver).clickCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("Sohaib", "Baig", "75500");
        checkout.completeOrder();
        Assert.assertTrue(driver.getPageSource().contains("Thank you for your order!"), "Order completion failed!");
    }

    @Test(priority = 8)
    public void TC08_LogoutTest() {
        loginHelper();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open Sidebar
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn"))).click();

        // Use JS click for logout to bypass animation interference
        WebElement logoutLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logout_sidebar_link")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutLink);

        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
        Assert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(), "Logout failed!");
    }

    @Test(priority = 9)
    public void TC09_InvalidLogin() {
        driver.get("https://www.saucedemo.com");
        new LoginPage(driver).login("locked_out_user", "secret_sauce");
        Assert.assertTrue(driver.getPageSource().contains("Epic sadface"), "Error message not displayed!");
    }

    @Test(priority = 10)
    public void TC10_PageLoad() {
        driver.get("https://www.saucedemo.com");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Page title mismatch!");
    }
}