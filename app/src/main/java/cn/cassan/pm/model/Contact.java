package cn.cassan.pm.model;

import android.graphics.Bitmap;

/**
 * 联系人
 * Created by anqin on 2016/10/18.
 */

public class Contact extends BaseEntity {


    private String name ; //姓名
    private Bitmap avatar;//图片
    private Long lastactivity; //最近活动时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public Long getLastactivity() {
        return lastactivity;
    }

    public void setLastactivity(Long lastactivity) {
        this.lastactivity = lastactivity;
    }
}
