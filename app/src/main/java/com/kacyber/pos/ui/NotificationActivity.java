package com.kacyber.pos.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.kacyber.pos.R;
import com.kacyber.pos.adapters.NotificationAdapter;
import com.kacyber.pos.ui.base.BaseActivity;

public class NotificationActivity extends BaseActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.notificationRV);
        recyclerView.setAdapter(new NotificationAdapter(getApplicationContext(), null, null));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
