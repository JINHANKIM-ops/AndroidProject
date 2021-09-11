package com.jmh.androidproject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vo.InfoVO;

public class ParserHome {

    //xml파싱: 웹에서 요소(title, author)를 검색하여 vo에 저장하는 작업(객체화 시키는 작업)
    InfoVO vo;
    String ServiceKey = "f9d82e57a6c643b682bf597e21f55a40";

    //서버 통신 메서드
    public ArrayList<InfoVO> connectKopis(ArrayList<InfoVO> list ){
        try{
            String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=f9d82e57a6c643b682bf597e21f55a40&stdate=20210620&eddate=20210826&cpage=1&rows=20&signgucode=11";

            URL url = new URL(urlStr); //위 경로로 접근
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //url을 실제 서버로 연결

            //파싱을 위한 객체
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            //XML의 정보를 가져옴
            parser.setInput(connection.getInputStream(), null);

            //본격적인 파싱작업
            int parserEvent = parser.getEventType();

            //서버측 xml문서의 끝을 만날때까지 반복
            while(parserEvent != XmlPullParser.END_DOCUMENT){
                //시작태그
                if(parserEvent == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();

                    if(tagName.equalsIgnoreCase("mt20id")) { //공연ID
                        vo = new InfoVO();
                        String bid = parser.nextText();
                        vo.setBid(bid);
                    }else if(tagName.equalsIgnoreCase("prfnm")) { //공연명
                        String title = parser.nextText(); //title 태그안으로 들어간다
                        vo.setTitle(title);
                    }else if(tagName.equalsIgnoreCase("prfpdfrom")){ //시작일
                        String period_s = parser.nextText();
                        vo.setS_period(period_s);
                    }else if(tagName.equalsIgnoreCase("prfpdto")){ //종료일
                        String period_e = parser.nextText();
                        vo.setE_period(period_e);
                    }else if(tagName.equalsIgnoreCase("poster")){ //이미지
                        String img = parser.nextText();
                        vo.setImg(img);
                        list.add(vo);
                    }
                }
                parserEvent = parser.next(); //다음요소
            }

        }catch (Exception e){

        }

        return list;

    }

}
