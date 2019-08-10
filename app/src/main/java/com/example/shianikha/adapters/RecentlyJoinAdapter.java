package com.example.shianikha.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.JsonList;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.commen.P;
import com.example.shianikha.fragments.ProfileDetailsFragments;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecentlyJoinAdapter extends RecyclerView.Adapter<RecentlyJoinAdapter.ViewHolder>
{
    private static final String TAG = "RecentlyJoinAdapter";

    //vars
    private Context context;
    JsonList jsonList;

    public RecentlyJoinAdapter(Context context, JsonList jsonList) {
        this.jsonList = jsonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_join, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");


        Picasso.get().load(jsonList.get(position).getString(P.profile_pic))
                .into(viewHolder.thumbnail);


        viewHolder.title.setText(jsonList.get(position).getString(P.full_name));

        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // we are passing last parameter user_id but in next profile details api it would be considered as profile_id
                ((HomeActivity)context).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment,HomeActivity.currentFragmentName,jsonList.get(position).getString(P.user_id));
                ((HomeActivity)context).fragmentLoader(((HomeActivity)context).profileDetailsFragments,P.profile_details_title);
            }
        });
    }


    @Override
    public int getItemCount() {
        return jsonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView thumbnail, overflow;

        public ViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

    private void fragmentLoader(Fragment fragment)
    {
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                 manager.beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }
}
