package com.hongfans.rxjava.operation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.LogUtil;
import com.hongfans.rxjava.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 3. 过滤操作
 * <p>
 * Debounce, Distinct, ElementAt, Filter, First, IgnoreElements, Last, Sample, Skip,
 * SkipLast, Take, TakeLast
 */
public class FilteringActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_10).setOnClickListener(this);
        findViewById(R.id.btn_11).setOnClickListener(this);
        findViewById(R.id.btn_12).setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btn_1:
                Observable.just(1, 2, 2, 3, 1)
                          .distinct()
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_2:
                Observable.just(1, 3, 5)
                          .elementAt(2)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_3:
                Observable.just(1, 3, 5)
                          .filter(new Func1<Integer, Boolean>(){
                              @Override
                              public Boolean call(Integer integer){
                                  return integer > 3;
                              }
                          })
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_4:
                Observable.just(1, "3", "2")
                          .ofType(Integer.class)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_5:
                Observable.just(1, 3, 2)
                          .first(new Func1<Integer, Boolean>(){
                              @Override
                              public Boolean call(Integer integer){
                                  return integer > 1;
                              }
                          })
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_6:
                Observable.just(1, 3, 2)
                          .take(2)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_7:
                Observable.just(1, 3, 2)
                          .single()
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_8:
                Observable.just(1, 3, 2)
                          .last()
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_9:
                Observable.just(1, 3, 2)
                          .sample(100, TimeUnit.NANOSECONDS)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_10:
                Observable.just(1, 2, 3)
                          .skip(2)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_11:

                break;
            case R.id.btn_12:

                break;

        }
    }
}
