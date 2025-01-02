package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.petstore.utils.PetStoreApiClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PetStoreAPINegativeTests {

    private PetStoreApiClient apiClient;

    @BeforeClass
    public void setup() {
        apiClient = new PetStoreApiClient();
    }

    @Test(priority = 1)
    public void testCreatePetWithoutName() {
        // name alanı eksik
        String invalidPet = "{\"id\":1234, \"status\": \"available\"}";

        Response response = apiClient.createPet(invalidPet);
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
    }

    @Test(priority = 2)
    public void testCreatePetWithInvalidIdType() {
        String invalidPet = "{\"id\":\"invalid_id\",\"name\":\"Bobby\",\"status\":\"available\"}";

        Response response = apiClient.createPet(invalidPet);
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
    }

    @Test(priority = 3)
    public void testCreatePetWithInvalidJsonFormat() {
        // Yanlış formatlı JSON verisi kullanıyoruz
        String invalidPet = "{\"id\":1234 \"name\": \"Bobby\", \"status\": \"available\"}"; // Virgül eksik

        Response response = apiClient.createPet(invalidPet);
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
    }

    @Test(priority = 4)
    public void testInvalidEndpoint() {
        Response response = apiClient.getPet(9999); // Mevcut olmayan bir ID

        Assert.assertEquals(response.getStatusCode(), 404, "Beklenen hata kodu 404 olmalıydı.");
    }

    @Test(priority = 5)
    public void testInvalidHttpMethod() {
        Response response = RestAssured.given()
                .post("/pet/1234"); // POST yerine GET olmalı

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        boolean isExpected = (statusCode == 405) || (statusCode == 200 && responseBody.contains("\"message\":\"1234\""));
        Assert.assertTrue(isExpected, "Beklenen hata kodu 405 olmalıydı veya yanıt gövdesinde '1234' mesajı bulunmalıydı.");
    }
}
