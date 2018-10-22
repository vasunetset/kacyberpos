package com.kacyber.pos.ui.fragment;

import android.os.Bundle;

import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseFragment;


/**
 * Created by MZY on 2017/9/28.
 */

public class ExpiredFragment extends BaseFragment {

    public static ExpiredFragment newInstance() {

        Bundle args = new Bundle();

        ExpiredFragment fragment = new ExpiredFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_expired;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
