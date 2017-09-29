package com.hongfans.rxjava.rxmine;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/29 23:40
 */
public interface Api{

    List<One> meetingSome();

    One findTheOne(List<One> list);

    String happyEnding(One one);
}
