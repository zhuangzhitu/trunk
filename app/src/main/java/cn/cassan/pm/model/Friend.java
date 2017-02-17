package cn.cassan.pm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Created by zhihao on 2016/10/25.
 * @describe 好友
 * @version_
 **/
public class Friend extends BaseEntity implements Parcelable {

    private int friendid;
    private String avatarurl;
    private String name;
    private String hxid;


    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {

            Friend friend = new Friend();
            friend.setFriendid(in.readInt());
            friend.setAvatarurl(in.readString());
            friend.setName(in.readString());
            friend.setHxid(in.readString());
            return friend;
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendid=" + friendid +
                ", avatarurl='" + avatarurl + '\'' +
                ", name='" + name + '\'' +
                ", hxid='" + hxid + '\'' +
                '}';
    }

    /**
     * 按顺序读写
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(friendid);
        dest.writeString(avatarurl);
        dest.writeString(name);
        dest.writeString(hxid);

    }
}
