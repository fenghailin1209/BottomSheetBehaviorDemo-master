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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.bottomsheetbehaviordemo.utils.GlideImageLoader;
import com.coco.bottomsheetbehaviordemo.utils.KeyBoardUtils;
import com.coco.bottomsheetbehaviordemo.view.MyLinearLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.coco.bottomsheetbehaviordemo.R.id.id_hot_illegal_drawer_tv;

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
public class BottomSheetSearchActivity extends AppCompatActivity implements View.OnClickListener {
    CoordinatorLayout coordinatorLayout;
    RecyclerView recyclerview;
    List<String> texts;
    BottomSheetBehavior<View> behavior;
    private static final String TAG = BottomSheetSearchActivity.class.getSimpleName();
    private View mBottomSheet;
    private Banner banner;
    private MyLinearLayout myLinearLayout;
    private EditText id_search_et;
    private TextView id_shade_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        initData();

        setHeaderViewData();

        setRecycleViewData();

        initEvent();
    }

    public void textView1OnClick(View view) {
        Toast.makeText(this, "textView1  OnClick", 0).show();
    }

    public void textView10OnClick(View view) {
        Toast.makeText(this, "textView10 OnClick", 0).show();
    }

    private void setRecycleViewData() {
        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(this, R.layout.item, texts) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setText(R.id.text, s);
                holder.getView(R.id.text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BottomSheetSearchActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
        View header = LayoutInflater.from(this).inflate(R.layout.layout_search_header, (ViewGroup) findViewById(android.R.id.content), false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swichBottomSheet();
            }
        });
//        mHeaderAndFooterWrapper.addHeaderView(header);

        recyclerview.setAdapter(mHeaderAndFooterWrapper);
        mHeaderAndFooterWrapper.notifyDataSetChanged();

        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //配置bottomSheet
        behavior = BottomSheetBehavior.from(mBottomSheet);
    }

    private void initEvent() {
        //设置监听bottomSheet的状态
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.i(TAG, "新状态：" + newState);
                if (newState == 3) {
                    id_shade_tv.setVisibility(View.VISIBLE);

                    myLinearLayout.setIsChildClick(true);
                    myLinearLayout.setOnClickListener(null);
                } else {
                    id_shade_tv.setVisibility(View.GONE);

                    myLinearLayout.setIsChildClick(false);
                    myLinearLayout.setOnClickListener(BottomSheetSearchActivity.this);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                int bsHight = bottomSheet.getHeight();
            }
        });
    }

    private void initData() {
        texts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            texts.add("测试 i=" + i);
        }
    }

    private void initView() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mBottomSheet = findViewById(R.id.bottom_sheet);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        id_search_et = (EditText) findViewById(R.id.id_search_et);

        id_shade_tv = (TextView) findViewById(R.id.id_shade_tv);
        id_shade_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swichBottomSheet();

                KeyBoardUtils.closeKeybord(id_search_et, BottomSheetSearchActivity.this);
            }
        });
    }

    private void setHeaderViewData() {
        myLinearLayout = (MyLinearLayout) findViewById(R.id.id_sample_header);
        myLinearLayout.setOnClickListener(this);

        ArrayList images = new ArrayList();
        images.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1508404544&di=b5be22fdeac3c3941c7492f3b9bc3d33&src=http://pic33.nipic.com/20130907/13534366_092511672176_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508414632838&di=0e8933bbcc50a86ec2eb7ad5e3d57116&imgtype=0&src=http%3A%2F%2Fpic7.photophoto.cn%2F20080529%2F0034034465128235_b.jpg");

        banner = (Banner) findViewById(R.id.banner);
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(BottomSheetSearchActivity.this, "点击了Banner:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
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
                swichBottomSheet();
                break;
        }
    }
}
