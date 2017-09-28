package com.hongfans.rxjava.rxcustom1;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/27 22:57
 */
public class ApiImpl implements Api{

    // 模拟数据

    @Override
    public List<Cat> queryCats(String query){
        List<Cat> list = new ArrayList<>();
        {
            Cat cat = new Cat();
            cat.cuteness = 88;
            list.add(cat);
        }
        {
            Cat cat = new Cat();
            cat.cuteness = 100;
            list.add(cat);
        }
        {
            Cat cat = new Cat();
            cat.cuteness = 66;
            list.add(cat);
        }
        return list;
    }

    @Override
    public Uri store(Cat cat){
        return Uri.parse("cat://" + cat.hashCode());
    }

    @Override
    public void queryCats(String query, CatsQueryCallback catsQueryCallback){
        try{
            List<Cat> cats = queryCats(query);
            catsQueryCallback.onCatListReceived(cats);
        } catch(Exception e){
            e.printStackTrace();
            catsQueryCallback.onError(e);
        }
    }


    @Override
    public void store(Cat cat, StoreCallback storeCallback){
        try{
            Uri store = store(cat);
            storeCallback.onCatStored(store);
        } catch(Exception e){
            e.printStackTrace();
            storeCallback.onStoreFailed(e);
        }

    }
}
