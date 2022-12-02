package day._17;

import util.coordinate.Coordinate;

public class Probe {

    private Coordinate position;
    private Coordinate velocity;

    public Probe(Coordinate position, Coordinate velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void fly() {
        setPosition(position.add(velocity));
        setVelocity(getNewVelocity());
    }

    private Coordinate getNewVelocity() {
        int vy = velocity.getY() - 1;
        int vx = 0;
        if (velocity.getX() > 0) {
            vx = velocity.getX() - 1;
        } else if (velocity.getX() < 0) {
            vx = velocity.getX() + 1;
        }
        return new Coordinate(vx, vy);
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
