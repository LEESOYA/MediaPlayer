package com.example.jangbi.myapplication;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        String ActivityName = i.getStringExtra("activity");
        try {
            Log.e("ActivityName", ActivityName);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("ActivityName", "null");

        }
        if (ActivityName == null)
            ActivityName = "ImageList";


        TabHost mTab = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this, audio.class);
        spec = mTab.newTabSpec("tab1").setIndicator("음악").setContent(intent);
        mTab.addTab(spec);

        intent = new Intent(this, Movie.class);
        spec = mTab.newTabSpec("tab2").setIndicator("동영상").setContent(intent);
        mTab.addTab(spec);

        mTab.addTab(mTab.newTabSpec("tab3").setIndicator("사진").setContent(new Intent(ActivityName)));
    }
}