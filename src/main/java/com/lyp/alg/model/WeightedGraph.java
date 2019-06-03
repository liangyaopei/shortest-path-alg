package com.lyp.alg.model;


import java.util.HashMap;
import java.util.Map;

public class WeightedGraph {
    private Map<Integer,Map<Integer,Integer>> graph;
    private Map<Integer,Map<Integer,Integer>> reverseGraph;
    private int edgeCount;

    public WeightedGraph(){
        this.graph = new HashMap<>();
        reverseGraph = new HashMap<>();
        this.edgeCount = 0;
    }

    /**
     * Put a weight edge into graph
     * @param from
     * @param to
     * @param weight
     */
    public void addEdge(int from,int to,int weight){
        edgeCount += 1;
        Map<Integer,Integer> edges = graph.get(from);
        if(edges == null){
            edges = new HashMap<>();
            graph.put(from,edges);
        }
        edges.put(to,weight);
    }

    public void addReverseEdge(int from,int to,int weight){
        Map<Integer,Integer> reverseEdges = reverseGraph.get(from);
        if(reverseEdges == null){
            reverseEdges = new HashMap<>();
            reverseGraph.put(from,reverseEdges);
        }
        reverseEdges.put(to,weight);
    }

    public Map<Integer,Integer> getReverseEdge(int from){
        return reverseGraph.getOrDefault(from,new HashMap<>());
    }


    public Map<Integer,Integer> getEdge(int from){
        return graph.get(from);
    }

    public int getWeight(int from, int to){
        if(from == to)
            return 0;
        return graph.get(from).getOrDefault(to,-1);
    }

    public int getReverseWeight(int from,int to){
        return reverseGraph.get(from).getOrDefault(to,-1);
    }

    public int getNodeCcount(){return graph.size();}


    public int getEdgeCount() {
        return edgeCount;
    }
}
