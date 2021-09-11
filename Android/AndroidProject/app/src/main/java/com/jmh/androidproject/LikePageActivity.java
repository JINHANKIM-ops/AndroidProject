package com.jmh.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class LikePageActivity extends AppCompatActivity {

    private ChipNavigationBar bottom_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar remove
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_like_page);

        bottom_bar = findViewById(R.id.bottom_bar);

        bottom_bar.setItemSelected(R.id.heart, true);

        bottom_bar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {



                switch(i){
                    case R.id.home:
                        // activity = new HomePageActivity();
                        startActivity( new Intent(LikePageActivity.this, HomePageActivity.class));
                        overridePendingTransition(0,0);
                        break;


                    case R.id.search:
                        //activity = new SearchPageActivity();
                        startActivity(new Intent(LikePageActivity.this, SearchPageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.heart:
                        //activity = new LikePageActivity();
                        break;

                    case R.id.person:
                        //activity = new MyPageActivity();
                        startActivity(new Intent(LikePageActivity.this,MyPageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                }

            }
        });



    }
}