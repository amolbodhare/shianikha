package com.nikha.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.activities.ImageViewPagerActivity;
import com.nikha.shianikha.activities.WriteMessageActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;


public class ProfileDetailsFragments extends Fragment implements View.OnClickListener, Api.OnLoadingListener, Api.OnErrorListener {
    private OnFragmentInteractionListener mListener;
    private Context context;
    private View fragmentView;
    public static Fragment previousFragment;
    public static String previousFragmentName;
    private static String profileId;
    private LoadingDialog loadingDialog;
    private JsonList jsonList;
    private String sharingLink = "";
    private int viewFlag;//0 = dont'show, 1 = show, 2 = plan expired.
    private String allowLike = "";
    private String allowConnected = "";

    public static ProfileDetailsFragments newInstance(Fragment fragment, String prev_frag_name, String profile_id) {
        ProfileDetailsFragments profileDetailsFragments = new ProfileDetailsFragments();
        previousFragment = fragment;
        previousFragmentName = prev_frag_name;
        profileId = profile_id;

        return profileDetailsFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for th1is fragment
        context = getContext();
        loadingDialog = new LoadingDialog(context);

        fragmentView = inflater.inflate(R.layout.fragment_profile_details, container, false);

        fragmentView.findViewById(R.id.imageViewer1).setOnClickListener(this);
        fragmentView.findViewById(R.id.sendMessageLinearLayout).setOnClickListener(this);
        fragmentView.findViewById(R.id.shareProfileLinearLayout).setOnClickListener(this);
        fragmentView.findViewById(R.id.favouriteLinearLayout).setOnClickListener(this);
        fragmentView.findViewById(R.id.likeImageView).setOnClickListener(this);

        hitProfileDetailsApi("profile_details", profileId);


        // here in profileId we have got user_id only but according to api its name is changed as profileId for this api
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
       /* if (viewFlag == 2 && v.getId() != R.id.favouriteLinearLayout && v.getId() != R.id.button) // condition after & is written to exclude from paid
        {
            showExpiredPopUp();
            return;
        }
        else if (viewFlag == 0 && v.getId() != R.id.favouriteLinearLayout && v.getId() != R.id.button)// condition after & is written to exclude favourite from paid
        {
            showNotPurchasedPopUp();
            return;
        }*/

        if (v.getId() == R.id.imageViewer1) {
            Intent intent = new Intent(context, ImageViewPagerActivity.class);
            intent.putExtra("ImageList", jsonList.toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.sendMessageLinearLayout) {
            if (viewFlag == 0) {
                ((HomeActivity)context).showNotPurchasedPopUp();
                return;
            } else if (viewFlag == 2) {
                ((HomeActivity)context).showExpiredPopUp();
                return;
            }

            Intent intent = new Intent(context, WriteMessageActivity.class);
            Object object = v.getTag();
            if (object != null) {
                String string = object.toString();
                intent.putExtra(P.profile_id, string);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.imageView) {
            if (allowConnected.equalsIgnoreCase("0"))
                ((HomeActivity)context).showNotPurchasedPopUp();
            else if (allowConnected.equalsIgnoreCase("2"))
                ((HomeActivity)context).showExpiredPopUp();
            else
                hitConnectNowApi();
        } else if (v.getId() == R.id.favouriteLinearLayout) {
            Object object = v.getTag();
            if (object != null) {
                String string = object.toString();
                hitFavouriteApi(string);
            }
        } else if (v.getId() == R.id.likeImageView) {
            if (allowLike.equalsIgnoreCase("0"))
                ((HomeActivity)context).showNotPurchasedPopUp();
            else if (allowLike.equalsIgnoreCase("2"))
                ((HomeActivity)context).showExpiredPopUp();
            else
                hitLikeApi();


        } else if (v.getId() == R.id.shareProfileLinearLayout) {
            if (viewFlag == 0) {
                ((HomeActivity)context).showNotPurchasedPopUp();
                return;
            } else if (viewFlag == 2) {
                ((HomeActivity)context).showExpiredPopUp();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String string = sharingLink.isEmpty() ? "linkNotFound" : sharingLink;
            intent.putExtra(Intent.EXTRA_TEXT, string + profileId);
            startActivity(Intent.createChooser(intent, "Share Via"));
        } else if (v.getId() == R.id.button)
        {
            hitRequestPhotoApi();
        }
    }

    private void hitConnectNowApi() {
        final String string = new Session(context).getString(P.tokenData);

        Json json = new Json();
        json.addString(P.user_id_receiver, profileId);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("send_request");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        hitProfileDetailsApi("profile_details", profileId);
                    }
                })
                .run("hitConnectNowApi");
    }

    private void hitLikeApi() {
        String string = new Session(context).getString(P.tokenData);

        Json json = new Json();
        json.addString(P.profile_id, profileId);
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

                        hitProfileDetailsApi("profile_details", profileId);
                    }
                })
                .run("hitLikeApi");
    }

    private void hitFavouriteApi(String id) {

        Session session = new Session(context);
        String string = session.getString(P.tokenData);

        Json json = new Json();
        json.addString(P.token_id, string);
        json.addString(P.profile_id, id);

        RequestModel requestModel = RequestModel.newRequestModel("add_favourites");
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
                            String string = json.getString(P.profile_id);
                            H.showMessage(context, "Added to favourite");

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitFavouriteApi");

    }

    @Override
    public void onLoading(boolean isLoading) {
        if (!((HomeActivity) context).isDestroyed()) {
            if (isLoading)
                loadingDialog.show();
            else
                loadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {
        H.showMessage(context, "Something went Wrong");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void hitProfileDetailsApi(String api_type, String profileId) {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);

        Json json = new Json();
        json.addString(P.token_id, string);
        json.addString(P.profile_id, profileId);

        RequestModel requestModel = RequestModel.newRequestModel(api_type);
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json profileDetailJson) {

                        if (profileDetailJson.getInt(P.status) == 1) {
                            profileDetailJson = profileDetailJson.getJson(P.data);

                            int action = profileDetailJson.getInt(P.view);
                            viewFlag = action;

                            allowLike = profileDetailJson.getString(P.allow_like);
                            allowConnected = profileDetailJson.getString(P.allow_connected);

                            sharingLink = profileDetailJson.getString(P.share_profile);
                            setData(profileDetailJson);

                            String string = profileDetailJson.getString(P.id);
                            if (string != null)
                                handleMessageAndFavouriteButton(string);

                            fragmentView.findViewById(R.id.relativeLayout).setTag(string);

                            string = profileDetailJson.getString(P.connected);
                            ImageView imageView = fragmentView.findViewById(R.id.imageView);
                            imageView.setOnClickListener(ProfileDetailsFragments.this);
                            changeConnectButtonStatus(string);

                            string = profileDetailJson.getString(P.like);
                            imageView = fragmentView.findViewById(R.id.likeImageView);
                            if (string.equals("1"))
                                imageView.setColorFilter(Color.parseColor("#00d882"));
                            else
                                imageView.setColorFilter(Color.parseColor("#FFFFFF"));

                        } else

                            H.showMessage(context, profileDetailJson.getString(P.msg));
                    }
                })
                .run("hitProfileDetailsApi");
    }

    private void changeConnectButtonStatus(String string) {
        ImageView imageView = fragmentView.findViewById(R.id.imageView);
        TextView textView = fragmentView.findViewById(R.id.connectNow);
        if (string.equals("0")) {
            imageView.setImageDrawable(null);
            textView.setText("Connect now");
        } else if (string.equals("1")) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
            textView.setText("Connected");
        } else if (string.equals("2")) {
            imageView.setImageDrawable(null);
            textView.setText("Pending Connection");
        }
    }

    private void handleMessageAndFavouriteButton(String string) {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.favouriteLinearLayout);
        linearLayout.setTag(string);
        linearLayout.setOnClickListener(ProfileDetailsFragments.this);

        linearLayout = fragmentView.findViewById(R.id.sendMessageLinearLayout);
        linearLayout.setTag(string);
        linearLayout.setOnClickListener(ProfileDetailsFragments.this);
    }

    private void setData(Json profileDetailJson) {

        try {
            Picasso.get().load(profileDetailJson.getString(P.profile_pic)).placeholder(R.drawable.user).fit().into((ImageView) fragmentView.findViewById(R.id.imagesView));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (App.showName) {
            ((TextView) fragmentView.findViewById(R.id.name)).setText(profileDetailJson.getString(P.full_name));
            ((TextView) fragmentView.findViewById(R.id.email)).setText(profileDetailJson.getString(P.email));
            ((TextView) fragmentView.findViewById(R.id.phoneNum)).setText(profileDetailJson.getString(P.ph_number));
        }
        ((TextView) fragmentView.findViewById(R.id.profileId)).setText(profileDetailJson.getString(P.profile_id));
        ((TextView) fragmentView.findViewById(R.id.aboutTextView)).setText(profileDetailJson.getString(P.about_2));
        ((TextView) fragmentView.findViewById(R.id.days_tv)).setText(profileDetailJson.getString(P.days) + " Days Ago");
        ((TextView) fragmentView.findViewById(R.id.ageAndHeight)).setText(profileDetailJson.getString(P.age) + " Yrs, " + profileDetailJson.getString(P.height) + "''");
        ((TextView) fragmentView.findViewById(R.id.designation)).setText(profileDetailJson.getString(P.occupation_name));
        ((TextView) fragmentView.findViewById(R.id.religion_tv)).setText(profileDetailJson.getString(P.religion));
        ((TextView) fragmentView.findViewById(R.id.citynamecountryname_tv)).setText(profileDetailJson.getString(P.city_name) + "," + profileDetailJson.getString(P.country_name));
        ((TextView) fragmentView.findViewById(R.id.age_and_height_tv)).setText(profileDetailJson.getString(P.age) + " yrs," + profileDetailJson.getString(P.height) + "'");
        ((TextView) fragmentView.findViewById(R.id.marital_status_tv)).setText(profileDetailJson.getString(P.marital_status));
        ((TextView) fragmentView.findViewById(R.id.city_state_country_tv)).setText(profileDetailJson.getString(P.city_name) + "," + profileDetailJson.getString(P.state_name) + "," + profileDetailJson.getString(P.country_name));
        ((TextView) fragmentView.findViewById(R.id.designation_tv)).setText(profileDetailJson.getString(P.occupation_name));
        jsonList = profileDetailJson.getJsonList("images");
        ((TextView) fragmentView.findViewById(R.id.profile_pic_count_tv)).setText(String.valueOf(jsonList.size()));
        ((TextView) fragmentView.findViewById(R.id.background_tv)).setText(profileDetailJson.getString(P.religion));
        ((TextView) fragmentView.findViewById(R.id.hijab)).setText(profileDetailJson.getString(P.hijab_preference));
        ((TextView) fragmentView.findViewById(R.id.roza)).setText(profileDetailJson.getString(P.roza));
        ((TextView) fragmentView.findViewById(R.id.namaz)).setText(profileDetailJson.getString(P.namaz));

        if (App.showName) {
            ((TextView) fragmentView.findViewById(R.id.fatherName)).setText(profileDetailJson.getString(P.father_name));
            ((TextView) fragmentView.findViewById(R.id.motherName)).setText(profileDetailJson.getString(P.mother_name));
            ((TextView) fragmentView.findViewById(R.id.fatherOccupation)).setText(profileDetailJson.getString(P.father_occupation));
            ((TextView) fragmentView.findViewById(R.id.motherOccupation)).setText(profileDetailJson.getString(P.mother_occupation));
            ((TextView) fragmentView.findViewById(R.id.maritalStatus)).setText(profileDetailJson.getString(P.marital_status));
        }

        ((TextView) fragmentView.findViewById(R.id.occupation)).setText(profileDetailJson.getString(P.education));
        ((TextView) fragmentView.findViewById(R.id.carrerOccupation)).setText(profileDetailJson.getString(P.occupation_name));

        LinearLayout linearLayout = fragmentView.findViewById(R.id.noPhotoLinearLayout);
        String string = profileDetailJson.getString(P.photo_available);
        Button button = linearLayout.findViewById(R.id.button);

        if (string.equals("0")) {
            linearLayout.setVisibility(View.VISIBLE);
            button.setOnClickListener(this);
        } else if (string.equals("1")) {
            linearLayout.setVisibility(View.GONE);
            button.setOnClickListener(null);
        }

        string = profileDetailJson.getString(P.request_photo);
        if (string.equals("0"))
            button.setText("Request Photo");
        else if (string.equals("1"))
            button.setText("Photo already requested.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }

    private void hitRequestPhotoApi() {
        Json json = new Json();
        json.addString(P.profile_id, profileId);

        String string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("request_photo");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {
                        hitProfileDetailsApi("profile_details", profileId);
                    }
                })
                .run("hitRequestPhotoApi");
    }
}
