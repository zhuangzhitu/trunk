package cn.cassan.pm.ui.huanxin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;

import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseHuanxinActivity;
import cn.cassan.pm.ui.MainActivity;

/**
 * 聊天窗口
 */
public class ChatActivity extends BaseHuanxinActivity {

    protected static final String TAG = ChatActivity.class.getSimpleName();
    String toChatUsername;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_chat);
        //获取用户编号或组编号
        toChatUsername = getIntent().getExtras().getString("userId");
        //使用聊天控件，动态填充到聊天窗口中
        chatFragment = new ChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // 确认只打开一个聊天窗口
       String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
           startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       //PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
