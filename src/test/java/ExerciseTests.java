import io.restassured.response.Response;
import org.core.TestDataGenerator;
import org.core.builders.LoginRequest;
import org.core.builders.RegisterRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.core.Endpoints.LOGIN_URL;
import static org.core.Endpoints.REGISTRATION_URL;
import static org.core.BaseRequest.postRequest;


public class ExerciseTests {
    private String username;
    private String password;
    private String externalId;
    SoftAssert softAssert;

    @BeforeClass
    public void generateNewUserData() {
        username = TestDataGenerator.generateUniqueUsername();
        password = "password_SlLVWDLl";
        externalId = TestDataGenerator.generateUniqueExternalId();
        softAssert = new SoftAssert();
    }

    @Test
    public void registration() {
        RegisterRequest.User user = RegisterRequest.User.builder()
                .firstName("ott_user_lWkiwzTJJGYI")
                .lastName("1585130417330")
                .address("ott_user_lWkiwzTJJGYI fake address")
                .city("ott_user_lWkiwzTJJGYI fake city")
                .externalId(externalId)
                .email("QATest_1585130417320@mailinator.com")
                .countryId(5)
                .objectType("KalturaOTTUser")
                .username(username)
                .build();

        RegisterRequest requestBody = RegisterRequest.builder()
                .password(password)
                .apiVersion("6.0.0")
                .partnerId(3197)
                .user(user)
                .build();

        Response response = postRequest(REGISTRATION_URL, requestBody);

        softAssert.assertEquals(response.getHeader("Content-Type"), "application/json");
        softAssert.assertTrue(response.getBody().jsonPath().get("result.id") instanceof String);
        softAssert.assertTrue(response.getBody().jsonPath().get("result.countryId") instanceof Integer);
        softAssert.assertAll();


    }

    @Test(dependsOnMethods = "registration")
    public void login() {
        LoginRequest requestBody = LoginRequest.builder()
                .apiVersion("6.0.0")
                .partnerId(3197)
                .username(username)
                .password(password)
                .extraParams(null)
                .build();

        Response response = postRequest(LOGIN_URL, requestBody);

        Assert.assertNotNull(response.getBody().jsonPath().get("result.user.lastLoginDate"));

        Integer lastLoginTimestamp = response.getBody().jsonPath().get("result.user.lastLoginDate");
        Date lastLoginDate = new Date(lastLoginTimestamp * 1000); // Assuming the timestamp is in seconds

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedLastLoginDate = dateFormat.format(lastLoginDate);
        System.out.println("lastLoginDate: " + formattedLastLoginDate);
    }

    @Test(dependsOnMethods = "registration")
    public void registerAlreadyRegistered() {
        RegisterRequest.User user = RegisterRequest.User.builder()
                .firstName("ott_user_lWkiwzTJJGYI")
                .lastName("1585130417330")
                .address("ott_user_lWkiwzTJJGYI fake address")
                .city("ott_user_lWkiwzTJJGYI fake city")
                .externalId(externalId)
                .email("QATest_1585130417320@mailinator.com")
                .countryId(5)
                .objectType("KalturaOTTUser")
                .username(username)
                .build();

        RegisterRequest requestBody = RegisterRequest.builder()
                .password(password)
                .apiVersion("6.0.0")
                .partnerId(3197)
                .user(user)
                .build();

        Response response = postRequest(REGISTRATION_URL, requestBody);

        String responseBody = response.getBody().asString();
        if (responseBody.contains("error")) {
            System.out.println("Error occurred: " + responseBody);
        }
    }


}
