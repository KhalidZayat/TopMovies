package com.android.khaled.topmovies.Splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.khaled.topmovies.AsyncTasks.FechMoviesFromWebsite;
import com.android.khaled.topmovies.DataBase.Movi_DB_Adapter;
import com.android.khaled.topmovies.Interfaces.OnNetworkResponse;
import com.android.khaled.topmovies.Main.MainActivity;
import com.android.khaled.topmovies.Model.Movi;
import com.android.khaled.topmovies.R;

public class SplashActivity extends Activity {

    public static boolean isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView im=(ImageView)findViewById(R.id.imagesplash);
        final Animation animator= AnimationUtils.loadAnimation(getBaseContext(), R.anim.splash_anim);

        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            Movi_DB_Adapter popAdapter = new Movi_DB_Adapter(this,"Movi");
            Movi_DB_Adapter topAdapter = new Movi_DB_Adapter(this,"RatedMovies");
            popAdapter.delete();
            topAdapter.delete();

            FechMoviesFromWebsite fechPopularMovies=new FechMoviesFromWebsite(this,popAdapter);
            FechMoviesFromWebsite fechTopRatedMovies=new FechMoviesFromWebsite(this,topAdapter);

            fechPopularMovies.execute(getString(R.string.popular_url));
            fechTopRatedMovies.execute(getString(R.string.top_rated_url));
            fechPopularMovies.setOnNetworkResponse(new OnNetworkResponse() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFail() {

                }
            });

            fechTopRatedMovies.setOnNetworkResponse(new OnNetworkResponse() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFail() {

                }
            });
        }


        if(!isConnected)
        {
            Toast.makeText(this, "Not Connected >> Offline", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }

        im.startAnimation(animator);

        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

}
