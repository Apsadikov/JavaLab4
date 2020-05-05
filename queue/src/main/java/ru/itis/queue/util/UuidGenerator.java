package ru.itis.queue.util;

import java.util.Random;

public class UuidGenerator {
    public static String generate() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 255;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
