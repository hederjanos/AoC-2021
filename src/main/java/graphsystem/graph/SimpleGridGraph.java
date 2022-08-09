package graphsystem.graph;

import graphsystem.grid.Direction;
import graphsystem.grid.GridCell;

import java.util.*;
import java.util.stream.Collectors;

import static graphsystem.grid.GridCellType.*;

public class SimpleGridGraph implements Graph<GridCell> {

    private final boolean fourWayDirection;
    private final int width;
    private final int height;
    private int numberOfEdges;
    private GridCell start;
    private final List<GridCell> cells;
    private final Map<Integer, Integer[]> connections;

    public SimpleGridGraph(int width, int height) {
        this(width, height, true);
    }

    public SimpleGridGraph(int width, int height, boolean fourWayDirection) {
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
        this.fourWayDirection = fourWayDirection;
        String[] dimensions = lines.get(0).split(" ");
        this.width = Integer.parseInt(dimensions[0]);
        this.height = Integer.parseInt(dimensions[1]);
        this.cells = new ArrayList<>();
        this.connections = new HashMap<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                GridCell cell = new GridCell(j, i - 1);
                setType(cell, line.charAt(j));
                if (cell.getType().equals(START)) {
                    start = cell;
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
                cell.setType(COIN);
                break;
            case '.':
                cell.setType(EMPTY);
                break;
            case 's':
                cell.setType(START);
                break;
            case '#':
                cell.setType(WALL);
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
    public boolean connect(GridCell node1, GridCell node2) {
        throw new UnsupportedOperationException();
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
        return null;
    }

    @Override
    public Iterable<GridCell> getAllNodes() {
        return cells.stream()
                .map(GridCell::copy)
                .collect(Collectors.toUnmodifiableList());
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
    public Optional<GridCell> getStartNode() {
        return Optional.of(start.copy());
    }

    @Override
    public boolean setStartNode(GridCell gridCell) {
        boolean startNodeSet = false;
        Optional<GridCell> cell = getNode(gridCell);
        if (cell.isPresent()) {
            start = cell.get();
            startNodeSet = true;
        }
        return startNodeSet;
    }

    public String getGridString() {
        StringBuilder gridBuilder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Optional<GridCell> currentCell = findCellByIndex(calculateCellIndex(j, i));
                if (currentCell.isPresent()) {
                    switch (currentCell.get().getType()) {
                        case COIN:
                            gridBuilder.append("c");
                            break;
                        case EMPTY:
                            gridBuilder.append(".");
                            break;
                        case START:
                            gridBuilder.append("s");
                            break;
                        case WALL:
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of vertices: ")
                .append(cells.size())
                .append(". Number of edges: ")
                .append(numberOfEdges)
                .append(".\n");
        connections.forEach((key, value) -> {
            stringBuilder.append(findCellByIndex(key).toString()).append(": ");
            for (Integer gridCell : value) {
                if (gridCell != null) {
                    stringBuilder.append(findCellByIndex(gridCell).toString()).append(" ");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

}