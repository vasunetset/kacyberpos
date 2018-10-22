package com.kacyber.pos.ui.fragment;

import android.os.Bundle;

import com.kacyber.pos.ui.base.BaseFragment;


/**
 * Created by MZY on 2017/9/28.
 */

public class UsedFragment extends BaseFragment {

    public static UsedFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UsedFragment fragment = new UsedFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayoutResID() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
