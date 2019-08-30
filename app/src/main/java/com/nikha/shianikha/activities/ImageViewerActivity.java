package com.nikha.shianikha.activities;

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

import com.nikha.shianikha.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageViewerActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public Context context;
    public View view;
    ArrayList<String> imageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        context = this;
        imageList =new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());
    }
    class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return false;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.slider_item, container, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            try {
                Picasso.get().load(imageList.get(position)).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((BaseActivity)context).showImagePopUp(context,imagaBaseUrl +imageList.get(position));
                }
            });*/

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }


    }
}


