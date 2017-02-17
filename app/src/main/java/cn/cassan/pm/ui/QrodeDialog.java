package cn.cassan.pm.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.google.zxing.WriterException;
import org.kymjs.kjframe.utils.FileUtils;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.util.QrCodeUtils;

public class QrodeDialog extends Dialog {

    private ImageView mIvCode;
    private Bitmap bitmap;

    private QrodeDialog(Context context, boolean flag, OnCancelListener listener) {
        super(context, flag, listener);
    }

    @SuppressLint("InflateParams")
    private QrodeDialog(Context context, int defStyle) {
        super(context, defStyle);
        View contentView = getLayoutInflater().inflate(
                cn.cassan.pm.R.layout.dialog_my_qr_code, null);
        mIvCode = (ImageView) contentView.findViewById(cn.cassan.pm.R.id.iv_qr_code);
        try {
            bitmap = QrCodeUtils.Create2DCode(String.format("http://my.oschina.net/u/%s", AppContext.getInstance().getLoginUid()));
            mIvCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mIvCode.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dismiss();
                if (FileUtils.bitmapToFile(bitmap, FileUtils.getSavePath("OSChina") + "/myqrcode.png")) {
                    AppContext.showToast("二维码已保存到oschina文件夹下");
                } else {
                    AppContext.showToast("SD卡不可写，二维码保存失败");
                }
                return false;
            }
        });

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                QrodeDialog.this.dismiss();
                return false;
            }
        });
        super.setContentView(contentView);
    }

    public QrodeDialog(Context context) {
        this(context, cn.cassan.pm.R.style.quick_option_dialog);
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.CENTER);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(p);
    }
}
