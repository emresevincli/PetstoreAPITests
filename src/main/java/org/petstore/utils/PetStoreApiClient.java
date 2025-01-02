package org.petstore.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PetStoreApiClient {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";

    public PetStoreApiClient() {
        RestAssured.baseURI = BASE_URI;
    }

    public Response createPet(String petJson) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(petJson)
                .post("/pet");
    }

    public Response getPet(long petId) {
        return RestAssured.given()
                .get("/pet/" + petId);
    }

    public Response updatePet(String petJson) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(petJson)
                .put("/pet");
    }

    public Response deletePet(long petId) {
        return RestAssured.given()
                .delete("/pet/" + petId);
    }
}
