package a.kr.co.moon.result;

import a.kr.co.moon.result.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MathMainActivity extends Activity implements OnClickListener {
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	ImageButton bTeacher;
	ImageButton bStudent;
	Drawable alpha1, alpha2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.math_main);
		// TODO Auto-generated method stub

		bTeacher = (ImageButton) findViewById(R.id.teacher);
		bStudent = (ImageButton) findViewById(R.id.student);
		bTeacher.setOnClickListener(this);
		bStudent.setOnClickListener(this);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		grade = idPrefs.getString("NAME", "null");
		ban = idPrefs.getString("BAN", "null");
		mSchool = idPrefs.getString("SCHOOL", "null");

		if (grade.equals("null") | ban.equals("null") | mSchool.equals("null")) {
			// C2dm_BroadcastReceiver.c2dmIdCreate(this);

			// Intent firstLogin = new Intent(this, FirstLogin.class);
			// startActivity(firstLogin);
			// finish();
		}
		// '
		// onClick(bStudent);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.teacher:
			editor.putBoolean("ISTEACHER", true);
			editor.commit();
			alpha1 = bTeacher.getBackground();

			alpha1.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent teacherIntent = new Intent(this, Teacher_FirstLogin.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(teacherIntent);
			break;
		case R.id.student:
			editor.putBoolean("ISTEACHER", false);
			editor.commit();
			alpha2 = bStudent.getBackground();
			alpha2.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent gradeIntent = new Intent(this, FirstLogin.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(gradeIntent);
			break;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		alpha1 = bTeacher.getBackground();
		alpha2 = bStudent.getBackground();
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}
}
