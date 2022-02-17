package com.nikha.shianikha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adoisstudio.helper.Json;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.P;
import com.squareup.picasso.Picasso;

public class ReadMessageActivity extends AppCompatActivity implements View.OnClickListener
{
    private String userId = "";
    private String messageId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_read_message);

        findViewById(R.id.replyLinearLayour).setOnClickListener(this);

        String string = getIntent().getStringExtra(P.json);
        extractAndSetData(string);
    }

    private void extractAndSetData(String string) {
        if (string!=null)
        {
            try {
                Json json = new Json(string);

                string = json.getString(P.full_name);
                ((TextView)findViewById(R.id.nameTextView)).setText(string);

                string = json.getString(P.message_id);
                if (string!=null)
                    messageId = string;

                string = json.getString(P.user_id);
                if (string!=null)
                    userId = string;

                string = json.getString(P.message);
                ((TextView)findViewById(R.id.messageTextView)).setText(string);

                string = json.getString(P.date);
                ((TextView)findViewById(R.id.dateAndTimeTextView)).setText(string);

                string = json.getString(P.profile_pic);
                Picasso.get().load(string).placeholder(R.drawable.user).fit().placeholder(R.drawable.user).into(((ImageView)findViewById(R.id.circleImageView)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.replyLinearLayour)
        {
            Intent intent = new Intent(ReadMessageActivity.this, WriteMessageActivity.class);
            intent.putExtra(P.profile_id,userId);
            intent.putExtra(P.message_id,messageId);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ((ReadMessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
        else if (v.getId() == R.id.sub_drawerMenu)
        {
            onMethodClick(v);
        }

    }
    public void  onMethodClick(View v)
    {
        finish();
        ((ReadMessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }
}
