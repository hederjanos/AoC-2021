package util.coordinate;

import java.util.*;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private Coordinate(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException();
        }
        setX(coordinate.getX());
        setY(coordinate.getY());
    }

    public Coordinate(String coordinate) {
        String[] input = coordinate.split(",");
        x = Integer.parseInt(input[0]);
        y = Integer.parseInt(input[1]);
    }

    public Coordinate copy() {
        return new Coordinate(this);
    }

    public Coordinate add(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException();
        }
        Coordinate newCoordinate = copy();
        newCoordinate.setX(newCoordinate.getX() + coordinate.getX());
        newCoordinate.setY(newCoordinate.getY() + coordinate.getY());
        return newCoordinate;
    }

    public Coordinate subtract(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException();
        }
        Coordinate newCoordinate = copy();
        newCoordinate.setX(newCoordinate.getX() - coordinate.getX());
        newCoordinate.setY(newCoordinate.getY() - coordinate.getY());
        return newCoordinate;
    }

    public Set<Coordinate> getAdjacentCoordinates() {
        Set<Coordinate> adjacentCoordinates = new HashSet<>();
        Coordinate neighborCoordinate;
        for (Direction direction : Direction.values()) {
            neighborCoordinate = copy();
            neighborCoordinate.setX(neighborCoordinate.getX() + direction.getX());
            neighborCoordinate.setY(neighborCoordinate.getY() + direction.getY());
            adjacentCoordinates.add(neighborCoordinate);
        }
        return adjacentCoordinates;
    }

    public Set<Coordinate> getOrthogonalAdjacentCoordinates() {
        Set<Coordinate> adjacentCoordinates = new HashSet<>();
        Coordinate neighborCoordinate;
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            if (direction.ordinal() % 2 == 0) {
                neighborCoordinate = copy();
                neighborCoordinate.setX(neighborCoordinate.getX() + direction.getX());
                neighborCoordinate.setY(neighborCoordinate.getY() + direction.getY());
                adjacentCoordinates.add(neighborCoordinate);
            }
        }
        return adjacentCoordinates;
    }

    public Set<Coordinate> getCoordinatesInLineBetweenWith(Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        coordinatesInLine.add(this);
        coordinatesInLine.add(end);
        if (this.getX() == end.getX()) {
            coordinatesInLine.addAll(createCoordinatesInVerticalDirection(this, end));
        } else if (this.getY() == end.getY()) {
            coordinatesInLine.addAll(createCoordinatesInHorizontalDirection(this, end));
        } else if ((this.getX() < end.getX() && this.getY() < end.getY()) || (this.getX() > end.getX() && this.getY() > end.getY())) {
            coordinatesInLine.addAll(createCoordinatesInFallingDiagonal(this, end));
        } else if ((this.getX() < end.getX() && this.getY() > end.getY()) || (this.getX() > end.getX() && this.getY() < end.getY())) {
            coordinatesInLine.addAll(createCoordinatesInGrowingDiagonal(this, end));
        }
        return coordinatesInLine;
    }

    private Set<Coordinate> createCoordinatesInVerticalDirection(Coordinate start, Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        int head = Math.min(start.getY(), end.getY());
        Coordinate newEnd = (end.getY() == head) ? start : end;
        while (head < newEnd.getY()) {
            coordinatesInLine.add(new Coordinate(newEnd.getX(), head + 1));
            head++;
        }
        return coordinatesInLine;
    }

    private Set<Coordinate> createCoordinatesInHorizontalDirection(Coordinate start, Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        int head = Math.min(start.getX(), end.getX());
        Coordinate newEnd = (end.getX() == head) ? start : end;
        while (head < newEnd.getX()) {
            coordinatesInLine.add(new Coordinate(head + 1, newEnd.getY()));
            head++;
        }
        return coordinatesInLine;
    }

    private Set<Coordinate> createCoordinatesInFallingDiagonal(Coordinate start, Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        Coordinate head = (start.getX() < end.getX()) ? start : end;
        Coordinate tail = (start.getX() > end.getX()) ? start : end;
        while (head.getX() != tail.getX() - 1) {
            Coordinate newHead = new Coordinate(head.getX() + 1, head.getY() + 1);
            coordinatesInLine.add(newHead);
            head = newHead;
        }
        return coordinatesInLine;
    }

    private Set<Coordinate> createCoordinatesInGrowingDiagonal(Coordinate start, Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        Coordinate head = (start.getX() < end.getX() && start.getY() > end.getY()) ? start : end;
        Coordinate tail = (start.getX() > end.getX() && start.getY() < end.getY()) ? start : end;
        while (head.getX() != tail.getX() - 1) {
            Coordinate newHead = new Coordinate(head.getX() + 1, head.getY() - 1);
            coordinatesInLine.add(newHead);
            head = newHead;
        }
        return coordinatesInLine;
    }

    public boolean isInHorizontalOrVerticalLineWith(Coordinate end) {
        return this.getX() == end.getX() || this.getY() == end.getY();
    }

    public Coordinate isAdjacent(Coordinate coordinate) {
        Coordinate subtract = this.subtract(coordinate);
        if (Math.abs(subtract.getX()) <= 1 && Math.abs(subtract.getY()) <= 1) {
            return null;
        } else {
            return subtract;
        }
    }

    public Coordinate moveByDirection(Direction direction) {
        Coordinate newCoordinate = copy();
        newCoordinate.setX(newCoordinate.getX() + direction.getX());
        newCoordinate.setY(newCoordinate.getY() + direction.getY());
        return newCoordinate;
    }

    public Coordinate moveByCoordinate(Coordinate coordinate) {
        return copy().add(getDirectionEquivalentCoordinate(coordinate));
    }

    private Coordinate getDirectionEquivalentCoordinate(Coordinate coordinate) {
        Coordinate adjusted = new Coordinate(coordinate);
        if (adjusted.getX() > 1) {
            adjusted.setX(adjusted.getX() - 1);
        } else if (adjusted.getX() < -1) {
            adjusted.setX(adjusted.getX() + 1);
        }
        if (adjusted.getY() > 1) {
            adjusted.setY(adjusted.getY() - 1);
        } else if (adjusted.getY() < -1) {
            adjusted.setY(adjusted.getY() + 1);
        }
        return adjusted;
    }

    public int getManhattanDistanceFrom(Coordinate coordinate) {
        return Math.abs(getX() - coordinate.getX()) + Math.abs(getY() - coordinate.getY());
    }

    public int getManhattanDistanceFromHorizontal(int y) {
        return Math.abs(getY() - y);
    }

    public List<Coordinate> calculateMinAndMaxExcisedCoordinatesFromHorizontal(int y, int distance) {
        List<Coordinate> coordinates = new ArrayList<>();
        int distanceFromLine = getManhattanDistanceFromHorizontal(y);
        coordinates.add(new Coordinate(getX() - (distance - distanceFromLine), y));
        coordinates.add(new Coordinate(getX() + (distance - distanceFromLine), y));
        return coordinates;
    }

    public static Comparator<Coordinate> getXOrderComparator() {
        return new XOrder();
    }

    private static class XOrder implements Comparator<Coordinate> {
        public int compare(Coordinate c1, Coordinate c2) {
            if (c1.getX() < c2.getX()) {
                return -1;
            }
            if (c1.getX() > c2.getX()) {
                return +1;
            }
            return Integer.compare(c1.getY(), c2.getY());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

}
