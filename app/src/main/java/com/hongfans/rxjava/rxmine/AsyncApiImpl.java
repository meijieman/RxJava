package com.hongfans.rxjava.rxmine;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/30 0:05
 */
public class AsyncApiImpl implements AsyncApi{

    Api api = new ApiImpl();


    @Override
    public void meetingSome(OnMeetingListener listener){
        new Thread(){
            @Override
            public void run(){
                try{
                    List<One> ones = api.meetingSome();
                    listener.onSuccess(ones);
                } catch(Exception e){
                    e.printStackTrace();
                    listener.onFailure(e);
                }
            }
        }.start();
    }

    @Override
    public void findTheOne(List<One> ones, OnFindTheOne listener){
        new Thread(){
            @Override
            public void run(){
                try{
                    One theOne = api.findTheOne(ones);
                    listener.onSuccess(theOne);
                } catch(Exception e){
                    e.printStackTrace();
                    listener.onFailure(e);
                }
            }
        }.start();
    }

    @Override
    public String happyEnding(One one){
        return "the end";
    }
}
