package com.jmh.androidproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import vo.SearchVO;

public class SearchVOAdapter extends ArrayAdapter<SearchVO> {

    Context context;
    ArrayList<SearchVO> list;
    SearchVO vo;
    int resource;

    public SearchVOAdapter(Context context, int resource, ArrayList<SearchVO>list) {
        super(context, resource, list);

        this.list = list;
        this.context = context;
        this.resource = resource;

    }//생성자
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater linf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = linf.inflate(resource, null);

        vo = list.get(position);

        TextView title = convertView.findViewById(R.id.title);
        TextView stdate = convertView.findViewById(R.id.stdate);
        TextView edate = convertView.findViewById(R.id.eddate);
        TextView genre = convertView.findViewById(R.id.genre);
        ImageView img = convertView.findViewById(R.id.poster);

        title.setText(vo.getS_shprfnm());
        stdate.setText("공연시작일 : " + vo.getS_stdate());
        edate.setText("공연종료일 : " + vo.getS_eddate());
        genre.setText("장르 : " + vo.getS_shcate());

        //이미지로드
        new ImgAsync(img).execute(vo.getS_poster());

        return convertView;
    }//getView()

    class ImgAsync extends AsyncTask< String, Void, Bitmap> {

        ImageView img;
        public ImgAsync(ImageView img){
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bm = null;

            try{

                //가져울 이미지의 경로를 URL 클래그에 전달
                URL img_url = new URL(strings[0]);

                BufferedInputStream bis = new BufferedInputStream(img_url.openStream());

                bm = BitmapFactory.decodeStream(bis);

                bis.close();


            }catch (Exception e){

            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);

        }
    }
}//SeachvoAdapter
