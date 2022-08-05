package org.example.calculator.parsing;

import org.example.termtree.*;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class TermParser {

    private final static TreeSet<Character> LEGAL_CHAR_SET = ParsingUtils.createLegalCharSet();

    private final static List<Character> MATHEMATICAL_OPERATORS = ParsingUtils.getMathematicalOperators();

    private String term;

    public TermParser(String term) {
        this.term = term;
    }

    public Node parse() throws MalformedTermException{
        validateCharactersInTerm();
        //validateBracketsInTerm();
        this.term = replaceMinusForPlus(term);
        this.term = replaceDivideForMultiply(term);
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

        Optional<Character> firstPlusOperator = operatorsQueue.stream().
                filter(character -> character == '+').findFirst();

        operatorToSplitAt = firstPlusOperator.orElseGet(operatorsQueue::peek);

        String[] subterms = term.split(getOperatorCharacterStringValue(operatorToSplitAt), 2);

        switch (operatorToSplitAt){
            case '+':
                return new AdditionNode(parseTerm(subterms[0]), parseTerm(subterms[1]));
            case '*':
                return new MultiplicationNode(parseTerm(subterms[0]), parseTerm(subterms[1]));
            default:
                throw new RuntimeException("Failed to split at operator " + operatorToSplitAt);
        }
    }

    /*private void validateBracketsInTerm() throws MalformedTermException{
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
    }*/

    private void validateCharactersInTerm() throws MalformedTermException{
        for(char character:term.toCharArray()){
            if(!LEGAL_CHAR_SET.contains(character))
                throw new MalformedTermException("The input term includes the illegal character " + character);
        }
    }

    private String getOperatorCharacterStringValue(Character operatorCharacter){
        switch (operatorCharacter){
            case '*':
                return "\\*";
            case '+':
                return "\\+";
            case '/':
                return "\\/";
            default:
                throw new RuntimeException("Failed to get corresponding ");
        }
    }

    private String replaceMinusForPlus(String term){
        //Replace '-' with '+-' if '-' is an operator
        TreeSet<Character> numericalCharacters = ParsingUtils.getNumericalCharSet();

        StringBuilder modifiedTerm = new StringBuilder();
        String[] termSplitByMinus = term.split("-");

        //Account for the case that the first character is a minus
        if(term.startsWith("-"))
            termSplitByMinus[0] = "-" + termSplitByMinus[0];

        modifiedTerm.append(termSplitByMinus[0]);

        int pointer = 1;

        while (pointer < termSplitByMinus.length){
            char[] precedingTerm = termSplitByMinus[pointer-1].toCharArray();
            if(numericalCharacters.contains(precedingTerm[precedingTerm.length-1]))
                modifiedTerm.append("+-");
            else
                modifiedTerm.append("-");
            modifiedTerm.append(termSplitByMinus[pointer]);
            pointer++;
        }

        return modifiedTerm.toString();
    }

    private String replaceDivideForMultiply(String term){
        StringBuilder modifiedTerm = new StringBuilder();

        CharacterIterator characterIterator = new StringCharacterIterator(term);

        while (characterIterator.current() != CharacterIterator.DONE){
            if(characterIterator.current() == '/'){
                characterIterator.next();
                modifiedTerm.append("*").append(1 / nextNumber(characterIterator));
            }
            else {
                modifiedTerm.append(characterIterator.current());
                characterIterator.next();
            }
        }

        return modifiedTerm.toString();
    }

    private double nextNumber(CharacterIterator characterIterator){
        TreeSet<Character> numericalCharacters = ParsingUtils.getNumericalCharSet();
        StringBuilder nextNumberStringBuilder = new StringBuilder();
        while (characterIterator.current() != CharacterIterator.DONE){
            if(numericalCharacters.contains(characterIterator.current())){
                nextNumberStringBuilder.append(characterIterator.current());
                characterIterator.next();
            }
            else
                break;
        }
        return Double.parseDouble(nextNumberStringBuilder.toString());
    }

}
