package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NegativeLoginTests {

    private WebDriver driver;
    private String loginPageUrl = "http://localhost/login";
    private String invalidCredentialsErrorMessageText = "Invalid username or password";
    private String blankCredentialsErrorMessageText = "Username and Password cannot be blank";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(loginPageUrl);
    }

    @Test
    public void testInvalidLoginWrongPassword() {
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        usernameField.sendKeys("testuser");
        passwordField.sendKeys("wrongpassword");
        loginButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement errorMessageElement = driver.findElement(By.id("errorMessage"));
        Assert.assertTrue(errorMessageElement.isDisplayed(), "Error message was not displayed.");
        Assert.assertEquals(errorMessageElement.getText(), invalidCredentialsErrorMessageText, "Error message text is incorrect.");
    }

    @Test
    public void testLoginWithBlankCredentials() {
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement errorMessageElement = driver.findElement(By.id("errorMessage"));
        Assert.assertTrue(errorMessageElement.isDisplayed(), "Error message for blank credentials was not displayed.");
        Assert.assertEquals(errorMessageElement.getText(), blankCredentialsErrorMessageText, "Error message text for blank credentials is incorrect.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
