package com.example.huynhha.cookandshare.model;

import android.provider.BaseColumns;

import com.example.huynhha.cookandshare.adapter.FavouriteAdapter;
import com.example.huynhha.cookandshare.entity.Like;
import com.example.huynhha.cookandshare.entity.Material;

public class DBContext {

    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "market";
        public static final String COLUMN_NAME_OF_RECIPE = "name";
        public static final String COLUMN_IMG_URL = "imageUrl";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_USERID = "userID";
        public static final String COLUMN_POST_ID = "postID";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_OF_RECIPE + " TEXT," +
                        FeedEntry.COLUMN_IMG_URL + " TEXT," +
                        FeedEntry.COLUMN_USERID + " TEXT," +
                        FeedEntry.COLUMN_POST_ID + " TEXT," +
                        FeedEntry.COLUMN_TIME + " TEXT)";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    }

    public static class MaterialDB implements BaseColumns {
        public static final String TABLE_NAME = "material";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME_OF_MATERIAL = "name";
        public static final String COLUMN_QUANTITY = "quatity";
        public static final String COLUMN_CHECK = "checkBoolean";

        public static final String SQL_CREATE_MATERIALS =
                "CREATE TABLE " + MaterialDB.TABLE_NAME + " (" +
                        MaterialDB._ID + " INTEGER PRIMARY KEY," +
                        MaterialDB.COLUMN_ID + " TEXT," +
                        MaterialDB.COLUMN_NAME_OF_MATERIAL + " TEXT," +
                        MaterialDB.COLUMN_QUANTITY + " TEXT," +
                        MaterialDB.COLUMN_CHECK + " TEXT)";

        public static final String SQL_DELETE_MATERIALS =
                "DROP TABLE IF EXISTS " + MaterialDB.TABLE_NAME;

    }

    public static class FavouriteDB implements BaseColumns {
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_POST_ID = "postID";

        public static final String SQL_CREATE_FAVOURITE =
                "CREATE TABLE " + FavouriteDB.TABLE_NAME + " (" +
                        FavouriteDB._ID + " INTEGER PRIMARY KEY," +
                        FavouriteDB.COLUMN_POST_ID + " TEXT)";

        public static final String SQL_DELETE_FAVOURITES =
                "DROP TABLE IF EXISTS " + FavouriteDB.TABLE_NAME;

    }

    public static class LikeDB implements BaseColumns {
        public static final String TABLE_NAME = "likeDB";
        public static final String COLUMN_POST_ID = "postID";

        public static final String SQL_CREATE_LIKE =
                "CREATE TABLE " + LikeDB.TABLE_NAME + " (" +
                        LikeDB._ID + " INTEGER PRIMARY KEY," +
                        LikeDB.COLUMN_POST_ID + " TEXT)";

        public static final String SQL_DELETE_LIKE =
                "DROP TABLE IF EXISTS " + LikeDB.TABLE_NAME;
    }

}
