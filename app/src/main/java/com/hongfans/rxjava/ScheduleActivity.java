package com.hongfans.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        LogUtil.i("click " + v.getId());
        switch(v.getId()) {
            case R.id.btn_1: {
                final Scheduler.Worker worker = Schedulers.newThread().createWorker();
                worker.schedule(new Action0(){
                    int count = 0;

                    @Override
                    public void call(){
                        count++;
                        LogUtil.i("aaaaaaaaaaa ");

                        if(count == 5){
                            worker.unsubscribe();
                        }
                        worker.schedule(this);
                    }
                });
            }
            break;
            case R.id.btn_2: {
                final Scheduler.Worker worker = Schedulers.newThread().createWorker();
                Subscription subscription = worker.schedule(new Action0(){
                    int count = 0;

                    @Override
                    public void call(){
                        while(!worker.isUnsubscribed()){
                            count++;
                            LogUtil.i("bbbbbbbbbb ");
                            if(count >= 5){
                                worker.unsubscribe();
                            }
                        }
                    }
                });
            }
            break;
            case R.id.btn_3: {
                Scheduler.Worker worker = Schedulers.newThread().createWorker();
                // 延时执行
                worker.schedule(new Action0(){
                    @Override
                    public void call(){
                        LogUtil.i("ccccccccccc ");
                    }
                }, 500, TimeUnit.MILLISECONDS);

                // 周期执行
                worker.schedulePeriodically(new Action0(){
                    @Override
                    public void call(){
                        LogUtil.i("ddddddddd ");
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
            }
            break;
            case R.id.btn_4:

                break;
            case R.id.btn_5:

                break;
            case R.id.btn_6:

                break;
            default:

                break;
        }
    }
}
