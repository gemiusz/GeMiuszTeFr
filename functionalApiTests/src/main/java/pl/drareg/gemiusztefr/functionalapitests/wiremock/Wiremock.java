package pl.drareg.gemiusztefr.functionalapitests.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.opentable.extension.BodyTransformer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class Wiremock {
    private WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8181).extensions(new BodyTransformer())); //No-args constructor will start on port 8181, no HTTPS

    public void startWireMockServer() {
        wireMockServer.start();

        wireMockServer.stubFor(
                get(urlEqualTo("/get"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"Message\": \"Hellow API !\"}")
                        .withTransformers("body-transformer")));

        wireMockServer.stubFor(
                post(urlEqualTo("/post"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"Message\": \"Hellow API $(stringToAdd)\"}")
                        .withTransformers("body-transformer")));


        wireMockServer.stubFor(
                post(urlEqualTo("/postAdvanced"))
                        .withHeader("Content-Type",containing("application/json"))
                        .withHeader("Accept", equalTo("application/json"))
                        .withRequestBody(equalToJson("{ \"stringToAdd\" : \"World!\" }"))
                .willReturn(
                        aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"Message\": \"Hellow API $(stringToAdd)\"}")
                        .withTransformers("body-transformer")
                )
        );


    }

    public void stopWireMockServer() {
        wireMockServer.stop();
    }

}
