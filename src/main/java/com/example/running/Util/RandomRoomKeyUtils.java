package com.example.running.Util;

import java.util.Random;

public class RandomRoomKeyUtils {
    public static final String[] POOL = {
            "A", "P", "E", "1", "2", "F", "W", "B", "C",
            "0", "G", "X", "Y", "Z", "T", "J", "9", "3",
            "4", "Q", "8", "6", "H", "I", "R", "S", "D",
            "V", "M", "5", "N", "O", "U", "K", "L", "7"};

    public static String GetRandomKey() {
        Random random = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            res.append(POOL[random.nextInt(POOL.length)]);
        }
        return res.toString();
    }
}
