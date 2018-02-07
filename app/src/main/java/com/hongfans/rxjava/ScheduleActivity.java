package com.hongfans.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        LogUtil.i("click " + v.getId());
        switch(v.getId()) {
            case R.id.btn_1: {
                final Scheduler.Worker worker = Schedulers.newThread().createWorker();
                worker.schedule(new Action0(){
                    int count = 0;

                    @Override
                    public void call(){
                        if(count == 5){
                            worker.unsubscribe();
                            return;
                        }
                        count++;
                        LogUtil.i("aaaaaaaaaaa ");
                        worker.schedule(this);
                    }
                });
            }
            break;
            case R.id.btn_2: {
                final Scheduler.Worker worker = Schedulers.newThread().createWorker();
                Subscription subscription = worker.schedule(new Action0(){
                    int count = 0;

                    @Override
                    public void call(){
                        while(!worker.isUnsubscribed()){
                            count++;
                            LogUtil.i("bbbbbbbbbb ");
                            if(count >= 5){
                                worker.unsubscribe();
                            }
                        }
                    }
                });
            }
            break;
            case R.id.btn_3: {
                Scheduler.Worker worker = Schedulers.newThread().createWorker();
                // 延时执行
                worker.schedule(new Action0(){
                    @Override
                    public void call(){
                        LogUtil.i("ccccccccccc ");
                    }
                }, 500, TimeUnit.MILLISECONDS);

                // 周期执行
                worker.schedulePeriodically(new Action0(){
                    @Override
                    public void call(){
                        LogUtil.i("ddddddddd ");
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
            }
            break;
            case R.id.btn_4:
                // 线程切换
                method1();
                break;
            case R.id.btn_5:

                break;
            case R.id.btn_6:

                break;
            default:

                break;
        }
    }

    private void method1() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.i("tag_rx", "create s " + Thread.currentThread().getName());
                        subscriber.onNext("hello");
                        Log.i("tag_rx", "create e " + Thread.currentThread().getName());
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i("tag_rx", "doOnSubscribe " + Thread.currentThread().getName());
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i("tag_rx", "doOnUnsubscribe " + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.i("tag_rx", "map " + Thread.currentThread().getName());

                        return s + " world ";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("tag_rx", "doOnNext " + Thread.currentThread().getName());
                    }
                })
                .doOnEach(new Action1<Notification<? super String>>() {
                    @Override
                    public void call(Notification<? super String> notification) {
                        Log.i("tag_rx", "doOnEach " + notification.getKind() + ", " + notification.getValue() + ", " + Thread.currentThread().getName());
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("tag_rx", "subscribe " + Thread.currentThread().getName());
                    }
                });
    }
}
