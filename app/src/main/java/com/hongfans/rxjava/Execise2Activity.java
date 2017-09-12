package com.hongfans.rxjava;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongfans.rxjava.rxcustom.ApiService;
import com.hongfans.rxjava.rxcustom.AsyncWork;
import com.hongfans.rxjava.rxcustom.Callback;

public class Execise2Activity extends AppCompatActivity implements View.OnClickListener{

    private ApiService service = new ApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execise2);

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
                testSync();
                break;
            case R.id.btn_2:
                testAsync();
                break;
            case R.id.btn_3:
                testWork();
                break;
            case R.id.btn_4:
                testTransform();
                break;
            case R.id.btn_5:
                testOperation();
                break;
            case R.id.btn_6:
                break;
            case R.id.btn_7:
                break;
            case R.id.btn_8:
                break;
            case R.id.btn_9:
                break;
            default:
                break;
        }
    }

    private void testSync(){
        LogUtil.iWithTime("testSync " + System.currentTimeMillis());
        Uri uri = service.getUriSync("Java");
        LogUtil.iWithTime("testSync uri " + uri);
        LogUtil.iWithTime("testSync " + System.currentTimeMillis());
    }

    private void testAsync(){
        LogUtil.iWithTime("testAsync " + System.currentTimeMillis());
        service.getUriAsync("python", new Callback<Uri>(){
            @Override
            public void onSuccess(Uri list){
                LogUtil.iWithTime("testSync onSuccess uri " + list + " " + System.currentTimeMillis());
            }

            @Override
            public void onFailure(Exception e){
                LogUtil.iWithTime("testSync onFailure " + e + " " + System.currentTimeMillis());

            }
        });
        LogUtil.iWithTime("testAsync " + System.currentTimeMillis());
    }

    private void testWork(){
        LogUtil.iWithTime("testWork " + System.currentTimeMillis());
        service.getUriWork("keybord")
               .start(new Callback<Uri>(){
                   @Override
                   public void onSuccess(Uri list){
                       LogUtil.iWithTime("testWork onSuccess uri " + list + " " + System.currentTimeMillis());
                   }

                   @Override
                   public void onFailure(Exception e){
                       LogUtil.iWithTime("testWork onFailure " + e + " " + System.currentTimeMillis());
                   }
               });
        LogUtil.iWithTime("testWork " + System.currentTimeMillis());
    }

    private void testTransform(){
        LogUtil.iWithTime("testTransform " + System.currentTimeMillis());
        AsyncWork<Uri> uriWork = service.getUriTransform("http");
        uriWork.start(new Callback<Uri>(){
            @Override
            public void onSuccess(Uri list){
                LogUtil.iWithTime("testTransform onSuccess uri " + list + " " + System.currentTimeMillis());
            }

            @Override
            public void onFailure(Exception e){
                LogUtil.iWithTime("testTransform onFailure " + e + " " + System.currentTimeMillis());
            }
        });
        LogUtil.iWithTime("testTransform " + System.currentTimeMillis());
    }

    private void testOperation(){
        LogUtil.iWithTime("testOperation " + System.currentTimeMillis());
        AsyncWork<Uri> over = service.getUriOperation("over");
        over.start(new Callback<Uri>(){
            @Override
            public void onSuccess(Uri list){
                LogUtil.iWithTime("testOperation onSuccess uri " + list + " " + System.currentTimeMillis());
            }

            @Override
            public void onFailure(Exception e){
                LogUtil.iWithTime("testOperation onFailure " + e + " " + System.currentTimeMillis());
            }
        });

        LogUtil.iWithTime("testOperation " + System.currentTimeMillis());


    }

}
