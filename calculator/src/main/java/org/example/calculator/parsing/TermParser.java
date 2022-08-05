package org.example.calculator.parsing;

import org.example.termtree.*;

import java.util.*;

public class TermParser {

    private final static TreeSet<Character> LEGAL_CHAR_SET = ParsingUtils.createLegalCharSet();

    private final static List<Character> MATHEMATICAL_OPERATORS = ParsingUtils.getMathematicalOperators();

    private final String term;

    public TermParser(String term) {
        this.term = term;
    }

    public Node parse() throws MalformedTermException{
        validateCharactersInTerm();
        validateBracketsInTerm();
        return parseTerm(term);
    }

    private Node parseTerm(String term){
        Queue<Character> operatorsQueue = new LinkedList<>();
        for (char character:term.toCharArray()){
            if(MATHEMATICAL_OPERATORS.contains(character))
                operatorsQueue.offer(character);
        }
        if(operatorsQueue.isEmpty()) return new LeafNode(Double.parseDouble(term));

        //Find the operator at which the term needs to be split
        //If any addition or subtraction is in the term, then split it at the first occurrence,
        // otherwise split at first multiplication or division
        Character operatorToSplitAt;

        Optional<Character> firstAdditiveOperator = operatorsQueue.stream().
                filter(character -> character == '+' || character == '-').findFirst();

        operatorToSplitAt = firstAdditiveOperator.orElseGet(operatorsQueue::peek);

        String[] subterms = term.split(String.valueOf(operatorToSplitAt), 1);

        return switch (operatorToSplitAt) {
            case '+' -> new AdditionNode(parseTerm(subterms[0]), parseTerm(subterms[1]));
            case '-' -> new SubstractionNode(parseTerm(subterms[0]), parseTerm(subterms[1]));
            case '*' -> new MultiplicationNode(parseTerm(subterms[0]), parseTerm(subterms[1]));
            case '/' -> new DivisionNode(parseTerm(subterms[0]), parseTerm(subterms[1]));
            default -> throw new RuntimeException("Failed to split at operator " + operatorToSplitAt);
        };
    }

    private void validateBracketsInTerm() throws MalformedTermException{
        int currentlyOpenBrackets = 0;

        for(char character:term.toCharArray()){
            if(character == '('){
                currentlyOpenBrackets++;
            }
            else if(character == ')'){
                currentlyOpenBrackets--;
                if (currentlyOpenBrackets < 0) throw new MalformedTermException("Brackets are not properly nested");
            }
        }
        if(currentlyOpenBrackets != 0) throw new MalformedTermException("The number of opening and closing brackets is not the same");
    }

    private void validateCharactersInTerm() throws MalformedTermException{
        for(char character:term.toCharArray()){
            if(!LEGAL_CHAR_SET.contains(character))
                throw new MalformedTermException("The input term includes the illegal character " + character);
        }
    }

}
