package org.webproject;

import java.util.Random;

public class RandomGenerator {

    public int checkBoxNumber(int checkBoxQuantity) {
        Random random = new Random();
        return (random.nextInt() % checkBoxQuantity) + 1;
    }
}
