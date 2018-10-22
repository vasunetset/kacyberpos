package com.kacyber.pos.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kacyber.pos.R;
import com.kacyber.pos.adapters.AllServicesAdapter;
import com.kacyber.pos.interfaces.OnSelectCallBack;
import com.kacyber.pos.models.ServicesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllServicesFragment extends DialogFragment {

    @BindView(R.id.servicesRV)
    RecyclerView servicesRV;
    Unbinder unbinder;
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.selectAllTV)
    TextView selectAllTV;

    private LinearLayoutManager linearLayoutManager;
    private ServicesData servicesData;
    private OnSelectCallBack calBack;
    private AllServicesAdapter servicesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
        servicesData = (ServicesData) getArguments().getSerializable("servicesData");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setOnItemSelect(OnSelectCallBack onItemSelect) {
        calBack = onItemSelect;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


         servicesAdapter = new AllServicesAdapter(AllServicesFragment.this, servicesData.list);
        servicesAdapter.setCallBack(calBack);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        servicesRV.setLayoutManager(linearLayoutManager);
        servicesRV.setAdapter(servicesAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cancel, R.id.selectAllTV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.selectAllTV:
                servicesAdapter.selectAll();
                calBack.onSelect(-1, "");
                dismiss();
                break;
        }
    }

    /*@OnClick(R.id.cancel)
    public void onViewClicked() {
        dismiss();
    }


    @OnClick(R.id.selectAllTV)
    public void onViewClicked() {
    }*/
}
