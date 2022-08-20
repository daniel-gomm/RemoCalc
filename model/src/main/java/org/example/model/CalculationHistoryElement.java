package org.example.model;

public class CalculationHistoryElement {

    private double result;
    private String term;

    public CalculationHistoryElement(double result, String term) {
        this.result = result;
        this.term = term;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    @Override
    public String toString() {
        return term + " | " + result;
    }
}
