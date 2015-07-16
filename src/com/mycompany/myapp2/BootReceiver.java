package com.mycompany.myapp2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mycompany.myapp2.MService;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context p1, Intent p2)
	{  
	SharedPreferences p=p1.getSharedPreferences("get",Context.MODE_PRIVATE);
	if(!p.getBoolean("get",false)){
	Intent inte=new Intent();
	inte.setClass(p1,MService.class);
	inte.setFlags(Intent.FLAG_FROM_BACKGROUND);
	p1.startService(inte);
	}
			}

}

