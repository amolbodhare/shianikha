package com.nikha.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

;

public class FavouritesFragment extends Fragment implements Api.OnLoadingListener, Api.OnErrorListener, View.OnClickListener {
    private View fragmentView;
    private Context context;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;
    private LoadingDialog loadingDialog;

    private Json searchJson = new Json();

    private JsonList jsonList = new JsonList();
    private CustomListAdapter customListAdapter = new CustomListAdapter();

    private Bundle bundle;

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
        //if (fragmentView == null) {
        context = getContext();
        loadingDialog = new LoadingDialog(context);
        fragmentView = inflater.inflate(R.layout.fragment_favourites, container, false);

        fragmentView.findViewById(R.id.refineLinerLayout).setOnClickListener(this);

        fragmentView.findViewById(R.id.sortByLinearLayout).setOnClickListener(this);

        ((ListView) fragmentView.findViewById(R.id.listView)).setAdapter(customListAdapter);

        bundle = getArguments();
        if (bundle != null) {
            String string = bundle.getString(P.json);

            try {
                json = new Json(string);
                if (string.contains("search_by_id"))
                    fragmentView.findViewById(R.id.sortByLinearLayout).setEnabled(false);
                searchJson = json;
                hitAllSearchApi();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else
            hitFavouriteListApi("1");

            /*final SwipeRefreshLayout swipeRefreshLayout = fragmentView.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh()
                {
                    swipeRefreshLayout.setRefreshing(false);
                   hitFavouriteListApi("1");
                }
            });*/

        //}
        return fragmentView;
    }

    private void hitAllSearchApi() {
        Api.newApi(context, P.baseUrl).addJson(searchJson).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1) {
                            jsonList = json.getJsonList(P.data);
                            ((TextView) fragmentView.findViewById(R.id.refineCount)).setText("Refine Search " + jsonList.size());

                            fragmentView.findViewById(R.id.refineLinerLayout).setVisibility(View.INVISIBLE);

                            String string = searchJson + "";
                            if (string.contains(":")) {
                                string = string.substring(string.lastIndexOf(":"));

                                if (string.contains("2"))
                                    ((TextView) fragmentView.findViewById(R.id.sortByTextView)).setText("Ascending");
                                else if (string.contains("3"))
                                    ((TextView) fragmentView.findViewById(R.id.sortByTextView)).setText("Descending");
                            }

                            customListAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .run("hitAllSearchApi");
    }

    private void showPopUpMenu(View viewById) {
        PopupMenu popupMenu = new PopupMenu(context, viewById);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.sort_by_menu, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.ascending) {
                    if (searchJson.length() == 0)
                        hitFavouriteListApi("2");
                    else
                        updateSearchJson("2");

                } else if (menuItem.getItemId() == R.id.descending) {
                    if (searchJson.length() == 0)
                        hitFavouriteListApi("3");
                    else
                        updateSearchJson("3");
                }
                return false;
            }
        });
    }

    private void updateSearchJson(String string) {
        Json json = searchJson.getJson(P.data);
        json.addString(P.sort, string);
        searchJson.addJSON(P.data, json);
        hitAllSearchApi();
    }

    private void hitFavouriteListApi(final String sortFlag) {
        Json json = new Json();
        String string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);
        json.addString(P.sort, sortFlag);//1 = sort by id, 2 = sort by name in ascending and 3 = sort by name in descending

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
                            jsonList = json.getJsonList(P.data);
                            ((TextView) fragmentView.findViewById(R.id.refineCount)).setText("Refine Search " + jsonList.size());
                            customListAdapter.notifyDataSetChanged();
                        }
                        H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitFavouriteListApi");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.refineLinerLayout) {
            ((HomeActivity) context).startRefineSearchActivity();
        } else if (view.getId() == R.id.sortByLinearLayout)
            showPopUpMenu(view);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class CustomListAdapter extends BaseAdapter implements View.OnClickListener {
        String string = "";
        Json json;
        ImageView imageView;

        @Override
        public int getCount() {
            if (jsonList.size() == 0)
                fragmentView.findViewById(R.id.noDataTextView).setVisibility(View.VISIBLE);
            else
                fragmentView.findViewById(R.id.noDataTextView).setVisibility(View.GONE);
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

            LinearLayout linearLayout = view.findViewById(R.id.linearLayout);
            Button button = linearLayout.findViewById(R.id.button);

            json = jsonList.get(i);

            string = json.getString(P.profile_pic);
            imageView = view.findViewById(R.id.thumbnail);
            try {
                Picasso.get().load(string).placeholder(R.drawable.user).fit().into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (App.showName) {
                //string = json.getString(P.full_name);
                string = json.getString(P.first_name) + json.getString(P.middle_name) + json.getString(P.last_name);
                ((TextView) view.findViewById(R.id.full_name)).setText(string);
            }

            string = json.getString(P.profile_id);
            ((TextView) view.findViewById(R.id.profile_id_tv)).setText(string);

            string = json.getString(P.user_id);

            imageView.setTag(string);
            imageView.setOnClickListener(this);

            button.setTag(string);
            button.setOnClickListener(this);

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
            changeConnectNowStatus(imageView, string);

            string = json.getString(P.age);
            ((TextView) view.findViewById(R.id.age_tv)).setText(string + " yrs");

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

            string = json.getString(P.day);
            ((TextView) view.findViewById(R.id.time)).setText(string + " days ago");

            string = json.getString(P.photo_available);
            if (string.equals("0")) {
                linearLayout.setVisibility(View.VISIBLE);
                button.setOnClickListener(this);
            } else if (string.equals("1")) {
                linearLayout.setVisibility(View.GONE);
                button.setOnClickListener(null);
            }

            string = json.getString(P.request_photo);
            if (string.equals("0"))
                button.setText("Request Photo");
            else if (string.equals("1"))
                button.setText("Photo already requested.");

            return view;
        }

        @Override
        public void onClick(View view)
        {
            if (view.getId() == R.id.thumbnail) {
                Object object = view.getTag();
                if (object != null) {
                    string = object.toString();

                    ((HomeActivity) context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName, string);
                    ((HomeActivity) context).fragmentLoader(((HomeActivity) context).profileDetailsFragments, getString(R.string.profileDetails));
                }
            } else if (view.getId() == R.id.imageView) {

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                Object object = relativeLayout.getTag();
                if (object != null)
                    string = object.toString();

                hitConnectNowApi(string);

            } else if (view.getId() == R.id.likeImageView) {

                RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
                Object object = relativeLayout.getTag();
                if (object != null)
                    string = object.toString();

                hitLikeApi(string);
            }
            else if (view.getId() == R.id.button)
            {
                Object object = view.getTag();
                if (object != null)
                    string = object.toString();

               hitRequestPhotoApi(string);
            }
        }
    }

    private void changeConnectNowStatus(ImageView imageView, String string) {
        RelativeLayout relativeLayout = ((RelativeLayout) imageView.getParent());
        TextView textView = relativeLayout.findViewById(R.id.connect_tv);

        if (string.equalsIgnoreCase("0")) {
            textView.setText("Connect Now");
            imageView.setImageDrawable(null);
        } else if (string.equalsIgnoreCase("1")) {
            textView.setText("Connected");
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_check_black_24dp));
        } else if (string.equalsIgnoreCase("2")) {
            textView.setText("Pending Connection");
            imageView.setImageDrawable(null);
        }
    }

    private void hitConnectNowApi(String profileId) {
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
                    public void onSuccess(Json json) {
                        if (json.getInt(P.status) == 1)
                        {
                            json = json.getJson(P.data);
                            String string = json.getString(P.connected_status);
                            if (string.equalsIgnoreCase("0"))
                                ((HomeActivity) context).showNotPurchasedPopUp();
                            else if (string.equalsIgnoreCase("2"))
                                ((HomeActivity) context).showExpiredPopUp();
                            else if (bundle != null)
                            {
                                string = bundle.getString(P.json);

                                try {
                                    json = new Json(string);
                                    if (string.contains("search_by_id"))
                                        fragmentView.findViewById(R.id.sortByLinearLayout).setEnabled(false);
                                    searchJson = json;
                                    hitAllSearchApi();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                            else
                                hitFavouriteListApi("1");
                        } else
                            H.showMessage(context, json.getString(P.msg));

                    }
                })
                .run("hitConnectNowApi");
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
                            String string = json.getString(P.like_status);
                            if (string.equalsIgnoreCase("0"))
                                ((HomeActivity) context).showNotPurchasedPopUp();
                            else if (string.equalsIgnoreCase("2"))
                                ((HomeActivity) context).showExpiredPopUp();
                            else {
                                if (bundle != null) {
                                    string = bundle.getString(P.json);

                                    try {
                                        json = new Json(string);
                                        if (string.contains("search_by_id"))
                                            fragmentView.findViewById(R.id.sortByLinearLayout).setEnabled(false);
                                        searchJson = json;
                                        hitAllSearchApi();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else
                                    hitFavouriteListApi("1");
                            }
                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitLikeApi");
    }

    public void hitApiForRefineSearchRequest() {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("search");
        requestModel.addJSON(P.data, App.json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (!((HomeActivity) context).isDestroyed()) {
                            if (isLoading)
                                loadingDialog.show("Please wait submitting your data...");
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
                            jsonList = json.getJsonList(P.data);
                            ((TextView) fragmentView.findViewById(R.id.refineCount)).setText("Refine Search " + jsonList.size());
                            customListAdapter.notifyDataSetChanged();

                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitRefineSearchhApi");
    }

    @Override
    public void onError() {
        H.showMessage(context, "Something went Wrong");
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (!((HomeActivity) context).isDestroyed()) {
            if (isLoading)
                loadingDialog.show();
            else
                loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }

    private void hitRequestPhotoApi(String string) {
        Json json = new Json();
        json.addString(P.profile_id, string);

        string = new Session(context).getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("request_photo");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {
                        if (json.getInt(P.status) == 1)
                        {
                            if (bundle != null) {
                                String string = bundle.getString(P.json);

                                try {
                                    json = new Json(string);
                                    if (string.contains("search_by_id"))
                                        fragmentView.findViewById(R.id.sortByLinearLayout).setEnabled(false);
                                    searchJson = json;
                                    hitAllSearchApi();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else
                                hitFavouriteListApi("1");
                        }
                    }
                })
                .run("hitRequestPhotoApi");
    }
}
