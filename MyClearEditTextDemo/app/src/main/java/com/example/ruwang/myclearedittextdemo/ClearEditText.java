package com.example.ruwang.myclearedittextdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * <b>Create Date:</b> 17/3/27<br>
 * <b>Author:</b> Zhanglei<br>
 * <b>Description:</b>登录界面手机号码自定义输入框，可删除！ <br>
 */

public class ClearEditText extends EditText implements TextWatcher, View.OnFocusChangeListener {

    private static String TAG = ClearEditText.class.getSimpleName();

    private Drawable mDrawable;//删除图标

    private boolean hasFocus;//是否获取到焦点

    private boolean isPhone;//是否是手机号类型

    private int beforeLen = 0;//变化前的长度

    private int afterLen = 0;//变化后的长度

    private int color;

    private Paint mPaint;


    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3.0f);
        color = Color.parseColor("#bfbfbf");
        mDrawable = getCompoundDrawables()[2];//上下左右四个图标，左0，上1，


        if (mDrawable == null) {
            mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.base_delete_normal_red);
        }
        if (mDrawable.getIntrinsicWidth() != -1) {
            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }


        setClearIconVisible(false);

        setOnFocusChangeListener(this);

        addTextChangedListener(this);

        isPhone = (getInputType() == InputType.TYPE_CLASS_PHONE);//必须在xml文件中写inputType属性为phone
        if (isPhone) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});//设置输入填充的长度
        }


    }

    /**
     * 设置删除图标是否可见
     * @param b
     */
    private void setClearIconVisible(boolean b) {
        Drawable right = b ? mDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeLen = s.length();
    }

    @Override
    public void afterTextChanged(Editable s) {
        afterLen = s.length();
        boolean can = beforeLen > afterLen;//表示删除
        if (isPhone) {
            if (!can) {
                int position = ClearEditText.this.getSelectionEnd();//获取最后字符的位置
                if (position == 4 || position == 9) {
                    s = s.insert(position - 1, " ");
                }
            } else {
                if (afterLen == 4) {
                    s = s.delete(3, 4);
                }
                if (afterLen == 9) {
                    s = s.delete(8, 9);
                }
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);//输入内容的监听，如果输入框内有内容，显示可见
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 监听
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < (getWidth() - getPaddingRight()));
                if (touchable) {
                    this.setText("");
                }
            }

        }
        return super.onTouchEvent(event);

    }
}
