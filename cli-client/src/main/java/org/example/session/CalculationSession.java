package org.example.session;

import org.example.cliclient.wsdl.CalculateResultResponse;
import org.example.gateway.CalculatorClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculationSession {

    private final CalculatorClient calculatorClient;

    public CalculationSession(CalculatorClient calculatorClient) {
        this.calculatorClient = calculatorClient;
    }

    public void runCalculationSession(){
        System.out.println("This is a live calculation session. Please input your calculation. You can use the following" +
                " operators and symbols for your calculation: \"+-*/\"\nTo exit this session enter \"exit\" as argument");

        boolean sessionRunning = true;

        BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
        while (sessionRunning){
            String input;
            try{
                input = stdInReader.readLine();
                if(input.equals("exit"))
                    sessionRunning = false;
                else
                    handleCalculation(input);
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
        }
    }

    public void handleCalculation(String term){
        CalculateResultResponse response = calculatorClient.calculateResult(term);
        if(response.getStatus() != 200)
            System.out.println("An exception occurred while requesting the result for the provided term:\n"
                    + response.getStatusMessage());
        else
            System.out.println("Result: " + response.getResult());
    }

}
