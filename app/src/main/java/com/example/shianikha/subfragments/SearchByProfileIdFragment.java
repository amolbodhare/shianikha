package com.example.shianikha.subfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.example.shianikha.fragments.FavouritesFragment;


public class SearchByProfileIdFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View fragmentView;
    private Context context;

    public SearchByProfileIdFragment() {
        // Required empty public constructor
    }

    public static SearchByProfileIdFragment newInstance() {
        SearchByProfileIdFragment fragment = new SearchByProfileIdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        if (fragmentView == null)
        {
            fragmentView = inflater.inflate(R.layout.fragment_search_by_profile_id, container, false);
            fragmentView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Json json = new Json();

                    json.addString(P.token_id,new Session(context).getString(P.tokenData));
                    json.addString(P.search_by_id,"1");

                    String string = ((EditText)fragmentView.findViewById(R.id.editText)).getText().toString();
                    if (string.isEmpty())
                    {
                        H.showMessage(context,"Please enter profile Id");
                        return;
                    }

                    json.addString(P.user_profile_id,string);

                    hitSearchApi(json);
                }
            });

        }
        return fragmentView;
    }

    private void hitSearchApi(Json json)
    {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("search");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener()
                {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait...");
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
                    public void onSuccess(Json json)
                    {
                        if (json.getInt(P.status) == 1)
                        {
                            ((HomeActivity) context).favouritesFragment = FavouritesFragment.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName);

                            Bundle bundle = new Bundle();
                            bundle.putString(P.json,json.toString());
                            ((HomeActivity)context).favouritesFragment.setArguments(bundle);

                            ((HomeActivity) context).fragmentLoader(((HomeActivity) context).favouritesFragment, "Search result");
                        }
                    }
                })
                .run("hitSearchApi");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
