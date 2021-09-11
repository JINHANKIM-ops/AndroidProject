package com.jmh.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import vo.InfoVO;

public class HomePageActivity extends AppCompatActivity {
    //bottom bar navigator
    private ChipNavigationBar bottom_bar;

    //홈화면 이미지 버튼
    ImageButton imageButton1, imageButton2, imageButton3;
    //홈화면 dialog (Modal)
    Dialog dialog;

    //뒤로가기 버튼
    Button btn1;

    //달력
    CustomCalendar custom_calendar;

    //진한씨 코딩
    ParserHome parser;
    ArrayList<InfoVO> list;
    ImageView img_sel, img_sel1, img_sel2, img_sel3, img_sel4, img_ch1, img_ch2, img_ch3, img_ch4, img_ch5, img_ch6, img_ch7, img_ch8, img_ch9, img_ch10;
    TextView txt_ch1, txt_ch2, txt_ch3, txt_ch4, txt_ch5, txt_ch6, txt_ch7, txt_ch8, txt_ch9, txt_ch10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //status bar remove
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);

        //진한씨 코딩
        parser = new ParserHome();
        list = new ArrayList<>();
        new KopisAsync().execute();


        //-----start bottom bar navigator-------------------------------------------------------------------------------------//
        bottom_bar = findViewById(R.id.bottom_bar);

        bottom_bar.setItemSelected(R.id.home, true);

        bottom_bar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch(i){
                    case R.id.home:
                       // activity = new HomePageActivity();
                        break;

                    case R.id.search:
                        //activity = new SearchPageActivity();
                        startActivity(new Intent(HomePageActivity.this, SearchPageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.heart:
                        //activity = new LikePageActivity();
                        startActivity( new Intent(HomePageActivity.this, LikePageActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.person:
                        //activity = new MyPageActivity();
                        startActivity(new Intent(HomePageActivity.this,MyPageActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });
        //-----end bottom bar navigator-------------------------------------------------------------------------------------//

        //-----start image slider-------------------------------------------------------------------------------------//
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.one));
        slideModels.add(new SlideModel(R.drawable.two));
        slideModels.add(new SlideModel(R.drawable.three));
        slideModels.add(new SlideModel(R.drawable.four));
        //imageurl ""

        imageSlider.setImageList(slideModels, true);
        //-----end image slider-------------------------------------------------------------------------------------//

        //-----start image button-------------------------------------------------------------------------------------//
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        dialog = new Dialog(this);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfirstDialog();

            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensecDialog();

            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openthirdDialog();

            }
        });

        //-----end image button-------------------------------------------------------------------------------------//


        //진한씨 코딩
        img_sel = findViewById(R.id.img_sel);
        img_sel1 = findViewById(R.id.img_sel1);
        img_sel2 = findViewById(R.id.img_sel2);
        img_sel3 = findViewById(R.id.img_sel3);
        img_sel4 = findViewById(R.id.img_sel4);

        img_ch1 = findViewById(R.id.img_ch1);
        img_ch2 = findViewById(R.id.img_ch2);
        img_ch3 = findViewById(R.id.img_ch3);
        img_ch4 = findViewById(R.id.img_ch4);
        img_ch5 = findViewById(R.id.img_ch5);
        img_ch6 = findViewById(R.id.img_ch6);
        img_ch7 = findViewById(R.id.img_ch7);
        img_ch8 = findViewById(R.id.img_ch8);
        img_ch9 = findViewById(R.id.img_ch9);
        img_ch10 = findViewById(R.id.img_ch10);

        txt_ch1 = findViewById(R.id.txt_ch1);
        txt_ch2 = findViewById(R.id.txt_ch2);
        txt_ch3 = findViewById(R.id.txt_ch3);
        txt_ch4 = findViewById(R.id.txt_ch4);
        txt_ch5 = findViewById(R.id.txt_ch5);
        txt_ch6 = findViewById(R.id.txt_ch6);
        txt_ch7 = findViewById(R.id.txt_ch7);
        txt_ch8 = findViewById(R.id.txt_ch8);
        txt_ch9 = findViewById(R.id.txt_ch9);
        txt_ch10 = findViewById(R.id.txt_ch10);

    }//onCreate()

    @Override
    protected void onResume() {
        super.onResume();

        parser = new ParserHome();
        list = new ArrayList<>();
        new KopisAsync().execute();
    }

    //-----start image button-------------------------------------------------------------------------------------//
    private void openthirdDialog() {
        dialog.setContentView(R.layout.third_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton btnclose = dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void opensecDialog() {
        dialog.setContentView(R.layout.sec_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton btnclose = dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button btnok = dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //----------START calendar--------------
        custom_calendar = dialog.findViewById(R.id.custom_calendar);
        ////////////////////////
        HashMap<Object, Property> descHashMap = new HashMap<>();

        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default",defaultProperty);

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource= R.id.text_view;
        descHashMap.put("current", currentProperty);

        //for present date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource=R.id.text_view;
        descHashMap.put("present", presentProperty);

        //for absent
        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("absent", absentProperty);

        //set desc hashmap on custom calender
        custom_calendar.setMapDescToProp(descHashMap);

        //Initialize date hash map
        HashMap<Integer,Object> dateHashMap = new HashMap<>();
        //Initialize calendar
        Calendar calendar = Calendar.getInstance();
        //put values
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        dateHashMap.put(1,"present");
        dateHashMap.put(2,"absent");
        dateHashMap.put(3,"present");
        dateHashMap.put(4,"absent");
        dateHashMap.put(20,"present");
        dateHashMap.put(30,"absent");
        //set date
        custom_calendar.setDate(calendar,dateHashMap);

        custom_calendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                        + "/" + (selectedDate.get(Calendar.MONTH)+ 1)
                        + "/" + selectedDate.get(Calendar.YEAR);

                //Display date in toast
                Toast.makeText(getApplicationContext(), sDate,Toast.LENGTH_SHORT).show();
            }
        });
        //----------END calendar--------------

        dialog.show();
    }

    private void openfirstDialog() {
        dialog.setContentView(R.layout.first_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton btnclose = dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    //-----end image button-------------------------------------------------------------------------------------//

    //진한씨 코딩
    //OpenAip
    class KopisAsync extends AsyncTask<String, Void, ArrayList<InfoVO>> {

        @Override
        protected ArrayList<InfoVO> doInBackground(String... strings) {
            return parser.connectKopis(list);
        }

        @Override
        protected void onPostExecute(ArrayList<InfoVO> infoVOS) {
            Log.i("MY", "size : " + infoVOS.size());
            String result = "bid="+infoVOS.get(0).getBid()+"&title="+infoVOS.get(0).getTitle()+"&s_period="+infoVOS.get(0).getS_period()+"&e_period="+infoVOS.get(0).getE_period()+"&img="+infoVOS.get(0).getImg();

            new ImageAsync(img_sel).execute(infoVOS.get(0).getImg());
            new ImageAsync1(img_sel1).execute(infoVOS.get(1).getImg());
            new ImageAsync2(img_sel2).execute(infoVOS.get(2).getImg());
            new ImageAsync3(img_sel3).execute(infoVOS.get(3).getImg());
            new ImageAsync4(img_sel4).execute(infoVOS.get(4).getImg());

            new ImgAsync1(img_ch1).execute(infoVOS.get(10).getImg());
            new ImgAsync2(img_ch2).execute(infoVOS.get(11).getImg());
            new ImgAsync3(img_ch3).execute(infoVOS.get(12).getImg());
            new ImgAsync4(img_ch4).execute(infoVOS.get(13).getImg());
            new ImgAsync5(img_ch5).execute(infoVOS.get(14).getImg());
            new ImgAsync6(img_ch6).execute(infoVOS.get(15).getImg());
            new ImgAsync7(img_ch7).execute(infoVOS.get(16).getImg());
            new ImgAsync8(img_ch8).execute(infoVOS.get(17).getImg());
            new ImgAsync9(img_ch9).execute(infoVOS.get(18).getImg());
            new ImgAsync10(img_ch10).execute(infoVOS.get(19).getImg());

            txt_ch1.setText(infoVOS.get(10).getTitle());
            txt_ch2.setText(infoVOS.get(11).getTitle());
            txt_ch3.setText(infoVOS.get(12).getTitle());
            txt_ch4.setText(infoVOS.get(13).getTitle());
            txt_ch5.setText(infoVOS.get(14).getTitle());
            txt_ch6.setText(infoVOS.get(15).getTitle());
            txt_ch7.setText(infoVOS.get(16).getTitle());
            txt_ch8.setText(infoVOS.get(17).getTitle());
            txt_ch9.setText(infoVOS.get(18).getTitle());
            txt_ch10.setText(infoVOS.get(19).getTitle());

            img_sel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(0).getBid());
                    startActivity(i);
                }
            });

            img_sel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(1).getBid());
                    startActivity(i);
                }
            });

            img_sel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(2).getBid());
                    startActivity(i);
                }
            });

            img_sel3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(3).getBid());
                    startActivity(i);
                }
            });

            img_sel4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(4).getBid());
                    startActivity(i);
                }
            });

            img_ch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(10).getBid());
                    startActivity(i);
                }
            });

            img_ch2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(11).getBid());
                    startActivity(i);
                }
            });

            img_ch3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(12).getBid());
                    startActivity(i);
                }
            });

            img_ch4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(13).getBid());
                    startActivity(i);
                }
            });

            img_ch5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(14).getBid());
                    startActivity(i);
                }
            });

            img_ch6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(15).getBid());
                    startActivity(i);
                }
            });

            img_ch7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(16).getBid());
                    startActivity(i);
                }
            });

            img_ch8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(17).getBid());
                    startActivity(i);
                }
            });

            img_ch9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(18).getBid());
                    startActivity(i);
                }
            });

            img_ch10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomePageActivity.this, ListPageActivity.class);
                    i.putExtra("bid", infoVOS.get(19).getBid());
                    startActivity(i);
                }
            });

        }
    }
}