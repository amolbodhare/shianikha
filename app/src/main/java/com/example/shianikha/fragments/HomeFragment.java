package com.example.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.adapters.RecentlyJoinAdapter;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.example.shianikha.commen.P.recently_join;
import static com.example.shianikha.commen.P.recently_visitors;

public class HomeFragment extends Fragment {

    private ArrayList<String> recently_join_Names = new ArrayList<>();
    private ArrayList<String> recently_join_ImageUrls = new ArrayList<>();
    private View fragmentView;
    private Context context;
    private LoadingDialog loadingDialog;

    private OnFragmentInteractionListener mListener;

    public static HomeFragment newInstance()
    {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (fragmentView == null)
        {
            context = getContext();
            LoadingDialog loadingDialog = new LoadingDialog(context);

            fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
            CircularImageView circularImageView = fragmentView.findViewById(R.id.cir_imv);

            hitDashboardApi();

            ((HomeActivity)context).makeStatusBarColorBlue(true);
        }
        return fragmentView;
    }

    private void hitDashboardApi() {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id,string);
        RequestModel requestModel = RequestModel.newRequestModel("dashboard");
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
                            setProfileData(json);
                            setRecentlyJoinData(json);
                            setRecentVisitorsData(json);

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitDashoardApi");
    }

    private void setProfileData(Json parentJson) {
          Json  json = parentJson.getJson(P.profile_details);
        String string = json.getString(P.profile_pic);
          if(json!=null)
          {
            try
            {
                Picasso.get().load(string).into((CircularImageView)fragmentView.findViewById(R.id.cir_imv));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
          }
        string=json.getString(P.full_name);
          if(string!=null)
              ((TextView)fragmentView.findViewById(R.id.name_tv)).setText(string);
        string=json.getString(P.profile_id);
            if(json!=null)
            ((TextView)fragmentView.findViewById(R.id.profile_id_tv)).setText(string);
         string=parentJson.getString(P.my_matches);
            if(parentJson!=null)
                ((TextView)fragmentView.findViewById(R.id.myMatches)).setText(string);
         string=parentJson.getString(P.profile_visitors);
            if(parentJson!=null)
                ((TextView)fragmentView.findViewById(R.id.profileVisitor)).setText(string);
         string=parentJson.getString(P.express_interest);
            if(parentJson!=null)
                ((TextView)fragmentView.findViewById(R.id.expressInterest)).setText(string);

    }


    /*private void getRecentlyJoinList() {
        //Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        ////////////
        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        /////////

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.rec_join_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), recently_join_Names, recently_join_ImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }*/

  /*  private void getRecentlyVisitedList() {
       // Log.d(TAG, "getSpecialOfferList: preparing bitmaps.");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        ////////////
        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        /////////

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");

        recently_join_ImageUrls.add("https://ibb.co/3YVQxfY");
        recently_join_Names.add("Kangna Ranout");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recent_visitors_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), );
        recyclerView.setAdapter(adapter);
    }*/

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setRecentlyJoinData(Json json)
    {
        JsonList jsonList=json.getJsonList(recently_join);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.rec_join_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), jsonList);
        recyclerView.setAdapter(adapter);

        /*recyclerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicked",
                 Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    private void setRecentVisitorsData(Json json)
    {
        JsonList jsonList=json.getJsonList(recently_visitors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recent_visitors_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(getActivity(), jsonList);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
