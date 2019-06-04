package com.lyp.alg;

import static org.junit.Assert.assertTrue;

import com.lyp.alg.algorithms.AStar;
import com.lyp.alg.algorithms.BiDirectionalDijkstra;
import com.lyp.alg.algorithms.Dijkstra;
import com.lyp.alg.io.DataIO;
import com.lyp.alg.model.Graph;
import com.lyp.alg.model.PathStatistics;
import com.lyp.alg.model.WeightedGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private Graph graph = new Graph();
    private WeightedGraph weightedGraph = new WeightedGraph();
    private int times = 5;
    private int[] from = new int[times];
    private int[] to = new int[times];
    private int[] dir = new int[times];
    private int[] biDir = new int[times];
    private int[] astar = new int[times];

    @Before
    public void init(){
        String dataPath = "./data/roadNet-CA.txt";
        DataIO.loadData(graph,weightedGraph,dataPath,200);
        int limit = graph.getNodeCount();
        Random random = new Random();
        for(int i=0;i<times;i++){
            from[i] = random.nextInt(limit);
            to[i] = random.nextInt(limit);
        }
    }

    @Test
    public void DijkstraTest(){
        PathStatistics statistics = Dijkstra.calculateDistance(graph,from[0],to[0]);
        System.out.println(
                String.format("from=%d,to=%d,hop=%d,distance=%d",
                        statistics.getFrom(),statistics.getTo(),statistics.getHop(),statistics.getDistance())
        );
        System.out.println("path:"+statistics.getPath());
    }

    @Test
    public void roadNestTest(){
        long start1 = System.currentTimeMillis();
        for(int i=0;i<times;i++){
            dir[i] = Dijkstra.calculateDistance(graph,from[i],to[i]).getDistance();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Dij consume times(ms):"+(end1-start1)/times);

        /*
        long start2 = System.currentTimeMillis();
        for(int i=0;i<times;i++){
            biDir[i] = BiDirectionalDijkstra.getDistance(weightedGraph,from[i],to[i]).getDistance();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("BiDij consume times(ms):"+(end2 - start2)/times);
         */

        long start3 = System.currentTimeMillis();
        for(int i=0;i<times;i++){
            astar[i] = AStar.getDistance(graph,from[i],to[i]);
        }
        long end3 = System.currentTimeMillis();
        System.out.println("Astar consume times(ms):"+(end3 - start3)/times);



        //Assert.assertArrayEquals(dir,biDir);
        Assert.assertArrayEquals(dir,astar);

        //Assert.assertEquals(dir,astar);
    }
}
