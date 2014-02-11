package a.kr.co.moon.result;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class About extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		// TODO Auto-generated method stub
		
		TextView tv_contents = (TextView) findViewById(R.id.title1);
		tv_contents.setTypeface(SpeedAlimActivity.mTypeface);
		
		
		final ImageButton voteBtn = (ImageButton) findViewById(R.id.vote_btn);
		voteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri = Uri
						.parse("market://details?id=a.kr.co.moon.result");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				startActivity(intent);
			}
		});

		final ImageButton changeId = (ImageButton) findViewById(R.id.firstLogin);
		changeId.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
