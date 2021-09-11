package com.jmh.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vo.DetailInfoVO;
import vo.ReservationVO;

public class Ticket_RevActivity extends AppCompatActivity {

    ImageView back_button;
    ReservationDe reservationDe;
    ArrayList<ReservationVO> list;
    String bid = "";
    TextView txt_ti1, txt_ti2, txt_ti3, txt_ti4, txt_ti5,txt_ti6;
    Ip ip;
    Button btn_reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar remove
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ticket_rev);

        //back button
        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Ticket_RevActivity.this, ListPageActivity.class);
                i.putExtra("bid", bid);
                startActivity(i);
            }

        });

        bid = getIntent().getStringExtra("bid");
        Log.i("re", "bid : " + bid);
        list = new ArrayList<ReservationVO>();
        reservationDe = new ReservationDe();
        new KopisAsync().execute();

        txt_ti1 = findViewById(R.id.txt_ti1); //bid
        txt_ti2 = findViewById(R.id.txt_ti2); //title
        txt_ti3 = findViewById(R.id.txt_ti3); //cast
        txt_ti4 = findViewById(R.id.txt_ti4); //screen_date
        txt_ti5 = findViewById(R.id.txt_ti5); //screen_time
        txt_ti6 = findViewById(R.id.txt_ti6); //price
        btn_reserve = findViewById(R.id.btn_reserve); //티켓 예약 버튼

    }

    class KopisAsync extends AsyncTask<String, Void, ArrayList<ReservationVO>> {

        @Override
        protected ArrayList<ReservationVO> doInBackground(String... strings) {
            return reservationDe.connectKopis(list, ListPageActivity.bid);
        }

        @Override
        protected void onPostExecute(ArrayList<ReservationVO> infoVOS) {
            Log.i("re", "res size : " + infoVOS.size());
            txt_ti1.setText(infoVOS.get(0).getId());
            txt_ti2.setText(infoVOS.get(0).getTitle());
            txt_ti3.setText(infoVOS.get(0).getCast());
            txt_ti4.setText(infoVOS.get(0).getScreen_date());
            txt_ti5.setText(infoVOS.get(0).getScreen_time());
            txt_ti6.setText(infoVOS.get(0).getPrice());

            btn_reserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Ticket_RevActivity.this, MyPageActivity.class);
                    i.putExtra("bid", infoVOS.get(0).getId());
                    startActivity(i);

                    String id = infoVOS.get(0).getId();
                    String title = infoVOS.get(0).getTitle();
                    String cast = infoVOS.get(0).getCast();
                    String screen_date = infoVOS.get(0).getScreen_date();
                    String screen_time = infoVOS.get(0).getScreen_time();
                    String price = infoVOS.get(0).getPrice();
                    String result = "id="+id+"&title="+title+"&cast="+cast+"&screen_date="
                            +screen_date+"&screen_time="+screen_time+"&price="+price;
                    new Task().execute(result, "type_reservation");
                }
            });

        }
    }

    //예약정보 저장
    class Task extends AsyncTask<String, Void, String> {

        String sendMsg, receiveMsg;
        String serverip = "http://"+Ip.ip+":9090/controller/reservation.do";

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
                Log.i("jo", "result : " + sendMsg);
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
    }

}