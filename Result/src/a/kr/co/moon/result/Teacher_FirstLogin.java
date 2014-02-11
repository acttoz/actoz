package a.kr.co.moon.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.jar.Attributes.Name;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;

import a.kr.co.moon.result.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Teacher_FirstLogin extends Activity implements OnClickListener {
	String ban = null;
	String school = null;
	Button banEdit;
	Button idSaveBtn;
	Button schoolBtn;
	TextView title1, title2;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	File idXml, dayDb;
	DayHelper mHelper;
	SQLiteDatabase db;
	EditText schoolSearch;
	int newVer;
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_firstlogin);
		// checkNetwokState();

		// TODO Auto-generated method stub
		schoolSearch = (EditText) findViewById(R.id.schoolInput);

		idSaveBtn = (Button) findViewById(R.id.idSaveBtn);
		idSaveBtn.setOnClickListener(this);

		schoolBtn = (Button) findViewById(R.id.schoolBtn);
		schoolBtn.setOnClickListener(this);

		banEdit = (Button) findViewById(R.id.banBtn);
		banEdit.setOnClickListener(this);

		title1 = (TextView) findViewById(R.id.title1);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.schoolBtn:
			// firstLoginIntent.putExtra("BAN", ban);

			String mSchoolSearch = schoolSearch.getText().toString();
			if (mSchoolSearch.equals("")) {
				Toast toast = Toast.makeText(this, "검색어를 입력해주세요.",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				schoolSearch.setText("");
				mSchoolSearch = mSchoolSearch.replace("초등학교", "");
				mSchoolSearch = mSchoolSearch.replace("초등", "");
				Log.d("dd", "학교이름" + mSchoolSearch);

				Intent schoolIntent = new Intent(this, SchoolPicker.class);
				schoolIntent.putExtra("SCHOOL", mSchoolSearch);
				startActivity(schoolIntent);
			}
			break;

		case R.id.banBtn:
			Intent banIntent = new Intent(this, NumberPicker.class);
			banIntent.putExtra("MAX", 12);
			banIntent.putExtra("GRADECHOICE", false);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(banIntent);
			break;

		case R.id.numBtn:
			Intent numIntent = new Intent(this, NumberPicker.class);
			numIntent.putExtra("MAX", 36);
			numIntent.putExtra("GRADECHOICE", true);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(numIntent);
			break;

		case R.id.idSaveBtn:
			if (idPrefs.getString("BAN", "") == ""
					| idPrefs.getString("SCHOOL", "학교") == "학교") {
				Toast toast = Toast.makeText(this, "학교와 학반을 정확히 입력해주세요!!",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;

			} else {
				Intent firstLoginIntent = new Intent(this,
						Math_Select_Main.class);
				startActivity(firstLoginIntent);
				finish();
				break;
			}

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (idPrefs.getString("SCHOOL", "") == "") {
			school = "학교이름을 검색";
		} else {
			school = idPrefs.getString("SCHOOL", "");
		}

		ban = idPrefs.getString("BAN", "");
		if (ban.equals("")) {
			ban = "몇 반인가요?";
		} else {
			ban = ban + "반";
		}

		schoolSearch.setHint(school);
		banEdit.setText(ban);
	}
}// 클래스
