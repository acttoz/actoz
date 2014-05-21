package kr.moon.chunk2;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.util.Log;

public class HttpBuilder{
	private String result;

	private static final int SUCESS_ADMIT = 0;
	private static final int FAIL_ADMIT = 1;
	
	public void loginResponse(final String id , final String pw, final String url, final Handler handler){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub 
				HttpPost request = makeHttpPost(id,pw,url);
				HttpClient client = new DefaultHttpClient();
				ResponseHandler<String> rsHandler = new BasicResponseHandler();
				try {
					result = client.execute(request,rsHandler);
					Log.e("test",result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (result.equals("sucess")) {
					handler.sendEmptyMessage(SUCESS_ADMIT);
				} else {
					handler.sendEmptyMessage(FAIL_ADMIT);
				}
			}
		}).start();
		
		
	}
	
	public HttpPost makeHttpPost(String $myid , String $myPw , String $url) {
		HttpPost request = new HttpPost($url);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", $myid));
		nameValuePairs.add(new BasicNameValuePair("pass", $myPw));
		request.setEntity(makeEntity(nameValuePairs));
		
		return request;
	}
	
	public HttpEntity makeEntity(ArrayList<NameValuePair> $nameValue) {
		HttpEntity result = null;
		try {
			result = new UrlEncodedFormEntity($nameValue, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
