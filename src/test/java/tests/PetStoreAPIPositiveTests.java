package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.petstore.utils.PetStoreApiClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import utils.TestUtils;
import utils.TestNGListener;

public class PetStoreAPIPositiveTests {

    private PetStoreApiClient apiClient;

    @BeforeClass
    public void setup() {
        apiClient = new PetStoreApiClient();
    }

    @Test(priority = 1)
    public void testCreatePet() {
        ExtentTest test = TestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, "Starting testCreatePet");
        }
        String newPet = TestUtils.createPetJson(1234, "Bobby", "available");

        Response response = apiClient.createPet(newPet);
        logResponse(response);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Bobby");
        if (test != null) {
            test.log(Status.PASS, "Test passed");
        }
    }

    @Test(priority = 2, dependsOnMethods = "testCreatePet")
    public void testGetPet() {
        ExtentTest test = TestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, "Starting testGetPet");
        }
        Response response = apiClient.getPet(1234);
        logResponse(response);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Bobby");
        if (test != null) {
            test.log(Status.PASS, "Test passed");
        }
    }

    @Test(priority = 3, dependsOnMethods = "testGetPet")
    public void testUpdatePet() {
        ExtentTest test = TestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, "Starting testUpdatePet");
        }
        String updatedPet = TestUtils.createPetJson(1234, "Bobby", "sold");

        Response response = apiClient.updatePet(updatedPet);
        logResponse(response);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("status"), "sold");
        if (test != null) {
            test.log(Status.PASS, "Test passed");
        }
    }

    @Test(priority = 4, dependsOnMethods = "testUpdatePet")
    public void testDeletePet() {
        ExtentTest test = TestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, "Starting testDeletePet");
        }
        Response response = apiClient.deletePet(1234);
        logResponse(response);
        Assert.assertEquals(response.getStatusCode(), 200);
        if (test != null) {
            test.log(Status.PASS, "Test passed");
        }
    }

    private void logResponse(Response response) {
        ExtentTest test = TestNGListener.getTest();
        if (test != null) {
            test.log(Status.INFO, "Status Code: " + response.getStatusCode());
            test.log(Status.INFO, "Response Body: " + response.getBody().asString());
        }
    }
}
