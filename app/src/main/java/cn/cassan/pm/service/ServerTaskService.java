package cn.cassan.pm.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import java.util.ArrayList;
import java.util.List;
import cn.cassan.pm.R;

public class ServerTaskService extends IntentService {
    public static final String ACTION_PUB_BLOG_COMMENT = "net.oschina.app.ACTION_PUB_BLOG_COMMENT";
    public static final String ACTION_PUB_COMMENT = "net.oschina.app.ACTION_PUB_COMMENT";
    public static final String ACTION_PUB_POST = "net.oschina.app.ACTION_PUB_POST";
    public static final String ACTION_PUB_TWEET = "net.oschina.app.ACTION_PUB_TWEET";
    public static final String ACTION_PUB_SOFTWARE_TWEET = "net.oschina.app" +
            ".ACTION_PUB_SOFTWARE_TWEET";
    public static final String KEY_ADAPTER = "adapter";
    public static final String BUNDLE_PUB_COMMENT_TASK = "BUNDLE_PUB_COMMENT_TASK";
    public static final String BUNDLE_PUB_POST_TASK = "BUNDLE_PUB_POST_TASK";
    public static final String BUNDLE_PUB_TWEET_TASK = "BUNDLE_PUB_TWEET_TASK";
    public static final String BUNDLE_PUB_SOFTWARE_TWEET_TASK = "BUNDLE_PUB_SOFTWARE_TWEET_TASK";
    public static final String KEY_SOFTID = "soft_id";
    private static final String SERVICE_NAME = "ServerTaskService";
    private static final String KEY_COMMENT = "comment_";
    private static final String KEY_TWEET = "tweet_";
    private static final String KEY_SOFTWARE_TWEET = "software_tweet_";
    private static final String KEY_POST = "post_";

    public static List<String> penddingTasks = new ArrayList<String>();

    public ServerTaskService() {
        this(SERVICE_NAME);
    }

    public ServerTaskService(String name) {
        super(name);
    }

    private synchronized void tryToStopServie() {
        if (penddingTasks == null || penddingTasks.size() == 0) {
            stopSelf();
        }
    }

    private synchronized void addPenddingTask(String key) {
        penddingTasks.add(key);
    }

    private synchronized void removePenddingTask(String key) {
        penddingTasks.remove(key);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_PUB_BLOG_COMMENT.equals(action)) {

        } else if (ACTION_PUB_COMMENT.equals(action)) {

        } else if (ACTION_PUB_POST.equals(action)) {

        } else if (ACTION_PUB_TWEET.equals(action)) {

        } else if (ACTION_PUB_SOFTWARE_TWEET.equals(action)) {

        }
    }

    private void notifySimpleNotifycation(int id, String ticker, String title, String content, boolean ongoing, boolean autoCancel) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setContentIntent(
                        PendingIntent.getActivity(this, 0, new Intent(), 0))
                .setSmallIcon(R.drawable.ic_notification);

        // if (AppContext.isNotificationSoundEnable()) {
        // builder.setDefaults(Notification.DEFAULT_SOUND);
        // }

        Notification notification = builder.build();

        NotificationManagerCompat.from(this).notify(id, notification);
    }

    private void cancellNotification(int id) {
        NotificationManagerCompat.from(this).cancel(id);
    }

//    class PublicCommentResponseHandler extends OperationResponseHandler {
//
//        public PublicCommentResponseHandler(Looper looper, Object... args) {
//            super(looper, args);
//        }
//
//        @Override
//        public void onSuccess(int code, ByteArrayInputStream is, Object[] args)
//                throws Exception {
//            PublicCommentTask task = (PublicCommentTask) args[0];
//            final int id = task.getId() * task.getUid();
//            ResultBean resB = XmlUtils.toBean(ResultBean.class, is);
//            Result res = resB.getResult();
//            if (res.OK()) {
//                final Comment comment = resB.getComment();
//                // UIHelper.sendBroadCastCommentChanged(ServerTaskService.this,
//                // isBlog, task.getId(), task.getCatalog(),
//                // Comment.OPT_ADD, comment);
//                notifySimpleNotifycation(id,
//                        getString(R.string.comment_publish_success),
//                        getString(R.string.comment_blog),
//                        getString(R.string.comment_publish_success), false,
//                        true);
//                removePenddingTask(KEY_COMMENT + id);
//            } else {
//                onFailure(100, res.getErrorMessage(), args);
//            }
//        }
//
//        @Override
//        public void onFailure(int code, String errorMessage, Object[] args) {
//            PublicCommentTask task = (PublicCommentTask) args[0];
//            int id = task.getId() * task.getUid();
//            notifySimpleNotifycation(id,
//                    getString(R.string.comment_publish_faile),
//                    getString(R.string.comment_blog),
//                    code == 100 ? errorMessage
//                            : getString(R.string.comment_publish_faile), false,
//                    true);
//            removePenddingTask(KEY_COMMENT + id);
//        }
//
//        @Override
//        public void onFinish() {
//            tryToStopServie();
//        }
//    }
//
//    class PublicTweetResponseHandler extends OperationResponseHandler {
//
//        String key = null;
//
//        public PublicTweetResponseHandler(Looper looper, Object... args) {
//            super(looper, args);
//            key = (String) args[1];
//        }
//
//        @Override
//        public void onSuccess(int code, ByteArrayInputStream is, Object[] args)
//                throws Exception {
////            Tweet tweet = (Tweet) args[0];
////            final int id = tweet.getId();
////            Result res = XmlUtils.toBean(ResultBean.class, is).getResult();
////            if (res.OK()) {
////                // 发布成功之后，删除草稿
////                AppContext.setTweetDraft("");
////                notifySimpleNotifycation(id,
////                        getString(R.string.tweet_publish_success),
////                        getString(R.string.tweet_public),
////                        getString(R.string.tweet_publish_success), false, true);
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        cancellNotification(id);
////                    }
////                }, 3000);
////                removePenddingTask(key + id);
////                if (tweet.getImageFilePath() != null) {
////                    File imgFile = new File(tweet.getImageFilePath());
////                    if (imgFile.exists()) {
////                        imgFile.delete();
////                    }
////                }
////            } else {
////                onFailure(100, res.getErrorMessage(), args);
////            }
//        }
//
//        @Override
//        public void onFailure(int code, String errorMessage, Object[] args) {
////            Tweet tweet = (Tweet) args[0];
////            int id = tweet.getId();
////            notifySimpleNotifycation(id,
////                    getString(R.string.tweet_publish_faile),
////                    getString(R.string.tweet_public),
////                    code == 100 ? errorMessage
////                            : getString(R.string.tweet_publish_faile), false,
////                    true);
////            removePenddingTask(key + id);
//        }
//
//        @Override
//        public void onFinish() {
//            tryToStopServie();
//        }
//    }
}
