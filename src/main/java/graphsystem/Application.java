package graphsystem;

import graphsystem.graph.SimpleGridGraph;

/**
 * simple (No.: 11)
 * level1 (No.: 25)
 * level2 (No.: 43)
 * level3 (No.: 102, not connected!!!)
 */
public class Application {

    public static void main(String[] args) {
        SimpleGridGraph simpleGridGraph = new SimpleGridGraph("level3.in");
        System.out.println(simpleGridGraph);
    }

}