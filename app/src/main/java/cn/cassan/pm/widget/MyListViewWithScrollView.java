package cn.cassan.pm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListViewWithScrollView extends ListView {

	public MyListViewWithScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyListViewWithScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListViewWithScrollView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mEpandsec= MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mEpandsec);
	}

}
