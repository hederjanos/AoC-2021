package day._18;

import org.junit.jupiter.api.Test;

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
    }

}
