package com.xdf.kb.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class ColorUtils {

    public static @ColorInt int getRandomColor() {
        Random random = new Random();
        int randomInt = random.nextInt(0xFFFFFF);
        return Color.parseColor(String.valueOf(randomInt));
    }
}
