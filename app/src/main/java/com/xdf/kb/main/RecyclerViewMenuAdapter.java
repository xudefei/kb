package com.xdf.kb.main;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdf.kb.R;
import com.xdf.kb.comm.myinterface.MyOnRecyclerItemClickListener;
import com.xdf.kb.utils.ColorUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class RecyclerViewMenuAdapter extends RecyclerView.Adapter<RecyclerViewMenuAdapter.ViewHolder> {
    private Activity activity;
    private List<String> list;
    private MyOnRecyclerItemClickListener onItemClickListener;

    public void setOnItemClickListener(MyOnRecyclerItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewMenuAdapter(Activity activity, List<String> datas) {
        this.activity = activity;
        this.list = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.main_page_recycler_view_menu_item, null));
        holder.setOnClicListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    if (v.getTag() != null) {
                        onItemClickListener.onRecyclerItemClick(parent,v,(int)v.getTag());
                    }

                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private View mView;

        protected void setOnClicListener(View.OnClickListener clicListener) {
            mView.setOnClickListener(clicListener);
        }
        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            int width = activity.getWindowManager().getDefaultDisplay().getWidth();
            textView = (TextView) itemView.findViewById(R.id.text);
            itemView.setBackgroundColor(ColorUtils.getRandomColor());
            //设置图片的相对于屏幕的宽高比
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / 3,(int) (200 + Math.random() * 200));
            itemView.setLayoutParams(params);
        }

        public void setData(int position) {
            textView.setText(list.get(position));
            mView.setTag(position);
        }
    }
}
