package org.coop.app.service;

import jakarta.ws.rs.HeaderParam;
import org.coop.app.dto.BulkTransferRequest;
import org.coop.app.dto.BulkTransferResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.concurrent.CompletionStage;

//@RegisterRestClient(configKey = "bulk-transfer-api")
//public interface BulkTransferService {
//
//    @POST
////    @Path("/bulk-transfer")
//    CompletionStage<BulkTransferResponse> processBulkTransfer(
//            @HeaderParam("X-API-KEY") String apiKey,
//            BulkTransferRequest bulkTransferRequest
//    );
//}

@RegisterRestClient(configKey = "bulk-transfer-api")
public interface BulkTransferService {

    @POST
    BulkTransferResponse processBulkTransfer(
            @HeaderParam("X-API-KEY") String apiKey,
            BulkTransferRequest bulkTransferRequest
    );
}

