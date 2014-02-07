package kr.co.moon.unesco;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Right5 extends Activity implements OnClickListener {
	private int mSuccess;
	private SoundPool mPool;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.right);
	    // TODO Auto-generated method stub
	    Button btn = (Button)findViewById(R.id.blankBtn);
	    btn.setOnClickListener(this);
	    mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	    mSuccess = mPool.load(this, R.raw.success, 1);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		mPool.play(mSuccess, 1, 1, 0, 0, 1);
		startActivity(new Intent(this,Goal.class));
		finish();
	}

}
