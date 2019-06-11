package com.example.navigation;

//
//import android.app.Fragment;
//import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigation.model.MdbAdapter;
import com.example.navigation.model.Movie;
import com.example.navigation.mvp.FavorPresenter;
import com.example.navigation.screen.FavorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavorFragment extends Fragment implements  OnListFragmentInteractionListener, FavorView {

    RecyclerView recyclerView;
    List<Movie> movies;
    private FavorPresenter presenter;
    MdbAdapter adapter;
    private ProgressDialog progressDialog;

    public FavorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_item_list, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Favorites");
        movies = new ArrayList<>();

        recyclerView = v.findViewById(R.id.list);;
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MdbAdapter(this, movies);
        recyclerView.setAdapter(adapter);
        presenter=new FavorPresenter(this);
        presenter.getAllMovies();
        return v;
    }

    @Override
    public void onListFragmentInteraction(Movie movie) {

        presenter.navigateDet(movie);
    }

    @Override
    public void showMessages(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMovies(List<Movie> movies) {

        adapter.setData(movies);
    }

    @Override
    public void navigate(Movie movie) {
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
}
