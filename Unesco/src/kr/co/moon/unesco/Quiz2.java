package kr.co.moon.unesco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class Quiz2 extends Activity implements OnClickListener, Callback {

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
	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	Button mPlayBtn;
	VideoView video;
	ImageView playbtn;
	int bool;
	int lid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz2);
		FirstMenu quizAct = (FirstMenu) FirstMenu.quiz1;
		quizAct.finish();
		quiz1 = this;
		FirstMenu.mp.setVolume(0.0f, 0.0f);
		String tmpSign = "m" + 15;
		lid = this.getResources().getIdentifier(tmpSign, "raw",
				this.getPackageName());
		Log.d("영상id", tmpSign);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
//		mHolder.setFixedSize(LayoutParams.MATCH_PARENT, 0);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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
			mPool.play(mTouch, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Right2.class));
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
			mPool.play(mIntro, 1, 1, 0, 0, 1);
			startActivity(new Intent(this, Wrong.class));
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mPlayer == null) {
			mPlayer = MediaPlayer.create(this, lid);
		} else {
			mPlayer.reset();
		}
		try {
			// mPlayer.setDataSource(getResources(R.raw.m1));
			mPlayer.setDisplay(holder);
			mPlayer.setLooping(true);
			mPlayer.prepare();
		} catch (Exception e) {
		}
		Log.d("dd", "서피스크리트");
		mPlayer.setOnCompletionListener(mComplete);
		mPlayer.setOnVideoSizeChangedListener(mSizeChange);
		mPlayer.start();

	}

	MediaPlayer.OnCompletionListener mComplete = new MediaPlayer.OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			Log.d("dd", "complete");
			

		}
	};

	MediaPlayer.OnVideoSizeChangedListener mSizeChange = new MediaPlayer.OnVideoSizeChangedListener() {

		public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
			// TODO Auto-generated method stub

		}
	};

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPlayer.release();
	}
}
