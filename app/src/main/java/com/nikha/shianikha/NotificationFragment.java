package com.nikha.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;


public class NotificationFragment extends Fragment {

    View fragmentView;
    Context context;
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
                                        ((HomeActivity) context).notifacationDetails = NotifacationDetails.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName);
                                        ((HomeActivity) context).fragmentLoader(((HomeActivity) context).notifacationDetails, context.getString(R.string.notificationdetails));
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
        private String string = "";

        ListAdapter(JsonList jsons) {
            jsonList = jsons;
        }

        @Override
        public int getCount() {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.notification_list_item, null, false);

            json = jsonList.get(position);



            return convertView;
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
