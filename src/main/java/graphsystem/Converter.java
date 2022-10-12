package graphsystem;

import graphsystem.graph.Edge;
import graphsystem.graph.GraphWithEdges;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Converter {

    private final boolean[] marked;
    private final Map<Integer, Integer> integerMap;
    private Integer weight = 0;
    private final Queue<Edge<Integer, Integer>> tree;
    private final Queue<Edge<Integer, Integer>> priorityQueue;

    public Converter(GraphWithEdges<Integer, Integer> graphWithEdges) {
        marked = new boolean[graphWithEdges.getNumberOfNodes()];
        tree = new ArrayDeque<>();
        priorityQueue = new PriorityQueue<>();

        visit(graphWithEdges, graphWithEdges.getStartNode());

        while (!priorityQueue.isEmpty()) {
            Edge<Integer, Integer> edge = priorityQueue.poll();
            Integer source = edge.getSource();
            Integer target = edge.getTarget();
            if (marked[graphWithEdges.encodeNode(source)] && marked[graphWithEdges.encodeNode(target)]) {
                continue;
            }
            tree.offer(edge);
            weight += edge.getWeight();
            if (!marked[graphWithEdges.encodeNode(source)]) {
                visit(graphWithEdges, source);
            }
            if (!marked[graphWithEdges.encodeNode(target)]) {
                visit(graphWithEdges, target);
            }
        }
        integerMap = tree.stream().flatMap(integerIntegerEdge -> Stream.of(integerIntegerEdge.getSource(), integerIntegerEdge.getTarget()))

                .collect(Collectors.toMap(
                                character -> character, // hogyan kapjuk meg a stream eleméből a kulcsot
                                character -> 1,         // hogyan kapjuk meg a stream elemében az értéket
                                //  ha nincs ütközés
                                (currentOccurrence, nextOccurence) -> currentOccurrence + 1)
                        // mit csináljunk, ha már szerepel ez a kulcs a mapben
                );
    }

    private void visit(GraphWithEdges<Integer, Integer> graphWithEdges, Integer node) {
        marked[graphWithEdges.encodeNode(node)] = true;
        Integer target;
        for (Edge<Integer, Integer> edge : graphWithEdges.getEdges(node)) {
            if (edge.getSource().equals(node)) {
                target = edge.getTarget();
            } else {
                target = edge.getSource();
            }
            if (!marked[graphWithEdges.encodeNode(target)]) {
                priorityQueue.add(edge);
            }
        }
    }

    public Map<Integer, Integer> getIntegerMap() {
        return integerMap;
    }
}