package com.hongfans.rxjava.rxcustom;

import android.net.Uri;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:12
 */
public class ApiImpl implements Api{

    @Override
    public List<News> getNewsList(String tag){

        List<News> list = new ArrayList<>();
        list.add(new News("100", "第一条新闻 " + tag));
        list.add(new News("102", "第二条新闻 " + tag));
        SystemClock.sleep(2000);
        return list;
    }

    @Override
    public Uri save(News news){
        SystemClock.sleep(2000);
        return null;
    }
}
