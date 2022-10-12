package graphsystem;

import graphsystem.graph.Edge;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.Path;
import graphsystem.path.PathFinder;
import util.PuzzleReader;

import java.util.*;
import java.util.stream.Collectors;

public class Application {

    private static Map<Integer, Integer> integerMap;

    public static void main(String[] args) {
        //simple (No.: 11, OK)
        //level1 (No.: 25)
        //level2 (No.: 43)
        //level3 (No.: 102)
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("level1.in");
        SimpleGridGraph simpleGridGraph = new SimpleGridGraph(puzzleReader.getPuzzleLines());

        PathFinder<GridCell, Integer> pathFinder = new BreadthSearchPathFinder<>(simpleGridGraph);
        pathFinder.findAllPathsFromNode(simpleGridGraph.getStartNode());
        GridCell furthestNode = (GridCell) ((BreadthSearchPathFinder) pathFinder).getFurthestNode(simpleGridGraph.getStartNode());
        System.out.println("f: " + furthestNode);

        pathFinder.findAllPathsFromNode(furthestNode);

        List<GridCell> closestCellsToFurthest = (List<GridCell>) pathFinder.getClosestCriticalNodesByCost(furthestNode, simpleGridGraph.getCriticalNodes().size());

        Path<GridCell, Integer> solution = new Path<>();
        solution.addNode(simpleGridGraph.getStartNode(), 0);
        while (solution.size() != simpleGridGraph.getCriticalNodes().size()) {
            System.out.println(solution.stream().map(simpleGridGraph::encodeNode).collect(Collectors.toUnmodifiableList()));
            //System.out.println(simpleGridGraph.encodeNode(solution.get(solution.size() - 1)));
            GridCell parent = solution.get(solution.size() - 1);
            pathFinder.findAllPathsFromNode(parent);
            List<GridCell> closestCellsToPrev = (List<GridCell>) pathFinder.getClosestCriticalNodesByCost(parent, simpleGridGraph.getCriticalNodes().size());
            GridCell neighbour1;
            int i = 0;
            do {
                neighbour1 = closestCellsToPrev.get(i);
                i++;
                if (i == closestCellsToPrev.size()) {
                    break;
                }
            } while (solution.contains(neighbour1));

            closestCellsToPrev.sort((o1, o2) -> Integer.compare(closestCellsToFurthest.indexOf(o2), closestCellsToFurthest.indexOf(o1)));

            GridCell neighbour2;
            int j = 0;
            do {
                neighbour2 = closestCellsToPrev.get(j);
                j++;
                if (j == closestCellsToPrev.size()) {
                    break;
                }
            } while (solution.contains(neighbour2));

            int n1 = parent.getPosition().calculateDistance(neighbour1.getPosition());
            int n2 = parent.getPosition().calculateDistance(neighbour2.getPosition());

            System.out.println(simpleGridGraph.encodeNode(neighbour1));
            System.out.println(n1);
            System.out.println(simpleGridGraph.encodeNode(neighbour2));
            System.out.println(n2);


            if (n1 < n2 && Math.abs(n1 - n2) < simpleGridGraph.getCriticalNodes().size()) {
                solution.addNode(neighbour1, pathFinder.getNumberOfMovesTo(neighbour1));
            } else {
                solution.addNode(neighbour2, pathFinder.getNumberOfMovesTo(neighbour2));
            }


        }
        System.out.println(solution.stream().map(simpleGridGraph::encodeNode).collect(Collectors.toUnmodifiableList()));
        System.out.println(solution.getWeight());


       /* WeightedIntegerGraph weightedIntegerGraph = new WeightedIntegerGraph();
        weightedIntegerGraph.transFormSimpleGridGraphByCriticalNodes(simpleGridGraph, 4);

        //trial
        System.out.println("-------------------------------------------------------------");
        System.out.println("WeightedIntegerGraph representation of input: ");
        System.out.println("-------------------------------------------------------------");
        System.out.println(weightedIntegerGraph);
        System.out.println("-------------------------------------------------------------");
        System.out.println("Solution:");
        System.out.println("-------------------------------------------------------------");

        Converter converter = new Converter(weightedIntegerGraph);
        integerMap = converter.getIntegerMap();
        System.out.println(integerMap);

        Optional<Path<Integer, Integer>> min = calculate(simpleGridGraph, weightedIntegerGraph);
        System.out.println("Start node: " + simpleGridGraph.encodeNode(simpleGridGraph.getStartNode()));
        System.out.println("Cost: " + min.get().getWeight());
        System.out.println("Path: ");
        min.get().stream().map(simpleGridGraph::decodeNode).forEach(System.out::println);
        nav(simpleGridGraph, weightedIntegerGraph);*/

    }

    private static List<IntDeg> nav(SimpleGridGraph simpleGridGraph, WeightedIntegerGraph weightedIntegerGraph) {
        List<IntDeg> path = new ArrayList<>();
        boolean[] isVisited = new boolean[weightedIntegerGraph.getNumberOfNodes()];
        Deque<IntDeg> nodes = new ArrayDeque<>();
        nodes.push(new IntDeg(weightedIntegerGraph.getStartNode(), 0));
        path.add(new IntDeg(weightedIntegerGraph.getStartNode(), 0));
        isVisited[weightedIntegerGraph.encodeNode(weightedIntegerGraph.getStartNode())] = true;
        while (!nodes.isEmpty()) {
            IntDeg pop = nodes.pop();
            int nodeInPath = path.indexOf(pop);
            List<Integer> neighbours = (List<Integer>) weightedIntegerGraph.getNeighbours(pop.getNode());
            List<Integer> collect = neighbours.stream().sorted((o1, o2) -> {
                GridCell orig = simpleGridGraph.decodeNode(pop.getNode());
                GridCell cell1 = simpleGridGraph.decodeNode(o1);
                GridCell cell2 = simpleGridGraph.decodeNode(o2);
                Integer i = orig.getPosition().calculateSquareDistance(cell1.getPosition());
                Integer j = orig.getPosition().calculateSquareDistance(cell2.getPosition());
                return i.compareTo(j);
            }).collect(Collectors.toUnmodifiableList());
            int counter = 0;
            for (Integer neighbour : collect) {
                if (!isVisited[weightedIntegerGraph.encodeNode(neighbour)]) {
                    isVisited[weightedIntegerGraph.encodeNode(neighbour)] = true;
                    nodes.push(new IntDeg(neighbour, 0));
                    path.add(new IntDeg(neighbour, 0));
                    counter++;
                }
            }
            path.get(nodeInPath).setDeg(counter);

        }

        System.out.println(path.size());
        for (IntDeg intDeg : path) {
            System.out.println(intDeg.getNode() + ": " + intDeg.getDeg());
        }
        return path;
    }

    private static class IntDeg {
        private Integer node;
        private Integer deg;

        public IntDeg(Integer node, Integer deg) {
            this.node = node;
            this.deg = deg;
        }

        public Integer getNode() {
            return node;
        }

        public void setNode(Integer node) {
            this.node = node;
        }

        public Integer getDeg() {
            return deg;
        }

        public void setDeg(Integer deg) {
            this.deg = deg;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntDeg intDeg = (IntDeg) o;
            return Objects.equals(node, intDeg.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node);
        }
    }

    private static Optional<Path<Integer, Integer>> calculate(SimpleGridGraph simpleGridGraph, WeightedIntegerGraph weightedIntegerGraph) {
        Path<Integer, Integer> solution = new Path<>();
        Deque<Path<Integer, Integer>> paths = new ArrayDeque<>();
        Deque<int[]> visitedNodeRegisters = new ArrayDeque<>();
        Path<Integer, Integer> startPath = new Path<>();
        int[] startBook = new int[simpleGridGraph.getCriticalNodes().size()];
        startBook[weightedIntegerGraph.encodeNode(weightedIntegerGraph.getStartNode())] = 1;
        startPath.addNode(weightedIntegerGraph.getStartNode(), 0);
        paths.push(startPath);
        visitedNodeRegisters.push(startBook);
        while (!paths.isEmpty()) {
            Path<Integer, Integer> path = paths.pop();
            int[] nodeRegister = visitedNodeRegisters.pop();
            System.out.println("S-------------------------------------------------------------");
            System.out.println("path: " + path.size() + ", " + path);
            System.out.println("remaining paths: " + paths.size());
            System.out.println("current solution: " + solution.size() + ", " + solution);
            System.out.println("E-------------------------------------------------------------");

            if (path.getWeight() > solution.getWeight()) {
                continue;
            }
            if (Arrays.stream(nodeRegister).filter(g -> g > 0).count() == simpleGridGraph.getCriticalNodes().size()) {
                Path<Integer, Integer> newPath = path.copy();
                if (solution.getWeight() > newPath.getWeight()) {
                    solution = newPath;
                    continue;
                }
            }
            Integer lastNode = path.get(path.size() - 1);
            int numberOfUnVisitedNeighbours = 0;
            Iterable<Edge<Integer, Integer>> edges = weightedIntegerGraph.getEdges(lastNode);
            List<Edge<Integer, Integer>> targets = new ArrayList<>();
            for (Edge<Integer, Integer> edge : edges) {
                if (edge.getSource().equals(lastNode)) {
                    targets.add(new Edge<>(edge.getSource(), edge.getTarget(), edge.getWeight()));
                } else {
                    targets.add(new Edge<>(edge.getTarget(), edge.getSource(), edge.getWeight()));
                }
            }

            for (Edge<Integer, Integer> edge : targets) {
                if (!path.contains(edge.getTarget()) && path.getWeight() + edge.getWeight() < solution.getWeight() && !edge.getTarget().equals(lastNode)) {
                    numberOfUnVisitedNeighbours++;
                    Path<Integer, Integer> newPath = path.copy();
                    newPath.addNode(edge.getTarget(), edge.getWeight());
                    paths.push(newPath);
                    int[] newNodeRegister = Arrays.copyOf(nodeRegister, nodeRegister.length);
                    newNodeRegister[weightedIntegerGraph.encodeNode(edge.getTarget())] += 1;
                    visitedNodeRegisters.push(newNodeRegister);
                }
            }
            boolean pathIsUpdatable = true;
            if (numberOfUnVisitedNeighbours == 0) {
                System.out.println(path);
                Path<Integer, Integer> modifiedPath = path.copy();
                path.removeNode(path.size() - 1);
                List<Integer> neighbours;
                do {
                    Integer weight = path.getWeightIncrement(path.size() - 1);
                    Integer removeNode = path.removeNode(path.size() - 1);
                    if (removeNode.equals(weightedIntegerGraph.getStartNode())) {
                        pathIsUpdatable = false;
                        break;
                    }
                    modifiedPath.addNode(removeNode, weight);
                    System.out.println("Removed: " + removeNode);
                    nodeRegister[weightedIntegerGraph.encodeNode(removeNode)] += 1;
                    neighbours = ((List<Integer>) weightedIntegerGraph.getNeighbours(removeNode));
                    if (nodeRegister[weightedIntegerGraph.encodeNode(removeNode)] > integerMap.get(removeNode)) {
                        pathIsUpdatable = false;
                        break;
                    }
                    System.out.println(Arrays.toString(nodeRegister));
                    System.out.println(neighbours);
                } while (!path.isEmpty() && neighbours.stream().filter(path::contains).count() == neighbours.size());
                if (pathIsUpdatable) {
                    paths.push(modifiedPath);
                    visitedNodeRegisters.push(nodeRegister);
                }
            }
        }
        return Optional.of(solution);
    }

}