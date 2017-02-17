package cn.cassan.pm.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.cassan.pm.service.NoticeUtils;
import cn.cassan.pm.util.TLog;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TLog.log("onReceive ->net.oschina.app收到定时获取消息");
        NoticeUtils.requestNotice(context);
    }
}
