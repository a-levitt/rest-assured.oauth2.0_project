package oauth2;

import io.restassured.path.json.JsonPath;

import java.util.Arrays;

import static io.restassured.RestAssured.*;

public class OAuth2Tests {
    public static void main(String[] args) {

        String clientId = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";

        // 3. Get access code
        String accessCodeResponse =
        given()
                .queryParam("scope", "https://www.googleapis.com/auth/userinfo.email")
                .queryParam("auth_url", "https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
        .when()
                .get("https://accounts.google.com/o/oauth2/v2/auth")
        .asString()
        ;


        // 2. Get access token
        String accessTokenResponse =
        given()
                .queryParam("code", "")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
        .when()
                .log().all()
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
                .log().all()
                .get("https://rahulshettyacademy.com/getCourse.php")
        .asString()
        ;
        System.out.println(response);
    }
}
