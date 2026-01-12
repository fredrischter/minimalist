package com.fredrischter.minimalist.resource;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class MoneyTransferResourceTest {

	private static final int port = 4567;
	private static final int OK = 200;
	private static final int NOT_FOUND = 404;

	static {
		new MoneyTransferResource();
	}

	@Test
	public void httpRequestTestGetBalance() {
		given()
			.port(port)
		.when()
			.get("/balance?account=123456")
		.then().statusCode(NOT_FOUND);
	}
	
	@Test
	public void creatingAccount() {
		given()
			.port(port)
		.when()
			.post("/deposit?account=123&amount=1000")
		.then().statusCode(OK);
	}
	
	@Test
	public void transfering() {
		given()
			.port(port)
		.when()
			.post("/deposit?account=111&amount=1000")
		.then().statusCode(OK);

		given()
			.port(port)
		.when()
			.post("/deposit?account=222&amount=1000")
		.then().statusCode(OK);

		given()
			.port(port)
		.when()
			.post("/transfer?originAccount=111&destinationAccount=222&amount=1000")
		.then().statusCode(OK);
	}
	
	@Test
	public void gettingBalance() {
		given()
			.port(port)
		.when()
			.post("/deposit?account=333&amount=1000")
		.then().statusCode(OK);

		given()
			.port(port)
		.when()
			.get("/balance?account=333")
		.then()
			.body(IsEqual.equalTo("1000"));
	}
	
}
