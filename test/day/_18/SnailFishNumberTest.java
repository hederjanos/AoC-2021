package day._18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnailFishNumberTest {

    @Test
    void test1() {
        String number = "[[[[[9,8],1],2],3],4]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(number);
        System.out.println(snailFishNumber);
        snailFishNumber.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding:");
        System.out.println("----------------");
        System.out.println(snailFishNumber);
    }

    @Test
    void test2() {
        String number = "[7,[6,[5,[4,[3,2]]]]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(number);
        System.out.println(snailFishNumber);
        snailFishNumber.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding:");
        System.out.println("----------------");
        System.out.println(snailFishNumber);
    }

    @Test
    void test3() {
        String number = "[[6,[5,[4,[3,2]]]],1]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(number);
        System.out.println(snailFishNumber);
        snailFishNumber.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding:");
        System.out.println("----------------");
        System.out.println(snailFishNumber);
    }

    @Test
    void test4() {
        String number = "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(number);
        System.out.println(snailFishNumber);
        snailFishNumber.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding:");
        System.out.println("----------------");
        System.out.println(snailFishNumber);
    }

    @Test
    void test5() {
        String number = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(number);
        System.out.println(snailFishNumber);
        snailFishNumber.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding:");
        System.out.println("----------------");
        System.out.println(snailFishNumber);
    }

    @Test
    void test6() {
        String number1 = "[[[[4,3],4],4],[7,[[8,4],9]]]";
        String number2 = "[1,1]";
        SnailFishNumber snailFishNumber1 = SnailFishNumber.parseANumber(number1);
        SnailFishNumber snailFishNumber2 = SnailFishNumber.parseANumber(number2);
        System.out.println(snailFishNumber1);
        SnailFishNumber add = snailFishNumber1.add(snailFishNumber2);
        System.out.println("----------------");
        System.out.println("after add:");
        System.out.println("----------------");
        System.out.println(add);
        add.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding 1:");
        System.out.println("----------------");
        System.out.println(add);
        add.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding 2:");
        System.out.println("----------------");
        System.out.println(add);
        add.split();
        System.out.println("----------------");
        System.out.println("after splitting 1:");
        System.out.println("----------------");
        System.out.println(add);
        add.split();
        System.out.println("----------------");
        System.out.println("after splitting 2:");
        System.out.println("----------------");
        System.out.println(add);
        add.explode(true);
        System.out.println("----------------");
        System.out.println("after exploding 3:");
        System.out.println("----------------");
        System.out.println(add);
    }

    @Test
    void test7() {
        String string = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(3488, snailFishNumber.magnitude());
    }

    @Test
    void test8() {
        String string = "[[[[5,0],[7,4]],[5,5]],[6,6]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(1137, snailFishNumber.magnitude());
    }

    @Test
    void test9() {
        String string = "[[[[3,0],[5,3]],[4,4]],[5,5]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(791, snailFishNumber.magnitude());
    }

    @Test
    void test10() {
        String string = "[[[[1,1],[2,2]],[3,3]],[4,4]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(445, snailFishNumber.magnitude());
    }

    @Test
    void test11() {
        String string = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(1384, snailFishNumber.magnitude());
    }

    @Test
    void test12() {
        String string = "[[1,2],[[3,4],5]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(143, snailFishNumber.magnitude());
    }

    @Test
    void test13() {
        String string = "[[9,1],[1,9]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(129, snailFishNumber.magnitude());
    }

    @Test
    void test14() {
        String string = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]";
        SnailFishNumber snailFishNumber = SnailFishNumber.parseANumber(string);
        assertEquals(3488, snailFishNumber.magnitude());
    }

}
