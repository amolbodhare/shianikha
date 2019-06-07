package com.example.shianikha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shianikha.R;

public class RefineSearchItemActivity extends AppCompatActivity {

    TextView filter_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refine_search_item);
        filter_title=findViewById(R.id.filter_title);
        filter_title.setText(getIntent().getStringExtra("filter_title"));
    }
}
