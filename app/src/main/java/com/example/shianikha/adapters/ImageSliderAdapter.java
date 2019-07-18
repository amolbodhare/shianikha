package com.example.shianikha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.JsonList;
import com.bumptech.glide.Glide;
import com.example.shianikha.R;
import com.example.shianikha.commen.P;

import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;
    JsonList jsonList;

    public ImageSliderAdapter(Context context, JsonList jsonList)
    {
        this.context=context;
        this.jsonList=jsonList;

    }


    @Override
    public int getCount() {
        return jsonList.size();
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

        View v=layoutInflater.inflate(R.layout.image_slider_layout,container,false);


        Glide.with(context)
                .asBitmap()
                .load(jsonList.get(position).getString("photo_name"))
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
