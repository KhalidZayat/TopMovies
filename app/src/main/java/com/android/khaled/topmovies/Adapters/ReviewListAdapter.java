package com.android.khaled.topmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.khaled.topmovies.Model.Review;
import com.android.khaled.topmovies.R;

import java.util.ArrayList;

/**
 * Created by khaled on 24/04/16.
 */
public class ReviewListAdapter extends ArrayAdapter<Review> {

    Context context;
    ArrayList<Review> reviews;


    public ReviewListAdapter(Context context, int resource, ArrayList<Review> objects) {
        super(context, resource, objects);
        this.reviews = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Review getItem(int arg0) {
        return reviews.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return 1;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholder viewholder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            viewholder = new viewholder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (viewholder) convertView.getTag();
        }
        viewholder.author.setText(reviews.get(position).getAuthor());
        viewholder.content.setText(reviews.get(position).getContent());
        return convertView;
    }


    public void updateAdapter(ArrayList<Review> arrayList) {
        if (arrayList != null) {
            reviews.clear();
            reviews.addAll(arrayList);
            this.notifyDataSetChanged();
        }
    }

    class viewholder {
        TextView author;
        TextView content;
        public viewholder(View convertview) {
            author = (TextView) convertview.findViewById(R.id.review_Author);
            content = (TextView) convertview.findViewById(R.id.review_content);
        }
    }
}
