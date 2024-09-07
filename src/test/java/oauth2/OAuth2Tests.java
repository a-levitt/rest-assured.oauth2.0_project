package oauth2;

import java.util.Arrays;

import static io.restassured.RestAssured.*;

public class OAuth2Tests {
    public static void main(String[] args) {

        String response =
        given()
                .queryParam("access_token", "")
        .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
        .asString()
        ;
        System.out.println(response);
    }
}
