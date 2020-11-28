package org.tut.micro.villian;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class VillainResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/villain/hello")
          .then()
             .statusCode(200);
    }

}