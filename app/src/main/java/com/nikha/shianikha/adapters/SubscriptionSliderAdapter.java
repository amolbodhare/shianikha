package com.nikha.shianikha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.P;

public class SubscriptionSliderAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
    private LayoutInflater layoutInflater;
    private JsonList jsonList;

    public SubscriptionSliderAdapter(Context context, JsonList jsons)
    {
        this.context=context;
        jsonList  = jsons;
    }

    //public String[] slide_headings={"INTRO HEADING","INTRO HEADING","INTRO HEADING"};
    //public String[] slide_descs={"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"};

    @Override
    public int getCount() {
        return jsonList.size();
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

        Json json = jsonList.get(position);

        String string = json.getString(P.package_name);
        TextView textView = v.findViewById(R.id.sub_plan_tv);
        textView.setText(string);

        string = " "+json.getString(P.amount);
        textView = v.findViewById(R.id.sub_plan_price_tv);
        textView.setText('\u20B9'+string);

        string = json.getString(P.description);
        textView = v.findViewById(R.id.sub_plan_duration_tv);
        textView.setText(H.convertHtml(string));

        string = json.getString(P.view);
        textView = v.findViewById(R.id.viewProfileTextView);
        //textView.setText("View " + string+ " profiles" );
        textView.setText(String.format("View %s profiles",string));

        textView = v.findViewById(R.id.shareProfileTextView);
        //textView.setText("Share " + string+ " profiles" );
        textView.setText(String.format("Share %s profiles",string));

        string = json.getString(P.id);
        Button button = v.findViewById(R.id.continueButton);
        button.setTag(string);
        button.setOnClickListener(this);

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CardView)object);
    }

    @Override
    public void onClick(View view)
    {
        H.log("planIdIs",view.getTag()+"");
    }
}
