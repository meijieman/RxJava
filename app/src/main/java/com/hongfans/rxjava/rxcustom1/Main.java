package com.hongfans.rxjava.rxcustom1;

import android.net.Uri;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/27 22:55
 */
public class Main{


    public static void main(String[] args){
        Main main = new Main();
        main.method1();
    }

    public void method1(){
        // 需要在 android 线程中运行
        Uri somecat = new CatsHelper().saveTheCutestCat("somecat");


    }
}
