package kr.co.moon.soko_study;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Menu2 extends Activity implements OnClickListener {
	ImageView content_image;
	Button content_btn1;
	Button content_btn2;
	Button content_btn3;
	Button content_btn4;
	Button content_btn5;
	Button content_btn6;
	Button movie_btn;
	Bitmap content_bitmap;
	Bitmap content_bitmap2;
	Bitmap content_bitmap3;
	Intent intent;
	int flag;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu2);
		intent = new Intent(this, M1.class);
		intent.putExtra("BOOL", 1);
		content_image = (ImageView) findViewById(R.id.content_image);
		content_bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.contents_basic1);

		content_btn1 = (Button) findViewById(R.id.btn1);
		content_btn2 = (Button) findViewById(R.id.btn2);
		content_btn3 = (Button) findViewById(R.id.btn3);
		content_btn4 = (Button) findViewById(R.id.btn4);
		content_btn5 = (Button) findViewById(R.id.btn5);
		content_btn6 = (Button) findViewById(R.id.btn6);
		movie_btn = (Button) findViewById(R.id.btn_movie);
		content_btn1.setOnClickListener(this);
		content_btn2.setOnClickListener(this);
		content_btn3.setOnClickListener(this);
		content_btn4.setOnClickListener(this);
		content_btn5.setOnClickListener(this);
		content_btn6.setOnClickListener(this);
		movie_btn.setOnClickListener(this);
		flag = 1;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		content_bitmap.recycle();
		switch (v.getId()) {
		case R.id.btn1:
			flag = 1;
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_basic1);
			content_image.setImageBitmap(content_bitmap);
			break;
		case R.id.btn2:
			flag = 2;
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_basic2);
			content_image.setImageBitmap(content_bitmap);
			break;
		case R.id.btn3:
			flag = 3;
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_basic3);
			content_image.setImageBitmap(content_bitmap);
			break;
		case R.id.btn4:
			flag = 4;
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_basic4);
			content_image.setImageBitmap(content_bitmap);
			break;
		case R.id.btn5:
			flag = 5;
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_basic5);
			content_image.setImageBitmap(content_bitmap);
			break;
		case R.id.btn6:
			flag = 6;
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_basic6);
			content_image.setImageBitmap(content_bitmap);
			break;
		case R.id.btn_movie:
			goMovie();
			break;

		}
	}

	private void goMovie() {
		// TODO Auto-generated method stub
		switch (flag) {
		case 1:
			intent.putExtra("BTNID", 5);
			startActivity(intent);
			break;
		case 2:
			intent.putExtra("BTNID", 8);
			startActivity(intent);
			break;
		case 3:
			intent.putExtra("BTNID", 9);
			startActivity(intent);
			break;
		case 4:
			intent.putExtra("BTNID", 10);
			startActivity(intent);
			break;
		case 5:
			intent.putExtra("BTNID", 12);
			startActivity(intent);
			break;
		case 6:
			intent.putExtra("BTNID", 21);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		content_bitmap.recycle();
	}

}
