package org.coop.app.client;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.coop.app.dto.CustomerInfoRequest;
import org.coop.app.dto.CustomerInfoResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(baseUri = "http://10.1.230.6:7081/v1/cbo/")
public interface ESBClient {

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    String fetchCustomer(String request);
}
