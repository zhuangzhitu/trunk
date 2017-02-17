package cn.cassan.pm.ui.workbench;

import android.view.View;

import cn.cassan.pm.R;
import cn.cassan.pm.base.BaseBackActivity;

public class DocumentActivity extends BaseBackActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.actionbar_title_document;
    }

    @Override
    public void onClick(View v) {

    }
     @Override
    public void initData()
     {
//         mRequestObject = PermissionUtil.with(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).onAllGranted(
//                 new Func() {
//                     @Override protected void call() {
//                         //Happy Path
//                     }
//                 }).onAnyDenied(
//                 new Func() {
//                     @Override protected void call() {
//                         //Sad Path
//                     }
//                 }).ask(REQUEST_CODE_STORAGE); // REQUEST_CODE_STORAGE is what ever int you want (should be distinct)
//
//         mRequestObject.onRequestPermissionsResult(requestCode, permissions, grantResults);
     }
}
