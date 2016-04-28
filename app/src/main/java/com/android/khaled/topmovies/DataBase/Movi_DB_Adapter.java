package com.android.khaled.topmovies.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.khaled.topmovies.Model.Movi;

import java.util.ArrayList;

/**
 * Created by khaled on 06/04/16.
 */
public class Movi_DB_Adapter {

    protected SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private Context mContext;
    private String MOVI_TABLE;


    private static final String WHERE_ID_EQUALS = DataBaseHelper.POSTER_PATH_COLUMN+ " =?";

    public Movi_DB_Adapter(Context context,String table) {
        this.mContext = context;
        this.MOVI_TABLE = table;
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DataBaseHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }


    public long save(Movi movi) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.ID_COLUMN, movi.getId());
        values.put(DataBaseHelper.TITLE_COLUMN, movi.getTitle());
        values.put(DataBaseHelper.OVERVIEW_COLUMN, movi.getOverview());
        values.put(DataBaseHelper.POSTER_PATH_COLUMN, movi.getPoster());
        values.put(DataBaseHelper.RELEASE_DATE_COLUMN, movi.getRelease_date());
        values.put(DataBaseHelper.VOTE_COLUMN, movi.getRate());

        return database.insert(MOVI_TABLE, null, values);
    }

    public int delete() {
        return database.delete(MOVI_TABLE, null,
                null);
    }

    public int deleteMovi(String id)
    {
        return database.delete(MOVI_TABLE,WHERE_ID_EQUALS,new String[]{id});
    }

    public ArrayList<Movi> getMovies() {
        ArrayList<Movi> movies = new ArrayList<Movi>();

        Cursor cursor = database.query(MOVI_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.TITLE_COLUMN,
                        DataBaseHelper.OVERVIEW_COLUMN,
                        DataBaseHelper.POSTER_PATH_COLUMN,
                        DataBaseHelper.RELEASE_DATE_COLUMN,
                        DataBaseHelper.VOTE_COLUMN }, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            Movi movi = new Movi();
            movi.setId(cursor.getInt(0));
            movi.setTitle(cursor.getString(1));
            movi.setOverview(cursor.getString(2));
            movi.setPoster(cursor.getString(3));
            movi.setRelease_date(cursor.getString(4));
            movi.setRate(cursor.getFloat(5));
            movies.add(movi);
        }
        return movies;
    }

    public ArrayList<String> getPosters() {
        ArrayList<String> urls = new ArrayList<String>();

        Cursor cursor = database.query(MOVI_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.TITLE_COLUMN,
                        DataBaseHelper.OVERVIEW_COLUMN,
                        DataBaseHelper.POSTER_PATH_COLUMN,
                        DataBaseHelper.RELEASE_DATE_COLUMN,
                        DataBaseHelper.VOTE_COLUMN }, null, null, null, null, null);

        while (cursor.moveToNext()) {
            urls.add(cursor.getString(3));
        }
        return urls;
    }

    public Movi getMovi(String id) {

        Movi movi = new Movi();

        String sql = "SELECT * FROM " + MOVI_TABLE
                + " WHERE " + DataBaseHelper.POSTER_PATH_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            movi.setId(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.ID_COLUMN)));
            movi.setTitle(cursor.getString(cursor.getColumnIndex(DataBaseHelper.TITLE_COLUMN)));
            movi.setOverview(cursor.getString(cursor.getColumnIndex(DataBaseHelper.OVERVIEW_COLUMN)));
            movi.setPoster(cursor.getString(cursor.getColumnIndex(DataBaseHelper.POSTER_PATH_COLUMN)));
            movi.setRelease_date(cursor.getString(cursor.getColumnIndex(DataBaseHelper.RELEASE_DATE_COLUMN)));
            movi.setRate(cursor.getFloat(cursor.getColumnIndex(DataBaseHelper.VOTE_COLUMN)));
        }
        return movi;
    }
}
