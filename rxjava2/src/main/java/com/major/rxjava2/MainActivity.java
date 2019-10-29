package com.major.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
