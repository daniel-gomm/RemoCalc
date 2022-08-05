package org.example.service.api;


import org.example.calculator.CalculateResultRequest;
import org.example.calculator.CalculateResultResponse;
import org.example.calculator.Operations;
import org.example.calculator.parsing.MalformedTermException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CalculationEndpoint {

    private final String NAMESPACE_URI = "http://example.org/calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculateResultRequest")
    @ResponsePayload
    public CalculateResultResponse calculateResult(@RequestPayload CalculateResultRequest calculateResultRequest){

        CalculateResultResponse response = new CalculateResultResponse();

        try {
            double result = Operations.calculateTerm(calculateResultRequest.getTerm());
            response.setResult(result);
            response.setStatus(200);
            response.setStatusMessage("success");
        } catch (MalformedTermException e) {
            response.setStatus(500);
            response.setStatusMessage(e.getMessage());
        }

        return response;
    }

}
