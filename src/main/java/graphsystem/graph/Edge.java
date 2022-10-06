package graphsystem.graph;

import java.util.Objects;

public final class Edge<N, W extends Number> implements Comparable<Edge<N, W>> {

    private final N source;
    private final N target;
    private final W weight;

    public Edge(N node1, N node2, W weight) {
        this.source = node1;
        this.target = node2;
        this.weight = weight;
    }

    public N getSource() {
        return source;
    }

    public N getTarget() {
        return target;
    }

    public W getWeight() {
        return weight;
    }

    public Edge<N, W> copy() {
        return new Edge<>(this.getSource(), this.getTarget(), this.getWeight());
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node1=" + source +
                ", node2=" + target +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?, ?> edge = (Edge<?, ?>) o;
        return (source.equals(edge.getSource()) && target.equals(edge.getTarget())) || (source.equals(edge.getTarget()) && target.equals(edge.getSource()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(source) + Objects.hash(target);
    }

    @Override
    public int compareTo(Edge<N, W> o) {
        return Double.compare(this.getWeight().doubleValue(), o.getWeight().doubleValue());
    }

}