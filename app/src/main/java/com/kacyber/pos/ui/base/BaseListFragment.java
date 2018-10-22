package com.kacyber.pos.ui.base;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import com.kacyber.pos.R;

import com.kacyber.pos.util.common.SwipeRefreshHelper;
import com.kacyber.pos.util.decoration.DividerDecoration;

/**
 * Fragment基类
 * Created by mzy on 2017/6/2.
 */

public abstract class BaseListFragment<T> extends BaseFragment {

    /**
     * 正常列表
     */
    public static final int LIST_TYPE_NORMAL = 1;
    /**
     * 搜索列表
     */
    public static final int LIST_TYPE_SEARCH = 2;
    /**
     * 列表类型。包含{@link #LIST_TYPE_NORMAL}和{@link #LIST_TYPE_SEARCH}
     */
    @IntDef({LIST_TYPE_NORMAL, LIST_TYPE_SEARCH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ListType {}

    /**
     * 列表类型。默认正常列表
     */
    protected @ListType int listType = LIST_TYPE_NORMAL;

    /**
     * 加载第一页
     */
    protected static final int LOAD_TYPE_FIRST = 1;
    /**
     * 加载下一页
     */
    protected static final int LOAD_TYPE_NEXT = 2;
    /**
     * 加载类型
     */
    @IntDef({LOAD_TYPE_FIRST, LOAD_TYPE_NEXT})
    @Retention(RetentionPolicy.SOURCE)
    protected @interface LoadType {}

    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    public BaseQuickAdapter<T, BaseViewHolder> mAdapter;

    protected SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData(LOAD_TYPE_FIRST);
        }
    };

    protected BaseQuickAdapter.RequestLoadMoreListener onLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            loadData(LOAD_TYPE_NEXT);
        }
    };

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            onListItemClick(adapter, view, position);
        }
    };

    @Override
    protected int getLayoutResID() {
        return R.layout.common_page_list;
    }

    /**
     * 是否可以下拉刷新，默认可以
     * @return
     */
    public boolean enableRefresh() {
        return true;
    }

    /**
     * 是否可以加载，默认可以
     * @return
     */
    public boolean enableLoadMore() {
        return true;
    }

    /**
     * 是否取消未满页的LoadMore，默认取消
     * @return
     */
    public boolean disableLoadMoreIfNotFullPage() {
        return true;
    }

    /**
     * 获取LayoutManager，默认使用LinearLayoutManager
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    /**
     * 是否显示分割线，默认显示
     * @return
     */
    public boolean showDividerDecoration() {
        return true;
    }

    /**
     * 是否显示分割线，默认显示
     * @return
     */
    public DividerDecoration getDividerDecoration() {
        return new DividerDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (showDividerDecoration()) {
            mRecyclerView.addItemDecoration(getDividerDecoration());
        }
        mRecyclerView.setLayoutManager(getLayoutManager());
        SwipeRefreshHelper.enableRefresh(mRefreshLayout, enableRefresh());
        SwipeRefreshHelper.init(mRefreshLayout, onRefreshListener);

        mAdapter = getAdapter();
        mAdapter.setEnableLoadMore(enableLoadMore());
        mAdapter.setOnItemClickListener(onItemClickListener);
        mAdapter.setOnLoadMoreListener(onLoadMoreListener, mRecyclerView);
        if (disableLoadMoreIfNotFullPage()) {// 必须在setOnItemClickListener之后
            mAdapter.disableLoadMoreIfNotFullPage();
        }
        mRecyclerView.setAdapter(mAdapter);

        loadData(LOAD_TYPE_FIRST);
    }

    protected abstract BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    protected abstract void onListItemClick(BaseQuickAdapter adapter, View view, int position);

    protected abstract void loadData(@LoadType int loadType);
}
