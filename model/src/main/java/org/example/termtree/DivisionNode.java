package org.example.termtree;

public class DivisionNode extends CalculationNode {

    public DivisionNode(Node rightChild, Node leftChild) {
        this.setRightChild(rightChild);
        this.setLeftChild(leftChild);
    }

    @Override
    public double value() {
        return getLeftChild().value() / getRightChild().value();
    }
}
