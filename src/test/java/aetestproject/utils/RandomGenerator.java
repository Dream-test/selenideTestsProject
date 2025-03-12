package aetestproject.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    public int getOption(int optionNumber) {
        return ThreadLocalRandom.current().nextInt(0, optionNumber);
    }
}
