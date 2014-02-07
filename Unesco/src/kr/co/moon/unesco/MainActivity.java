package kr.co.moon.unesco;

import kr.co.moon.unesco.R;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	static MediaPlayer bgm = null;
	public static Activity mainAct;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton btn0 = (ImageButton) findViewById(R.id.btn0);
		ImageButton btn1 = (ImageButton) findViewById(R.id.btn1);
		ImageButton btn2 = (ImageButton) findViewById(R.id.btn2);
		ImageButton btn3 = (ImageButton) findViewById(R.id.btn3);
		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		// bgm = MediaPlayer.create(this, R.raw.bgm);
		// bgm.start();
		mainAct = this;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn0:
			Intent intent = new Intent(this, Unesco1.class);
			intent.putExtra("MENU", "menu1");
			startActivity(intent);
			break;
		case R.id.btn1:
			Intent intent2 = new Intent(this, Unesco1.class);
			intent2.putExtra("MENU", "menu2");
			startActivity(intent2);
			break;
		case R.id.btn2:
			Intent intent3 = new Intent(this, Unesco1.class);
			intent3.putExtra("MENU", "menu3");
			startActivity(intent3);
			break;
		case R.id.btn3:
			Intent intent4 = new Intent(this, FirstMenu.class);
			startActivity(intent4);
			break;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (FirstMenu.mp != null) {
			FirstMenu.mp.release();
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
