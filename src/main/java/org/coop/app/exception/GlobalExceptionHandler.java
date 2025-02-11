//package org.coop.app.exception;
//
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.ExceptionMapper;
//import jakarta.ws.rs.ext.Provider;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Provider
//@Slf4j
//public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
//
//    @Override
//    public Response toResponse(Exception e) {
//        if (e instanceof BadRequestException) {
//            return handleBadRequestException((BadRequestException) e);
//        } else if (e instanceof ConstraintViolationException) {
//            return handleConstraintViolationException((ConstraintViolationException) e);
//        }
//
//        log.error("Unhandled exception: {}", e.getMessage(), e);
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                .entity(new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage()))
//                .type(MediaType.APPLICATION_JSON)
//                .build();
//    }
//
//    private Response handleBadRequestException(BadRequestException e) {
//        log.warn("Bad request: {}", e.getMessage());
//        return Response.status(Response.Status.BAD_REQUEST)
//                .entity(new ErrorResponse("BAD_REQUEST", e.getMessage()))
//                .type(MediaType.APPLICATION_JSON)
//                .build();
//    }
//
//    private Response handleConstraintViolationException(ConstraintViolationException e) {
//        List<String> errors = e.getConstraintViolations()
//                .stream()
//                .map(ConstraintViolation::getMessage)
//                .collect(Collectors.toList());
//
//        log.warn("Validation failed: {}", errors);
//        return Response.status(Response.Status.BAD_REQUEST)
//                .entity(new ErrorResponse("VALIDATION_ERROR", String.join(", ", errors)))
//                .type(MediaType.APPLICATION_JSON)
//                .build();
//    }
//}
//
//
