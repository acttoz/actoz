package a.com.moon.character;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
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
		ImageButton btn4 = (ImageButton) findViewById(R.id.btn4);
		ImageButton btn5 = (ImageButton) findViewById(R.id.btn5);
		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		// bgm = MediaPlayer.create(this, R.raw.bgm);
		// bgm.start();
		mainAct = this;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn0:
			Intent intent = new Intent(this, M1.class);
			intent.putExtra("BTNID", "1");
			startActivity(intent);
			break;
		case R.id.btn1:
			Intent intent2 = new Intent(this, M1.class);
			intent2.putExtra("BTNID", "2");
			startActivity(intent2);
			break;
		case R.id.btn2:
			Intent intent3 = new Intent(this, M1.class);
			intent3.putExtra("BTNID", "3");
			startActivity(intent3);
			break;
		case R.id.btn3:
			Intent intent4 = new Intent(this, M1.class);
			intent4.putExtra("BTNID", "4");
			startActivity(intent4);
			break;
		case R.id.btn4:
			Intent intent5 = new Intent(this, M1.class);
			intent5.putExtra("BTNID", "5");
			startActivity(intent5);
			break;
		case R.id.btn5:
			Intent intent6 = new Intent(this, M1.class);
			intent6.putExtra("BTNID", "6");
			startActivity(intent6);
			break;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
