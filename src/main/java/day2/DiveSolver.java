package day2;

import util.Solver;

public class DiveSolver extends Solver<Integer> {

    public DiveSolver(String filename) {
        super(filename);
    }

    @Override
    public Integer solve() {
        Position position = new Position(0, 0);
        for (String puzzleLine : puzzle) {
            String[] move = puzzleLine.split(" ");
            switch (Direction.valueOf(move[0].toUpperCase())) {
                case UP:
                    position.rise(Integer.parseInt(move[1]));
                    break;
                case DOWN:
                    position.sink(Integer.parseInt(move[1]));
                    break;
                case FORWARD:
                    position.moveForward(Integer.parseInt(move[1]));
                    break;
            }
        }
        return position.getHorizontal() * position.getDepth();
    }

    @Override
    public void printResult() {
        System.out.println(solve());
    }
}


