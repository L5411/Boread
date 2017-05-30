package com.example.l_5411.boread.util;

import android.net.Uri;

import com.example.l_5411.boread.BuildConfig;

import java.util.Set;

/**
 * Created by L_5411 on 2017/3/11.
 * 知乎日报API
 */

public class Api {
    // 知乎日报

    // 在ZHIHU_NEWS后拼接id获得NEWS详细信息
    public static final String ZHIHU_NEWS = "http://news-at.zhihu.com/api/4/news/";
    // 在ZHIHU_HISTORY后拼接8位日期，如20170311获取20170310消息
    public static final String ZHIHU_HISTORY = "http://news.at.zhihu.com/api/4/news/before/";

    // 豆瓣电影top250
    public static final String DOUBAN_MOVIE_TOP = "https://api.douban.com/v2/movie/top250";
    // 豆瓣电影搜索 需加参数q或者tag
    public static final String DOUBAN_MOVIE_SEARCH = "https://api.douban.com/v2/movie/search";
    public static final String DOUBAN_MOVIE_ITEM = "https://api.douban.com/v2/movie/subject";
    public static String getDoubanMovieTopApi(int start, int size) {
        Uri uri = Uri.parse(DOUBAN_MOVIE_TOP)
                .buildUpon()
                .appendQueryParameter("start", Integer.toString(start))
                .appendQueryParameter("count", Integer.toString(size))
                .build();
        return uri.toString();
    }
    public static String getDoubanMovieSearch(String tag, int start, int size) {
        Uri uri = Uri.parse(DOUBAN_MOVIE_SEARCH)
                .buildUpon()
                .appendQueryParameter("q", tag)
                .appendQueryParameter("start", Integer.toString(start))
                .appendQueryParameter("count", Integer.toString(size))
                .build();
        return uri.toString();
    }

    public static String getDoubanMovieItemApi(int id) {
        Uri uri = Uri.parse(DOUBAN_MOVIE_ITEM)
                .buildUpon()
                .appendPath(Integer.toString(id))
                .build();
        return uri.toString();
    }
    // Pexels API 需要添加请求头部 Authorization: YOUR_API_KEY
    public static final String PEXELS_API_POPULAR = "http://api.pexels.com/v1/popular";
    public static final String PEXELS_API_KEY = BuildConfig.pexels_api_key;
    public static final String PEXELS_API_SEARCH = "http://api.pexels.com/v1/search";
    public static String getPexelsApiPopular(int page) {
        Uri uri = Uri.parse(PEXELS_API_POPULAR).buildUpon()
                .appendQueryParameter("page",Integer.toString(page))
                .appendQueryParameter("per_page","16")
                .build();
        return uri.toString();
    }
    public static String getPexelsApiSearch(int page, String tag) {
        Uri uri = Uri.parse(PEXELS_API_SEARCH).buildUpon()
                .appendQueryParameter("query",tag)
                .appendQueryParameter("page",Integer.toString(page))
                .appendQueryParameter("per_page","16")
                .build();
        return uri.toString();
    }

    public static Uri replaceUriParameter(Uri uri, String key, String newValue) {
        final Set<String> params = uri.getQueryParameterNames();
        final Uri.Builder newUri = uri.buildUpon().clearQuery();
        for (String param : params) {
            String value;
            if (param.equals(key)) {
                value = newValue;
            } else {
                value = uri.getQueryParameter(param);
            }
            newUri.appendQueryParameter(param, value);
        }
        return newUri.build();
    }
}
