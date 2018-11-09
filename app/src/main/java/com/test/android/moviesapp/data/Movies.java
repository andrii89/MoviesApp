package com.test.android.moviesapp.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    List<MovieObject> results;

    public Movies(){
        results = new ArrayList<>();
    }

    public static Movies parseJSON(String movies) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(movies, Movies.class);
    }

    public List<MovieObject> getMovies() {
        return results;
    }

    public MovieObject getMovieObject(int id){
        for (MovieObject item : results){
            if (item.getId() == (id)){
                return item;
            }
        }
        return null;
    }

}
