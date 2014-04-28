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

public class SubMenu1 extends Activity implements OnClickListener {

	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submenu1);

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
		ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
		imageButton7.setOnClickListener(this);
		ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
		imageButton8.setOnClickListener(this);
		ImageButton imageButton9 = (ImageButton) findViewById(R.id.imageButton9);
		imageButton9.setOnClickListener(this);
		ImageButton imageButton10 = (ImageButton) findViewById(R.id.imageButton10);
		imageButton10.setOnClickListener(this);
		ImageButton imageButton11 = (ImageButton) findViewById(R.id.imageButton11);
		imageButton11.setOnClickListener(this);
		ImageButton imageButton12 = (ImageButton) findViewById(R.id.imageButton12);
		imageButton12.setOnClickListener(this);
		ImageButton imageButton13 = (ImageButton) findViewById(R.id.imageButton13);
		imageButton13.setOnClickListener(this);
		ImageButton imageButton14 = (ImageButton) findViewById(R.id.imageButton14);
		imageButton14.setOnClickListener(this);
		ImageButton imageButton15 = (ImageButton) findViewById(R.id.imageButton15);
		imageButton15.setOnClickListener(this);
		ImageButton imageButton16 = (ImageButton) findViewById(R.id.imageButton16);
		imageButton16.setOnClickListener(this);
		ImageButton imageButton17 = (ImageButton) findViewById(R.id.imageButton17);
		imageButton17.setOnClickListener(this);
		ImageButton imageButton18 = (ImageButton) findViewById(R.id.imageButton18);
		imageButton18.setOnClickListener(this);
		ImageButton imageButton19 = (ImageButton) findViewById(R.id.imageButton19);
		imageButton19.setOnClickListener(this);
		ImageButton imageButton20 = (ImageButton) findViewById(R.id.imageButton20);
		imageButton20.setOnClickListener(this);
		ImageButton imageButton21 = (ImageButton) findViewById(R.id.imageButton21);
		imageButton21.setOnClickListener(this);
		
		Button homebtn = (Button)findViewById(R.id.button1);
		homebtn.setOnClickListener(this);

		intent = new Intent(this, M1.class);
		intent.putExtra("BOOL", 2);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.imageButton1:
			intent.putExtra("BTNID", 1);
			startActivity(intent);
			break;
		case R.id.imageButton2:
			intent.putExtra("BTNID", 2);
			startActivity(intent);
			break;
		case R.id.imageButton3:
			intent.putExtra("BTNID", 3);
			startActivity(intent);
			break;
		case R.id.imageButton4:
			intent.putExtra("BTNID", 4);
			startActivity(intent);
			break;
		case R.id.imageButton5:
			intent.putExtra("BTNID", 5);
			startActivity(intent);
			break;
		case R.id.imageButton6:
			intent.putExtra("BTNID", 6);
			startActivity(intent);
			break;
		case R.id.imageButton7:
			intent.putExtra("BTNID", 7);
			startActivity(intent);
			break;
		case R.id.imageButton8:
			intent.putExtra("BTNID", 8);
			startActivity(intent);
			break;
		case R.id.imageButton9:
			intent.putExtra("BTNID", 9);
			startActivity(intent);
			break;
		case R.id.imageButton10:
			intent.putExtra("BTNID", 10);
			startActivity(intent);
			break;
		case R.id.imageButton11:
			intent.putExtra("BTNID", 11);
			startActivity(intent);
			break;
		case R.id.imageButton12:
			intent.putExtra("BTNID", 12);
			startActivity(intent);
			break;
		case R.id.imageButton13:
			intent.putExtra("BTNID", 13);
			startActivity(intent);
			break;
		case R.id.imageButton14:
			intent.putExtra("BTNID", 14);
			startActivity(intent);
			break;
		case R.id.imageButton15:
			intent.putExtra("BTNID", 15);
			startActivity(intent);
			break;
		case R.id.imageButton16:
			intent.putExtra("BTNID", 16);
			startActivity(intent);
			break;
		case R.id.imageButton17:
			intent.putExtra("BTNID", 17);
			startActivity(intent);
			break;
		case R.id.imageButton18:
			intent.putExtra("BTNID", 18);
			startActivity(intent);
			break;
		case R.id.imageButton19:
			intent.putExtra("BTNID", 19);
			startActivity(intent);
			break;
		case R.id.imageButton20:
			intent.putExtra("BTNID", 20);
			startActivity(intent);
			break;
		case R.id.imageButton21:
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
