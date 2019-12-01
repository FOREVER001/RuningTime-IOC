package com.client.iocdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.client.ioc.InjectTools;
import com.client.ioc.annotation.BindView;
import com.client.ioc.annotation.ContentView;
import com.client.ioc.annotation.OnClick;
import com.client.ioc.annotation_common.OnClickCommon;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView mTextView;
    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectTools.inject(this);
       mTextView.setText("哈哈哈，注入控件成功--tv");
        tv2.setText("哈哈哈，注入控件成功tv2");
    }
    @OnClick(R.id.tv)
    public void show(){
        Toast.makeText(this, "我是tv1", Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.tv2)
    public void onClick(){
        Toast.makeText(this, "我是tv2", Toast.LENGTH_SHORT).show();
    }
    @OnClickCommon(R.id.btn_common)
    public void commonClick(){  // 动态点击事件
        Toast.makeText(this, "动态点击事件", Toast.LENGTH_SHORT).show();
    }
    @OnClickCommon(R.id.btn_common_long)
    public void commonLongClick(){ // 动态长按点击事件
        Toast.makeText(this, "动态长按点击事件", Toast.LENGTH_SHORT).show();

    }
}
