package com.example.l_5411.boread.detail.douban;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.l_5411.boread.R;
import com.example.l_5411.boread.adapter.CastAdapter;
import com.example.l_5411.boread.bean.DoubanMovieBean;
import com.example.l_5411.boread.interfaces.OnRecyclerViewOnClickListener;
import com.example.l_5411.boread.widget.MyNestedScrollView;

/**
 * Created by L_5411 on 2017/5/21.
 */

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View{

    private MovieDetailContract.Presenter presenter;
    private Context context;

    private CollapsingToolbarLayout toolbarLayout;
    private MyNestedScrollView scrollView;
    private ImageView imageView;

    private TextView movieNameText;
    private TextView directorsText;
    private TextView yearText;
    private TextView countryText;
    private TextView genresText;
    private TextView rateText;

    private TextView descriptionText;

    private RecyclerView castRecyclerView;


    public static MovieDetailFragment newInstance() {
        return new MovieDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_read_layout, container, false);
        initViews(view);
        presenter.start();
        return view;
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        if(presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        imageView = (ImageView) view.findViewById(R.id.movie_read_image_view);
        scrollView = (MyNestedScrollView) view.findViewById(R.id.scroll_view);

        movieNameText = (TextView) view.findViewById(R.id.movie_name);
        directorsText = (TextView) view.findViewById(R.id.movie_directors);
        yearText = (TextView) view.findViewById(R.id.movie_year);
        countryText = (TextView) view.findViewById(R.id.movie_countries);
        genresText = (TextView) view.findViewById(R.id.movie_genres);
        rateText = (TextView) view.findViewById(R.id.movie_rating);

        descriptionText = (TextView) view.findViewById(R.id.movie_description);

        castRecyclerView = (RecyclerView) view.findViewById(R.id.cast_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        castRecyclerView.setLayoutManager(manager);
        castRecyclerView.setFocusable(false);

        MovieDetailActivity activity = (MovieDetailActivity) getActivity();
        activity.setSupportActionBar((Toolbar)view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        view.findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }

    @Override
    public void setTitle() {
        setCollapsingToolbarLayoutTitle();
    }

    @Override
    public void showCover(String url) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.placeholder)
                .into(imageView);
    }

    @Override
    public void showMovie(DoubanMovieBean movie) {
        movieNameText.setText(movie.getTitle());

        StringBuffer directors = new StringBuffer();
        for(int i = 0; i < movie.getDirectors().size(); i++) {
            directors.append(movie.getDirectors().get(i).getName());
            if(i < movie.getDirectors().size() - 1) {
                directors.append(" / ");
            }
        }
        directorsText.setText(directors);

        yearText.setText(movie.getYear());

        StringBuffer countries = new StringBuffer();
        for(int i = 0; i < movie.getCountries().size(); i++) {
            countries.append(movie.getCountries().get(i));
            if(i < movie.getCountries().size() - 1) {
                countries.append(" / ");
            }
        }
        countryText.setText(countries);

        StringBuffer genres = new StringBuffer();
        for(int i = 0; i < movie.getGenres().size(); i++) {
            genres.append(movie.getGenres().get(i));
            if(i < movie.getGenres().size() - 1) {
                genres.append(" / ");
            }
        }
        genresText.setText(genres);
        rateText.setText(movie.getRating().getAverage() + " / 10");
        descriptionText.setText(movie.getSummary());

        CastAdapter adapter = new CastAdapter(context, movie);
        adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        castRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoadError() {
        Snackbar.make(getView(), R.string.loaded_failed, Snackbar.LENGTH_INDEFINITE).show();
    }

    private void setCollapsingToolbarLayoutTitle() {
        toolbarLayout.setTitle(getString(R.string.movie_details));
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        toolbarLayout.setTitleEnabled(true);
//        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
//        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }
}
