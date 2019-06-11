package com.example.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigation.model.MdbAdapter;
import com.example.navigation.model.Movie;
import com.example.navigation.mvp.MainPresenter;
import com.example.navigation.screen.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment implements MainView, OnListFragmentInteractionListener {

    private MdbAdapter adapter;
    private MainPresenter presenter;
    private LinearLayoutManager layoutManager;
    public static final String TAG="frga";
    private ProgressBar progressBar;
    private TextView textView;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        presenter = App.getPresenter();
        Log.i(TAG,"onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Search Movie");

        textView=view.findViewById(R.id.textNoInet);
        progressBar=view.findViewById(R.id.progressBar);
        List<Movie> movies =new ArrayList<>();
        adapter = new MdbAdapter(this, movies);
        layoutManager = new LinearLayoutManager(view.getContext());

        presenter.attachView(this);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                presenter.onScrolledRecyclerView(visibleItemCount, totalItemCount, firstVisibleItem);
            }
        });

        presenter.showMovies();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this );
        Log.i(TAG,"onStart "+ presenter.getMovies().size());
    }

    @Override
    public void onStop() {
        presenter.detachView();
        Log.i(TAG,"onStop "+ presenter.getMovies().size());
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQuery(presenter.getQuery(), false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                presenter.onQueryTextSubmit(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionsItemSelected(item.getItemId());
        return true;
    }


    @Override
    public void showMessages(String msg) {

        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
        textView.setText(msg);
        textView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showMovies(List<Movie> moviesList) {

        textView.setVisibility(View.INVISIBLE);
        adapter.setData(moviesList);

    }

    @Override
    public void navigateDetail(Movie movie) {

//        Fragment fr = new DetailFragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("movie", movie);
//        fr.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        fm.beginTransaction()
                .replace(R.id.frag_container, DetailFragment.newInstance(movie))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigateFavor(int itemId) {

        switch (itemId) {
            case R.id.action_fav:

                Fragment fr = new FavorFragment();
                FragmentManager fm = getFragmentManager();
                assert fm != null;
                fm.beginTransaction()
                        .replace(R.id.frag_container, fr)
                        .addToBackStack(null)
                        .commit();

            default:
        }
    }

    @Override
    public void onListFragmentInteraction(Movie movie) {
        navigateDetail(movie);
    }


}
