package com.nikha.shianikha.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nikha.shianikha.R;
import com.nikha.shianikha.adapters.SubscriptionSliderAdapter;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    private ViewPager subPlanSlideViewPager;
    private SubscriptionSliderAdapter subPlanSliderAdapter;
    ImageView imv_pre_btn, imv_next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        context = SubscriptionActivity.this;
        subPlanSlideViewPager = findViewById(R.id.sub_plan_viewpager);

        imv_pre_btn = findViewById(R.id.prev_imv);
        imv_next_btn = findViewById(R.id.next_imv);

        imv_pre_btn.setOnClickListener(this);
        imv_next_btn.setOnClickListener(this);

        subPlanSliderAdapter = new SubscriptionSliderAdapter(context);

        subPlanSlideViewPager.setAdapter(subPlanSliderAdapter);

    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.prev_imv) {
            subPlanSlideViewPager.setCurrentItem(getItem(-1), true);
        }
        else if (v.getId() == R.id.next_imv)
        {
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
        ((SubscriptionActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    private int getItem(int i) {
        return subPlanSlideViewPager.getCurrentItem() + i;
    }

}
