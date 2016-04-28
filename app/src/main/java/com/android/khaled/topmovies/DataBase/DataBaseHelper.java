package com.android.khaled.topmovies.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by khaled on 06/04/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "Movies.db";
    private static final int DATABASE_VERSION = 2;

    public static final String MOVI_TABLE = "Movi";
    public static final String RATED_MOVI_TABLE = "RatedMovies";
    public static final String FAVORITE_MOVI_TABLE = "FavoritesMovies";

    public static final String ID_COLUMN = "ID";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String POSTER_PATH_COLUMN = "POSTER";
    public static final String OVERVIEW_COLUMN = "OVERVIEW";
    public static final String RELEASE_DATE_COLUMN = "RELEASE";
    public static final String VOTE_COLUMN = "RATE";

    public static final String CREATE_MOVI_TABLE = "CREATE TABLE "
            + MOVI_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + TITLE_COLUMN + " TEXT, " + POSTER_PATH_COLUMN + " TEXT, "
            + OVERVIEW_COLUMN + " TEXT, " +VOTE_COLUMN + " FLOAT, "
            + RELEASE_DATE_COLUMN + " TEXT" + ")";

    public static final String CREATE_RATED_MOVI_TABLE = "CREATE TABLE "
            + RATED_MOVI_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + TITLE_COLUMN + " TEXT, " + POSTER_PATH_COLUMN + " TEXT, "
            + OVERVIEW_COLUMN + " TEXT, " +VOTE_COLUMN + " FLOAT, "
            + RELEASE_DATE_COLUMN + " TEXT" + ")";

    public static final String CREATE_FAVORITE_MOVI_TABLE = "CREATE TABLE "
            + FAVORITE_MOVI_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + TITLE_COLUMN + " TEXT, " + POSTER_PATH_COLUMN + " TEXT, "
            + OVERVIEW_COLUMN + " TEXT, " +VOTE_COLUMN + " FLOAT, "
            + RELEASE_DATE_COLUMN + " TEXT" + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVI_TABLE);
        db.execSQL(CREATE_RATED_MOVI_TABLE);
        db.execSQL(CREATE_FAVORITE_MOVI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+MOVI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+RATED_MOVI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+FAVORITE_MOVI_TABLE);
        onCreate(db);
    }

}