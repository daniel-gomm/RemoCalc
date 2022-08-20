package org.example.gateway;

import org.example.cliclient.wsdl.CalculateResultRequest;
import org.example.cliclient.wsdl.CalculateResultResponse;
import org.example.cliclient.wsdl.GetLatestCalculationsRequest;
import org.example.cliclient.wsdl.GetLatestCalculationsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CalculatorClient extends WebServiceGatewaySupport {


    public CalculateResultResponse calculateResult(String term){
        CalculateResultRequest request = new CalculateResultRequest();
        request.setTerm(term);
        //final SoapActionCallback soapActionCallback = new SoapActionCallback("http://example.org/calculator/CalculateResultRequest");

        return (CalculateResultResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/calculator", request,
                        new SoapActionCallback("http://example.org/calculator/CalculateResultRequest"));
    }

    public GetLatestCalculationsResponse getLatestCalculations(int historySize){
        GetLatestCalculationsRequest request = new GetLatestCalculationsRequest();
        request.setHistorySize(historySize);

        return (GetLatestCalculationsResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/calculator", request,
                        new SoapActionCallback("http://example.org/calculator/GetLatestCalculationsRequest"));
    }

}
