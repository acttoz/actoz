package kr.co.moon.soko_study;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Info extends Activity implements OnClickListener {
	ImageView content_image;
	Button content_btn1;
	Button content_btn2;
	Button content_btn3;
	Bitmap content_bitmap;
	Bitmap content_bitmap2;
	Bitmap content_bitmap3;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		content_image = (ImageView) findViewById(R.id.content_image);
		content_bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.contents_text_1);

		content_btn1 = (Button) findViewById(R.id.btn1);
		content_btn2 = (Button) findViewById(R.id.btn2);
		content_btn3 = (Button) findViewById(R.id.btn3);
		content_btn1.setOnClickListener(this);
		content_btn2.setOnClickListener(this);
		content_btn3.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		content_bitmap.recycle();
		switch (v.getId()) {
		case R.id.btn1:
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_text_1);
			break;
		case R.id.btn2:
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_text_2);
			break;
		case R.id.btn3:
			content_bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.contents_text_3);
			break;

		}
		content_image.setImageBitmap(content_bitmap);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		content_bitmap.recycle();
	}

}
