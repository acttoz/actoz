package kr.co.moon.unesco;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Goal extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal);
		// TODO Auto-generated method stub
		Button btn = (Button) findViewById(R.id.blankBtn);
		btn.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Quiz5 quizAct = (Quiz5) Quiz5.quiz1;
		quizAct.finish();
		FirstMenu.mp.release();
		MainActivity mainAct2 = (MainActivity) MainActivity.mainAct;
		mainAct2.finish();
		finish();
		// ActivityManager am = (ActivityManager)
		// getSystemService(ACTIVITY_SERVICE);
		// am.restartPackage(getPackageName());

	}

}
