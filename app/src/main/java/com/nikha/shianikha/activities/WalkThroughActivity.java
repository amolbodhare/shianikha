package com.nikha.shianikha.activities;

        import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.adoisstudio.helper.LoadingDialog;
import com.google.android.material.tabs.TabLayout;
import com.nikha.shianikha.R;
import com.nikha.shianikha.adapters.SliderAdapter;

public class WalkThroughActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager slideViewPager;
    private SliderAdapter sliderAdapter;
    Button btn_login, btn_reg;
    private LoadingDialog loadingDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        context = WalkThroughActivity.this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        slideViewPager = findViewById(R.id.slideViewPager);
        sliderAdapter = new SliderAdapter(context);
        slideViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position)
            {

                page.setCameraDistance(20000);


                if (position < -1){
                    page.setAlpha(0);
                }
                else if (position <= 0)
                {
                    page.setAlpha(1);
                    page.setPivotX(page.getWidth());
                    page.setRotationY(90*Math.abs(position));
                }
                else if (position <= 1)
                {
                    page.setAlpha(1);
                    page.setPivotX(0);
                    page.setRotationY(-90*Math.abs(position));
                }
                else{
                    page.setAlpha(0);
                }


                if (Math.abs(position) <= 0.5){
                    page.setScaleY(Math.max(.4f,1-Math.abs(position)));
                }
                else if (Math.abs(position) <= 1){
                    page.setScaleY(Math.max(.4f,1-Math.abs(position)));

                }
            }
        });

        slideViewPager.setAdapter(sliderAdapter);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);


        loadingDialog = new LoadingDialog(this);

        TabLayout tabLayout = findViewById(R.id.tab_layoutt);
        tabLayout.setupWithViewPager(slideViewPager, true);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_login)
        {
            Intent i = new Intent(WalkThroughActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else if (v.getId() == R.id.btn_reg)
        {
            Intent i = new Intent(WalkThroughActivity.this, RegistrationActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        //finish();
    }
}
