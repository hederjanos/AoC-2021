package day._18;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SnailFishSolverTest {

    @Test
    void test1() {
        SnailFishSolver snailFishSolver = new SnailFishSolver("example1.txt");
        snailFishSolver.solvePartOne();
        System.out.println(snailFishSolver.getResult().toString());
        Assertions.assertEquals(3488, snailFishSolver.getResult().magnitude());
    }

    @Test
    void test2() {
        SnailFishSolver snailFishSolver = new SnailFishSolver("example2.txt");
        snailFishSolver.solvePartOne();
        System.out.println(snailFishSolver.getResult().toString());
        Assertions.assertEquals(4140, snailFishSolver.getResult().magnitude());
    }

}
