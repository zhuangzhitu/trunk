package cn.cassan.pm.model;

import java.io.Serializable;

/**
 * 项目信息
 * Created by anqin on 2016/9/17.
 *
 * @title
 */
public class ProjectInfo extends BaseEntity implements Serializable {

    private int projectid;
    private String projectname;
    private String managername;
    private String provincename;
    private String cityname;

    public int getProjectid() {
        return projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public String getManagername() {
        return managername;
    }

    public String getProvincename() {
        return provincename;
    }

    public String getCityname() {
        return cityname;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
