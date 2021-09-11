package com.jmh.androidproject;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vo.ReservationVO;

public class MyPageActivity extends AppCompatActivity {

    //bottom bar
    private ChipNavigationBar bottom_bar;

    //내 예약 확인 dialog
    Dialog dialog;
    Button btn_ticket;
    TextView res_title, res_date, res_time;
    static ArrayList<ReservationVO> list;

    //민수씨 코드
    private boolean saveLoginData;
    Button btn_logout, btn_sub, btn_bell,
            btn_q1,btn_q2,btn_q3,btn_q4,txt_logout;
    TextView txt_perform,txt_artist,txt_hall, txt_part,user_name,text_inform;

    String shared = "file";
    SharedPreferences pref;

    Intent i= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar remove
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_page);

        //-----start bottom bar navigator-------------------------------------------------------------------------------------//
        bottom_bar = findViewById(R.id.bottom_bar);

        bottom_bar.setItemSelected(R.id.person, true);

        bottom_bar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch(i){
                    case R.id.home:
                        // activity = new HomePageActivity();
                        startActivity( new Intent(MyPageActivity.this, HomePageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.search:
                        //activity = new SearchPageActivity();
                        startActivity(new Intent(MyPageActivity.this, SearchPageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.heart:
                        //activity = new LikePageActivity();
                        startActivity(new Intent(MyPageActivity.this,LikePageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.person:
                        //activity = new MyPageActivity();
                        break;
                }
            }
        });

        //-----end bottom bar navigator-------------------------------------------------------------------------------------//

        //----민수씨 코드

        btn_logout = findViewById(R.id.btn_logout);
        text_inform = findViewById(R.id.text_inform);

        i = getIntent();

        if(i.getStringExtra("login") != null) {
            Log.i("M", i.getStringExtra("login"));
        }

        if(i.getStringExtra("login") != null ){
            Log.i("M", "res:"+i.getStringExtra("login"));

            if(i.getStringExtra("login").equals("ok")) {
                btn_logout.setText("로그아웃");
            }
        }

        try {

            String email = i.getStringExtra("message");
            text_inform.setText(email);

        }catch (Exception e){

            pref = getSharedPreferences(shared,0);
            String value = pref.getString("u_email","");
            text_inform.setText(value);

        }

        //구독 버튼
        /*btn_sub = findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPageActivity.this, MySubscribeActivity.class));
            }
        });*/

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( btn_logout.getText().toString().equals("로그인") ) {
                    Intent logoutIntent = new Intent(MyPageActivity.this, Login_SignupActivity.class);
                    startActivity(logoutIntent);
                    finish();
                }else{

                    i = null;
                    btn_logout.setText("로그인");

                    Intent logoutIntent = new Intent(MyPageActivity.this,Login_SignupActivity.class);
                    startActivity(logoutIntent);
                    finish();
                }


            }
        });

        //reserve search
        pref = getSharedPreferences(shared,0);
        String email = pref.getString("u_email", "");

        i = getIntent();
        String id = i.getStringExtra("bid");

        Log.i("jo", "bid : " + id);
        Log.i("jo", "email : " + email);

        SharedPreferences out = getSharedPreferences("editor", MODE_PRIVATE);
        String result = "id="+id+"&email="+email;
        if(email != null && id != null){
            new Task().execute(result, "type_reservation_list");
        }

        btn_ticket = findViewById(R.id.btn_ticket);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_mypage);





        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }//onCreate()

    //dialog
    private void showmypagedialog(ArrayList<ReservationVO> r){
        dialog.setContentView(R.layout.dialog_mypage);
        ImageButton btnclose = dialog.findViewById(R.id.btnclose);
        res_title = dialog.findViewById(R.id.res_title);
        res_date = dialog.findViewById(R.id.res_date);
        res_time = dialog.findViewById(R.id.res_time);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        res_title.setText(r.get(0).getTitle());
        res_date.setText(r.get(0).getScreen_date());
        res_time.setText(r.get(0).getScreen_time());

        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        pref = getSharedPreferences(shared,0);
        SharedPreferences.Editor editor = pref.edit();
        String value = text_inform.getText().toString();
        editor.putString("u_email",value);
        editor.commit();

    }

    //예약정보 내역
    class Task extends AsyncTask<String, Void, ArrayList<ReservationVO>> {

        ReservationVO vo;
        String sendMsg, receiveMsg;
        String serverip = "http://" + Ip.ip + ":9090/controller/reservation_list.do";

        @Override
        protected ArrayList<ReservationVO> doInBackground(String... strings) {

            list = new ArrayList<ReservationVO>();
            String str = "";

            try {
                URL url = new URL(serverip);

                //위의 경로로 서버 접속
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //osw라는 아웃풋 스트림을 통해 스프링 서버로 파라미터 전달
                sendMsg = strings[0];

                //실제로 서버에 값을 전송
                Log.i("jo", "result : " + sendMsg);
                osw.write(sendMsg);
                osw.flush(); //물리적으로 완료했음을 명시하는 메서드

                //서버통신이 완료되면 결과를 얻기위한 영역
                //getResponseCode() : 200이면 정상
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);

                    //서버에서 넘겨준 JSON형식의 데이터를 받는다
                    str = reader.readLine();

                    String str2 = "{res:" + str + "}";
                    str = str2;

                    //JSON 형태의 배열을 받는 클래스
                    //{res:[{'result' : 'success'}]}
                    JSONArray jarray = new JSONObject(str).getJSONArray("res");

                    for (int i = 0; i < jarray.length(); i++) {

                        JSONObject jObject = jarray.getJSONObject(i);

                        vo = new ReservationVO();

                        vo.setTitle(jObject.optString("title"));
                        vo.setScreen_date(jObject.optString("screen_date"));
                        vo.setScreen_time(jObject.optString("screen_time"));

                        list.add(vo);
                    }

                }
            } catch (Exception e) {

            }
            return list; //통신이 끝나면 onPostExecute()메서드로 넘어간다
        }

        @Override
        protected void onPostExecute(ArrayList<ReservationVO> r) {

            Log.i("jo", "title: " + r.get(0).getTitle());
            Log.i("jo", "date: " + r.get(0).getScreen_date());
            Log.i("jo", "time: " + r.get(0).getScreen_time());

           // res_title = diaolog.findViewById(R.id.res_title);


            btn_ticket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showmypagedialog(r);
                }
            });

        }
    }



}

