package kr.moon.chunk2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView_Client extends WebViewClient {

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (url.contains("google")) { // 외부로 보낼 조건
			view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			return true;
		} else {

			return false;
		}

	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);

		MainActivity.dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		MainActivity.dialog1.setMessage("잠시만 기다려 주세요.");
		MainActivity.dialog1.setCancelable(true);
		MainActivity.dialog1.setProgress(30);
		// Act2.dialog1.setButton("취소", new DialogInterface.OnClickListener(){
		// public void onClick(DialogInterface dialog, int which){
		// dialog.cancel();
		// }
		// });
		MainActivity.dialog1.show();

	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// TODO Auto-generated method stub
		super.onReceivedError(view, errorCode, description, failingUrl);

		MainActivity.toast.show();
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		MainActivity.dialog1.cancel();
	}

}
