package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import org.testng.annotations.Listeners;

@Listeners(utils.TestListener.class)
public class AllTests extends BaseTest {

    private void loginHelper() {
        driver.get("https://www.saucedemo.com");
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
        products.openCart();
        CartPage cart = new CartPage(driver);
        cart.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Checkout page didn't load!");
    }

    @Test(priority = 6)
    public void TC06_FillCheckout() {
        loginHelper();
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack(); // Ensure there's an item, otherwise checkout might hang
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("Sohaib", "Baig", "75500");

        // Check for the 'Checkout: Overview' header text
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
                "Failed to transition to the Overview page!");
    }

    @Test(priority = 7)
    public void TC07_CompleteOrder() {
        loginHelper();
        // Add an item first to ensure the checkout flow is valid
        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("Sohaib", "Baig", "75500");
        checkout.completeOrder();

        Assert.assertTrue(driver.getPageSource().contains("Thank you for your order!"), "Order completion failed!");
    }

    @Test(priority = 8)
    public void TC09_InvalidLogin() {
        driver.get("https://www.saucedemo.com");
        LoginPage login = new LoginPage(driver);
        login.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(driver.getPageSource().contains("Epic sadface"), "Error message not displayed for invalid login!");
    }

    @Test(priority = 9)
    public void TC10_PageLoad() {
        driver.get("https://www.saucedemo.com");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Page title mismatch!");
    }
}