package com.hongfans.rxjava.rxtask;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * TODO
 * Created by MEI on 2017/8/2.
 */

public class RxTaskManager {

    private Scheduler.Worker mWorkerIO;
    private Scheduler.Worker mWorkerUI;

    private RxTaskManager() {
        mWorkerIO = Schedulers.io().createWorker();
        mWorkerUI = AndroidSchedulers.mainThread().createWorker();
    }

    private static final class HolderClass {
        public static final RxTaskManager INSTANCE = new RxTaskManager();
    }

    public static RxTaskManager getInstance() {
        return HolderClass.INSTANCE;
    }

    public void destroy() {
        mWorkerIO.unsubscribe();
        mWorkerUI.unsubscribe();
    }

    public <T> void doOnIOThread(final IOTask<T> task) {
        mWorkerIO = Schedulers.io().createWorker();
        mWorkerIO.schedule(new Action0() {
            @Override
            public void call() {
                task.doOnIOThread();
            }
        });
    }

    public <T> void doOnUIThreadDelay(final UITask<T> task, long delayTime, TimeUnit unit) {
        mWorkerUI.schedule(new Action0() {
            @Override
            public void call() {
                task.doOnUIThread();
            }
        }, delayTime, unit);
    }

    public interface IOTask<T> {
        void doOnIOThread();
    }

    public interface UITask<T> {
        void doOnUIThread();
    }

    public abstract class Task<T> {
        private T t;

        public Task(T t) {
            this.t = t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public T getT() {
            return t;
        }

        public abstract void doOnUIThread();

        public abstract void doOnIOThread();
    }
}
