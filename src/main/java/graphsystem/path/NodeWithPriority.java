package graphsystem.path;

import java.util.Objects;

public class NodeWithPriority<N> implements Comparable<NodeWithPriority<N>> {

    private final N node;
    private double priority;

    public NodeWithPriority(N node, double priority) {
        this.node = node;
        this.priority = priority;
    }

    public NodeWithPriority(N node) {
        this(node, 0d);
    }

    public N getNode() {
        return node;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWithPriority<?> that = (NodeWithPriority<?>) o;
        return Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node);
    }

    @Override
    public int compareTo(NodeWithPriority<N> o) {
        if (this.getPriority() < o.getPriority()) {
            return -1;
        } else if (this.getPriority() > o.getPriority()) {
            return 1;
        }
        return 0;
    }

}