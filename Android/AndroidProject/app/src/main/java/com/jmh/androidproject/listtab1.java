package com.jmh.androidproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vo.DetailInfoVO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link listtab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class listtab1 extends Fragment {

    //진한씨 코딩
    ParserDe parserDe;
    ArrayList<DetailInfoVO> list;
    String bid = "";
    ImageView img_d;
    TextView txt_d, txt_de, txt_det, txt_detail;
    FloatingActionButton btn_t;

    TextView details;
    LinearLayout layout;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public listtab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment listtab1.
     */
    // TODO: Rename and change types and number of parameters
    public static listtab1 newInstance(String param1, String param2) {
        listtab1 fragment = new listtab1();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView =  inflater.inflate(R.layout.fragment_listtab1, container, false);

        //진한씨 코딩

        img_d = myView.findViewById(R.id.img_d);
        txt_d = myView.findViewById(R.id.txt_d);
        txt_de = myView.findViewById(R.id.txt_de);
        txt_det = myView.findViewById(R.id.txt_det);
        txt_detail = myView.findViewById(R.id.txt_detail);
       btn_t = myView.findViewById(R.id.btn_t);

        bid = getActivity().getIntent().getStringExtra("bid");
        Log.i("MY", "test:"+bid);

        list = new ArrayList<DetailInfoVO>();
        parserDe = new ParserDe();
        new KopisAsync().execute();


        return myView;
    }//oncreateview

    //진한씨 코딩
    //api 불러오기
    class KopisAsync extends AsyncTask<String, Void, ArrayList<DetailInfoVO>> {

        @Override
        protected ArrayList<DetailInfoVO> doInBackground(String... strings) {

            return parserDe.connectKopis(list, ListPageActivity.bid);
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
                    Intent i = new Intent(getActivity(), Ticket_RevActivity.class);
                    i.putExtra("bid", bid);
                    Log.i("re", "li : " + bid);
                    startActivity(i);
                }
            });

        }
    }
}













