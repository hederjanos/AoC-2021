package util.coordinate;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException();
        }
        setX(coordinate.getX());
        setY(coordinate.getY());
    }

    public Coordinate(String coordinate) {
        String[] input = coordinate.split(",");
        this.x = Integer.parseInt(input[0]);
        this.y = Integer.parseInt(input[1]);
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
        for (int i = 0; i < 4; i++) {
            neighborCoordinate = copy();
            neighborCoordinate.setX(neighborCoordinate.getX() + directions[i].getX());
            neighborCoordinate.setY(neighborCoordinate.getY() + directions[i].getY());
            adjacentCoordinates.add(neighborCoordinate);
        }
        return adjacentCoordinates;
    }

    public Set<Coordinate> getCoordinatesInLineBetweenCoordinates(Coordinate end) {
        Set<Coordinate> coordinatesInLine = new HashSet<>();
        coordinatesInLine.add(this);
        coordinatesInLine.add(end);
        if (this.getX() == end.getX()) {
            createCoordinatesInVerticalDirection(this, end, coordinatesInLine);
        } else if (this.getY() == end.getY()) {
            createCoordinatesInHorizontalDirection(this, end, coordinatesInLine);
        } else if ((this.getX() < end.getX() && this.getY() < end.getY()) || (this.getX() > end.getX() && this.getY() > end.getY())) {
            createCoordinatesInFallingDiagonal(this, end, coordinatesInLine);
        } else if ((this.getX() < end.getX() && this.getY() > end.getY()) || (this.getX() > end.getX() && this.getY() < end.getY())) {
            createCoordinatesInGrowingDiagonal(this, end, coordinatesInLine);
        }
        return coordinatesInLine;
    }

    private void createCoordinatesInVerticalDirection(Coordinate start, Coordinate end, Set<Coordinate> coordinatesInLine) {
        int head = Math.min(start.getY(), end.getY());
        Coordinate newEnd = (end.getY() == head) ? start : end;
        while (head < newEnd.getY()) {
            coordinatesInLine.add(new Coordinate(newEnd.getX(), head + 1));
            head++;
        }
    }

    private void createCoordinatesInHorizontalDirection(Coordinate start, Coordinate end, Set<Coordinate> coordinatesInLine) {
        int head = Math.min(start.getX(), end.getX());
        Coordinate newEnd = (end.getX() == head) ? start : end;
        while (head < newEnd.getX()) {
            coordinatesInLine.add(new Coordinate(head + 1, newEnd.getY()));
            head++;
        }
    }

    private void createCoordinatesInFallingDiagonal(Coordinate start, Coordinate end, Set<Coordinate> coordinatesInLine) {
        Coordinate head = (start.getX() < end.getX()) ? start : end;
        Coordinate tail = (start.getX() > end.getX()) ? start : end;
        while (head.getX() != tail.getX() - 1) {
            Coordinate newHead = new Coordinate(head.getX() + 1, head.getY() + 1);
            coordinatesInLine.add(newHead);
            head = newHead;
        }
    }

    private void createCoordinatesInGrowingDiagonal(Coordinate start, Coordinate end, Set<Coordinate> coordinatesInLine) {
        Coordinate head = (start.getX() < end.getX() && start.getY() > end.getY()) ? start : end;
        Coordinate tail = (start.getX() > end.getX() && start.getY() < end.getY()) ? start : end;
        while (head.getX() != tail.getX() - 1) {
            Coordinate newHead = new Coordinate(head.getX() + 1, head.getY() - 1);
            coordinatesInLine.add(newHead);
            head = newHead;
        }
    }

    public boolean isInHorizontalOrVerticalLineWith(Coordinate end) {
        return this.getX() == end.getX() || this.getY() == end.getY();
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
