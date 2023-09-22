package model;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class UserRandomizer {

    public static Faker faker = new Faker();

    @Step("Создание нового пользователя с рандомными данными")
    public static User createNewRandomUser() {
        return new User(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }
}