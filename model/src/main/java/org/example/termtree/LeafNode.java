package org.example.termtree;

public final class LeafNode implements Node {
    private final double value;

    public LeafNode(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}
