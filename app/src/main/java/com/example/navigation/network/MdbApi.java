package com.example.navigation.network;

import com.example.navigation.model.MdbResponse;
import com.example.navigation.model.Movie;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MdbApi {

    @GET("search/movie")
//    Call<MdbResponse> getData(@Query("api_key") String apiKey, @Query("query") String query);
    Observable<MdbResponse> getData(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<Movie> getDataM(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
