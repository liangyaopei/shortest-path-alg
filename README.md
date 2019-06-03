这个项目实现了有向有环加权图中点对的最短路径计算，使用Java语言实现了迪杰特斯拉算法和双向迪杰特斯拉算法。在加州公路网 ([http://snap.stanford.edu/data/roadNet-CA.html](http://snap.stanford.edu/data/roadNet-CA.html) )上进行测, 公路网具有1,965,206个节点和 5,533,214条边。测试结果表明可以在这样的图中随机选取一个起始点和终点，算法可以在2000ms内找点对之间的最短路径。

> 程序运行时将roadNext.txt放在data文件夹下。

## 迪杰特斯拉算法



```java
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
```



## 双向迪杰特斯拉算法

分别从起始点和结束点进行最短路径的寻找。当两个优先队列的top的距离之和比当前找到的最短距离要小时，跳出循环。

在双向搜索时，两边的搜索可能汇于一个相同的点，也可能是一条边讲前向搜索和后向搜索的节点链接起来，从而形成最短路径。

```java
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
```



## 测试结果

在图中随机选取10个点，进行时间测量。

```java
@Test
    public void roadNestTest(){
        long start1 = System.currentTimeMillis();
        for(int i=0;i<times;i++){
            dir[i] = Dijkstra.calculateDistance(graph,from[i],to[i]).getDistance();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("Dij consume times(ms):"+(end1-start1)/times);

        long start2 = System.currentTimeMillis();
        for(int i=0;i<times;i++){
            biDir[i] = BiDirectionalDijkstra.getDistance(weightedGraph,from[i],to[i]).getDistance();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("BiDij consume times(ms):"+(end2 - start2)/times);


        Assert.assertArrayEquals(dir,biDir);
    }
```

结果：

```
Dij consume times(ms):2148
BiDij consume times(ms):1752
```

