package com.test.android.moviesapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.android.moviesapp.data.MovieObject;

import static com.test.android.moviesapp.MovieGalleryFragment.sMoviesObjects;

public class PreviewFragment extends Fragment {

    private final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private MovieObject movieObject;

    private ImageView mItemImageView;
    private TextView mTitleTextView;
    private TextView mPreviewTextView;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        int itemId = (int) getActivity().getIntent().getSerializableExtra(PreviewActivity.EXTRA_ITEM_ID);
        movieObject = sMoviesObjects.getMovieObject(itemId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detailed_view,container, false);

        mItemImageView = view.findViewById(R.id.imageview);
        mItemImageView.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
        String imageUrl = IMAGE_BASE_URL + movieObject.getPosterPath();
        Picasso.with(getActivity()).load(imageUrl)
                .placeholder(R.drawable.no_image)
                .into(mItemImageView);

        mTitleTextView = view.findViewById(R.id.title_textview);
        mTitleTextView.setText(movieObject.getOriginalTitle());
        mPreviewTextView = view.findViewById(R.id.preview_textview);
        mPreviewTextView.setText(movieObject.getOverview());

        return view;
    }
}
