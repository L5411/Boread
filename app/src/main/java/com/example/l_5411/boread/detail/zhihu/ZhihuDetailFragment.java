package com.example.l_5411.boread.detail.zhihu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.l_5411.boread.R;

/**
 *
 * Created by L_5411 on 2017/3/16.
 */

public class ZhihuDetailFragment extends Fragment implements ZhihuDetailContract.View{

    private ImageView imageView;
    private WebView webView;
    private NestedScrollView scrollView;
    private CollapsingToolbarLayout toolbarLayout;

    private Context context;

    private ZhihuDetailContract.Presenter presenter;

    public static ZhihuDetailFragment newInstance(){
        return new ZhihuDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_read_layout, container, false);
        setHasOptionsMenu(true);
        initViews(view);
        presenter.start();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_more, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        } else if(id == R.id.menu_more) {
            final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());

            View view = getActivity().getLayoutInflater().inflate(R.layout.reading_actions_sheet, null);
            view.findViewById(R.id.layout_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    presenter.shareAsText();
                }
            });
            view.findViewById(R.id.layout_in_browser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    presenter.openInBrowser();
                }
            });
            dialog.setContentView(view);
            dialog.show();
        }
        return true;
    }

    @Override
    public void setPresenter(ZhihuDetailContract.Presenter presenter) {
        if(presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        imageView = (ImageView) view.findViewById(R.id.zhihu_read_image_view);
        scrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);

        webView = (WebView) view.findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);

        ZhihuDetailActivity detailActivity = (ZhihuDetailActivity) getActivity();
        detailActivity.setSupportActionBar((Toolbar)view.findViewById(R.id.toolbar));
        detailActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        view.findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }

    @Override
    public void showSharingError() {
        Snackbar.make(imageView, R.string.share_error, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void showResult(String result) {
        webView.loadDataWithBaseURL("x-data://base",result,"text/html","utf-8",null);
    }


    @Override
    public void showCover(String url) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .error(R.drawable.placeholder)
                .into(imageView);
    }

    @Override
    public void setTitle(String title) {
        setCollapsingToolbarLayoutTitle(title);
    }

    @Override
    public void setImageMode(boolean showImage) {
        webView.getSettings().setBlockNetworkImage(showImage);
    }

    @Override
    public void showBrowserNotFoundError() {
        Snackbar.make(imageView, R.string.no_browser_found,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showTextCopied() {
        Snackbar.make(imageView, R.string.copied_to_clipboard, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showCopyTextError() {
        Snackbar.make(imageView, R.string.copied_to_clipboard_failed, Snackbar.LENGTH_SHORT).show();
    }

    private void setCollapsingToolbarLayoutTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }
}
