package cn.cassan.pm.util.Huanxin;


/**
 * 数据同步监听
 */
public interface DataSyncListener {
    /**
     * 同步完成
     *
     */
    void onSyncComplete(boolean success);
}