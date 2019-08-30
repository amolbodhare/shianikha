package com.nikha.shianikha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adoisstudio.helper.JsonList;
import com.nikha.shianikha.R;
import com.squareup.picasso.Picasso;

public class ImageSliderAdapter extends PagerAdapter implements ViewPager.PageTransformer
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


        Picasso.get().load(jsonList.get(position).getString("photo_name"))
                .into((ImageView) v.findViewById(R.id.slider_image));


        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1){    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);

        }
        else if (position <= 0) {    // [-1,0]
            page.setAlpha(1);
            page.setPivotX(page.getWidth());
            page.setRotationY(-90 * Math.abs(position));

        }
        else if (position <= 1){    // (0,1]
            page.setAlpha(1);
            page.setPivotX(0);
            page.setRotationY(90 * Math.abs(position));

        }
        else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);

        }
    }
}
