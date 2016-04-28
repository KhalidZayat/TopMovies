package com.android.khaled.topmovies.Interfaces;

import com.android.khaled.topmovies.Model.Review;

import java.util.ArrayList;

/**
 * Created by khaled on 24/04/16.
 */
public interface OnNetworkDone {
    public void onSuccess(ArrayList<Review>reviews);
    public void onFail();
}
