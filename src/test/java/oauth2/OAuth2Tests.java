package oauth2;

import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static payload.Secret.*;

public class OAuth2Tests {
    public static void main(String[] args) throws InterruptedException {

        String clientId = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";

        // Use Selenium to get authorization code
        WebDriver driver = new ChromeDriver();

        driver.get("https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=https://www.googleapis.com/auth/userinfo.email&" +
                "auth_url=https://accounts.google.com/o/oauth2/v2/auth&" +
                "client_id=" + clientId + "&" +
                "response_type=code&" +
                "redirect_uri=https://rahulshettyacademy.com/getCourse.php");

        driver.findElement(By.id("identifierId")).sendKeys(getEmail());
        driver.findElement(By.xpath("(//button[@jsname=\"LgbsSe\"])[2]")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(getPassword());
        driver.findElement(By.xpath("(//button[@jsname=\"LgbsSe\"])[2]")).click();
        Thread.sleep(4000);
        String codeUrl = driver.getCurrentUrl();
        String partialCode = codeUrl.split("code=")[1];
        String authorizationCode = partialCode.split("&scope")[0];

        // 2. Get access token
        String accessTokenResponse =
        given()
                .queryParam("code", authorizationCode)
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
