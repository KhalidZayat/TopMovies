package com.android.khaled.topmovies.AsyncTasks;

import android.os.AsyncTask;

import com.android.khaled.topmovies.Interfaces.OnNetworkDone;
import com.android.khaled.topmovies.Interfaces.OnNetworkResponse;
import com.android.khaled.topmovies.Json.Json_Parser;
import com.android.khaled.topmovies.Model.Review;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by khaled on 24/04/16.
 */
public class FechReviews extends AsyncTask<String, Void, ArrayList<Review>> {

    OnNetworkDone onNetworkDone;

    public void setOnNetworkResponse(OnNetworkDone onNetworkResponse){
        this.onNetworkDone=onNetworkResponse;
    }

    @Override
    protected ArrayList<Review> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String reviewJsonStr = null;

        try {

            String buildUri = params[0];
            URL url = new URL(buildUri);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                reviewJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                reviewJsonStr = null;
            }
            reviewJsonStr = buffer.toString();
        } catch (IOException e) {
            reviewJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {

                }
            }
        }
        try {
            return new Json_Parser().parseReviews(reviewJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        onNetworkDone.onSuccess(reviews);
    }
}
