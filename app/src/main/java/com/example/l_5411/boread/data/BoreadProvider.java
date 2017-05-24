package com.example.l_5411.boread.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by L_5411 on 2017/3/13.
 */

public class BoreadProvider extends ContentProvider {

    private static final String TAG = BoreadProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BoreadDbHelper mOpenHelper;

    private static final int ZHIHU = 100;
    private static final int ZHIHU_WITH_ID = 101;
    private static final int ZHIHU_WITH_DATE = 102;

    private static final int MOVIE = 200;
    private static final int MOVIE_WITH_ID = 201;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BoreadContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, BoreadContract.PATH_ZHIHU, ZHIHU);
        uriMatcher.addURI(authority, BoreadContract.PATH_ZHIHU + "/#", ZHIHU_WITH_ID);
        uriMatcher.addURI(authority, BoreadContract.PATH_ZHIHU + "/date/#", ZHIHU_WITH_DATE);

        uriMatcher.addURI(authority, BoreadContract.PATH_DOUBAN, MOVIE);
        uriMatcher.addURI(authority, BoreadContract.PATH_DOUBAN + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BoreadDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor retCursor;
        switch (match) {
            // zhihu
            case ZHIHU:
                retCursor = db.query(
                        BoreadContract.ZhihuEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            // zhihu/*
            case ZHIHU_WITH_ID:
                retCursor = db.query(
                        BoreadContract.ZhihuEntry.TABLE_NAME,
                        projection,
                        BoreadContract.ZhihuEntry.COLUMN_ZHIHU_ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)}, // 获取COLUMN_ZHIHU_ID
                        null,
                        null,
                        sortOrder
                );
                break;
            // zhihu/date/*
            case ZHIHU_WITH_DATE:
                retCursor = db.query(
                        BoreadContract.ZhihuEntry.TABLE_NAME,
                        projection,
                        BoreadContract.ZhihuEntry.COLUMN_DATE + " = ?",
                        new String[]{uri.getPathSegments().get(2)}, // 获取COLUMN_DATE
                        null,
                        null,
                        sortOrder
                );
                break;
            // douban/
            case MOVIE:
                retCursor = db.query(
                        BoreadContract.DoubanEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            // douban/#
            case MOVIE_WITH_ID:
                Log.i(TAG, "movie with id" + uri.toString());
                retCursor = db.query(
                        BoreadContract.DoubanEntry.TABLE_NAME,
                        projection,
                        BoreadContract.DoubanEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)}, // COLUMN_MOVIE_ID
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ZHIHU:
                return BoreadContract.ZhihuEntry.CONTENT_TYPE;
            case ZHIHU_WITH_ID:
                return BoreadContract.ZhihuEntry.CONTENT_ITEM_TYPE;
            case ZHIHU_WITH_DATE:
                return BoreadContract.ZhihuEntry.CONTENT_TYPE;
            case MOVIE:
                return BoreadContract.DoubanEntry.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return BoreadContract.DoubanEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case ZHIHU:
                long _id = db.insert(BoreadContract.ZhihuEntry.TABLE_NAME, null, values);
                if(_id > 0) {
                    returnUri = BoreadContract.ZhihuEntry.buildZhihuUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case MOVIE:
                _id = db.insert(BoreadContract.DoubanEntry.TABLE_NAME, null, values);
                if(_id > 0) {
                    returnUri = BoreadContract.DoubanEntry.buildDoubnaUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case ZHIHU:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for(ContentValues value: values) {
                        long _id = db.insert(BoreadContract.ZhihuEntry.TABLE_NAME, null, value);
                        if(_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                break;
            case MOVIE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for(ContentValues value: values) {
                        long _id = db.insert(BoreadContract.DoubanEntry.TABLE_NAME, null, value);
                        if(_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO: 2017/3/13  
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update: " + uri );
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated = 0;
        switch (match) {
            case ZHIHU_WITH_ID:
                rowsUpdated = db.update(
                        BoreadContract.ZhihuEntry.TABLE_NAME,
                        values,
                        BoreadContract.ZhihuEntry.COLUMN_ZHIHU_ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)}
                );
                break;
            case MOVIE_WITH_ID:
                rowsUpdated = db.update(
                        BoreadContract.DoubanEntry.TABLE_NAME,
                        values,
                        BoreadContract.DoubanEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{uri.getPathSegments().get(1)}
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if( rowsUpdated != 0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
