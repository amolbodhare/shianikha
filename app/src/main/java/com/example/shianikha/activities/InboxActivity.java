package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shianikha.NotifacationDetails;
import com.example.shianikha.R;

public class InboxActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_inbox);
        context = InboxActivity.this;

        //((HomeActivity) context).makeStatusBarColorBlue(context.getColor(R.color.white));

        findViewById(R.id.inbox).setOnClickListener(this);
        findViewById(R.id.sent_message).setOnClickListener(this);
        findViewById(R.id.receive_list).setOnClickListener(this);
        findViewById(R.id.sub_drawerMenu).setOnClickListener(this);

        ((ListView) findViewById(R.id.listView)).setAdapter(new ListAdapter());

        ((ListView) findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(InboxActivity.this, InboxMessageActivity.class);
                startActivity(i);
                ((InboxActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.inbox || i == R.id.sent_message || i == R.id.receive_list) {
            changeColorsOfThreeTab(v);
        } else if (v.getId() == R.id.sub_drawerMenu) {
            onMethodClick(v);
        }

    }

    public void onMethodClick(View v) {
        finish();
        ((InboxActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }


    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 25;
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
            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.inbox_list_item, null, false);
                convertView.findViewById(R.id.del_msg_btn_imv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "want to delete the record?", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return convertView;
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
