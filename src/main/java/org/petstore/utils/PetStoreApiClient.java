package org.petstore.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RequestUtils {

    public static Response createPet(String petJson) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(petJson)
                .post("/pet");
    }

    public static Response getPet(long petId) {
        return RestAssured.given()
                .get("/pet/" + petId);
    }

    public static Response updatePet(String petJson) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(petJson)
                .put("/pet");
    }

    public static Response deletePet(long petId) {
        return RestAssured.given()
                .delete("/pet/" + petId);
    }
}
