package com.kacyber.pos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

import com.kacyber.pos.R;
import com.kacyber.pos.adapters.CityAdapter;
import com.kacyber.pos.models.CityModel;
import com.kacyber.pos.recycalView.FastScrollRecyclerViewItemDecoration;
import com.kacyber.pos.ui.base.BaseActivity;

public class CityListActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mTitleView;
    private RecyclerView citiesRecyclerView;
    private CityAdapter cityAdapter;
    private ArrayList<CityModel> cityModelList;
    private String from = "";
    private SearchView searchView;
    private HashMap<String, Integer> mapIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_city_list;
    }

    @Override
    public boolean hideNavigationIcon() {
        return false;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (from.equalsIgnoreCase(getResources().getString(R.string.from)))
            mTitleView.setText(getResources().getString(R.string.from));
        else
            mTitleView.setText(getResources().getString(R.string.to));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getCitiesListFromPrevious();
        initRecyclerView();
    }

    private void getCitiesListFromPrevious() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        cityModelList = (ArrayList<CityModel>) intent.getSerializableExtra("cityList");
        ArrayList<String> myDataset = new ArrayList<String>();

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i=0; i<alphabet.length; i++) {
            myDataset.add(alphabet[i]+" Item");
        }
        mapIndex = calculateIndexesForName(myDataset);
    }

    private HashMap<String, Integer> calculateIndexesForName(ArrayList<String> items){
        HashMap<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i<items.size(); i++){
            String name = items.get(i);
            String index = name.substring(0,1);
            index = index.toUpperCase();

            if (!mapIndex.containsKey(index)) {
                mapIndex.put(index, i);
            }
        }
        return mapIndex;
    }

    private void initRecyclerView() {
        citiesRecyclerView = findViewById(R.id.rv_cities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CityListActivity.this);
        citiesRecyclerView.setLayoutManager(linearLayoutManager);

        cityAdapter = new CityAdapter(CityListActivity.this, cityModelList,this, mapIndex);
        citiesRecyclerView.setAdapter(cityAdapter);
        FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(this);
        citiesRecyclerView.addItemDecoration(decoration);
        citiesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                cityAdapter.getFilter().filter(text);
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
    }

    public void itemClick(CityModel cityModel,int adapterPosition) {
        String cityId = cityModel.getId();
        String cityName = cityModel.getName();
        Intent intent = new Intent();
        intent.putExtra("cityId", cityId);
        intent.putExtra("cityName", cityName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
