package vo;

public class InfoVO {
    private String bid; //공연ID
    private String title; //공연명
    private String s_period; //시작일
    private String e_period; //종료일
    private String img; //포스트



    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
