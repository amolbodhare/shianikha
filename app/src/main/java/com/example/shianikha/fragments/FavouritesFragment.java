package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class FavouritesFragment extends Fragment implements Api.OnLoadingListener, Api.OnErrorListener {
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
                Picasso.get().load(string).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            string = json.getString(P.first_name) + json.getString(P.middle_name) + json.getString(P.last_name);
            ((TextView) view.findViewById(R.id.full_name)).setText(string);

            string = json.getString(P.profile_id);
            ((TextView) view.findViewById(R.id.profile_id_tv)).setText(string);

            string = json.getString(P.user_id);
            imageView.setTag(string);
            imageView.setOnClickListener(this);

            view.findViewById(R.id.fifth_lay).setTag(string);

            imageView = view.findViewById(R.id.likeImageView);
            imageView.setOnClickListener(this);

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

            string = json.getString(P.occupation);
            ((TextView) view.findViewById(R.id.profession_tv)).setText(string);

            view.findViewById(R.id.imageView).setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.thumbnail) {
                Object object = view.getTag();
                if (object != null) {
                    string = object.toString();

                    ((HomeActivity) context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName, string);
                    ((HomeActivity) context).fragmentLoader(((HomeActivity) context).profileDetailsFragments, getString(R.string.profileDetails));
                }
            } else if (view.getId() == R.id.imageView) {
                ImageView imageView = (ImageView) view;
                if (imageView.getDrawable() == null) {
                    imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
                } else
                    imageView.setImageDrawable(null);

            } else if (view.getId() == R.id.likeImageView) {
                ImageView imageView = (ImageView) view;
                String string = (String) imageView.getTag();
                if (string.equals("0")) {
                    imageView.setColorFilter(context.getColor(R.color.green2));
                    imageView.setTag("1");
                } else if (string.equals("1")) {
                    imageView.setColorFilter(context.getColor(R.color.white));
                    imageView.setTag("0");
                }

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                Object object = relativeLayout.getTag();
                if (object != null)
                    string = object.toString();

                hitLikeApi(string);
            }
        }
    }

    private void hitLikeApi(String string) {
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

                        if (json.getInt(P.status) == 1) {
                            json = json.getJson(P.data);

                        } else
                            H.showMessage(context, json.getString(P.msg));
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
}
