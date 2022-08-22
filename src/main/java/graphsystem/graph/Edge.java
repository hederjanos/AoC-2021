package graphsystem.graph;

public class Edge<N, W extends Number> {

    private N node1;
    private N node2;
    private W weight;

    public Edge(N node1, N node2, W weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public N getNode1() {
        return node1;
    }

    public void setNode1(N node1) {
        this.node1 = node1;
    }

    public N getNode2() {
        return node2;
    }

    public void setNode2(N node2) {
        this.node2 = node2;
    }

    public W getWeight() {
        return weight;
    }

    public void setWeight(W weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
               "node1=" + node1 +
               ", node2=" + node2 +
               ", weight=" + weight +
               '}';
    }
}
