package com.xdf.kb.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/30 0030.
 * 颜色工具
 */
public class ColorUtils {

    public static @ColorInt int getRandomColor() {
        Random random = new Random();
        int randomInt = random.nextInt(0xFFFFFF);
        String colorStr = Integer.toHexString(randomInt);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(colorStr);
        while (stringBuilder.length() < 6) {
            stringBuilder.insert(0,"0");
        }
        return Color.parseColor("#"+stringBuilder.toString());
    }
}
