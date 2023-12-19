package com.demo.example.neonkeyboard.ledkeyboard.MyGif.utils;

import android.os.Build;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class RandomCompat {
    
    public static Random get() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ThreadLocalRandom.current();
        }
        return (Random) new ThreadLocal<Random>() { 
            @Override 
            public Random initialValue() {
                return new Random();
            }
        }.get();
    }
}
