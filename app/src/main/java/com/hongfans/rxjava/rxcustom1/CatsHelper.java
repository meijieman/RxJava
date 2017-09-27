package com.hongfans.rxjava.rxcustom1;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/27 22:00
 */
public class CatsHelper{

    interface CutestCatCallback{

        void onCutestCatSaved(Uri uri);

        void onQueryFailed(Exception e);
    }

    Api api = new ApiImpl();


    public void saveTheCutestCat(String query, CutestCatCallback cutestCatCallback){
        api.queryCats(query, new Api.CatsQueryCallback(){
            @Override
            public void onCatListReceived(List<Cat> cats){
                Cat cutest = findCutest(cats);
                api.store(cutest, new Api.StoreCallback(){
                    @Override
                    public void onCatStored(Uri uri){
                        cutestCatCallback.onCutestCatSaved(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception e){
                        cutestCatCallback.onQueryFailed(e);
                    }
                });
            }

            @Override
            public void onError(Exception e){
                cutestCatCallback.onQueryFailed(e);
            }
        });
    }

    private Cat findCutest(List<Cat> cats){
        return Collections.max(cats);
    }

    // -----------------------------------
    ApiWrapper apiWrapper;

    public void saveTheCutestCat(String query, Callback<Uri> cutestCallback){
        apiWrapper.queryCats(query, new Callback<List<Cat>>(){
            @Override
            public void onResult(List<Cat> result){
                Cat cutest = findCutest(result);
                apiWrapper.store(cutest, cutestCallback);
            }

            @Override
            public void onError(Exception e){
                cutestCallback.onError(e);
            }
        });
    }

}
