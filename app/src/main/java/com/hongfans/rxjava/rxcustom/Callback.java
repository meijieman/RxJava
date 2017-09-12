package com.hongfans.rxjava.rxcustom;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:52
 */
public interface Callback<T>{

    void onSuccess(T list);

    void onFailure(Exception e);
}
