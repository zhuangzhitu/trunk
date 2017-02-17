package cn.cassan.pm.model;

/**
 * @author Created by zhihao on 2016/10/26.
 * @describe
 * @version_
 **/
public class City extends BaseEntity {

    private int cityid;
    private String cityname;

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
