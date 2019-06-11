package com.example.navigation.mvp;

import android.util.Log;

import com.example.navigation.App;
import com.example.navigation.model.Movie;
import com.example.navigation.screen.MainView;

import java.util.ArrayList;
import java.util.List;

import static com.example.navigation.MainFragment.TAG;

public class MainPresenter {

    private static final String STUB_QUERY = "Fight";
    private static final int VISIBLE_THRESHOLD = 5;
    private int previousTotal;
    private MainView view;
    private Model model;
    private List<Movie> movies;
    private String query;
    private int page;
    private boolean loading;

    public String getQuery() {
        return query;
    }

    public List<Movie> getMovies() {
        return movies;
    }


    public MainPresenter() {
        model = App.getModel();
        movies=new ArrayList<>();
        query=STUB_QUERY;
        page=1;
        previousTotal=0;
        loading=true;
    }

    public void attachView(MainView mainView) {
        view = mainView;
    }

    public void detachView() {
        view = null;
    }

    private void loadMovies(boolean isScroll) {

        if(!isScroll){
            page=1;
            previousTotal=0;
        }
        if(model.isOnline()) {
            model.loadMovies(query,page)
                    .doOnSubscribe(disposable -> view.showProgress())
                    .doAfterTerminate(view::hideProgress)
                    .subscribe(movies1 -> {
                                if(!isScroll){
                                    this.movies=movies1;
                                }else {
                                    this.movies.addAll(movies1);
                                }
                                view.showMovies(this.movies);
                            }
                            ,
                            throwable -> {
                                view.showMessages(throwable.getMessage());
                                Log.i("mdb","load err");

                            }
                    );
        }else {
            view.showMessages("No internet connection");
        }
    }



    public void showMovies(){
        if (movies.size() == 0) {
            loadMovies(false);
        } else {
            restoreMovies();
        }
    }

    private void restoreMovies(){
        view.showMovies(movies);
    }

    public void onScrolledRecyclerView(int visibleItemCount, int totalItemCount, int firstVisibleItem) {

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {

            page++;
            loadMovies(true);
            loading = true;
        }
    }

    public void onOptionsItemSelected(int itemId) {
        view.navigateFavor(itemId);
    }

    public void onQueryTextSubmit(String s) {

        query=s;
        loadMovies(false);
    }
}
