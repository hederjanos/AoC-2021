package day._17;

import util.coordinate.Coordinate;

public class Probe {

    private Coordinate position;
    private Coordinate velocity;

    public Probe(Coordinate position, Coordinate velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Coordinate getVelocity() {
        return velocity;
    }

    public void setVelocity(Coordinate velocity) {
        this.velocity = velocity;
    }

}
