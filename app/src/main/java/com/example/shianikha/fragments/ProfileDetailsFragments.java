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
import android.widget.ListView;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.bumptech.glide.Glide;
import com.example.shianikha.R;
import com.example.shianikha.activities.ImageViewPagerActivity;
import com.example.shianikha.activities.ImageViewerActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;

import java.util.ArrayList;


public class ProfileDetailsFragments extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private Context context;
    private View fragmentView;
    public static Fragment previousFragment;
    public static String previousFragmentName;
    public static String profileId;
    LoadingDialog loadingDialog;
    JsonList jsonList;



    public static ProfileDetailsFragments newInstance(Fragment fragment, String prev_frag_name,String profile_id) {
        ProfileDetailsFragments profileDetailsFragments = new ProfileDetailsFragments();
        previousFragment = fragment;
        previousFragmentName = prev_frag_name;
        profileId=profile_id;

        return profileDetailsFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        context=getContext();
        loadingDialog=new LoadingDialog(context);


        if (fragmentView == null)
        {


            fragmentView = inflater.inflate(R.layout.fragment_profile_details, container, false);

            ((ImageView)fragmentView.findViewById(R.id.images_pager_click)).setOnClickListener(this);

            fragmentView.findViewById(R.id.imagesView).setOnClickListener(this);
            fragmentView.findViewById(R.id.imageViewer1).setOnClickListener(this);

        }

        // here in profileId we have got user_id only but according to api its name is changed as profileId for this api
        hitProfileDetailsApi("profile_details",profileId);
        return fragmentView;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.imagesView || v.getId() == R.id.imageViewer1)
        {
            /*Intent intent = new Intent(context, ImageViewerActivity.class);
            startActivity(intent);*/
        }
        else if(v.getId()==R.id.images_pager_click)
        {
            Intent intent = new Intent(context, ImageViewPagerActivity.class);
            intent.putExtra("ImageList",jsonList.toString());
            startActivity(intent);

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void hitProfileDetailsApi(String api_type,String profileId)
    {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id,string);
        json.addString(P.profile_id,profileId);
        RequestModel requestModel = RequestModel.newRequestModel(api_type);
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
                        H.showMessage(context,"Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json profileDetailJson) {

                        if (profileDetailJson.getInt(P.status) == 1)
                        {
                            profileDetailJson = profileDetailJson.getJson(P.data);


                            Glide.with(context)
                                    .asBitmap()
                                    .load(profileDetailJson.getString(P.profile_pic))
                                    //.load(R.drawable.kangna)
                                    .into((ImageView) fragmentView.findViewById(R.id.imagesView));

                            ((TextView)fragmentView.findViewById(R.id.name)).setText(profileDetailJson.getString(P.full_name));
                            ((TextView)fragmentView.findViewById(R.id.profileId)).setText(profileDetailJson.getString(P.profile_id));
                            ((TextView)fragmentView.findViewById(R.id.days_tv)).setText(profileDetailJson.getString(P.days)+" Days Ago");
                            ((TextView)fragmentView.findViewById(R.id.ageAndHeight)).setText(profileDetailJson.getString(P.age)+" Yrs, "+profileDetailJson.getString(P.height)+"''");
                            ((TextView)fragmentView.findViewById(R.id.designation)).setText(profileDetailJson.getString(P.occupation_name));
                            ((TextView)fragmentView.findViewById(R.id.religion_tv)).setText(profileDetailJson.getString(P.religion));
                            ((TextView)fragmentView.findViewById(R.id.citynamecountryname_tv)).setText(profileDetailJson.getString(P.city_name)+","+profileDetailJson.getString(P.country_name));
                            ((TextView)fragmentView.findViewById(R.id.phoneNum)).setText(profileDetailJson.getString(P.ph_number));
                            ((TextView)fragmentView.findViewById(R.id.email)).setText(profileDetailJson.getString(P.email));
                            ((TextView)fragmentView.findViewById(R.id.age_and_height_tv)).setText(profileDetailJson.getString(P.age)+" yrs,"+profileDetailJson.getString(P.height)+"'");
                            ((TextView)fragmentView.findViewById(R.id.marital_status_tv)).setText(profileDetailJson.getString(P.marital_status));
                            ((TextView)fragmentView.findViewById(R.id.city_state_country_tv)).setText(profileDetailJson.getString(P.city_name)+","+profileDetailJson.getString(P.state_name)+","+profileDetailJson.getString(P.country_name));
                            ((TextView)fragmentView.findViewById(R.id.designation_tv)).setText(profileDetailJson.getString(P.occupation_name));
                             jsonList=profileDetailJson.getJsonList("images");
                            ((TextView)fragmentView.findViewById(R.id.profile_pic_count_tv)).setText(String.valueOf(jsonList.size()));
                            ((TextView)fragmentView.findViewById(R.id.background_tv)).setText("Religion : "+profileDetailJson.getString(P.religion));
                            ((TextView)fragmentView.findViewById(R.id.education_and_career_tv)).setText("Education : "+profileDetailJson.getString(P.education)+"\n"+"Education : "+profileDetailJson.getString(P.occupation_name));
                            //((TextView)fragmentView.findViewById(R.id.profile_pic_count_tv)).setText(jsonList.size());

                        }

                        else

                            H.showMessage(context, profileDetailJson.getString(P.msg));
                    }
                })
                .run("hitDashoardApi");
    }
}
