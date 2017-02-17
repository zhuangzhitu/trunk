package cn.cassan.pm.model;

/**
 * 用户信息表
 * Created by anqin on 2016/9/17.
 */

public class UserInfo {
    /**
     * id : 1
     * username : 13000000000
     * nickname :
     * birthday :
     * avatarurl :
     * mobilephone : 13000000000
     * token : 8db8e32d-faf8-45af-a130-0c7219253ec3
     * gender : 0
     * huanxinusername : 13000000000
     * huanxinpassword :
     * departmentid : 1
     * departmentname : 电力设计中心
     * companyid : 1
     * companyname : 云南广众工程咨询有限公司
     */

    protected int id;
    protected String username="";
    private String nickname="";
    private String birthday="";
    private String avatarurl="";
    private String mobilephone="";
    private String token="";
    private int gender=0;
    private String huanxinusername="";
    private String huanxinpassword="";
    private int departmentid=0;
    private String departmentname="";
    private int companyid=1;
    private String companyname="";
    private String baiduchannelid="";
    private int mobilesystem=1;//0：ios 安卓：1
    private String systemversion="";

    public UserInfo(String username, int id, String avatarurl) {
        this.username = username;
        this.id = id;
        this.avatarurl = avatarurl;
    }

    public UserInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHuanxinusername() {
        return huanxinusername;
    }

    public void setHuanxinusername(String huanxinusername) {
        this.huanxinusername = huanxinusername;
    }

    public String getHuanxinpassword() {
        return huanxinpassword;
    }

    public void setHuanxinpassword(String huanxinpassword) {
        this.huanxinpassword = huanxinpassword;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    private String password;

    private boolean isRememberMe;

    private String latestonline;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        isRememberMe = rememberMe;
    }


    public String getLatestonline() {
        return latestonline;
    }

    public void setLatestonline(String latestonline) {
        this.latestonline = latestonline;
    }

    public String getBaiduchannelid() {
        return baiduchannelid;
    }

    public void setBaiduchannelid(String baiduchannelid) {
        this.baiduchannelid = baiduchannelid;
    }

    public int getMobileSystem() {
        return mobilesystem;
    }

    public void setMobileSystem(int mbilesystem) {
        this.mobilesystem = mbilesystem;
    }

    public String getSystemVersion() {
        return systemversion;
    }

    public void setSystemVersion(String systemversion) {
        this.systemversion = systemversion;
    }
}
