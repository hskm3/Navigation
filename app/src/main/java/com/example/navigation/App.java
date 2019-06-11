package com.example.navigation;

import android.app.Application;
import android.arch.persistence.room.Room;


import com.example.navigation.db.AppDatabase;
import com.example.navigation.mvp.MainPresenter;
import com.example.navigation.mvp.Model;
import com.example.navigation.network.MdbApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App instance;

    private static MdbApi mdbApi;
    private static AppDatabase database;
    private static MainPresenter presenter;
    private static Model model;
//    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mdbApi = retrofit.create(MdbApi.class);

        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
        model=new Model();

        presenter=new MainPresenter();

//        cicerone=Cicerone.create();

    }


    public static App getInstance() {
        return instance;
    }

    public static MdbApi getApi() {
        return mdbApi;
    }
    public static AppDatabase getDatabase() {
        return database;
    }
    public static MainPresenter getPresenter() {
        return presenter;
    }
    public static Model getModel() {
        return model;
    }

//    public NavigatorHolder getNavigatorHolder() {
//        return cicerone.getNavigatorHolder();
//    }
//
//    public  Router getRouter() {
//        return cicerone.getRouter();
//    }

}
