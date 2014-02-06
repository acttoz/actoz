package a.com.moon.baedalmal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Intro extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// TODO Auto-generated method stub
		ImageView intro = (ImageView) findViewById(R.id.intro);
		intro.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
//		if (!dbCopied) {
//			startActivity(new Intent(this, COPYDB.class));
//		} else {
			startActivity(new Intent(this, List.class));
//		}
		finish();

	}

}
