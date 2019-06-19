package com.example.shianikha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shianikha.R;
import com.example.shianikha.entities.MatchesEntity;

import java.util.List;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.MyViewHolder>
{

    private Context mContext;
    private List<MatchesEntity> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public AcceptedAdapter(Context mContext, List<MatchesEntity> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accepted_list_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MatchesEntity album = albumList.get(position);
        holder.title.setText(album.getName());
        //holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library

        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

      /*  holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //howPopupMenu(holder.overflow);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */

}
