package com.demo.airport.it;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirportServiceApplicationIT {
	@LocalServerPort
	int serverPort;

	@BeforeEach
	void setUpRestAssured() {
		RestAssured.port = serverPort;
		RestAssured.config = RestAssuredConfig.newConfig()
			.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
	}

	@Test
	@DisplayName("Test 401 Authentication failed when auth details not provided")
	void test_401_Unauthorized() {

		Response response = given()
			//.auth().basic("app-user1", "user-pass1")
		.when()
			.get("/api/countries-by-name?search=india")
			.andReturn();
		assertThat(response.statusCode()).isEqualTo(401);
	}

	@Test
	@DisplayName("Test 403 Forbidden with wrong auth details")
	void test_403_Forbidden() {

		Response response = given()
			.auth().basic("app-user1", "user-pass1")
			.when()
			.get("/api/countries-by-name?search=india")
			.andReturn();
		assertThat(response.statusCode()).isEqualTo(403);
	}

	@ParameterizedTest
	@CsvSource({
		"/api/countries-by-name?search=ind, test-data/countries-by-name.json",
		"/api/runways-by-country?search=IN, test-data/runways-by-country-code.json",
		"/api/runways-by-country?search=netherlands, test-data/runways-by-country-name.json",
		"/api/countries-with-max-airports?maxResults=2, test-data/countries-with-max-airports.json"
	})
	void test_200_Success(String apiUrl, String fileName) throws JSONException, IOException {

		Response response = given()
			.auth().basic("app-user2", "user-pass2")
			.when()
			.get(apiUrl)
			.andReturn();
		assertThat(response.statusCode()).isEqualTo(200);
		JSONAssert.assertEquals(
			loadFileContents(fileName),
			response.getBody().asString(),
			true
		);
	}

	@Test
	@DisplayName("Test 404 for get runways by wrong country")
	void test_runwaysByCountry_404_NotFound() {

		Response response = given()
			.auth().basic("app-user2", "user-pass2")
			.when()
			.get("/api/runways-by-country?search=wrong")
			.andReturn();
		assertThat(response.statusCode()).isEqualTo(404);
	}

	@Test
	@DisplayName("Test 200 for get countries by partial name not present returns empty list")
	void test_countriesByName_200_Success_EmptyList() throws JSONException {

		Response response = given()
			.auth().basic("app-user2", "user-pass2")
			.when()
			.get("/api/countries-by-name?search=wrong")
			.andReturn();
		assertThat(response.statusCode()).isEqualTo(200);
		JSONAssert.assertEquals(
			"[]",
			response.getBody().asString(),
			true
		);
	}


	private String loadFileContents(String fileName) throws IOException {
		return StreamUtils.copyToString(
			new ClassPathResource(fileName).getInputStream(), Charset.defaultCharset()
		);
	}

}
