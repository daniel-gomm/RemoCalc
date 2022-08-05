package org.example.termtree;

public class SubstractionNode extends CalculationNode{

    public SubstractionNode(Node rightChild, Node leftChild) {
        this.setRightChild(rightChild);
        this.setLeftChild(leftChild);
    }

    @Override
    public double value() {
        return getLeftChild().value() - getRightChild().value();
    }
}
