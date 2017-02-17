package cn.cassan.pm.widget;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class HolderTextView extends TextView {

    public HolderTextView(Context context) {
        super(context);
        setTextSize(getResources().getDimension(cn.cassan.pm.R.dimen.text_size_10));
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding((int) getResources().getDimension(cn.cassan.pm.R.dimen.text_size_30), 10,
                0, 10);
    }
}
