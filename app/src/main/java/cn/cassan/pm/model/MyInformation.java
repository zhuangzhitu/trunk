package cn.cassan.pm.model;

/**
 * 我的资料实体类
 *
 * 我的信息
 */

@SuppressWarnings("serial")
public class MyInformation extends BaseEntity {

    private UserInfo user;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
