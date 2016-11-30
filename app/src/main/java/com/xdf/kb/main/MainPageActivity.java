package com.xdf.kb.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.xdf.kb.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/11/30 0030.
 * 主界面
 */
@ContentView(R.layout.activity_login)
public class MainPageActivity extends AppCompatActivity {
//    @ViewInject(R.id.recycler_view_menu)
    private RecyclerView recyclerView;
    private RecyclerViewMenuAdapter recyclerAdapter;



    private void initUI() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerAdapter = new RecyclerViewMenuAdapter(this, Arrays.asList(getResources().getStringArray(R.array.main_page_menus)));
        recyclerView.setAdapter(recyclerAdapter);
    }
}
