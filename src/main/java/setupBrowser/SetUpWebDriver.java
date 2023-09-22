package setupBrowser;

import constants.Endpoints;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class SetUpWebDriver {
//    private static String browser = "yandex";
//    private static String browser = "chrome";

    public static WebDriver setUpWDM() {
        WebDriverManager.chromedriver().setup();
        if (System.getProperty("browser.type") != null && System.getProperty("browser.type").equals("yandex")) {
            System.setProperty("webdriver.chrome.driver", Endpoints.YANDEXDRIVER);
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        WebDriver driver;
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}