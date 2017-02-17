package cn.cassan.pm.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import cn.cassan.pm.R;


/**
 * @author Created by zhihao on 2016/10/22.
 * @describe 带有左侧和右侧图标的editText
 * @version_
 **/
public class MyEditText extends EditText {

    //右侧删除图标
    private Drawable mDrawable = null;
    private int initLeftId = 0;
    private int initRightId = 0;
    private String initHint;
    private float upX = 0;
    private boolean isClear = true;
    private boolean isHide = true;


    public void setIconVisible(boolean isVisible) {
        if (isVisible) {
            setCompoundDrawablesWithIntrinsicBounds(initLeftId, 0, initRightId, 0);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(initLeftId, 0, 0, 0);
        }
    }

    public void setClear(boolean isClear) {
        this.isClear = isClear;
        if (isClear) {
            initRightId = R.drawable.btn_clear_input_normal;
        } else {
            initRightId = R.drawable.see_num_selector;
        }
        setCompoundDrawablesWithIntrinsicBounds(initLeftId, 0, initRightId, 0);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
        if (typed != null) {
            initLeftId = typed.getResourceId(R.styleable.MyEditText_drawLeft, 0);
            initRightId = typed.getResourceId(R.styleable.MyEditText_drawRight, 0);
            typed.recycle();
        }
        setIconVisible(initRightId == R.drawable.see_num_selector);
        mDrawable = context.getResources().getDrawable(R.drawable.btn_clear_input_normal);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                setIconVisible(s.length() > 0);

                updateCleanable(length(), true);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(), hasFocus);

            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //
        //        switch (event.getAction()) {
        //            case MotionEvent.ACTION_UP:
        //                if (listener == null) {
        //                    break;
        //                }
        //                upX = event.getX();
        //                if ((getWidth() - upX) <= getCompoundPaddingRight()) {
        //                    if (length() > 0) {
        //                        if (isClear) {
        //                            setText("");
        //
        //                            if (getHint() != null) {
        //                                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right, 0);
        //                            }
        //
        //                            listener.clear();
        //                            return false;
        //                        } else {
        //                            if (isHide) {
        //                                //隐藏
        //                                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //                            } else {
        //                                //可见
        //                                setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        //                            }
        //                            isHide = !isHide;
        //                            listener.seeNum(getText().toString().trim());
        //
        //                            return false;
        //                        }
        //                    }
        //                } else if ((upX) <= getCompoundPaddingLeft()) {
        //
        //                    listener.back();
        //                    return false;
        //                }
        //                break;
        //        }
        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:

                final int DRAWABLE_RIGHT = 2;
                //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
                Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (rightIcon != null) {
                    //检查点击的位置是否是右侧的删除图标
                    //注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
                    int leftEdgeOfRightDrawable = getRight() - getPaddingRight()
                            - rightIcon.getBounds().width();
                    if (event.getRawX() >= leftEdgeOfRightDrawable) {
                        if (isClear) {
                            setText("");
                        } else {

                            if (isHide) {
                                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            } else if (!isHide) {
                                //可见
                                setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            }
                            isHide = !isHide;
                        }
                    }
                }


                break;

        }

        return super.onTouchEvent(event);

    }

    //当内容不为空，而且获得焦点，才显示右侧删除按钮
    public void updateCleanable(int length, boolean hasFocus) {
        if (isClear) {
            if (length() > 0 && hasFocus)
                setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
            else
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }


    @Override
    protected void finalize() throws Throwable {
        mDrawable = null;
        super.finalize();
    }


}