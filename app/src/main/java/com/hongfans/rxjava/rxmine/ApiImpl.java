package com.hongfans.rxjava.rxmine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/29 23:43
 */
public class ApiImpl implements Api{

    @Override
    public List<One> meetingSome(){
        List<One> list = new ArrayList<>();
        Random random = new Random();
        try{
            for(int i = 0; i < 10; i++){
                One one = new One("somebody " + i, random.nextInt(100));
                list.add(one);
                Thread.sleep(random.nextInt(100) * 10);
            }
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public One findTheOne(List<One> list){
        try{
            Thread.sleep(1000 * 2);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return Collections.max(list);
    }

    @Override
    public String happyEnding(One one){
        try{
            Thread.sleep(1000 * 5);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return one.getName() + " & me have a happyEnding";
    }
}
