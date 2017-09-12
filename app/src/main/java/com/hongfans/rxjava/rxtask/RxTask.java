package com.hongfans.rxjava.rxtask;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 21:27
 */
public class RxTask{

    public static void doOnIOThread(IOTask task){
        Observable.just(task)
                  .observeOn(Schedulers.io())
                  .subscribe(new Action1<IOTask>(){
                      @Override
                      public void call(IOTask ioTask){
                          ioTask.onIOThread();
                      }
                  }, new Action1<Throwable>(){
                      @Override
                      public void call(Throwable throwable){
                          throwable.printStackTrace();
                      }
                  });
    }

    public static void doOnUIThread(UITask task){
        doOnUIThrreadDelay(task, 0, TimeUnit.MILLISECONDS);
    }

    public static void doOnUIThrreadDelay(UITask task, long delay, TimeUnit unit){
        Observable.just(task)
                  .delay(delay, unit)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<UITask>(){
                      @Override
                      public void call(UITask uiTask){
                          uiTask.onUIThread();
                      }
                  }, new Action1<Throwable>(){
                      @Override
                      public void call(Throwable throwable){
                          throwable.printStackTrace();
                      }
                  });
    }

    public static <T> void doTask(final Task<T> task){
        Observable
                .create(new Observable.OnSubscribe<T>(){
                    @Override
                    public void call(Subscriber<? super T> subscriber){
                        T t = task.onIOThread();
                        subscriber.onNext(t);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<T>(){
                    @Override
                    public void call(T t){
                        task.onUIThread(t);
                    }
                }, new Action1<Throwable>(){
                    @Override
                    public void call(Throwable throwable){
                        throwable.printStackTrace();
                    }
                });
    }

    public interface IOTask{

        void onIOThread();
    }

    public interface UITask{

        void onUIThread();
    }

    public interface Task<T>{

        T onIOThread();

        void onUIThread(T t);
    }

}
