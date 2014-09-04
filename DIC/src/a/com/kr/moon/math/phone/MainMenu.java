package a.com.kr.moon.math.phone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainMenu extends Activity implements OnClickListener {
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	ImageView bSolve, bView, bReview, bTutorial;
	Drawable alpha1, alpha2, alpha3, alpha4;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		bSolve = (ImageView) findViewById(R.id.menu1);
		bView = (ImageView) findViewById(R.id.menu2);
		bReview = (ImageView) findViewById(R.id.menu3);
		bTutorial = (ImageView) findViewById(R.id.menu4);
		bSolve.setOnClickListener(this);
		bView.setOnClickListener(this);
		bReview.setOnClickListener(this);
		bTutorial.setOnClickListener(this);

		alpha1 = bSolve.getDrawable();
		alpha2 = bView.getDrawable();
		alpha3 = bReview.getDrawable();
		alpha4 = bTutorial.getDrawable();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.menu1:
			alpha1.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu1Intent = new Intent(this, Math_Select_Main.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu1Intent);
			break;
		case R.id.menu2:
			alpha2.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu2Intent = new Intent(this, ChartAct.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu2Intent);
			break;
		case R.id.menu3:
			alpha3.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu3Intent = new Intent(this, AndroidImageView.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu3Intent);
			break;
		case R.id.menu4:
			alpha4.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu4Intent = new Intent(this, Math_QuizVIEW.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu4Intent);
			break;

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha4.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}
}
