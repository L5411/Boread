package com.example.l_5411.boread.detail.zhihu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.l_5411.boread.bean.StringModelImpl;
import com.example.l_5411.boread.bean.ZhihuDailyStory;
import com.example.l_5411.boread.data.BoreadContract;
import com.google.gson.Gson;

/**
 * Created by L_5411 on 2017/3/16.
 */

public class ZhihuDetailPresenter implements ZhihuDetailContract.Presenter {

    private static final String TAG = ZhihuDetailPresenter.class.getSimpleName();

    private Context context;
    private ZhihuDetailContract.View view;
    private StringModelImpl model;

    private ZhihuDailyStory story;
    private Gson gson;

    private int id;
    private String title;
    private String coverUrl;

    public void setId(int id) {
        this.id = id;
    }


    public ZhihuDetailPresenter(Context context, ZhihuDetailContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        gson = new Gson();
    }

    @Override
    public void openInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(story.getShare_url()));
        context.startActivity(intent);
    }

    @Override
    public void shareAsText() {
        Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain");
        String text = title + " " + story.getShare_url();
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, "分享至"));
    }


    @Override
    public void requestData() {
            Cursor cursor = context.getContentResolver().query(
                    BoreadContract.ZhihuEntry.buildZhihuWithId(id),
                    null,
                    null,
                    null,
                    null);
            if(cursor.moveToFirst()) {
                String content = cursor.getString(cursor.getColumnIndex(BoreadContract.ZhihuEntry.COLUMN_CONTENT));
                story = gson.fromJson(content, ZhihuDailyStory.class);
                this.coverUrl = story.getImage();
                this.title = story.getTitle();
                view.showResult(convertZhihuContent(story.getBody()));
            }
            cursor.close();
    }

    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";

        String theme = "<body className=\"\" onload=\"onLoaded()\">";

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }

    @Override
    public void start() {
        requestData();
        view.setTitle(title);
        view.showCover(coverUrl);
        Log.i("ZhihuDetailPresenter", coverUrl);
    }
}
