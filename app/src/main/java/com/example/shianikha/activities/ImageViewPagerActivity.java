package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.R;
import com.example.shianikha.adapters.SliderAdapter;

public class ImageViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager slideViewPager;
    private SliderAdapter sliderAdapter;
    private LoadingDialog loadingDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewpager);

        context = ImageViewPagerActivity.this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        slideViewPager = findViewById(R.id.slideViewPager);
        sliderAdapter = new SliderAdapter(this);
        slideViewPager.setAdapter(sliderAdapter);

        loadingDialog = new LoadingDialog(this);

        TabLayout tabLayout = findViewById(R.id.tab_layoutt);
        tabLayout.setupWithViewPager(slideViewPager, true);

    }

    @Override
    public void onClick(View v)
    {
        /*if (v.getId() == R.id.btn_login)
        {
            Intent i = new Intent(ImageViewPagerActivity.this, LoginActivity.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.btn_reg)
        {
            Intent i = new Intent(ImageViewPagerActivity.this, RegistrationActivity.class);
            startActivity(i);
        }
        finish();
 */
    }
}
