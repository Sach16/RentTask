package com.skpissay.renttask;

import com.orm.SugarContext;
import android.support.multidex.MultiDexApplication;

/**
 * Created by skpissay on 08/06/17.
 */
public class RentTask extends MultiDexApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
