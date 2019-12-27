package com.nikha.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;


public class MyActivityFragment extends Fragment implements View.OnClickListener {
    private View fragmentView;
    private Context context;
    public static Fragment previousFragment;
    public static String previousFragmentName;
    private String which = "Request";
    private String whichApi = "";
    private String userId = "";

    public MyActivityFragment() {
        // Required empty public constructor
    }

    public static MyActivityFragment newInstance(Fragment fragment, String string) {
        MyActivityFragment myActivityFragment = new MyActivityFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return myActivityFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_my_activity, container, false);

            whichApi = "request_list";

            hitApiForList(whichApi);

            final SwipeRefreshLayout swipeRefreshLayout = fragmentView.findViewById(R.id.swipeRefreshLayout);
           swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    hitApiForList(whichApi);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

            setOnClickListenerOnScrollBarChild();
        }

        return fragmentView;
    }

    private void hitApiForList(String string) {
        Json json = new Json();
        json.addString(P.token_id, new Session(context).getString(P.tokenData));
        whichApi = string;

        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel(string);
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show();
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            JsonList jsonList = json.getJsonList(P.data);
                            if (jsonList != null)
                                ((GridView) fragmentView.findViewById(R.id.gridView)).setAdapter(new GridViewAdapter(jsonList));

                        }
                        else {
                            H.showMessage(context, json.getString(P.msg));
                            ((GridView) fragmentView.findViewById(R.id.gridView)).setAdapter(new GridViewAdapter(new JsonList()));
                        }
                    }
                })
                .run("hitMyActivitiesApi");
    }

    private void setOnClickListenerOnScrollBarChild() {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.linearLayout);
        for (int i = 0; i < linearLayout.getChildCount(); i += 2)
            linearLayout.getChildAt(i).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.linearLayout);
        LinearLayout childLayout;
        TextView textView;

        for (int i = 0; i < linearLayout.getChildCount(); i += 2) {
            childLayout = (LinearLayout) linearLayout.getChildAt(i);
            textView = (TextView) childLayout.getChildAt(0);
            textView.setBackgroundColor(context.getColor(R.color.textview_back_color));
            textView.setTextColor(context.getColor(R.color.textview_grey_color));
            textView.setElevation(0f);

            if ((i % 4 == 0) && view.getId() == childLayout.getId())
                implementAutoScroll(childLayout);

            childLayout.getChildAt(1).setVisibility(View.INVISIBLE);
        }

        childLayout = (LinearLayout) view;
        textView = (TextView) childLayout.getChildAt(0);
        String string = textView.getText().toString();
        which = string;
        chooseApi(string);
        textView.setBackgroundColor(context.getColor(R.color.textpurle2));
        textView.setTextColor(context.getColor(R.color.white));
        textView.setElevation(7f);
        H.log("theDataList",textView.toString());
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void chooseApi(String string)
    {
        if (string.equalsIgnoreCase("request"))
            hitApiForList("request_list");
        else if (string.equalsIgnoreCase("accepted"))
            hitApiForList("accepted");
        else if (string.equalsIgnoreCase("accepted me"))
            hitApiForList("accepted_me");
        else if (string.equalsIgnoreCase("contacted me"))
            hitApiForList("contacted_me");
        else if (string.equalsIgnoreCase("I Have Contacted"))
            hitApiForList("i_have_contacted");
        else if (string.equalsIgnoreCase("Who Visited My Profile"))
            hitApiForList("who_visited_my_profile");
        else if (string.equalsIgnoreCase("Profiles Viewed By Me"))
            hitApiForList("profiles_viewed_by_me");
        else
            H.log("wentTo","log and string is "+string);
    }

    private void implementAutoScroll(LinearLayout linearLayout) {
        HorizontalScrollView horizontalScrollView = fragmentView.findViewById(R.id.horizontalScrollView);
        horizontalScrollView.smoothScrollBy((int) linearLayout.getX(), 0);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class GridViewAdapter extends BaseAdapter implements View.OnClickListener {
        private JsonList jsonList;

        private GridViewAdapter(JsonList jsons) {
            jsonList = jsons;
        }

        @Override
        public int getCount() {
            if (jsonList.size() == 0)
                fragmentView.findViewById(R.id.noDataTextView).setVisibility(View.VISIBLE);
            else
                fragmentView.findViewById(R.id.noDataTextView).setVisibility(View.GONE);
            return jsonList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Json json = jsonList.get(i);
            String string = "";

            if (!which.equals("Request"))
                view = LayoutInflater.from(context).inflate(R.layout.accepted_list_card, viewGroup, false);
            else {
                view = LayoutInflater.from(context).inflate(R.layout.request_item, viewGroup, false);
                string = json.getString(P.user_id);

                Button button = view.findViewById(R.id.acceptButton);
                button.setTag(string);
                button.setOnClickListener(this);

                button = view.findViewById(R.id.rejectButton);
                button.setTag(string);
                button.setOnClickListener(this);
            }

            string = json.getString(P.profile_pic);
            try {
                Picasso.get().load(string).fit().placeholder(R.drawable.user).into((ImageView) view.findViewById(R.id.thumbnail));
            } catch (Exception e) {
                e.printStackTrace();
            }

            string = json.getString(P.ph_number);
            ImageView imageView = view.findViewById(R.id.imv_call);
            if (imageView != null) {
                imageView.setTag(string);
                imageView.setOnClickListener(this);
            }

            string = json.getString(P.email);
            imageView = view.findViewById(R.id.imv_mail);
            if (imageView != null) {
                imageView.setTag(string);
                imageView.setOnClickListener(this);
            }

            string = json.getString(P.profile_id);
            ((TextView) view.findViewById(R.id.profile_id)).setText(string);

            if (App.showName) {

                string = json.getString(P.full_name);
                ((TextView) view.findViewById(R.id.title)).setText(string);

                StringBuilder stringBuilder = new StringBuilder();

                string = json.getString(P.age) + "yrs";
                stringBuilder.append(string);

                string = json.getString(P.height);
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(string.split(".")));
                if (arrayList.size() == 2)
                    string = arrayList.get(0) + "'" + arrayList.get(1) + "\"";
                stringBuilder.append(string);

                string = json.getString(P.religion);
                stringBuilder.append(string);

                ((TextView) view.findViewById(R.id.age_height_cast_religion_details)).setText(string);
            }

            return view;
        }

        @Override
        public void onClick(View view)
        {
            if (view.getId() == R.id.acceptButton || view.getId() == R.id.rejectButton) {
                if (view.getId() == R.id.acceptButton)
                    hitAcceptRejectApi("1", view.getTag() + "");
                else
                    hitAcceptRejectApi("0", view.getTag() + "");
            } else if (App.showName) {
                Object object;
                if (view.getId() == R.id.imv_call) {
                    object = view.getTag();
                    if (object != null)
                        makeIntent(object.toString(), "p");
                } else if (view.getId() == R.id.imv_mail) {
                    object = view.getTag();
                    if (object != null)
                        makeIntent(object.toString(), "m");
                }
            } else {
                H.showYesNoDialog(context, "Plan not purchased", "Feature available only for paid user.", "purchase plan", "cancel", new H.OnYesNoListener() {
                    @Override
                    public void onDecision(boolean isYes) {
                        if (isYes) {
                            ((HomeActivity) context).showSubscriptionPlanActivity();
                            //((HomeActivity) context).onBack(new View(context));
                        }
                    }
                });
            }
        }
    }

    private void hitAcceptRejectApi(String flag, String id) {
        Json json = new Json();
        json.addString(P.token_id, new Session(context).getString(P.tokenData));
        json.addString("user_id_receiver", id);
        json.addString("status", flag);

        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("accept_reject_request");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show();
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        //hitApiForList("request_list");
                        chooseApi(which);
                    }
                })
                .run("hitAcceptRejectApi");
    }

    private void makeIntent(String data, String string) {
        Intent intent = new Intent();

        try {
            if (string.equalsIgnoreCase("p")) {
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + data));
            } else if (string.equalsIgnoreCase("m")) {
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{data});//, " atul.hemani@citizencenter.co.in"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}
