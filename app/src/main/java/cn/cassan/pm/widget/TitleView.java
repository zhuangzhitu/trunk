package cn.cassan.pm.widget;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.cassan.pm.R;

public class TitleView extends RelativeLayout implements OnClickListener {

	public static final String IMAGELEFT = "image_left";
	public static final String IMAGERIGHT = "image_right";
	public static final String TEXTLEFT = "text_left";
	public static final String TEXTRIGHT = "text_right";

	private TitleClickListener listener;

	public void setTitleClickListener(TitleClickListener listener) {
		this.listener = listener;
	}

	private TextView textLeft, title, textRight;
	private ImageView imageLeft, imageRight;

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);

		View.inflate(context, R.layout.titleview, this);

		textLeft = (TextView) findViewById(R.id.text_left);
		title = (TextView) findViewById(R.id.text_title);
		textRight = (TextView) findViewById(R.id.text_right);
		imageLeft = (ImageView) findViewById(R.id.image_left);
		imageRight = (ImageView) findViewById(R.id.image_right);

		TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
		if (typed != null) {
			int iconLeft = typed.getResourceId(R.styleable.TitleView_ImageLeft, 0);
			if (iconLeft != 0) {
				setImageLeft(iconLeft);
			}

			int iconRight = typed.getResourceId(R.styleable.TitleView_ImageRight, 0);
			if (iconRight != 0) {
				setImageRight(iconRight);
			}
			int drawLeft = typed.getResourceId(R.styleable.TitleView_drawLeft, 0);
			if (drawLeft != 0) {
				setDrawLeft(drawLeft);
			}

			int drawRight = typed.getResourceId(R.styleable.TitleView_drawRight, 0);
			if (drawRight != 0) {
				setDrawRight(drawRight);
			}

			int strLeft = typed.getResourceId(R.styleable.TitleView_TextLeft, 0);
			if (strLeft != 0) {
				setTextLeft(strLeft);
			}

			int strRight = typed.getResourceId(R.styleable.TitleView_TextRight, 0);
			if (strRight != 0) {
				setTextRight(strRight);
			}

			int strTitle = typed.getResourceId(R.styleable.TitleView_TitleText, 0);
			if (strTitle != 0) {
				title.setText(strTitle);
			}

			typed.recycle();
		}
	}

	private void setDrawLeft(int drawLeft) {

		textLeft.setCompoundDrawablesWithIntrinsicBounds(drawLeft, 0, 0, 0);
	}

	private void setDrawRight(int drawRight) {

		textRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawRight, 0);
	}

	public void setTitle(String text) {
		title.setText(text);
	}

	public void setImageLeft(int iconLeft) {

		imageLeft.setImageResource(iconLeft);
		imageLeft.setVisibility(View.VISIBLE);
		imageLeft.setOnClickListener(this);
	}

	public void setImageRight(int iconRight) {

		imageRight.setImageResource(iconRight);
		imageRight.setVisibility(View.VISIBLE);
		imageRight.setOnClickListener(this);
	}

	public void setTextLeft(int strLeft) {

		setTextLeft(getContext().getString(strLeft));
	}

	public void setTextLeft(String text) {

		textLeft.setText(text);
		textLeft.setVisibility(View.VISIBLE);
		textLeft.setOnClickListener(this);
	}

	public void setTextRight(int strRight) {

		setTextRight(getContext().getString(strRight));
	}

	public void setTextRight(String text) {

		textRight.setText(text);
		textRight.setVisibility(View.VISIBLE);
		textRight.setOnClickListener(this);
	}

	public void setRightVisible(boolean istrue) {
		if (istrue == true) {
			imageRight.setVisibility(View.VISIBLE);
		}
	}

	public String getTextRight() {
		return textRight.getText().toString().trim();
	}

	public String getTextLeft() {
		return textLeft.getText().toString().trim();

	}


	@Override
	public void onClick(View v) {

		int id = v.getId();
		if (id == R.id.text_left) {
			if (listener != null) {
				listener.onClick(TEXTLEFT);
			}
		} else if (id == R.id.text_right) {
			if (listener != null) {
				listener.onClick(TEXTRIGHT);
			}
		} else if (id == R.id.image_left) {
			if (listener != null) {
				listener.onClick(IMAGELEFT);
			} else {
				if (v.getContext() instanceof Activity) {
					((Activity) v.getContext()).onBackPressed();
				}
			}
		} else if (id == R.id.image_right) {
			if (listener != null) {
				listener.onClick(IMAGERIGHT);
			}
		}
	}
	
	public void hideImageRight(){
		imageRight.setVisibility(View.INVISIBLE);
	}
	
	public void hideTextLeft(){
		textLeft.setVisibility(View.INVISIBLE);
	}
	
	public interface TitleClickListener {
		public void onClick(String action);
	}
}
