package com.jmh.androidproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.net.URL;

public class ImgAsync9 extends AsyncTask<String, Void, Bitmap> {

    ImageView img_ch9;

    public ImgAsync9(ImageView img_ch9){
        this.img_ch9 = img_ch9;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        //Bitmap: bit단위의 데이터를 모아서 이미지로 만들어주는 클래스
        Bitmap bm = null;

        try{
            //가져올 이미지의 경로를 URL클래스에 전달
            URL img_url = new URL(strings[0]);
            BufferedInputStream bis = new BufferedInputStream( img_url.openStream());
            //bis가 읽어온 정보로부터 Bitmap객체를 생성
            bm = BitmapFactory.decodeStream(bis);

            bis.close();
        }catch (Exception e){

        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        img_ch9.setImageBitmap( bitmap );
    }
}
