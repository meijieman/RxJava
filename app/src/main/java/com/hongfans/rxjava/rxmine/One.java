package com.hongfans.rxjava.rxmine;

import android.support.annotation.NonNull;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/29 23:37
 */
public class One implements Comparable<One>{

    private String name;
    private int similar;

    public One(String name, int similar){
        this.name = name;
        this.similar = similar;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getSimilar(){
        return similar;
    }

    public void setSimilar(int similar){
        this.similar = similar;
    }

    @Override
    public int compareTo(@NonNull One o){
        return similar - o.getSimilar();
    }

    @Override
    public String toString(){
        return "One{" +
               "name='" + name + '\'' +
               ", similar=" + similar +
               '}';
    }
}
