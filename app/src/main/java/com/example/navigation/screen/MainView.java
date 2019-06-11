package com.example.navigation.screen;

import com.example.navigation.model.Movie;

import java.util.List;


public interface MainView {
    void showMessages(String msg);

    void showProgress();
    void hideProgress();



    void showMovies(List<Movie> moviesl);


    void navigateDetail(Movie movie);

    void navigateFavor(int itemId);
}
