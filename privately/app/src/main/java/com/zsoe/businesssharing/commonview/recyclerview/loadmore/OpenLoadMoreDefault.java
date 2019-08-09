package com.zsoe.businesssharing.commonview.recyclerview.loadmore;

import android.content.Context;
import android.view.View;

import java.util.HashMap;
import java.util.List;

/**
 * T pageable里的bean
 * V 返回的数据列表
 * 2006/8/00 by onion
 */
public class OpenLoadMoreDefault<V> implements LoadMoreContainer {

    private LoadMoreHandler mLoadMoreHandler;

    private boolean mIsLoading;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;
    //考虑与服务端对接，分页参数
    public HashMap<String, String> pagerAble;
    //考虑Presenter处理数据，保留数据
    public List<V> datas;
    //标示目前是缓存数据
    private boolean isCache;
    private int page = 1;
    public static final int pageNum = 10;
    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;
    private LoadMoreUIHandler mLoadMoreUIHandler;
    private View mFooterView;
    private Context context;

    public OpenLoadMoreDefault(Context context, List<V> list) {
        this.context = context;
        datas = list;
        pagerAble = new HashMap<>();
        pagerAble.put("page", String.valueOf(page));
        pagerAble.put("pageNum", String.valueOf(pageNum));
        useDefaultFooter();

    }

    public OpenLoadMoreDefault(Context context, List<V> list, int showEmpty) {
        this.context = context;
        datas = list;
        pagerAble = new HashMap<>();
        pagerAble.put("page", String.valueOf(page));
        pagerAble.put("pageNum", String.valueOf(pageNum));
        useDefaultFooter(showEmpty);

    }

    @Override
    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setOnScrollListener() {
        onReachBottom();
    }

    @Override
    public void setLoadMoreView(View view) {
//        if (mFooterView != null && mFooterView != view) {
//            removeFooterView(view);
//        }
        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });

    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    //    public void loadMoreFinish(boolean hasMore) {
//
//        loadMoreFinish(false,hasMore);
//    }
//    public void loadMoreFinish(int num) {
//        loadMoreFinish(false,num>=pagerAble.size);
//    }
    //
    public void loadMoreFinish(List<V> list) {
        if (isCache) {
            datas.clear();
            isCache = false;
        }
        if (list != null) {
            datas.addAll(list);
            loadMoreFinish(datas.isEmpty(), list.size() >= pageNum);
        } else {
            loadMoreFinish(datas.isEmpty(), false);
        }
    }

    public void loadCache(List<V> list) {
        datas.addAll(list);
        isCache = true;
        pagerAble.put("page", "1");
    }

    @Override
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (hasMore) {
            page++;
            pagerAble.put("page", String.valueOf(page));
        }

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }
    }

    @Override
    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        mHasMore = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    public void loadMoreError() {
        mIsLoading = false;
        mLoadError = true;
        mHasMore = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, 99, "网络异常,请重新加载");
        }
    }

    @Override
    public int getPageNum() {
        return page;
    }

    /**
     * 在外部重新对page赋值
     */
    public void setNextPage(int currentPage) {
        //currentPage是当前页码，要设置下一页的页码，在此+1
        page = currentPage + 1;
        pagerAble.put("page", String.valueOf(page));
    }

    public void refresh() {
        page = 1;
        pagerAble.put("page", String.valueOf(page));
    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }

        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

    private void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

    public void useDefaultFooter() {
        LoadMoreDefaultFooterRecyclerView footerView = new LoadMoreDefaultFooterRecyclerView(context);
        footerView.setVisibility(View.GONE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }

    public void useDefaultFooter(int showEmpty) {
        LoadMoreDefaultFooterRecyclerView footerView = new LoadMoreDefaultFooterRecyclerView(context, showEmpty);
        footerView.setVisibility(View.GONE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }


    @Override
    public View getFooterView() {
        return mFooterView;
    }

    //检测页码，并清理数据
    public void fixNumAndClear() {
        if (page == 1 && datas != null) {
            datas.clear();
        }
    }

}
