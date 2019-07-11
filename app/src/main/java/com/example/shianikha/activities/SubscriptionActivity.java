package com.example.shianikha.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shianikha.R;
import com.example.shianikha.adapters.SubscriptionSliderAdapter;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    private ViewPager subPlanSlideViewPager;
    private SubscriptionSliderAdapter subPlanSliderAdapter;
    ImageView imv_pre_btn, imv_next_btn;
    ImageView back_nav_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        context = SubscriptionActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        subPlanSlideViewPager = findViewById(R.id.sub_plan_viewpager);

        imv_pre_btn = findViewById(R.id.prev_imv);
        imv_next_btn = findViewById(R.id.next_imv);
        //back_nav_icon=findViewById(R.id.drawerMenu);
        //findViewById(R.id.sub_drawerMenu).setOnClickListener(this);

        imv_pre_btn.setOnClickListener(this);
        imv_next_btn.setOnClickListener(this);
        //back_nav_icon.setOnClickListener(this);

        subPlanSliderAdapter = new SubscriptionSliderAdapter(context);

        subPlanSlideViewPager.setAdapter(subPlanSliderAdapter);

    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.prev_imv) {
            subPlanSlideViewPager.setCurrentItem(getItem(-1), true);
        }
        else if (v.getId() == R.id.next_imv) {
            subPlanSlideViewPager.setCurrentItem(getItem(+1), true);
        }
        else if (v.getId() == R.id.sub_drawerMenu)
        {

            onMethodClick(v);
        }
    }

    public void  onMethodClick(View v)
    {
        finish();
    }
    private int getItem(int i) {
        return subPlanSlideViewPager.getCurrentItem() + i;
    }


}