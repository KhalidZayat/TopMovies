package com.android.khaled.topmovies.Details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khaled.topmovies.Adapters.ReviewListAdapter;
import com.android.khaled.topmovies.AsyncTasks.FechReviews;
import com.android.khaled.topmovies.AsyncTasks.FechTrailer;
import com.android.khaled.topmovies.DataBase.Movi_DB_Adapter;
import com.android.khaled.topmovies.Interfaces.OnNetworkDone;
import com.android.khaled.topmovies.Interfaces.OnNetworkFinish;
import com.android.khaled.topmovies.Model.Movi;
import com.android.khaled.topmovies.Model.Review;
import com.android.khaled.topmovies.R;
import com.android.khaled.topmovies.Splash.SplashActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    TextView title;
    TextView date;
    TextView overview;
    ImageView poster;
    RatingBar rate;
    TextView makefavorite;
    Intent intent;
    Movi m;
    ListView listView;
    Movi_DB_Adapter mAdapter;
    ReviewListAdapter listAdapter;
    ImageButton Favorite,Trailer;
    Boolean fav = false;
    FechTrailer fechTrailer;
    Bundle bundle = new Bundle();
    protected  ArrayList<Review> revs =new ArrayList<>();

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        intent = getActivity().getIntent();
        m = intent.getParcelableExtra("Movi");

        if(m==null)
        {
            bundle = getArguments();
            m = bundle.getParcelable("Movi");
        }

        if(SplashActivity.isConnected == true) {
            FechReviews fechReviews = new FechReviews();
            fechReviews.execute("http://api.themoviedb.org/3/movie/" + m.getId() + "/reviews?api_key=1136cb50864014944f473eba1267d164");
            fechReviews.setOnNetworkResponse(new OnNetworkDone() {
                @Override
                public void onSuccess(ArrayList<Review> reviews) {
                  if(reviews!=null) {
                      revs = reviews;
                      listView = (ListView) getActivity().findViewById(R.id.review);
                      listView.setOnTouchListener(new View.OnTouchListener() {
                          // Setting on Touch Listener for handling the touch inside ScrollView
                          @Override
                          public boolean onTouch(View v, MotionEvent event) {
                              // Disallow the touch request for parent scroll on touch of child view
                              v.getParent().requestDisallowInterceptTouchEvent(true);
                              return false;
                          }
                      });
                      setListViewHeightBasedOnChildren(listView);
                      listAdapter = new ReviewListAdapter(getActivity(), listView.getId(), revs);
                      listView.setAdapter(listAdapter);
                  }
                }

                @Override
                public void onFail() {

                }
            });

        }


        title = (TextView)root.findViewById(R.id.title);
        title.setText(m.getTitle());

        date = (TextView)root.findViewById(R.id.date);
        date.setText(m.getRelease_date());

        overview = (TextView)root.findViewById(R.id.overview);
        overview.setText(m.getOverview());

        poster = (ImageView)root.findViewById(R.id.poster_details);
        Picasso.with(getActivity()).load(m.getPoster()).into(poster);

        rate = (RatingBar)root.findViewById(R.id.ratingBar);
        rate.setRating(m.getRate() / 2);

        Trailer =(ImageButton)root.findViewById(R.id.btn_trailer);
        Favorite =(ImageButton)root.findViewById(R.id.btn_favorite);


        makefavorite = (TextView)root.findViewById(R.id.makefavorite);
        mAdapter= new Movi_DB_Adapter(getActivity(),"FavoritesMovies");

        final Movi mm = mAdapter.getMovi(m.getPoster());
        if(mm.getTitle()==null)
        {
            Favorite.setImageResource(R.drawable.heart2);
            fav = false;
            makefavorite.setText("Add To Favorites");
        }

        else
        {
            Favorite.setImageResource(R.drawable.herat);
            fav = true;
            makefavorite.setText("Remove From Favorites");
        }

        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav == false) {
                    Favorite.setImageResource(R.drawable.herat);
                    mAdapter.save(m);
                    fav = true;
                    makefavorite.setText("Remove From Favorites");
                    Toast.makeText(getActivity(), "Movi Added To Favorite List", Toast.LENGTH_SHORT).show();
                } else {
                    Favorite.setImageResource(R.drawable.heart2);
                    mAdapter.deleteMovi(m.getPoster());
                    fav = false;
                    makefavorite.setText("Add To Favorites");
                    Toast.makeText(getActivity(), "Movi Removed From Favorite List", Toast.LENGTH_SHORT).show();
                }
            }
        });




        Trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechTrailer = new FechTrailer();
                fechTrailer.execute("http://api.themoviedb.org/3/movie/"+m.getId()+"/videos?api_key=1136cb50864014944f473eba1267d164");
                fechTrailer.setOnNetworkfinish(new OnNetworkFinish() {
                    @Override
                    public void onSuccess(String s) {

                        if(s==null) {
                            showNull();
                        }
                        else {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + s));
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://www.youtube.com/watch?v=" + s));
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });


        return root;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public  void  showNull()
    {
        Toast.makeText(getActivity(), "There is no trailer", Toast.LENGTH_SHORT).show();
    }
}
