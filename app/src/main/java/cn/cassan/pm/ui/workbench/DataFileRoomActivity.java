package cn.cassan.pm.ui.workbench;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import butterknife.Bind;
import cn.cassan.pm.R;
import cn.cassan.pm.adapter.DataFileAdapter;
import cn.cassan.pm.base.BaseFragmentActivity;
import cn.cassan.pm.widget.MyGridView;
import cn.cassan.pm.widget.TitleView;

/**
 * @author Created by zhihao on 2016/11/1.
 * @describe 资料室
 * @version_
 **/
public class DataFileRoomActivity extends BaseFragmentActivity {

    @Bind(R.id.titleview)
    TitleView titleView;
    @Bind(R.id.gridview)
    MyGridView myGridView;
    private DataFileAdapter myGridAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_datafile_room;
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
        myGridAdapter = new DataFileAdapter();
        myGridView.setAdapter(myGridAdapter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(DataFileRoomActivity.this, DataListActivity.class).putExtra("position", position));
            }
        });
    }
}
