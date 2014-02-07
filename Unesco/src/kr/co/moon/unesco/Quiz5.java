package kr.co.moon.unesco;

import kr.co.moon.unesco.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Quiz5 extends Activity implements OnClickListener {

	Intent intent;
	static Typeface mTypeface = null;
	public static Activity quiz1;
	public static Activity quiz2;
	public static Activity quiz3;
	public static Activity quiz4;
	public static Activity quiz5;
	private SoundPool mPool;
	private int mTouch;
	private int mIntro;
	private int mSuccess;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstmenu);
		Quiz4 quizAct = (Quiz4) Quiz4.quiz1;
		quizAct.finish();
		LinearLayout background = (LinearLayout) findViewById(R.id.back);
		background.setBackgroundResource(R.drawable.quiz5);
		quiz1 = this;
		Button imageButton1 = (Button) findViewById(R.id.imageButton1);
		imageButton1.setOnClickListener(this);
		Button imageButton2 = (Button) findViewById(R.id.imageButton2);
		imageButton2.setOnClickListener(this);
		Button imageButton3 = (Button) findViewById(R.id.imageButton3);
		imageButton3.setOnClickListener(this);
		Button imageButton4 = (Button) findViewById(R.id.imageButton4);
		imageButton4.setOnClickListener(this);
		Button imageButton5 = (Button) findViewById(R.id.imageButton5);
		imageButton5.setOnClickListener(this);
		if (mTypeface == null) {
			mTypeface = Typeface.createFromAsset(getAssets(), "font.ttf");
		}
		mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mTouch = mPool.load(this, R.raw.touch, 1);
		mIntro = mPool.load(this, R.raw.intro, 1);
		imageButton1.setTypeface(mTypeface);
		imageButton2.setTypeface(mTypeface);
		imageButton3.setTypeface(mTypeface);
		imageButton4.setTypeface(mTypeface);
		imageButton5.setTypeface(mTypeface);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.imageButton1:
			mPool.play(mIntro, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Wrong.class));
			break;
		}

		switch (v.getId()) {
		case R.id.imageButton2:
			mPool.play(mIntro, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Wrong.class));
			break;
		}

		switch (v.getId()) {
		case R.id.imageButton3:
			mPool.play(mIntro, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Wrong.class));
			break;
		}
		switch (v.getId()) {
		case R.id.imageButton4:
			mPool.play(mTouch, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Goal.class));
			break;
		}
		switch (v.getId()) {
		case R.id.imageButton5:
			mPool.play(mIntro, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Wrong.class));
			break;
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	 
}
