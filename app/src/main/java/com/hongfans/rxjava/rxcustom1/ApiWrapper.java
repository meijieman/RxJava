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

    // ===============================================

    public AsyncJob<List<Cat>> queryCats(String query){
        return new AsyncJob<List<Cat>>(){
            @Override
            public void start(Callback<List<Cat>> catsCallback){
                api.queryCats(query, new Api.CatsQueryCallback(){
                    @Override
                    public void onCatListReceived(List<Cat> cats){
                        catsCallback.onResult(cats);
                    }

                    @Override
                    public void onError(Exception e){
                        catsCallback.onError(e);
                    }
                });
            }
        };
    }

    public AsyncJob<Uri> store(Cat cat){
        return new AsyncJob<Uri>(){
            @Override
            public void start(Callback<Uri> callback){
                api.store(cat, new Api.StoreCallback(){
                    @Override
                    public void onCatStored(Uri uri){
                        callback.onResult(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception e){
                        callback.onError(e);
                    }
                });
            }
        };
    }

}
