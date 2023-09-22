import constants.Endpoints;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.User;
import model.UserRandomizer;
import model.UserSteps;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import pageObjects.MainPage;
import pageObjects.PasswordRecoveryPage;
import pageObjects.SignUpPage;
import setupBrowser.SetUpWebDriver;

import static org.junit.Assert.assertTrue;


public class LoginTest {

    public static String accessToken;
    public static User user;
    public static WebDriver driver;
    public static LoginPage loginPage;
    public MainPage mainPage;

    @Before
    public void setUp() {
        driver = SetUpWebDriver.setUpWDM();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeClass
    public static void beforeClass() {
        RestAssured.baseURI = Endpoints.BASE_URL;
        user = UserRandomizer.createNewRandomUser();
        accessToken = UserSteps.createUser(user).path("accessToken");
    }

    @AfterClass
    public static void afterClass() {
        UserSteps.deleteUser(accessToken);
    }

    @Test
    public void checkLoginFromLoginPage() {
        driver.get(Endpoints.BASE_URL + Endpoints.LOGIN);
        assertions();
    }

    @Test
    @DisplayName("Проверка авторизации пользователя по логину и паролю - Личный кабинет")
    public void checkLoginFromMainPageProfileButton() {
        driver.get(Endpoints.BASE_URL);
        mainPage.waitingForMainPageLoading();
        mainPage.clickOnProfileEnterButton();
        assertions();
    }

    @Test
    @DisplayName("Проверка авторизации пользователя по логину и паролю - Войти в аккаунт")
    public void checkLoginFromMainPageByEmailAndPassword() {
        driver.get(Endpoints.BASE_URL);
        mainPage.waitingForMainPageLoading();
        mainPage.clickOnAccountEnterButton();
        assertions();
    }

    @Test
    @DisplayName("Проверка авторизации по кнопке - Войти - на странице - Регистрация")
    public void checkLoginFromSignUpPage() {
        driver.get(Endpoints.BASE_URL + Endpoints.REGISTER);
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.clickSignInLink();
        assertions();
    }

    @Test
    @DisplayName("Проверка авторизации через страницу восстановления пароля")
    public void checkLoginFromRecoveryPage() {
        driver.get(Endpoints.BASE_URL + Endpoints.FORGOT_PASS);
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickSignInLink();
        assertions();
    }

    @Step("Страница входа c правильным адресом")
    public static boolean loginPageCorrectUrl() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitingForLoginFormLoading();
        return driver.getCurrentUrl().equals(Endpoints.BASE_URL + Endpoints.LOGIN);
    }

    @Step("Главная страница с кнопкой 'Оформить заказ'")
    public static boolean mainPageIsLoadedAfterSuccessfulLogin() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitingForMainPageLoading();
        return mainPage.orderPlaceButtonIsDisplayed();
    }

    public static void assertions() {
        assertTrue("Зашли на страницу логина", loginPageCorrectUrl());
        loginPage.insertUserDataAndClickLoginButton(user);
        assertTrue("Вход выполнен успешно", mainPageIsLoadedAfterSuccessfulLogin());
    }


}
