package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {

    WebDriver driver;
    WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By backpack = By.id("add-to-cart-sauce-labs-backpack");
    By bike = By.id("add-to-cart-sauce-labs-bike-light");
    By cart = By.className("shopping_cart_link");

    public void addBackpack() {
        wait.until(ExpectedConditions.elementToBeClickable(backpack)).click();
    }

    public void addBike() {
        wait.until(ExpectedConditions.elementToBeClickable(bike)).click();
    }


    public void openCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Use the class name "shopping_cart_link"
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();
        // Force wait for navigation
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }
}