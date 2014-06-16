package a.kr.co.moon.chunk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class M1 extends Activity implements SurfaceHolder.Callback,
		OnClickListener {
	int lid;
	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	Button mPlayBtn;
	VideoView video;
	ImageView playbtn;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	String checkId;
	int checkNum;
	String chapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m1);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		Intent intent = getIntent();
		String chunk = intent.getStringExtra("CHUNK");
		chapter = intent.getStringExtra("CHAPTER");
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		checkId = "CHECK" + chapter + chunk;
		checkNum = idPrefs.getInt(checkId, 0);

		String tmpSign = "v" + chapter + chunk;
		Log.d("영상소스", tmpSign);
		lid = this.getResources().getIdentifier(tmpSign, "raw",
				this.getPackageName());

		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		playbtn = (ImageView) findViewById(R.id.playbtn);
		mPlayBtn = (Button) findViewById(R.id.play);
		mPlayBtn.setOnClickListener(this);

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mHolder = holder;
		if (mPlayer == null) {
			mPlayer = MediaPlayer.create(this, lid);
		} else {
			mPlayer.reset();
		}
		try {
			// mPlayer.setDataSource(getResources(R.raw.m1));
			mPlayer.setDisplay(holder);
			mPlayer.setLooping(false);
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
			if (checkNum < 5) {
				editor.putInt(checkId, ++checkNum);
				editor.commit();
			}
			Toast.makeText(M1.this, "청크 1회, 10 points Up!", Toast.LENGTH_SHORT)
					.show();
			List.pointSetter.setPoint(10);
			Log.d("point", List.pointSetter.getPoint() + "");
			AlertDialog.Builder dlg = new AlertDialog.Builder(M1.this);
			dlg.setTitle("notice");
			dlg.setMessage("Replay?");
			dlg.setIcon(android.R.drawable.ic_search_category_default);

			dlg.setPositiveButton("replay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// mPlayer.reset();
							Intent intent = getIntent();
							mPlayer.release();
							finish();
							startActivity(intent);
						}
					});

			dlg.setNegativeButton("no", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					mPlayer.release();
					finish();
				}
			});

//			dlg.setNeutralButton("원문보기", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int whichButton) {
//					Intent activityIntent3 = new Intent(M1.this,
//							Intonation.class);
//					activityIntent3.putExtra("CHAPTER", chapter);
//					finish();
//					startActivity(activityIntent3);
//				}
//			});

			dlg.setCancelable(true);
			dlg.show();

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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mPlayer.release();
			finish();
			// TODO Auto-generated method stub

		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.play:

			if (mPlayer.isPlaying() == false) {
				mPlayer.start();
				playbtn.setImageResource(R.drawable.btn_pause);
			} else {
				mPlayer.pause();
				playbtn.setImageResource(R.drawable.btn_play);
			}

			break;

		case R.id.button1:
			mPlayer.release();
			finish();
		}
	}
}
