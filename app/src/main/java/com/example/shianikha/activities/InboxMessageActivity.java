package com.example.shianikha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.shianikha.R;

public class InboxMessageActivity extends AppCompatActivity implements View.OnClickListener
{

    ImageView replymsgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_message);
        replymsgBtn=findViewById(R.id.reply_msg_btn);
        replymsgBtn.setOnClickListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.reply_msg_btn)
        {
            Intent i = new Intent(InboxMessageActivity.this, ReplyMessageActivity.class);
            startActivity(i);
            ((InboxMessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else if (v.getId() == R.id.sub_drawerMenu)
        {
            onMethodClick(v);
        }

    }
    public void  onMethodClick(View v)
    {
        finish();
        ((InboxMessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }
}
