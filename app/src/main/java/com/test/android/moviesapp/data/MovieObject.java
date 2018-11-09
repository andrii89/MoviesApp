package com.test.android.moviesapp.data;

import com.google.gson.annotations.SerializedName;

public class MovieObject {

    @SerializedName("poster_path")
    String posterPath;

    String overview;

    int id;

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("vote_count")
    int voteCount;

    @SerializedName("vote_average")
    double voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
