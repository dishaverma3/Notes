package com.example.notes.util;

import android.graphics.Color;

import com.example.notes.R;

public class ColorUtil {

    int[] colorList = new int[]{
            R.color.listtem1,
            R.color.listtem2,
            R.color.listtem3,
            R.color.listtem4,
            R.color.listtem5,
            R.color.listtem6,
            R.color.listtem7,
            R.color.listtem8,
            R.color.listtem9,

    };

    public int getColors(int index) {

        if(index >= 0 && index < 9)
            return colorList[index];
        return colorList[index%9];
    }
}
