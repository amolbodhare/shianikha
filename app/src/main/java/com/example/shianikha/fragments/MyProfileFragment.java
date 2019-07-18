package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class MyProfileFragment extends Fragment {

    View fragmentView;
    public static Fragment previousFragment;
    public static String previousFragmentName;
    Context context;

    private OnFragmentInteractionListener mListener;


    public static MyProfileFragment newInstance(Fragment fragment, String string) {
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return myProfileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentView == null) {
            context=getContext();
            fragmentView = inflater.inflate(R.layout.fragment_my_profile, container, false);
            hitMyProfileApi();

        }
        return fragmentView;
    }

    private void hitMyProfileApi() {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id,string);
        RequestModel requestModel = RequestModel.newRequestModel("my_profile");
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
                            setMyProfileData(json);
                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("my_profile");
    }

    private void setMyProfileData(Json json) {
        json=json.getJson(P.data);
        String string = json.getString(P.profile_pic);


            try
            {
                Picasso.get().load(string).into((ImageView) fragmentView.findViewById(R.id.image_profile_pic));
                H.log("imageisLoaded","hello");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        string=json.getString(P.full_name);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.name_tv)).setText(string);
        string=json.getString(P.profile_id);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.profile_id_tv)).setText(string);
        string=json.getString(P.packages_applied);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.account_type_tv)).setText(string);
        string=json.getString(P.email);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.eMail)).setText(string);
        string=json.getString(P.ph_number);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.phNumber)).setText(string);
        string=json.getString(P.gender);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.gender)).setText(string);
        string=json.getString(P.dob);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.dob)).setText(string);
        string=json.getString(P.height);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.height)).setText(string);
        string=json.getString(P.city_name);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.cityName)).setText(string);
        string=json.getString(P.state_name);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.stateName)).setText(string);
        string=json.getString(P.country_name);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.Country)).setText(string);
        string=json.getString(P.religion);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.religion)).setText(string);
        string=json.getString(P.ethnicity_name);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.ethinicity)).setText(string);
        string=json.getString(P.father_city);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.fatherCity)).setText(string);
        string=json.getString(P.mother_city);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.motherCity)).setText(string);
        string=json.getString(P.occupation_name);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.occupation)).setText(string);
        string=json.getString(P.about_occupation);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.aboutOccupation)).setText(string);
        string=json.getString(P.education);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.education)).setText(string);
        string=json.getString(P.language);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.language)).setText(string);
        string=json.getString(P.smoke_id);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.smoke)).setText(string);
        string=json.getString(P.relocate_id);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.reLocate)).setText(string);
        string=json.getString(P.cvt_islam);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.convert)).setText(string);
        string=json.getString(P.about_1);
        if(string!=null)
            ((TextView)fragmentView.findViewById(R.id.aboutOne)).setText(string);


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
