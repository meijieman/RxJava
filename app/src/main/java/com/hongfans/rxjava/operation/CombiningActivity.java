package com.hongfans.rxjava.operation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.LogUtil;
import com.hongfans.rxjava.R;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 4. 组合操作
 * <p>
 * And/Then/When, CombineLatest, Join, Merge, StartWith, Switch, Zip
 */
public class CombiningActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combining);


        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btn_1:
                Observable.just(1, 2, 3)
                          .startWith(9)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_2:
                Observable.just(1, 2, 3)
                          .mergeWith(Observable.just(4, 8))
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_3:
                Observable<Integer> just1 = Observable.just(1, 3, 5).subscribeOn(Schedulers.newThread());
                Observable<Integer> just2 = Observable.just(2, 4, 6);

                Observable.merge(just1, just2)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_4:
                break;
            case R.id.btn_5:
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
