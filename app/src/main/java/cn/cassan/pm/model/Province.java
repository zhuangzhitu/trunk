package cn.cassan.pm.model;

import java.util.List;

/**
 * @author Created by zhihao on 2016/10/26.
 * @describe
 * @version_
 **/
public class Province {

    private int provinceid;
    private String provincename;
    private List<City> citys;

    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public List<City> getCityList() {
        return citys;
    }

    public void setCityList(List<City> citys) {
        this.citys = citys;
    }


}
