package com.test.android.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class PreviewActivity extends FragmentActivity {

    public static final String EXTRA_ITEM_ID = "item_id";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new PreviewFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context packageContext, int itemId){
        Intent intent = new Intent(packageContext, PreviewActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }
}
