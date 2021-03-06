package com.hongfans.rxjava.rxtask;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * RxJava 线程切换替代 Thread 和 Handler
 * Created by MEI on 2017/8/2.
 * <p>
 * http://shiyiliang.cn/2017/07/09/RxJava%E7%BA%BF%E7%A8%8B%E5%88%87%E6%8D%A2%E4%BB%A3%E6%9B%BFThread%E5%92%8CHandler/
 */

public class RxTask {

    public static void doOnIOThread(IOTask task) {
        Observable.just(task)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<IOTask>() {
                    @Override
                    public void call(IOTask ioTask) {ioTask.onIOThread();}
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public static void doOnUIThread(UITask task) {
        doOnUIThrreadDelay(task, 0, TimeUnit.MILLISECONDS);
    }

    public static void doOnUIThrreadDelay(UITask task, long delay, TimeUnit unit) {
        Observable.just(task)
                .delay(delay, unit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UITask>() {
                    @Override
                    public void call(UITask uiTask) {
                        uiTask.onUIThread();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public static <T> void doTask(final Task<T> task) {
        Observable
                .create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        T t = task.onIOThread();
                        subscriber.onNext(t);
                        subscriber.onCompleted();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        task.onUIThread(t);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public interface IOTask {

        void onIOThread();
    }

    public interface UITask {

        void onUIThread();
    }

    public interface Task<T> {

        T onIOThread();

        void onUIThread(T t);
    }

}
