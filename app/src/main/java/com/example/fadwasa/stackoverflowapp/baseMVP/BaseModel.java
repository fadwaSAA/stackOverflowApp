package com.example.fadwasa.stackoverflowapp.baseMVP;

/**
 * Created by Fadwasa on 04/12/2018 AD.
 */

public class BaseModel {
    private static final long STALE_MS = 20 * 1000; // Data is stale after 20 seconds




    public boolean isUpToDate(long timestamp) {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }


}
