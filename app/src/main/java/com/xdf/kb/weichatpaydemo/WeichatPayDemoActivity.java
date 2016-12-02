package com.xdf.kb.weichatpaydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.xdf.kb.R;
import com.xdf.kb.base.BaseActivity;

import org.xutils.view.annotation.ContentView;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
@ContentView(R.layout.activity_weichat_pay_demo)
public class WeichatPayDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null ) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
