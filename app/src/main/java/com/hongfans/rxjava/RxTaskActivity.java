package com.hongfans.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.rxtask.RxTask;
import com.hongfans.rxjava.rxtask.RxTaskManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxTaskActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_task);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LogUtil.i("click " + v.getId());
        switch (v.getId()) {
            case R.id.btn_1:
                RxTask.doOnIOThread(new RxTask.IOTask() {
                    @Override
                    public void onIOThread() {
                        LogUtil.i("运行于 io thread " + Thread.currentThread().getName());
                    }
                });
                break;
            case R.id.btn_2:
                RxTask.doOnUIThread(new RxTask.UITask() {
                    @Override
                    public void onUIThread() {
                        LogUtil.i("运行于 ui thread " + Thread.currentThread().getName());
                    }
                });
                break;
            case R.id.btn_3:
                Observable
                        .create(new Observable.OnSubscribe<Void>() {
                            @Override
                            public void call(Subscriber<? super Void> subscriber) {
                                LogUtil.i("运行于 create " + Thread.currentThread().getName());
                                if (true) {
                                    throw new RuntimeException("自定义异常");
                                }
                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.computation())
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends Void>>() {
                            @Override
                            public Observable<? extends Void> call(Throwable throwable) {
                                LogUtil.i("处理异常 " + throwable + ", " + Thread.currentThread().getName());

//                                return Observable.just(null);
                                return Observable.empty();
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                LogUtil.i("运行于  subscribe " + Thread.currentThread().getName());

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                                LogUtil.i("异常 " + throwable + ", " + Thread.currentThread().getName());
                            }
                        });
                break;
            case R.id.btn_4:
                RxTaskManager.getInstance().doOnUIThreadDelay(new RxTaskManager.UITask<String>() {
                    @Override
                    public void doOnUIThread() {
                        LogUtil.i("运行于 ui " + Thread.currentThread().getName());
                    }
                }, 1, TimeUnit.SECONDS);
                break;
            case R.id.btn_5:
                RxTaskManager.getInstance().doOnIOThread(new RxTaskManager.IOTask<String>() {
                    @Override
                    public void doOnIOThread() {
                        LogUtil.i("运行于 io " + Thread.currentThread().getName());
                    }
                });
                break;
            case R.id.btn_6:
                RxTaskManager.getInstance().destroy();
                break;
            case R.id.btn_7:

                break;
            default:
                break;
        }
    }
}
