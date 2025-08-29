package com._BRS1135_q1.bajaj_odd_q1;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WebhookService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void executeWebhookFlow() {
        try {

            WebhookResponse webhookResponse = generateWebhook();


            String finalQuery = solveSqlProblem();


            submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), finalQuery);

        } catch (Exception e) {
            System.err.println("Error in webhook flow: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private WebhookResponse generateWebhook() throws Exception {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";


        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "Vasireddy Yashwanth Kumar");
        requestBody.put("regNo", "22BRS1135");
        requestBody.put("email", "yashwanthkumarvasireddy67@gmail.com");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);


        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        String webhookUrl = jsonResponse.get("webhook").asText();
        String accessToken = jsonResponse.get("accessToken").asText();

        System.out.println("Webhook generated successfully!");
        System.out.println("Webhook URL: " + webhookUrl);

        return new WebhookResponse(webhookUrl, accessToken);
    }

    private String solveSqlProblem() {


        String sqlQuery = """
            SELECT 
                p.AMOUNT as SALARY,
                CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME,
                TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as AGE,
                d.DEPARTMENT_NAME
            FROM PAYMENTS p
            JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
            JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
            WHERE DAY(p.PAYMENT_TIME) != 1
            ORDER BY p.AMOUNT DESC
            LIMIT 1
            """;

        System.out.println("SQL Query generated for Question 1 (odd regNo)");
        System.out.println("Query: " + sqlQuery);

        return sqlQuery;
    }

    private void submitSolution(String webhookUrl, String accessToken, String finalQuery) throws Exception {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("finalQuery", finalQuery);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> response = restTemplate.exchange(webhookUrl, HttpMethod.POST, request, String.class);

        System.out.println("Solution submitted successfully!");
        System.out.println("Response: " + response.getBody());
    }

    // Inner class to hold webhook response data
    private static class WebhookResponse {
        private final String webhook;
        private final String accessToken;

        public WebhookResponse(String webhook, String accessToken) {
            this.webhook = webhook;
            this.accessToken = accessToken;
        }

        public String getWebhook() {
            return webhook;
        }

        public String getAccessToken() {
            return accessToken;
        }
    }
}