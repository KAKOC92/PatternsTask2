package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {                                                     // Класс Дата Генератор есть

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker FAKER = new Faker(new Locale("en"));

    @Value
    public static class RegistrationDto {                                // в Этом классе находится что есть у пользователя
        String login;
        String password;
        String status;
    }

    private static RegistrationDto sendRequest(RegistrationDto user) {        // Тут метод должен получить пользователя
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    public static String getRandomLogin() {
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        return  FAKER.internet().password();

    }

    public static class Registration {               // Класс должен соеденить методы которые создают случайного
                                                     // пользователя и регистрируют его

        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
           return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            return sendRequest(getUser(status));
        }
    }
}



