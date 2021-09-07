package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.FindStallionFragments.ViewPagerTabLayoutDisplayStallion;
import com.example.myapplication.LoginFragments.LoginMainScreen;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_login, new LoginMainScreen(), null)
                    .commit();
        }
    }
}