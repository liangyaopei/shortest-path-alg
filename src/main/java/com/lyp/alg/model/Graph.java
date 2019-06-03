package com.lyp.alg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, List<GraphNode>> graph;
    private int edgeCount;

    public Graph() {
        graph = new HashMap<>();
        edgeCount = 0;
    }

    public void addEdge(int from, int to,int weight){
        edgeCount+=1;
        List<GraphNode> list = graph.get(from);
        if(list == null){
            list = new ArrayList<>();
            graph.put(from,list);
        }
        list.add(new GraphNode(to,weight));
    }

    public void show(){
        graph.forEach((key,value) -> System.out.println(
                String.format("from:%d,list: %s",key,value)
        ));
    }

   public List<GraphNode> getEdge(int from){
        return graph.get(from);
   }

   public int getNodeCount(){
        return graph.size();
   }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getWeight(int from, int to){
        System.out.println("get weight,from="+from+",to"+to);
        return graph.getOrDefault(from,new ArrayList<>())
                .stream()
                .filter(graphNode -> graphNode.getTo() == to)
                .findFirst()
                .get()
                .getWeight();
   }
}
