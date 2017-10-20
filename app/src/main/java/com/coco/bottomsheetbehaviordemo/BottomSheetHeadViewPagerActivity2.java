package com.coco.bottomsheetbehaviordemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.bottomsheetbehaviordemo.view.MyLinearLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;

import static com.coco.bottomsheetbehaviordemo.R.id.recyclerview;

/**
 * BottomSheetBehavior可以轻松实现底部动作条功能，底部动作条的引入需要在布局添加app:layout_behavior属性，
 * 并将这个布局作为CoordianatorLayout的子View。这个可以用于一些从下面弹出选项的操作。
 * <p>
 * 方法	用途
 * setPeekHeight	        默认显示后View露头的高度
 * getPeekHeight	        @see setPeekHeight()
 * setHideable	            设置是否可以隐藏，如果为true,表示状态可以为STATE_HIDDEN
 * isHideable	            @see setHideable()
 * setState	                设置状态;设置不同的状态会影响BottomSheetView的显示效果
 * getState	                获取状态
 * setBottomSheetCallback	设置状态改变回调
 */
public class BottomSheetHeadViewPagerActivity2 extends AppCompatActivity implements View.OnClickListener {
    CoordinatorLayout coordinatorLayout;
    BottomSheetBehavior<View> behavior;
    private static final String TAG = BottomSheetHeadViewPagerActivity2.class.getSimpleName();
    private View mBottomSheet;
    private MyLinearLayout myLinearLayout;
    private TextView id_hot_illegal_drawer_tv;
    private View id_illegal_hot_more_rl;
    private ArrayList<String> texts, texts2;
    private TextView id_left_tv, id_right_tv;
    private View id_left_line_view, id_right_line_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_viewpager2);

        initView();

        initData();

        setHeaderViewData();

        setRecycleViewData();

        initEvent();
    }

    public void initData() {
        texts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            texts.add("测试 左边 i=" + i);
        }

        texts2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            texts2.add("测试 右边 i=" + i);
        }
    }

    public void textView1OnClick(View view) {
        Toast.makeText(this, "textView1  OnClick", 0).show();
    }

    public void textView10OnClick(View view) {
        Toast.makeText(this, "textView10 OnClick", 0).show();
    }


    private void setRecycleViewData() {
        final RecyclerView id_left_rw = (RecyclerView) findViewById(R.id.id_left_rw);
        setRecycleViewViewData(id_left_rw, texts);

        final RecyclerView id_right_rw = (RecyclerView) findViewById(R.id.id_right_rw);
        setRecycleViewViewData(id_right_rw, texts2);

        id_left_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_left_tv.setTextColor(Color.parseColor("#ffffbb33"));
                id_right_tv.setTextColor(Color.parseColor("#333333"));
                id_left_line_view.setVisibility(View.VISIBLE);
                id_right_line_view.setVisibility(View.INVISIBLE);

                id_left_rw.setVisibility(View.VISIBLE);
                id_right_rw.setVisibility(View.GONE);
            }
        });

        id_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_right_tv.setTextColor(Color.parseColor("#ffffbb33"));
                id_left_tv.setTextColor(Color.parseColor("#333333"));
                id_right_line_view.setVisibility(View.VISIBLE);
                id_left_line_view.setVisibility(View.INVISIBLE);

                id_left_rw.setVisibility(View.GONE);
                id_right_rw.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setRecycleViewViewData(RecyclerView recyclerview, ArrayList<String> texts) {
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.item_head_vieapager, texts) {
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
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initEvent() {
        //设置监听bottomSheet的状态
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.i(TAG, "新状态：" + newState);
                if (newState == 3) {
                    myLinearLayout.setIsChildClick(true);
                    myLinearLayout.setOnClickListener(null);
                    id_hot_illegal_drawer_tv.setBackgroundResource(R.mipmap.ic_hight_illegal_down);
                } else {
                    id_hot_illegal_drawer_tv.setBackgroundResource(R.mipmap.ic_hight_illegal_up);
                    myLinearLayout.setIsChildClick(false);
                    myLinearLayout.setOnClickListener(BottomSheetHeadViewPagerActivity2.this);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }


    private void initView() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mBottomSheet = findViewById(R.id.bottom_sheet);
        id_hot_illegal_drawer_tv = (TextView) findViewById(R.id.id_hot_illegal_drawer_tv);
        id_illegal_hot_more_rl = findViewById(R.id.id_illegal_hot_more_rl);
        id_illegal_hot_more_rl.setOnClickListener(this);//BottomSheetBehavior

        id_left_tv = (TextView) findViewById(R.id.id_left_tv);
        id_right_tv = (TextView) findViewById(R.id.id_right_tv);
        id_left_line_view = findViewById(R.id.id_left_line_view);
        id_right_line_view = findViewById(R.id.id_right_line_view);

        //配置bottomSheet
        behavior = BottomSheetBehavior.from(mBottomSheet);
    }

    private void setHeaderViewData() {
        myLinearLayout = (MyLinearLayout) findViewById(R.id.id_sample_header);
        myLinearLayout.setOnClickListener(this);
    }


    public void SearchOperationClick(View viwe) {
        Toast.makeText(this, "开始搜索", 0).show();
    }

    private void swichBottomSheet() {
        int state = behavior.getState();
        if (state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_sample_header:
            case R.id.id_illegal_hot_more_rl:
                swichBottomSheet();
                break;
        }
    }
}
