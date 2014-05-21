package a.kr.co.moon.chunk;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Responser {
	private String result;

	public void getResponse(final String paramString) {
		Thread localThread = new Thread(new Runnable() {
			public void run() {
				HttpGet localHttpGet = new HttpGet(paramString);
				DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
				BasicResponseHandler localBasicResponseHandler = new BasicResponseHandler();
				try {
					Responser.this.result = ((String) localDefaultHttpClient
							.execute(localHttpGet, localBasicResponseHandler))
							.trim();
					return;
				} catch (Exception localException) {
					localException.printStackTrace();
				}
			}
		});
		localThread.start();
		try {
			localThread.join();
			localThread.interrupt();
			return;
		} catch (InterruptedException localInterruptedException) {
			for (;;) {
				localInterruptedException.printStackTrace();
			}
		}
	}

	public String getResult() {
		return this.result;
	}
}
