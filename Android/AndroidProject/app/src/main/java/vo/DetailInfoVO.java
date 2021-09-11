package vo;

public class DetailInfoVO {
    private String id; //공연ID
    private String title; //공연명
    private String s_period; //시작일
    private String e_period; //종료일
    private String cast; //출연진
    private String production; //제작진
    private String screen_date; //공연날짜
    private String screen_time; //공연시간
    private String limit_age; //연령제한
    private String company; //극단
    private String price; //티켓가격
    private String img; //포스터 이미지 경로

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getS_period() {
        return s_period;
    }

    public void setS_period(String s_period) {
        this.s_period = s_period;
    }

    public String getE_period() {
        return e_period;
    }

    public void setE_period(String e_period) {
        this.e_period = e_period;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getScreen_date() {
        return screen_date;
    }

    public void setScreen_date(String screen_date) {
        this.screen_date = screen_date;
    }

    public String getScreen_time() {
        return screen_time;
    }

    public void setScreen_time(String screen_time) {
        this.screen_time = screen_time;
    }

    public String getLimit_age() {
        return limit_age;
    }

    public void setLimit_age(String limit_age) {
        this.limit_age = limit_age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
