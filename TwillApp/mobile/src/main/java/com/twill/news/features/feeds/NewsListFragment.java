package com.twill.news.features.feeds;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twill.news.R;
import com.twill.news.data.local.dao.ArticlesDao;
import com.twill.news.data.local.dao.SourcesDao;
import com.twill.news.data.local.entity.Sources;
import com.twill.news.data.remote.NewsAPIDBService;
import com.twill.news.data.remote.NewsAPIRepository;
import com.twill.news.databinding.FragmentNewssourceListBinding;
import com.twill.news.features.detail.NewsListActivity;

/**
 *  Created by Anil on 6/7/2017.
 */

public class NewsListFragment extends LifecycleFragment implements NewsListCallback {

//    @Inject
//    NewsSourcesListViewModel newsSourcesListViewModel;
    private NewsSourcesListViewModel newsSourcesListViewModel;
    private NewsAPIRepository newsAPIRepository;

    FragmentNewssourceListBinding binding;
    private ArticlesDao articlesDao;
    private SourcesDao sourcesDao;
    private NewsAPIDBService newsAPIDBService;

    public NewsListFragment() {
    }

    public static NewsListFragment newInstance() {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AndroidSupportInjection.inject(this);
//        newsSourcesListViewModel = new NewsSourcesListViewModel(newsAPIRepository);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewssourceListBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.recyclerView.setAdapter(new NewsListAdapter(this));
       /* binding.recyclerView.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {

                ((HomeActivity)getActivity()).getBinding().navigation.animate().setDuration(200).alphaBy(100).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((HomeActivity)getActivity()).getBinding().navigation.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onShow() {
                ((HomeActivity)getActivity()).getBinding().navigation.animate().setDuration(200).translationY(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((HomeActivity)getActivity()).getBinding().navigation.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onScrolled() {
                // To load more data
            }
        });*/
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*        final ArticlesDao articlesDao = null;
        final SourcesDao sourcesDao = null;
        final NewsAPIDBService newsAPIDBService = null;*/

        newsAPIRepository = new NewsAPIRepository(articlesDao,sourcesDao,newsAPIDBService);

                newsSourcesListViewModel = new NewsSourcesListViewModel(newsAPIRepository);

                newsSourcesListViewModel
                        .getNewsSources()
                        .observe(this, listResource -> binding.setResource(listResource));
    }


    @Override
    public void onNewsSourceClicked(Sources sources, View sharedView) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), sharedView, getString(R.string.shared_image));
         startActivity(NewsListActivity.newIntent(getActivity(), sources.getId()), options.toBundle());
    }
}
