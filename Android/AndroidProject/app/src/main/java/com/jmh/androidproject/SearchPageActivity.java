package com.jmh.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import vo.SearchVO;

public class SearchPageActivity extends AppCompatActivity {
    //bottom bar
    private ChipNavigationBar bottom_bar;

    //search를 위한:
    static EditText search;
    ListView myListView;
    ImageView search_btn;

    ParserSearch parser;
    ArrayList<SearchVO> list;
    SearchVOAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        //-----start Search-------------------------------------------------------------------------------------//
        search = findViewById(R.id.search);
        myListView = findViewById(R.id.recycleview);
        search_btn = findViewById(R.id.search_btn);

        parser = new ParserSearch();

        //parser를 통해 kopis와 통신
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list = new ArrayList<>();

                new KopisAsync().execute("안","녕");

            }
        });
        //-----end Search-------------------------------------------------------------------------------------//

        //-----start bottom bar navigator-------------------------------------------------------------------------------------//
        bottom_bar = findViewById(R.id.bottom_bar);

        bottom_bar.setItemSelected(R.id.search, true);

        bottom_bar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch(i){
                    case R.id.home:
                        // activity = new HomePageActivity();
                        startActivity( new Intent(SearchPageActivity.this, HomePageActivity.class));
                        overridePendingTransition(0,0);
                        break;


                    case R.id.search:
                        //activity = new SearchPageActivity();
                        break;

                    case R.id.heart:
                        //activity = new LikePageActivity();
                        startActivity(new Intent(SearchPageActivity.this,LikePageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.person:
                        //activity = new MyPageActivity();
                        startActivity(new Intent(SearchPageActivity.this, MyPageActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });
        //-----end bottom bar navigator-------------------------------------------------------------------------------------//

    }// onCreate()

    //-----start Search-------------------------------------------------------------------------------------//
    class KopisAsync extends AsyncTask<String, Void, ArrayList<SearchVO>> {

        @Override
        protected ArrayList<SearchVO> doInBackground(String... strings) {
            return parser.connectKopis(list);
        }

        @Override
        protected void onPostExecute(ArrayList<SearchVO> searchVOS) {

            adapter = new SearchVOAdapter(SearchPageActivity.this, R.layout.search_item, searchVOS);
            myListView.setAdapter(adapter);

        }
    }
    //-----end Search-------------------------------------------------------------------------------------//
}