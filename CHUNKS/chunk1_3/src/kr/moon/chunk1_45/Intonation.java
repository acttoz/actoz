package kr.moon.chunk1_45;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Intonation extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intonation);
		Bitmap myBitmap2;
		ImageView into=(ImageView)findViewById(R.id.intonation);
		Intent intent = getIntent();
		String chapter = intent.getStringExtra("CHAPTER");
		String tmpSign = "speak" + chapter;
		int lid = this.getResources().getIdentifier(tmpSign, "drawable",
				this.getPackageName());	
		myBitmap2 = BitmapFactory.decodeResource(getResources(), lid);
		into.setImageBitmap(myBitmap2);
	    // TODO Auto-generated method stub
	}

}
