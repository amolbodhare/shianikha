package com.nikha.shianikha.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.R;
import com.nikha.shianikha.adapters.SubscriptionSliderAdapter;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;

    private ViewPager subPlanSlideViewPager;
    private SubscriptionSliderAdapter subPlanSliderAdapter;
    ImageView imv_pre_btn, imv_next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getColor(R.color.transparent));
        context = SubscriptionActivity.this;
        subPlanSlideViewPager = findViewById(R.id.sub_plan_viewpager);

        imv_pre_btn = findViewById(R.id.prev_imv);
        imv_next_btn = findViewById(R.id.next_imv);

        imv_pre_btn.setOnClickListener(this);
        imv_next_btn.setOnClickListener(this);

        hitSubscriptionPlanApi();
    }

    private void hitSubscriptionPlanApi()
    {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("plan_list");
        Json json = new Json();
        json.addString(P.token_id,new Session(context).getString(P.tokenData));
        requestModel.addJSON(P.data, json);
        //5XtinfoxR0jl2d4JQGKvzVwNc

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (!isDestroyed()) {
                            if (isLoading)
                                loadingDialog.show("Please wait...");
                            else
                                loadingDialog.dismiss();
                        }
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1)
                        {
                            int i = json.getInt(P.currency_type);

                            JsonList jsonList = json.getJsonList(P.data);
                            if (jsonList != null)
                            {
                                subPlanSliderAdapter = new SubscriptionSliderAdapter(context, jsonList,i);

                                subPlanSlideViewPager.setAdapter(subPlanSliderAdapter);
                            }
                        }
                    }
                })
                .run("hitSubscriptionPlanApi");
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.prev_imv) {
            subPlanSlideViewPager.setCurrentItem(getItem(-1), true);
        }
        else if (v.getId() == R.id.next_imv)
        {
            subPlanSlideViewPager.setCurrentItem(getItem(+1), true);
        }
        else if (v.getId() == R.id.sub_drawerMenu)
        {

            onMethodClick(v);
        }
    }

    public void  onMethodClick(View v)
    {

        finish();
        ((SubscriptionActivity.this)).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    private int getItem(int i) {
        return subPlanSlideViewPager.getCurrentItem() + i;
    }
}
