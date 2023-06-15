package org.core;


import java.util.Random;

public class TestDataGenerator {
    public static String generateUniqueUsername() {
        Random random = new Random();
        long randomNum = random.nextInt(900000000) + 1000000000; // Generate a 10-digit number
        return "QATest_" + randomNum;
    }

    public static String generateUniqueExternalId() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000000) + 1000000000);
    }

}
