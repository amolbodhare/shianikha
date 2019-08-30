package com.nikha.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.R;
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

            hitApiForList("accepted");

            setOnClickListenerOnScrollBarChild();
        }

        return fragmentView;
    }

    private void hitApiForList(String string) {
        Json json = new Json();
        json.addString(P.token_id, new Session(context).getString(P.tokenData));

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

                        if (json.getInt(P.status) == 1) {
                            JsonList jsonList = json.getJsonList(P.data);
                            if (jsonList != null)
                                ((GridView) fragmentView.findViewById(R.id.gridView)).setAdapter(new GridViewAdapter(jsonList));

                        } else
                            H.showMessage(context, json.getString(P.msg));
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
        chooseApi(string);
        textView.setBackgroundColor(context.getColor(R.color.textpurle2));
        textView.setTextColor(context.getColor(R.color.white));
        textView.setElevation(7f);
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void chooseApi(String string) {
        if (string.equalsIgnoreCase("accepted"))
            hitApiForList("accepted");
        else if (string.equalsIgnoreCase("accepted me"))
            hitApiForList("accepted_me");
        else if (string.equalsIgnoreCase("contacted me"))
            hitApiForList("contacted_me");
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
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.accepted_list_card, viewGroup, false);

            Json json = jsonList.get(i);
            String string = json.getString(P.profile_pic);
            try {
                Picasso.get().load(string).fit().placeholder(R.drawable.user).into((ImageView) view.findViewById(R.id.thumbnail));
            } catch (Exception e) {
                e.printStackTrace();
            }

            string = json.getString(P.full_name);
            ((TextView) view.findViewById(R.id.title)).setText(string);

            string = json.getString(P.profile_id);
            ((TextView) view.findViewById(R.id.profile_id)).setText(string);

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

            string = json.getString(P.ph_number);
            ImageView imageView = view.findViewById(R.id.imv_call);
            imageView.setTag(string);
            imageView.setOnClickListener(this);

            string = json.getString(P.email);
            imageView = view.findViewById(R.id.imv_mail);
            imageView.setTag(string);
            imageView.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View view) {
          /*  Object object;
            if (view.getId() == R.id.imv_call) {
                object = view.getTag();
                if (object != null)
                    makeIntent(object.toString(), "p");
            } else if (view.getId() == R.id.imv_mail) {
                object = view.getTag();
                if (object != null)
                    makeIntent(object.toString(), "m");
            }*/
        }
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
