package org.example;

import org.example.gateway.CalculatorClient;
import org.example.session.CalculationSession;
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
            if(args.length > 1){
                printUsage();
                return;
            }
            CalculationSession calculationSession = new CalculationSession(calculatorClient);
            if(args.length == 0)
                calculationSession.runCalculationSession();
            else
                calculationSession.handleInput(args[0]);
        };
    }

    public static void printUsage(){
        System.out.println("The arguments you provided when calling this application are not supported.\n" +
                "You can either run the application without arguments to start a live calculation session or run it with " +
                "exactly one calculation as argument. Make sure to enclose the calculation in quotation marks, e.g.: \n" +
                "\"5*9+7*-3\"");
    }
}
