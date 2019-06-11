package com.example.navigation.screen;

import com.example.navigation.model.Movie;
import java.util.List;


public interface FavorView {

    void showMessages(String msg);

    void showProgress();
    void hideProgress();


    void showMovies(List<Movie> movies);


    void navigate(Movie movie);

}
