package graphsystem.graph;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Edge<N, W extends Number> {

    private final List<N> nodes = new ArrayList<>();
    private final W weight;

    public Edge(N node1, N node2, W weight) {
        nodes.add(node1);
        nodes.add(node2);
        this.weight = weight;
    }

    public N getNode1() {
        return nodes.get(0);
    }

    public N getNode2() {
        return nodes.get(1);
    }

    public W getWeight() {
        return weight;
    }

    public boolean containsNode(N node) {
        return nodes.contains(node);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "nodes=" + nodes +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?, ?> edge = (Edge<?, ?>) o;
        return nodes.equals(edge.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }

}
