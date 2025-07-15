package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginYatra {

    private WebDriver driver;

    // Locators
    private By username = By.id("//*[@id=\"mobile-number\"]");
    private By password = By.id("//*[@id=\"pass\"]");
    private By loginButton = By.id("//button[text()=\"Login\"]");

    // Constructor
    public LoginYatra(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsernameText(String user) {
        driver.findElement(username).sendKeys(user);
    }

    public void enterPasswordText(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}

