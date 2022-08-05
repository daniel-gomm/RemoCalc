package org.example.termtree;

public class AdditionNode extends CalculationNode{

    public AdditionNode(Node leftChild, Node rightChild) {
        this.setRightChild(rightChild);
        this.setLeftChild(leftChild);
    }

    @Override
    public double value() {
        return getLeftChild().value() + getRightChild().value();
    }
}
