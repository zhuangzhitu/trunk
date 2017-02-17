package com.cassan.circlefriend.bean;

import android.text.TextUtils;

import java.util.List;

import cn.cassan.pm.model.Documents;
import cn.cassan.pm.model.UserInfo;


public class CircleItem extends BaseBean {

    public final static String TYPE_URL = "1";
    public final static String TYPE_IMG = "2";
    public final static String TYPE_VIDEO = "3";

    private int notifymessageid;
    private int notifymessagetype;
    private String title;
    private String createtime;
    private int followstatus;
    private List<Documents> documents;

    public int getNotifymessageid() {
        return notifymessageid;
    }

    public void setNotifymessageid(int notifymessageid) {
        this.notifymessageid = notifymessageid;
    }

    public int getNotifymessagetype() {
        return notifymessagetype;
    }

    public void setNotifymessagetype(int notifymessagetype) {
        this.notifymessagetype = notifymessagetype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //    public String getContent() {
    //        return content;
    //    }
    //
    //    public void setContent(String content) {
    //        this.content = content;
    //    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getFollowstatus() {
        return followstatus;
    }

    public void setFollowstatus(int followstatus) {
        this.followstatus = followstatus;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String content;
    private String createTime;
    private String type;//1:链接  2:图片 3:视频
    private String linkImg;
    private String linkTitle;
    private List<String> photos;
    private List<FavortItem> favorters;
    private List<CommentItem> comments;
    private UserInfo user;
    private String videoUrl;
    private String videoImgUrl;
    //	    private int notifymessageid;
    //    private int notifymessagetype;
    //    private String title;
    //    private String content;
    //    private String createtime;
    //    private int followstatus;
    //    private List<Documents> documents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FavortItem> getFavorters() {
        return favorters;
    }

    public void setFavorters(List<FavortItem> favorters) {
        this.favorters = favorters;
    }

    public List<CommentItem> getComments() {
        return comments;
    }

    public void setComments(List<CommentItem> comments) {
        this.comments = comments;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public boolean hasFavort() {
        if (favorters != null && favorters.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasComment() {
        if (comments != null && comments.size() > 0) {
            return true;
        }
        return false;
    }

    public int getCurUserFavortId(int curUserId) {
        int favortid = 0;
        if (!TextUtils.isEmpty(String.valueOf(curUserId)) && hasFavort()) {
            for (FavortItem item : favorters) {
                if (curUserId == (item.getUser().getId())) {
                    favortid = item.getId();
                    return favortid;
                }
            }
        }
        return favortid;
    }
}
