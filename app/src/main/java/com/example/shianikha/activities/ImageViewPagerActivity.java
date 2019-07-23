package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.R;
import com.example.shianikha.adapters.ImageSliderAdapter;
import com.example.shianikha.adapters.SliderAdapter;

import java.util.ArrayList;

public class ImageViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager slideViewPager;
    private ImageSliderAdapter sliderAdapter;
    private LoadingDialog loadingDialog;
    Context context;
    ArrayList<String> arrayListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewpager);
       String jsonList_string = getIntent().getStringExtra("ImageList");
       JsonList jsonList=new JsonList(jsonList_string);

        context = ImageViewPagerActivity.this;
        findViewById(R.id.sub_drawerMenu).setOnClickListener(ImageViewPagerActivity.this);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        slideViewPager = findViewById(R.id.slideViewPager);
        sliderAdapter = new ImageSliderAdapter(context,jsonList);
        slideViewPager.setAdapter(sliderAdapter);

        loadingDialog = new LoadingDialog(this);

        TabLayout tabLayout = findViewById(R.id.tab_layoutt);
        tabLayout.setupWithViewPager(slideViewPager, true);

    }



    @Override
    public void onClick(View v)
    {
          if (v.getId() == R.id.sub_drawerMenu)
    {

           onMethodClick(v);
    }

    }
    public void  onMethodClick(View v)
    {
        finish();
        ((ImageViewPagerActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

}
