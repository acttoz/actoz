package kr.co.moon.speedalim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.adam.publisher.AdView;

import java.io.File;

public class AddAccount extends Activity implements OnClickListener {
	String ban = null;
	String grade = null;
	String school = null;
	EditText gradeEdit;
	EditText banEdit;
	Button startBtn;
	Button schoolBtn;
	TextView title1, title2;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	File idXml, dayDb;
	DayHelper mHelper;
	SQLiteDatabase db;
	int newVer;
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account);
		// checkNetwokState();



		schoolBtn = (Button) findViewById(R.id.btnSchool);
		schoolBtn.setOnClickListener(this);
		startBtn = (Button) findViewById(R.id.btnStart);
		startBtn.setOnClickListener(this);

		gradeEdit=(EditText)findViewById(R.id.grade);
		banEdit=(EditText)findViewById(R.id.ban);


//		schoolBtn.setTypeface(SpeedAlimActivity.mTypeface);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

	}

	public boolean checkNetwokState() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo lte_4g = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		boolean blte_4g = false;
		if (lte_4g != null)
			blte_4g = lte_4g.isConnected();
		if (mobile.isConnected() || wifi.isConnected() || blte_4g)
			return true;
		else {

			// 여기는 걍 네트워크 상태 체크와 상관없는 연결이 잘 안될때 경고문 띄움 ㅋㅋ
			AlertDialog.Builder dlg = new AlertDialog.Builder(AddAccount.this);
			dlg.setTitle("네트워크 오류");
			dlg.setMessage("'스피드 알림장'은 네트워크 연결이 필수입니다. 네트워크 연결후 다시 실행 해주세요.^^");
			dlg.setIcon(R.drawable.search);
			dlg.setNegativeButton("종료", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			});
			dlg.setCancelable(false);
			dlg.show();
			return false;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {


		case R.id.btnSchool:
			// firstLoginIntent.putExtra("BAN", ban);
			Intent schoolIntent = new Intent(this, SchoolPicker.class);
			startActivity(schoolIntent);
			break;



		case R.id.btnStart:

			if (gradeEdit.getText().toString().equals("")
					| gradeEdit.getText().toString().equals("")
					| idPrefs.getString("SCHOOL", "").equals("")) {
				Toast toast = Toast.makeText(this, "학교와 학반을 정확히 입력해주세요!!",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;

			} else {

//				editor.putString("SCHOOLID",
//						schoolID.get(position));
//				editor.commit();
//				Intent firstLoginIntent = new Intent(this,
//						SpeedAlimActivity.class);
//				startActivity(firstLoginIntent);
//				finish();
//				break;
			}

		}

	}

	void onSync() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set sync='1' where _id like '1'");
		db.close();

	}

	void offSync() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set sync='0' where _id like '1'");
		db.close();
	}

	void onThread() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set notice='1' where _id like '1'");
		db.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		grade = idPrefs.getString("GRADE", "") + "학년";
//		ban = idPrefs.getString("BAN", "") + "반";
//		school = idPrefs.getString("SCHOOL", "학교");
//
//		gradeEdit.setText(grade);
//		banEdit.setText(ban);
//
//		schoolBtn.setText(school);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}
}// 클래스
