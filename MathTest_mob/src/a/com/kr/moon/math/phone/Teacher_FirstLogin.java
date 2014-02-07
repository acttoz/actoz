package a.com.kr.moon.math.phone;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	static int checkPass = 3524;

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
		Toast toast = Toast.makeText(this, "샘플로 수남초등학교 5학년 3반으로 입력됨.", 1000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		editor.putString("SCHOOL", "경남_수남초");
		editor.putString("BAN", "3");
		editor.commit();

	}

	public void mPassword() {

		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.custom_dialog,
				(ViewGroup) findViewById(R.id.layout_root));

		AlertDialog.Builder aDialog = new AlertDialog.Builder(
				Teacher_FirstLogin.this);// 여기서buttontest는
		// 패키지이름
		aDialog.setTitle("임시 인증번호 '1234'를 넣으시오.");
		aDialog.setView(layout);

		aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				EditText editText = (EditText) layout
						.findViewById(R.id.inputText);
				try {

					int inputText = Integer.parseInt(editText.getText()
							.toString());
					if (inputText == 1234) {
						Intent firstLoginIntent = new Intent(
								Teacher_FirstLogin.this, Teacher_Menu.class);
						startActivity(firstLoginIntent);
					} else {
						finish();
						startActivity(new Intent(Teacher_FirstLogin.this,
								Teacher_FirstLogin.class));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		aDialog.setNegativeButton("종료", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();

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

				Toast toast = Toast.makeText(this, "임시 인증번호는 '1234'입니다.",
						1000);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				mPassword();
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
