package com.yourcast.api.integration;

import lombok.SneakyThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.yourcast.api.AbstractIntegrationTest;

import static com.yourcast.api.util.ResourceUtil.AUTH_REGISTER_REQUEST;
import static com.yourcast.api.util.ResourceUtil.AUTH_REGISTER_RESPONSE;
import static com.yourcast.api.util.RoutingUtil.AUTH_REGISTER_URI;
import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("Integration tests to cover AuthController")
@Sql(
    scripts = {
        "/testcontainers/sql-scripts/truncate_all_tables.sql",
    },
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
public class AuthControllerTest extends AbstractIntegrationTest {

  @Test
  @SneakyThrows
  void successRegisterTest() {
    String getPayrollRecipientsResponse = given()
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .body(AUTH_REGISTER_REQUEST)
        .post(AUTH_REGISTER_URI)
        .then()
        .log().all()
        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .assertThat().statusCode(200)
        .extract()
        .response()
        .body()
        .asString();

    JSONAssert.assertEquals(AUTH_REGISTER_RESPONSE, getPayrollRecipientsResponse, true);
  }
}