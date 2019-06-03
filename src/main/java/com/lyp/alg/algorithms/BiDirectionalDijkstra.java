package com.lyp.alg.algorithms;

import com.lyp.alg.model.HeapNode;
import com.lyp.alg.model.PathStatistics;
import com.lyp.alg.model.WeightedGraph;

import java.util.*;

public class BiDirectionalDijkstra {
    public static PathStatistics getDistance(WeightedGraph graph, int from, int to){
        PriorityQueue<HeapNode> front =
                new PriorityQueue<>(Comparator.comparingInt(HeapNode::getDistance));
        PriorityQueue<HeapNode> reverse =
                new PriorityQueue<>(Comparator.comparingInt(HeapNode::getDistance));
        //add initial node
        front.add(new HeapNode(from,0));
        reverse.add(new HeapNode(to,0));


        Map<Integer,HeapNode> frontSeen = new HashMap<>();
        Map<Integer,HeapNode> reverseSeen = new HashMap<>();

        int minDistance = Integer.MAX_VALUE;

        while(!front.isEmpty() && !reverse.isEmpty()){
            HeapNode frontTop = front.poll();
            HeapNode reverseTop = reverse.poll();

            while (frontSeen.containsKey(frontTop.getNodeId())){
                frontTop = front.poll();
            }
            frontSeen.put(frontTop.getNodeId(),frontTop);
            
            while (reverseSeen.containsKey(reverseTop.getNodeId())){
                reverseTop = reverse.poll();
            }
            reverseSeen.put(reverseTop.getNodeId(),reverseTop);
            
            int frontDistance = frontTop.getDistance();
            int reverseDistance = reverseTop.getDistance();
            
            //weight = frontSeen.get(reverseTop.getNodeId())
            // what if there is an edge between top and nodes in frontSeen?
            if(frontSeen.containsKey(reverseTop.getNodeId())){
                int dist = frontSeen.get(reverseTop.getNodeId()).getDistance()
                        + reverseTop.getDistance();
                minDistance = Math.min(minDistance,dist);
            }

            minDistance = Math.min(minDistance, getDistance(graph,true,reverseTop,frontSeen));

            if(reverseSeen.containsKey(frontTop.getNodeId())){
                int dist = reverseSeen.get(frontTop.getNodeId()).getDistance()
                        + frontTop.getDistance();
                minDistance = Math.min(minDistance,dist);
            }

            minDistance = Math.min(minDistance,getDistance(graph,false,frontTop,reverseSeen));
            

            //termination condition 1
            if(frontTop.getDistance() + reverseTop.getDistance() >= minDistance)
                break;

            graph.getEdge(frontTop.getNodeId()).forEach((succ,weight) ->{
                if(!frontSeen.containsKey(succ)){
                    front.add(new HeapNode(succ,weight + frontDistance));
                }
            });


            //get reverse edge
            graph.getReverseEdge(reverseTop.getNodeId()).forEach((succ,weight)->{
                if(!reverseSeen.containsKey(succ)){
                    reverse.add(new HeapNode(succ,weight + reverseDistance));
                }
            });

        }

        return new PathStatistics(from,to,minDistance);
    }

    private static int getDistance(WeightedGraph graph,boolean reverse,
                                       HeapNode top, Map<Integer,HeapNode> seen){
        Map<Integer,Integer> edges = reverse == true ?
                graph.getReverseEdge(top.getNodeId()) :
                graph.getEdge(top.getNodeId());
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        edges.forEach((succ,weight) ->{
            HeapNode heapNode = seen.get(succ);
            if(heapNode != null){
                int dist = top.getDistance() + heapNode.getDistance()+weight;
                min[0] = Math.min(min[0],dist);
            }
        });
        return min[0];
    }
}
