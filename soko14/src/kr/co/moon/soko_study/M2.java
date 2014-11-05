package kr.co.moon.soko_study;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class M2 extends Activity implements SurfaceHolder.Callback,
		OnClickListener {

	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	Button mPlayBtn;
	Button btnPrevious;
	Button btnNext;
	TextView currentText;
	VideoView video;
	ImageView playbtn;
	int bool;
	int btnId;
	int lid;
	TextView text;
	int current;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m2);

		Intent intent = getIntent();
		btnId = intent.getIntExtra("BTNID", 0);
		bool = intent.getIntExtra("BOOL", 0);

		text = (TextView) findViewById(R.id.textView1);
		currentText = (TextView) findViewById(R.id.current);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		playbtn = (ImageView) findViewById(R.id.playbtn);

		btnNext = (Button) findViewById(R.id.next);
		btnPrevious = (Button) findViewById(R.id.previous);
		btnNext.setOnClickListener(this);
		btnPrevious.setOnClickListener(this);

		mPlayBtn = (Button) findViewById(R.id.play);
		mPlayBtn.setOnClickListener(this);
		current = 0;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = holder;
		playClip(SubMenu1.mId.get(current));

	}

	public void playClip(int num) {
		currentText.setText(num + "¹ø µ¿ÀÛ");
		String tmpSign = "m" + num;
		lid = this.getResources().getIdentifier(tmpSign, "raw",
				this.getPackageName());
		if (mPlayer == null) {
			mPlayer = MediaPlayer.create(this, lid);
		} else {
			mPlayer.reset();
			mPlayer = MediaPlayer.create(this, lid);
		}
		// mPlayer.setDataSource(getResources(R.raw.m1));
		mPlayer.setDisplay(mHolder);
		mPlayer.setLooping(false);
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer.setOnCompletionListener(mComplete);
		mPlayer.setOnVideoSizeChangedListener(mSizeChange);
		mPlayer.start();
	}

	MediaPlayer.OnCompletionListener mComplete = new MediaPlayer.OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			playClip(SubMenu1.mId.get(current++));
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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.play:

			if (mPlayer.isPlaying() == false) {
				mPlayer.start();
				playbtn.setImageResource(R.drawable.pausebtn);
			} else {
				mPlayer.pause();
				playbtn.setImageResource(R.drawable.playbtn);
			}

			break;

		case R.id.previous:
			if (current > 0)
				playClip(SubMenu1.mId.get(--current));
			break;
		case R.id.next:
			if (current < SubMenu1.mId.size())
				playClip(SubMenu1.mId.get(++current));
			break;
		}
		
		Log.d("current", current+"");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPlayer.release();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mPlayer.release();
			finish();
			// TODO Auto-generated method stub

		}
		return super.onKeyDown(keyCode, event);
	}
}
