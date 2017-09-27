package com.hongfans.rxjava.rxcustom1;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * TODO
 * Created by MEI on 2017/9/21.
 * NotRxJava懒人专用指南 http://www.devtf.cn/?p=323
 */

public class Cat implements Comparable<Cat>{

    Bitmap image;
    int cuteness;

    @Override
    public int compareTo(@NonNull Cat o){
        return Integer.compare(cuteness, o.cuteness);
    }
}
