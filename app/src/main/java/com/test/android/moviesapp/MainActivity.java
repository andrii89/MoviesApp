package com.test.android.moviesapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment resultFragment = MovieGalleryFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.movie_gallery_fragment_container, resultFragment)
                .commit();
    }
}
