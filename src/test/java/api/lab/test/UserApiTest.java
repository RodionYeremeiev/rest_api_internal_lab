package api.lab.test;

import api.lab.main.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class UserApiTest {
    private final String BASE_URI = "https://petstore.swagger.io";
    private final String BASE_URI_V3 = "https://petstore3.swagger.io/api";
    private final String BASE_PATH_V2 = "/v2";
    private final String BASE_PATH_V3 = "/v3";
    private final String USER_ENDPOINT = "/user";

    User testUser3 = new User(3, "Petro", "Petrenko", "98155699", "+380634445566");

    List<User> testUsers = Arrays.asList(
        new User(0, "No", "Body", "000000000", "+380630000000"),
        new User(1, "Semen", "Semenchenko", "147258369", "+380632221100"),
        new User(2, "Ivan", "Ivanchenko", "123456789", "+380633334455"),
        new User(3, "Petro", "Petrenko", "98155699", "+380634445566"));


    @BeforeEach
    public void createUserIfNotPresent() {
        Response response =
            RestAssured.given()
                       .baseUri(BASE_URI)
                       .basePath(BASE_PATH_V2)
                       .accept(ContentType.JSON)
                       .contentType(ContentType.JSON)
                       .when().get(USER_ENDPOINT + "/" + testUser3.getUsername())
                       .prettyPeek();
        if (response.getStatusCode() != 200) {
            RestAssured.given()
                       .baseUri(BASE_URI)
                       .basePath(BASE_PATH_V2)
                       .accept(ContentType.JSON)
                       .contentType(ContentType.JSON)
                       .body(testUser3).when().post(USER_ENDPOINT)
                       .prettyPeek()
                       .then()
                       .statusCode(200);
        }
    }

    @AfterEach
    void removeAllUsers() {
        // TODO implement logic to cleanUp all created users
        for (User userToCleanUp : testUsers) {
            RestAssured.given()
                       .baseUri(BASE_URI)
                       .basePath(BASE_PATH_V2)
                       .accept(ContentType.JSON)
                       .contentType(ContentType.JSON)
                       .when().delete(USER_ENDPOINT + "/" + userToCleanUp)
                       .prettyPeek();
        }
    }

    @Test
    void canCreateUser() {
        User testUser2 = testUsers.get(2);
        RestAssured.given()
                   .baseUri(BASE_URI)
                   .basePath(BASE_PATH_V2)
                   .accept(ContentType.JSON)
                   .contentType(ContentType.JSON)
                   .body(testUser2).when().post(USER_ENDPOINT)
                   .prettyPeek()
                   .then()
                   .statusCode(200)
                   .and()
                   .body("message", CoreMatchers.equalTo(String.valueOf(testUser2.getId())))
                   .and()
                   .body("type", CoreMatchers.equalTo("unknown"));
    }

    @Test
    void canDeleteUser() {
        RestAssured.given()
                   .baseUri(BASE_URI)
                   .basePath(BASE_PATH_V2)
                   .accept(ContentType.JSON)
                   .contentType(ContentType.JSON)
                   .when().delete(USER_ENDPOINT + "/" + testUser3.getUsername())
                   .prettyPeek()
                   .then()
                   .statusCode(200);
    }

    @Test
    void canGetUserByUserName() {

    }

    @Test
    void canUpdateUserByUserName() {

    }

    @Test
    void canLogInUserByUserName() {

    }

    @Test
    void canLogOutUserByUserName() {

    }
}
