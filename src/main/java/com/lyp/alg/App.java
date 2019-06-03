package com.lyp.alg;

import com.lyp.alg.algorithms.BiDirectionalDijkstra;
import com.lyp.alg.algorithms.Dijkstra;
import com.lyp.alg.io.DataIO;
import com.lyp.alg.model.Graph;
import com.lyp.alg.model.PathStatistics;
import com.lyp.alg.model.WeightedGraph;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Graph graph = new Graph();
        WeightedGraph weightedGraph = new WeightedGraph();
        String dataPath = "./data/roadNet-CA.txt";
        DataIO.loadData(graph,weightedGraph,dataPath,200);

        //int bound = 200;
        Random random = new Random();
        int edgeCount = graph.getNodeCount();
        int from = random.nextInt(edgeCount);
        int to = random.nextInt(edgeCount);

        long start2 = System.currentTimeMillis();
        PathStatistics weightPath = BiDirectionalDijkstra.getDistance(weightedGraph,from,to);
        long end2 = System.currentTimeMillis();
        System.out.println("bi dir:consume:"+(end2 - start2));
        System.out.println("bi distance:"+weightPath.getDistance());

        long start1 = System.currentTimeMillis();
        PathStatistics statistics = Dijkstra.calculateDistance(graph,from,to);
        long end1 = System.currentTimeMillis();
        System.out.println("dir:consume:"+(end1 - start1));
        System.out.println("from:"+statistics.getFrom());
        System.out.println("to:"+statistics.getTo());
        // System.out.println("hop:"+statistics.getHop());
        System.out.println("distance:"+statistics.getDistance());
        /// System.out.println("path:"+statistics.getPath());


    }
}
