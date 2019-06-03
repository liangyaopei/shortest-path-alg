package com.lyp.alg.model;

public class PathStatistics {
    private int from;
    private int to;
    private int distance;
    private String path;
    private int hop;

    public PathStatistics(int from, int to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public PathStatistics(int from, int to, int hop, int distance, String path) {
        this.from = from;
        this.to = to;
        this.hop = hop;
        this.distance = distance;
        this.path = path;
    }



    public int getDistance() {
        return distance;
    }

    public String getPath() {
        return path;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getHop() {
        return hop;
    }
}
