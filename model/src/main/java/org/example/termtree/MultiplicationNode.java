package org.example.termtree;

public class MultiplicationNode extends CalculationNode {

    public MultiplicationNode(Node leftChild, Node rightChild) {
        this.setRightChild(rightChild);
        this.setLeftChild(leftChild);
    }

    @Override
    public double value() {
        return getLeftChild().value() * getRightChild().value();
    }
}
