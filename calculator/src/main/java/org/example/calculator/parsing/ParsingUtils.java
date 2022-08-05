package org.example.calculator.parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class ParsingUtils {

    public static TreeSet<Character> createLegalCharSet(){
        TreeSet<Character> legalCharSet = getNumericalCharSet();
        Character[] itemsToInclude = {'*', '+', '/'};
        Collections.addAll(legalCharSet, itemsToInclude);
        return legalCharSet;
    }

    public static List<Character> getMathematicalOperators(){
        List<Character> operators = new ArrayList<>();
        Character[] itemsToInclude = {'+', '*', '/'};
        Collections.addAll(operators, itemsToInclude);
        return operators;
    }

    public static TreeSet<Character> getNumericalCharSet(){
        TreeSet<Character> numericalCharacters = new TreeSet<>();
        Character[] itemsToInclude = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.', '-'};
        Collections.addAll(numericalCharacters, itemsToInclude);
        return numericalCharacters;
    }

}
