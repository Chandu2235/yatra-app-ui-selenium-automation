package LoginTePage;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginYatra;

public class LoginTest {

	public static void main(String[] args) {
			
		// Automatically downloads and sets up the correct ChromeDriver version
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

	  driver.get("https://www.yatra.com/");
	 
	  LoginYatra test=new LoginYatra(driver);
	  test.enterUsernameText("chandu.birhade@gmail.com");
	  test.clickLoginButton();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  
	  test.enterPasswordText("Chandu22@#$");
	  test.clickLoginButton();
	
	}

}
