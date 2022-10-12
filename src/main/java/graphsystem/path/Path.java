package graphsystem.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Path<N, W extends Number> extends ArrayList<N> implements Comparable<Path<N, W>> {

    private final List<W> weightIncrements = new ArrayList<>();

    public static <N, W extends Number> List<Path<N, W>> findPermutations(List<N> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<Path<N, W>> permutations = new ArrayList<>();
        Path<N, W> initPath = new Path<>();
        initPath.add(nodes.get(0));
        permutations.add(initPath);

        for (int i = 1; i < nodes.size(); i++) {
            for (int j = permutations.size() - 1; j >= 0; j--) {
                Path<N, W> lastTempPath = permutations.remove(j);
                for (int k = 0; k <= lastTempPath.size(); k++) {
                    Path<N, W> newPath = new Path<>();
                    newPath.addAll(lastTempPath.subList(0, k));
                    newPath.add(nodes.get(i));
                    newPath.addAll(lastTempPath.subList(k, lastTempPath.size()));
                    permutations.add(newPath);
                }
            }
        }
        return permutations;
    }

    public double getWeight() {
        if (weightIncrements.isEmpty()) {
            return Double.MAX_VALUE;
        }
        return weightIncrements.stream().mapToDouble(Number::doubleValue).sum();
    }

    public void addNode(N node, W weight) {
        weightIncrements.add(weight);
        add(node);
    }

    public N removeNode(int index) {
        weightIncrements.remove(index);
        return remove(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Path<?, ?> path = (Path<?, ?>) o;
        return Objects.equals(weightIncrements, path.weightIncrements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weightIncrements);
    }

    @Override
    public int compareTo(Path<N, W> o) {
        if (this.getWeight() < o.getWeight()) {
            return -1;
        } else if (this.getWeight() > o.getWeight()) {
            return 1;
        }
        return 0;
    }

    public Path<N, W> copy() {
        Path<N, W> newPath = new Path<>();
        for (int i = 0; i < weightIncrements.size(); i++) {
            newPath.addNode(this.get(i), weightIncrements.get(i));
        }
        return newPath;
    }

    public W getWeightIncrement(int index) {
        return weightIncrements.get(index);
    }

}