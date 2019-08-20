package com.example.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.activities.EditProfileActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;


public class AccountSettingsFragment extends Fragment implements View.OnClickListener {

    View fragmentView;
    Context context;
    RelativeLayout email_address_link_layout;
    RelativeLayout change_password_link_layout;
    RelativeLayout who_can_contact_link_layout;
    RelativeLayout profile_visibility_link_layout;
    RelativeLayout who_can_message_link_layout;

    ExpandableRelativeLayout email_address_exp_layout;
    ExpandableRelativeLayout change_password_exp_layout;
    ExpandableRelativeLayout who_can_contact_exp_layout;
    ExpandableRelativeLayout profile_visibility_exp_layout;
    ExpandableRelativeLayout who_can_message_exp_layout;
    private Session session;

    /*TextView edit_password_tv_btn;
    EditText change_password_edt;*/
    /* LoadingDialog loadingDialog;
     ListAdapter listAdapter;*/

    public static Fragment previousFragment;
    public static String previousFragmentName;

    Switch simpleSwitch;


    private OnFragmentInteractionListener mListener;

    public AccountSettingsFragment() { }


    // TODO: Rename and change types and number of parameters

    public static AccountSettingsFragment newInstance(Fragment fragment, String string) {
        AccountSettingsFragment accountSettingsFragment = new AccountSettingsFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return accountSettingsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        session = new Session(getActivity());
        //loadingDialog = new LoadingDialog(context);
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_account_settings, null, false);

            /*edit_password_tv_btn = fragmentView.findViewById(R.id.edit_password_tv_btn);
            change_password_edt = fragmentView.findViewById(R.id.change_password_edt);*/

            email_address_link_layout = fragmentView.findViewById(R.id.email_address_link_layout);
            //change_password_link_layout = fragmentView.findViewById(R.id.change_password_link_layout);
            who_can_contact_link_layout = fragmentView.findViewById(R.id.who_can_contact_link_layout);
            profile_visibility_link_layout = fragmentView.findViewById(R.id.profile_photo_visibility_layout);
            who_can_message_link_layout = fragmentView.findViewById(R.id.who_can_message_link_layout);

            email_address_exp_layout = fragmentView.findViewById(R.id.email_address_exp_layout);
            //change_password_exp_layout = fragmentView.findViewById(R.id.change_password_exp_layout);
            who_can_contact_exp_layout = fragmentView.findViewById(R.id.who_can_contact_exp_layout);
            profile_visibility_exp_layout = fragmentView.findViewById(R.id.profile_photo_visibility_exp_layout);
            who_can_message_exp_layout = fragmentView.findViewById(R.id.who_can_message_exp_layout);


            simpleSwitch =  fragmentView.findViewById(R.id.simpleSwitch);
            fragmentView.findViewById(R.id.edit_profile_link_layout).setOnClickListener(this);


            email_address_link_layout.setOnClickListener(this);
//            change_password_link_layout.setOnClickListener(this);
            who_can_contact_link_layout.setOnClickListener(this);
            profile_visibility_link_layout.setOnClickListener(this);
            who_can_message_link_layout.setOnClickListener(this);

            email_address_exp_layout.setOnClickListener(this);
            //change_password_exp_layout.setOnClickListener(this);
            who_can_contact_exp_layout.setOnClickListener(this);
            profile_visibility_exp_layout.setOnClickListener(this);
            who_can_message_exp_layout.setOnClickListener(this);

            fragmentView.findViewById(R.id.saveButon).setOnClickListener(this);


            email_address_exp_layout.collapse();
            //change_password_exp_layout.collapse();
            who_can_contact_exp_layout.collapse();
            profile_visibility_exp_layout.collapse();
            who_can_message_exp_layout.collapse();

            String string = new Session(context).getString(P.email);
            ((TextView)fragmentView.findViewById(R.id.email_address_exp_link_tv)).setText(string);


           /* change_password_edt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        //this is for backspace
                    }
                    return false;
                }
            });*/

        }
        return fragmentView;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.email_address_link_layout)
            email_address_exp_layout.toggle();

        else if (v.getId() == R.id.who_can_contact_link_layout)
            who_can_contact_exp_layout.toggle();

        else if (v.getId() == R.id.profile_photo_visibility_layout)
            profile_visibility_exp_layout.toggle();

        else if (v.getId() == R.id.who_can_message_link_layout)
            who_can_message_exp_layout.toggle();

        else if (v.getId() == R.id.edit_profile_link_layout)
            startActivity(new Intent(getActivity(), EditProfileActivity.class));

        else if (v.getId() == R.id.saveButon)
            makeJson();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void makeJson() {

        Json json = new Json();
        json.addString(P.token_id, session.getString(P.tokenData));


        JSONArray jsonArray = new JSONArray();
        WhoCanContactJsonArray(jsonArray);

        if (jsonArray.length() < 1) {
            H.showMessage(context, "Please select contact option");
            return;
        }
        json.addJSONArray(P.who_can_contact, jsonArray);


        jsonArray = new JSONArray();
        profileVisibilityJsonArray(jsonArray);
        if (jsonArray.length() < 1) {
            H.showMessage(context, "Please select profile visibility");
            return;
        }
        json.addJSONArray(P.profile_with_visibility, jsonArray);


        jsonArray = new JSONArray();
        whoCanMessageJsonArray(jsonArray);
        if (jsonArray.length() < 1) {
            H.showMessage(context, "Please select who can message you");
            return;
        }
        json.addJSONArray(P.who_can_message, jsonArray);


        // check current state of a Switch (true or false).
        boolean switchState = simpleSwitch.isChecked();

        if (switchState) {
            json.addString(P.notification, "1");
        } else {
            json.addString(P.notification, "0");
        }


        hitAccountSettingApi(json);

    }

    private void WhoCanContactJsonArray(JSONArray jsonArray) {

        CheckBox checkBox;
        String string = "";
        LinearLayout who_can_contact_ll_view =  fragmentView.findViewById(R.id.who_can_contact_ll);
        Object object;
        for (int i = 0; i < who_can_contact_ll_view.getChildCount(); i++) {

            checkBox = (CheckBox) who_can_contact_ll_view.getChildAt(i);

            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }
    }

    private void profileVisibilityJsonArray(JSONArray jsonArray) {

        CheckBox checkBox;
        String string = "";
        LinearLayout profile_photo_visibility_ll_view =  fragmentView.findViewById(R.id.profile_photo_visibility_ll);
        Object object;
        for (int i = 0; i < profile_photo_visibility_ll_view.getChildCount(); i++) {

            checkBox = (CheckBox) profile_photo_visibility_ll_view.getChildAt(i);

            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }
    }

    private void whoCanMessageJsonArray(JSONArray jsonArray) {

        CheckBox checkBox;
        String string = "";
        LinearLayout who_can_message_ll_view =  fragmentView.findViewById(R.id.who_can_message_ll);
        Object object;
        for (int i = 0; i < who_can_message_ll_view.getChildCount(); i++) {

            checkBox = (CheckBox) who_can_message_ll_view.getChildAt(i);

            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }
    }

    private void hitAccountSettingApi(Json json) {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("account_setting");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait submitting your data...");
                        else
                            loadingDialog.dismiss();
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

                        if (json.getInt(P.status) == 1) {

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitAccountSettingApi");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}
