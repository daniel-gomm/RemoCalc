package org.example.termtree;

public class MultiplicationNode extends CalculationNode {

    public MultiplicationNode(Node rightChild, Node leftChild) {
        this.setRightChild(rightChild);
        this.setLeftChild(leftChild);
    }

    @Override
    public double value() {
        return getLeftChild().value() * getRightChild().value();
    }
}
