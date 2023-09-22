package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {
    private final By signInLink = By.xpath(".//a[text()='Войти']");
    private final WebDriver driver;

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignInLink() {
        driver.findElement(signInLink).click();
    }
}