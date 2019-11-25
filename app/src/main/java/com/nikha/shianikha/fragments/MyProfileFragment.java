package com.nikha.shianikha.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

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

    public void hitMyProfileApi() {
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

        String gender = json.getString(P.gender);
        gender = gender.toLowerCase();
        Drawable drawable = gender.equals("male") ? context.getDrawable(R.drawable.male) : context.getDrawable(R.drawable.female);

        String string = json.getString(P.profile_pic);
        try {
            Picasso.get().load(string).placeholder(drawable).fit().into((ImageView) fragmentView.findViewById(R.id.image_profile_pic));
            H.log("imageisLoaded", "hello");
        } catch (Exception e) {
            e.printStackTrace();
        }

        string = json.getString(P.first_name) + " " + json.getString(P.middle_name) + " " + json.getString(P.last_name);
        ((TextView) fragmentView.findViewById(R.id.name_tv)).setText(string);

        string = json.getString(P.first_name);
        ((TextView) fragmentView.findViewById(R.id.firstName)).setText(string);

        string = json.getString(P.middle_name);
        ((TextView) fragmentView.findViewById(R.id.middleName)).setText(string);

        string = json.getString(P.last_name);
        ((TextView) fragmentView.findViewById(R.id.lastName)).setText(string);

        string = json.getString(P.skin_tone);
        ((TextView) fragmentView.findViewById(R.id.skinTone)).setText(string);

        string = json.getString(P.body_type);
        ((TextView) fragmentView.findViewById(R.id.bodyType)).setText(string);

        string = json.getString(P.religion);
        ((TextView) fragmentView.findViewById(R.id.shiaCommunity)).setText(string);

        string = json.getString(P.marital_status);
        ((TextView) fragmentView.findViewById(R.id.maritualStatus)).setText(string);

        TextView textView = fragmentView.findViewById(R.id.education);
        string = json.getString(P.educationlevel);
        textView.setText(string);

        if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others")) {
            string = json.getString(P.other_edulevel);
            if (!string.isEmpty())
                textView.append("(" + string + ")");
        }

        textView = fragmentView.findViewById(R.id.occupation);
        string = json.getString(P.occupation_name);
        textView.setText(string);

        if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others")) {
            string = json.getString(P.other_occupation);
            if (!string.isEmpty())
                textView.append("(" + string + ")");
        }


        string = json.getString(P.monthly_income);
        ((TextView) fragmentView.findViewById(R.id.monthlyIncome)).setText(string);

        string = json.getString(P.residency_address);
        ((TextView) fragmentView.findViewById(R.id.resAddress)).setText(string);

        string = json.getString(P.mothertongue);
        textView = fragmentView.findViewById(R.id.motherTongue);
        textView.setText(string);

        if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others")) {
            string = json.getString(P.other_mother_tongue);
            if (!string.isEmpty())
                textView.append("(" + string + ")");
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray = json.getJsonArray(P.language_id);
        textView = fragmentView.findViewById(R.id.language);
        StringBuilder stringBuilder = new StringBuilder();
        int j = 0;
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    j = CommonListHolder.languageIdList.indexOf(jsonArray.getString(i));
                    if (j != -1)
                        stringBuilder.append(CommonListHolder.languageNameList.get(j)).append(", ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            j = stringBuilder.lastIndexOf(",");
            if (j != -1)
                stringBuilder.deleteCharAt(j);
            textView.setText(stringBuilder);
        }

        string = json.getString(P.other_language);
        if (!string.isEmpty())
            textView.append("(" + string + ")");

        jsonArray = json.getJsonArray(P.intreasted_in);
        stringBuilder = new StringBuilder();
        textView = fragmentView.findViewById(R.id.interestedInTextView);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    j = CommonListHolder.intrestedInIdList.indexOf(jsonArray.getString(i));
                    if (j != -1)
                        stringBuilder.append(CommonListHolder.intrestedInNameList.get(j)).append(", ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            j = stringBuilder.lastIndexOf(",");
            if (j != -1)
                stringBuilder.deleteCharAt(j);
            textView.setText(stringBuilder);
        }

        string = json.getString(P.other_intreasted);
        if (!string.isEmpty())
            textView.append("(" + string + ")");

        textView = fragmentView.findViewById(R.id.fatherOccupation);
        string = json.getString(P.father_occupation);
        textView.setText(string);

        if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others")) {
            string = json.getString(P.father_other_occupation);
            if (!string.isEmpty())
                textView.append("(" + string + ")");
        }

        textView = fragmentView.findViewById(R.id.motherOccupation);
        string = json.getString(P.mother_occupation);
        textView.setText(string);

        if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others")) {
            string = json.getString(P.mother_other_occupation);
            if (!string.isEmpty())
                textView.append("(" + string + ")");
        }

        textView = fragmentView.findViewById(R.id.ethincity);
        string = json.getString(P.ethnicity_name);
        textView.setText(string);

        if (string.equalsIgnoreCase("other") || string.equalsIgnoreCase("others")) {
            string = json.getString(P.other_ethnicity);
            if (!string.isEmpty())
                textView.append("(" + string + ")");
        }

        /*string = json.getString(P.ethnicity_name);
        if (string.isEmpty() || string.equalsIgnoreCase("null")) {
            string = json.getString(P.other_ethnicity);
            ((TextView) fragmentView.findViewById(R.id.ethincity)).setText(string);
        } else {
            ((TextView) fragmentView.findViewById(R.id.ethincity)).setText(string);
        }*/

        string = json.getString(P.father_name);
        ((TextView) fragmentView.findViewById(R.id.fatherName)).setText(string);

        string = json.getString(P.mother_name);
        ((TextView) fragmentView.findViewById(R.id.motherName)).setText(string);

        string = json.getString(P.father_country);
        ((TextView) fragmentView.findViewById(R.id.fatherCountry)).setText(string);

        string = json.getString(P.mother_country);
        ((TextView) fragmentView.findViewById(R.id.mothersCountry)).setText(string);

        string = json.getString(P.profile_id);
        ((TextView) fragmentView.findViewById(R.id.profile_id_tv)).setText(string);

        if (App.showName)
            ((TextView) fragmentView.findViewById(R.id.account_type_tv)).setText(": Paid");
        else
            ((TextView) fragmentView.findViewById(R.id.account_type_tv)).setText(": Free");

        string = json.getString(P.email);
        ((TextView) fragmentView.findViewById(R.id.eMail)).setText(string);

        string = json.getString(P.ph_number);
        ((TextView) fragmentView.findViewById(R.id.phNumber)).setText(string);

        string = json.getString(P.gender);
        ((TextView) fragmentView.findViewById(R.id.gender)).setText(string);

        string = json.getString(P.height);
        ((TextView) fragmentView.findViewById(R.id.height)).setText(string);

        string = json.getString(P.city_name);
        ((TextView) fragmentView.findViewById(R.id.cityName)).setText(string);

        string = json.getString(P.state_name);
        ((TextView) fragmentView.findViewById(R.id.stateName)).setText(string);

        string = json.getString(P.country_name);
        ((TextView) fragmentView.findViewById(R.id.Country)).setText(string);

        //string = json.getString(P.religion);

        string = json.getString(P.smoke_id);
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
