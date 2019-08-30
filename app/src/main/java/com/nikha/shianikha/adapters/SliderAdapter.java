package com.nikha.shianikha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikha.shianikha.R;

public class SliderAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;

    public  SliderAdapter(Context context)
    {
        this.context=context;

    }
    public int[] slide_images={R.drawable.intro1,R.drawable.intro2,R.drawable.intro3};
    public String[] slide_headings={" "," "," "};
    public String[] slide_descs={"Shia Nikah the fastest growing matchmaking service, was founded with a simple objective - to help people find happiness"," Provide a pleasant, satisfying, and superior matchmaking experience to our customers while zealously protecting their privacy and security","Give our customers complete control through easy to use interfaces and features that can help them identify, filter and contact potential partners"};
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

        ImageView img=v.findViewById(R.id.slider_image);
        TextView title_tv=v.findViewById(R.id.title_tv);
        TextView desc_tv=v.findViewById(R.id.desc_tv);

        img.setImageResource(slide_images[position]);
        title_tv.setText(slide_headings[position]);
        desc_tv.setText(slide_descs[position]);

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
