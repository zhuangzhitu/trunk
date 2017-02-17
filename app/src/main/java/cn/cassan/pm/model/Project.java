package cn.cassan.pm.model;

import java.io.Serializable;

/**
 * @author Created by zhihao on 2016/10/29.
 * @describe
 * @version_
 **/
public class Project implements Serializable {

    /**
     * projectidmodel : 1
     * projectname : 初始化测试项目
     * managername :
     * plantobegin : 2016-10-27t21:11:43.29
     * totalperiod : 0
     * stateprovinceid : 0
     * stateprovincename :
     * cityid : 0
     * cityname :
     * constructioncompanyid : null
     * constructioncompanyname :
     */

    private int projectidmodel;
    private String projectname;
    private String managername;
    private String plantobegin;
    private int totalperiod;
    private int stateprovinceid;
    private String stateprovincename;
    private int cityid;
    private String cityname;
    private Object constructioncompanyid;
    private String constructioncompanyname;

    public int getProjectidmodel() {
        return projectidmodel;
    }

    public void setProjectidmodel(int projectidmodel) {
        this.projectidmodel = projectidmodel;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public String getPlantobegin() {
        return plantobegin;
    }

    public void setPlantobegin(String plantobegin) {
        this.plantobegin = plantobegin;
    }

    public int getTotalperiod() {
        return totalperiod;
    }

    public void setTotalperiod(int totalperiod) {
        this.totalperiod = totalperiod;
    }

    public int getStateprovinceid() {
        return stateprovinceid;
    }

    public void setStateprovinceid(int stateprovinceid) {
        this.stateprovinceid = stateprovinceid;
    }

    public String getStateprovincename() {
        return stateprovincename;
    }

    public void setStateprovincename(String stateprovincename) {
        this.stateprovincename = stateprovincename;
    }

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

    public Object getConstructioncompanyid() {
        return constructioncompanyid;
    }

    public void setConstructioncompanyid(Object constructioncompanyid) {
        this.constructioncompanyid = constructioncompanyid;
    }

    public String getConstructioncompanyname() {
        return constructioncompanyname;
    }

    public void setConstructioncompanyname(String constructioncompanyname) {
        this.constructioncompanyname = constructioncompanyname;
    }
}
