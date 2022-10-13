package graphsystem.graph;

import graphsystem.grid.Direction;
import graphsystem.grid.GridCell;

import java.util.*;
import java.util.stream.Collectors;

import static graphsystem.grid.GridCellType.*;

public final class SimpleGridGraph implements Graph<GridCell> {

    private final boolean fourWayDirection;
    private final int width;
    private final int height;
    private int numberOfEdges;
    private GridCell start;
    private List<GridCell> cells;
    private List<GridCell> criticalCells;
    private Map<Integer, Integer[]> connections;

    public SimpleGridGraph(int width, int height) {
        this(width, height, true);
    }

    public SimpleGridGraph(int width, int height, boolean fourWayDirection) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        this.fourWayDirection = fourWayDirection;
        this.width = width;
        this.height = height;
        this.cells = new ArrayList<>();
        this.connections = new HashMap<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                GridCell cell = new GridCell(j, i);
                cells.add(cell);
                int indexOfCell = calculateCellIndex(j, i);
                Integer[] neighbours = fourWayDirection ? new Integer[4] : new Integer[8];
                connections.put(indexOfCell, neighbours);
                setConnections(j, i);
            }
        }
    }

    private int calculateCellIndex(int x, int y) {
        return x + y * width;
    }

    private void setConnections(int x, int y) {
        for (Direction direction : Direction.values()) {
            if (fourWayDirection && direction.ordinal() % 2 != 0) {
                continue;
            }
            int newX = x + direction.getX();
            int newY = y + direction.getY();
            if (areCoordinatesInBounds(newX, newY)) {
                int indexOfCell = calculateCellIndex(x, y);
                int indexOfNeighbour = calculateCellIndex(newX, newY);
                if (findCellByIndex(indexOfNeighbour).isPresent()) {
                    connectNeighbours(indexOfCell, indexOfNeighbour, direction);
                }
            }
        }
    }

    private boolean areCoordinatesInBounds(int x, int y) {
        return x < width && x >= 0 && y < height && y >= 0;
    }

    private Optional<GridCell> findCellByIndex(int index) {
        Optional<GridCell> cell;
        try {
            cell = Optional.ofNullable(cells.get(index));
        } catch (IndexOutOfBoundsException exception) {
            cell = Optional.empty();
        }
        return cell;
    }

    private void connectNeighbours(Integer cell, Integer neighbour, Direction direction) {
        int directionIndex = direction.ordinal();
        int oppositeDirectionIndex = direction.getOppositeDirection().ordinal();
        if (fourWayDirection) {
            directionIndex /= 2;
            oppositeDirectionIndex /= 2;
        }
        connections.get(cell)[directionIndex] = neighbour;
        connections.get(neighbour)[oppositeDirectionIndex] = cell;
        numberOfEdges++;
    }

    public SimpleGridGraph(List<String> lines) {
        this(lines, true);
    }

    public SimpleGridGraph(List<String> lines, boolean fourWayDirection) {
        if (lines.size() <= 1) {
            throw new IllegalArgumentException();
        }
        this.fourWayDirection = fourWayDirection;
        String[] dimensions = lines.get(0).split(" ");
        this.width = Integer.parseInt(dimensions[0]);
        this.height = Integer.parseInt(dimensions[1]);
        this.cells = new ArrayList<>();
        this.criticalCells = new ArrayList<>();
        this.connections = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                GridCell cell = new GridCell(j, i - 1);
                setType(cell, line.charAt(j));
                if (START.equals(cell.getType())) {
                    start = cell;
                }
                if (MUST_BE_VISITED.equals(cell.getType()) || START.equals(cell.getType())) {
                    criticalCells.add(cell);
                }
                cells.add(cell);
                int indexOfCell = calculateCellIndex(j, i - 1);
                Integer[] neighbours = fourWayDirection ? new Integer[4] : new Integer[8];
                connections.put(indexOfCell, neighbours);
                setConnections(j, i - 1);
            }
        }
    }

    private void setType(GridCell cell, char charAt) {
        switch (charAt) {
            case 'c':
                cell.setType(MUST_BE_VISITED);
                break;
            case '.':
                cell.setType(EMPTY);
                break;
            case 's':
                cell.setType(START);
                break;
            case '#':
                cell.setType(IMPASSABLE);
                break;
            case 't':
                cell.setType(TARGET);
                break;
            default:
                break;
        }
    }

    @Override
    public int getNumberOfNodes() {
        return cells.size();
    }

    @Override
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    @Override
    public Iterable<GridCell> getNeighbours(GridCell cell) {
        Optional<GridCell> gridCell = getNode(cell);
        if (gridCell.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            Integer indexOfCell = encodeNode(cell);
            return Arrays.stream(connections.get(indexOfCell))
                    .filter(Objects::nonNull)
                    .map(integer -> findCellByIndex(integer).get())
                    .map(GridCell::copy)
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    @Override
    public Optional<GridCell> getNode(GridCell cell) {
        return findCell(cell);
    }

    @Override
    public boolean nodeIsPassable(GridCell cell) {
        boolean cellIsPassable = false;
        Optional<GridCell> gridCell = getNode(cell);
        if (gridCell.isPresent() && !IMPASSABLE.equals(gridCell.get().getType())) {
            cellIsPassable = true;
        }
        return cellIsPassable;
    }

    private Optional<GridCell> findCell(GridCell cell) {
        Optional<GridCell> optionalCell;
        try {
            optionalCell = Optional.ofNullable(cells.get(encodeNode(cell)).copy());
        } catch (IndexOutOfBoundsException exception) {
            optionalCell = Optional.empty();
        }
        return optionalCell;
    }

    @Override
    public Graph<GridCell> copy() {
        SimpleGridGraph newGraph = new SimpleGridGraph(width, height, fourWayDirection);
        newGraph.setStartNode(getStartNode());
        ArrayList<GridCell> gridCells = (ArrayList<GridCell>) getAllNodes();
        Map<Integer, Integer[]> connectionMap = new HashMap<>();
        for (int i = 0; i < gridCells.size(); i++) {
            Integer[] neighbours = fourWayDirection ? new Integer[4] : new Integer[8];
            connectionMap.put(i, neighbours);
            setConnections(gridCells.get(i).getPosition().getX(), gridCells.get(i).getPosition().getY());
        }
        newGraph.setCells(gridCells);
        newGraph.setConnections(connectionMap);
        return newGraph;
    }

    private void setStartNode(GridCell cell) {
        start = cell;
    }

    @Override
    public Iterable<GridCell> getAllNodes() {
        return cells.stream()
                .map(GridCell::copy)
                .collect(Collectors.toUnmodifiableList());
    }

    private void setCells(List<GridCell> cells) {
        this.cells = cells;
    }

    private void setConnections(Map<Integer, Integer[]> connections) {
        this.connections = connections;
    }

    @Override
    public Integer encodeNode(GridCell cell) {
        return calculateCellIndex(cell.getPosition().getX(), cell.getPosition().getY());
    }

    @Override
    public GridCell decodeNode(Integer index) {
        Optional<GridCell> cell = findCellByIndex(index);
        if (cell.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return cell.get().copy();
        }
    }

    @Override
    public GridCell getStartNode() {
        return start.copy();
    }

    @Override
    public Iterable<GridCell> getCriticalNodes() {
        return criticalCells.stream()
                .map(GridCell::copy)
                .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfCriticalNodes() {
        return criticalCells.size();
    }

    public String getGridString() {
        StringBuilder gridBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Optional<GridCell> currentCell = findCellByIndex(calculateCellIndex(j, i));
                if (currentCell.isPresent()) {
                    switch (currentCell.get().getType()) {
                        case MUST_BE_VISITED:
                            gridBuilder.append("c");
                            break;
                        case EMPTY:
                            gridBuilder.append(".");
                            break;
                        case START:
                            gridBuilder.append("s");
                            break;
                        case IMPASSABLE:
                            gridBuilder.append("#");
                            break;
                        case TARGET:
                            gridBuilder.append("t");
                            break;
                        default:
                            break;
                    }
                }
            }
            gridBuilder.append("\n");
        }
        return gridBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of nodes: ")
                .append(cells.size())
                .append(". Number of edges: ")
                .append(numberOfEdges)
                .append(".\n");
        connections.forEach((key, value) -> {
            stringBuilder.append(findCellByIndex(key).toString()).append(": ");
            for (Integer gridCell : value) {
                if (gridCell != null) {
                    stringBuilder.append(findCellByIndex(gridCell).toString()).append(", ");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString().trim();
    }

}