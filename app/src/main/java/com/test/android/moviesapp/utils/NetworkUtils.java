package com.test.android.moviesapp.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    private final static String MOVIE_QUERY_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String API_KEY = "396a8d3ff98916d09cb9d37d0905b9e0";


    /**
     * Builds the URL used to query api.
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(MOVIE_QUERY_URL).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the result from the HTTP response.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            if(urlConnection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException(urlConnection.getResponseMessage() + ": with " + url);
            }

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
