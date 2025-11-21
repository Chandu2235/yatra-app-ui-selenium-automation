package LoginTePage;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginYatra;

public class LoginTest {

    private WebDriver driver;
    private LoginYatra loginPage;

    @BeforeMethod
    public void setUp() {
        // Automatically downloads and sets up the correct ChromeDriver version
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        // Add headless mode for CI/CD
        if (System.getProperty("headless", "false").equals("true")) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        loginPage = new LoginYatra(driver);
    }

    @Test
    public void testYatraLogin() {
        driver.get("https://www.yatra.com/");
        
        loginPage.enterUsernameText("chandu.birhade@gmail.com");
        loginPage.clickLoginButton();
        
        // Add some wait or verification here
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        loginPage.enterPasswordText("Chandu22@#");
        loginPage.clickLoginButton();
        
        // Add assertions here to verify login success
        System.out.println("Login test completed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}