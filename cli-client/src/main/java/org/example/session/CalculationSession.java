package org.example.session;

import org.example.cliclient.wsdl.CalculateResultResponse;
import org.example.cliclient.wsdl.GetLatestCalculationsResponse;
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

        BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        try {
            do{
                input = stdInReader.readLine();
            }while (handleInput(input));
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public boolean handleInput(String input){
        if(input.equals("exit"))
            return false;
        if(input.startsWith("history"))
            handleHistoryRequest(input);
        else
            handleCalculation(input);
        return true;
    }

    private void handleCalculation(String term){
        try {
            CalculateResultResponse response = calculatorClient.calculateResult(term);
            if(response.getStatus() != 200)
                System.out.println("An exception occurred while requesting the result for the provided term:\n"
                        + response.getStatusMessage());
            else
                System.out.println("Result: " + response.getResult());
        } catch (Exception e){
            System.out.println("There was an unexpected error. Please check if your input is correct and try again.");
        }
    }

    private void handleHistoryRequest(String input){
        String[] splitArgs= input.split(" ");
        if(splitArgs.length != 2){
            System.out.println("Please provide an appropriate argument for the history command!");
            return;
        }
        int historySize = Integer.parseInt(splitArgs[1]);
        try {
            GetLatestCalculationsResponse response = calculatorClient.getLatestCalculations(historySize);
            System.out.println("History:\n" + response.getResult().substring(0, response.getResult().length()-1));
        }catch (Exception e){
            System.out.println("There was an unexpected error. Please check if your input is correct and try again.");
        }
    }

}
