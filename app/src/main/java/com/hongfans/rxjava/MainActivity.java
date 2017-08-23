package com.hongfans.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.operation.CombiningActivity;
import com.hongfans.rxjava.operation.CreatingActivity;
import com.hongfans.rxjava.operation.FilteringActivity;
import com.hongfans.rxjava.operation.TransformingActivity;
import com.hongfans.rxjava.operation.UtilityActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.btn_1:
                startActivity(new Intent(this, ScheduleActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, CreatingActivity.class));
                break;
            case R.id.btn_3:
                startActivity(new Intent(this, TransformingActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(this, FilteringActivity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(this, CombiningActivity.class));
                break;
            case R.id.btn_6:
                startActivity(new Intent(this, UtilityActivity.class));
                break;
            default:

                break;
        }
    }
}
