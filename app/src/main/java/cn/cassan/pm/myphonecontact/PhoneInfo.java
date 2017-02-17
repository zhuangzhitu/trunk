package cn.cassan.pm.myphonecontact;

/**
 * @author Created by zhihao on 2016/10/29.
 * @describe
 * @version_
 **/
public class PhoneInfo extends MobileContact{

    private String name;
    private String number;
    private String firstLetter;
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

    public PhoneInfo() {
    }

    public PhoneInfo(String name, String number, String firstLetter) {
        this.name = name;
        this.number = number;
        this.firstLetter = firstLetter;
    }

    public String getName() {
        return name;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getNumber() {
        return number;
    }

    public String getFirstLetter() {
        return firstLetter;
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

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                ", friendid=" + friendid +
                ", mobilephone='" + mobilephone + '\'' +
                ", employeename='" + employeename + '\'' +
                ", friendind=" + friendind +
                '}';
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