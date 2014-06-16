package kr.moon.chunk2;

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
		ImageView link1_2 = (ImageView) findViewById(R.id.link1_2);
		ImageView link1_3 = (ImageView) findViewById(R.id.link1_3);
		ImageView link2_1 = (ImageView) findViewById(R.id.link2_1);
		ImageView link2_2 = (ImageView) findViewById(R.id.link2_2);
		ImageView link2_3 = (ImageView) findViewById(R.id.link2_3);
		ImageView link3_1 = (ImageView) findViewById(R.id.link3_1);
		ImageView link3_2 = (ImageView) findViewById(R.id.link3_2);
		ImageView link3_3 = (ImageView) findViewById(R.id.link3_3);
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
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0abVy")));
			}
		});
		link1_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0avaz")));
			}
		});
		link1_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		link2_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/094c3")));
			}
		});
		link2_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0abVw")));
			}
		});
		link2_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0avab")));
			}
		});
		link3_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0abVu")));
			}
		});
		link3_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0avag")));
			}
		});
		link3_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://m.site.naver.com/0avai")));
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
