package cn.cassan.pm.ui.message;

import android.view.View;
import android.widget.AdapterView;

import butterknife.Bind;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.MessageGriViewAdapter;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.util.Helper;
import cn.cassan.pm.widget.MyGridView;
import cn.cassan.pm.widget.TitleView;

public class NewMessageActivity extends BaseFragmentActivity {

    @Bind(R.id.titleview)
    TitleView titleView;
    @Bind(R.id.gridview)
    MyGridView myGridView;
    private MessageGriViewAdapter myGridAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_message;
    }


    @Override
    public void initView() {

        titleView.setTitleClickListener(new TitleView.TitleClickListener() {
            @Override
            public void onClick(String action) {

                switch (action) {
                    case TitleView.IMAGELEFT:
                    case TitleView.TEXTLEFT:
                        finish();
                }
            }
        });
    }

    @Override
    public void initData() {
        myGridAdapter = new MessageGriViewAdapter();
        myGridView.setAdapter(myGridAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Helper.showToast(getContext(), "你点击了" + position);
            }
        });
    }
}
