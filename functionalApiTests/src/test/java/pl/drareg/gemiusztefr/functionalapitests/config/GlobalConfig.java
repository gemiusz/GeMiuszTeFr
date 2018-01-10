package pl.drareg.gemiusztefr.functionalapitests.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import pl.drareg.gemiusztefr.functionalapitests.wiremock.Wiremock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class GlobalConfig {

    public static final Logger logger = Logger.getLogger(GlobalConfig.class.getName());

    protected static RequestSpecification spec;
    protected static RequestSpecification specShowFullReqRes;

    private static Wiremock wiremockObj = new Wiremock();

    @BeforeSuite
    public static void init() {


        wiremockObj.startWireMockServer();

    }


    @BeforeClass
    public static void initSpec() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RestAssuredConfig config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper));

        ObjectMapper objectMapperShowFullReqRes = new ObjectMapper();
        RestAssuredConfig configShowFullReqRes = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapperShowFullReqRes));


        // we want all the details for failed tests
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        spec = new RequestSpecBuilder()
                .setConfig(config)
                .setContentType(ContentType.JSON)
                .setBaseUri(Environment.env.getHost())
                // .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                // .addFilter(new RequestLoggingFilter())
                .build();
        specShowFullReqRes = new RequestSpecBuilder()
                .setConfig(configShowFullReqRes)
                .setContentType(ContentType.JSON)
                .setBaseUri(Environment.env.getHost())
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @AfterSuite
    public static void clean() {

        wiremockObj.stopWireMockServer();

    }

    @AfterMethod
    public void afterMethod(ITestContext ctx, ITestResult result) {
        long testDuration = result.getEndMillis() - result.getStartMillis();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss.SSS").format(new Date());

        System.out.println("[INFO] " + timeStamp + " SuiteName=" + "\"" + ctx.getSuite().getXmlSuite().getName() + "\"" + " SuiteTestName=" + "\"" + ctx.getName() + "\"" + " ClassName=" + "\"" + result.getTestClass().getName() + "\"" + " MethodName=" + "\"" + result.getName() + "\"" + " Status=" + "\"" + result.getStatus() + "\"" + " Duration=" + "\"" + testDuration + "\"");
    }
}
