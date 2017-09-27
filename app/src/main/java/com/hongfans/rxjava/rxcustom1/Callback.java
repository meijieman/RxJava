package com.hongfans.rxjava.rxcustom1;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/28 7:47
 */
public interface Callback<T>{

    void onResult(T result);

    void onError(Exception e);
}
