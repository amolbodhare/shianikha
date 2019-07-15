package com.example.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.bumptech.glide.Glide;
import com.example.shianikha.R;
import com.example.shianikha.activities.ImageViewerActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;


public class ProfileDetailsFragments extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private Context context;
    private View fragmentView;
    public static Fragment previousFragment;
    public static String previousFragmentName;
    private static Json profileDetailJson;


    public static ProfileDetailsFragments newInstance(Fragment fragment, String string, Json json) {
        ProfileDetailsFragments profileDetailsFragments = new ProfileDetailsFragments();
        previousFragment = fragment;
        previousFragmentName = string;
        profileDetailJson=json;

        return profileDetailsFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getContext();
        if (fragmentView == null)
        {

            fragmentView = inflater.inflate(R.layout.fragment_profile_details, container, false);

            Glide.with(context)
                    .asBitmap()
                    .load(profileDetailJson.getString(P.profile_pic))
                    //.load(R.drawable.kangna)
                    .into((ImageView) fragmentView.findViewById(R.id.imagesView));

            ((TextView)fragmentView.findViewById(R.id.name)).setText(profileDetailJson.getString(P.full_name));
            ((TextView)fragmentView.findViewById(R.id.profileId)).setText(profileDetailJson.getString(P.profile_id));
            ((TextView)fragmentView.findViewById(R.id.ageAndHeight)).setText(profileDetailJson.getString(P.age)+" "+profileDetailJson.getString(P.height));
            ((TextView)fragmentView.findViewById(R.id.whichMuslim)).setText(profileDetailJson.getString(P.religion));
            ((TextView)fragmentView.findViewById(R.id.designation)).setText(profileDetailJson.getString(P.occupation));
            ((TextView)fragmentView.findViewById(R.id.area)).setText(profileDetailJson.getString(P.city_name)+","+profileDetailJson.getString(P.state_name));
            ((TextView)fragmentView.findViewById(R.id.phoneNum)).setText(profileDetailJson.getString(P.ph_number));
            ((TextView)fragmentView.findViewById(R.id.email)).setText(profileDetailJson.getString(P.email));
            ((TextView)fragmentView.findViewById(R.id.age_and_height_tv)).setText(profileDetailJson.getString(P.age)+","+profileDetailJson.getString(P.height)+"'");
            ((TextView)fragmentView.findViewById(R.id.designation_tv)).setText(profileDetailJson.getString(P.occupation));
            ((TextView)fragmentView.findViewById(R.id.religion)).setText(profileDetailJson.getString(P.religion));

            fragmentView.findViewById(R.id.imagesView).setOnClickListener(this);
            fragmentView.findViewById(R.id.imageViewer1).setOnClickListener(this);

        }
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imagesView || v.getId() == R.id.imageViewer1) {
            /*Intent intent = new Intent(context, ImageViewerActivity.class);
            startActivity(intent);*/
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void hitmatchesApi()
    {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id,string);
        RequestModel requestModel = RequestModel.newRequestModel("");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        /*if (isLoading)s
                            loadingDialog.show();
                        else
                            loadingDialog.dismiss();*/
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context,"Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {

                            //setProfileData(json);
                            //setRecentlyJoinData(json);
                            //setRecentVisitorsData(json);

                        }

                        else

                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitDashoardApi");
    }
}
