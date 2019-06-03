package com.lyp.alg.algorithms;

import com.lyp.alg.model.Graph;
import com.lyp.alg.model.PathStatistics;
import com.lyp.alg.model.WeightedGraph;
import org.junit.Assert;
import org.junit.Test;

public class DistanceCalculatorTest {


    /**
     *             |------ 10 -----|
     *             |               |
     * 1--->100---> 2 -----8 ----> 3
     * |           ^
     * |           |
     * |-120-------
     * from: 1, to:3
     * shortest distance: 108
     */
    @Test
    public void testGraph1(){
        Graph graph = new Graph();
       // WeightedGraph weightedGraph = new WeightedGraph();

        graph.addEdge(1,2,100);
        graph.addEdge(1,2,120);
        graph.addEdge(2,3,10);
        graph.addEdge(2,3,8);
        int from = 1, to = 3;

        PathStatistics statistics = Dijkstra.calculateDistance(graph,from,to);
        //System.out.println(statistics.getDistance());
        Assert.assertEquals(statistics.getDistance(),108);
    }

    /**
     * sample graph to test,This is a cycle graph with weight
     * 1 --1--> 2 --2--> 3 --9--> 4
     * |        |                 ^
     * |        5                 |
     * |        |                 |
     * |        V                 |
     * |---2--> 5---------3-------
     *
     * from: 1, to : 4
     * shortest distance: 5
     * shortest path: 1->5->4
     */
    @Test
    public void calculateDistanceTest(){
        Graph graph = new Graph();
        WeightedGraph weightedGraph = new WeightedGraph();

        graph.addEdge(1,2,1);
        graph.addEdge(2,3,2);
        graph.addEdge(3,4,9);
        graph.addEdge(1,5,2);
        graph.addEdge(2,5,5);
        graph.addEdge(5,4,3);

        weightedGraph.addEdge(1,2,1);
        weightedGraph.addReverseEdge(2,1,1);
        weightedGraph.addEdge(2,3,2);
        weightedGraph.addReverseEdge(3,2,2);
        weightedGraph.addEdge(3,4,9);
        weightedGraph.addReverseEdge(4,3,9);
        weightedGraph.addEdge(1,5,2);
        weightedGraph.addReverseEdge(5,1,2);
        weightedGraph.addEdge(2,5,5);
        weightedGraph.addReverseEdge(5,2,5);
        weightedGraph.addEdge(5,4,3);
        weightedGraph.addReverseEdge(4,5,3);

        PathStatistics statistics = Dijkstra.calculateDistance(graph,1,4);
        Assert.assertEquals(statistics.getDistance(),5);
        Assert.assertEquals(statistics.getPath(),"1->5->4");

        PathStatistics statistics1 = BiDirectionalDijkstra
                .getDistance(weightedGraph,1,4);
        Assert.assertEquals(statistics1.getDistance(),5);
    }

}
