package com.test.android.moviesapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.android.moviesapp.data.MovieObject;
import com.test.android.moviesapp.data.Movies;
import com.test.android.moviesapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieGalleryFragment extends Fragment {

    private final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String DIALOG_RETRY = "DialogRetry";
    private static final int REQUEST_RETRY = 0;

    private int column;

    private URL movieResultsUrl;

    public static Movies sMoviesObjects;
    private List<MovieObject> mItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    //create new instance of the fragment
    public static MovieGalleryFragment newInstance(){
        return new MovieGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            column = 4;
        } else {
            column = 2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_gallery, container, false);

        mRecyclerView = view.findViewById(R.id.fragment_movie_gallery_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), column));

        mLoadingIndicator = view.findViewById(R.id.loading_indicator);


        movieResultsUrl = NetworkUtils.buildUrl();
        new MovieQueryTask().execute(movieResultsUrl);

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        updateUI();
    }

    class ResultHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        MovieObject movieObject;

        TextView mTitleTextView;
        TextView mVoteTextView;
        ImageView mPosterImageView;

        public ResultHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);


            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mVoteTextView = itemView.findViewById(R.id.vote_text_view);
            mPosterImageView = itemView.findViewById(R.id.poster_image_view);

        }

        @Override
        public void onClick(View view){
            Intent intent = PreviewActivity.newIntent(getActivity(), movieObject.getId());
            startActivity(intent);
        }

        public void bindArticle(MovieObject item){
            movieObject = item;

            mTitleTextView.setText(item.getOriginalTitle());
            mVoteTextView.setText((getVote(item)));
            String imageUrl = IMAGE_BASE_URL + item.getPosterPath();
            Picasso.with(getActivity()).load(imageUrl)
                    .placeholder(R.drawable.no_image)
                    .into(mPosterImageView);
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultHolder>{
        private List<MovieObject> mMovies;

        public ResultAdapter(List<MovieObject> results){
            mMovies = results;
        }

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.movie_gallery_item_view, parent, false);
            return new ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position){
            MovieObject listItem = mMovies.get(position);
            holder.bindArticle(listItem);
        }

        @Override
        public int getItemCount(){
            return mMovies.size();
        }
    }

    private void setupAdapter(){
        if(isAdded()){
            mRecyclerView.setAdapter(new ResultAdapter(mItems));
        }
    }

    private void updateUI(){
        if (mRecyclerView.getAdapter() == null){
            setupAdapter();
        } else{
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private String getVote(MovieObject movieObject){
        String stringVote;
        stringVote = String.valueOf(movieObject.getVoteAverage())+"/10("
                +String.valueOf(movieObject.getVoteCount())+")";

        return stringVote;
    }


    /**
     * The class for AsyncTask to execute HTTP connection
     */
    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.bringToFront();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {

            URL moviesUrl = params[0];
            String nhlResults = null;
            try {
                nhlResults = NetworkUtils.getResponseFromHttpUrl(moviesUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return nhlResults;
        }

        @Override
        protected void onPostExecute(String movieResult) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieResult != null && !movieResult.equals("")) {
                sMoviesObjects = Movies.parseJSON(movieResult);
                if (!sMoviesObjects.getMovies().isEmpty()){
                    mItems = sMoviesObjects.getMovies();
                } else {
                    mItems = null;
                }
                updateUI();
                setupAdapter();
            } else {
                onDialog();
            }
        }
    }

    private void onDialog(){
        FragmentManager manager = getFragmentManager();
        RetryDialogFragment dialog = RetryDialogFragment.
                newInstance();
        dialog.setTargetFragment(MovieGalleryFragment.this, REQUEST_RETRY);
        dialog.show(manager, DIALOG_RETRY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == REQUEST_RETRY){
            if (resultCode == Activity.RESULT_OK){
                new MovieQueryTask().execute(movieResultsUrl);
            }
        }
    }

}
