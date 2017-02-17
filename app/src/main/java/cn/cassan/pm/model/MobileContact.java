package cn.cassan.pm.model;

/**
 * Created by anqin on 2016/11/5.
 */

public class MobileContact {

    private String mobliephone;
    private String name;
    /**
     * friendid : 4
     * mobilephone : 13000000003
     * employeename : aa
     * friendind : false
     */

    private int friendid;
    private String mobilephone;
    private String employeename;
    private boolean friendind;

    public String getMobliephone() {
        return mobliephone;
    }

    public void setMobliephone(String mobliephone) {
        this.mobliephone = mobliephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public boolean isFriendind() {
        return friendind;
    }

    public void setFriendind(boolean friendind) {
        this.friendind = friendind;
    }
}
