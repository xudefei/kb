package com.xdf.kb.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xdf.kb.R;
import com.xdf.kb.base.BaseActivity;
import com.xdf.kb.main.MainPageActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.loading_page_activity)
public class LoadingPage extends BaseActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        setContentView(R.layout.loading_page_activity);
        context = this;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context,MainPageActivity.class);
                        context.startActivity(intent);
                        LoadingPage.this.finish();
                    }
                });
            }
        },3000);
    }
}
