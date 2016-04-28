package com.android.khaled.topmovies.AsyncTasks;

import android.os.AsyncTask;

import com.android.khaled.topmovies.Interfaces.OnNetworkDone;
import com.android.khaled.topmovies.Interfaces.OnNetworkFinish;
import com.android.khaled.topmovies.Json.Json_Parser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by khaled on 24/04/16.
 */
public class FechTrailer extends AsyncTask<String,Void,String> {

    OnNetworkFinish onNetworkFinish;

    public void setOnNetworkfinish(OnNetworkFinish onNetworkResponse){
        this.onNetworkFinish=onNetworkResponse;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String Trailer = null;

        try {

            String buildUri = params[0];
            URL url = new URL(buildUri);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Trailer = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                Trailer = null;
            }
            Trailer = buffer.toString();
        } catch (IOException e) {
            Trailer = null;
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
            return new Json_Parser().GetTrailerKey(Trailer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        onNetworkFinish.onSuccess(s);
    }
}
