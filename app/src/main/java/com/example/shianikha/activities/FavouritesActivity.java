package com.example.shianikha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.shianikha.R;



public class FavouritesActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //((HomeActivity) context).makeStatusBarColorBlue(context.getColor(R.color.white));

        findViewById(R.id.sub_drawerMenu).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

          if (v.getId() == R.id.sub_drawerMenu)
        {
            onMethodClick(v);
        }
    }


    public void  onMethodClick(View v)
    {
        finish();
        ((FavouritesActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }
}
