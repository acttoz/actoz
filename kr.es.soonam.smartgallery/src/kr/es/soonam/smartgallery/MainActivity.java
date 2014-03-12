package kr.es.soonam.smartgallery;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private SoundPool mPool;
	private int mTouch;
	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mTouch = mPool.load(this, R.raw.touch, 1);

		ImageButton btn1 = (ImageButton) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		ImageButton btn2 = (ImageButton) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		ImageButton btn3 = (ImageButton) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);
		ImageButton btn4 = (ImageButton) findViewById(R.id.btn4);
		btn4.setOnClickListener(this);

		intent = new Intent(this, Act5.class);

	}

	@Override
	public void onClick(View v) {
		mPool.play(mTouch, 1, 1, 0, 0, 1);
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn1:

			intent.putExtra("ID", 1);
			startActivity(intent);
			break;
		case R.id.btn2:
			intent.putExtra("ID", 2);
			startActivity(intent);
			break;
		case R.id.btn3:
			intent.putExtra("ID", 3);
			startActivity(intent);
			break;
		case R.id.btn4:
			intent.putExtra("ID", 4);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			System.exit(0);
			// TODO Auto-generated method stub

		}
		return super.onKeyDown(keyCode, event);
	}

}
