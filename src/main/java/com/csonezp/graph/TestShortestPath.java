package com.csonezp.graph;


/**
 * @author zhangpeng
 * @date 2019-06-20 13:22
 */
public class TestShortestPath {//hapjin test
    public static void main(String[] args) {
        String graphFilePath;
        if(args.length == 0)
            graphFilePath = "F:\\xxx";
        else
            graphFilePath = args[0];

        String graphContent = FileUtil.read(graphFilePath, null);
        NonDirectedGraph graph = new NonDirectedGraph(graphContent);
        graph.unweightedShortestPath();
        graph.showDistance();
    }
}
