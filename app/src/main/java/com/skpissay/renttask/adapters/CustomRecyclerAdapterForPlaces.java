package com.skpissay.renttask.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skpissay.renttask.R;
import com.skpissay.renttask.db.PlaceInfo;
import com.skpissay.renttask.interfaces.RecyclerPlaceListener;

import java.util.List;

/**
 * Created by skpissay on 08/06/17.
 */
public class CustomRecyclerAdapterForPlaces extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static RecyclerPlaceListener m_cClickListener;
    private static List<PlaceInfo> m_cObjJsonUsers;
    private Context m_cObjContext;

    public CustomRecyclerAdapterForPlaces(Context pContext, List<PlaceInfo> pJsonUsers,
                                          RecyclerPlaceListener pListener) {
        this.m_cObjContext = pContext;
        this.m_cObjJsonUsers = pJsonUsers;
        this.m_cClickListener = pListener;
    }

    @Override
    public int getItemCount() {
        return m_cObjJsonUsers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_cObjJsonUsers.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View lView;
        // paging logic
        if (viewType == VIEW_ITEM) {
            lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_cell, parent, false);
            DataObjectHolder ldataObjectHolder = new DataObjectHolder(lView);
            vh = ldataObjectHolder;
        } else {
            lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressdialog_paging, parent, false);
            ProgressViewHolder lprogressViewHolder = new ProgressViewHolder(lView);
            vh = lprogressViewHolder;
        }
        return vh;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout mainCell;

        TextView fullNametxt;

        TextView dec;

        ImageView imgdel;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mainCell = (RelativeLayout) itemView.findViewById(R.id.MAIN_CELL);
            mainCell.setOnClickListener(this);
            fullNametxt = (TextView) itemView.findViewById(R.id.NAME);
            dec = (TextView) itemView.findViewById(R.id.DEC);
            imgdel = (ImageView) itemView.findViewById(R.id.IMG_DEL);
            imgdel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.MAIN_CELL:
                    m_cClickListener.onInfoClick(getPosition(), m_cObjJsonUsers.get(getPosition()), v);
                    break;
                case R.id.IMG_DEL:
                    m_cClickListener.onDelClick(getPosition(), m_cObjJsonUsers.get(getPosition()), v);
                    break;
            }
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DataObjectHolder) {

            try {
                ((DataObjectHolder) holder).fullNametxt.setText(m_cObjJsonUsers.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((DataObjectHolder) holder).dec.setText(m_cObjJsonUsers.get(position).getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}