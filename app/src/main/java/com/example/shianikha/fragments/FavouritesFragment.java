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
import android.widget.TextView;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.commen.P;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class FavouritesFragment extends Fragment {
    private View fragmentView;
    private Context context;

    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

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
            fragmentView = inflater.inflate(R.layout.fragment_favourites, container, false);

            Bundle bundle = getArguments();
            if (bundle != null) {
                String string = bundle.getString(P.json);
                try {
                    json = new Json(string);
                    JsonList jsonList = json.getJsonList(P.data);
                    CustomListAdapter customListAdapter = new CustomListAdapter(jsonList);
                    ((ListView) fragmentView.findViewById(R.id.listView)).setAdapter(customListAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return fragmentView;
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
            imageView.setOnClickListener(this);

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

            }
        }
    }

}
