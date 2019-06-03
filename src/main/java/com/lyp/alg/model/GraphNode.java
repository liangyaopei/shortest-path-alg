package com.lyp.alg.model;

public class GraphNode {
    private int to;
    private int weight;

    public GraphNode(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("to = %d,weight = %d",to,weight);
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
