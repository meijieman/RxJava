package com.hongfans.rxjava.rxmine;

import java.util.List;

import static com.hongfans.rxjava.rxmine.Main.print;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/30 0:18
 */
public class LifeHelper{

    public void myLife(){
        Api api = new ApiImpl();
        List<One> ones = api.meetingSome();
        print("ones " + ones);
        One theOne = api.findTheOne(ones);
        print("theOne " + theOne);
        String s = api.happyEnding(theOne);
        print("s " + s);
    }

    public void myLife1(){
        AsyncApi api = new AsyncApiImpl();
        api.meetingSome(new AsyncApi.OnMeetingListener(){
            @Override
            public void onSuccess(List<One> ones){
                api.findTheOne(ones, new AsyncApi.OnFindTheOne(){
                    @Override
                    public void onSuccess(One one){
                        String s = api.happyEnding(one);
                        print("s " + s);
                    }

                    @Override
                    public void onFailure(Exception e){
                        print("onFailure " + e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e){
                print("onFailure " + e);
            }
        });

    }

}
