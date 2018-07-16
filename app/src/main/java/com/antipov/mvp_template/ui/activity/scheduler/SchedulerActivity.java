package com.antipov.mvp_template.ui.activity.scheduler;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.antipov.mvp_template.R;
import com.antipov.mvp_template.ui.base.BaseActivity;
import com.antipov.mvp_template.ui.fragment.scheduler.SchedulerFragment;

public class SchedulerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.content, new SchedulerFragment()).commit();
    }

    @Override
    public void getExtras() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scheduler;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.wallpaper_scheduler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scheduler, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
