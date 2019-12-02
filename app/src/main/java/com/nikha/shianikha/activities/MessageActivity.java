package com.nikha.shianikha.activities;

import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private LoadingDialog loadingDialog;
    private String apiName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        setContentView(R.layout.activity_message);
        loadingDialog = new LoadingDialog(this);

        //((HomeActivity) context).makeStatusBarColorBlue(context.getColor(R.color.white));

        findViewById(R.id.inbox).setOnClickListener(this);
        findViewById(R.id.sent_message).setOnClickListener(this);

        /*findViewById(R.id.receive_list).setOnClickListener(this);
        findViewById(R.id.sub_drawerMenu).setOnClickListener(this);*/

        String string = getIntent().getStringExtra("open");
        if (string != null)
        {
            apiName = string.equalsIgnoreCase(P.inbox) ? "inbox" : "sent_message";
            hitApiForInboxOrSentMessage(apiName);
        }

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
                hitApiForInboxOrSentMessage(apiName);
            }
        });
    }

    private void hitApiForInboxOrSentMessage(final String string)
    {
        Json json = new Json();
        json.addString(P.token_id, new Session(this).getString(P.tokenData));

        RequestModel requestModel = RequestModel.newRequestModel(string);
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait...");
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(MessageActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(final Json json)
                    {
                        if (json.getInt(P.status) == 1) {
                            JsonList jsonList = json.getJsonList(P.data);
                            if (jsonList == null)
                                return;

                            if (string.equalsIgnoreCase("sent_message"))
                                changeColorsOfThreeTab(findViewById(R.id.sent_message));

                            ListView listView = findViewById(R.id.listView);
                            listView.setAdapter(new ListAdapter(jsonList));
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                {
                                    H.log("viewIs",view.getTag()+"");
                                    Object object = view.getTag();
                                    if (object!=null)
                                    {
                                        String string = object.toString();
                                        Intent intent = new Intent(MessageActivity.this, ReadMessageActivity.class);
                                        intent.putExtra(P.json,string);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                })
                .run("hitApiForInboxOrSentMessage");
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.inbox || i == R.id.sent_message)// || i == R.id.receive_list)
            changeColorsOfThreeTab(v);

        if (i == R.id.inbox) {
            apiName = "inbox";
            hitApiForInboxOrSentMessage(apiName);
        }
        else if (i==R.id.sent_message) {
            apiName = "sent_message";
            hitApiForInboxOrSentMessage(apiName);
        }
    }

    public void onMethodClick(View v) {
        finish();
        ((MessageActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }


    private class ListAdapter extends BaseAdapter {
        private JsonList jsonList;
        private Json json;
        private String string;
        private ImageView imageView;

        private ListAdapter(JsonList jsons) {
            jsonList = jsons;
        }

        @Override
        public int getCount() {
            return jsonList.size();
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
                convertView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.inbox_list_item, null, false);

            json = jsonList.get(position);
            convertView.setTag(json);

            string = json.getString(P.profile_pic);
            try
            {
                imageView = convertView.findViewById(R.id.notiCircle);
                Picasso.get().load(string).placeholder(R.drawable.user).fit().into(imageView);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            string = json.getString(P.full_name);
            ((TextView)convertView.findViewById(R.id.nameTextView)).setText(string);

            string = json.getString(P.message_id);
            convertView.findViewById(R.id.del_msg_btn_imv).setTag(string);

            string = json.getString(P.message);
            ((TextView)convertView.findViewById(R.id.messageTextView)).setText(string);

            string = json.getString(P.date);
            ((TextView)convertView.findViewById(R.id.dateAndTime)).setText(string);


            convertView.findViewById(R.id.del_msg_btn_imv).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Object object = v.getTag();
                    if (object!=null) {
                        string = object.toString();
                        hitDeleteMessageApi(string);
                    }
                }
            });

            return convertView;
        }
    }

    private void hitDeleteMessageApi(String string)
    {
        Json json = new Json();
        json.addString(P.id,string);
        json.addString(P.token_id, new Session(this).getString(P.tokenData));

        RequestModel requestModel = RequestModel.newRequestModel("delete_message");
        requestModel.addJSON(P.data, json);

        Api.newApi(this, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait...");
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(MessageActivity.this, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            H.showMessage(MessageActivity.this, "Message deleted successfully.");
                            hitApiForInboxOrSentMessage(apiName);
                        }
                        else
                            H.showMessage(MessageActivity.this,json.getString(P.msg));
                    }
                })
                .run("hitDeleteMessageApi");
    }

    private void changeColorsOfThreeTab(View v) {

        LinearLayout parentLayout = findViewById(R.id.threeTabContainer);
        LinearLayout childLayout;
        TextView textView;

        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            childLayout = (LinearLayout) parentLayout.getChildAt(i);
            textView = ((TextView) childLayout.getChildAt(0));
            textView.setTextColor(this.getColor(R.color.textview_grey_color));
            textView.setBackgroundColor(this.getColor(R.color.textview_back_color));
            childLayout.getChildAt(1).setVisibility(View.GONE);
        }

        childLayout = (LinearLayout) v.getParent();
        ((TextView) childLayout.getChildAt(0)).setTextColor(this.getColor(R.color.white));
        childLayout.getChildAt(0).setBackgroundColor(this.getColor(R.color.textpurle2));
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        App.mPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        App.mPlayer.pause();
    }

}
