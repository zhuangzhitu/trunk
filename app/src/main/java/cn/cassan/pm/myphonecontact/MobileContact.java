package cn.cassan.pm.myphonecontact;

import cn.cassan.pm.model.BaseEntity;

/**
 * @author Created by zhihao on 2016/11/5.
 * @describe
 * @version_
 **/
public class MobileContact extends BaseEntity {

    public String mobliephone;
    public String name;


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

}
