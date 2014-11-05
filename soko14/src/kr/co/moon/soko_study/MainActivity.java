package kr.co.moon.soko_study;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView btn0 = (ImageView) findViewById(R.id.btn0);
		btn0.setOnClickListener(this);
		ImageView btn1 = (ImageView) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		ImageView btn2 = (ImageView) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		ImageView btn3 = (ImageView) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn0:
			startActivity(new Intent(this, Menu1.class));
			break;
		case R.id.btn1:
			startActivity(new Intent(this, Menu2.class));
			break;
		case R.id.btn2:
			startActivity(new Intent(this, SubMenu1.class));
			break;
		case R.id.btn3:
			Intent intent = new Intent(this, M1.class);
			intent.putExtra("BTNID", 33);
			intent.putExtra("BOOL", 2);
			startActivity(intent);

			break;
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
