package com.xdf.kb.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xdf.kb.R;
import com.xdf.kb.alipaydemo.AlipayDemoMainActivity;
import com.xdf.kb.base.BaseActivity;
import com.xdf.kb.comm.myinterface.MyOnRecyclerItemClickListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Arrays;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Administrator on 2016/11/30 0030.
 * 主界面
 */
@ContentView(R.layout.activity_main)
public class MainPageActivity extends BaseActivity implements MyOnRecyclerItemClickListener {
    @ViewInject(R.id.recycler_view_menu)
    private RecyclerView recyclerView;
    private RecyclerViewMenuAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initUI();
    }

    private void initUI() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerAdapter = new RecyclerViewMenuAdapter(this, Arrays.asList(getResources().getStringArray(R.array.main_page_menus)));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(this);
    }


    public void onRecyclerItemClick(ViewGroup parent, View v, int position) {
        switch (position) {
            case 0:
                startNewAcitivity(AlipayDemoMainActivity.class,false);
                break;
            case 1:
                // TODO 微信支付Demo
                break;
            case 2://RxjavaDemo
                break;
            default:
                break;
        }
    }

    private void startNewAcitivity(Class<? extends Activity> activity, boolean isFinisThis) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (isFinisThis) finish();
    }
}
