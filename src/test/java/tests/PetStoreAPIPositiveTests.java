package tests;

import io.restassured.response.Response;
import org.petstore.utils.PetStoreApiClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestUtils;

public class PetStoreAPIPositiveTests {

    private PetStoreApiClient apiClient;

    @BeforeClass
    public void setup() {
        apiClient = new PetStoreApiClient();
    }

    @Test(priority = 1)
    public void testCreatePet() {
        String newPet = TestUtils.createPetJson(1234, "Bobby", "available");

        Response response = apiClient.createPet(newPet);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Bobby");
    }

    @Test(priority = 2, dependsOnMethods = "testCreatePet")
    public void testGetPet() {
        Response response = apiClient.getPet(1234);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Bobby");
    }

    @Test(priority = 3, dependsOnMethods = "testGetPet")
    public void testUpdatePet() {
        String updatedPet = TestUtils.createPetJson(1234, "Bobby", "sold");

        Response response = apiClient.updatePet(updatedPet);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("status"), "sold");
    }

    @Test(priority = 4, dependsOnMethods = "testUpdatePet")
    public void testDeletePet() {
        Response response = apiClient.deletePet(1234);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
