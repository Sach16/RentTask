package com.skpissay.renttask.interfaces;

import android.view.View;

import com.skpissay.renttask.db.PlaceInfo;

/**
 * Created by skpissay on 08/06/17.
 */
public interface RecyclerPlaceListener {

    public void onInfoClick(int pPostion, PlaceInfo placeInfo, View pView);
    public void onDelClick(int pPostion, PlaceInfo placeInfo, View pView);
}
