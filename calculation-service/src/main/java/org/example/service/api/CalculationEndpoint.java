package org.example.service.api;

import org.example.calculator.*;
import org.example.calculator.parsing.MalformedTermException;
import org.example.model.CalculationHistoryElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Stack;

@Endpoint
public class CalculationEndpoint {

    private final String NAMESPACE_URI = "http://example.org/calculator";

    private final static Logger logger = LoggerFactory.getLogger(CalculationEndpoint.class);

    private final static Stack<CalculationHistoryElement> calculationHistory = new Stack<>();

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
            logger.error(e.getMessage());
        }

        if(response.getStatus() == 200){
            calculationHistory.add(new CalculationHistoryElement(response.getResult(), calculateResultRequest.getTerm()));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLatestCalculationsRequest")
    @ResponsePayload
    public GetLatestCalculationsResponse getCalculationsHistory(@RequestPayload GetLatestCalculationsRequest calculationsRequest){
        GetLatestCalculationsResponse response = new GetLatestCalculationsResponse();
        StringBuilder returnStringBuilder = new StringBuilder();

        Stack<CalculationHistoryElement> buffer = new Stack<>();

        for(int i = 0; i < calculationsRequest.getHistorySize(); i++){
            if(!calculationHistory.isEmpty()){
                CalculationHistoryElement e = calculationHistory.pop();
                buffer.push(e);
                returnStringBuilder.append(e.toString()).append("\n");
            }
        }
        while (!buffer.isEmpty())
            calculationHistory.add(buffer.pop());

        response.setResult(returnStringBuilder.toString());

        return response;
    }

}
