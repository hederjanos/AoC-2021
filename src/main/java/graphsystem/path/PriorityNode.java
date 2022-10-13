package graphsystem.path;

import java.util.Objects;

public final class PriorityNode<N, P extends Comparable<P>> implements Comparable<PriorityNode<N, P>> {

    private final N node;
    private P priority;

    public PriorityNode(N node, P priority) {
        this.node = node;
        this.priority = priority;
    }

    public N getNode() {
        return node;
    }

    public P getPriority() {
        return priority;
    }

    public void setPriority(P priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriorityNode<?, ?> that = (PriorityNode<?, ?>) o;
        return Objects.equals(node, that.node) && Objects.equals(priority, that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, priority);
    }

    @Override
    public int compareTo(PriorityNode<N, P> o) {
       return this.getPriority().compareTo(o.getPriority());
    }

    @Override
    public String toString() {
        return "PriorityNode{" +
                "node=" + node +
                ", priority=" + priority +
                '}';
    }

}