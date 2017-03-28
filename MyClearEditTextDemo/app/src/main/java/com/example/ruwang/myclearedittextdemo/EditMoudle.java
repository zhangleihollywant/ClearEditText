package com.example.ruwang.myclearedittextdemo;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * <b>Create Date:</b> 17/3/27<br>
 * <b>Author:</b> Zhanglei<br>
 * <b>Description:</b>附加上常用的正则帮助类 <br>
 */

public class EditMoudle extends BaseObservable {

    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    public ObservableField<String> number = new ObservableField<>();
    public ObservableInt type = new ObservableInt();
    public ObservableField<String> password = new ObservableField<>();


    public EditMoudle() {
        type.set(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    /*
    @BindingAdapter({"regular", "errorMsg"})
    public static void editRule(EditText editText, final String regularExpression, final String errorMsg) {
        final Context context = editText.getContext();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.toString()
                            .subSequence(s.length() - 1, s.length())
                            .toString()
                            .matches(regularExpression)
                            ) {

                    } else {
                        Toast.makeText(context, "不匹配", Toast.LENGTH_SHORT).show();
                        s.delete(s.length() - 1, s.length());
                    }
                }
            }
        });
    }
*/

    public void onClickImg(View view) {
        ImageView imageView = (ImageView) view;
        Drawable hindImg = ContextCompat.getDrawable(view.getContext(), R.drawable.user_login_pwdhint_icon);
        Drawable showImg = ContextCompat.getDrawable(view.getContext(), R.drawable.user_login_pwdunhint_icon);

        int tag = type.get();
        if (tag == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {//显示的情况
            type.set(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageDrawable(showImg);
        } else if (tag == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            type.set(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageDrawable(hindImg);
        }
    }


    public void onBtnClick(View v) {
        String s = number.get().toString();
        //里面有空格,匹配不上 trim（）方便也没用的，必须删除拼装
//        String s1 = text.get();//进行验证的输入框
        //进行拆分组装
        String[] split = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(split[i]);
        }

        if (isPhone(sb.toString())) {
            Toast.makeText(v.getContext(), "yes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(v.getContext(), "nonono", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isMatch(String regex, CharSequence sequence) {
        return sequence != null && sequence.length() > 0 && Pattern.matches(regex, sequence);
    }

    public boolean isPhone(CharSequence sequence) {
        return isMatch(REGEX_MOBILE_EXACT, sequence);
    }
}
