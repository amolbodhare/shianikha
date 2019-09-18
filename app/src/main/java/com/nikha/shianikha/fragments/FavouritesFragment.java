package com.nikha.shianikha.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;;
import com.nikha.shianikha.activities.FilterActivity;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class FavouritesFragment extends Fragment implements Api.OnLoadingListener, Api.OnErrorListener, View.OnClickListener {
    private View fragmentView;
    private Context context;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;
    private LoadingDialog loadingDialog;

    public FavouritesFragment() {
        // Required empty public constructor
    }


    public static FavouritesFragment newInstance(Fragment fragment, String string) {
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        previousFragment = fragment;
        previousFragmentName = string;
        return favouritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Json json;
        if (fragmentView == null) {
            context = getContext();
            loadingDialog = new LoadingDialog(context);
            fragmentView = inflater.inflate(R.layout.fragment_favourites, container, false);

            fragmentView.findViewById(R.id.refineLinerLayout).setOnClickListener(this);

            Bundle bundle = getArguments();
            if (bundle != null) {
                String string = bundle.getString(P.json);
                try {
                    json = new Json(string);
                    JsonList jsonList = json.getJsonList(P.data);
                    ((TextView) fragmentView.findViewById(R.id.refineCount)).setText("Refine Search " + jsonList.size());
                    CustomListAdapter customListAdapter = new CustomListAdapter(jsonList);
                    ((ListView) fragmentView.findViewById(R.id.listView)).setAdapter(customListAdapter);

                    fragmentView.findViewById(R.id.refineLinerLayout).setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                hitFavouriteListApi();

        }
        return fragmentView;
    }

    private void hitFavouriteListApi() {
        Json json = new Json();
        String string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("favourites");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1) {
                            JsonList jsonList = json.getJsonList(P.data);
                            ((TextView) fragmentView.findViewById(R.id.refineCount)).setText("Refine Search " + jsonList.size());
                            CustomListAdapter customListAdapter = new CustomListAdapter(jsonList);
                            ((ListView) fragmentView.findViewById(R.id.listView)).setAdapter(customListAdapter);

                        } else

                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitFavouriteListApi");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.refineLinerLayout) {
            startActivity(new Intent(context, FilterActivity.class));
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class CustomListAdapter extends BaseAdapter implements View.OnClickListener {
        private JsonList jsonList;
        String string = "";
        Json json;
        ImageView imageView;

        private CustomListAdapter(JsonList jsons) {
            jsonList = jsons;
        }

        @Override
        public int getCount() {
            return jsonList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.matches_card, viewGroup, false);

            json = jsonList.get(i);

            string = json.getString(P.profile_pic);
            imageView = view.findViewById(R.id.thumbnail);
            try {
                Picasso.get().load(string).placeholder(R.drawable.user).fit().into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //string = json.getString(P.first_name) + json.getString(P.middle_name) + json.getString(P.last_name);
            if (App.showName) {
                string = json.getString(P.full_name);
                ((TextView) view.findViewById(R.id.full_name)).setText(string);
            }

            string = json.getString(P.profile_id);
            ((TextView) view.findViewById(R.id.profile_id_tv)).setText(string);

            string = json.getString(P.user_id);
            imageView.setTag(string);
            imageView.setOnClickListener(this);

            view.findViewById(R.id.fifth_lay).setTag(string);

            string = json.getString(P.like);
            imageView = view.findViewById(R.id.likeImageView);
            if (string.equalsIgnoreCase("1"))
                imageView.setColorFilter(context.getColor(R.color.green2));
            else
                imageView.setColorFilter(context.getColor(R.color.white));
            imageView.setOnClickListener(this);

            string = json.getString(P.connected);
            imageView = view.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
            changeConnectNowStatus(imageView,string);

            string = json.getString(P.age);
            ((TextView) view.findViewById(R.id.age_tv)).setText(string + "yrs");

            string = json.getString(P.height);
            ((TextView) view.findViewById(R.id.height_tv)).setText(string + "\"");

            string = json.getString(P.religion);
            ((TextView) view.findViewById(R.id.religion_tv)).setText(string);

            String str = json.getString(P.state_name);
            string = json.getString(P.city_name);
            ((TextView) view.findViewById(R.id.city_tv)).setText(string + str);

            string = json.getString(P.country_name);
            ((TextView) view.findViewById(R.id.country_tv)).setText(string);

            string = json.getString(P.occupation_name);
            ((TextView) view.findViewById(R.id.profession_tv)).setText(string);

            return view;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.thumbnail)
            {
                Object object = view.getTag();
                if (object != null) {
                    string = object.toString();

                    ((HomeActivity) context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName, string);
                    ((HomeActivity) context).fragmentLoader(((HomeActivity) context).profileDetailsFragments, getString(R.string.profileDetails));
                }
            }
            else if (view.getId() == R.id.imageView)
            {
                final ImageView imageView = (ImageView) view;

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                Object object = relativeLayout.getTag();
                if (object != null)
                    string = object.toString();

                hitConnectNowApi(string, new Response() {
                    @Override
                    public void onResponse(String string) {
                        changeConnectNowStatus(imageView,string);
                    }
                });

            }
            else if (view.getId() == R.id.likeImageView)
            {
                final ImageView imageView = (ImageView) view;

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                Object object = relativeLayout.getTag();
                if (object != null)
                    string = object.toString();

                hitLikeApi(string, new CallBack() {
                    @Override
                    public void onCallBack(boolean statusTrue)
                    {
                        if (statusTrue)
                            imageView.setColorFilter(context.getColor(R.color.green2));
                        else
                            imageView.setColorFilter(context.getColor(R.color.white));
                    }
                });
            }
        }
    }

    private void changeConnectNowStatus(ImageView imageView,String string)
    {
        RelativeLayout relativeLayout = ((RelativeLayout)imageView.getParent());
        TextView textView = relativeLayout.findViewById(R.id.connect_tv);

        if (string.equalsIgnoreCase("0"))
        {
            textView.setText("Connect Now");
            imageView.setImageDrawable(null);
        }
        else if (string.equalsIgnoreCase("1"))
        {
            textView.setText("Connected");
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
        }
        else if (string.equalsIgnoreCase("2"))
        {
            textView.setText("Pending Connection");
            imageView.setImageDrawable(null);
        }
    }

    private void hitConnectNowApi(String profileId, final Response response) {
        String string = new Session(context).getString(P.tokenData);

        Json json = new Json();
        json.addString(P.user_id_receiver, profileId);
        json.addString(P.token_id, string);

        final RequestModel requestModel = RequestModel.newRequestModel("send_request");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {

                        String string = json.getInt(P.status)+"";
                        response.onResponse(string);
                    }
                })
                .run("hitConnectNowApi");
    }

    private void hitLikeApi(String string, final CallBack callBack) {
        Json json = new Json();
        json.addString(P.profile_id, string);

        string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("like");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            //json = json.getJson(P.data);
                            callBack.onCallBack(true);

                        }
                        else {
                            callBack.onCallBack(false);
                            H.showMessage(context, json.getString(P.msg));
                        }
                    }
                })
                .run("hitLikeApi");
    }


    @Override
    public void onError() {
        H.showMessage(context, "Something went Wrong");
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }

    private interface CallBack
    {
        void onCallBack(boolean statusTrue);
    }

    private interface Response
    {
        void onResponse(String string);
    }
}
