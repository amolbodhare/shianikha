package com.example.shianikha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Json;
import com.example.shianikha.R;

public class RefineSearchItemActivity extends AppCompatActivity {

    TextView filter_title;
    LinearLayout dynamic_layout_ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refine_search_item);
        filter_title=findViewById(R.id.filter_title);
        dynamic_layout_ll=findViewById(R.id.dymanic_layout);

        filter_title.setText(getIntent().getStringExtra("filter_title"));
        setUpCustomList();
    }

    private  void setUpCustomList()
    {


        for (int i = 0; i < 3; i++)
        {
            final TextView sub_menu_link_tv;

            final ImageView imv;
            View view = getLayoutInflater().inflate(R.layout.inflating_view, null, false);

            RelativeLayout rl=view.findViewById(R.id.active_members_link_layout);
            imv=view.findViewById(R.id.imv);

            sub_menu_link_tv=rl.findViewById(R.id.sub_menu_link_tv);
            sub_menu_link_tv.setText("Amol");

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    imv.setImageResource(R.drawable.ic_green_dot);

                }
            });

            dynamic_layout_ll.addView(view);
        }

    }
}
