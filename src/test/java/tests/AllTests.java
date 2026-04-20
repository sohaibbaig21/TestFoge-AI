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

    /* Commented out for incremental testing

    @Test(priority = 3)
    public void TC02_AddItems() { ... }

    @Test(priority = 4)
    public void TC03_CheckoutFlow() { ... }
    */
}