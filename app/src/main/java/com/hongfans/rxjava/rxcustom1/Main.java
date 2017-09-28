package com.hongfans.rxjava.rxcustom1;

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
//        Uri somecat = new CatsHelper().saveTheCutestCat("somecat");

        new AsyncJob<String>(){
            @Override
            public void start(Callback<String> callback){
                new Thread(){
                    @Override
                    public void run(){
                        String s = "file";
                        callback.onResult(s + ".log");
                        System.out.println("-------------- start " + Thread.currentThread().getName());
                    }
                }.start();

            }
        }.start(new Callback<String>(){
            @Override
            public void onResult(String result){
                System.out.println("-------------- " + result + ", " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Exception e){
                System.out.println("-------------- " + e + ", " + Thread.currentThread().getName());

            }
        });

    }
}
