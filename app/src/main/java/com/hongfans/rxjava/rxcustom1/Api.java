package com.hongfans.rxjava.rxcustom1;

import android.net.Uri;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/27 22:00
 */
public interface Api{

    interface CatsQueryCallback{

        void onCatListReceived(List<Cat> cats);

        void onError(Exception e);
    }

    interface StoreCallback{

        void onCatStored(Uri uri);

        void onStoreFailed(Exception e);
    }

    List<Cat> queryCats(String query);

    Uri store(Cat cat);

    void queryCats(String query, CatsQueryCallback catsQueryCallback);

    void store(Cat cat, StoreCallback storeCallback);
}
