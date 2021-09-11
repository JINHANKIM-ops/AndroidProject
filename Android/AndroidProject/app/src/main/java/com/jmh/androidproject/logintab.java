package com.jmh.androidproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link logintab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class logintab extends Fragment {

    EditText edit_email, edit_pwd;
    Button btn_login;
    TextView btn_pwd_find;
    float v =0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public logintab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment logintab.
     */
    // TODO: Rename and change types and number of parameters
    public static logintab newInstance(String param1, String param2) {
        logintab fragment = new logintab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_logintab, container, false);
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_logintab, container,false);

        edit_email = root.findViewById(R.id.edit_email);
        edit_pwd = root.findViewById(R.id.edit_pwd);
        btn_pwd_find = root.findViewById(R.id.btn_pwd_find);
        btn_login = root.findViewById(R.id.btn_login);

        edit_email.setTranslationX(800); //email
        edit_pwd.setTranslationX(800);
        btn_pwd_find.setTranslationX(800);
        btn_login.setTranslationX(800);

        edit_email.setAlpha(v);
        edit_pwd.setAlpha(v);
        btn_pwd_find.setAlpha(v);
        btn_login.setAlpha(v);

        edit_email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edit_pwd.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn_pwd_find.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btn_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();



        //로그인을 위한 이벤트 감지자
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyPageActivity.class);

                String email = edit_email.getText().toString();
                String pwd = edit_pwd.getText().toString();

                String result = "email="+email+"&pwd="+pwd;

                new LoginTask().execute(result);

            }
        });
        return root;
    }//oncreate()

    //로그인 화면 Async클래스
    class LoginTask extends AsyncTask<String, Void, String> {

        public String ip = Ip.ip;
        String sendMsg, receiveMsg;
        String serverip = "http://" + ip + ":9090/controller/login.do";

        @Override
        protected String doInBackground(String... strings) {

            String str = "";

            try {

                URL url = new URL(serverip);

                //위의 경로로 서버접속
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //osw라는 아웃풋 스트림을 통해 스프링 서버로 파라미터를 전달
                //regi.do?id=a&pwd=1111
                sendMsg = strings[0];

                //실제로 서버에 값을 전송
                osw.write(sendMsg);
                osw.flush();//물리적으로 기록을 완료했음을 명시하는 메서드

                //서버통신이 완료되면 결과를 얻기위한 영역
                //conn.getResponseCode() : 200이면 정상
                //                         404, 500등... 비정상
                if( conn.getResponseCode() == conn.HTTP_OK ){


                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);

                    //서버에서 넘겨준 JSON형식의 데이터를 받는다
                    //{res:[{'result':'success'}]}
                    receiveMsg = reader.readLine();
                    Log.i("MY", receiveMsg);

                    //JSON형태의 배열을 받는 클래스
                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");

                    //jarray : [{'result':'success'}]
                    JSONObject jObject = jarray.getJSONObject(0);

                    //jObject : {'result':'success'}
                    str = jObject.optString("result");
                }

            }catch (Exception e){

            }
            return str;
        }

        @Override
        protected void onPostExecute(String s) {



            if( s.equalsIgnoreCase("success") ){
                //Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();

                //로그인 성공시 메인페이지로 전환
                Intent i = new Intent(getActivity(), MyPageActivity.class);

                i.putExtra("message", edit_email.getText().toString());
                i.putExtra("login", "ok");

                startActivity(i);

                finish();



            }else{

                Toast.makeText(getActivity(), "아이디나 비밀번호가 올바르지 않음", Toast.LENGTH_SHORT).show();

            }


        }
    }//Task

    private void finish() {
    }
}