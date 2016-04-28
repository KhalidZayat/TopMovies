package com.android.khaled.topmovies.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.android.khaled.topmovies.DataBase.Movi_DB_Adapter;
import com.android.khaled.topmovies.Interfaces.OnNetworkResponse;
import com.android.khaled.topmovies.Json.Json_Parser;
import com.android.khaled.topmovies.Model.Movi;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by khaled on 08/04/16.
 */
public class FechMoviesFromWebsite extends AsyncTask<String, Void, Movi[]> {
    Context context;
    Movi_DB_Adapter mAdapter;
    OnNetworkResponse onNetworkResponse;
    public FechMoviesFromWebsite(Context context,Movi_DB_Adapter adapter) {
        this.context = context;
        this.mAdapter = adapter;
    }

    public void setOnNetworkResponse(OnNetworkResponse onNetworkResponse){
        this.onNetworkResponse=onNetworkResponse;
    }

    @Override
    protected Movi[] doInBackground(String... params) {
        if (params.length == 0)
            return null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviJsonStr = null;

        try {

            String buildUri = params[0];
            URL url = new URL(buildUri);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                moviJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                moviJsonStr = null;
            }
            moviJsonStr = buffer.toString();
        } catch (IOException e) {
            moviJsonStr = null;
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
            return new Json_Parser().parse(moviJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movi[] movis) {
        for (int i = 0; i < movis.length; i++) {
            mAdapter.save(movis[i]);
        }
        onNetworkResponse.onSuccess();
    }
}
