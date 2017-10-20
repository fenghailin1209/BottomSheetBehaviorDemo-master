package com.coco.bottomsheetbehaviordemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Administrator on 2017/10/20.
 */

public class MainActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCommon(View view){
        jump(BottomSheetSearchActivity.class);
    }

    public void onClickViewPager1(View view){
        jump(BottomSheetHeadViewPagerActivity.class);
    }

    public void onClickViewPager2(View view){
        jump(BottomSheetHeadViewPagerActivity2.class);
    }

    private void jump(Class clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
