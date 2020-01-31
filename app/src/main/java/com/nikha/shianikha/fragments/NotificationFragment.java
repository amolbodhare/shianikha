package com.nikha.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.R;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationFragment extends Fragment {

    private View fragmentView;
    private Context context;
    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters

    public static NotificationFragment newInstance(Fragment fragment, String string) {
        NotificationFragment notificationFragment = new NotificationFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return notificationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_notification, null, false);

            hitNotificationApi();
            ((HomeActivity)context).hideOrUpdateNotificationCount(true,0);
        }

        final SwipeRefreshLayout swipeRefreshLayout = fragmentView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);
                hitNotificationApi();
            }
        });

        return fragmentView;
    }

    private void hitNotificationApi()
    {
        Json json = new Json();
        json.addString(P.token_id, new Session(context).getString(P.tokenData));

        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("notification");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (!((HomeActivity)context).isDestroyed()) {
                            if (isLoading)
                                loadingDialog.show("Please wait...");
                            else
                                loadingDialog.dismiss();
                        }
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
                            JsonList jsonList = json.getJsonList(P.data);
                            if (jsonList != null) {
                                ListAdapter listAdapter = new ListAdapter(jsonList);
                                ListView listView = fragmentView.findViewById(R.id.notificationList);
                                listView.setAdapter(listAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                       /* ((HomeActivity) context).notifacationDetails = NotifacationDetails.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName);
                                        ((HomeActivity) context).fragmentLoader(((HomeActivity) context).notifacationDetails, context.getString(R.string.notificationdetails));*/

                                        Json j = (Json)view.getTag();
                                        String  string = j.getString(P.notification_users_id);
                                        ((HomeActivity) context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName, string);
                                        ((HomeActivity) context).fragmentLoader(((HomeActivity) context).profileDetailsFragments, getString(R.string.profileDetails));
                                        ((HomeActivity)context).tempView = view;
                                    }
                                });
                            }
                        }
                    }
                })
                .run("hitNotificationApi");
    }


    private class ListAdapter extends BaseAdapter {
        private JsonList jsonList;
        private Json json;
        private CircleImageView imageView;
        private TextView textView;
        private String string = "";

        ListAdapter(JsonList jsons) {
            jsonList = jsons;
        }

        @Override
        public int getCount() {
            if (jsonList.size() == 0)
                fragmentView.findViewById(R.id.noDataTextView).setVisibility(View.VISIBLE);
            else
                fragmentView.findViewById(R.id.noDataTextView).setVisibility(View.GONE);
            return jsonList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.notification_list_item, null, false);

            json = jsonList.get(position);
            view.setTag(json);

            imageView=view.findViewById(R.id.notiCircle);
            string = json.getString(P.profile_pic);
            if (string != null)
                try {
                    Picasso.get().load( string).placeholder(R.drawable.placeholder).into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            string = json.getString(P.message);
            if (string != null) {
                textView = view.findViewById(R.id.message);
                textView.setText(string);
            }

            string = json.getString(P.date);
            if (string != null) {
                textView = view.findViewById(R.id.date);
                textView.setText(string);
            }

             return view;
        }
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
