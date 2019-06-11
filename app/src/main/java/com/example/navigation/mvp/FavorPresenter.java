package com.example.navigation.mvp;

import com.example.navigation.App;
import com.example.navigation.model.Movie;
import com.example.navigation.screen.FavorView;

public class FavorPresenter {

    private static final String TAG = "mdbapp";
    private FavorView view;
    private Model model;

    public FavorPresenter(FavorView view) {
        this.view = view;
        this.model = App.getModel();
    }

    public void getAllMovies(){
        model.getAllMovies()
//                .doOnSubscribe(disposable -> view.showProgress())
//                .doAfterTerminate(view::hideProgress)
                .subscribe(movies -> {
                    view.showMovies(movies);
                        },
                        throwable -> view.showMessages(throwable.getMessage()) );
    }

    public void navigateDet(Movie movie){
        view.navigate(movie);
    }
}
