package com.lyp.alg.algorithms;

import com.lyp.alg.model.AStarNode;
import com.lyp.alg.model.Graph;
import com.lyp.alg.model.GraphNode;

import java.util.*;


/**
 * pseudo code:
 * https://medium.com/@nicholas.w.swift/easy-a-star-pathfinding-7e6689c7f7b2
 * correctness explanation, A-star is guaranteed to provide the shortest path according to your metric function :
 * https://stackoverflow.com/questions/16246026/is-a-star-guaranteed-to-give-the-shortest-path-in-a-2d-grid
 * http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
 */
public class AStar {
    public static int getDistance(Graph graph,int from,int to){
        int minDistance = 0;
        PriorityQueue<AStarNode> open = new PriorityQueue<>(
                Comparator.comparingInt(AStarNode::getCombinedDistance)
        );
        Map<Integer,Integer> openMap = new HashMap<>();

        Set<Integer> close = new HashSet<>();
        open.add(new AStarNode(from,0,0,0));
        while (!open.isEmpty()){
            AStarNode node = open.poll();
            close.add(node.getNodeId());
            if(node.getNodeId() == to){
                minDistance = node.getActualDistance();
                break;
            }
            List<GraphNode> adjList = graph.getEdge(node.getNodeId());

           for (GraphNode graphNode : adjList){
               int childNodeId = graphNode.getTo();
               int weight = graphNode.getWeight();
               if(close.contains(childNodeId)){
                   continue;
               }
               int actualDistance = node.getActualDistance() + weight;
               int heuristic = calculateHeuristic(childNodeId,to);
               int combinedDistance = actualDistance + heuristic;
               AStarNode child = new AStarNode(childNodeId,actualDistance,
                       heuristic,combinedDistance);

               Integer curCombined = openMap.get(childNodeId);
               if(curCombined != null && combinedDistance > curCombined){
                   continue;
               }
               open.add(child);
               openMap.put(childNodeId,combinedDistance);
           }
        }
        return minDistance;
    }

    /**
     * To note that,
     * if heuristic is too small, it can guarantee find the shortest path, but run slower
     * if heuristic is too large, it cannot guarantee find the shortest path, but run faster
     * @param from
     * @param to
     * @return estimated distance from current node to target
     */
    private static int calculateHeuristic(int from,int to){
        return Math.abs(to - from)%10;
    }
}
