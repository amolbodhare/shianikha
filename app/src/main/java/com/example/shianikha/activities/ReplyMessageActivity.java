package com.example.shianikha.activities;

import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.shianikha.R;

public class ReplyMessageActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_reply_message);


    }

    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.reply_msg_btn)
        {

        }

        else if (v.getId() == R.id.sub_drawerMenu)
        {
            onMethodClick(v);
        }
    }

    public void  onMethodClick(View v)
    {
        finish();
        ((ReplyMessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }


    }

