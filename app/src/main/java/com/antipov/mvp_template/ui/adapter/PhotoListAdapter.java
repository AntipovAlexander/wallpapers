package com.antipov.mvp_template.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antipov.mvp_template.Const;
import com.antipov.mvp_template.R;
import com.antipov.mvp_template.pojo.Picture;
import com.antipov.mvp_template.ui.activity.main.MainActivity;
import com.antipov.mvp_template.ui.activity.photo_detail.PhotoDetailActivity;
import com.antipov.mvp_template.utils.GlideRequests;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AlexanderAntipov on 05.06.2018.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private final GlideRequests mGlide;
    private final Context mContext;
    private List<Picture> mData = new ArrayList<>();

    public PhotoListAdapter(Context context, GlideRequests glide) {
        this.mGlide = glide;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PhotoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_photo_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListAdapter.ViewHolder holder, int position) {
        mGlide
            .load(mData.get(position).getUrls().getSmall())
            .into(holder.mImagePreview);
        holder.mTitle.setText(mContext.getString(R.string.author_placeholder, mData.get(position).getUser().getName()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PhotoDetailActivity.class);
            intent.putExtra(Const.Args.ID, mData.get(position).getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setPictures(List<Picture> model){
        mData = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_preview) ImageView mImagePreview;
        @BindView(R.id.tv_title) TextView mTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
