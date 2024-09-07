package oauth2;

import io.restassured.path.json.JsonPath;

import java.util.Arrays;

import static io.restassured.RestAssured.*;

public class OAuth2Tests {
    public static void main(String[] args) {


        //2. Get access token
        String accessTokenResponse =
        given()
                .queryParam("code", "")
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
        .when()
                .post("https://www.googleapis.com/oauth2/v4/token")
        .asString()
        ;
        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.get("access_token");

        // 1. Actual request
        String response =
        given()
                .queryParam("access_token", accessToken)
        .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
        .asString()
        ;
        System.out.println(response);
    }
}
