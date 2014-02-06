package a.com.moon.baedalmal;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m1);

		Intent intent = getIntent();
		String chunk = intent.getStringExtra("CHUNK");
		String chapter = intent.getStringExtra("CHAPTER");

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		String tmpSign = "v" + chapter + chunk;
		Log.d("영상소스", tmpSign);
		lid = this.getResources().getIdentifier(tmpSign, "raw",
				this.getPackageName());

		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

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
			/*
			 * AlertDialog.Builder dlg = new AlertDialog.Builder(M1.this);
			 * dlg.setTitle("notice"); dlg.setMessage("Replay?");
			 * dlg.setIcon(android.R.drawable.ic_search_category_default);
			 * dlg.setPositiveButton("replay", new
			 * DialogInterface.OnClickListener() { public void
			 * onClick(DialogInterface dialog, int whichButton) {
			 * 
			 * mPlayer.reset(); mPlayer = MediaPlayer.create(M1.this, lid); try
			 * { mPlayer.prepare(); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 * 
			 * Log.d("dd", "서피스크리트"); mPlayer.start();
			 * 
			 * } }); dlg.setNegativeButton("no", new
			 * DialogInterface.OnClickListener() { public void
			 * onClick(DialogInterface dialog, int whichButton) {
			 * mPlayer.release(); finish(); } }); dlg.setCancelable(true);
			 * dlg.show();
			 */
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

	}
}
