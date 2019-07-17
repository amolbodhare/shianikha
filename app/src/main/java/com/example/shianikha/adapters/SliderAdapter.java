package com.example.shianikha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

public class SliderAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;

    public  SliderAdapter(Context context)
    {
        this.context=context;

    }
    public int[] slide_images={R.drawable.kangana_ranaut,R.drawable.kangna,R.drawable.intro3};
    public String[] slide_headings={"INTRO HEADING","INTRO HEADING","INTRO HEADING"};
    public String[] slide_descs={"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"};
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.slide_layout,container,false);



        //img.setImageResource(slide_images[position]);

        Glide.with(context)
                .asBitmap()
                .load(slide_images[position])
                //.load(R.drawable.kangna)
                .into((ImageView) v.findViewById(R.id.slider_image));

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
