package com.jmh.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vo.DetailInfoVO;

public class ListPageActivity extends AppCompatActivity {
    //Tab
    TabLayout tabs;
    ViewPager2 views;
    ListPageAdapter listPageAdapter;
    float v =0;

    //back button
    ImageView back_button;

    FloatingActionButton btn_t;

    ReviewAdapter reviewAdapter;

   /* //진한씨 코딩
    ParserDe parserDe;
    ArrayList<DetailInfoVO> list;

    ImageView img_d;
    TextView txt_d, txt_de, txt_det, txt_detail;
    ImageButton btn_t;*/

    static String bid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar remove
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list_page);


      //진한씨 코딩
        bid = getIntent().getStringExtra("bid");

         /* img_d = findViewById(R.id.img_d);
        txt_d = findViewById(R.id.txt_d);
        txt_de = findViewById(R.id.txt_de);
        txt_det = findViewById(R.id.txt_det);
        txt_detail = findViewById(R.id.txt_detail);
        btn_t = findViewById(R.id.btn_t);

        list = new ArrayList<DetailInfoVO>();
        parserDe = new ParserDe();
        new KopisAsync().execute();*/

//-----TAB START-------------------------------------------------------------------------------------//
        tabs = findViewById(R.id.tabs);
        views = findViewById(R.id.views);

        FragmentManager fm = getSupportFragmentManager();
        listPageAdapter = new ListPageAdapter(fm, getLifecycle());
        views.setAdapter(listPageAdapter);

        tabs.addTab(tabs.newTab().setText("정보 보기"));
        tabs.addTab(tabs.newTab().setText("리뷰 보기"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                views.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        views.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabs.selectTab(tabs.getTabAt(position));
            }
        });

        tabs.setTranslationY(300);

        tabs.setAlpha(v);

        tabs.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();


        //back button
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListPageActivity.this, HomePageActivity.class);
                startActivity(i);
            }

            });

    }//oncreate()

    /*//진한씨 코딩
    //api 불러오기
    class KopisAsync extends AsyncTask<String, Void, ArrayList<DetailInfoVO>> {

        @Override
        protected ArrayList<DetailInfoVO> doInBackground(String... strings) {
            return parserDe.connectKopis(list, bid);
        }

        @Override
        protected void onPostExecute(ArrayList<DetailInfoVO> infoVOS) {
            Log.i("MY", "detail size : " + infoVOS.size());

            new ImageAsync(img_d).execute(infoVOS.get(0).getImg());
            txt_d.setText(infoVOS.get(0).getTitle());
            txt_de.setText(infoVOS.get(0).getScreen_date());
            txt_det.setText(infoVOS.get(0).getCast());
            txt_detail.setText(infoVOS.get(0).getScreen_time());

            btn_t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                }
            });

            String result = "bid="+infoVOS.get(0).getId()+"&title="+infoVOS.get(0).getTitle()+"&s_period="
                    +infoVOS.get(0).getS_period()+"&e_period="+infoVOS.get(0).getE_period()+"&cast="
                    +infoVOS.get(0).getCast()+"&production="+infoVOS.get(0).getProduction()+"&screen_date="
                    +infoVOS.get(0).getScreen_date()+ "&screen_time="+infoVOS.get(0).getScreen_time()+"&limit_age="
                    +infoVOS.get(0).getLimit_age()+"&company="+infoVOS.get(0).getCompany()+"&price="
                    +infoVOS.get(0).getPrice()+"&img="+infoVOS.get(0).getImg();
            new Task().execute(result, "type_insert");
        }
    }

    //DB에 저장
    class Task extends AsyncTask<String, Void, String>{

        public String ip = "172.30.1.11";
        String sendMsg, receiveMsg;
        String serverip = "http://"+ip+":9090/controller/insert.do";

        @Override
        protected String doInBackground(String... strings) {

            String str = "";
            try {
                URL url = new URL(serverip);

                //위의 경로로 서버 접속
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //osw라는 아웃풋 스트림을 통해 스프링 서버로 파라미터 전달
                //regi.do?
                sendMsg = strings[0] + "&type="+strings[1];

                //실제로 서버에 값을 전송
                osw.write(sendMsg);
                osw.flush(); //물리적으로 완료했음을 명시하는 메서드

                //서버통신이 완료되면 결과를 얻기위한 영역
                //getResponseCode() : 200이면 정상
                if(conn.getResponseCode() == conn.HTTP_OK){
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);

                    //서버에서 넘겨준 JSON형식의 데이터를 받는다
                    receiveMsg = reader.readLine();

                    //JSON 형태의 배열을 받는 클래스
                    //{res:[{'result' : 'success'}]}
                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");

                    //jarray : [{'result' : 'success'}]
                    JSONObject jObject = jarray.getJSONObject(0);

                    //jObject: {'result' : 'success'}
                    str = jObject.optString("result");
                }
            }catch (Exception e){

            }

            return str; //통신이 끝나면 onPostExecute()메서드로 넘어간다
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equalsIgnoreCase("success")){

            }else{

            }
        }
    }*/
}