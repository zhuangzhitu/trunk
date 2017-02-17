package cn.cassan.pm.model;



/**
 * 实体类
 *
 * @author
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public abstract class BaseEntity {


    protected int id;

    protected String cacheKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
