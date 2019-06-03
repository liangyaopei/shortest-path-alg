package com.lyp.alg.model;

public class HeapNode{
    private int nodeId;
    private int distance;


    public HeapNode(int nodeId, int distance) {
        this.nodeId = nodeId;
        this.distance = distance;
    }



    public int getNodeId() {
        return nodeId;
    }

    public int getDistance() {
        return distance;
    }


    @Override
    public String toString() {
        return String.format("nodeID:%d,distance:%d",nodeId,distance);
    }
}
