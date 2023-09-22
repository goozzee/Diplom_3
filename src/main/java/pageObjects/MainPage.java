package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private final By orderPlaceButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By profileEnterButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By accountEnterButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By burgerAssembleTitle = By.xpath(".//*[text()='Соберите бургер']");
    private final By menuBuns = By.xpath(".//span[text()='Булки']/..");
    private final By menuSauces = By.xpath(".//span[text()='Соусы']/..");
    private final By menuFillings = By.xpath(".//span[text()='Начинки']/..");
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitingForMainPageLoading() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(burgerAssembleTitle));
    }

    public boolean orderPlaceButtonIsDisplayed() {
        return driver.findElement(orderPlaceButton).isDisplayed();
    }

    public void clickOnProfileEnterButton() {
        driver.findElement(profileEnterButton).click();
    }

    public void clickOnAccountEnterButton() {
        driver.findElement(accountEnterButton).click();
    }

    public void clickOnBunsMenu() {
        driver.findElement(menuBuns).click();
    }

    public void clickOnSaucesMenu() {
        driver.findElement(menuSauces).click();
    }

    public void clickOnFillingsMenu() {
        driver.findElement(menuFillings).click();
    }

    public boolean bunsMenuIsSelected() {
        return driver.findElement(menuBuns).getAttribute("class").contains("current");
    }

    public boolean saucesMenuIsSelected() {
        return driver.findElement(menuSauces).getAttribute("class").contains("current");
    }

    public boolean fillingsMenuIsSelected() {
        return driver.findElement(menuFillings).getAttribute("class").contains("current");
    }
}