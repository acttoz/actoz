package kr.co.moon.unesco;

import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Intro extends Activity {
	ImageView logo;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		logo = (ImageView) findViewById(R.id.logo);
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			public void run() {

				final Animation fade_In = AnimationUtils.loadAnimation(
						Intro.this, R.anim.alpha);
				final Animation fade_Out = AnimationUtils.loadAnimation(
						Intro.this, R.anim.fade_out);
				logo.startAnimation(fade_In);
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						logo.startAnimation(fade_Out);

						logo.setImageResource(R.drawable.logo_500);
						logo.startAnimation(fade_In);
					}
				});

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				Intent intent2 = new Intent(Intro.this, MainActivity.class);
				startActivity(intent2);
				finish();
			}
		}).start();
	}
}
