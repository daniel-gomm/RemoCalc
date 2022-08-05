package org.example.calculator;

import org.example.calculator.parsing.MalformedTermException;
import org.example.calculator.parsing.TermParser;
import org.example.termtree.Node;

public class Operations {

    public static double calculateTerm(String calculationTerm) throws MalformedTermException {
        TermParser parser = new TermParser(calculationTerm);
        Node termTreeRootNode = parser.parse();
        return termTreeRootNode.value();
    }

}
