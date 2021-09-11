package com.jmh.androidproject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import vo.SearchVO;

public class ParserSearch {

    SearchVO vo;
    String shprfnm="";
    // String ServiceKey = "cb2878bcbfc544f09380bbb98df98d74";

    //서버통신 메서드
    public ArrayList<SearchVO> connectKopis(ArrayList<SearchVO> list){

        try{
            shprfnm = URLEncoder.encode(SearchPageActivity.search.getText().toString(),"UTF-8");

            // String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr/PF132236?service=cb2878bcbfc544f09380bbb98df98d74&mt20id=PF132236";
            // String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=cb2878bcbfc544f09380bbb98df98d74&mt20id="+mt20id+"display=100";
            // String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=cb2878bcbfc544f09380bbb98df98d74&stdate=20210501&eddate=20210901&cpage=1&rows=10";
            // String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?shprfnm="+shprfnm+"&service=cb2878bcbfc544f09380bbb98df98d74&stdate=20210501&eddate=20210901&cpage=1&rows=10";
            // String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?shprfnm=%EC%82%AC%EB%9E%91&service=cb2878bcbfc544f09380bbb98df98d74&stdate=20210501&eddate=20210901&cpage=1&rows=10";
           // String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=cb2878bcbfc544f09380bbb98df98d74&stdate=20210501&eddate=20210901&cpage=1&rows=10";
            String urlStr = "http://www.kopis.or.kr/openApi/restful/pblprfr?shprfnm="+shprfnm+"&service=cb2878bcbfc544f09380bbb98df98d74&stdate=20210901&eddate=20220901&cpage=1&rows=10";

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //파싱을 위한 객체들
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(connection.getInputStream(), null);

            int parseEvent = parser.getEventType();
            while(parseEvent != XmlPullParser.END_DOCUMENT){

                if(parseEvent == XmlPullParser.START_TAG){
                    String tagname = parser.getName();

                    if(tagname.equalsIgnoreCase("prfnm")){
                        vo = new SearchVO();
                        String prfnm = parser.nextText();

                      /*  //<b>태그 제거
                        Pattern pattern = Pattern.compile("<.*?>");
                        Matcher matcher = pattern.matcher(mt20id);
                        if(matcher.find()){
                            String s_id = matcher.replaceAll("");
                            vo.setS_shprfnm(s_id);
                        }else{
                            vo.setS_shprfnm(mt20id);
                        }
                        //<b>태그*/

                        vo.setS_shprfnm(prfnm);

                    }else if(tagname.equalsIgnoreCase("prfpdfrom")){
                        String prfpdfrom = parser.nextText();
                        vo.setS_stdate(prfpdfrom);

                    }else if(tagname.equalsIgnoreCase("prfpdto")){
                        String prfpdto = parser.nextText();
                        vo.setS_eddate(prfpdto);

                    }else if(tagname.equalsIgnoreCase("poster")){
                        String poster = parser.nextText();
                        vo.setS_poster(poster);

                    }else if(tagname.equalsIgnoreCase("genrenm")) {
                        String genrenm = parser.nextText();
                        vo.setS_shcate(genrenm);

                        list.add(vo);
                    }
                }

                parseEvent = parser.next();

            }

        }catch (Exception e){

        }
        return list;
    }//connectKopis()

}
