package com.nikha.shianikha.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import static com.nikha.shianikha.commen.P.recently_join;
import static com.nikha.shianikha.commen.P.recently_visitors;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;
    private Context context;
    private LoadingDialog loadingDialog;

    private OnFragmentInteractionListener mListener;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            context = getContext();

            fragmentView = inflater.inflate(R.layout.fragment_home, container, false);

            hitDashboardApi();

            ((HomeActivity) context).makeStatusBarColorBlue(true);

            fragmentView.findViewById(R.id.partner_preferences_link_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.subscription_plan_link_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.my_activity_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.account_settings_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.notifications_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.help_suport_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.about_app_layout).setOnClickListener(this);
            fragmentView.findViewById(R.id.contact_us_layout).setOnClickListener(this);

            final SwipeRefreshLayout swipeRefreshLayout = fragmentView.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                    hitDashboardApi();
                }
            });

        }
        return fragmentView;
    }
    public void hitDashboardApi() {
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        Json json = new Json();
        json.addString(P.token_id, string);
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
                        H.showMessage(context, "Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            setProfileData(json);
                            //if (App.i == 0) {//to avoid crash
                            setRecentlyJoinData(json);
                            setRecentVisitorsData(json);
                            //App.i = 1;
                            // }
                            ((HomeActivity) context).setDrawerData(json);

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitDashoardApi");
    }

    private void setProfileData(Json parentJson) {
        Json json = parentJson.getJson(P.profile_details);

        String gender = json.getString(P.gender);
        gender = gender.toLowerCase();
        Drawable drawable = gender.equals("male") ? context.getDrawable(R.drawable.male) : context.getDrawable(R.drawable.female);

        String string = json.getString(P.profile_pic);
        try {
            Picasso.get().load(string).placeholder(drawable).fit().into((CircularImageView) fragmentView.findViewById(R.id.cir_imv));
        } catch (Exception e) {
            e.printStackTrace();
        }

        string = json.getString(P.full_name);
        ((TextView) fragmentView.findViewById(R.id.name_tv)).setText(string);

        string = json.getString(P.profile_id);
        ((TextView) fragmentView.findViewById(R.id.profile_id_tv)).setText(string);

        string = parentJson.getString(P.my_matches);
        ((TextView) fragmentView.findViewById(R.id.myMatches)).setText(string);

        string = parentJson.getString(P.profile_visitors);
        ((TextView) fragmentView.findViewById(R.id.profileVisitor)).setText(string);

        string = parentJson.getString(P.express_interest);
        ((TextView) fragmentView.findViewById(R.id.expressInterest)).setText(string);

        int i = json.getInt(P.notifacation_count);
        ((HomeActivity) context).updateNotificationCount(i);

        if (App.showName)
            ((TextView) fragmentView.findViewById(R.id.account_type_tv)).setText(": Paid");
        else
            ((TextView) fragmentView.findViewById(R.id.account_type_tv)).setText(": Free");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        if (i == R.id.partner_preferences_link_layout)
            ((HomeActivity) context).showPartnerPreferenceActivity();
        else if (i == R.id.subscription_plan_link_layout)
            ((HomeActivity) context).showSubscriptionPlanActivity();
        else if (i == R.id.my_activity_layout)
            ((HomeActivity) context).showMyActivityFragment();
        else if (i == R.id.account_settings_layout)
            ((HomeActivity) context).showAccountSettingFragment();
        else if (i == R.id.notifications_layout)
            ((HomeActivity) context).showNotificationFragment();
        else if (i == R.id.help_suport_layout)
            ((HomeActivity) context).showHelpAndSupportFrgment();
        else if (i == R.id.about_app_layout)
            ((HomeActivity) context).showAboutAppFragment();
        else if (i == R.id.contact_us_layout)
            ((HomeActivity) context).showContactUsFragment();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setRecentlyJoinData(Json json) {
        JsonList jsonList = json.getJsonList(recently_join);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.rec_join_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(jsonList);
        recyclerView.setAdapter(adapter);
    }

    private void setRecentVisitorsData(Json json) {
        JsonList jsonList = json.getJsonList(recently_visitors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recent_visitors_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecentlyJoinAdapter adapter = new RecentlyJoinAdapter(jsonList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }

    class RecentlyJoinAdapter extends RecyclerView.Adapter<RecentlyJoinAdapter.ViewHolder> {
        private static final String TAG = "RecentlyJoinAdapter";

        JsonList jsonList;

        public RecentlyJoinAdapter(JsonList jsonList) {
            this.jsonList = jsonList;
        }

        @NonNull
        @Override
        public RecentlyJoinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_join, parent, false);
            return new RecentlyJoinAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecentlyJoinAdapter.ViewHolder viewHolder, final int position) {
            Log.d(TAG, "onBindViewHolder: called.");


            Picasso.get().load(jsonList.get(position).getString(P.profile_pic)).placeholder(R.drawable.user).fit().into(viewHolder.thumbnail);

            if (App.showName)
                viewHolder.title.setText(jsonList.get(position).getString(P.full_name));
            else
                viewHolder.title.setText("");

            viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we are passing last parameter user_id but in next profile details api it would be considered as profile_id
                    ((HomeActivity) context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName, jsonList.get(position).getString(P.user_id));
                    ((HomeActivity) context).fragmentLoader(((HomeActivity) context).profileDetailsFragments, P.profile_details_title);
                }
            });
        }


        @Override
        public int getItemCount() {
            return jsonList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;
            public ImageView thumbnail, overflow;

            public ViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                //overflow = (ImageView) view.findViewById(R.id.overflow);
            }


        }
    }
}
