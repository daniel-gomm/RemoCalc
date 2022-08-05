package org.example.service.api;


import org.example.calculator.CalculateResultRequest;
import org.example.calculator.CalculateResultResponse;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

public class CalculationEndpoint {

    private final String NAMESPACE_URI = "http://example.org/calculator";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculateResultRequest")
    @ResponsePayload
    public CalculateResultResponse calculateResult(CalculateResultRequest calculateResultRequest){

        CalculateResultResponse response = new CalculateResultResponse();



        return null;
    }

}
