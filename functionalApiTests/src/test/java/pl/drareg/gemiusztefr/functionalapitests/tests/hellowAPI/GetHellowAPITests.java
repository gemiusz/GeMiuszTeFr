package pl.drareg.gemiusztefr.functionalapitests.tests.hellowAPI;

import org.testng.annotations.Test;
import pl.drareg.gemiusztefr.functionalapitests.config.GlobalConfig;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;



public class GetHellowAPITests extends GlobalConfig {
    @Test
    public void getHellowAPIShowFullReqRes_checkStatus200() {

        // @formatter:off
        given()
                .spec(specShowFullReqRes)
                .contentType("application/json")
                .accept("application/json")
        .when()
                .get("/get")
        .then()
                .assertThat()
                  .statusCode(200);
        // @formatter:on
    }

    @Test
    public void getHellowAPI_checkStatus200andResponseMessage() {
        // @formatter:off
        given()
                .spec(specShowFullReqRes)
        .when()
                .contentType("application/json")
                .accept("application/json")
                .get("/get")
        .then()
                .assertThat()
                    .statusCode(200)
                .and()
                    .body("Message", equalTo("Hellow API !"));
        // @formatter:on
    }
    @Test
    public void getHellowAPI_checkStatus200andResponseMessageAndSchema() {
        // @formatter:off
        given()
                .spec(specShowFullReqRes)
        .when()
                .contentType("application/json")
                .accept("application/json")
                .get("/get")
        .then()
                .assertThat()
                    .statusCode(200)
                .and()
                    .body("Message", equalTo("Hellow API !"))
                .and()
                    .body(matchesJsonSchemaInClasspath("HellowAPI-ResponseSchema.json"));
        // @formatter:on
    }
}
