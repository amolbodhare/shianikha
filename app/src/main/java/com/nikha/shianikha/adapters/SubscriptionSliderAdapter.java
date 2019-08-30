package com.nikha.shianikha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikha.shianikha.R;

public class SubscriptionSliderAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;

    public SubscriptionSliderAdapter(Context context)
    {
        this.context=context;

    }
    public int[] slide_images={R.drawable.intro1,R.drawable.intro2,R.drawable.intro3};
    public String[] slide_headings={"INTRO HEADING","INTRO HEADING","INTRO HEADING"};
    public String[] slide_descs={"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"};
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return view==(CardView)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.sub_plan_slide_layout,container,false);

        /*TextView title_tv=v.findViewById(R.id.title_tv);
        TextView desc_tv=v.findViewById(R.id.desc_tv);


        title_tv.setText(slide_headings[position]);
        desc_tv.setText(slide_descs[position]);
*/
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CardView)object);
    }
}
