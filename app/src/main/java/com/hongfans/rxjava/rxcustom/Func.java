package com.hongfans.rxjava.rxcustom;

/**
 * @desc: 类型变换
 * @author: Major
 * @since: 2017/9/12 23:37
 */
public interface Func<T, R>{

    R call(T t);

}
