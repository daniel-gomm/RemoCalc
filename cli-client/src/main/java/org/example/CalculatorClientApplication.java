package org.example;

import org.example.cliclient.wsdl.CalculateResultResponse;
import org.example.gateway.CalculatorClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalculatorClientApplication {

    public static void main(String[] args){
        SpringApplication.run(CalculatorClientApplication.class, args);
    }

    @Bean
    CommandLineRunner calculate(CalculatorClient calculatorClient){
        return args -> {
            if(args.length != 1){
                System.out.println("Please provide exactly one term as argument");
                return;
            }
            CalculateResultResponse response = calculatorClient.calculateResult(args[0]);
            if(response.getStatus() != 200)
                System.out.println("An exception occurred while requesting the result for the provided term:\n"
                        + response.getStatusMessage());
            else
                System.out.println("Result: " + response.getResult());
        };
    }
}
