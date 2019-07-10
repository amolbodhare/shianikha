package com.example.shianikha.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shianikha.R;
import com.example.shianikha.fragments.ProfileDetailsFragments;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageViewerActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public Context context;
    public View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        context = this;
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());
    }

}

class ViewPagerAdapter extends PagerAdapter {
    View view;
    Context context;
    ArrayList<String> imageList;

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }


}
