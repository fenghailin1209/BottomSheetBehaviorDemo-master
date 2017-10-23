package com.coco.bottomsheetbehaviordemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coco.bottomsheetbehaviordemo.behavior.MyCustomBottomSheetBehavior;
import com.coco.bottomsheetbehaviordemo.fragment.BaseFragmentAdapter;
import com.coco.bottomsheetbehaviordemo.fragment.ListFragment;
import com.coco.bottomsheetbehaviordemo.view.MyLinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.coco.bottomsheetbehaviordemo.R.id.id_shade_tv;

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
public class BottomSheetHeadViewPagerActivity extends AppCompatActivity implements View.OnClickListener{
    CoordinatorLayout coordinatorLayout;
    MyCustomBottomSheetBehavior<View> behavior;
    private static final String TAG = BottomSheetHeadViewPagerActivity.class.getSimpleName();
    private View mBottomSheet;
    private MyLinearLayout myLinearLayout;
    private TextView id_hot_illegal_drawer_tv;
    private View id_illegal_hot_more_rl;
    List<Fragment> mFragments;
    String[] mTitles = new String[]{
            "主页", "微博"
    };
    private TabLayout mTabLayout;
    ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_viewpager);

        initView();

        setHeaderViewData();

        setRecycleViewData();

        initEvent();
    }


    public void textView1OnClick(View view){
        Toast.makeText(this,"textView1  OnClick",0).show();
    }

    public void textView10OnClick(View view){
        Toast.makeText(this,"textView10 OnClick",0).show();
    }


    private void setRecycleViewData() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            ListFragment listFragment = ListFragment.newInstance(mTitles[i]);
            mFragments.add(listFragment);
        }
        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);

        mViewPager.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);

        //配置bottomSheet
        behavior = MyCustomBottomSheetBehavior.from(mBottomSheet);
    }

    private void initEvent() {
        //设置监听bottomSheet的状态
        behavior.setBottomSheetCallback(new MyCustomBottomSheetBehavior.BottomSheetCallback() {
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
                    myLinearLayout.setOnClickListener(BottomSheetHeadViewPagerActivity.this);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(TAG,"--->>>onSlide slideOffset:"+slideOffset);
            }
        });
    }



    private void initView() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mBottomSheet = findViewById(R.id.bottom_sheet);
        id_hot_illegal_drawer_tv = (TextView) findViewById(R.id.id_hot_illegal_drawer_tv);
        id_illegal_hot_more_rl = findViewById(R.id.id_illegal_hot_more_rl);
        id_illegal_hot_more_rl.setOnClickListener(this);//BottomSheetBehavior
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
            behavior.setState(MyCustomBottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(MyCustomBottomSheetBehavior.STATE_EXPANDED);
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
