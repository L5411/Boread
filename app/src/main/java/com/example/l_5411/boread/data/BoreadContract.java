package com.example.l_5411.boread.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by L_5411 on 2017/3/13.
 */

public class BoreadContract {

    public static final String CONTENT_AUTHORITY = "com.example.l_5411.boread";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ZHIHU = "zhihu";

    public static final String PATH_DOUBAN = "douban";

    public static final class ZhihuEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ZHIHU).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ZHIHU;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ZHIHU;


        public static Uri buildZhihuUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildZhihuWithId(int id) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        }

        public static final String TABLE_NAME = "zhihu";    // 表名
        public static final String COLUMN_ZHIHU_ID = "zhihu_id";    // 知乎内容id
        public static final String COLUMN_DATE = "date";    // 日期
        public static final String COLUMN_NEWS = "news";    // StoriesBean 实体
        public static final String COLUMN_CONTENT = "content";  // 详细内容实体

    }

    public static final class DoubanEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DOUBAN).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOUBAN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOUBAN;

        public static Uri buildDoubnaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDoubnaWithId(int id) {

            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        }

        public static final String TABLE_NAME = "douban";   // 表明
        public static final String COLUMN_MOVIE_ID = "id";  // 电影id
        public static final String COLUMN_MOVIE_SUM = "movie_summary";  // 电影概要
        public static final String COLUMN_MOVIE_CONTENT = "movie_content";  // 电影实体
    }
}
