package com.android.khaled.topmovies.Main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.khaled.topmovies.Adapters.MoviListAdapter;
import com.android.khaled.topmovies.DataBase.Movi_DB_Adapter;
import com.android.khaled.topmovies.Details.DetailsActivity;
import com.android.khaled.topmovies.Details.DetailsActivityFragment;
import com.android.khaled.topmovies.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView gridView;
    MoviListAdapter gridAdapter;
    ArrayList<String> urls;
    Intent intent =null;
    Movi_DB_Adapter db;
    String Type="Movi";
    DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();
    Bundle bundle;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView)root.findViewById(R.id.gridViewMovies);
        updateGridView();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            updateGridView();
            return true;
        }

        if (id == R.id.action_popular) {
            Type = "Movi";
            updateGridView();
            return true;
        }

        if (id == R.id.action_top_rated) {
            Type = "RatedMovies";
            updateGridView();
            return true;
        }

        if (id == R.id.action_favorite) {
            Type = "FavoritesMovies";
            updateGridView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateGridView()
    {
        db =new Movi_DB_Adapter(getActivity(),Type);
        urls = db.getPosters();
        gridAdapter=new MoviListAdapter(getContext(),urls);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MainActivity.CheckTablet == true)
                {
                    bundle = new Bundle();
                    bundle.putParcelable("Movi",db.getMovi(urls.get(position)));
                    detailsActivityFragment = new DetailsActivityFragment();
                    detailsActivityFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, detailsActivityFragment).commit();
                }
                else
                {
                    intent=new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("Movi",db.getMovi(urls.get(position)));
                    startActivity(intent);
                }
            }
        });
        gridView.setAdapter(gridAdapter);
    }
}
