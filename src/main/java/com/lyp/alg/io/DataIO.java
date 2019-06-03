package com.lyp.alg.io;

import com.lyp.alg.model.Graph;
import com.lyp.alg.model.WeightedGraph;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

public class DataIO {
    private static String dataPath = "./data/roadNet-CA.txt";

    public static void loadData(Graph graph, WeightedGraph weightedGraph,
                                String path, int bound){
        try(Stream<String> lines = Files.lines(Paths.get(path), StandardCharsets.UTF_8)){
            Random random = new Random();
           lines
                   .skip(4)
                   .forEach(line -> {
               String[] paras = line.split("\t");
               int from = Integer.parseInt(paras[0]);
               int to = Integer.parseInt(paras[1]);
               int weight = random.nextInt(bound);
               graph.addEdge(from,to,weight);
               weightedGraph.addEdge(from,to,weight);
               weightedGraph.addReverseEdge(to,from,weight);
           });

           System.out.println(
                   String.format("Programme loaded %d nodes and %d edges",graph.getNodeCount(),graph.getEdgeCount()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
