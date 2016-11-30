package com.xdf.kb.main;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdf.kb.R;
import com.xdf.kb.utils.ColorUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class RecyclerViewMenuAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<String> list;

    public RecyclerViewMenuAdapter(Activity activity, List<String> datas) {
        this.activity = activity;
        this.list = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.main_page_recycler_view_menu_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            textView = (TextView) itemView.findViewById(R.id.text);
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            itemView.setBackgroundColor(ColorUtils.getRandomColor());
            //设置图片的相对于屏幕的宽高比
            params.width = width / 3;
            params.height = (int) (200 + Math.random() * 400);
            itemView.setLayoutParams(params);
        }

        public void setData(int position) {
            textView.setText(position);
        }
    }
}
