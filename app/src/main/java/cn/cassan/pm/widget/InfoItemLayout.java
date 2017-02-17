package cn.cassan.pm.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cassan.pm.R;
import cn.cassan.pm.util.Helper;


public class InfoItemLayout extends LinearLayout {

    private MyEditText editRight;
    public TextView textRight;
    private Context mContext;
    private ImageView rightImage;

    public InfoItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View.inflate(context, R.layout.layout_infoitem, this);
        editRight = (MyEditText) findViewById(R.id.editRight);
        editRight.setIconVisible(false);
        textRight = (TextView) findViewById(R.id.textRight);
        rightImage = (ImageView) findViewById(R.id.rightImage);

        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.InfoItemLayout);
        if (typed != null) {
            boolean TopLongLine = typed.getBoolean(R.styleable.InfoItemLayout_TopLongLine, false);
            if (TopLongLine) {
                findViewById(R.id.topLongLine).setVisibility(View.VISIBLE);
            }

            boolean TopshortLine = typed.getBoolean(R.styleable.InfoItemLayout_TopshortLine, false);
            if (TopshortLine) {
                findViewById(R.id.topShortLine).setVisibility(View.VISIBLE);
            }

            TextView LeftText = (TextView) findViewById(R.id.leftText);
            LeftText.setText(typed.getResourceId(R.styleable.InfoItemLayout_LeftText, 0));

            boolean BottomLongLine = typed.getBoolean(R.styleable.InfoItemLayout_BottomLongLine, false);
            if (BottomLongLine) {
                findViewById(R.id.bottomLongLine).setVisibility(View.VISIBLE);
            }
            String hintText = typed.getString(R.styleable.InfoItemLayout_hintText);
            if (hintText != null) {
                textRight.setVisibility(VISIBLE);
                textRight.setHint(hintText);
            }
            boolean isImage = typed.getBoolean(R.styleable.InfoItemLayout_isImage, false);
            if (isImage) {
                rightImage.setVisibility(View.VISIBLE);
            } else {
                textRight.setVisibility(View.VISIBLE);
            }
            typed.recycle();
        }
    }

    public void setRightTextVisable(boolean isImage) {
        if (isImage) {
            editRight.setVisibility(GONE);
            textRight.setVisibility(VISIBLE);
        } else {
            textRight.setVisibility(GONE);
            editRight.setVisibility(VISIBLE);
        }

    }
    public void setRightImage(String url) {
        if (!url.startsWith("http")) {
            url = "file://" + url;
        }
        ImageLoader.getInstance().displayImage(url, rightImage, Helper.getCircleOptions(R.drawable.defaultavatar));
    }
    public void setRightText(String text) {

        textRight.setText(text);
    }

    public String getRightText() {

        return textRight.getText().toString();
    }

    public void setFocus() {

        editRight.setFocusable(true);
        editRight.setFocusableInTouchMode(true);
        editRight.requestFocus();
        //        Helper.showSoftInput();
    }


    public void setEditVisible(boolean visibility) {
        if (visibility) {
            editRight.setVisibility(View.VISIBLE);
        } else {
            editRight.setVisibility(View.GONE);

        }
    }

    public String getEditText() {

        if (editRight.isShown()) {
            return editRight.getText().toString().trim();
        } else {
            return null;
        }
    }

}
