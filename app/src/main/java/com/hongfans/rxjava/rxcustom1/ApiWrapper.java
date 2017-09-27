package com.hongfans.rxjava.rxcustom1;

import android.net.Uri;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/27 22:18
 */
public class ApiWrapper{

    Api api;

    public void queryCats(String query, Callback<List<Cat>> catCallback){
        api.queryCats(query, new Api.CatsQueryCallback(){
            @Override
            public void onCatListReceived(List<Cat> cats){
                catCallback.onResult(cats);
            }

            @Override
            public void onError(Exception e){
                catCallback.onError(e);
            }
        });
    }

    public void store(Cat cat, Callback<Uri> uriCallback){
        api.store(cat, new Api.StoreCallback(){
            @Override
            public void onCatStored(Uri uri){
                uriCallback.onResult(uri);
            }

            @Override
            public void onStoreFailed(Exception e){
                uriCallback.onError(e);
            }
        });
    }


}
