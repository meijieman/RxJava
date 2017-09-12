package com.hongfans.rxjava.rxcustom;


/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:51
 * http://www.jianshu.com/p/68160c4bd9dc
 */
public abstract class AsyncWork<T>{

    public abstract void start(Callback<T> call);

    public <R> AsyncWork<R> map(final Func<T, R> func){
        final AsyncWork<T> source = this;
        return new AsyncWork<R>(){
            @Override
            public void start(final Callback<R> callback){
                source.start(new Callback<T>(){
                    @Override
                    public void onSuccess(T list){
                        R r = func.call(list);
                        callback.onSuccess(r);
                    }

                    @Override
                    public void onFailure(Exception e){
                        callback.onFailure(e);
                    }
                });
            }
        };
    }

    // 方法的递归调用
    public <R> AsyncWork<R> flatMap(final Func<T, AsyncWork<R>> func){
        final AsyncWork<T> source = this;
        return new AsyncWork<R>(){
            @Override
            public void start(final Callback<R> callback){
                source.start(new Callback<T>(){
                    @Override
                    public void onSuccess(T list){
                        // 此处与 map() 不同
                        AsyncWork<R> aw = func.call(list);
                        aw.start(callback);
                    }

                    @Override
                    public void onFailure(Exception e){
                        callback.onFailure(e);
                    }
                });
            }
        };
    }

}
