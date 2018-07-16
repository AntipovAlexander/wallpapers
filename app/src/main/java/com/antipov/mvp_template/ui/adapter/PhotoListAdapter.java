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

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.common.Const;
import com.antipov.mvp_template.pojo.Picture;
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
    private final int NOT_CURRENT = 0;
    private final int CURRENT = 1;
    private List<Picture> mData = new ArrayList<>();

    public PhotoListAdapter(Context context, GlideRequests glide) {
        this.mGlide = glide;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PhotoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == NOT_CURRENT) {
            view = inflater.inflate(R.layout.recycler_item_photo_card, parent, false);
        } else {
            view = inflater.inflate(R.layout.recycler_item_current_wallpaper, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoListAdapter.ViewHolder holder, int position) {
        mGlide
                .load(mData.get(position).getUrls().getSmall())
                .into(holder.mImagePreview);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, PhotoDetailActivity.class);
            intent.putExtra(Const.Args.PICTURE, mData.get(position));
            mContext.startActivity(intent);
        });

        // if it current wallpaper leaving text from xml
        if (holder.getItemViewType() == CURRENT) {
            return;
        } else {
            holder.mTitle.setText(mContext.getString(R.string.author_placeholder, mData.get(position).getUser().getName()));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isCurrent()) {
            return CURRENT;
        } else {
            return NOT_CURRENT;
        }
    }

    public void setPictures(List<Picture> model) {
        mData = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_preview)
        ImageView mImagePreview;
        @BindView(R.id.tv_title)
        TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
