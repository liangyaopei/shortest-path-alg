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


        /**
         * Forward search
         * nodeId -> shortest distance from 'from' node to nodeId
         */
        Map<Integer,Integer> frontSeen = new HashMap<>();
        /**
         * Backward search
         * nodeId -> shortest distance from 'to' node to nodeId
         */
        Map<Integer,Integer> reverseSeen = new HashMap<>();

        /**
         * Forward search
         * nodeId -> candidate distance from 'from' node to nodeId
         */
        Map<Integer,Integer> frontDistanceMap = new HashMap<>();
        /**
         * Backward search
         * nodeId -> candidate distance from 'to' node to nodeId
         */
        Map<Integer,Integer> reverseDistanceMap = new HashMap<>();


        int minDistance = Integer.MAX_VALUE;

        while(!front.isEmpty() && !reverse.isEmpty()){
            HeapNode frontTop = front.poll();
            HeapNode reverseTop = reverse.poll();

            //termination condition 1
            if(frontTop.getDistance() + reverseTop.getDistance() >= minDistance)
                break;

            while (frontSeen.containsKey(frontTop.getNodeId())){
                frontTop = front.poll();
            }
            frontSeen.put(frontTop.getNodeId(),frontTop.getDistance());
            
            while (reverseSeen.containsKey(reverseTop.getNodeId())){
                reverseTop = reverse.poll();
            }
            reverseSeen.put(reverseTop.getNodeId(),reverseTop.getDistance());



            int frontDistance = frontTop.getDistance();
            int reverseDistance = reverseTop.getDistance();
            

            if(frontSeen.containsKey(reverseTop.getNodeId())){
                int dist = frontSeen.get(reverseTop.getNodeId())
                        + reverseTop.getDistance();
                minDistance = Math.min(minDistance,dist);
            }

            minDistance = Math.min(minDistance, getDistance(graph,true,reverseTop,frontSeen));


            if(reverseSeen.containsKey(frontTop.getNodeId())){
                int dist = reverseSeen.get(frontTop.getNodeId())
                        + frontTop.getDistance();
                minDistance = Math.min(minDistance,dist);
            }


            minDistance = Math.min(minDistance,getDistance(graph,false,frontTop,reverseSeen));
            



            graph.getEdge(frontTop.getNodeId()).forEach((succ,weight) ->{
                //succ is not in visited shortest path
                if(!frontSeen.containsKey(succ)){
                    int curDistance = weight + frontDistance;
                    Integer candidateDistance = frontDistanceMap.get(succ);
                    //there is no path towards succ or there is shorter path towards succ
                    if(candidateDistance == null || curDistance < candidateDistance){
                        frontDistanceMap.put(succ,curDistance); // record shorter distance to succ

                        front.add(new HeapNode(succ,curDistance));
                    }
                    //front.add(new HeapNode(succ,weight + frontDistance));
                }
            });


            //get reverse edge
            graph.getReverseEdge(reverseTop.getNodeId()).forEach((succ,weight)->{
                if(!reverseSeen.containsKey(succ)){
                    int curDistance = weight + reverseDistance;
                    Integer candidateDistance = reverseDistanceMap.get(succ);
                    if(candidateDistance == null || curDistance < candidateDistance){
                        reverse.add(new HeapNode(succ,curDistance));
                        reverseDistanceMap.put(succ,curDistance);
                    }
                    //reverse.add(new HeapNode(succ,weight + reverseDistance));
                }
            });

        }

        return new PathStatistics(from,to,minDistance);
    }

    private static int getDistance(WeightedGraph graph,boolean reverse,
                                       HeapNode top, Map<Integer,Integer> seen){
        Map<Integer,Integer> edges = reverse == true ?
                graph.getReverseEdge(top.getNodeId()) :
                graph.getEdge(top.getNodeId());
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        edges.forEach((succ,weight) ->{
            Integer tempDistance = seen.get(succ);
            if(tempDistance != null){
                int dist = top.getDistance() + tempDistance + weight;
                min[0] = Math.min(min[0],dist);
            }
        });
        return min[0];
    }
}
