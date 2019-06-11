package com.example.navigation.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.navigation.model.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;


@Dao
public interface ResultDao {

    @Query("SELECT * FROM Movie")
    Flowable<List<Movie>> getAll();
//    List<Movie> getAll();

    @Query("SELECT * FROM Movie WHERE id = :id")
    Maybe<Movie> getById(long id);
//    Movie getById(long id);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);
}
