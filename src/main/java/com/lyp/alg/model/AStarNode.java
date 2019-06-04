package com.lyp.alg.model;

public class AStarNode {
    private int nodeId;
    // g
    private int actualDistance;
    // h
    private int heuristic;
    // f = g + h
    private int combinedDistance;

    public AStarNode(int nodeId) {
        this.nodeId = nodeId;
    }

    public AStarNode(int nodeId, int actualDistance, int heuristic, int combinedDistance) {
        this.nodeId = nodeId;
        this.actualDistance = actualDistance;
        this.heuristic = heuristic;
        this.combinedDistance = combinedDistance;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getActualDistance() {
        return actualDistance;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getCombinedDistance() {
        return combinedDistance;
    }

    @Override
    public String toString() {
        return String.format("nodeId=%d,g=%d,h=%d,f=%d",
                nodeId,actualDistance,heuristic,combinedDistance);
    }

    public void setActualDistance(int actualDistance) {
        this.actualDistance = actualDistance;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public void setCombinedDistance(int combinedDistance) {
        this.combinedDistance = combinedDistance;
    }
}
