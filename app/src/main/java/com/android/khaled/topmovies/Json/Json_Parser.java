package com.android.khaled.topmovies.Json;

import com.android.khaled.topmovies.Model.Movi;
import com.android.khaled.topmovies.Model.Review;
import com.android.khaled.topmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by khaled on 06/04/16.
 */
public class Json_Parser
{
    public Movi[] parse(String jsonStr)throws JSONException
    {
        JSONObject moviJson = new JSONObject(jsonStr);
        JSONArray moviArray = moviJson.getJSONArray("results");
        JSONObject movi;
        Movi[] movies=new Movi[20];

        for (int i = 0; i < 20 ; i++)
        {
            movies[i]=new Movi();
            movi = moviArray.getJSONObject(i);
            movies[i].setId(movi.getInt("id"));
            movies[i].setPoster("http://image.tmdb.org/t/p/w185/"+movi.getString("poster_path"));
            movies[i].setTitle(movi.getString("original_title"));
            movies[i].setRelease_date(movi.getString("release_date"));
            movies[i].setRate((float) movi.getDouble("vote_average"));
            movies[i].setOverview(movi.getString("overview"));
        }
        return movies;
    }

    public ArrayList<Review> parseReviews(String jsonStr)throws JSONException
    {
        JSONObject reviewJson = new JSONObject(jsonStr);
        JSONArray reviewArray = reviewJson.getJSONArray("results");
        int totalReasults = reviewJson.getInt("total_results");
        ArrayList<Review> R = new ArrayList<>();
        Review rev = new Review();
        for (int i = 0; i <totalReasults ; i++) {
            rev = new Review();
            JSONObject r = reviewArray.getJSONObject(i);
            rev.setAuthor(r.getString("author"));
            rev.setContent(r.getString("content"));
            R.add(rev);
        }
        return R;
    }

    public String GetTrailerKey(String jsonStr)throws JSONException
    {
        JSONObject TrailerJson = new JSONObject(jsonStr);
        JSONArray results = TrailerJson.getJSONArray("results");
        JSONObject trailer =null;
        for (int i = 0; i <results.length(); i++) {
            trailer = results.getJSONObject(i);
            return trailer.getString("key");
        }
        return null;
    }
}