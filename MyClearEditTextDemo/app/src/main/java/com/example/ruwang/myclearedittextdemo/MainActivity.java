package com.example.ruwang.myclearedittextdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.ruwang.myclearedittextdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EditMoudle modle = new EditMoudle();
        mBinding.setModle(modle);
        modle.setPadding(mBinding.btn,200);
        mBinding.jiami.setText(SHA1.sha1("12341235"));//密码加密
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
