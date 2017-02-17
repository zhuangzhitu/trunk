package cn.cassan.pm.model;

/**
 * @author Created by zhihao on 2016/11/2.
 * @describe
 * @version_
 **/
public class Documents extends BaseEntity {
    /**
     * documentid : 13
     * type : 0
     * url : ~/media/picture/0000000/a9ea13fc-cf0a-4b98-a7fb-0445c49fca90.jpg
     * thumburl : ~/media/picture/0000000/thumb/a9ea13fc-cf0a-4b98-a7fb-0445c49fca90.jpg
     */

    private int documentid;
    private int type;
    private String url;
    private String thumburl;

    public int getDocumentid() {
        return documentid;
    }

    public void setDocumentid(int documentid) {
        this.documentid = documentid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }
}
