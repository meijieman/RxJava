package com.hongfans.rxjava.rxcustom;

import android.net.Uri;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:23
 */
public class ApiAsync{

    private Api api = new ApiImpl();
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public void getNewsList(final String tag, final Callback<List<News>> callback){
        executor.execute(new Runnable(){
            @Override
            public void run(){
                try{
                    callback.onSuccess(api.getNewsList(tag));
                } catch(Exception e){
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }
        });
    }

    public void save(final News news, final Callback<Uri> callback){
        executor.execute(new Runnable(){
            @Override
            public void run(){
                try{
                    Uri save = api.save(news);
                    callback.onSuccess(save);
                } catch(Exception e){
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }
        });
    }

}

