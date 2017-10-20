package com.coco.bottomsheetbehaviordemo.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coco.bottomsheetbehaviordemo.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;


/**
 * @function viewpageradapter
 * @auther: Created by yinglan
 * @time: 16/3/16
 */
public class IllegalHotDrawerPagerAdapter extends PagerAdapter {

    private static final String TAG = IllegalHotDrawerPagerAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<View> views;
    private ArrayList<String> texts;

    public IllegalHotDrawerPagerAdapter(Context context, ArrayList<View> views) {
        mContext = context;
        this.views = views;

        initData();

        setViewData();
    }

    private void setViewData() {
        for (int position = 0; position < views.size(); position++) {
            View view = views.get(position);
            setAdapter(view);
        }
    }


    private void initData() {
        texts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            texts.add("测试 i=" + i);
        }
    }

    @NonNull
    private CommonAdapter setAdapter(View view) {
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(mContext, R.layout.item_head_vieapager, texts) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setText(R.id.text, s);
                holder.getView(R.id.text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
        recyclerview.setAdapter(mHeaderAndFooterWrapper);
        mHeaderAndFooterWrapper.notifyDataSetChanged();

        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        return commonAdapter;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }
}
