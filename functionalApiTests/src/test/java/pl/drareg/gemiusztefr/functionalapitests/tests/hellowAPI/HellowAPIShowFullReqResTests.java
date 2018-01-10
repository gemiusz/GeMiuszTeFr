package pl.drareg.gemiusztefr.functionalapitests.tests.hellowAPI;

import org.testng.annotations.Test;
import pl.drareg.gemiusztefr.functionalapitests.config.GlobalConfig;

import static io.restassured.RestAssured.given;

public class HellowAPIShowFullReqResTests extends GlobalConfig {
    @Test
    public void getHellowAPIShowFullReqResTest() {

        // @formatter:off
        given()
                .spec(specShowFullReqRes)
                .when()
                .get("/get")
                .then()
                .assertThat()
                .statusCode(200);
        // @formatter:on
    }
}
