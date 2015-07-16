package com.mycompany.myapp2;

import android.content.*;
import android.widget.*;

public class MReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context p1, Intent p2)
	{
		// TODO: Implement this method
		SharedPreferences p=p1.getSharedPreferences("get",Context.MODE_PRIVATE);
		if(!p.getBoolean("get",false)){
			Intent inte=new Intent();
			inte.setClass(p1,MService.class);
			inte.setFlags(Intent.FLAG_FROM_BACKGROUND);
			p1.startService(inte);
		}
		Toast.makeText(p1,"c",100).show();
	}

}
