package cn.cassan.pm.model;


import java.util.List;

/**
 * 工作台通知信息
 *
 * @author
 * @created
 */
public class WorkBenchNotice extends BaseEntity {
    /**
     * notifymessageid : 1
     * notifymessagetype : 0
     * title : 飞洒发顺丰
     * content : 复反反复复反反复复吩咐
     * createtime : 2016-10-31t23:59:34.843
     * followstatus : 1
     * documents : []
     */

    private int notifymessageid;
    private int notifymessagetype;
    private String title;
    private String content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    //
    //    private String title;
    //    private String content;
    //    private String time;
    //    private String count;
    //    private int type;
    //
    //    public String getTitle() {
    //        return title;
    //    }
    //
    //    public void setTitle(String title) {
    //        this.title = title;
    //    }
    //
    //    public String getContent() {
    //        return content;
    //    }
    //
    //    public void setContent(String content) {
    //        this.content = content;
    //    }
    //
    //    public String getTime() {
    //        return time;
    //    }
    //
    //    public void setTime(String time) {
    //        this.time = time;
    //    }
    //
    //    public String getCount() {
    //        return count;
    //    }
    //
    //    public void setCount(String count) {
    //        this.count = count;
    //    }
    //
    //    public int getType() {
    //        return type;
    //    }
    //
    //    public void setType(int type) {
    //        this.type = type;
    //    }
}
