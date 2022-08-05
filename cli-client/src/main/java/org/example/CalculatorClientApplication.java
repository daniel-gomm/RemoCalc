package org.example;

import org.example.gateway.CalculatorClient;
import org.example.session.CalculationSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalculatorClientApplication {

    private final static Logger logger = LoggerFactory.getLogger(CalculatorClientApplication.class);

    public static void main(String[] args){
        SpringApplication.run(CalculatorClientApplication.class, args);
    }

    @Bean
    CommandLineRunner calculate(CalculatorClient calculatorClient){
        return args -> {
            if(args.length > 1){
                logger.error("Please provide exactly one term as argument");
                return;
            }
            CalculationSession calculationSession = new CalculationSession(calculatorClient);
            if(args.length == 0)
                calculationSession.runCalculationSession();
            else
                calculationSession.handleCalculation(args[0]);
        };
    }
}
