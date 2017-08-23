package com.hongfans.rxjava.operation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.LogUtil;
import com.hongfans.rxjava.R;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * 2. 变换操作
 * <p>
 * Buffer, FlatMap, GroupBy, Map, Scan, Window
 */
public class TransformingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transforming);


        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btn_1:
                Observable.just(1, 3, 5)
                          .map(new Func1<Integer, String>(){
                              @Override
                              public String call(Integer integer){
                                  return integer + ".txt";
                              }
                          })
                          .subscribe(new Action1<String>(){
                              @Override
                              public void call(String s){
                                  LogUtil.i("s " + s);
                              }
                          });
                break;
            case R.id.btn_2:
                Observable.just(1, 3, 5)
                          .flatMap(new Func1<Integer, Observable<String>>(){
                              @Override
                              public Observable<String> call(Integer integer){
                                  return Observable.just(integer + ".txt");

                              }
                          })
                          .subscribe(new Action1<String>(){
                              @Override
                              public void call(String s){
                                  LogUtil.i("s " + s);
                              }
                          });
                break;
            case R.id.btn_3:
                Observable.just(1, 3, 5)
                          .concatMap(new Func1<Integer, Observable<String>>(){
                              @Override
                              public Observable<String> call(Integer integer){
                                  return Observable.just(integer + ".txt");
                              }
                          })
                          .subscribe(new Action1<String>(){
                              @Override
                              public void call(String s){
                                  LogUtil.i("s " + s);
                              }
                          });
                break;
            case R.id.btn_4:
                Observable.just(1, 3, 5)
                          .switchMap(new Func1<Integer, Observable<String>>(){
                              @Override
                              public Observable<String> call(Integer integer){
                                  return Observable.just(integer + ".log");
                              }
                          })
                          .subscribe(new Action1<String>(){
                              @Override
                              public void call(String s){
                                  LogUtil.i("s " + s);
                              }
                          });
                break;
            case R.id.btn_5:
                Observable.range(2, 5)
                          .scan(new Func2<Integer, Integer, Integer>(){
                              @Override
                              public Integer call(Integer integer, Integer integer2){
                                  return integer + integer2;
                              }
                          })
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){


                              }
                          });
                break;
            case R.id.btn_6:

                break;
            case R.id.btn_7:

                break;
            case R.id.btn_8:

                break;


        }
    }
}
