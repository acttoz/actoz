package kr.co.moon.soko_study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class FirstMenu extends Activity implements OnClickListener {

	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstmenu);

		ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton1.setOnClickListener(this);
		ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton2.setOnClickListener(this);
		ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton3.setOnClickListener(this);
		ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
		imageButton4.setOnClickListener(this);
		ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
		imageButton5.setOnClickListener(this);
		ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
		imageButton6.setOnClickListener(this);

		Button homebtn = (Button) findViewById(R.id.button1);
		homebtn.setOnClickListener(this);

		intent = new Intent(this, M1.class);
		intent.putExtra("BOOL", 1);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.imageButton1:
			intent.putExtra("BTNID", 5);
			startActivity(intent);
			break;
		case R.id.imageButton2:
			intent.putExtra("BTNID", 8);
			startActivity(intent);
			break;
		case R.id.imageButton3:
			intent.putExtra("BTNID", 9);
			startActivity(intent);
			break;
		case R.id.imageButton4:
			intent.putExtra("BTNID", 10);
			startActivity(intent);
			break;
		case R.id.imageButton5:
			intent.putExtra("BTNID", 12);
			startActivity(intent);
			break;
		case R.id.imageButton6:
			intent.putExtra("BTNID", 21);
			startActivity(intent);
			break;

		case R.id.button1:
			finish();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
