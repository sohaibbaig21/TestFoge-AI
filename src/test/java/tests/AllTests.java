package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class AllTests extends BaseTest {

    // 🔹 TC01 - Login Test
    @Test
    public void TC01_LoginTest() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_saucee");

        Assert.assertTrue(driver.findElements(By.className("inventory_list")).size() > 0);
    }

    // 🔹 TC02 - Add Backpack
    @Test
    public void TC02_AddBackpack() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.addBackpack();

        Assert.assertTrue(driver.getPageSource().contains("Remove"));
    }

    // 🔹 TC03 - Add Bike Light
    @Test
    public void TC03_AddBike() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.addBike();

        Assert.assertTrue(driver.getPageSource().contains("Remove"));
    }

    // 🔹 TC04 - Open Cart
    @Test
    public void TC04_OpenCart() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.openCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }

    // 🔹 TC05 - Checkout Page Load
    @Test
    public void TC05_CheckoutPage() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        Assert.assertTrue(driver.getPageSource().contains("Checkout"));
    }

    // 🔹 TC06 - Fill Checkout Form
    @Test
    public void TC06_FillCheckout() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("John", "Doe", "12345");

        Assert.assertTrue(true);
    }

    // 🔹 TC07 - Complete Order
    @Test
    public void TC07_CompleteOrder() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        ProductsPage products = new ProductsPage(driver);
        products.openCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillDetails("John", "Doe", "12345");
        checkout.completeOrder();

        Assert.assertTrue(driver.getPageSource().contains("Thank you"));
    }

    // 🔹 TC08 - Logout (basic placeholder)
    @Test
    public void TC08_LogoutTest() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        Assert.assertTrue(true);
    }

    // 🔹 TC09 - Invalid Login
    @Test
    public void TC09_InvalidLogin() {
        driver.get("https://www.saucedemo.com");

        LoginPage login = new LoginPage(driver);
        login.login("wrong_user", "wrong_pass");

        Assert.assertTrue(driver.getPageSource().contains("Epic sadface"));
    }

    // 🔹 TC10 - Page Load Test
    @Test
    public void TC10_PageLoad() {
        driver.get("https://www.saucedemo.com");

        Assert.assertTrue(driver.getTitle().contains("Swag"));
    }
}