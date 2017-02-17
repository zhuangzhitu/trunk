package cn.cassan.pm.model;



import java.util.ArrayList;
import java.util.List;

/**
 * 搜索实体类
 *
 * @author
 * @created
 */
public class SearchList extends BaseEntity implements ListEntity<SearchResult> {

    public final static String CATALOG_ALL = "all";
    public final static String CATALOG_NEWS = "news";
    public final static String CATALOG_POST = "post";
    public final static String CATALOG_SOFTWARE = "software";
    public final static String CATALOG_BLOG = "blog";


    private int pageSize;


    private List<SearchResult> list = new ArrayList<SearchResult>();

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public List<SearchResult> getList() {
        return list;
    }

    public void setList(List<SearchResult> list) {
        this.list = list;
    }
}
