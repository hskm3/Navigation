package com.example.navigation.mvp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.navigation.App;
import com.example.navigation.model.MdbResponse;
import com.example.navigation.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class Model  {

    private static final String API_KEY ="22e3f7dd60ebd88242ad20b2353bb229";

    Observable<List<Movie>> loadMovies(String query, int page) {

        return App.getApi().getData(API_KEY, query,page)
                .map(MdbResponse::getResults)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;

    }

    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    Flowable<List<Movie>> getAllMovies(){
        return App.getInstance().getDatabase().resultDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    Maybe<Movie> getMovieById(long id){
        return
        App.getInstance().getDatabase()
                .resultDao().getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    Completable addFavMovie(Movie movie){

        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                App.getInstance().getDatabase().resultDao().insert(movie);
            }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Completable delFavMovie(Movie movie){

        return Completable.fromAction(new Action() {
                        @Override
                        public void run() throws Exception {
                            App.getInstance().getDatabase().resultDao().delete(movie);
                        }
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
    }


}
