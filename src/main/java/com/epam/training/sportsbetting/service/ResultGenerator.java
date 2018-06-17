package com.epam.training.sportsbetting.service;

import java.util.Random;

public class ResultGenerator {
    private static final Random rand = new Random();

    public boolean isWon() {
        return rand.nextBoolean();
    }
}
