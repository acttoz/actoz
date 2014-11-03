package kr.co.moon.soko_study;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class M1 extends Activity implements SurfaceHolder.Callback,
		OnClickListener {

	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	Button mPlayBtn;
	VideoView video;
	ImageView playbtn;
	int bool;
	int btnId;
	int lid;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m1);

		Intent intent = getIntent();
		btnId = intent.getIntExtra("BTNID", 0);
		bool = intent.getIntExtra("BOOL", 0);

		TextView text = (TextView) findViewById(R.id.textView1);
		
		if(bool == 1){
			if(btnId == 5)
				text.setText(1 + "번 동작");
			if(btnId == 8)
				text.setText(2 + "번 동작");
			if(btnId == 9)
				text.setText(3 + "번 동작");
			if(btnId == 10)
				text.setText(4 + "번 동작");
			if(btnId == 12)
				text.setText(5 + "번 동작");
			if(btnId == 21)
				text.setText(6 + "번 동작");
				
			
		}else if(bool == 2){
			if (btnId == 33) {
				text.setText("전체 동작");
			} else {
				text.setText(btnId + "번 동작");
			}
			
		}

		

		String tmpSign = "m" + btnId;

		lid = this.getResources().getIdentifier("m33", "raw",
				this.getPackageName());

		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		playbtn = (ImageView) findViewById(R.id.playbtn);
		
		Button backbtn = (Button)findViewById(R.id.button1);
		backbtn.setOnClickListener(this);
		
		mPlayBtn = (Button) findViewById(R.id.play);
		mPlayBtn.setOnClickListener(this);

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
			mPlayer.setOnCompletionListener(mComplete);
			mPlayer.setOnVideoSizeChangedListener(mSizeChange);
		} catch (Exception e) {
		}

		mPlayer.start();
	}

	MediaPlayer.OnCompletionListener mComplete = new MediaPlayer.OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
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
			
		case R.id.button1:
			mPlayer.release();
			finish();
			
			
		}
		
		
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
