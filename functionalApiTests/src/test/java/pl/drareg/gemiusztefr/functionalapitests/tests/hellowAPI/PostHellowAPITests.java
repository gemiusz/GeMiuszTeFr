package pl.drareg.gemiusztefr.functionalapitests.tests.hellowAPI;

import org.testng.annotations.Test;
import pl.drareg.gemiusztefr.functionalapitests.config.GlobalConfig;
import pl.drareg.gemiusztefr.functionalapitests.objects.hellowAPI.HellowAPIRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostHellowAPITests extends GlobalConfig {

    @Test
    public void postHellowAPI_checkStatus200() {
        HellowAPIRequest hellowAPIRequestObj = new HellowAPIRequest();
        hellowAPIRequestObj.setStringToAdd("World!");
        // @formatter:off
        given()
                .spec(specShowFullReqRes)
                .when()
                .contentType("application/json")
                .accept("application/json")
                .body(hellowAPIRequestObj)
                .post("/post")
                .then()
                .assertThat().statusCode(200);
        // @formatter:on
    }

    @Test
    public void postHellowAPI_checkStatus200andResponseMessage() {
        HellowAPIRequest hellowAPIRequestObj = new HellowAPIRequest();
        hellowAPIRequestObj.setStringToAdd("World!");
        // @formatter:off
        given()
                .spec(specShowFullReqRes)
                .when()
                .contentType("application/json")
                .accept("application/json")
                .body(hellowAPIRequestObj)
                .post("/post")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("Message", equalTo("Hellow API " + hellowAPIRequestObj.getStringToAdd()));
        // @formatter:on
    }
}

