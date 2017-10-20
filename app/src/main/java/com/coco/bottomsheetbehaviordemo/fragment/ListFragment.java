package com.coco.bottomsheetbehaviordemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.coco.bottomsheetbehaviordemo.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;

/**
 * @ explain:
 * @ author：xujun on 2016/10/18 17:21
 * @ email：gdutxiaoxu@163.com
 */
public class ListFragment extends BaseFragment {

    private static final String KEY = "key";
    private ArrayList<String> texts;

    public static ListFragment newInstance(String title) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }


    public void initData() {
        texts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            texts.add("测试 i=" + i);
        }
    }


    @Override
    protected void initView(View view) {

        initData();

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_head_vieapager;
    }

    @Override
    public void fetchData() {

    }
}
