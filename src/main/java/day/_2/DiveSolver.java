package day._2;

import util.common.Solver;

public class DiveSolver extends Solver<Integer> {

    public DiveSolver(String filename) {
        super(filename);
    }

    @Override
    public Integer solvePartOne() {
        Submarine submarine = new Submarine();
        for (String line : puzzle) {
            Command command = new Command(line);
            switch (command.getDirection()) {
                case UP:
                    submarine.rise(command.getValue());
                    break;
                case DOWN:
                    submarine.sink(command.getValue());
                    break;
                case FORWARD:
                    submarine.moveForward(command.getValue());
                    break;
            }
        }
        return submarine.getHorizontal() * submarine.getDepth();
    }

    @Override
    protected Integer solvePartTwo() {
        Submarine submarine = new Submarine();
        for (String line : puzzle) {
            Command command = new Command(line);
            switch (command.getDirection()) {
                case UP:
                    submarine.aimUp(command.getValue());
                    break;
                case DOWN:
                    submarine.aimDown(command.getValue());
                    break;
                case FORWARD:
                    submarine.moveForwardWithAim(command.getValue());
                    break;
            }
        }
        return submarine.getHorizontal() * submarine.getDepth();
    }

}
