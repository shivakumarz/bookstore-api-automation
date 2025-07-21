package base;

import config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import utils.TokenManager;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private RequestSpecification spec;

    @BeforeClass
    public void setup() {
        Response healthResponse = RestAssured.get(Config.getBaseUrl() + "/health");
        boolean skipHealthCheck = Boolean.getBoolean("skip.health.check");

        if (!skipHealthCheck && healthResponse.getStatusCode() != 200) {
            throw new SkipException("Server health check failed. Skipping tests.");
        }

        spec = new RequestSpecBuilder()
                .setBaseUri(Config.getBaseUrl())
                .addHeader("Authorization", "Bearer " + TokenManager.getToken())
                .setContentType("application/json")
                .build();
    }

    public RequestSpecification getSpec() {
        return given().spec(spec);
    }
}
