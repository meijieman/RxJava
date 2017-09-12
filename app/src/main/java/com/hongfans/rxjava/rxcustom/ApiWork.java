package com.hongfans.rxjava.rxcustom;

import android.net.Uri;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:51
 */
public class ApiWork{

    private ApiAsync api = new ApiAsync();


    public AsyncWork<List<News>> getNewsList(final String tag){
        return new AsyncWork<List<News>>(){
            @Override
            public void start(Callback<List<News>> call){
                api.getNewsList(tag, call);
            }
        };
    }

    public AsyncWork<Uri> save(final News news){
        return new AsyncWork<Uri>(){
            @Override
            public void start(Callback<Uri> call){
                api.save(news, call);
            }
        };
    }

}
