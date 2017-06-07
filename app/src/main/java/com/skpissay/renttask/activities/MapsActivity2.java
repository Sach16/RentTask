package com.skpissay.renttask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.skpissay.renttask.R;
import com.skpissay.renttask.adapters.CustomRecyclerAdapterForPlaces;
import com.skpissay.renttask.db.PlaceInfo;
import com.skpissay.renttask.interfaces.RecyclerPlaceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skpissay on 07/06/17.
 */
public class MapsActivity2 extends FragmentActivity implements RecyclerPlaceListener, View.OnClickListener{

    LinearLayoutManager m_cLayoutManager;
    CustomRecyclerAdapterForPlaces m_cRecycAdPlaces;
    public static final String OBJ_PLACE = "OBJ_PLACE";

    private boolean m_cLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RecyclerView m_cRecycPlaces;
    ImageView m_cBackImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        m_cBackImg = (ImageView) findViewById(R.id.BACK_IMG);
        m_cBackImg.setOnClickListener(this);
        m_cRecycPlaces = (RecyclerView) findViewById(R.id.RV_MAIN);
        m_cLayoutManager = new LinearLayoutManager(this);
        m_cLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        m_cRecycPlaces.setLayoutManager(m_cLayoutManager);
        m_cRecycPlaces.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = m_cLayoutManager.getChildCount();
                    totalItemCount = m_cLayoutManager.getItemCount();
                    pastVisiblesItems = m_cLayoutManager.findFirstVisibleItemPosition();

//                    int page = totalItemCount / 15;
                    if (m_cLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            m_cLoading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
//                            int lpage = page + 1;
//                            page = lpage;
//                            doPagination(lpage);
                        }
                    }
                }
            }
        });

        List<PlaceInfo> placeInfoList = PlaceInfo.listAll(PlaceInfo.class);
        m_cRecycAdPlaces = new CustomRecyclerAdapterForPlaces(this, placeInfoList, this);
        m_cRecycPlaces.setAdapter(m_cRecycAdPlaces);

    }

    @Override
    public void onInfoClick(int pPostion, PlaceInfo placeInfo, View pView) {
        Intent lObjIntent = new Intent(this, MapsActivity3.class);
        lObjIntent.putExtra(OBJ_PLACE, (new Gson()).toJson(placeInfo));
        startActivity(lObjIntent);
    }

    @Override
    public void onDelClick(int pPostion, PlaceInfo placeInfo, View pView) {
        List<PlaceInfo> placeInfoList = PlaceInfo.find(PlaceInfo.class, "uniquecode = ?", placeInfo.getUniquecode());
        PlaceInfo.delete(placeInfoList.get(0));
        List<PlaceInfo> newplaceInfoList = PlaceInfo.listAll(PlaceInfo.class);
        m_cRecycPlaces.setAdapter(new CustomRecyclerAdapterForPlaces(this, newplaceInfoList, this));
        m_cRecycPlaces.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BACK_IMG:
                onBackPressed();
                break;
        }
    }
}
