package com.example.navigation.mvp;


import android.util.Log;


import com.example.navigation.App;
import com.example.navigation.model.Movie;
import com.example.navigation.screen.DetailView;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class DetailPresenter {

    private static final String TAG = "mdbapp";
    private DetailView view;
    private Model model;
    private Movie movie;

    public DetailPresenter(DetailView view, Movie movie) {
        this.view = view;
        this.model = App.getModel();
        this.movie=movie;
    }

    public void setOnFavBtn(int id, Movie movie){
        model.getMovieById(id)
                .subscribe(movie1 -> {
                    if (movie1.getFav() == 1) {
                        movie.setFav(1);
                        view.setOnFavBtn();
                    }
                });
    }

    public void onClickFavBtn() {
        if(movie.getFav()!=1) {
            addFavMovie(movie);
            view.setOnFavBtn();
        } else {
            delFavMovie(movie);
            view.setOffFavBtn();

        }
    }


    private void delFavMovie(Movie movie){

        model.delFavMovie(movie)
                .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Log.i(TAG,"db delete movie");
                                    view.showMessages("delete movie from favorites");
//                                    Toast.makeText(view,"delete movie from favorites",Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i(TAG,e.getMessage());
                                    view.showMessages(e.getMessage());

                                }
                            });
    }

    private void addFavMovie(Movie movie) {

        movie.setFav(1);
        model.addFavMovie(movie)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "db insert movie");
                        view.showMessages("add movie to favorites");
//                        Toast.makeText(view,"add movie to favorites",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, e.getMessage());
                        view.showMessages(e.getMessage());
//                        Toast.makeText(view,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}
