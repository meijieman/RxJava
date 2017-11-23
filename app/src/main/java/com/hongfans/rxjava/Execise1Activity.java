package com.hongfans.rxjava;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class Execise1Activity extends AppCompatActivity implements View.OnClickListener {

    private Subscription mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execise1);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        LogUtil.i("onDestroy" + mSubscribe.isUnsubscribed());

        if (mSubscribe != null && !mSubscribe.isUnsubscribed()) {
            LogUtil.i("取消订阅");
            mSubscribe.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                method1();
                break;
            case R.id.btn_2:
                method2();
                break;
            case R.id.btn_3:
                // 每个5s重试
                Observable
                        .create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                LogUtil.i("tag_aaa invoke call");
                                if (true) {
                                    throw new RuntimeException("mock exc");
                                }
                                subscriber.onNext("some message");
                                subscriber.onCompleted();
                            }
                        })
                        .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                            @Override
                            public Observable<?> call(Observable<? extends Throwable> observable) {
                                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Throwable throwable) {
                                        LogUtil.i("tag_aaa 重试 " + throwable);
                                        // 判断错误类型
                                        if (throwable instanceof UnknownHostException) {
                                            return Observable.error(throwable);
                                        }

                                        // 加入重试超时
                                        return Observable
                                                .just(throwable)
                                                .zipWith(Observable.range(1, 5), new Func2<Throwable, Integer, Integer>() {
                                                    @Override
                                                    public Integer call(Throwable throwable, Integer integer) {
                                                        return integer;
                                                    }
                                                })
                                                .flatMap(new Func1<Integer, Observable<? extends Long>>() {
                                                    @Override
                                                    public Observable<? extends Long> call(Integer retryCount) {
                                                        LogUtil.i("tag_aaa flatMap retryCount " + retryCount);
                                                        return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                                                    }
                                                });
//                                        return Observable.timer(5, TimeUnit.SECONDS);
//                                        return Observable.just(throwable).delay(5, TimeUnit.SECONDS);

                                    }
                                });
                            }
                        })
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i("tag_aaa invoke onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i("tag_aaa invoke onError " + e);
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtil.i("tag_aaa invoke onNext " + s);
                            }
                        });
                break;
            case R.id.btn_4:
                // toList()
                Observable<String> just = Observable.just("1");
                Observable<List<String>> listObservable = just.toList();
                listObservable.subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {

                    }
                });
                break;
            case R.id.btn_5:
                // concat 和 first 可以实现有缓存去缓存，没缓存取网络数据

                // distinct() 去重   对象的唯一性判断是通过复写 equal() 和 hashCode() 来实现
                // filter() 过滤

                // merge 可以选择多个 Observable 中第一个执行完成的 Observable
                break;
            case R.id.btn_6:
                Observable
                        .create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                throw new NullPointerException("自定义 NPE");
                            }
                        })
                        .retry(3)
                        .flatMap(new Func1<Integer, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(Integer integer) {
                                return Observable.just(integer);
                            }
                        })
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i("onError");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                LogUtil.i("onNext " + integer);
                            }
                        });
                break;
            case R.id.btn_7:
                // retryWhen
                Observable
                        .create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                subscriber.onNext("first");
                                subscriber.onNext("second");
                                subscriber.onNext("third " + 3 / 0);
                                subscriber.onCompleted();
                            }
                        })
                        .retry(3)
                        /*.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                            @Override
                            public Observable<?> call(Observable<? extends Throwable> observable) {
                                return observable
                                        .zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Object>() {
                                            @Override
                                            public Object call(Throwable throwable, Integer integer) {
                                                return integer;
                                            }
                                        });
                            }
                        })*/
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.i("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.i("onError " + e);
                            }

                            @Override
                            public void onNext(String s) {
                                LogUtil.i("onNext " + s);
                            }
                        });
                break;
        }
    }


    private void method2() {
        // 耗时操作
        mSubscribe = Observable
                .create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        // 耗时操作
                        for (int i = 0; i < 5; i++) {
                            if (mSubscribe.isUnsubscribed()) {
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

    private void method1() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("great");
                        LogUtil.i("call " + Thread.currentThread().getName());
                        subscriber.onCompleted();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        LogUtil.i("map call " + Thread.currentThread().getName());

                        return s + ".log";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.i("doOnNext call " + s + ", " + Thread.currentThread().getName());

                    }
                })
                .observeOn(Schedulers.computation())
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
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
