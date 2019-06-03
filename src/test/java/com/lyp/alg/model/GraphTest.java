package com.lyp.alg.model;

import org.junit.Test;

public class GraphTest {
    @Test
    public void testGraph(){
        Graph graph = new Graph();
        graph.addEdge(1,2,3);
        graph.addEdge(1,3,3);
        graph.addEdge(1,4,3);
        //graph.show();
        System.out.println(graph.getWeight(1,5));
    }
}
