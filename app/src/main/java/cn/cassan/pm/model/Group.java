package cn.cassan.pm.model;

/**
 * @author Created by zhihao on 2016/10/22.
 * @describe 群组
 * @version_
 **/
public class Group extends BaseEntity {

    private String groupid;
    private String groupname;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
