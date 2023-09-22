import constants.Endpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;
import model.UserRandomizer;
import model.UserSteps;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import pageObjects.LoginPage;
import pageObjects.SignUpPage;
import setupBrowser.SetUpWebDriver;

public class RegistrationTest {

    public WebDriver driver;
    public SignUpPage signUpPage;
    public LoginPage loginPage;
    public static String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = Endpoints.BASE_URL;
        driver = SetUpWebDriver.setUpWDM();
        signUpPage = new SignUpPage(driver);
        driver.get(Endpoints.BASE_URL + Endpoints.REGISTER);
        signUpPage.waitingForSignUpPageLoading();
    }
    @After
    public void tearDown() {
        if(driver != null){
            driver.quit();
        }
    }
    @AfterClass
    public static void afterClass() {
        UserSteps.deleteUser(accessToken);
    }


    @Test
    public void signUpTest() {
        User user = UserRandomizer.createNewRandomUser();
        loginPage = new LoginPage(driver);
        signUpPage.insertUserSignUpData(user);
        signUpPage.clickSignUpButton();
        loginPage.waitingForLoginFormLoading();
        Assert.assertEquals("Перешли на страницу логина", Endpoints.BASE_URL + Endpoints.LOGIN, driver.getCurrentUrl());
        Response response = UserSteps.authUser(user);
        Assert.assertEquals("Удалось залогиниться с данными созданного пользователя", 200, response.statusCode());
        accessToken = response.path("accessToken");
    }

    @Test
    public void signUpWithshortPasswordTest() {
        User user = UserRandomizer.createNewRandomUser();
        user.setPassword("12345");
        signUpPage.insertUserSignUpData(user);
        signUpPage.clickSignUpButton();
        Assert.assertTrue("Отображается ошибка о некорректном пароле", signUpPage.checkSignUpWrongPasswordError());
        Assert.assertEquals("Остались на странице логина", Endpoints.BASE_URL + Endpoints.REGISTER, driver.getCurrentUrl());
        Response response = UserSteps.authUser(user);
        Assert.assertFalse("Не удалось залогиниться с данными созданного пользователя", response.path("success"));
    }

}
