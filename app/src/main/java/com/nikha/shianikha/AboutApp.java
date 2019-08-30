package com.nikha.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;


public class AboutApp extends Fragment {


    public static Fragment previousFragment;
    public static String previousFragmentName;
    private Context context;
    private LoadingDialog loadingDialog;
    private View fragmentView;
    

    private OnFragmentInteractionListener mListener;

    public AboutApp() {
        // Required empty public constructor
    }


    public static AboutApp newInstance(Fragment fragment, String string) {
        AboutApp aboutAppFragment = new AboutApp();
        previousFragment = fragment;
        previousFragmentName = string;

        return aboutAppFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(fragmentView==null)
        {
            context =getContext();
            LoadingDialog loadingDialog = new LoadingDialog(context);
            fragmentView=inflater.inflate(R.layout.fragment_about_app, container, false);
            hitAboutUs();
        }
        return fragmentView;
    }

    private void hitAboutUs() {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id, string);
        RequestModel requestModel = RequestModel.newRequestModel("about");
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


                            String  string = json.getString(P.data);
                            ((TextView) fragmentView.findViewById(R.id.textView)).setText(string);


                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitAboutUs");
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
