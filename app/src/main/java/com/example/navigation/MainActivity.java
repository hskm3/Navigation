package com.example.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import androidx.appcompat.app.AppCompatActivity;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.pure.AppNavigator;


public class MainActivity extends AppCompatActivity {

    Router router;
    NavigatorHolder navigatorHolder;
    private Navigator navigator=new AppNavigator(this,-1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

//        router=App.getInstance().getRouter();
//        navigatorHolder=App.getInstance().getNavigatorHolder();

//        router.navigateTo(new Screens.MainScreen());

//        Button button=findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                router.navigateTo(new Screens.MainScreen());
//            }
//        });
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frag_container);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.frag_container, fragment)
//                    .addToBackStack(null)
                    .commit();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        navigatorHolder.setNavigator(navigator);
//    }
//
//    @Override
//    protected void onPause() {
//        navigatorHolder.removeNavigator();
//        super.onPause();
//    }

//    @Override
//    public void onBackPressed() {
//
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }
//
//    }


}
