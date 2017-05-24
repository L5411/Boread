package com.example.l_5411.boread.util;

import android.net.Uri;

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

    public static String getDoubanMovieTopApi(int start, int size) {
        Uri uri = Uri.parse(DOUBAN_MOVIE_TOP)
                .buildUpon()
                .appendQueryParameter("start", Integer.toString(start))
                .appendQueryParameter("count", Integer.toString(size))
                .build();
        return uri.toString();
    }

    // 豆瓣电影搜索 需加参数q或者tag
    public static final String DOUBAN_MOVIE_SEARCH = "https://api.douban.com/v2/movie/search";

    public static final String DOUBAN_MOVIE_ITEM = "https://api.douban.com/v2/movie/subject";
    public static String getDoubanMovieItemApi(int id) {
        Uri uri = Uri.parse(DOUBAN_MOVIE_ITEM)
                .buildUpon()
                .appendPath(Integer.toString(id))
                .build();
        return uri.toString();
    }
    // Pexels API 需要添加请求头部 Authorization: YOUR_API_KEY
    public static final String PEXELS_API = "http://api.pexels.com/v1/popular";
    public static final String PEXELS_API_KEY =
            "563492ad6f91700001000001bd5d9e93a8164ee45ebbe78c2feb6a98";

}
