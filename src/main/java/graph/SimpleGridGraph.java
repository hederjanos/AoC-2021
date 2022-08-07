package graph;

import graph.grid.Direction;
import graph.grid.GridCell;

import java.util.*;
import java.util.stream.Collectors;

import static graph.grid.GridCellType.*;

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
                int indexOfCell = encodeNode(cell);
                Integer[] neighbours = fourWayDirection ? new Integer[4] : new Integer[8];
                connections.put(indexOfCell, neighbours);
                setConnections(cell);
            }
        }
    }

    @Override
    public Integer encodeNode(GridCell cell) {
        return calculateCellIndex(cell.getPosition().getX(), cell.getPosition().getY());
    }

    @Override
    public GridCell decodeNode(Integer index) {
        return findCellByIndex(index).orElse(null);
    }

    private int calculateCellIndex(int x, int y) {
        return x + y * width;
    }

    private void setConnections(GridCell cell) {
        for (Direction direction : Direction.values()) {
            if (fourWayDirection && direction.ordinal() % 2 != 0) {
                continue;
            }
            int newX = cell.getPosition().getX() + direction.getX();
            int newY = cell.getPosition().getY() + direction.getY();
            if (areCoordinatesInBounds(newX, newY)) {
                int indexOfCell = encodeNode(cell);
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

    private Optional<GridCell> findCell(GridCell cell) {
        Optional<GridCell> optionalCell;
        try {
            optionalCell = Optional.ofNullable(cells.get(encodeNode(cell)));
        } catch (IndexOutOfBoundsException exception) {
            optionalCell = Optional.empty();
        }
        return optionalCell;
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
                int indexOfCell = encodeNode(cell);
                Integer[] neighbours = fourWayDirection ? new Integer[4] : new Integer[8];
                connections.put(indexOfCell, neighbours);
                setConnections(cell);
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
    public void connect(GridCell node1, GridCell node2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<GridCell> getNeighbours(GridCell cell) {
        Optional<GridCell> gridCell = findCell(cell);
        if (gridCell.isPresent()) {
            Integer indexOfCell = encodeNode(cell);
            return Arrays.stream(connections.get(indexOfCell))
                    .filter(Objects::nonNull)
                    .map(integer -> findCellByIndex(integer).get())
                    .collect(Collectors.toUnmodifiableList());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public GridCell getNode(GridCell gridCell) {
        return cells.stream().filter(cell -> cell.equals(gridCell)).findFirst().orElse(null);
    }

    @Override
    public Graph<GridCell> copy() {
        return null;
    }

    @Override
    public Iterable<GridCell> getAllNodes() {
        return cells;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public GridCell getStartNode() {
        return start;
    }

    @Override
    public void setStartNode(GridCell start) {
        this.start = start;
    }

    @Override
    public boolean isStartNodeSet() {
        return start != null;
    }
}
