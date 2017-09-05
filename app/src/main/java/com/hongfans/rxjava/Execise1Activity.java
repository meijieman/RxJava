package com.hongfans.rxjava;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Execise1Activity extends AppCompatActivity implements View.OnClickListener{

    private Subscription mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execise1);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    protected void onDestroy(){
        LogUtil.i("onDestroy" + mSubscribe.isUnsubscribed());

        if(mSubscribe != null && !mSubscribe.isUnsubscribed()){
            LogUtil.i("取消订阅");
            mSubscribe.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btn_1:
                method1();
                break;
            case R.id.btn_2:
                method2();

                break;
            case R.id.btn_3:

                break;
        }
    }


    private void method2(){
        // 耗时操作
        mSubscribe = Observable
                .create(new Observable.OnSubscribe<Object>(){
                    @Override
                    public void call(Subscriber<? super Object> subscriber){
                        // 耗时操作
                        for(int i = 0; i < 5; i++){
                            if(mSubscribe.isUnsubscribed()){
                                LogUtil.i("call 取消订阅了");
                            } else {
                                SystemClock.sleep(2000);
                                subscriber.onNext(new Object());
                                LogUtil.i("call " + i + ", " + Thread.currentThread().getName());
                            }
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    private void method1(){
        Observable
                .create(new Observable.OnSubscribe<String>(){
                    @Override
                    public void call(Subscriber<? super String> subscriber){
                        subscriber.onNext("great");
                        LogUtil.i("call " + Thread.currentThread().getName());
                        subscriber.onCompleted();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>(){
                    @Override
                    public String call(String s){
                        LogUtil.i("map call " + Thread.currentThread().getName());

                        return s + ".log";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>(){
                    @Override
                    public void call(String s){
                        LogUtil.i("doOnNext call " + s + ", " + Thread.currentThread().getName());

                    }
                })
                .observeOn(Schedulers.computation())
                .flatMap(new Func1<String, Observable<String>>(){
                    @Override
                    public Observable<String> call(String s){
                        LogUtil.i("flatMap call " + Thread.currentThread().getName());
                        return Observable.just(s + ".bak");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(/*new Subscriber<String>(){
                    @Override
                    public void onCompleted(){
                        LogUtil.i("onCompleted " + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(Throwable e){
                        LogUtil.i("onError " + Thread.currentThread().getName());

                    }

                    @Override
                    public void onNext(String s){
                        LogUtil.i("onNext " + s + ", " + Thread.currentThread().getName());

                    }
                }*/);


    }
}
