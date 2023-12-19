package com.demo.example.neonkeyboard.ledkeyboard.MyGif.utils;

import com.tenor.android.core.util.AbstractListUtils;
import java.util.Random;


public class SdkListUtils extends AbstractListUtils {
    public static int[] shuffle(int[] iArr) {
        if (iArr.length <= 0) {
            return iArr;
        }
        Random random = RandomCompat.get();
        for (int length = iArr.length - 1; length > 0; length--) {
            int nextInt = random.nextInt(length + 1);
            int i = iArr[nextInt];
            iArr[nextInt] = iArr[length];
            iArr[length] = i;
        }
        return iArr;
    }
}
