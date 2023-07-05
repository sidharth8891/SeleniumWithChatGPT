package Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
public class TestRailIntegration {

    // TestRail API endpoint URLs
    private static final String TESTRAIL_URL = "https://your-testrail-instance-url.com/";
    private static final String API_ADD_RESULT_URL = TESTRAIL_URL + "index.php?/api/v2/add_result_for_case/";

    // TestRail API credentials
    private static final String TESTRAIL_USERNAME = "your-testrail-username";
    private static final String TESTRAIL_PASSWORD = "your-testrail-password";

    // TestRail project and test suite details
    private static final int PROJECT_ID = 1;
    private static final int TEST_SUITE_ID = 1;

    public static void main(String[] args) {
        // Example usage
        updateTestRailStatus(1234, "PASSED");
    }

    public static void updateTestRailStatus(int testCaseId, String status) {
        try {
            // Create the API endpoint URL for updating the test case result
            String apiUrl = API_ADD_RESULT_URL + testCaseId;

            // Create the HTTP client
            HttpClient client = HttpClientBuilder.create().build();

            // Create the HTTP POST request
            HttpPost request = new HttpPost(new URI(apiUrl));
            request.addHeader("Content-Type", "application/json");

            // Set the TestRail credentials
            String credentials = TESTRAIL_USERNAME + ":" + TESTRAIL_PASSWORD;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            request.setHeader("Authorization", "Basic " + encodedCredentials);

            // Prepare the test case result data
            JSONObject testResult = new JSONObject();
            testResult.put("status_id", getStatusId(status));

            JSONArray testResults = new JSONArray();
            testResults.put(testResult);

            JSONObject requestBody = new JSONObject();
            requestBody.put("results", testResults);

            // Set the request body
            StringEntity requestBodyEntity = new StringEntity(requestBody.toString(), ContentType.APPLICATION_JSON);
            request.setEntity(requestBodyEntity);

            // Send the HTTP POST request
            HttpResponse response = client.execute(request);

            // Check the response status code
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                System.out.println("TestRail update successful");
            } else {
                System.err.println("TestRail update failed. Status code: " + statusCode);
            }
        } catch (URISyntaxException | MalformedURLException e) {
            System.err.println("Invalid TestRail URL: " + TESTRAIL_URL);
        } catch (IOException e) {
            System.err.println("Failed to update TestRail status: " + e.getMessage());
        }
    }

    private static int getStatusId(String status) {
        // Map your desired status strings to TestRail status IDs
        Map<String, Integer> statusMap = new HashMap<>();
        statusMap.put("PASSED", 1);
        statusMap.put("BLOCKED", 2);
        statusMap.put("UNTESTED", 3);
        statusMap.put("RETEST", 4);
        statusMap.put("FAILED", 5);

        return statusMap.getOrDefault(status, 3); // Default to "Untested" status if not found
    }
}
