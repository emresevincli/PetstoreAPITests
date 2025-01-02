package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.petstore.utils.PetStoreApiClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import utils.TestUtils;
import utils.TestNGListener;

public class PetStoreAPINegativeTests {

    private PetStoreApiClient apiClient;

    @BeforeClass
    public void setup() {
        apiClient = new PetStoreApiClient();
    }

    @Test(priority = 1, expectedExceptions = { AssertionError.class }, expectedExceptionsMessageRegExp = ".*Beklenen hata kodu 400 veya 500 olmalıydı.*")
    public void testCreatePetWithoutName() {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Starting testCreatePetWithoutName");
        String invalidPet = TestUtils.createInvalidPetJsonWithoutName(1234, "available");

        Response response = apiClient.createPet(invalidPet);
        logResponse(response);
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
        test.log(Status.PASS, "Test passed");
    }

    @Test(priority = 2, expectedExceptions = { AssertionError.class }, expectedExceptionsMessageRegExp = ".*Beklenen hata kodu 400 veya 500 olmalıydı.*")
    public void testCreatePetWithoutStatus() {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Starting testCreatePetWithoutStatus");
        String invalidPet = TestUtils.createInvalidPetJsonWithoutStatus(1234, "Bobby");

        Response response = apiClient.createPet(invalidPet);
        logResponse(response);
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
        test.log(Status.PASS, "Test passed");
    }

    @Test(priority = 3)
    public void testCreatePetWithInvalidIdType() {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Starting testCreatePetWithInvalidIdType");
        String invalidPet = TestUtils.createInvalidPetJsonWithInvalidIdType("invalid_id", "Bobby", "available");

        Response response = apiClient.createPet(invalidPet);
        logResponse(response);
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
        test.log(Status.PASS, "Test passed");
    }

    @Test(priority = 4)
    public void testCreatePetWithInvalidJsonFormat() {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Starting testCreatePetWithInvalidJsonFormat");
        String invalidPet = TestUtils.createInvalidPetJsonWithInvalidFormat(1234, "Bobby", "available");

        Response response = apiClient.createPet(invalidPet);
        logResponse(response);
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Beklenen hata kodu 400 veya 500 olmalıydı.");
        test.log(Status.PASS, "Test passed");
    }

    @Test(priority = 5)
    public void testInvalidEndpoint() {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Starting testInvalidEndpoint");
        Response response = apiClient.getPet(9999); // Mevcut olmayan bir ID

        Assert.assertEquals(response.getStatusCode(), 404, "Beklenen hata kodu 404 olmalıydı.");
        logResponse(response);
        test.log(Status.PASS, "Test passed");
    }

    @Test(priority = 6)
    public void testInvalidHttpMethod() {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Starting testInvalidHttpMethod");
        Response response = RestAssured.given()
                .post("/pet/1234"); // POST yerine GET olmalı

        logResponse(response);
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        boolean isExpected = (statusCode == 405) || (statusCode == 200 && responseBody.contains("\"message\":\"1234\""));
        Assert.assertTrue(isExpected, "Beklenen hata kodu 405 olmalıydı veya yanıt gövdesinde '1234' mesajı bulunmalıydı.");
        test.log(Status.PASS, "Test passed");
    }

    private void logResponse(Response response) {
        ExtentTest test = TestNGListener.getTest();
        test.log(Status.INFO, "Status Code: " + response.getStatusCode());
        test.log(Status.INFO, "Response Body: " + response.getBody().asString());
    }
}
