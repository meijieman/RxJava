package com.hongfans.rxjava.rxmine;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/30 0:05
 */
public interface AsyncApi{

    void meetingSome(OnMeetingListener listener);

    void findTheOne(List<One> ones, OnFindTheOne listener);

    String happyEnding(One one);

    interface OnMeetingListener{

        void onSuccess(List<One> ones);

        void onFailure(Exception e);
    }

    interface OnFindTheOne{

        void onSuccess(One one);

        void onFailure(Exception e);
    }
}
