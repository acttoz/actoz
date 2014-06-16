package kr.moon.chunk2_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						logo.startAnimation(fade_Out);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, 0, 7.0f);
						logo.setLayoutParams(params);
						logo.setImageResource(R.drawable.john);
						logo.startAnimation(fade_In);
					}
				});
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT, 0, 4.0f);
						logo.setLayoutParams(params2);
						logo.startAnimation(fade_Out);

						logo.setImageResource(R.drawable.moon_logo);
						logo.startAnimation(fade_In);
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent intent2 = new Intent(Intro.this, List.class);
				startActivity(intent2);
				finish();
			}
		}).start();
	}
}
