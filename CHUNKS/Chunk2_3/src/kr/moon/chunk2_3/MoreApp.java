package kr.moon.chunk2_3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MoreApp extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moreapp);
		// TODO Auto-generated method stub
		ImageView link1_1 = (ImageView) findViewById(R.id.link1_1);
		ImageView link2_1 = (ImageView) findViewById(R.id.link2_1);
		ImageView link2_2 = (ImageView) findViewById(R.id.link2_2);
		ImageView link3_1 = (ImageView) findViewById(R.id.link3_1);
		ImageView more_x = (ImageView) findViewById(R.id.more_x);

		// link.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
		// }
		// });
		link1_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.site.naver.com/0abVy")));
			}
		});
		link2_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.site.naver.com/094c3")));
			}
		});
		link2_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.site.naver.com/0abVw")));
			}
		});
		link3_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.site.naver.com/0abVu")));
			}
		});
		more_x.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
