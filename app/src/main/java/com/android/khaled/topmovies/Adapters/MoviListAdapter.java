package com.android.khaled.topmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.khaled.topmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled on 06/04/16.
 */
public class MoviListAdapter extends ArrayAdapter<String>
{
    private Context context;
    List<String> urls;


    public MoviListAdapter(Context context, List<String> moviesurls) {
        super(context, R.layout.gridview_item, moviesurls);
        this.context = context;
        this.urls = moviesurls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item,parent,false);
            holder = new ViewHolder();

            holder.movi_poster = (ImageView) convertView.findViewById(R.id.poster_main);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(urls.get(position)).placeholder(R.drawable.ic_launcher).into(holder.movi_poster);
        return convertView;
    }

    public void updateAdapter(List<String> u) {
        if (u != null) {
            urls.clear();
            urls.addAll(u);
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder
    {
        ImageView movi_poster;
    }

}
