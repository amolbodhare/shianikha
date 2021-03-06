package com.nikha.shianikha.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.google.android.material.tabs.TabLayout;
import com.nikha.shianikha.R;
import com.nikha.shianikha.adapters.ImageSliderAdapter;

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
