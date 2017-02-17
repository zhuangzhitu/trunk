package cn.cassan.pm.model;

/**
 * @author Created by zhihao on 2016/11/5.
 * @describe
 * @version_
 **/
public class Data extends BaseEntity {


    /**
     * documentid : 6
     * documentname : none-30152548.jpg
     * url : ~/media/document/0000000/none-30152548.jpg
     * createtime : 2016/11/05 13:28
     * mimetype : image/jpeg
     * extension : jpg
     */

    private int documentid;
    private String documentname;
    private String url;
    private String createtime;
    private String mimetype;
    private String extension;

    public int getDocumentid() {
        return documentid;
    }

    public void setDocumentid(int documentid) {
        this.documentid = documentid;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
