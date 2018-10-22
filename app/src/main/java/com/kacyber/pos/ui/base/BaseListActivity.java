package com.kacyber.pos.ui.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import com.kacyber.pos.R;

public class BaseListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_list;
    }

    @Override
    public boolean hideNavigationIcon() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
