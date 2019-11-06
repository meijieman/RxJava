package com.major.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        method1();

//        method2();

//        method3();


        Observable.just(1, 2, 3)
                .collect(new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<List<String>, Integer>() {
                    @Override
                    public void accept(List<String> strings, Integer integer) throws Exception {
                        strings.add(integer + "");
                    }
                })
                .subscribe();
    }

    private void method3() {
        Observable<String> o1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtil.i("create " + Thread.currentThread().getName());
                emitter.onNext("bilibili");
                emitter.onComplete();
            }
        });

        Observable<Boolean> o2 = Observable.just(false);


        Observable
                .zip(o1, o2, new BiFunction<String, Boolean, Integer>() {
                    @Override
                    public Integer apply(String s, Boolean aBoolean) throws Exception {
                        LogUtil.i("zip " + s + aBoolean + ", " + Thread.currentThread().getName());
                        return 100;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i("onNext " + integer + ", " + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("onError " + e + ", " + Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i("onComplete " + ", " + Thread.currentThread().getName());

                    }
                });
    }

    private void method2() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        LogUtil.i("subscribe " + Thread.currentThread().getName());

                        emitter.onNext("abcde");
                        emitter.onComplete();
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        LogUtil.i("apply " + s + ", " + Thread.currentThread().getName());

                        return null;
                    }
                })
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) throws Exception {
                        LogUtil.i("apply onErrorReturn " + throwable + ", " + Thread.currentThread().getName());

                        return "error";
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + " more " + ", " + Thread.currentThread().getName();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.i("onNext " + s + ", " + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("onError " + e + ", " + Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i("onComplete " + ", " + Thread.currentThread().getName());

                    }
                });
    }


    private void method1() {
        Observable
                .create((ObservableOnSubscribe<String>) emitter -> {
                    emitter.onNext("china");
//                        emitter.onError(new Throwable("ex"));
                    emitter.onNext("china2");

                    emitter.onComplete();
                })
                .flatMap((Function<String, ObservableSource<String>>) s -> {
                    s += " aha";
                    return Observable.just(s);
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.i("onSubscribe " + d);
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.i("onNext " + s);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i("onError " + e);

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i("onComplete ");

                    }
                });
    }
}
