package com.example.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.activities.FilterActivity;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.activities.PerfectMatchActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

public class MyMatchesFragment extends Fragment implements View.OnClickListener, Api.OnErrorListener, Api.OnLoadingListener {
    private OnFragmentInteractionListener mListener;

    private View fragmentView;
    private Context context;

    public static Fragment previousFragment;
    public static String previousFragmentName;
    LoadingDialog loadingDialog;

    public static MyMatchesFragment newInstance(Fragment fragment, String string) {
        MyMatchesFragment myMatchesFragment = new MyMatchesFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return myMatchesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        //((HomeActivity) context).makeStatusBarColorBlue(context.getColor(R.color.white));
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_my_matches, container, false);

            fragmentView.findViewById(R.id.top_matches).setOnClickListener(this);
            fragmentView.findViewById(R.id.i_am_looking_for).setOnClickListener(this);
            fragmentView.findViewById(R.id.looking_for_me).setOnClickListener(this);


            fragmentView.findViewById(R.id.refine_imv).setOnClickListener(this);
            fragmentView.findViewById(R.id.refine_btn).setOnClickListener(this);
            loadingDialog = new LoadingDialog(context);

            hitMatchesApi("top_matches");

            setSpannableText();

        }
        return fragmentView;
    }

    private void setSpannableText() {
        TextView textView = fragmentView.findViewById(R.id.matches_text);
        SpannableString spannableString = new SpannableString("Matches as per your preferences Edit");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PerfectMatchActivity.class);
                intent.putExtra("makeVisible", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };

        spannableString.setSpan(clickableSpan, 32, 36, 0);//for bottom line
        spannableString.setSpan(new ForegroundColorSpan(context.getColor(R.color.text_neon_color)), 32, 36, 0);
        //    SpanString.setSpan(new UnderlineSpan(), 32, 45, 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);

    }

    private void hitMatchesApi(String api_type) {
        String string = new Session(context).getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel(api_type);
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {


                            ((ListView) fragmentView.findViewById(R.id.listView)).setAdapter(new CustomListAdapte(context, json.getJsonList(P.data)));

                        } else

                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitMatchesApi");
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.top_matches) {
            hitMatchesApi("top_matches");
            changeColorsOfThreeTab(v);
        } else if (i == R.id.i_am_looking_for) {
            hitMatchesApi("i_am_looking_for");
            changeColorsOfThreeTab(v);
        } else if (i == R.id.looking_for_me) {
            hitMatchesApi("looking_for_me");
            changeColorsOfThreeTab(v);
        } else if (v.getId() == R.id.refine_imv || v.getId() == R.id.refine_btn) {
            Intent intent = new Intent(getActivity(), FilterActivity.class);
            startActivity(intent);
            ((HomeActivity) context).overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
        }
    }

    private void changeColorsOfThreeTab(View v) {
        LinearLayout parentLayout = fragmentView.findViewById(R.id.threeTabContainer);
        LinearLayout childLayout;
        TextView textView;
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            childLayout = (LinearLayout) parentLayout.getChildAt(i);
            textView = ((TextView) childLayout.getChildAt(0));
            textView.setTextColor(context.getColor(R.color.textview_grey_color));
            textView.setBackgroundColor(context.getColor(R.color.textview_back_color));
            childLayout.getChildAt(1).setVisibility(View.GONE);
        }

        childLayout = (LinearLayout) v.getParent();
        ((TextView) childLayout.getChildAt(0)).setTextColor(context.getColor(R.color.white));
        childLayout.getChildAt(0).setBackgroundColor(context.getColor(R.color.textpurle2));
        childLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    @Override
    public void onError() {
        H.showMessage(context, "Something went Wrong");
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class CustomListAdapte extends BaseAdapter implements View.OnClickListener {

        Context context;
        JsonList jsonList;
        String string = "";
        Json json;
        LinearLayout linearLayout;

        CustomListAdapte(Context context, JsonList jsonList) {
            this.context = context;
            this.jsonList = jsonList;
        }

        @Override
        public int getCount() {
            return jsonList.size();
        }

        @Override
        public Object getItem(int position) {
            return jsonList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.matches_card, null, false);

            json = jsonList.get(position);

            ImageView imageView = convertView.findViewById(R.id.thumbnail);
            imageView.setOnClickListener(this);

            string = json.getString(P.user_id);
            imageView.setTag(string);

            try {
                Picasso.get().load(json.getString(P.profile_pic)).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            imageView = convertView.findViewById(R.id.likeImageView);
            imageView.setOnClickListener(this);

            convertView.findViewById(R.id.fifth_lay).setTag(string);

            //string = json.getString(P.first_name) + " " + json.getString(P.middle_name) + " " + json.getString(P.last_name);
            string = json.getString(P.full_name);

            ((TextView) convertView.findViewById(R.id.full_name)).setText(string);
            ((TextView) convertView.findViewById(R.id.time)).setText(json.getString(P.day) + " " + "day ago");
            ((TextView) convertView.findViewById(R.id.profile_id_tv)).setText(json.getString(P.profile_id) + " " + "day ago");
            ((TextView) convertView.findViewById(R.id.age_tv)).setText(json.getString(P.age) + "yrs,");
            ((TextView) convertView.findViewById(R.id.height_tv)).setText(json.getString(P.height) + "''");
            ((TextView) convertView.findViewById(R.id.profession_tv)).setText(json.getString(P.edu_level));
            //((TextView)convertView.findViewById(R.id.caste_tv)).setText(jsonList.get(position).getString(P.edu_level));
            ((TextView) convertView.findViewById(R.id.religion_tv)).setText(json.getString(P.religion));

            convertView.findViewById(R.id.imageView).setOnClickListener(this);

            linearLayout = convertView.findViewById(R.id.linearLayout);
            string = json.getString(P.photo_available);
            if (string.equals("0")) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout.findViewById(R.id.button).setOnClickListener(this);
            } else if (string.equals("1")) {
                linearLayout.setVisibility(View.GONE);
                linearLayout.findViewById(R.id.button).setOnClickListener(null);
            }
            string = json.getString(P.user_id);
            linearLayout.findViewById(R.id.button).setTag(string);

            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.thumbnail) {
                Object object = v.getTag();
                if (object != null) {
                    string = object.toString();

                    ((HomeActivity) context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName, string);
                    ((HomeActivity) context).fragmentLoader(((HomeActivity) context).profileDetailsFragments, "Profile Details");
                }
            } else if (v.getId() == R.id.imageView) {
                ImageView imageView = (ImageView) v;
                if (imageView.getDrawable() == null) {
                    imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
                } else
                    imageView.setImageDrawable(null);
            } else if (v.getId() == R.id.button) {
                Object object = v.getTag();
                if (object != null) {
                    string = object.toString();
                    hitRequestPhotoApi(string);
                }
            }
            else if (v.getId() == R.id.likeImageView)
            {
                ImageView imageView = (ImageView) v;
                String string = (String) imageView.getTag();
                if (string.equals("0")) {
                    imageView.setColorFilter(context.getColor(R.color.green2));
                    imageView.setTag("1");
                }
                else if (string.equals("1")) {
                    imageView.setColorFilter(context.getColor(R.color.white));
                    imageView.setTag("0");
                }

                RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                Object object = relativeLayout.getTag();
                if (object != null)
                    string = object.toString();

                hitLikeApi(string);
            }
        }
    }

    private void hitLikeApi(String string) {
        Json json = new Json();
        json.addString(P.profile_id, string);

        string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("like");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            json = json.getJson(P.data);

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitLikeApi");
    }

    private void hitRequestPhotoApi(String string) {
        Json json = new Json();
        json.addString(P.profile_id, string);

        string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("request_photo");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                            H.showMessage(context, "Your request has been sent.");
                        else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitRequestPhotoApi");
    }
}
