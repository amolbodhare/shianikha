package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    public static String profile_details_string;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


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
            context = getContext();
            fragmentView = inflater.inflate(R.layout.fragment_my_profile, container, false);
            hitMyProfileApi();

        }
        return fragmentView;
    }

    private void hitMyProfileApi() {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id, string);
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
                        H.showMessage(context, "Something went Wrong");
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
        json = json.getJson(P.data);
        profile_details_string = json.toString();
        String string = json.getString(P.profile_pic);


        try {
            Picasso.get().load(string).into((ImageView) fragmentView.findViewById(R.id.image_profile_pic));
            H.log("imageisLoaded", "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }

        string=json.getString(P.first_name) + " " +json.getString(P.middle_name)+" "+json.getString(P.last_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.name_tv)).setText(string);

        string = json.getString(P.first_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.firstName)).setText(string);
        string = json.getString(P.middle_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.middleName)).setText(string);
        string = json.getString(P.last_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.lastName)).setText(string);
        string = json.getString(P.skin_tone);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.skinTone)).setText(string);
        string = json.getString(P.body_type);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.bodyType)).setText(string);
        string = json.getString(P.religion);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.shiaCommunity)).setText(string);
        string = json.getString(P.marital_status);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.maritualStatus)).setText(string);

        string = json.getString(P.educationlevel);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.other_edulevel);
            ((TextView) fragmentView.findViewById(R.id.education)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.education)).setText(string);
        }
        string = json.getString(P.occupation_name);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.other_occupation);
            ((TextView) fragmentView.findViewById(R.id.language)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.language)).setText(string);
        }

        string = json.getString(P.monthly_income);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.monthlyIncome)).setText(string);

        string = json.getString(P.residency_address);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.resAddress)).setText(string);


        string = json.getString(P.mothertongue);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.other_mother_tongue);
            ((TextView) fragmentView.findViewById(R.id.motherTongue)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.motherTongue)).setText(string);
        }

        string = json.getString(P.language);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.other_language);
            ((TextView) fragmentView.findViewById(R.id.language)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.language)).setText(string);
        }

        string = json.getString(P.father_occupation);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.father_other_occupation);
            ((TextView) fragmentView.findViewById(R.id.fatherOccupation)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.fatherOccupation)).setText(string);
        }


        string = json.getString(P.mother_occupation);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.mother_other_occupation);
            ((TextView) fragmentView.findViewById(R.id.motherOccupation)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.motherOccupation)).setText(string);
        }

        string = json.getString(P.ethnicity_name);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.other_ethnicity);
            ((TextView) fragmentView.findViewById(R.id.ethincity)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.ethincity)).setText(string);
        }

        string = json.getString(P.father_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.fatherName)).setText(string);

        string = json.getString(P.mother_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.motherName)).setText(string);


        string = json.getString(P.father_country);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.fatherCountry)).setText(string);

        string = json.getString(P.mother_country);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.mothersCountry)).setText(string);

        string = json.getString(P.profile_id);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.profile_id_tv)).setText(string);
        string = json.getString(P.packages_applied);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.account_type_tv)).setText(string);
        string = json.getString(P.email);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.eMail)).setText(string);
        string = json.getString(P.ph_number);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.phNumber)).setText(string);
        string = json.getString(P.gender);

        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.gender)).setText(string);
        string = json.getString(P.height);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.height)).setText(string);
        string = json.getString(P.city_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.cityName)).setText(string);
        string = json.getString(P.state_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.stateName)).setText(string);
        string = json.getString(P.country_name);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.Country)).setText(string);
        string = json.getString(P.religion);

        string = json.getString(P.smoke_id);
        if (string != null)
            ((TextView) fragmentView.findViewById(R.id.smoke)).setText(string);


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}
