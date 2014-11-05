package kr.co.moon.soko_study;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SubMenu1 extends Activity implements OnClickListener {

	Intent intent;
	public static ArrayList<Integer> mId;
	TextView playlist;
	String tempText;
	Button back;
	Button movie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submenu1);
		mId = new ArrayList<Integer>();
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

		back = (Button) findViewById(R.id.btn_back);
		movie = (Button) findViewById(R.id.btn_movie);
		back.setOnClickListener(this);
		movie.setOnClickListener(this);
		playlist = (TextView) findViewById(R.id.playlist);

		intent = new Intent(this, M1.class);
		intent.putExtra("BOOL", 2);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.imageButton1:
			mId.add(1);
			break;
		case R.id.imageButton2:
			mId.add(2);
			break;
		case R.id.imageButton3:
			mId.add(3);
			break;
		case R.id.imageButton4:
			mId.add(4);
			break;
		case R.id.imageButton5:
			mId.add(5);
			break;
		case R.id.imageButton6:
			mId.add(6);
			break;
		case R.id.imageButton7:
			mId.add(7);
			break;
		case R.id.imageButton8:
			mId.add(8);
			break;
		case R.id.imageButton9:
			mId.add(9);
			break;
		case R.id.imageButton10:
			mId.add(10);
			break;
		case R.id.imageButton11:
			mId.add(11);
			break;
		case R.id.imageButton12:
			mId.add(12);
			break;
		case R.id.imageButton13:
			mId.add(13);
			break;
		case R.id.imageButton14:
			mId.add(14);
			break;
		case R.id.imageButton15:
			mId.add(15);
			break;
		case R.id.imageButton16:
			mId.add(16);
			break;
		case R.id.imageButton17:
			mId.add(17);
			break;
		case R.id.imageButton18:
			mId.add(18);
			break;

		case R.id.btn_back:
			mId.remove(mId.size() - 1);
			break;
		case R.id.btn_movie:
			startActivity(new Intent(this, M2.class));
			break;
		}
		tempText = "";
		for (int i = 0; i < mId.size(); i++) {
			if (i == 0)
				tempText += String.valueOf(mId.get(i));
			else
				tempText += String.valueOf("->" + mId.get(i));
		}
		playlist.setText(tempText);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mId.clear();
		tempText = "";
		for (int i = 0; i < mId.size(); i++) {
			if (i == 0)
				tempText += String.valueOf(mId.get(i));
			else
				tempText += String.valueOf("->" + mId.get(i));
		}
		playlist.setText(tempText);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
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
