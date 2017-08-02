package com.hongfans.rxjava.operation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.LogUtil;
import com.hongfans.rxjava.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * 1. 创建操作
 * <p>
 * Create, Defer,
 * Empty 创建一个不发射任何数据但是正常终止的Observable
 * Nerver 创建一个不发射数据也不终止的Observable
 * Throw 创建一个不发射数据以一个错误终止的Observable
 * , From, Interval, Just, Range, Repeat, Start, Timer
 */
public class CreatingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1: {
                // create 默认不在任何特定的调度器上执行
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 5; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                }).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i("onNext " + integer);
                    }
                });
            }
            break;
            case R.id.btn_2: {
                // 直到有观察者订阅时才创建Observable， 并且为每个观察者创建一个新的Observable
                Observable.defer(new Func0<Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call() {

                        return null;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtil.i("call " + integer);
                    }
                });
            }
            break;
            case R.id.btn_3:

                break;
            case R.id.btn_4:

                break;
            case R.id.btn_5:

                break;
        }
    }
}
