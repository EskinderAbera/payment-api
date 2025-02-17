package org.coop.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.coop.app.client.ESBClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Inject
    @RestClient
    ESBClient esbClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Map<String, Object>> fetchCustomerFromCore(String accountNo) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> customerInfoRequest = new HashMap<>();
        Map<String, Object> esbHeader = new HashMap<>();
        Map<String, Object> customerInfo = new HashMap<>();

        String messageId = UUID.randomUUID().toString();

        esbHeader.put("serviceCode", "040000");
        esbHeader.put("channel", "USSD");
        esbHeader.put("Service_name", "customerInfo");
        esbHeader.put("Message_Id", messageId);

        customerInfo.put("AccountId", accountNo);

        customerInfoRequest.put("ESBHeader", esbHeader);
        customerInfoRequest.put("CusomerInfo", customerInfo);
        requestBody.put("CustomerInfoRequest", customerInfoRequest);

        String request = objectMapper.writeValueAsString(requestBody);

        try {
            String response = esbClient.fetchCustomer(request);
            JsonNode responseJson = objectMapper.readTree(response);
            JsonNode esbStatus = responseJson.path("CustomerInfoResponse").path("ESBStatus");
            String status = esbStatus.path("Status").asText();
            String responseCode = esbStatus.path("responseCode").asText();

            if ("Success".equalsIgnoreCase(status) && "ESB_BRK_success".equalsIgnoreCase(responseCode)) {
                JsonNode customerInfoNode = responseJson.path("CustomerInfoResponse").path("CustomerInfo");
                if (!customerInfoNode.isMissingNode() && customerInfoNode.isArray() && !customerInfoNode.isEmpty()) {
                    JsonNode customer = customerInfoNode.get(0);

                    Map<String, Object> result = new HashMap<>();
                    result.put("availableBalance", customer.path("availableBalance").asDouble());
                    result.put("customerName", customer.path("customerName").asText());

                    return Optional.of(result);
                }
            }
            return Optional.empty();
        } catch (JsonProcessingException e) {
            Log.error("Error fetching customer info", e);
            return Optional.empty();
        }
    }
}