package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shianikha.R;

public class InboxActivity extends AppCompatActivity implements View.OnClickListener
{


    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        context = InboxActivity.this;
        //((HomeActivity) context).makeStatusBarColorBlue(context.getColor(R.color.white));


            findViewById(R.id.top_matches).setOnClickListener(this);
            findViewById(R.id.i_am_looking_for).setOnClickListener(this);
            findViewById(R.id.looking_for_me).setOnClickListener(this);

            ((ListView) findViewById(R.id.listView)).setAdapter(new CustomListAdapte());


        }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.top_matches || i == R.id.i_am_looking_for || i == R.id.looking_for_me)
            changeColorsOfThreeTab(v);
        /*else if (v.getId() == R.id.refine_imv || v.getId() == R.id.refine_btn)
        {
            Intent intent = new Intent(InboxActivity.this, FilterActivity.class);
            startActivity(intent);
            ((HomeActivity) context).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }*/

    }

    class CustomListAdapte extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.matches_card, null, false);

            convertView.findViewById(R.id.imageView).setOnClickListener(this);

            if (position == 1) {
                convertView.findViewById(R.id.thumbnail).setVisibility(View.GONE);
                convertView.findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);
            }

            return convertView;
        }

        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.imageView)
            {
                ImageView imageView = (ImageView)v;
                if (imageView.getDrawable() == null)
                    imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
                else
                    imageView.setImageDrawable(null);
            }
        }
    }

    private void changeColorsOfThreeTab(View v) {
        LinearLayout parentLayout = findViewById(R.id.threeTabContainer);
        LinearLayout childLayout;
        TextView textView;
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            childLayout = (LinearLayout) parentLayout.getChildAt(i);
            textView = ((TextView) childLayout.getChildAt(0));
            textView.setTextColor(context.getColor(R.color.textview_grey_color));
            textView.setBackgroundColor(context.getColor(R.color.textview_back_color));
            childLayout.getChildAt(1).setVisibility(View.GONE);
        }

        childLayout = (LinearLayout) v.getParent();
        ((TextView) childLayout.getChildAt(0)).setTextColor(context.getColor(R.color.white));
        childLayout.getChildAt(0).setBackgroundColor(context.getColor(R.color.textpurle2));
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

}
