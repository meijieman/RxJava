package com.hongfans.rxjava.operation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.LogUtil;
import com.hongfans.rxjava.R;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Timestamped;

/**
 * 6. 辅助操作
 * <p>
 * Delay, Do, Materialize/Dematerialize, ObserveOn, Serialize, Subscribe, SubscribeOn,
 * TimeInterval, Timeout, Timestamp, Using
 */
public class UtilityActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility);

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
                Observable.just(2, 1, 3)
                          .delay(1000, TimeUnit.MILLISECONDS)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_2:
                Observable.just(1, 2, 3)
                          .doOnNext(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  if(integer == 2){
                                      throw new RuntimeException("val equals 2");
                                  }
                              }
                          })
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          }, new Action1<Throwable>(){
                              @Override
                              public void call(Throwable throwable){
                                  LogUtil.i("" + throwable);
                              }
                          });

                break;
            case R.id.btn_3:
                //ObservableOn 指定一个观察者在哪个调度器上观察这个Observable


                break;
            case R.id.btn_4:
                // SubscribeOn 指定Observable自身在哪个调度器上执行


                break;
            case R.id.btn_5:
                Observable.just(2, 3)
                          .delay(3000, TimeUnit.MILLISECONDS)
                          .timeout(2000, TimeUnit.MILLISECONDS)
                          .subscribe(new Action1<Integer>(){
                              @Override
                              public void call(Integer integer){
                                  LogUtil.i("" + integer);
                              }
                          });
                break;
            case R.id.btn_6:
                Observable.just(1, 2, 3)
                          .timestamp()
                          .subscribe(new Action1<Timestamped<Integer>>(){
                              @Override
                              public void call(Timestamped<Integer> integerTimestamped){
                                  LogUtil.i("" + integerTimestamped);
                              }
                          });
                break;
            case R.id.btn_7:
                Observable.just(1, 2, 3)
                          .toList()
                          .subscribe(new Action1<List<Integer>>(){
                              @Override
                              public void call(List<Integer> integers){
                                  LogUtil.i("" + integers);
                              }
                          });
                break;
            case R.id.btn_8:
                Observable.just(1, 2, 3)
                          .toMap(new Func1<Integer, String>(){
                              @Override
                              public String call(Integer integer){
                                  return integer + 22 + "";
                              }
                          })
                          .subscribe(new Action1<Map<String, Integer>>(){
                              @Override
                              public void call(Map<String, Integer> stringIntegerMap){
                                  LogUtil.i("" + stringIntegerMap);
                              }
                          });
                break;
        }
    }
}
