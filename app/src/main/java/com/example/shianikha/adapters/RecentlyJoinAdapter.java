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

import com.bumptech.glide.Glide;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.fragments.ProfileDetailsFragments;

import java.util.ArrayList;

public class RecentlyJoinAdapter extends RecyclerView.Adapter<RecentlyJoinAdapter.ViewHolder>
{
    private static final String TAG = "RecentlyJoinAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public RecentlyJoinAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls) {
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
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

        Glide.with(mContext)
                .asBitmap()
                //.load(mImageUrls.get(position))
                .load(R.drawable.kangna)
                .into(viewHolder.thumbnail);

        viewHolder.title.setText(mNames.get(position));

        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((HomeActivity)mContext).profileDetailsFragments = ProfileDetailsFragments.newInstance(HomeActivity.currentFragment,HomeActivity.currentFragmentName);
                ((HomeActivity)mContext).fragmentLoader(((HomeActivity)mContext).profileDetailsFragments,mContext.getString(R.string.profileDetails));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mImageUrls.size();
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
        FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();

                 manager.beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }
}
