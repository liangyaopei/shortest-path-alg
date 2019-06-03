package com.lyp.alg.algorithms;

import com.lyp.alg.model.Graph;
import com.lyp.alg.model.HeapNode;
import com.lyp.alg.model.PathStatistics;


import java.util.*;


public class Dijkstra {

    /**
     *
     * @param graph
     * @param from
     * @param to
     * @return shortest Path between from and to
     */
    public static PathStatistics calculateDistance(Graph graph,int from, int to){
        int shortestDistance = 0;

        PriorityQueue<HeapNode> minHeap = new PriorityQueue<>(
               Comparator.comparingInt(HeapNode::getDistance)
        );
        Set<Integer> visitedSet = new HashSet<>();
        /**
         * is a nodeId-distance Map
         * record the shortest distance to nodeid
         */
        Map<Integer,Integer> distanceMap = new HashMap<>();
        /**
         * is a nodeID-nodeID Map
         * record the shortest distance prefix to nodeId
         */
        Map<Integer,Integer> prefixMap = new HashMap<>();


        minHeap.add(new HeapNode(from,0));

        while(!minHeap.isEmpty()){
            HeapNode minNode = minHeap.poll();
            // exclude the all already visited node
            while (visitedSet.contains(minNode.getNodeId()) && minHeap.isEmpty() == false){
                minNode = minHeap.poll();
            }
            int currNodeId = minNode.getNodeId();
            int currDistance = minNode.getDistance();
            visitedSet.add(currNodeId);

            // found the shortest path
            if(currNodeId == to){
                shortestDistance = currDistance;
                break;
            }

            graph.getEdge(currNodeId).forEach(graphNode -> {
                int id = graphNode.getTo();
                int weight = graphNode.getWeight();
                if(!visitedSet.contains(id)){
                    int totalDistance = weight + currDistance;
                    Integer prefix = prefixMap.get(id);
                    if(prefix == null){
                        prefixMap.put(id,currNodeId);
                        distanceMap.put(id,totalDistance);
                    }else{
                        Integer tempDistance = distanceMap.get(id);
                        if(totalDistance<tempDistance){
                            distanceMap.put(id,totalDistance);
                            prefixMap.put(id,currNodeId);
                        }
                    }
                    HeapNode heapNode = new HeapNode(id,weight + currDistance);
                    minHeap.add(heapNode);
                }
            });


        }
        return printPath(prefixMap,from,to,shortestDistance);
    }

    private static PathStatistics printPath(Map<Integer,Integer> prefixMap,
                                            int from,int to,int shortestDistance){
        StringBuilder builder = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        int prefix = to;
        int hop = 0;
        while (prefix != from){
            hop++;
            stack.push(prefix);
            prefix = prefixMap.get(prefix);
        }
        builder.append(from);
        while (stack.isEmpty()==false){
           builder.append("->"+stack.pop());
        }
        hop+=1;
        return new PathStatistics(from,to,hop,shortestDistance,builder.toString());
    }
}
