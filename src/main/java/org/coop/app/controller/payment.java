package org.coop.app.controller;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coop.app.dto.PaymentRequest;
import org.coop.app.service.PaymentService;
import org.jboss.logging.Logger;

@Path("/api/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class payment {

    private static final Logger LOG = Logger.getLogger(payment.class);

    @Inject
    PaymentService paymentService;

    @POST
    @Path("/process")
    @Blocking
    public Uni<Response> processPayment(@Valid PaymentRequest request) {
        LOG.info("Received payment request for account: " + request.getDebitAccount());

        return paymentService.savePayment(request)
                .onItem().transform(payment -> {
                        return Response.ok(payment).build();
//                    }

                })
                .onFailure().recoverWithItem(error -> {
                    LOG.error("Payment processing error: " + error.getMessage(), error);
                    throw new BadRequestException("Error processing payment");

                });

    }
}



