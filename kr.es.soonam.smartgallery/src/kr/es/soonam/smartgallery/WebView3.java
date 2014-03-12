package kr.es.soonam.smartgallery;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView3 extends WebViewClient {

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);
		
		 
		 Act5.dialog3.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 Act5.dialog3.setMessage("잠시만 기다려 주세요.");
		 Act5.dialog3.setCancelable(true);
		 Act5.dialog3.setProgress(30);
//		 Act2.dialog2.setButton("취소", new DialogInterface.OnClickListener(){
//			 public void onClick(DialogInterface dialog, int which){
//				 dialog.cancel();
//			 }
//		 });
		 Act5.dialog3.show();
		 
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// TODO Auto-generated method stub
		super.onReceivedError(view, errorCode, description, failingUrl);
		
		Act5.toast2.show();
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		Act5.dialog3.cancel();
	}

}
